package moe.leer.lightblogjava;

import moe.leer.lightblogjava.dao.UserDaoWrapper;
import moe.leer.lightblogjava.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author leer
 * Created at 10/23/18 3:57 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTests {

  @Autowired
  private UserDaoWrapper userDao;

  @Test
  public void testGetUserByName() {
    User user = userDao.getUserByName("LeeR");
    if (user == null) {
      System.out.println("not find");
    } else
      System.out.println(user);
  }

  @Test
  public void testUserProfile() {
    User user = userDao.getUserProfile(1L);
    System.out.println(user.myBlogs);
  }

  @Test
  public void testAvatar() {
    User user = new User("Lee", String.format(User.DEFAULT_AVATAR, "Lee"), "0000", new Date());
    System.out.println(user.userAvatar);
    Assert.assertTrue(user.userAvatar.contains("Lee"));
  }
}
