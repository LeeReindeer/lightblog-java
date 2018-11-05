package moe.leer.lightblogjava;

/**
 * @author leer
 * Created at 10/27/18 3:59 PM
 */

import moe.leer.lightblogjava.util.CipherUtil;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * Created by xiang.li on 2015/2/27.
 */
public class HMAC {
  /**
   * 定义加密方式
   * MAC算法可选以下多种算法
   * <pre>
   * HmacMD5
   * HmacSHA1
   * HmacSHA256
   * HmacSHA384
   * HmacSHA512
   * </pre>
   */
  public static String test(String key) {
    try {
      Mac hasher = Mac.getInstance("HmacSHA1");
      hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA1"));

      byte[] hash = hasher.doFinal("".getBytes());
      // to lowercase hexits
      return DatatypeConverter.printHexBinary(hash).toLowerCase();
      // to base64
//      return DatatypeConverter.printBase64Binary(hash);
    } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
    }
    return null;
  }

  @Test
  public void main() throws Exception {
    String word = "0915";
    System.out.println(test(word)); // use password as key, data is empty
    assertEquals("fff6e8410211d03d5caffb7027277fc42ec6a0be", test(word));
    assertEquals("leer", CipherUtil.decryptBase64(CipherUtil.encryptBase64("leer")));
  }
}
