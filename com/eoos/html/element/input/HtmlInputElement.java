package com.eoos.html.element.input;

import com.eoos.html.element.HtmlElement;
import java.util.Map;

public interface HtmlInputElement extends HtmlElement {
  void setValue(Map paramMap);
  
  void setValue(Object paramObject);
  
  Object getValue();
  
  boolean isDisabled();
  
  void setDisabled(Boolean paramBoolean);
  
  boolean clicked();
  
  Object onClick(Map paramMap);
  
  boolean check(boolean paramBoolean);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\HtmlInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */