package com.eoos.gm.tis2web.frame.export.declaration.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface ResourceService extends Service {
  boolean existsResource(String paramString);
  
  InputStream getResource(String paramString) throws IOException;
  
  Collection searchResources(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\ResourceService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */