package com.eoos.gm.tis2web.vc.v2.base.configuration;

import com.eoos.gm.tis2web.vc.v2.base.value.Value;
import java.util.Set;

public interface IConfiguration {
  Set getKeys();
  
  Value getValue(Object paramObject);
  
  public static interface Mutable {
    void setAttribute(Object param1Object, Value param1Value);
    
    void removeAttribute(Object param1Object);
    
    void clear();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\IConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */