package com.eoos.jdbc;

import java.sql.PreparedStatement;

public interface IStatementManager {
  PreparedStatement getStatement(String paramString) throws Exception;
  
  void releaseStatement(PreparedStatement paramPreparedStatement);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\IStatementManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */