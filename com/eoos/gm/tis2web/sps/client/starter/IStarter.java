package com.eoos.gm.tis2web.sps.client.starter;

import com.eoos.gm.tis2web.sps.common.gtwo.export.Installer;
import java.io.File;
import java.util.List;

public interface IStarter {
  boolean isStartAllowed();
  
  List<String> getAvailableCommPorts();
  
  String getTech2WinComPort();
  
  boolean startProcess(String paramString);
  
  boolean setEnvironment();
  
  Integer install(File paramFile, Installer paramInstaller);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\starter\IStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */