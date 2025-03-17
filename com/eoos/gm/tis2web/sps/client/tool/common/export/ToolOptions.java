package com.eoos.gm.tis2web.sps.client.tool.common.export;

import java.util.List;

public interface ToolOptions {
  String getId();
  
  ToolOption getOptionByPropertyKey(String paramString);
  
  List getOptions();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */