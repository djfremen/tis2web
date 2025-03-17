/*    */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ 
/*    */ public class SIOWISElementImpl
/*    */   extends SIOLUElementImpl {
/*    */   public SIOWISElementImpl(Integer id, int order, VCR vcr, SIOElementImpl.Callback callback, ILVCAdapter.Retrieval lvcr) {
/* 15 */     super(id, order, vcr, callback, lvcr);
/* 16 */     this.callback = callback;
/*    */   }
/*    */   private SIOElementImpl.Callback callback;
/*    */   public SIOBlob getDocument(LocaleInfo locale) {
/* 20 */     if (hasProperty((SITOCProperty)SIOProperty.Page)) {
/*    */       
/* 22 */       Integer target = getIntegerProperty((SITOCProperty)SIOProperty.Page);
/* 23 */       return this.callback.getDocument(SIOType.SI, target, locale);
/*    */     } 
/* 25 */     return this.callback.getDocument((SIO)this, locale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIOWISElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */