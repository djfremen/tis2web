/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CTOCNodeBase
/*    */   implements CTOCNode
/*    */ {
/*    */   public List filterSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/* 11 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CTOCNode getParent() {
/* 15 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean isQualified(LocaleInfo locale, VCR vcr) {
/* 19 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getChildren() {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 27 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public String getLabel(LocaleInfo locale) {
/* 35 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public int getOrder() {
/* 39 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Object getProperty(SITOCProperty property) {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public SITOCType getType() {
/* 47 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public VCR getVCR() {
/* 51 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean hasProperty(SITOCProperty property) {
/* 55 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean isSIO() {
/* 59 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCNodeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */