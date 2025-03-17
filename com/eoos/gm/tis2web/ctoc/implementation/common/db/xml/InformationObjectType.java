package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml;

import java.util.List;

public interface InformationObjectType {
  String getVersion();
  
  void setVersion(String paramString);
  
  List getConstraint();
  
  List getLabel();
  
  String getServiceInformationType();
  
  void setServiceInformationType(String paramString);
  
  String getInformationObjectID();
  
  void setInformationObjectID(String paramString);
  
  List getFileReference();
  
  String getPage();
  
  void setPage(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\InformationObjectType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */