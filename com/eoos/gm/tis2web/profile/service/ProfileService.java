package com.eoos.gm.tis2web.profile.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import java.util.Map;

public interface ProfileService extends Service {
  Object getUI(String paramString, Map paramMap, ReturnHandler paramReturnHandler);
  
  public static interface ReturnHandler {
    Object onReturn(Map param1Map);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\service\ProfileService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */