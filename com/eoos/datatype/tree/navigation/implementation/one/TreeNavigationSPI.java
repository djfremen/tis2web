package com.eoos.datatype.tree.navigation.implementation.one;

import java.util.List;

public interface TreeNavigationSPI {
  List getChildren(Object paramObject);
  
  Object getParent(Object paramObject);
  
  Object getSuperroot();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\navigation\implementation\one\TreeNavigationSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */