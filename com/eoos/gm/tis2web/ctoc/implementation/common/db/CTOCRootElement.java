/*    */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ 
/*    */ public class CTOCRootElement extends CTOCElementImpl {
/*    */   public CTOCRootElement(int tocID, int ctocType, boolean hasChildren, boolean hasContent, VCR vcr, ILVCAdapter adapter) {
/* 10 */     super(tocID, null, 0, ctocType, hasChildren, hasContent, vcr, adapter);
/*    */   }
/*    */   
/*    */   public String getLabel(LocaleInfo locale) {
/* 14 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\CTOCRootElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */