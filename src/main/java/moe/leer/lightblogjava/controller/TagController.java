package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.App;
import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.TagDao;
import moe.leer.lightblogjava.model.Tag;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leer
 * Created at 10/26/18 7:32 PM
 */
@Controller
public class TagController extends BaseController {

  @Autowired
  private TagDao tagDao;

  @Autowired
  private BlogDaoWrapper blogDao;

  @GetMapping("/tag/{tagId}")
  public String taggedBlog(HttpServletRequest request, HttpServletResponse response,
                           Model model, @PathVariable("tagId") Long tagId, RedirectAttributes flash) {
    User user = getCurrentUser();
    Tag tag = tagDao.getTagById(tagId);
    if (tag == null) {
      CtrlUtil.flashError(flash, "标签不存在");
      return CtrlUtil.redirectTo("/");
    }
    model.addAttribute("user", user);
    model.addAttribute("redirect", CtrlUtil.getCurrentURL(request));
    model.addAttribute("tag", tag);
    model.addAttribute("blogs", blogDao.getBlogsWithTag(tagId));
    return App.TEMPLATE_TAG;
  }
}
