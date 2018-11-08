package moe.leer.lightblogjava.model;

import java.util.Date;

/**
 * @author leer
 * Created at 10/22/18 8:11 PM
 */
public class Tag {
  private Long tagId;
  public String tagName;
  public Date tagTime;

  // for mybatis...
  public Tag() {
  }

  public Tag(String tagName, Date tagTime) {
    this.tagName = tagName;
    this.tagTime = tagTime;
  }

  public Long getTagId() {
    return tagId;
  }

  @Override
  public String toString() {
    return "Tag{" +
        "tagId=" + tagId +
        ", tagName='" + tagName + '\'' +
        ", tagTime=" + tagTime +
        '}';
  }
}
