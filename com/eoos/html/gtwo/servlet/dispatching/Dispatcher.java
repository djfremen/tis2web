package com.eoos.html.gtwo.servlet.dispatching;

import com.eoos.html.gtwo.servlet.RequestHandler;

public interface Dispatcher extends RequestHandler {
  void registerHandler(RequestHandler paramRequestHandler);
  
  void unregisterHandler(RequestHandler paramRequestHandler);
  
  String getDispatchPath(RequestHandler paramRequestHandler);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\servlet\dispatching\Dispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */