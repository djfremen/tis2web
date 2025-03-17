package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.CPRDocumentNotSupportedException;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface SICache {
  public static final String INSPECTIONS = "SIT-13";
  
  DBVersionInformation getVersionInfo();
  
  void register(CTOC paramCTOC);
  
  SITOCElement make(CTOCType paramCTOCType, int paramInt1, int paramInt2, long paramLong, VCR paramVCR);
  
  List loadSIOs(List paramList);
  
  void loadProperties(List paramList);
  
  SIOElement getElement(Integer paramInteger);
  
  SIOElement loadElement(Integer paramInteger);
  
  String getSubject(SIO paramSIO, LocaleInfo paramLocaleInfo);
  
  String getSubject(Integer paramInteger, LocaleInfo paramLocaleInfo);
  
  SIOBlob getDocument(SIO paramSIO, LocaleInfo paramLocaleInfo);
  
  SIOBlob getDocument(SIOType paramSIOType, Integer paramInteger, LocaleInfo paramLocaleInfo);
  
  String getMimeType(int paramInt);
  
  String getMimeType4Image(int paramInt);
  
  SIOBlob getGraphic(int paramInt);
  
  SIOBlob getImage(int paramInt);
  
  List searchDocumentsByNumber(String paramString);
  
  List searchDocumentsByPublicationID(String paramString);
  
  CTOCNode provideDocumentsBySIO(List paramList);
  
  SI.MHTML provideMHTMLDocument(List paramList, LocaleInfo paramLocaleInfo) throws DocumentNotFoundException, DocumentContainerConstructionException, CPRDocumentNotSupportedException;
  
  CTOCNode getWiringDiagrams(String paramString);
  
  String getElectronicSystemLabel(LocaleInfo paramLocaleInfo, String paramString);
  
  boolean checkElectronicSystemID(String paramString);
  
  List loadCheckingProcedures(List paramList);
  
  List provideTSBs();
  
  List provideDTCs();
  
  List provideIBs(Integer paramInteger);
  
  byte[] getScreenData(String paramString) throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SICache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */