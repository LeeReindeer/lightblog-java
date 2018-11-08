package moe.leer.lightblogjava.controller;

import moe.leer.lightblogjava.util.$;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leer
 * Created at 11/8/18 7:27 PM
 */
@Controller
public class ErrorController {

  @GetMapping("/error")
  public String error(HttpServletRequest request, HttpServletResponse response,
                      Model model, @RequestParam("msg") String msg) {
    if ($.StringNotNullAndEmpty(msg)) {
      model.addAttribute("msg", msg);
    }
    return "error";
  }

}
