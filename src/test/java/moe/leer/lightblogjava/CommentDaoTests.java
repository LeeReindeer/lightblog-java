package moe.leer.lightblogjava;

import moe.leer.lightblogjava.dao.CommentDao;
import moe.leer.lightblogjava.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author leer
 * Created at 10/23/18 4:48 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTests {

  @Autowired
  private CommentDao commentDao;

  @Test
  public void testSaveComment() {
    commentDao.saveComment(new Comment(9L, 1L, 0L, "testMyBatis", new Date()));
  }

}
