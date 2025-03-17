package com.eoos.datatype.tree.construction.implementation.one;

public interface TreeConstructionSPI {
  Object getSuperroot();
  
  void addRelation(Object paramObject1, Object paramObject2);
  
  void removeRelation(Object paramObject1, Object paramObject2);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\construction\implementation\one\TreeConstructionSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */