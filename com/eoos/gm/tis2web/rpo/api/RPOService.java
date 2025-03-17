package com.eoos.gm.tis2web.rpo.api;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface RPOService extends Service, VisualModule, RPORetrieval {
  CharSequence getRPOXML(String paramString, Locale paramLocale);
  
  Map<String, RPOFamily> resolveFamilies(Collection<String> paramCollection);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\api\RPOService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */