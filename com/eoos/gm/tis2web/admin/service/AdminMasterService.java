package com.eoos.gm.tis2web.admin.service;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;

public interface AdminMasterService extends VisualModule, Service {
  boolean isAvailable(ClientContext paramClientContext);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\service\AdminMasterService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */