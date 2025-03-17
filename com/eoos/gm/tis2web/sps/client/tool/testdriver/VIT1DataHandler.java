package com.eoos.gm.tis2web.sps.client.tool.testdriver;

import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
import java.util.List;

public interface VIT1DataHandler {
  public static final String COMMENT = "Rem";
  
  public static final String OPTION = "Rem Option";
  
  public static final String VIT_ID = "VIT_ID";
  
  public static final String STATUS = "Status";
  
  public static final String WRITE_MODULES = "Write_Modules";
  
  public static final String READ_HANDLING = "Read_Handling";
  
  public static final String RENAME_VIT1 = "Rename_VIT1";
  
  public static final String VAL_AFTER_READ = "After Read";
  
  public static final String VAL_AFTER_DOWNLOAD = "After Download";
  
  public static final String VAL_READ = "\"read\"";
  
  public static final String EXT_TMP = "_TMP_";
  
  List extractVIT1Data(List paramList, ITestDriverSettings paramITestDriverSettings);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\VIT1DataHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */