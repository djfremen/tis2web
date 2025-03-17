package com.eoos.persistence.mixin;

import java.util.Collection;

public interface BulkStore {
  void store(StoreAdapter paramStoreAdapter);
  
  public static interface StoreAdapter {
    Collection getIdentifiers();
    
    Object getObject(Object param1Object);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\mixin\BulkStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */