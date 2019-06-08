package moe.leer.lightblogjava.base;

import moe.leer.lightblogjava.dao.UserDaoWrapper;
import moe.leer.lightblogjava.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author leer
 * Created at 11/5/18 6:09 PM
 */
public abstract class BaseController {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  protected UserDaoWrapper userDao;

  protected User getCurrentUser() {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) getAuthentication().getPrincipal();
    if (user == null) return null;
    // the username with this user is user id in moe.leer.lightblog.model.User
    return userDao.getUserById(Long.valueOf(user.getUsername()));
  }

  // Get user id without SQL query
  protected Long getCurrentUserId() {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) getAuthentication().getPrincipal();
    if (user == null) return null;
    return Long.valueOf(user.getUsername());
  }

  protected String getCurrentUserName() {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) getAuthentication().getPrincipal();
    return user == null ? null : user.getUsername();
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}
