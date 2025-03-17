package com.eoos.gm.tis2web.snapshot.common.export.email;

import java.util.List;

public interface Email {
  String getContent();
  
  String getFrom();
  
  String getRecipient();
  
  String getSubject();
  
  List getAttachments();
  
  void setContent(String paramString);
  
  void setFrom(String paramString);
  
  void setRecipient(String paramString);
  
  void setSubject(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\common\export\email\Email.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */