package moe.leer.lightblogjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import moe.leer.lightblogjava.util.$;

/**
 * @author leer
 * Created at 10/22/18 7:55 PM
 */
public class LightBlog {
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

  @JsonIgnore
  public String getTimeString() {
    return $.formatTime(this.blog.blogTime);
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
