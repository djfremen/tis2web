package com.eoos.gm.tis2web.si.client.starter;

import java.util.List;

public interface IStarter {
  boolean isStartAllowed();
  
  List<String> getAvailableCommPorts();
  
  boolean startProcess(String paramString);
  
  boolean setEnvironment();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\starter\IStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */