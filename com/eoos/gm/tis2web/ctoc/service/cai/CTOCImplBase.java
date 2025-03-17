/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.CTOCStore;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class CTOCImplBase
/*    */   implements CTOC {
/*    */   public CTOCNode searchMO(String es) {
/* 15 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/* 19 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void registryRootSBT() {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void registerDTCs(Set set) {
/* 27 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode lookupMO(Integer ctocID) {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode lookupCTOC(CTOCDomain type, Integer ctocID) {
/* 35 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Map<Integer, SITOCElement> loadContent(Collection arg0) {
/* 39 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public SITOCElement loadContent(Integer contentID) {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public ILVCAdapter.Retrieval getLvcRetrieval() {
/* 47 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCLabel getLabel(Integer labelID) {
/* 51 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public String getElectronicSystemLabel(LocaleInfo locale, String escode) {
/* 55 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getDTCs() {
/* 59 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getCellLinks(String publicationID, int cellID) {
/* 63 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getCTOCs() {
/* 67 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCStore getCTOCStore() {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode getCTOC(CTOCDomain arg0) {
/* 75 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode getCTOC(VCR vcr) {
/* 79 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean checkElectronicSystemID(String escode) {
/* 83 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getSITS() {
/* 87 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Map lookupMOs(List ids) {
/* 91 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCImplBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */