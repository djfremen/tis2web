package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av;

import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.util.List;

public interface CustomAVMap extends AttributeValueMap {
  void explicitSet(Attribute paramAttribute, Value paramValue);
  
  List getExplicitEntries();
  
  public static interface Entry {
    Attribute getAttribute();
    
    Value getValue();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\av\CustomAVMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */