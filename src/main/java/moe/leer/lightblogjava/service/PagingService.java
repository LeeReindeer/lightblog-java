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

  public static final int PAGE_SIZE = 20;

  public interface Callback<ARG> {
    long callback(ARG... args);
  }

  @SuppressWarnings({"unchecked", "ConfusingArgumentToVarargsMethod"})
  public int paging(HttpServletRequest request, Model model, Callback callback) {
    logger.info("page={}", request.getQueryString());
    int currentPage = 1;
    if (request.getQueryString() != null && request.getQueryString().startsWith("page=")) {
      currentPage = Integer.parseInt(request.getQueryString().substring(5));
    }
    long cnt = callback.callback(null);
    long pages = cnt / PAGE_SIZE;
    if (cnt % PAGE_SIZE != 0) pages += 1;
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
