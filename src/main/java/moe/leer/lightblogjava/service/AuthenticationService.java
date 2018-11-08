package moe.leer.lightblogjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import moe.leer.lightblogjava.dao.UserDaoWrapper;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.util.CipherUtil;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leer
 * Created at 11/5/18 3:46 PM
 */
@Service
public class AuthenticationService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserDaoWrapper userDao;

  public static final String ISSUER = "Super LeeR";

  public String getToken(User user) {
    String token = "";
    try {
      token = JWT.create()
          .withIssuer(ISSUER)
          .withClaim("username", user.getUserName())
          .withClaim("uid", user.getUserId())
          .sign(Algorithm.HMAC256(user.getPassword()));
    } catch (JWTCreationException ignore) {
    }
    return token;
  }

  public User getCurrentUser(HttpServletRequest request) {
    String token = CtrlUtil.isCookieMiss(request);
    Long uid = null;
    if (token == null) {
      return null;
    } else {
      Claim claim = null;
      try {
        claim = JWT.decode(token).getClaim("uid");
      } catch (JWTDecodeException ignore) {
        logger.error("bad token: {}", token);
        return null;
      }
      uid = claim.asLong();
      if (uid == null) {
        logger.error("user not find, please relogin");
        return null;
      }
    }
    return userDao.getUserById(uid);
  }

  /**
   * Check JWT token is valid otr not
   *
   * @param request HttpServletRequest
   * @return is valid token
   */
  public boolean isLogin(HttpServletRequest request) {
    User user = getCurrentUser(request);
    String token = CtrlUtil.isCookieMiss(request);
    if (user == null) return false;
    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword()))
          .withIssuer(AuthenticationService.ISSUER)
          .build();
      verifier.verify(token);
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean cmpPasswordHash(String inputPassword, User userInDB) {
    return CipherUtil.getPasswordHash(inputPassword).equals(userInDB.getPassword());
  }

}
