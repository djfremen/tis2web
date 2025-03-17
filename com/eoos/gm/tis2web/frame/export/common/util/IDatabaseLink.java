package com.eoos.gm.tis2web.frame.export.common.util;

import java.sql.Connection;

public interface IDatabaseLink {
  public static final int OTHER_DBMS = 0;
  
  public static final int ORACLE_DBMS = 1;
  
  public static final int TRANSBASE_DBMS = 2;
  
  public static final int SQLSERVER_DBMS = 3;
  
  boolean isDataSource();
  
  String getDatabaseLinkDescription();
  
  String getDatabaseLinkInformation();
  
  String getDatabaseLinkInformation(Connection paramConnection);
  
  Connection requestConnection() throws Exception;
  
  Connection requestConnection_ConfigurationService() throws Exception;
  
  void testConnection(Connection paramConnection) throws Exception;
  
  void releaseConnection(Connection paramConnection);
  
  String translate(String paramString);
  
  void handleError(Connection paramConnection, Exception paramException);
  
  void logException(Connection paramConnection, Exception paramException);
  
  int getDBMS();
  
  void close();
  
  void releaseConnection_ConfigurationService(Connection paramConnection);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\IDatabaseLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */