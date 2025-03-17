package com.eoos.scsm.v2.util.progress;

public interface ProgressObserver {
  void onProgress(ProgressInfo paramProgressInfo);
  
  boolean isCanceled();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\progress\ProgressObserver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */