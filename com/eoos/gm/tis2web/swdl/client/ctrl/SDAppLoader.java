package com.eoos.gm.tis2web.swdl.client.ctrl;

import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
import java.util.Hashtable;

public interface SDAppLoader {
  SDFileInfo[] getFilesInfo();
  
  byte[] getFileData(Integer paramInteger);
  
  Hashtable getFileID2Name();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\SDAppLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */