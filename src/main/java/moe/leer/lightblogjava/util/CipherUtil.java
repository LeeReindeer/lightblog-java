package moe.leer.lightblogjava.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author leer
 * Created at 10/27/18 2:01 PM
 */
public class CipherUtil {

  private static final String MAC_NAME = "HmacSHA1";
  private static final String ENCODING = "UTF-8";

  public static String getPasswordHash(String rawPass) {
    return sha1(rawPass);
  }

  private static String sha1(String key) {
    try {
      Mac hasher = Mac.getInstance(MAC_NAME);
      hasher.init(new SecretKeySpec(key.getBytes(), MAC_NAME));

      byte[] hash = hasher.doFinal("".getBytes()); // EMPTY DATA
      // to lowercase hexits
      return DatatypeConverter.printHexBinary(hash).toLowerCase();
      // to base64
      //return DatatypeConverter.printBase64Binary(hash);
    } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
    }
    return null;
  }

  public static String encryptBase64(String data) {
    return (new BASE64Encoder()).encodeBuffer(data.getBytes());
  }

  public static String decryptBase64(String key) {
    try {
      return new String((new BASE64Decoder()).decodeBuffer(key));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
