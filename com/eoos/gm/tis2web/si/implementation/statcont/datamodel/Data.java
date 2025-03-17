package com.eoos.gm.tis2web.si.implementation.statcont.datamodel;

public interface Data {
  boolean isTraceableDocument();
  
  byte[] toByteArray();
  
  String getFilename();
  
  String getPath();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */