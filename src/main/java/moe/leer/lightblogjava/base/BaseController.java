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
//  @Autowired
//  protected AuthenticationService authService;

  //  protected boolean isLogin(HttpServletRequest request) {
//    return authService.isLogin(request);
//  }
//
  protected User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    if (user == null) return null;
    return userDao.getUserByName(user.getUsername());
  }

  protected String getCurrentUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    return user == null ? null : user.getUsername();
  }

}
