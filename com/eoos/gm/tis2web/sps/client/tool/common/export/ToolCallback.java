package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
import com.eoos.gm.tis2web.sps.common.VIT1;

public interface ToolCallback {
  VIT1 getVIT1(Integer paramInteger);
  
  ISPSTool getSPSTool();
  
  ToolContext getToolContext();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */