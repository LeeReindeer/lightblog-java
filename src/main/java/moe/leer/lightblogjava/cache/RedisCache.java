package moe.leer.lightblogjava.cache;

import io.lettuce.core.RedisException;
import moe.leer.lightblogjava.util.SpringContextHolder;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author leer
 * Created at 6/21/19 12:16 AM
 */
public class RedisCache implements Cache {

  private Logger logger = LoggerFactory.getLogger(RedisCache.class);

  interface RedisCallback {
    Object call(RedisTemplate<Object, Object> redisTemplate);
  }

  static class RedisPartitionTolerantClient {

    private RedisTemplate<Object, Object> redisTemplate;

    public RedisPartitionTolerantClient() {
      this.redisTemplate = SpringContextHolder.redisTemplate();
    }

    public Object execute(RedisCallback callback) {
      try {
        // check connection, throw exception
        redisTemplate.getConnectionFactory().getConnection().isClosed();
        return callback.call(redisTemplate);
      } catch (RedisException | RedisConnectionFailureException ignored) {
        return null;
      }
    }
  }

  private String id;
  private ReadWriteLock readWriteLock;
  private RedisPartitionTolerantClient redisClient;

  public RedisCache(final String id) {
    if (null == id || "".equals(id)) {
      throw new IllegalArgumentException("mybatis redis cache need an id.");
    }
    this.id = id;
    readWriteLock = new ReentrantReadWriteLock();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void putObject(Object key, Object value) {
    if (key == null) return;
    redisClient = getRedisClient();
    redisClient.execute(redisTemplate -> {
      redisTemplate.opsForValue().set(key, value);
      // a list save keys, list's key is cache id
      redisTemplate.opsForList().leftPush(id, key);
      return null;
    });
  }

  @Override
  public Object getObject(Object key) {
    if (key == null) return null;
    redisClient = getRedisClient();
    return redisClient.execute(redisTemplate -> redisTemplate.opsForValue().get(key));
  }

  @Override
  public Object removeObject(Object key) {
    if (key == null) return null;
    redisClient = getRedisClient();
    return redisClient.execute(redisTemplate -> {
      Boolean result = redisTemplate.delete(key);
      redisTemplate.opsForList().leftPop(id);
      return result;
    });
  }

  @Override
  public void clear() {
    redisClient = getRedisClient();
    redisClient.execute(redisTemplate -> {
//      Long len = redisTemplate.opsForList().size(id);
//      if (len == null || len == 0) return null;
//      List<Object> list = redisTemplate.opsForList().range(id, 0, len - 1);
//      for (Object key : list) {
//        redisTemplate.delete(key);
//      }
      redisTemplate.execute((org.springframework.data.redis.core.RedisCallback<Object>) connection -> {
        connection.flushDb();
        return null;
      });
      return null;
    });
  }

  @Override
  public int getSize() {
    redisClient = getRedisClient();
    return (Integer) redisClient.execute(redisTemplate -> {
      Long size = redisTemplate.opsForList().size(id);
      if (size == null) {
        return 0;
      }
      return Math.toIntExact(size);
    });
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return readWriteLock;
  }

  private RedisPartitionTolerantClient getRedisClient() {
    if (redisClient == null) {
      redisClient = new RedisPartitionTolerantClient();
    }
    return redisClient;
  }
}
