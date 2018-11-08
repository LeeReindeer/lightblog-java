package moe.leer.lightblogjava.base;

import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leer
 * Created at 11/5/18 6:09 PM
 */
public abstract class BaseController {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  protected AuthenticationService authService;

  protected boolean isLogin(HttpServletRequest request) {
    return authService.isLogin(request);
  }

  protected User getCurrentUser(HttpServletRequest request) {
    return authService.getCurrentUser(request);
  }

}
