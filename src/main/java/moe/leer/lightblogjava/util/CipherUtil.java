package moe.leer.lightblogjava.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
      return Hex.encodeHexString(hash).toLowerCase();
      // to base64
      //return DatatypeConverter.printBase64Binary(hash);
    } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
    }
    return null;
  }

  public static String encryptBase64(String data) {
    return new String(Base64.getEncoder().encode(data.getBytes()));
  }

  public static String decryptBase64(String key) {
    return new String(Base64.getDecoder().decode(key));
  }

}
