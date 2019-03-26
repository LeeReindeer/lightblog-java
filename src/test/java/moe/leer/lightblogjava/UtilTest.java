package moe.leer.lightblogjava;

import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.util.$;
import org.junit.Test;

import java.util.Date;

/**
 * @author leer
 * Created at 11/6/18 5:41 PM
 */
public class UtilTest {

  @Test
  public void testTagContent() {
    LightBlog lightBlog = null;
    try {
      lightBlog = $.getBlogTag("#Java#\n" +
          "println(\"Hello wwww\")");
    } catch ($.Tag2LongException e) {
      e.printStackTrace();
      System.out.println("tag too long");
      return;
    }
    System.out.println("content: " + lightBlog.blog.blogContent);
    System.out.println("tag: " + lightBlog.tagName);
    System.out.println("tag length: " + lightBlog.tagName.length());
  }

  @Test
  public void testTimezone() {
    System.out.println($.formatTime(new Date()));
  }

}
