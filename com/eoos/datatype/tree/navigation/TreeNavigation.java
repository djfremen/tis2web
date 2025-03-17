package com.eoos.datatype.tree.navigation;

import java.util.List;

public interface TreeNavigation {
  List getChildren(Object paramObject);
  
  Object getParent(Object paramObject);
  
  List getSiblings(Object paramObject, boolean paramBoolean);
  
  Object getNextSibling(Object paramObject);
  
  Object getPreviousSibling(Object paramObject);
  
  List getRoots();
  
  Object getSibling(Object paramObject, int paramInt);
  
  TreeNavigation getSubTreeNavigation(Object paramObject);
  
  boolean isLeaf(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\navigation\TreeNavigation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */