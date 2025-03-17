package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.util.List;

public interface SPSControllerSequence extends Value {
  Integer getSequenceID();
  
  String getDisplay();
  
  Integer getDisplayOrder();
  
  boolean supportsVCI();
  
  List getFunctions();
  
  boolean qualifySequence(int paramInt) throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSControllerSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */