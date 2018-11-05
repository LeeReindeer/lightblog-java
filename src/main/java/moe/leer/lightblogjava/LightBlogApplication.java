package moe.leer.lightblogjava;

import moe.leer.lightblogjava.util.CipherUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("moe.leer.lightblogjava.dao")
public class LightBlogApplication {

  public static void main(String[] args) {
//    System.out.println(CipherUtil.getPasswordHash("0915"));
//    System.out.println(CipherUtil.getPasswordHash("0915"));

    SpringApplication.run(LightBlogApplication.class, args);
  }
}
