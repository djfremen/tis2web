package com.eoos.gm.tis2web.snapshot.client.app;

public interface DTCCallback {
  String getBACCode();
  
  void onReadDTC(byte[] paramArrayOfbyte);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\DTCCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */