package com.eoos.gm.tis2web.sps.service.cai;

import java.util.Collection;

public interface AttributeValueMap {
  void exchange(Attribute paramAttribute, Value paramValue);
  
  void set(Attribute paramAttribute, Value paramValue);
  
  void remove(Attribute paramAttribute);
  
  Collection getAttributes();
  
  Value getValue(Attribute paramAttribute);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\cai\AttributeValueMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */