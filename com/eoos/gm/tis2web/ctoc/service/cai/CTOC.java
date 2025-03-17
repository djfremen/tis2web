package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.ctoc.implementation.io.db.CTOCStore;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CTOC {
  CTOCNode getCTOC(VCR paramVCR);
  
  List getCTOCs();
  
  CTOCNode getCTOC(CTOCDomain paramCTOCDomain);
  
  List getSITS();
  
  CTOCNode lookupCTOC(CTOCDomain paramCTOCDomain, Integer paramInteger);
  
  CTOCNode lookupMO(Integer paramInteger);
  
  Map lookupMOs(List paramList);
  
  CTOCNode searchMO(String paramString);
  
  CTOCNode searchByProperty(CTOCNode paramCTOCNode, CTOCProperty paramCTOCProperty, String paramString, VCR paramVCR);
  
  List getCellLinks(String paramString, int paramInt);
  
  SITOCElement loadContent(Integer paramInteger);
  
  Map<Integer, SITOCElement> loadContent(Collection paramCollection);
  
  boolean checkElectronicSystemID(String paramString);
  
  String getElectronicSystemLabel(LocaleInfo paramLocaleInfo, String paramString);
  
  void registryRootSBT();
  
  CTOCLabel getLabel(Integer paramInteger);
  
  void registerDTCs(Set paramSet);
  
  List getDTCs();
  
  CTOCStore getCTOCStore();
  
  ILVCAdapter.Retrieval getLvcRetrieval();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOC.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */