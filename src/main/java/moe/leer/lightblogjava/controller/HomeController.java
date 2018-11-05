package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leer
 * Created at 10/24/18 10:12 AM
 */
@Controller
public class HomeController extends BaseController {

  @Autowired
  private BlogDaoWrapper blogDao;

  @GetMapping({"/", "/home", "/index"})
  public String timeline(HttpServletRequest request, HttpServletResponse response,
                         Model model) {
    User user = getCurrentUser(request);
    model.addAttribute("username", user.getUserName());
    model.addAttribute("blogs", blogDao.getTimeline(user.getUserId()));
    model.addAttribute("redirect", "/");
    return "index";
  }
}
