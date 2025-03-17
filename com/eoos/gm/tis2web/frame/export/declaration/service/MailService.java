package com.eoos.gm.tis2web.frame.export.declaration.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import java.util.Collection;
import javax.mail.MessagingException;

public interface MailService extends Service {
  void send(Callback paramCallback) throws MessagingException;
  
  void send(String paramString1, Collection paramCollection1, Collection paramCollection2, String paramString2, String paramString3, Collection paramCollection3) throws MessagingException;
  
  public static interface Callback extends com.eoos.mail.MailService.MailDataCallback {}
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\MailService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */