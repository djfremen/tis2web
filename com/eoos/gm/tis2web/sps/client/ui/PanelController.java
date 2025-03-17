package com.eoos.gm.tis2web.sps.client.ui;

import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.Value;
import javax.swing.JComponent;

public interface PanelController {
  void handleComboSelection(JComponent paramJComponent);
  
  void handleSelection(Attribute paramAttribute, Value paramValue);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\PanelController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */