package com.eoos.datatype.selection.implementation;

import java.util.Collection;

public interface SelectionControlSPI {
  void addSelection(Object paramObject);
  
  void removeSelection(Object paramObject);
  
  Collection getSelection();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\selection\implementation\SelectionControlSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */