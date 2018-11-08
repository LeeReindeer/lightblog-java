package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.CommentDao;
import moe.leer.lightblogjava.model.Comment;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author leer
 * Created at 10/26/18 7:30 PM
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

  @Autowired
  private CommentDao commentDao;

  @Autowired
  private BlogDaoWrapper blogDao;

  @GetMapping("/like/{id}")
  public String likeComment(HttpServletRequest request, HttpServletResponse response,
                            Model model,
                            @PathVariable("id") Long commId, @RequestParam("redirect") String redirect) {
    commentDao.likeComment(commId);
    return CtrlUtil.redirectTo(redirect);
  }

  @GetMapping("/delete/{id}")
  public String deleteComment(HttpServletRequest request, HttpServletResponse response,
                              Model model, RedirectAttributes flash,
                              @PathVariable("id") Long id, @RequestParam("redirect") String redirect) {
    Comment comment = commentDao.getCommentById(id);
    if (comment == null) {
      CtrlUtil.flashError(flash, "评论不存在");
      return CtrlUtil.redirectTo(redirect);
    }
    commentDao.deleteComment(id);
    blogDao.decBlogComment(comment.getCommBlogId());
    return CtrlUtil.redirectTo(redirect);
  }

  // do post in blog detail page
  @PostMapping
  public String postComment(HttpServletRequest request, HttpServletResponse response, Model model,
                            String commentContent, Long blogId, Long fromUserId, Long toUserId, String redirect) {
    if (toUserId == null) toUserId = 0L;
    Comment comment = new Comment(blogId, fromUserId, toUserId, commentContent, new Date());
    commentDao.saveComment(comment);
    //fixme assert above op is succeed
    blogDao.incBlogComment(blogId);
    return CtrlUtil.redirectTo(redirect);
  }
}
