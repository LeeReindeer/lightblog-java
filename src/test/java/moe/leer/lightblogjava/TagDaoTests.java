package moe.leer.lightblogjava;

import moe.leer.lightblogjava.dao.TagDao;
import moe.leer.lightblogjava.modle.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author leer
 * Created at 10/23/18 1:43 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagDaoTests {
  @Autowired
  private TagDao tagDao;

  @Test
  public void testGetTagById() {
    Tag tag = tagDao.getTagById(1L);
    System.out.println(tag);
  }

  @Test
  public void testSaveTag() {
    Tag tag = new Tag("testMybatis1", new Date());
    tagDao.saveTag(tag);
  }

}
