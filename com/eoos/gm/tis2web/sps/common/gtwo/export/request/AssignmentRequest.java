package com.eoos.gm.tis2web.sps.common.gtwo.export.request;

import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.Request;

public interface AssignmentRequest extends Request {
  void setRequestGroup(RequestGroup paramRequestGroup);
  
  RequestGroup getRequestGroup();
  
  boolean autoSubmit();
  
  void setAutoSubmit(boolean paramBoolean);
  
  Attribute getAttribute();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\request\AssignmentRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */