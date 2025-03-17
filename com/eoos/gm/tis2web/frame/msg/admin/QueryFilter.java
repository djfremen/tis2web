package com.eoos.gm.tis2web.frame.msg.admin;

public interface QueryFilter {
  public static final String WILDCARD_ANY = "*";
  
  public static final String WILDCARD_ONE = "?";
  
  String getIDPattern();
  
  IMessage.Status getStatus();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\QueryFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */