package com.eoos.datatype.selection;

import java.util.Collection;

public interface SelectionControl {
  void setSelected(Object paramObject, boolean paramBoolean);
  
  boolean isSelected(Object paramObject);
  
  Collection getSelection();
  
  void toggleSelection(Object paramObject);
  
  void setSingleSelection(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\selection\SelectionControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */