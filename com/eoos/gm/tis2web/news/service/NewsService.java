package com.eoos.gm.tis2web.news.service;

import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
import com.eoos.gm.tis2web.frame.export.common.service.Module;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
import java.util.Iterator;
import java.util.Map;

public interface NewsService extends Service, Module {
  public static final String PARAMETER_KEY_MODULE_IDENTIFIER = "moduleid";
  
  public static final String PARAMETER_KEY_PAGE_IDENTIFIER = "pageid";
  
  public static final String PARAMETER_KEY_SIT = "sit";
  
  Boolean containsNewItems(String paramString);
  
  Object getUI(String paramString, Map paramMap);
  
  Iterator getInstances();
  
  IOFactory getIOFactory(String paramString);
  
  IDatabaseLink getDatabaseLink(String paramString);
  
  void reset();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\service\NewsService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */