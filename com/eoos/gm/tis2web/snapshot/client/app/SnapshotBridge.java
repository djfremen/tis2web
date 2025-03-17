package com.eoos.gm.tis2web.snapshot.client.app;

import com.eoos.datatype.gtwo.Pair;

public interface SnapshotBridge {
  boolean createInstance();
  
  boolean discardInstance();
  
  boolean setProperties(Pair[] paramArrayOfPair);
  
  boolean installDTCCallback();
  
  boolean uninstallDTCCallback();
  
  boolean installEmailCallback();
  
  boolean uninstallEmailCallback();
  
  boolean startUI();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\SnapshotBridge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */