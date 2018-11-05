package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.modle.Blog;
import moe.leer.lightblogjava.modle.LightBlog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author leer
 * Created at 10/22/18 8:23 PM
 */
@Component
public class BlogDaoWrapper implements BlogDao {

  private static Logger logger = LoggerFactory.getLogger(BlogDaoWrapper.class);

  @Autowired
  private BlogDao blogDao;

  @Autowired
  private TagDao tagDao;

  public static String getBlogPreview(Blog blog) {
    String blogPreview;
    if (blog.blogContent.length() > 140)
      blogPreview = blog.blogContent.substring(0, 139) + " ..."; // 140 words
    else
      blogPreview = blog.blogContent;
    return blogPreview;
  }

  public static void processLightBlog(LightBlog lightBlog, TagDao tagDao) {
    lightBlog.blogPreview = getBlogPreview(lightBlog.blog);

    if (lightBlog.blog.getBlogTagId() != null && lightBlog.blog.getBlogTagId() != 0)
      lightBlog.tagName = tagDao.getTagName(lightBlog.blog.getBlogTagId());
  }

  // get first page of timeline
  public List<LightBlog> getTimeline(Long uid) {
    return this.getTimelineByUIDWithPaging(uid, 1);
  }

  @Override
  public List<LightBlog> getTimelineByUIDWithPaging(Long uid, int page) {
    List<LightBlog> blogList = blogDao.getTimelineByUIDWithPaging(uid, (page - 1) * 20);
    blogList.forEach(blog -> processLightBlog(blog, tagDao));
    return blogList;
  }

  @Override
  public Long getTimelineCnt(Long uid) {
    return blogDao.getTimelineCnt(uid);
  }

  @Override
  public LightBlog getBlogDetail(Long blogId) {
    LightBlog blog = blogDao.getBlogDetail(blogId);
    processLightBlog(blog, tagDao);
    return blog;
  }

  @Override
  public LightBlog getBlogById(Long id) {
    LightBlog blog = blogDao.getBlogById(id);
    processLightBlog(blog, tagDao);
    return blog;
  }

  @Override
  public List<LightBlog> getBlogsByUID(long uid) {
    List<LightBlog> blogList = blogDao.getBlogsByUID(uid);
    blogList.forEach(lightBlog -> processLightBlog(lightBlog, tagDao));
    return blogList;
  }

  @Override
  public void saveBlog(Blog blog) {
    blogDao.saveBlog(blog);
  }

  @Override
  public void updateBlog(Blog blog) {
    blogDao.updateBlog(blog);
  }

  @Override
  public void deleteBlog(Long blogId) {
    blogDao.deleteBlog(blogId);
  }

  @Override
  public Integer getBlogLike(Long blogId) {
    return blogDao.getBlogLike(blogId);
  }

  @Override
  public Integer getBlogUnlike(Long blogId) {
    return blogDao.getBlogUnlike(blogId);
  }

  @Override
  public void incLikeBlog(Long blogId, Long uid) {
    blogDao.incLikeBlog(blogId, uid);
  }

  // todo check
  @Override
  public void decLikeBlog(Long blogId, Long uid) {
    if (blogDao.getBlogLike(blogId) > 0 && isLikedBlog(blogId, uid)) {
      blogDao.decLikeBlog(blogId, uid);
    } else {
      logger.warn("like count <= 0");
    }
  }

  public boolean isLikedBlog(Long blogId, Long uid) {
    return blogDao.isUserLikedBlog(blogId, uid) != 0;
  }

  public boolean isDislikeBlog(Long blogId, Long uid) {
    return blogDao.isUserDislikeBlog(blogId, uid) != 0;
  }

  @Override
  public void incDislikeBlog(Long blogId, Long uid) {
    blogDao.incDislikeBlog(blogId, uid);

  }

  //todo  check
  @Override
  public void decDislikeBlog(Long blogId, Long uid) {
    if (blogDao.getBlogUnlike(blogId) > 0 && isDislikeBlog(blogId, uid)) {
      blogDao.decDislikeBlog(blogId, uid);
    } else {
      logger.warn("dislike count <= 0");
    }
  }

  @Override
  public void incBlogComment(Long blogId) {
    blogDao.incBlogComment(blogId);
  }

  @Override
  public void decBlogComment(Long blogId) {
    blogDao.decBlogComment(blogId);
  }

  @Override
  public int isUserLikedBlog(Long blogId, Long uid) {
    throw new NotImplementedException();
  }

  @Override
  public int isUserDislikeBlog(Long blogId, Long uid) {
    throw new NotImplementedException();
  }
}
