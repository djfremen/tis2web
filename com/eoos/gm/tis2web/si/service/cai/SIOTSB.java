package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
import com.eoos.gm.tis2web.vc.service.cai.VCValue;
import java.util.Date;
import java.util.List;

public interface SIOTSB extends SIOLU {
  void setRemedyNumber(String paramString);
  
  String getRemedyNumber();
  
  void setPublicationDate(String paramString);
  
  Date getPublicationDate();
  
  void setDTCs(List paramList);
  
  void setSymptoms(List paramList);
  
  String getSalesMake();
  
  boolean hasModelRestriction();
  
  String getModel();
  
  List getModels();
  
  boolean hasEngineRestriction();
  
  String getEngine();
  
  List getEngines();
  
  String getAssemblyGroup();
  
  boolean matchSalesMake(VCValue paramVCValue);
  
  boolean matchModel(VCValue paramVCValue);
  
  boolean matchEngine(VCValue paramVCValue);
  
  boolean matchAssemblyGroup(String paramString);
  
  boolean matchDTC(String paramString);
  
  boolean matchSymptom(String paramString);
  
  boolean matchSymptom(CTOCNode paramCTOCNode);
  
  SIOLU getSIOLU();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOTSB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */