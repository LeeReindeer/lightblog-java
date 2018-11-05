package moe.leer.lightblogjava.dao;

import moe.leer.lightblogjava.modle.Tag;
import org.springframework.stereotype.Component;

/**
 * @author leer
 * Created at 10/23/18 1:33 PM
 */
@Component
public interface TagDao {

  Tag getTagById(Long tagId);

  String getTagName(Long tagId);

  void saveTag(Tag tag);
}
