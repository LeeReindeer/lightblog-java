package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.dao.UserDaoWrapper;
import moe.leer.lightblogjava.modle.User;
import moe.leer.lightblogjava.service.AuthenticationService;
import moe.leer.lightblogjava.util.CipherUtil;
import moe.leer.lightblogjava.util.CookieUtil;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author leer
 * Created at 10/26/18 7:31 PM
 * Handle user login/register and profile page
 */
@Controller
public class UserController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserDaoWrapper userDao;

  @Autowired
  private AuthenticationService authService;

  @GetMapping("/register")
  String getRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
    model.addAttribute("register", true);
    model.addAttribute("login", false);
    return "login";
  }

  @PostMapping("/register")
  String postRegister(HttpServletRequest request, HttpServletResponse response,
                      String username, String password, String passwordAgain, RedirectAttributes flash) {
    // check params
    if (!username.isEmpty()) {
      User exitUser = userDao.getUserByName(username);
      if (exitUser != null) {
        CtrlUtil.flashWaring(flash, "用户名已存在");
        return CtrlUtil.redirectTo("/register");
      }
    } else {
      return CtrlUtil.redirectTo("/register");
    }
    if (!password.equals(passwordAgain)) {
      CtrlUtil.flashWaring(flash, "两次输入的密码不同");
      return CtrlUtil.redirectTo("/register");
    }

    // save to db
    String hash = CipherUtil.getPasswordHash(password);
    User user = new User(username, User.DEFAULT_AVATAR, password, new Date());
    userDao.saveUser(user);

    CtrlUtil.flashWaring(flash, "注册成功，可以登录了");
    return CtrlUtil.redirectTo("/login");
  }

  @GetMapping("/login")
  String getLogin(HttpServletRequest request, HttpServletResponse response,
                  Model model) {
    //todo redirect to home if already login
    model.addAttribute("register", false);
    model.addAttribute("login", true);
    return "login";
  }

  @PostMapping("/login")
  String postLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes flash,
                   String username, String password /*boolean rememberMe*/) {
    if (password == null || password.isEmpty() || username == null || username.isEmpty()) {
      return CtrlUtil.redirectTo("/login");
    }
    logger.info("username: {}", username);
    logger.info("password: {}", password);
    User user = userDao.getUserByName(username);
    if (user == null) {
      CtrlUtil.flashError(flash, "用户不存在");
      return CtrlUtil.redirectTo("/login");
    } else if (!authService.cmpPasswordHash(password, user)) {
      CtrlUtil.flashError(flash, "密码错误");
      logger.info("password error");
      return CtrlUtil.redirectTo("/login");
    } else {
      String token = authService.getToken(user);
      // put token in cookie
      CookieUtil.add(response, CtrlUtil.COOKIE_TOKEN, token);
      logger.info("login succeed");
      return CtrlUtil.redirectTo("/");
    }
  }

  @GetMapping("logout")
  String logout(HttpServletRequest request, HttpServletResponse response) {
    CookieUtil.clearRoot(response, CtrlUtil.COOKIES);
    logger.info("logout succeed");
    return CtrlUtil.redirectTo("/login");
  }
}
