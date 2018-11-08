package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.App;
import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.TagDao;
import moe.leer.lightblogjava.model.Blog;
import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.model.Tag;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.service.PagingService;
import moe.leer.lightblogjava.util.$;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author leer
 * Created at 10/24/18 10:12 AM
 */
@Controller
public class HomeController extends BaseController {

  @Autowired
  private BlogDaoWrapper blogDao;

  @Autowired
  private PagingService pagingService;

  @Autowired
  private TagDao tagDao;

  @GetMapping({"/", "/index"})
  public String timeline(HttpServletRequest request, HttpServletResponse response,
                         Model model) {
    User user = getCurrentUser(request);
    int currentPage = pagingService.paging(request, model, user.getUserId());
    List<LightBlog> blogList = blogDao.getTimelineByUIDWithPaging(user.getUserId(), currentPage);
    model.addAttribute("username", user.getUserName());
    model.addAttribute("blogs", blogList);
    model.addAttribute("redirect", CtrlUtil.getCurrentURL(request));
    return App.TEMPLATE_HOME;
  }

  @PostMapping({"/", "/index"})
  public String newLight(HttpServletRequest request, HttpServletResponse response,
                         Model model, String content) {
    if ($.StringNullOrEmpty(content)) {
      return CtrlUtil.redirectTo("/");
    }
    User user = getCurrentUser(request);
    LightBlog tmpBlog = $.getBlogTag(content);
    Blog blog = new Blog(user.getUserId(), tmpBlog.blog.blogContent, new Date());
    if ($.StringNotNullAndEmpty(tmpBlog.tagName)) {
      // save tag, if exist return exist id else return new id
      Tag existTag = tagDao.getTagByName(tmpBlog.tagName);
      Tag newTag = null;
      if (existTag == null) {
        tagDao.saveTag(newTag = new Tag(tmpBlog.tagName, new Date()));
        blog.setBlogTagId(newTag.getTagId());
        logger.info("save new tag {}", newTag);
      } else {
        blog.setBlogTagId(existTag.getTagId());
        logger.info("use exist tag {}", existTag);
      }
    }
    blogDao.saveBlog(blog);
    return CtrlUtil.redirectTo("/");
  }
}
