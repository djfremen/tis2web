package com.eoos.gm.tis2web.vc.v2.provider;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import java.util.Set;

public interface CfgProviderRetrieval {
  Set getCfgProviders();
  
  public static interface Contextual {
    Set getCfgProviders(ClientContext param1ClientContext);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\CfgProviderRetrieval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */