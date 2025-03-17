package com.eoos.gm.tis2web.frame.dwnld.client.api;

public interface IDownloadStatus {
  long getTransferredByteCount();
  
  long getRemainingTimeEstimate();
  
  int getPercentage();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IDownloadStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */