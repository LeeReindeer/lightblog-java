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
  public boolean isDisLlkedBlog(@PathVariable("id") Long blogId) {
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

  @PutMapping("/blog/like/{id}")
  public void likeBlog(@PathVariable("id") Long blogId) {
    blogDao.toggleLikeBlog(blogId, getCurrentUserId());
  }

  @PutMapping("/blog/dislike/{id}")
  public void dislikeBlog(@PathVariable("id") Long blogId) {
    blogDao.toggleDislikeBlog(blogId, getCurrentUserId());
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
