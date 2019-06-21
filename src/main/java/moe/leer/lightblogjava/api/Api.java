package moe.leer.lightblogjava.api;

import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.model.LightBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author leer
 * Created at 5/27/19 7:46 PM
 * <p>
 * RESTful api for lightblog
 */
@RestController
@RequestMapping("/api")
public class Api extends BaseController {

  @Autowired
  private BlogDaoWrapper blogDao;

  /*Blog start*/

  @GetMapping("/blog/liked/{id}")
  public boolean isLikedBlog(@PathVariable("id") Long blogId) {
    return blogDao.isLikedBlog(blogId, getCurrentUserId());
  }

  @GetMapping("/blog/disliked/{id}")
  public boolean isDisLikedBlog(@PathVariable("id") Long blogId) {
    return blogDao.isDislikeBlog(blogId, getCurrentUserId());
  }

  @GetMapping("/blog/likes/{id}")
  public int getBlogLikes(@PathVariable("id") Long blogId) {
    return blogDao.getBlogLike(blogId);
  }

  @GetMapping("/blog/dislikes/{id}")
  public int getBlogDislikes(@PathVariable("id") Long blogId) {
    return blogDao.getBlogUnlike(blogId);
  }

  /**
   * Like or recycle your like of the blog
   *
   * @return the like count
   */
  @PutMapping("/blog/like/{id}")
  public int likeBlog(@PathVariable("id") Long blogId) {
    Long userId = getCurrentUserId();
    blogDao.toggleLikeBlog(blogId, userId);
    Integer cnt = blogDao.getBlogLike(blogId);
    return cnt;
  }

  /**
   * DisLike or recycle your dislike of the blog
   *
   * @return the dislike count
   */
  @PutMapping("/blog/dislike/{id}")
  public int dislikeBlog(@PathVariable("id") Long blogId) {
    Long userId = getCurrentUserId();
    blogDao.toggleDislikeBlog(blogId, userId);
    Integer cnt = blogDao.getBlogUnlike(blogId);
    return cnt;
  }

  @DeleteMapping("/blog/{id}")
  public void deleteBlog(@PathVariable("id") Long blogId) {
    blogDao.deleteBlog(blogId);
  }

  //todo return uniform json:
  // {code: 200,data:{[],[]}}
  @GetMapping("/blog/{id}")
  public LightBlog getBlog(@PathVariable("id") Long blogId) {
    return blogDao.getBlogById(blogId);
  }

  //todo update blog
  /*Blog end*/
}
