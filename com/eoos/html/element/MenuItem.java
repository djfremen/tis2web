package com.eoos.html.element;

import com.eoos.html.HtmlCodeRenderer;
import java.util.Map;

public interface MenuItem extends HtmlCodeRenderer {
  Object onClick(Map paramMap);
  
  boolean isAvailable(Map paramMap);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\MenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */