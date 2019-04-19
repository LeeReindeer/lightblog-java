package moe.leer.lightblogjava.config;

import moe.leer.lightblogjava.service.UserDetailService;
import moe.leer.lightblogjava.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author leer
 * Created at 4/19/19 6:54 PM
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailService userDetailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/logout").permitAll()
        .antMatchers("/error").permitAll()
        .antMatchers("/**").hasRole("USER") // it should not start with "ROLE_" as this is automatically inserted.
        .and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/")
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
        .and()
        .csrf().disable(); //disable csrf
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder);
  }

  @Bean
  public CustomPasswordEncoder passwordEncoder() {
    return new CustomPasswordEncoder();
  }

}
