package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr;

import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;

public interface NodeChangeListener {
  void onSetNode(Object paramObject, History paramHistory);
  
  void onResetNodes();
  
  void onClearNodes();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\cpr\NodeChangeListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */