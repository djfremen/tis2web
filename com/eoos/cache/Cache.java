package com.eoos.cache;

import java.util.Collection;

public interface Cache {
  Object lookup(Object paramObject);
  
  void store(Object paramObject1, Object paramObject2);
  
  void remove(Object paramObject);
  
  Collection getKeys();
  
  void clear();
  
  public static interface Size {
    int getSize();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\Cache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */