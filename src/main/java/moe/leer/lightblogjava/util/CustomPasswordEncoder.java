package moe.leer.lightblogjava.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author leer
 * Created at 4/19/19 7:10 PM
 */
public class CustomPasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    return CipherUtil.getPasswordHash(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    if (encodedPassword == null || encodedPassword.length() == 0) {
      return false;
    }
    return encode(rawPassword).equals(encodedPassword);
  }
}
