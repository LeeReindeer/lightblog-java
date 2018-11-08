package moe.leer.lightblogjava.service;

import moe.leer.lightblogjava.dao.BlogDaoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leer
 * Created at 11/8/18 9:48 PM
 */
@Service
public class PagingService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private BlogDaoWrapper blogDao;

  public int paging(HttpServletRequest request, Model model, Long uid) {
    logger.info("page={}", request.getQueryString());
    int currentPage = 1;
    if (request.getQueryString() != null && request.getQueryString().startsWith("page=")) {
      currentPage = Integer.parseInt(request.getQueryString().substring(5));
    }
    long cnt = blogDao.getTimelineCnt(uid);
    long pages = cnt / 20;
    if (cnt % 20 != 0) pages += 1;
    long[] pagesArray = new long[(int) pages];
    for (int i = 0; i < pages; i++) {
      pagesArray[i] = i + 1;
    }
    model.addAttribute("pages", pagesArray);
    if (currentPage < pages) model.addAttribute("next", currentPage + 1);
    if (currentPage > 1) model.addAttribute("prev", currentPage - 1);
    return currentPage;
  }
}
