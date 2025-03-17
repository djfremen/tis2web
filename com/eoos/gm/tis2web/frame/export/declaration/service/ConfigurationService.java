package com.eoos.gm.tis2web.frame.export.declaration.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.propcfg.Configuration;
import java.util.Properties;

public interface ConfigurationService extends Service, Configuration, Configuration.Modifyable {
  public static final String KEY_PREFIX_URL_CLUSTER_SERVER = "frame.url.cluster.server.";
  
  public static final String KEY_MODE_DEVELOP = "frame.mode.develop";
  
  public static final String KEY_SESSION_TIMEOUT_PUBLIC_ACCESS = "frame.session.timeout.public.access";
  
  public static final String KEY_SESSION_TIMEOUT = "frame.session.timeout";
  
  public static final String KEY_PREFIX_STARTUP_TASK = "frame.startup.task.";
  
  public static final String PREFIX_LOG4J = "log4j.";
  
  void addObserver(Observer paramObserver);
  
  void removeObserver(Observer paramObserver);
  
  void update();
  
  void setProperties(Properties paramProperties);
  
  void dumpConfiguration();
  
  public static interface Observer {
    void onModification();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\ConfigurationService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */