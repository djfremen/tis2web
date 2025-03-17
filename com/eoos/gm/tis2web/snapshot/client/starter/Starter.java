package com.eoos.gm.tis2web.snapshot.client.starter;

import java.util.List;

public interface Starter {
  boolean isStartAllowed();
  
  List<String> getAvailableCommPorts();
  
  boolean startProcess(String paramString);
  
  boolean setEnvironment();
  
  String getTech2WinComPort();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\starter\Starter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */