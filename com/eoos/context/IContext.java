package com.eoos.context;

import com.eoos.filter.Filter;
import java.util.Collection;

public interface IContext {
  void storeObject(Object paramObject1, Object paramObject2);
  
  Object getObject(Object paramObject);
  
  Collection getObjects(Filter paramFilter);
  
  void removeObject(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\context\IContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */