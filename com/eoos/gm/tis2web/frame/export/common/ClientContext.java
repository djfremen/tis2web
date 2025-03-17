package com.eoos.gm.tis2web.frame.export.common;

import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
import com.eoos.html.Dispatchable;
import com.eoos.html.base.ApplicationContext;
import com.eoos.html.base.ClientContextBase;
import java.util.Date;
import java.util.Locale;

public interface ClientContext extends ClientContextBase {
  ApplicationContext getApplicationContext();
  
  Locale getLocale();
  
  void setLocale(Locale paramLocale);
  
  String getSessionID();
  
  void keepAlive();
  
  SharedContext getSharedContext();
  
  String getRequestURL();
  
  String toString();
  
  void registerDispatchable(Dispatchable paramDispatchable);
  
  void clearAllDispatchables();
  
  String formatDate(Date paramDate);
  
  boolean isPublicAccess();
  
  boolean isActive();
  
  long getLastAccess();
  
  boolean isSpecialAccess();
  
  boolean offerSpecialAccess();
  
  String getUserGroup();
  
  public static interface Persistent {}
  
  public static interface Logout {
    void logout();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClientContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */