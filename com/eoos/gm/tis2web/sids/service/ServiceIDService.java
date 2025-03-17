package com.eoos.gm.tis2web.sids.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.sids.service.cai.InvalidVinException;
import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
import com.eoos.gm.tis2web.sps.service.cai.RequestException;
import java.util.Locale;

public interface ServiceIDService extends Service {
  ServiceID getServiceID(Locale paramLocale, String paramString, AttributeValueMap paramAttributeValueMap) throws RequestException, InvalidVinException, NoServiceIDException;
  
  ServiceID getServiceID(String paramString);
  
  void reset();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\service\ServiceIDService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */