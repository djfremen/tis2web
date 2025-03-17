package com.eoos.html.element.input.tree.gtwo;

import java.util.List;

public interface TreeControl {
  Object getIdentifier(Object paramObject);
  
  String getLabel(Object paramObject);
  
  List getChilds(Object paramObject);
  
  void setSelectedNode(Object paramObject);
  
  Object getSelectedNode();
  
  List getRoots();
  
  List getSelectedPath();
  
  boolean isLeaf(Object paramObject);
  
  Object getParent(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\gtwo\TreeControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */