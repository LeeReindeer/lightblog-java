package moe.leer.lightblogjava.util;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static moe.leer.lightblogjava.util.CtrlUtil.COOKIE_PASS;
import static moe.leer.lightblogjava.util.CtrlUtil.COOKIE_USER;

/**
 * @author leer
 * Created at 4/27/18 8:08 PM
 */

public class CookieUtil {

  public static final int MAX_AGE = 3600 * 24 * 365 * 10;
  public static final int SESSION_AGE = -1;

  public static void add(HttpServletResponse httpServletResponse, String path, String name, String value, Integer maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setSecure(false);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge);
    cookie.setPath(path);
    httpServletResponse.addCookie(cookie);
  }

  public static void add(HttpServletResponse httpServletResponse, String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setSecure(false);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(MAX_AGE);
    cookie.setPath("/");
    httpServletResponse.addCookie(cookie);
  }


  public static void clear(HttpServletResponse httpServletResponse, String path, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setPath(path);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    httpServletResponse.addCookie(cookie);
  }

  public static void clearAll(HttpServletResponse response, String path, String names[]) {
    for (String name : names) {
      clear(response, path, name);
    }
  }

  public static void clearRoot(HttpServletResponse response, String names[]) {
    CookieUtil.clearAll(response, "/", names);
  }

  public static String getValue(HttpServletRequest httpServletRequest, String name) {
    Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
    return cookie != null ? cookie.getValue() : null;
  }
}
