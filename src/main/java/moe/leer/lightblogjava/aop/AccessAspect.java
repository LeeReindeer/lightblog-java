package moe.leer.lightblogjava.aop;

import moe.leer.lightblogjava.service.AuthenticationService;
import moe.leer.lightblogjava.util.CookieUtil;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leer
 * Created at 10/27/18 2:43 PM
 */
@Aspect
@Configuration
public class AccessAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private AuthenticationService authService;

  @Around("execution(* moe.leer.lightblogjava.controller.*.*(..)) " +
      "&& !execution(* moe.leer.lightblogjava.controller.UserController.*(..))")
  public String around(ProceedingJoinPoint joinPoint) {
    //check login
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    if (joinPoint.getArgs()[0] instanceof HttpServletRequest)
      request = (HttpServletRequest) joinPoint.getArgs()[0];
    if ((joinPoint.getArgs()[1] instanceof HttpServletResponse))
      response = (HttpServletResponse) joinPoint.getArgs()[1];

    if (request == null || response == null) {
      logger.error("args not match: {} {}", joinPoint.getArgs()[0].getClass().getSimpleName(),
          joinPoint.getArgs()[1].getClass().getSimpleName());
      CookieUtil.clearRoot(response, CtrlUtil.COOKIES);
      return CtrlUtil.redirectTo("/login");
    }

    logger.info("checking login");
    boolean isLogin = authService.isLogin(request);
    if (isLogin) {
      try {
        return (String) joinPoint.proceed(joinPoint.getArgs());
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    } else {
      logger.warn("you're not login");
      CookieUtil.clearRoot(response, CtrlUtil.COOKIES);
      return CtrlUtil.redirectTo("/login");
    }
    CookieUtil.clearRoot(response, CtrlUtil.COOKIES);
    return "/error"; //todo write error page
  }
}
