package com.eoos.datatype.tree.construction;

import java.util.List;

public interface TreeConstruction {
  void insertParent(Object paramObject1, Object paramObject2);
  
  void addChild(Object paramObject1, Object paramObject2);
  
  void removeNode(Object paramObject);
  
  void removeSubTree(Object paramObject);
  
  void insertChildren(Object paramObject, List paramList);
  
  void addRoot(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\construction\TreeConstruction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */