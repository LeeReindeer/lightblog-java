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
  //TODO MORE

  public static void main(String[] args) {
//    System.out.println(CipherUtil.getPasswordHash("0915"));
//    System.out.println(CipherUtil.getPasswordHash("0915"));

    SpringApplication.run(App.class, args);
  }
}
