package moe.leer.lightblogjava.service;

import moe.leer.lightblogjava.dao.UserDaoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author leer
 * Created at 4/19/19 7:03 PM
 */
@Service
public class UserDetailService implements UserDetailsService {

  //Must start with "ROLE_" !!!
  public final static String USER_ROLE = "ROLE_USER";

  @Autowired
  private UserDaoWrapper userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    moe.leer.lightblogjava.model.User user = userDao.getUserByName(username);
    if (user == null) throw new UsernameNotFoundException("用户不存在");
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(USER_ROLE));
    return new User(user.getUserName(), user.getPassword(), authorities);
  }
}
