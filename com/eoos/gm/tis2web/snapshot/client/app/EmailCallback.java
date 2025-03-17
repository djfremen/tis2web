package com.eoos.gm.tis2web.snapshot.client.app;

public interface EmailCallback {
  void onSetRecipient(String paramString);
  
  void onSetFrom(String paramString);
  
  void onSetSubject(String paramString);
  
  void onSetContent(String paramString);
  
  void onSetAttachment(String paramString, byte[] paramArrayOfbyte);
  
  boolean onSendEmail();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\EmailCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */