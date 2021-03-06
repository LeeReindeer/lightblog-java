package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leer
 * Created at 10/27/18 6:39 PM
 */
@Component
public class UserDaoWrapper implements UserDao {

  @Autowired
  private UserDao userDao;

  @Autowired
  private TagDao tagDao;

  @Override
  public List<User> searchAll(String key) {
    return userDao.searchAll(key);
  }

  @Override
  public User getUserByName(String name) {
    return userDao.getUserByName(name);
  }

  @Override
  public User getUserProfile(Long uid) {
    User user = userDao.getUserProfile(uid);
    user.myBlogs.forEach(lightBlog -> BlogDaoWrapper.processLightBlog(lightBlog, tagDao));
    return user;
  }

  @Override
  public User getUserById(Long id) {
    return userDao.getUserById(id);
  }

  @Override
  public String getPasswordHashByName(String name) {
    return userDao.getPasswordHashByName(name);
  }

  @Override
  public void saveUser(User uesr) {
    userDao.saveUser(uesr);
  }

  @Transactional
  @Override
  public void followUser(Long fromId, Long toId) {
    userDao.followUser(fromId, toId);
  }

  // Add Transactional annotation for multi SQL execute
  @Transactional
  @Override
  public void unFollowUser(Long fromId, Long toId) {
    userDao.unFollowUser(fromId, toId);
  }

  // Should not be called
  @Override
  @Deprecated
  public Long isFollowed(Long fromId, Long toId) {
    throw new IllegalStateException();
  }

  public boolean isUserFollowed(Long fromId, Long toId) {
    Long to = userDao.isFollowed(fromId, toId);
    return to != null && to.equals(toId);
  }
}
