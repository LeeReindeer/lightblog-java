package moe.leer.lightblogjava.modle;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * @author leer
 * Created at 10/22/18 7:55 PM
 */
public class LightBlog {

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

  public Long id;
  public Blog blog;
  // extra data for convenience
  public String tagName;
  // substring(0, 140) of blog content
  public String blogPreview;
  // owner of this light blog
  public String userName;
  public String userAvatar;

  public LightBlog() {
  }

  public String getTimeString() {
    return dateFormat.format(this.blog.blogTime);
  }

  @Override
  public String toString() {
    return "LightBlog{" +
        "id=" + id +
        ", blog=" + blog +
        ", tagName='" + tagName + '\'' +
        ", blogPreview='" + blogPreview + '\'' +
        ", userName='" + userName + '\'' +
        ", userAvatar='" + userAvatar + '\'' +
        '}';
  }
}
