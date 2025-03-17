package com.eoos.gm.tis2web.sps.common;

import com.eoos.datatype.gtwo.Pair;
import java.util.List;

public interface VIT {
  public static final String VIT_TYPE = "vit_type";
  
  public static final String SPS_ID = "sps_id";
  
  public static final String CHKSUM = "chksum";
  
  public static final String TABLE_LEN = "table_len";
  
  public static final String PROTOCOL = "protocol";
  
  public static final String VIN = "vin";
  
  public static final String VMECUHN = "vmecuhn";
  
  public static final String SSECUSVN = "ssecusvn";
  
  public static final String SSECUHN = "ssecuhn";
  
  public static final String SNOET = "snoet";
  
  public static final String DIAGDATA_ID = "diagdata_id";
  
  public static final String NUMSEEDS = "numseeds";
  
  public static final String SEED = "seed";
  
  public static final String NUM_PART = "num_part";
  
  public static final String PARTNO = "partno";
  
  public static final String READCVN = "cvn_in_vit1";
  
  public static final String PARTNUM = "partnum";
  
  public static final String SUB_ASSEMBLY = "sub_asm";
  
  public static final String NAV_INFO = "nav_info";
  
  public static final String RESERVED = "reserved";
  
  public static final String SHOPCODE = "shopcode";
  
  public static final String PROGDATE = "progdate";
  
  public static final String NUMCMS = "numcms";
  
  public static final String BLOCKLEN = "blocklen";
  
  public static final String DISP_TYPE = "disp_type";
  
  public static final String ECU_ADR = "ecu_adr";
  
  public static final String SWCOMPAT_ID = "swcompat_id";
  
  public static final String PINNUM = "pinnum";
  
  public static final String CONFIG_AREA_SIZE = "config_area_size";
  
  public static final String DEVICE_TYPE = "devicetype";
  
  public static final String SPS_MODE = "spsmode";
  
  public static final String POST_PROG_INSTRUCTIONS = "post_prog_instructions";
  
  public static final String CONF_BYTE = "conf_byte";
  
  public static final String OPTION = "option";
  
  public static final String INT_VER = "int_ver";
  
  public static final String VIT_NAME = "vit_name";
  
  public static final String VIT_ID = "vit_id";
  
  public static final String ECU_CONFIG_DATA_LENGTH = "ecu_config_data_length";
  
  public static final String TYPE = "type";
  
  public static final String BLOCKNUM = "blocknum";
  
  public static final String BYTENUM = "bytenum";
  
  public static final String BYTEDATA = "bytedata";
  
  public static final String ODOMETER = "odometer";
  
  public static final String KEYCODE = "keycode";
  
  public static final String CONTROLLER_SUPPORTED = "controller_supported";
  
  void setAttributes(List paramList);
  
  void addAttributes(List paramList);
  
  void addAttribute(Pair paramPair);
  
  void setAttribute(Pair paramPair);
  
  Pair getAttribute(String paramString);
  
  String getAttrValue(String paramString);
  
  List getAttrValues(String paramString);
  
  List getAttributes(String paramString);
  
  List getAttributes();
  
  boolean isEmpty();
  
  String getAttrValue(String paramString, int paramInt);
  
  List getValues4Value(String paramString1, String paramString2);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\VIT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */