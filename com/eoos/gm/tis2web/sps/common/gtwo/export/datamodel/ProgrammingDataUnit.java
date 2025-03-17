package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
import java.io.Serializable;

public interface ProgrammingDataUnit extends Serializable {
  Integer getBlobID();
  
  String getBlobName();
  
  Integer getBlobSize();
  
  void setBlobSize(Integer paramInteger);
  
  byte[] getCheckSum();
  
  DownloadServer getDownloadSite();
  
  String getDownloadID();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\ProgrammingDataUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */