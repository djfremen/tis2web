package com.eoos.gm.tis2web.admin.service.cai;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.html.element.HtmlElement;
import java.util.Locale;

public interface AdminSubService extends Service {
  boolean isAvailable(ClientContext paramClientContext);
  
  HtmlElement getEmbeddableUI(ClientContext paramClientContext);
  
  CharSequence getName(Locale paramLocale);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\service\cai\AdminSubService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */