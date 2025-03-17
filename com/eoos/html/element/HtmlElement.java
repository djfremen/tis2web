package com.eoos.html.element;

import com.eoos.html.HtmlCodeRenderer;

public interface HtmlElement extends HtmlCodeRenderer {
  void setContainer(HtmlElementContainer paramHtmlElementContainer);
  
  HtmlElementContainer getContainer();
  
  HtmlLayout getLayout();
  
  String getBookmark();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */