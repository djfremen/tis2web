package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device;

public interface J2534Error {
  public static final int STATUS_NOERROR = 0;
  
  public static final int ERR_NOT_SUPPORTED = 1;
  
  public static final int ERR_INVALID_CHANNEL_ID = 2;
  
  public static final int ERR_INVALID_PROTOCOL_ID = 3;
  
  public static final int ERR_NULL_PARAMETER = 4;
  
  public static final int ERR_INVALID_IOCTL_VALUE = 5;
  
  public static final int ERR_INVALID_FLAGS = 6;
  
  public static final int ERR_FAILED = 7;
  
  public static final int ERR_DEVICE_NOT_CONNECTED = 8;
  
  public static final int ERR_TIMEOUT = 9;
  
  public static final int ERR_INVALID_MSG = 10;
  
  public static final int ERR_INVALID_TIME_INTERVAL = 11;
  
  public static final int ERR_EXCEEDED_LIMIT = 12;
  
  public static final int ERR_INVALID_MSG_ID = 13;
  
  public static final int ERR_INVALID_ERROR_ID = 14;
  
  public static final int ERR_INVALID_IOCTL_ID = 15;
  
  public static final int ERR_BUFFER_EMPTY = 16;
  
  public static final int ERR_BUFFER_FULL = 17;
  
  public static final int ERR_BUFFER_OVERFLOW = 18;
  
  public static final int ERR_PIN_INVALID = 19;
  
  public static final int ERR_CHANNEL_IN_USE = 20;
  
  public static final int ERR_MSG_PROTOCOL_ID = 21;
  
  public static final int ERR_INVALID_FILTER_ID = 22;
  
  public static final int ERR_NO_FLOW_CONTROL = 23;
  
  public static final int ERR_NOT_UNIQUE = 24;
  
  public static final int ERR_INVALID_BAUDRATE = 25;
  
  public static final int ERR_INVALID_DEVICE_ID = 32;
  
  public static final int ERR_NULLPARAMETER = 4;
  
  int getErrorCode();
  
  String getErrorDescription();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\device\J2534Error.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */