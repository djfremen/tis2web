package com.eoos.persistence.mixin;

import java.util.Collection;

public interface BulkLoad {
  void load(LoadAdapter paramLoadAdapter);
  
  public static interface LoadAdapter {
    Collection getIdentifiers();
    
    void setObject(Object param1Object1, Object param1Object2);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\mixin\BulkLoad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */