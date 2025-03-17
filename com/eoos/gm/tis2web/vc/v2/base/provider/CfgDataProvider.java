package com.eoos.gm.tis2web.vc.v2.base.provider;

import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import java.util.Set;

public interface CfgDataProvider {
  long getLastModified();
  
  Set getKeys();
  
  Set getValues(Object paramObject, IConfiguration paramIConfiguration);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\CfgDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */