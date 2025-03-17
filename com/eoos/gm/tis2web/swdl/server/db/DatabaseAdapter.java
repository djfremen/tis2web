package com.eoos.gm.tis2web.swdl.server.db;

import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import com.eoos.gm.tis2web.swdl.common.domain.application.File;
import java.util.Set;

public interface DatabaseAdapter {
  Set getApplications();
  
  File getFile(String paramString);
  
  DBVersionInformation getVersionInfo();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */