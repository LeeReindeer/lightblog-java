package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.App;
import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.CommentDao;
import moe.leer.lightblogjava.model.Blog;
import moe.leer.lightblogjava.model.Comment;
import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.util.$;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author leer
 * Created at 10/26/18 7:29 PM
 */
@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController {

  @Autowired
  private BlogDaoWrapper blogDao;

  @Autowired
  private CommentDao commentDao;

  @GetMapping("/{id}")
  public String blogDetail(HttpServletRequest request, HttpServletResponse response,
                           Model model, @PathVariable("id") Long blogId) {
    User user = getCurrentUser(request);
    if (blogId == null || blogId == 0) return CtrlUtil.redirectTo("/");

    LightBlog lightBlog = blogDao.getBlogDetail(blogId);
    List<Comment> comments = commentDao.getAllByBlogId(blogId);

    model.addAttribute("user", user);
    model.addAttribute("username", user.getUserName());
    model.addAttribute("blog", lightBlog);
    model.addAttribute("comments", comments);
    model.addAttribute("redirect", request.getRequestURL().toString());
    return App.TEMPLATE_DETAIL;
  }

  @GetMapping("/delete/{id}")
  public String deleteBlog(HttpServletRequest request, HttpServletResponse response,
                           Model model, @PathVariable("id") Long blogId) {
    blogDao.deleteBlog(blogId);
    return CtrlUtil.redirectTo("/");
  }

  @GetMapping("/like/{id}")
  public String likeBlog(HttpServletRequest request, HttpServletResponse response,
                         Model model, @PathVariable("id") Long blogId,
                         @RequestParam("redirect") String redirectURL) {
    Long userId = getCurrentUser(request).getUserId();
    blogDao.toggleLikeBlog(blogId, userId);
    return CtrlUtil.redirectTo(redirectURL);
  }

  @GetMapping("/dislike/{id}")
  public String dislikeBlog(HttpServletRequest request, HttpServletResponse response,
                            Model model, @PathVariable("id") Long blogId,
                            @RequestParam("redirect") String redirectURL) {
    Long userId = getCurrentUser(request).getUserId();
    blogDao.toggleDislikeBlog(blogId, userId);
    return CtrlUtil.redirectTo(redirectURL);
  }

  @GetMapping("/edit/{id}")
  public String getEditBlog(HttpServletRequest request, HttpServletResponse response,
                            Model model, @PathVariable("id") Long blogId) {
    logger.warn("edit blog");
    model.addAttribute("blog", blogDao.getBlogById(blogId));
    return App.TEMPLATE_EDITBLOG;
  }

  @PostMapping("/edit/{id}")
  public String editBlog(HttpServletRequest request, HttpServletResponse response,
                         Model model, @PathVariable("id") Long blogId, String content) {
    String redirect = "/blog/" + blogId;
    LightBlog prevBlog = blogDao.getBlogById(blogId);
    if ($.StringNullOrEmpty(content) || prevBlog.blog.blogContent.equals(content)) return CtrlUtil.redirectTo(redirect);
    logger.warn("update blog");
    try {
      blogDao.updateBlog(blogId, content, new Date());
    } catch (Exception e) {
      logger.error("occur unsupported string");
      return CtrlUtil.redirectTo(redirect);
    }
    return CtrlUtil.redirectTo(redirect);
  }
}
