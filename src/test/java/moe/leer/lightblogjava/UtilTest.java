package moe.leer.lightblogjava;

import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.util.$;
import org.junit.Test;

/**
 * @author leer
 * Created at 11/6/18 5:41 PM
 */
public class UtilTest {

  @Test
  public void testTagContent() {
    LightBlog lightBlog = $.getBlogTag("Test tag??? ");
    System.out.println("content: " + lightBlog.blog.blogContent);
    System.out.println("tag: " + lightBlog.tagName);
  }

}
