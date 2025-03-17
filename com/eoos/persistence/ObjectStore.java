package com.eoos.persistence;

public interface ObjectStore {
  void store(Object paramObject1, Object paramObject2);
  
  Object load(Object paramObject);
  
  void delete(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\ObjectStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */