package com.eoos.html.gtwo.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface RequestHandler {
  void handle(HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\servlet\RequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */