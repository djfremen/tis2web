package com.eoos.gm.tis2web.sas.client.ui.util;

import javax.swing.JPanel;

public interface PanelStack {
  void push(JPanel paramJPanel);
  
  JPanel pop();
  
  JPanel peek();
  
  void clear();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\PanelStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */