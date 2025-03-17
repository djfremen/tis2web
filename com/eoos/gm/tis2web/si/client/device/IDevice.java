package com.eoos.gm.tis2web.si.client.device;

public interface IDevice {
  String initialize(String paramString);
  
  boolean uploadData(byte[] paramArrayOfbyte, short paramShort);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\device\IDevice.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */