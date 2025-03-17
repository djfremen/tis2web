package com.eoos.html.gtwo.util;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface ParameterMap extends Map {
  HttpSession getSession();
  
  HttpServletRequest getRequest();
  
  HttpServletResponse getResponse();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\ParameterMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */