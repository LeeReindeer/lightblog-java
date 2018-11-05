package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.modle.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leer
 * Created at 10/23/18 2:20 PM
 */
@Component
public interface CommentDao {
  List<Comment> getAllByBlogId(Long blogId);

  Comment getCommentById(Long commId);

  // todo return bool?
  void saveComment(Comment comment);

  void deleteComment(Long commId);

  void likeComment(Long commId);

}
