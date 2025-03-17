package com.eoos.gm.tis2web.si.service.cai;

public interface SIOBlob {
  Object getProperty(BLOBProperty paramBLOBProperty);
  
  void setProperty(BLOBProperty paramBLOBProperty, Object paramObject);
  
  String getCharset();
  
  String getMime();
  
  byte[] getData();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */