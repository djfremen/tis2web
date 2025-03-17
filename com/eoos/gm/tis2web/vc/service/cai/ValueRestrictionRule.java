package com.eoos.gm.tis2web.vc.service.cai;

import java.io.Serializable;

public interface ValueRestrictionRule extends Serializable {
  boolean include(String paramString, Object paramObject, VehicleContextData paramVehicleContextData);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\ValueRestrictionRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */