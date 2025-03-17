package com.eoos.gm.tis2web.frame.dwnld.client.api;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IGDSFacade {
  Collection getAvailableCoreVersions();
  
  IDownloadUnit getNewestCoreVersion();
  
  Collection getAvailableJREVersions();
  
  IDownloadUnit getNewestJREVersion();
  
  Collection getAvailableMDIVersions();
  
  IDownloadUnit getNewestMDIVersion();
  
  Collection getDeliverableDescriptors();
  
  IDownloadPackage createPackage(IDeliverableDescriptor paramIDeliverableDescriptor, File paramFile) throws IOException;
  
  IDownloadPackage createPackage(Collection paramCollection, File paramFile) throws IOException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IGDSFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */