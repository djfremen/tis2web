package com.eoos.scsm.v2.util.progress.v2;

public interface ProgressObserver {
  void onProgress(ProgressInfo paramProgressInfo);
  
  void setCancellationListener(CancellationListener paramCancellationListener);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\progress\v2\ProgressObserver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */