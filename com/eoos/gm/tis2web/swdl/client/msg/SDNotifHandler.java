package com.eoos.gm.tis2web.swdl.client.msg;

public interface SDNotifHandler {
  void OnBuildAppFile();
  
  void OnStartDownload(int paramInt);
  
  void OnEndDownload();
  
  void OnDataTransmitted(int paramInt);
  
  void OnDownAborted(int paramInt);
  
  void OnSeeking();
  
  boolean OnTechReset();
  
  void PDTBIOSMessage();
  
  void OnEndErase();
  
  void OnStartErase(int paramInt);
  
  void OnEraseProgress(int paramInt);
  
  void OnDirRelearn();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\msg\SDNotifHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */