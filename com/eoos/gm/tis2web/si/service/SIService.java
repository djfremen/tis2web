package com.eoos.gm.tis2web.si.service;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
import com.eoos.gm.tis2web.si.service.cai.SI;
import java.net.URL;

public interface SIService extends Service, VisualModule {
  SI getSI(ClientContext paramClientContext);
  
  URL getSearchURL(ClientContext paramClientContext, String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\SIService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */