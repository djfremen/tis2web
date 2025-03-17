package com.eoos.html.element.input.tree;

import java.util.List;

public interface TreeControl {
  String getLabel(Object paramObject);
  
  List getChilds(Object paramObject);
  
  void setSelectedNode(Object paramObject);
  
  Object getSelectedNode();
  
  List getRoots();
  
  List getSelectedPath();
  
  boolean isLeaf(Object paramObject);
  
  boolean isExpanded(Object paramObject);
  
  void toggleExpanded(Object paramObject);
  
  void setExpanded(Object paramObject, boolean paramBoolean);
  
  Object getParent(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\TreeControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */