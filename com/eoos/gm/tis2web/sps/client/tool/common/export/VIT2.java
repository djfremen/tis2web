package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.common.VIT;

public interface VIT2 extends VIT {
  public static final String POST_PROG_INSTRUCTIONS = "post_prog_instructions";
  
  public static final String KEY_PAIR = "key_pair";
  
  public static final String NUM_MOD = "num_mod";
  
  public static final String MOD_PARTNO = "mod_partno";
  
  public static final String MOD_ID = "mod_id";
  
  public static final String MOD_CARD = "mod_card";
  
  public static final String MOD_PAGE = "mod_page";
  
  public static final String MOD_ADR = "mod_adr";
  
  public static final String MOD_LEN = "mod_len";
  
  public static final String EVENT_TYPE = "event_type";
  
  public static final String SECURITY_CODE = "SecCodeVeh";
  
  public static final String CONTROLLER_SECURITY_CODE = "SecCodeCtrl";
  
  Object[] getVIT2Array();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\VIT2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */