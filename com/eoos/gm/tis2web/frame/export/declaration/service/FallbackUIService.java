package com.eoos.gm.tis2web.frame.export.declaration.service;

import com.eoos.gm.tis2web.frame.export.common.service.Module;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
import java.util.Map;

public interface FallbackUIService extends Service, Module {
  Object getUI(String paramString1, String paramString2, Map paramMap);
  
  Object getUI(String paramString, Map paramMap, Callback paramCallback);
  
  public static interface Callback {
    VCLinkElement.Callback getVCLinkCallback();
    
    String getModuleType();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\FallbackUIService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */