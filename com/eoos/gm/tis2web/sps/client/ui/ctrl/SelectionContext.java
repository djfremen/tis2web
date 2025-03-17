package com.eoos.gm.tis2web.sps.client.ui.ctrl;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
import com.eoos.gm.tis2web.sps.service.cai.Attribute;

public interface SelectionContext {
  void add(Attribute paramAttribute, DisplayableValue paramDisplayableValue);
  
  void remove(Attribute paramAttribute);
  
  DisplayableValue getValue(Attribute paramAttribute);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\ctrl\SelectionContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */