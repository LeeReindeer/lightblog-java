# lightblog.sql
# lightblog 数据库表设计
# author: LeeReindeer
# IMPORT: mysql -uroot -p < lightblog.sql
CREATE DATABASE IF NOT EXISTS lightblog;
USE lightblog;

# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  user_id INT AUTO_INCREMENT PRIMARY KEY ,
  user_name VARCHAR(20) UNIQUE, #用户名
  user_avatar VARCHAR(100), #用户头像链接
  user_password VARCHAR(40), #密码，服务器之存储密码的 hash
  user_register DATETIME, #用户注册时间
  user_bio VARCHAR(140), #用户简介
  user_followers INT, # 粉丝数量
  user_following INT #关注数量
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# 轻博客表
DROP TABLE IF EXISTS blog;
CREATE TABLE blog (
  blog_id INT AUTO_INCREMENT PRIMARY KEY,
  blog_uid INT NOT NULL, # 作者 id
  blog_tag_id INT, # 博客标签，一篇博客只有一个标签
  blog_content VARCHAR(1000), # lightblog 字数限制 1k
  blog_time DATETIME, #微博发布时间

  blog_like INT, #喜欢数量
  blog_unlike INT, #unlike 数量
  blog_comment INT, #评论数量

  FOREIGN KEY (blog_uid)
    REFERENCES user(user_id)
    ON DELETE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# 点赞/反对 用户表
DROP TABLE IF EXISTS blog_counter;
CREATE TABLE blog_counter (
  blog_id INT,
  user_id INT,
  count_type TINYINT, #like 0 , unlike 1

  primary key (blog_id, user_id, count_type), #一个用户只能点赞一次

  FOREIGN KEY (blog_id)
    REFERENCES blog(blog_id)
    ON DELETE CASCADE,
  FOREIGN KEY (user_id)
    REFERENCES user(user_id)
    ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# 评论表
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
  comm_id INT AUTO_INCREMENT PRIMARY KEY,
  comm_blog_id INT NOT NULL, #评论附属的博客id
  comm_from_uid INT NOT NULL, #发表评论的用户 id
  comm_to_uid INT, #回复的目标用户，当回复博客时为0

  comm_content VARCHAR(141), #评论内容
  comm_time DATETIME, #评论时间
  comm_like INT, #评论点赞数量。方便起见，评论点赞不再记录点赞的用户，因此可重复点赞

  # 冗余信息，方便单表查询
  comm_from_name VARCHAR(20),
  comm_from_avatar VARCHAR(100),
  comm_to_name VARCHAR(20),
  comm_to_avatar VARCHAR(100),

  FOREIGN KEY (comm_blog_id)
    REFERENCES blog(blog_id)
    ON DELETE CASCADE, #博客删除的同时评论也删除
  FOREIGN KEY (comm_from_uid)
    REFERENCES user(user_id)
    ON DELETE NO ACTION #保留注销用户的评论，显示帐号为已注销
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# 关注关系表
DROP TABLE IF EXISTS fan_follow;
CREATE TABLE fan_follow (
  user_from INT,
  user_to INT,

  FOREIGN KEY (user_from)
    REFERENCES user(user_id)
    ON DELETE CASCADE, # delete followers and followings when you delete account
  FOREIGN KEY (user_to)
    REFERENCES user(user_id)
    ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# 标签表
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  tag_id INT AUTO_INCREMENT PRIMARY KEY,
  tag_name VARCHAR(25) UNIQUE NOT NULL, #标签名称，限制10个字符
  tag_time DATETIME #标签创建的时间
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# blog 详情 视图
CREATE VIEW blogdetail AS
SELECT blog_id, blog_uid, blog_tag_id, blog_content, blog_time, blog_like, blog_unlike, blog_comment,
  user_name AS blog_user_name, user_avatar AS blog_user_avatar
FROM blog, user
WHERE blog.blog_uid = user.user_id
