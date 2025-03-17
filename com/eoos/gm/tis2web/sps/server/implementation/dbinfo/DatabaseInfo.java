package com.eoos.gm.tis2web.sps.server.implementation.dbinfo;

import java.util.List;

public interface DatabaseInfo {
  String getTitle();
  
  String getVIN();
  
  List getTables();
  
  public static interface Table {
    String getTitle();
    
    int getRowCount();
    
    int getColumnCount();
    
    Object getContent(int param1Int1, int param1Int2);
    
    boolean isHeader(int param1Int1, int param1Int2);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\dbinfo\DatabaseInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */