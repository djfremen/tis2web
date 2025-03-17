package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml;

import java.util.List;

public interface PositionType {
  String getComment();
  
  void setComment(String paramString);
  
  String getNr();
  
  void setNr(String paramString);
  
  List getFootnote();
  
  StdFootnoteType getStdFootnote();
  
  void setStdFootnote(StdFootnoteType paramStdFootnoteType);
  
  String getText();
  
  void setText(String paramString);
  
  List getParameter();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\PositionType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */