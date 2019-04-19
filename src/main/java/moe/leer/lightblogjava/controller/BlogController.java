package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.App;
import moe.leer.lightblogjava.base.BaseController;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import moe.leer.lightblogjava.dao.CommentDao;
import moe.leer.lightblogjava.model.Comment;
import moe.leer.lightblogjava.model.LightBlog;
import moe.leer.lightblogjava.model.User;
import moe.leer.lightblogjava.util.$;
import moe.leer.lightblogjava.util.CtrlUtil;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

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
    if (blogId == null || blogId == 0) return CtrlUtil.redirectTo("/");

    LightBlog lightBlog = blogDao.getBlogDetail(blogId);
    List<Comment> comments = commentDao.getAllByBlogId(blogId);
    if (lightBlog.tagName != null && lightBlog.tagName.toLowerCase().equals("java")) {
      String compileResult = getCompileResult(lightBlog.blog.blogContent);
      logger.warn("compile result: {}", compileResult);
      model.addAttribute("compile", compileResult);
    }
    User user = getCurrentUser();
    model.addAttribute("user", user);
    model.addAttribute("blog", lightBlog);
    model.addAttribute("comments", comments);
    model.addAttribute("redirect", request.getRequestURL().toString());
    return App.TEMPLATE_DETAIL;
  }

  private String getCompileResult(String code) {
    Supplier<String> supplier = null;
    long id = System.currentTimeMillis();
    Scanner scanner = new Scanner(code);
    StringBuilder sb = new StringBuilder();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      // deny system ops
      if (line.contains("System.")) {
        return "Permission denied at \"" + line + "\"";
      }
      // deny reflection
      if (line.contains("Class") || line.contains("Method") ||
          line.matches(".*\\.invoke(.*)")) {
        return "Don not do reflect at \"" + line + "\"";
      }
      if (line.matches(" *(printf|print|println)\\(.*\\);?")) {
        line = "System.out." + line;
      }
      if (!line.endsWith("{") && !line.endsWith("}")) {
        line += ";";
      }
      sb.append(line).append("\n");
    }
    logger.warn("compiling: {}", sb.toString());
    try {
      supplier = Reflect.compile(
          "moe.leer.lightblogjava.CompileTest" + id,
          "package moe.leer.lightblogjava;\n" +
              "import java.util.*;\n" +
              "import java.io.*;\n" +
              "class CompileTest" + id + "\n" +
              "implements java.util.function.Supplier<String> {\n" +
              "  public String get() {\n" +
              "     PrintStream stdout = System.out;\n" +
              "     ByteArrayOutputStream baos = new ByteArrayOutputStream();\n" +
              "     System.setOut(new PrintStream(baos));\n" +
              sb.toString() +
              "     System.setOut(stdout);\n" +
              "     return baos.toString();\n" +
              "  }\n" +
              "}\n"
      ).create().get();
    } catch (Exception e) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(e.getMessage()).append("\n");
//      ByteArrayOutputStream baos = new ByteArrayOutputStream();
//      PrintStream printStream = new PrintStream(baos);
//      e.printStackTrace(printStream);
//      stringBuilder.append(baos.toString());
//      printStream.close();
      return stringBuilder.toString();
    }
    return supplier.get();
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
    Long userId = getCurrentUser().getUserId();
    blogDao.toggleLikeBlog(blogId, userId);
    return CtrlUtil.redirectTo(redirectURL);
  }

  @GetMapping("/dislike/{id}")
  public String dislikeBlog(HttpServletRequest request, HttpServletResponse response,
                            Model model, @PathVariable("id") Long blogId,
                            @RequestParam("redirect") String redirectURL) {
    Long userId = getCurrentUser().getUserId();
    blogDao.toggleDislikeBlog(blogId, userId);
    return CtrlUtil.redirectTo(redirectURL);
  }

  @GetMapping("/edit/{id}")
  public String getEditBlog(HttpServletRequest request, HttpServletResponse response,
                            Model model, @PathVariable("id") Long blogId) {
    logger.warn("edit blog");
    User user = getCurrentUser();
    model.addAttribute("user", user);
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
      // do not change blog create time
      blogDao.updateBlog(blogId, content, prevBlog.blog.blogTime);
    } catch (Exception e) {
      logger.error("occur unsupported string");
      return CtrlUtil.redirectTo(redirect);
    }
    return CtrlUtil.redirectTo(redirect);
  }
}
