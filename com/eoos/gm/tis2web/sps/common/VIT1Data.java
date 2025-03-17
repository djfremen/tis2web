package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface VIT1Data extends Value, Serializable {
  public static final String Opel_Part_Number = "vmecuhn";
  
  public static final String Software_Version_Number = "ssecusvn";
  
  public static final String Broad_Cast_Code = "snoet";
  
  public static final String DDI = "ddi";
  
  public static final String Calibration = "partno";
  
  public static final String Supplier_HW_Number = "ssecuhn";
  
  public static final String SW_Compatibility_Ident = "swcompat_id";
  
  public static final String Seed = "seed";
  
  public static final String NavigationInfo = "nav_info";
  
  public static final String DeviceType = "devicetype";
  
  public static final String SPSMode = "spsmode";
  
  List getDeviceList();
  
  Map getOptionBytes();
  
  Map getAdaptiveBytes();
  
  String getKeycode();
  
  String getOdometer();
  
  boolean isValid();
  
  String getID();
  
  String getCompatibilityNr();
  
  int getProtocol();
  
  int getECUAdress();
  
  String getOPNumber();
  
  String getHWNumber();
  
  String getSNOET();
  
  List getTokenizedSNOET();
  
  String getSWVersion();
  
  List getParts();
  
  List getParts(int paramInt);
  
  boolean getReadCVN();
  
  List getPartNumbers();
  
  List getPartNumbers(int paramInt);
  
  List getSubAssembly();
  
  List getSubAssembly(int paramInt);
  
  List getSeeds();
  
  List getOptions();
  
  int getECU();
  
  String getDeviceType();
  
  String getSPSMode();
  
  String getNavigationInfo();
  
  int getSeedCount();
  
  void merge(VIT1Data paramVIT1Data, List paramList);
  
  VIT getVIT();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\VIT1Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */