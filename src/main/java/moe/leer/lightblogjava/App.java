package moe.leer.lightblogjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("moe.leer.lightblogjava.dao")
public class App {

  public static final String TEMPLATE_LOGIN = "login";
  public static final String TEMPLATE_HOME = "index";
  public static final String TEMPLATE_PROFILE = "profile";
  public static final String TEMPLATE_DETAIL = "detail";
  public static final String TEMPLATE_EDITBLOG = "edit_blog";
  public static final String TEMPLATE_ERROR = "error";
  public static final String TEMPLATE_TAG = "tag_blog";
  public static final String TEMPLATE_SEARCH = "search";

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
