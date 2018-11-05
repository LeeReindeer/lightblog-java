package moe.leer.lightblogjava;

import moe.leer.lightblogjava.modle.LightBlog;
import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.List;

/**
 * @author leer
 * Created at 10/23/18 5:48 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogDaoTests {
  @Autowired
  private BlogDaoWrapper blogDao;

  @Test
  public void testTimeline() {
    List<LightBlog> blogList = blogDao.getTimelineByUIDWithPaging(1L, 1);
    for (LightBlog lightBlog : blogList) {
      System.out.println(lightBlog.blogPreview);
    }
    assertEquals(20, blogList.size());
  }

  @Test
  public void testBlogPreview() {
    List<LightBlog> blogList = blogDao.getTimeline(1L);
    for (LightBlog lightBlog : blogList) {
      System.out.println(lightBlog.blogPreview);
    }
  }
}
