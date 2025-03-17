package com.eoos.gm.tis2web.sps.client.common;

public interface ReprogramProgress {
  void init(long paramLong);
  
  void progress(long paramLong);
  
  void done();
  
  void onStatusChange(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\ReprogramProgress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */