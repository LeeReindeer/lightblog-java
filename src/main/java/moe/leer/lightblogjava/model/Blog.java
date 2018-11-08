package moe.leer.lightblogjava.model;

import java.util.Date;

/**
 * @author leer
 * Created at 10/22/18 7:38 PM
 */
public class Blog {
  // only getter
  private Long blogId;
  private Long blogUid;
  private Long blogTagId;
  public String blogContent;
  public Date blogTime;

  public Integer blogLike;
  public Integer blogUnLike;
  public Integer blogComment;

  public static final int LIKE = 0;
  public static final int UNLIKE = 1;

  // for mybatis
  public Blog() {
  }

  // create blog without tag
  public Blog(Long blogUid, String blogContent, Date blogTime) {
    this.blogUid = blogUid;
    this.blogContent = blogContent;
    this.blogTime = blogTime;
    this.blogLike = this.blogUnLike = this.blogComment = 0;
  }

  // create blog with tag
  public Blog(Long blogUid, Long blogTagId, String blogContent, Date blogTime) {
    this(blogUid, blogContent, blogTime);
    this.blogTagId = blogTagId;
  }

  public Long getBlogId() {
    return blogId;
  }

  public Long getBlogUid() {
    return blogUid;
  }

  public Long getBlogTagId() {
    return blogTagId;
  }

  public void setBlogUid(Long blogUid) {
    this.blogUid = blogUid;
  }

  public void setBlogTagId(Long blogTagId) {
    this.blogTagId = blogTagId;
  }

  @Override
  public String toString() {
    return "Blog{" +
        "blogId=" + blogId +
        ", blogUid=" + blogUid +
        ", blogTagId=" + blogTagId +
        ", blogContent='" + blogContent + '\'' +
        ", blogTime=" + blogTime +
        ", blogLike=" + blogLike +
        ", blogUnLike=" + blogUnLike +
        ", blogComment=" + blogComment +
        '}';
  }
}
