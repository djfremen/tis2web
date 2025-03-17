package com.eoos.gm.tis2web.frame.dwnld.client.api;

import com.eoos.gm.tis2web.frame.dwnld.common.ExecutionMode;
import java.io.Serializable;

public interface IDownloadFile extends Serializable {
  long getSize();
  
  String getName();
  
  String getPath();
  
  public static interface IExecutable {
    ExecutionMode getExecutionMode();
    
    String getType();
    
    String getCmdLineParams();
    
    String getSuccessCodes();
    
    String getFailureCodes();
    
    String getExecutablePath();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IDownloadFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */