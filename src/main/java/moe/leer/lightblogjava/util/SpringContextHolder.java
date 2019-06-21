package moe.leer.lightblogjava.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author leer
 * Created at 6/21/19 12:47 AM
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

  private static ApplicationContext context;

  /**
   * Get redisTemplate from spring bean in static method
   */
  @SuppressWarnings("unchecked")
  public static RedisTemplate<Object, Object> redisTemplate() {
    return (RedisTemplate<Object, Object>) context.getBean("redisTemplate");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}
