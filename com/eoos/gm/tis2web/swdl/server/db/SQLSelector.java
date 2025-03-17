package com.eoos.gm.tis2web.swdl.server.db;

public interface SQLSelector {
  public static final int SELECT_APPLICATIONS = 1;
  
  public static final int SELECT_VERSIONS = 2;
  
  public static final int SELECT_FILES_INFO = 3;
  
  public static final int SELECT_FILE = 4;
  
  String getSQL(int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\SQLSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */