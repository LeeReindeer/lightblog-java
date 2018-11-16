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

  public static LightBlog getBlogTag(String content) {
    LightBlog lightBlog = new LightBlog();
    int firstSpace = 0;
    boolean hasTag = false;
    if (content.startsWith("#") || content.startsWith("＃")) {
      char[] charArray = content.toCharArray();
      for (int i = 0; i < charArray.length; i++) {
        if (charArray[i] == ' ') {
          firstSpace = i;
          break;
        }
      }
      if (firstSpace > 1 && firstSpace <= 25) {
        lightBlog.tagName = content.substring(1, firstSpace);
        hasTag = true;
      } else {
        hasTag = false;
      }
    }
    lightBlog.blog = new Blog();
    lightBlog.blog.blogContent = content.substring(hasTag ? firstSpace + 1 : firstSpace);
    return lightBlog;
  }

  public static String formatTime(Date date) {
    // App build just for GMT+8 Timezone
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return dateFormat.format(date);
  }

}
