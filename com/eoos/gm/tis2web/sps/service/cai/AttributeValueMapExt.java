package com.eoos.gm.tis2web.sps.service.cai;

public interface AttributeValueMapExt extends AttributeValueMap {
  Object getSavePoint();
  
  void restoreSavePoint(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\cai\AttributeValueMapExt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */