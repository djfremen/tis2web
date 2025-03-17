package com.eoos.html.base;

import com.eoos.html.Dispatchable;
import java.util.Locale;

public interface ClientContextBase {
  void addLogoutListener(LogoutListener paramLogoutListener);
  
  void removeLogoutListener(LogoutListener paramLogoutListener);
  
  void setRequestURL(String paramString);
  
  Object getObject(Object paramObject);
  
  void storeObject(Object paramObject1, Object paramObject2);
  
  String getLabel(String paramString);
  
  String getMessage(String paramString);
  
  Locale getLocale();
  
  void registerDispatchable(Dispatchable paramDispatchable);
  
  void unregisterDispatchable(Dispatchable paramDispatchable);
  
  void clearAllDispatchables();
  
  String createID();
  
  String constructDispatchURL(Dispatchable paramDispatchable, String paramString);
  
  String constructURL(String paramString);
  
  Object getLockObject();
  
  public static interface LogoutListener {
    void onLogout();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\base\ClientContextBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */