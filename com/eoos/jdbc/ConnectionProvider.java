package com.eoos.jdbc;

import java.sql.Connection;

public interface ConnectionProvider {
  Connection getConnection();
  
  void releaseConnection(Connection paramConnection);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\ConnectionProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */