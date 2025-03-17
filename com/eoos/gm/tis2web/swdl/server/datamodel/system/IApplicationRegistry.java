package com.eoos.gm.tis2web.swdl.server.datamodel.system;

import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
import java.util.Set;

public interface IApplicationRegistry {
  DatabaseAdapter getDatabaseAdapter(Device paramDevice, String paramString);
  
  Set getApplications();
  
  Set getApplications(Device paramDevice);
  
  Version getNewestVersion(Application paramApplication);
  
  Version getNewestVersion(Device paramDevice, String paramString1, String paramString2, String paramString3);
  
  Version getNewestVersion(Device paramDevice, String paramString1, String paramString2);
  
  Version getVersion(Device paramDevice, String paramString1, String paramString2, String paramString3);
  
  public static final class ReInitializationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\IApplicationRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */