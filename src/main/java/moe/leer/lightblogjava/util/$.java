package moe.leer.lightblogjava.util;

/**
 * @author leer
 * Created at 11/6/18 4:40 PM
 *
 * Unsorted utility
 */
public class $ {

  public static boolean StringNullOrEmpty(String s) {
    return s == null || s.isEmpty();
  }

  public static boolean StringNotNullAndEmpty(String s) {
    return s != null && !s.isEmpty();
  }

}
