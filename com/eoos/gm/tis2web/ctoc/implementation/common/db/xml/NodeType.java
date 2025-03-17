package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml;

import java.util.List;

public interface NodeType {
  String getNodeID();
  
  void setNodeID(String paramString);
  
  List getNode();
  
  String getVersion();
  
  void setVersion(String paramString);
  
  List getConstraint();
  
  List getInformationObject();
  
  List getLabel();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\NodeType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */