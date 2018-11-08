package moe.leer.lightblogjava.model;

import java.util.Date;
import java.util.List;

/**
 * @author leer
 * Created at 10/22/18 7:56 PM
 */
public class User {
  private Long userId;
  private String userName;
  public String userAvatar;
  private String password;
  public Date userRegister;
  public String userBio;
  public Integer userFollowers;
  public Integer userFollowing;

  // for load profile page
  public List<LightBlog> myBlogs;

  public static final String DEFAULT_AVATAR= "https://avatars2.githubusercontent.com/u/24387694?s=100&v=4";

  public User() {
  }

  public User(String userName, String userAvatar, String password, Date userRegister) {
    this.userName = userName;
    this.userAvatar = userAvatar;
    this.password = password;
    this.userRegister = userRegister;
    this.userBio = "";
    userFollowers = userFollowing = 0;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean setCheckUserName(String userName) {
    //todo check user name
    this.userName = userName;
    return true;
  }

  public String getPassword() {
    return password;
  }

  public boolean setCheckPassword(String password) {
    //todo check password
    this.password = password;
    return true;
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", userName='" + userName + '\'' +
        ", password='" + password + '\'' +
        ", userRegister=" + userRegister +
        ", userBio='" + userBio + '\'' +
        ", userFollowers=" + userFollowers +
        ", userFollowing=" + userFollowing +
        '}';
  }
}
