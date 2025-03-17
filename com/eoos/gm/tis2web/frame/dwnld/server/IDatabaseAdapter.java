package com.eoos.gm.tis2web.frame.dwnld.server;

import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IDatabaseAdapter {
  Collection getDownloadUnits(Collection paramCollection);
  
  void getData(DownloadFile paramDownloadFile, OutputStream paramOutputStream) throws IOException;
  
  Collection getRelatedUnits(DownloadUnit paramDownloadUnit);
  
  DBVersionInformation getDBVersionInfo();
  
  Collection getAllDownloadURIs();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\IDatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */