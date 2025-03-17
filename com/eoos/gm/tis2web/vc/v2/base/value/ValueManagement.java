package com.eoos.gm.tis2web.vc.v2.base.value;

import java.util.Collection;
import java.util.Set;

public interface ValueManagement {
  Value toValue(Object paramObject);
  
  Set resolve(Value paramValue, ResolveCallback paramResolveCallback) throws UnresolvableException;
  
  Value intersect(Value paramValue1, Value paramValue2);
  
  boolean equals(Value paramValue1, Value paramValue2);
  
  int hashCode(Value paramValue);
  
  Set createDisjunctiveValues(Set paramSet1, Set paramSet2);
  
  Value union(Collection paramCollection);
  
  boolean isANY(Value paramValue);
  
  Value getANY();
  
  public static interface ResolveCallback {
    Set getDomain();
  }
  
  public static interface Mixin1 {
    boolean haveIntersection(Value param1Value1, Value param1Value2);
    
    boolean includes(Value param1Value1, Value param1Value2);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\value\ValueManagement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */