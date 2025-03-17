package com.eoos.gm.tis2web.feedback.service;

import com.eoos.gm.tis2web.frame.export.common.service.Module;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import java.util.Map;

public interface FeedbackService extends Service, Module {
  Object getUI(String paramString1, String paramString2, Map paramMap1, Map paramMap2);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\service\FeedbackService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */