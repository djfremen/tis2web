package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

public interface SPSVIN {
  boolean validate() throws Exception;
  
  char getPosition(int paramInt);
  
  String getWMI();
  
  char getMake();
  
  char getLine();
  
  char getSeries();
  
  char getEngine();
  
  char getCheckDigit();
  
  char getModelYear();
  
  String getSequence();
  
  int getSequenceNo();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */