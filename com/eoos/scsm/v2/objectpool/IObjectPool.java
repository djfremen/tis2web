package com.eoos.scsm.v2.objectpool;

public interface IObjectPool {
  Object get();
  
  void free(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\IObjectPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */