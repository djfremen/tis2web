package com.eoos.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Task extends Serializable {
  Object execute();
  
  public static interface ClusterExecution {}
  
  public static interface LocalExecution {}
  
  public static interface InjectHttpResponse {
    void setHttpServletResponse(HttpServletResponse param1HttpServletResponse);
  }
  
  public static interface InjectHttpRequest {
    void setHttpServletRequest(HttpServletRequest param1HttpServletRequest);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Task.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */