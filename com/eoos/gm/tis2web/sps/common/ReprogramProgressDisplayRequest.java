package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ConfirmationRequest;

public interface ReprogramProgressDisplayRequest extends ConfirmationRequest {
  void addObserver(Observer paramObserver);
  
  void removeObserver(Observer paramObserver);
  
  public static interface Observer {
    void onStart(long param1Long);
    
    void onProgress(long param1Long);
    
    void onStatusChange(String param1String);
    
    void onFinished();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\ReprogramProgressDisplayRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */