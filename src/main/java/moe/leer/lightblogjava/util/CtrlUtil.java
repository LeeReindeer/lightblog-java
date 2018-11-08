package moe.leer.lightblogjava.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leer
 * Created at 10/26/18 8:26 PM
 */
public class CtrlUtil {
  public static final String FLASH_ERROR = "flash_error";
  public static final String FLASH_WARING = "flash_waring";
  public static final String FLASH_INFO = "flash_info";

  public static final String COOKIE_USER = "username";
  public static final String COOKIE_UID = "uid";
  public static final String COOKIE_PASS = "p";

  public static final String COOKIE_TOKEN = "token";

  public static final String[] COOKIES = new String[]{COOKIE_TOKEN};

  public static void flashError(RedirectAttributes flash, Object msg) {
    flash.addAttribute(FLASH_ERROR, msg);
  }

  public static void flashWaring(RedirectAttributes flash, Object msg) {
    flash.addAttribute(FLASH_WARING, msg);
  }

  public static void flashInfo(RedirectAttributes flash, Object msg) {
    flash.addAttribute(FLASH_INFO, msg);
  }

  public static String redirectTo(String url) {
    return "redirect:" + url;
  }

  public static String isCookieMiss(HttpServletRequest request) {
    return CookieUtil.getValue(request, COOKIE_TOKEN);
  }

  public static String getCurrentURL(HttpServletRequest request) {
    String queryString = request.getQueryString();
    if ($.StringNullOrEmpty(queryString)) {
      return request.getRequestURL().toString();
    } else {
      return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
  }
}
