package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.App;
import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.UserDao;
import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.service.PagingService;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author leer
 * Created at 11/10/18 6:09 PM
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {

  @Autowired
  private UserDao userDao;

  @Autowired
  private BlogDaoWrapper blogDao;

  @Autowired
  private PagingService pagingService;

  // search blog and user
  @GetMapping
  public String search(HttpServletRequest request, HttpServletResponse response,
                       Model model, @RequestParam("q") String query) {
    List<User> users = userDao.searchAll(query);
    List<LightBlog> blogs = blogDao.searchAll(query);
    model.addAttribute("users", users);
    model.addAttribute("blogs", blogs);
    model.addAttribute("redirect", CtrlUtil.getCurrentURL(request));

    pagingService.paging(request, model, objects -> blogs.size());

    return App.TEMPLATE_SEARCH;
  }
}
