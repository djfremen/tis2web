package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

import com.eoos.datatype.Denotation;
import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;

public interface SPSControllerFunction extends Denotation, DisplayableValue {
  Integer getFunctionID();
  
  String getControllerDescription();
  
  Integer getRequestInfoID();
  
  String getOnSameSW();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSControllerFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */