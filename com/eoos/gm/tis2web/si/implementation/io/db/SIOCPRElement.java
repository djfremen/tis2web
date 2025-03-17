/*    */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ 
/*    */ public class SIOCPRElement
/*    */   extends SIOElementImpl implements SIOCPR {
/*    */   private SIOElementImpl.Callback callback;
/*    */   
/*    */   public SIOCPRElement(Integer id, int order, VCR vcr, SIOElementImpl.Callback callback, ILVCAdapter.Retrieval lvcr) {
/* 18 */     super(id, order, SIOType.CPR.ord(), vcr, callback, lvcr);
/* 19 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public SIOBlob getDocument(LocaleInfo locale) {
/* 23 */     return this.callback.getDocument((SIO)this, locale);
/*    */   }
/*    */   
/*    */   public String getSubject(LocaleInfo locale) {
/* 27 */     return getLabel(locale);
/*    */   }
/*    */   
/*    */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CTOCNode getWiringDiagrams() {
/* 37 */     Integer id = (Integer)CollectionUtil.getFirst(getElectronicSystemLinks());
/* 38 */     return (id == null) ? null : this.callback.getWiringDiagrams(id.toString());
/*    */   }
/*    */   
/*    */   public Integer getElectronicSystemCode() {
/* 42 */     return (Integer)CollectionUtil.getFirst(getElectronicSystemLinks());
/*    */   }
/*    */   
/*    */   public String getElectronicSystemLabel(LocaleInfo locale) {
/* 46 */     return this.callback.getElectronicSystemLabel(locale, getElectronicSystemCode().toString());
/*    */   }
/*    */   
/*    */   public ILVCAdapter getILVCAdapter() {
/* 50 */     return getLvcr().getLVCAdapter();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIOCPRElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */