package com.eoos.scsm.v2.io.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface ResourceProvider {
  Collection getResources();
  
  InputStream getInputStream(Object paramObject) throws IOException;
  
  String getName(Object paramObject);
  
  public static interface Search {
    Collection searchResources(Filter param1Filter);
    
    public static interface Filter {}
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\resource\ResourceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */