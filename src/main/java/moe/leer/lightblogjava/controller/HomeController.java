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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @GetMapping("/")
  public String timeline(HttpServletRequest request, HttpServletResponse response,
                         Model model) {
    User user = getCurrentUser();
    int currentPage = pagingService.paging(request, model, new PagingService.Callback() {
      @Override
      public long callback(Object[] objects) {
        return blogDao.getTimelineCnt(user.getUserId());
      }
    });

    List<LightBlog> blogList = blogDao.getTimelineByUIDWithPaging(user.getUserId(), currentPage);
    model.addAttribute("user", user);
    model.addAttribute("blogs", blogList);
    model.addAttribute("redirect", CtrlUtil.getCurrentURL(request));
    return App.TEMPLATE_HOME;
  }

  @PostMapping("/")
  public String newLight(HttpServletRequest request, HttpServletResponse response,
                         Model model, String content, RedirectAttributes flash) {
    if ($.StringNullOrEmpty(content)) {
      return CtrlUtil.redirectTo("/");
    }
    User user = getCurrentUser();
    LightBlog tmpBlog = null;

    try {
      tmpBlog = $.getBlogTag(content);
    } catch ($.Tag2LongException e) {
      CtrlUtil.flashError(flash, "你的标签超过25个字符了");
      return CtrlUtil.redirectTo("/");
    }

    Blog blog = new Blog(user.getUserId(), tmpBlog.blog.blogContent, new Date());
    // todo support emoji
    try {
      if ($.StringNotNullAndEmpty(tmpBlog.tagName)) {
        logger.info("tag: {}", tmpBlog.tagName);
        logger.info("tag size: {}", tmpBlog.tagName.length());
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
    } catch (Exception e) {
      logger.error("occur unsupported string");
      CtrlUtil.flashError(flash, "未发布成功：不支持的文本类型");
      return CtrlUtil.redirectTo("/");
    }
    return CtrlUtil.redirectTo("/");
  }
}
