package com.eoos.html.gtwo.servlet;

import javax.servlet.http.HttpServlet;

public interface ApplicationConnector extends RequestHandler {
  void onStartup(HttpServlet paramHttpServlet);
  
  void onShutdown(HttpServlet paramHttpServlet);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\servlet\ApplicationConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */