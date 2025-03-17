package com.eoos.gm.tis2web.vc.v2.base.configuration;

import com.eoos.gm.tis2web.vc.v2.base.value.Value;

public interface ConfigurationManagement {
  IConfiguration getEmptyConfiguration();
  
  IConfiguration setAttribute(IConfiguration paramIConfiguration, Object paramObject, Value paramValue);
  
  IConfiguration removeAttribute(IConfiguration paramIConfiguration, Object paramObject);
  
  IConfiguration toMutableConfiguration(IConfiguration paramIConfiguration);
  
  IConfiguration toImmutableConfiguration(IConfiguration paramIConfiguration);
  
  public static interface Mixin1 {
    boolean equals(IConfiguration param1IConfiguration1, IConfiguration param1IConfiguration2);
    
    int hashCode(IConfiguration param1IConfiguration);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\ConfigurationManagement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */