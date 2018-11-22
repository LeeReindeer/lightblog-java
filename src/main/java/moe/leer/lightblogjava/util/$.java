package moe.leer.lightblogjava.util;

import moe.leer.lightblogjava.model.Blog;
import moe.leer.lightblogjava.model.LightBlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author leer
 * Created at 11/6/18 4:40 PM
 * <p>
 * Unsorted utility
 */
public class $ {

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);


  public static boolean StringNullOrEmpty(String s) {
    return s == null || s.isEmpty();
  }

  public static boolean StringNotNullAndEmpty(String s) {
    return s != null && !s.isEmpty();
  }

  public static class Tag2LongException extends Exception {
    public Tag2LongException() {
      super();
    }
  }

  public static LightBlog getBlogTag(String content) throws Tag2LongException {
    LightBlog lightBlog = new LightBlog();
    boolean hasTag = false;
    int tagEnd = 0;
    if (content.matches("^[#＃][^#＃]+[#＃][\\s\\S]+")) {
      char[] charArray = content.toCharArray();
      for (int i = 0; i < charArray.length; i++) {
        if (charArray[i] == '#' || charArray[i] == '＃') {
          if (i != 0) {
            tagEnd = i;
            break;
          }
        }
      }
      String tag = content.substring(1, tagEnd);
      if (tagEnd > 1 && tagEnd <= 25) {
        lightBlog.tagName = tag;
        hasTag = true;
      } else {
        throw new Tag2LongException();
      }
    }
    lightBlog.blog = new Blog();
    lightBlog.blog.blogContent = content.substring(hasTag ? tagEnd + 1 : 0);
    return lightBlog;
  }

  public static String formatTime(Date date) {
    // App build just for GMT+8 Timezone
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return dateFormat.format(date);
  }

}
