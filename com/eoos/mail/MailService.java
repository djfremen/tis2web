package com.eoos.mail;

import java.util.Collection;
import javax.activation.DataSource;
import javax.mail.MessagingException;

public interface MailService {
  void send(MailDataCallback paramMailDataCallback) throws MessagingException;
  
  void send(String paramString1, Collection paramCollection1, Collection paramCollection2, String paramString2, String paramString3, Collection paramCollection3) throws MessagingException;
  
  public static interface MailDataCallback {
    String getSender();
    
    Collection getReplyTo();
    
    Collection getRecipients();
    
    String getSubject();
    
    String getText();
    
    DataSource[] getAttachments();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\MailService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */