package com.eoos.scsm.v2.swing.table;

import javax.swing.table.TableModel;

public interface IDiffModel {
  TableModel getDelegate();
  
  int getBackendIndex(int paramInt);
  
  Object[] getRowAddition(int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\table\IDiffModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */