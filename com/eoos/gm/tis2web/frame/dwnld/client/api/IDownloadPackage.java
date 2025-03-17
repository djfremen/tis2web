package com.eoos.gm.tis2web.frame.dwnld.client.api;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

public interface IDownloadPackage extends Serializable {
  Identifier getIdentifier();
  
  Collection getUnits();
  
  long getTotalBytes();
  
  File getDestinationDir();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IDownloadPackage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */