/*    */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SIOWDElement
/*    */   extends SIOElementImpl implements SIOWD {
/*    */   private SIOElementImpl.Callback callback;
/*    */   
/*    */   public SIOWDElement(Integer id, int order, VCR vcr, SIOElementImpl.Callback callback, ILVCAdapter.Retrieval lvcr) {
/* 19 */     super(id, order, SIOType.WD.ord(), vcr, callback, lvcr);
/* 20 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public SIOBlob getWiringDiagram(LocaleInfo locale) {
/* 25 */     if (!hasProperty((SITOCProperty)SIOProperty.CircuitFile))
/*    */     {
/* 27 */       return this.callback.getDocument(SIOType.WD, (Integer)getProperty((SITOCProperty)SIOProperty.WD), locale);
/*    */     }
/*    */     
/* 30 */     return this.callback.getDocument(SIOType.WD, getID(), locale);
/*    */   }
/*    */ 
/*    */   
/*    */   public List getRelatedCheckingProcedures() {
/* 35 */     List esLinks = getElectronicSystemLinks();
/* 36 */     if (Util.isNullOrEmpty(esLinks)) {
/* 37 */       return null;
/*    */     }
/* 39 */     List cprs = this.callback.loadCheckingProcedures(esLinks);
/* 40 */     return cprs;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIOWDElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */