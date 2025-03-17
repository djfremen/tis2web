package com.eoos.gm.tis2web.frame.export.declaration.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;

public interface StorageService extends Service {
  void store(CharSequence paramCharSequence, byte[] paramArrayOfbyte) throws Exception;
  
  byte[] load(CharSequence paramCharSequence) throws Exception;
  
  void delete(CharSequence paramCharSequence) throws Exception;
  
  ObjectStore getObjectStoreFacade();
  
  public static interface ObjectStore {
    void store(CharSequence param1CharSequence, Object param1Object) throws Exception;
    
    Object load(CharSequence param1CharSequence) throws Exception;
    
    void delete(CharSequence param1CharSequence) throws Exception;
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\StorageService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */