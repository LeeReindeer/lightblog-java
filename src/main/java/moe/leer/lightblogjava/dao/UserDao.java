package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author leer
 * Created at 10/23/18 3:11 PM
 */
@Component
public interface UserDao {
  User getUserByName(String name);

  User getUserProfile(Long uid);

  User getUserById(Long id);

  String getPasswordHashByName(String name);

  void saveUser(User uesr);

  // is fromId followed toId
  Long isFollowed(@Param("fromId") Long fromId, @Param("toId") Long toId);

  //  use @Param for multi params
  void followUser(@Param("fromId") Long fromId, @Param("toId") Long toId);

  void unFollowUser(@Param("fromId") Long fromId, @Param("toId") Long toId);

}
