package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.model.Blog;
import moe.leer.lightblogjava.model.LightBlog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author leer
 * Created at 10/22/18 8:19 PM
 */
@Component
public interface BlogDao {

  List<LightBlog> getTimelineByUIDWithPaging(@Param("uid") Long uid, @Param("page") int page);

  Long getTimelineCnt(Long uid);

  LightBlog getBlogDetail(Long blogId);

  LightBlog getBlogById(Long id);

  List<LightBlog> getBlogsByUID(long uid);

  void saveBlog(Blog blog);

  void updateBlog(@Param("id") Long id, @Param("content") String newContent, @Param("date") Date newDate);

  void deleteBlog(Long blogId);


  /**
   * get like count for the blog
   *
   * @param blogId the id
   */
  Integer getBlogLike(Long blogId);

  Integer getBlogUnlike(Long blogId);

  /**
   * increase like cnt for this blog
   *
   * @param blogId the blog id
   * @param uid    who like this blog?
   */
  void incLikeBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  /**
   * cancel like this blog
   * only if this user already liked and like cnt is greater than 0
   *
   * @param blogId the blog id
   * @param uid    who cancel like
   */
  void decLikeBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  /**
   * check whether the user like this blog in wrapper method
   *
   * @param blogId the blog id
   * @param uid    who
   */
  int isUserLikedBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  int isUserDislikeBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  void incDislikeBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  void decDislikeBlog(@Param("blogId") Long blogId, @Param("uid") Long uid);

  void incBlogComment(Long blogId);

  void decBlogComment(Long blogId);
}
