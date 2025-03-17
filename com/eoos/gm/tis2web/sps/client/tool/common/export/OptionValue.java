package com.eoos.gm.tis2web.sps.client.tool.common.export;

public interface OptionValue extends ToolValue {
  public static final String RESOURCEPREFIX2 = ".optionvalue.";
  
  String getKey();
  
  void setKey(String paramString);
  
  String getPropertyKey();
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\OptionValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */