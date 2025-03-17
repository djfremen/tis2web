package com.eoos.gm.tis2web.sps.client.tool.testdriver;

import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
import com.eoos.gm.tis2web.sps.common.VIT1;
import java.util.List;

public interface VIT2DataHandler {
  List getExVIT2Attrs(VIT1 paramVIT1, VIT2 paramVIT2, ITestDriverSettings paramITestDriverSettings, List paramList);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\VIT2DataHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */