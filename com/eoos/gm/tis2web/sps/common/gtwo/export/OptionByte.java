package com.eoos.gm.tis2web.sps.common.gtwo.export;

public interface OptionByte {
  public static final int ADAPTIVE_BYTE = 0;
  
  public static final int OPTION_BYTE = 1;
  
  int getDeviceID();
  
  int getType();
  
  int getBlockNum();
  
  int getByteNum();
  
  int getData();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\OptionByte.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */