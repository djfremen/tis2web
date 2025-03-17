package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
import java.util.List;
import java.util.Locale;

public interface ToolOption extends DisplayableAttribute {
  public static final String RESOURCEPREFIX2 = ".option.";
  
  String getKey();
  
  String getDenotation(Locale paramLocale);
  
  List getValues();
  
  OptionValue getOptionValueByPropertyValue(String paramString);
  
  OptionValue getOptionValue(int paramInt);
  
  int valueIndex(OptionValue paramOptionValue);
  
  void addValue(OptionValue paramOptionValue);
  
  int getDefaultValueIndex();
  
  void setDefaultValueIndex(int paramInt);
  
  String getPropertyKey();
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */