package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device;

public interface J2534Device {
  public static final String VERSION_0202 = "02.02";
  
  public static final String VERSION_0404 = "04.04";
  
  boolean loadLibrary(String paramString1, String paramString2);
  
  void freeLibrary();
  
  J2534Error passThruOpen();
  
  J2534Error passThruClose();
  
  J2534Error passThruReadVersion(J2534Version paramJ2534Version);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\device\J2534Device.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */