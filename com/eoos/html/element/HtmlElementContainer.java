package com.eoos.html.element;

import com.eoos.html.element.input.HtmlInputElement;
import java.util.List;

public interface HtmlElementContainer extends HtmlInputElement {
  void addElement(HtmlElement paramHtmlElement);
  
  void removeElement(HtmlElement paramHtmlElement);
  
  List getElements();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElementContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */