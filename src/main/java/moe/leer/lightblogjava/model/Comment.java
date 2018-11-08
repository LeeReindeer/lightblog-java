package moe.leer.lightblogjava.model;

import java.util.Date;

/**
 * @author leer
 * Created at 10/22/18 8:01 PM
 */
public class Comment {
  private Long commId;
  private Long commBlogId;
  private Long commFromUid;
  // set to 0 when reply to blog
  private Long commToUid;

  public String commContent;
  public Date commTime;
  public Integer commLike;
  // extra data
  private String commFromName;
  private String commFromAvatar;
  private String commToName;
  private String commToAvatar;

  public Comment() {
  }

  public Comment(Long commBlogId, Long commFromUid, Long commToUid, String commContent, Date commTime) {
    this.commBlogId = commBlogId;
    this.commFromUid = commFromUid;
    this.commToUid = commToUid;
    this.commContent = commContent;
    this.commTime = commTime;
    this.commLike = 0;
  }

  public Long getCommId() {
    return commId;
  }

  public Long getCommBlogId() {
    return commBlogId;
  }

  public void setCommBlogId(Long commBlogId) {
    this.commBlogId = commBlogId;
  }

  public Long getCommFromUid() {
    return commFromUid;
  }

  public void setCommFromUid(Long commFromUid) {
    this.commFromUid = commFromUid;
  }

  public Long getCommToUid() {
    return commToUid;
  }

  public void setCommToUid(Long commToUid) {
    this.commToUid = commToUid;
  }

  public String getCommFromName() {
    return commFromName;
  }

  public void setCommFromName(String commFromName) {
    this.commFromName = commFromName;
  }

  public String getCommFromAvatar() {
    return commFromAvatar;
  }

  public void setCommFromAvatar(String commFromAvatar) {
    this.commFromAvatar = commFromAvatar;
  }

  public String getCommToName() {
    return commToName;
  }

  public void setCommToName(String commToName) {
    this.commToName = commToName;
  }

  public String getCommToAvatar() {
    return commToAvatar;
  }

  public void setCommToAvatar(String commToAvatar) {
    this.commToAvatar = commToAvatar;
  }

  @Override
  public String toString() {
    return "Comment{" +
        "commId=" + commId +
        ", commBlogId=" + commBlogId +
        ", commFromUid=" + commFromUid +
        ", commToUid=" + commToUid +
        ", commContent='" + commContent + '\'' +
        ", commTime=" + commTime +
        ", commLike=" + commLike +
        '}';
  }
}
