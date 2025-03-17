package com.eoos.log;

public interface ILogger {
  void debug(Object paramObject);
  
  void debug(Object paramObject, Throwable paramThrowable);
  
  void info(Object paramObject);
  
  void info(Object paramObject, Throwable paramThrowable);
  
  void warn(Object paramObject);
  
  void warn(Object paramObject, Throwable paramThrowable);
  
  void error(Object paramObject);
  
  void error(Object paramObject, Throwable paramThrowable);
  
  void fatal(Object paramObject);
  
  void fatal(Object paramObject, Throwable paramThrowable);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\ILogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */