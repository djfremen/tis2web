package com.eoos.gm.tis2web.frame.login.dialog;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import com.eoos.html.ResultObject;
import java.util.Map;

public interface LoginDialogAccess {
  boolean isAvailable(ClientContext paramClientContext);
  
  String getDialogCode(Map paramMap, ClientContext paramClientContext, ResultObject paramResultObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\dialog\LoginDialogAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */