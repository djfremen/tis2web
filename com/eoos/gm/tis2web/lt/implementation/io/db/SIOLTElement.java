/*    */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SIOLTElement
/*    */   extends CTOCElementImpl
/*    */   implements SIOLT
/*    */ {
/*    */   public String getSubject(LocaleInfo locale) {
/* 22 */     String label = getLabel(locale);
/* 23 */     if (label == null) {
/* 24 */       List<Integer> flc = locale.getLocaleFLC(LGSIT.LT);
/* 25 */       if (flc == null) {
/* 26 */         return null;
/*    */       }
/* 28 */       for (int i = 0; i < flc.size(); i++) {
/* 29 */         LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(flc.get(i));
/* 30 */         label = getLabel(li);
/* 31 */         if (label != null) {
/*    */           break;
/*    */         }
/*    */       } 
/*    */     } 
/* 36 */     return label;
/*    */   }
/*    */   
/*    */   public String getDisplay(LocaleInfo locale) {
/* 40 */     String ps = getPaintingStage();
/* 41 */     if (ps == null) {
/* 42 */       return getMajorOperationNumber() + " " + getSubject(locale);
/*    */     }
/* 44 */     return getMajorOperationNumber() + " " + getSubject(locale) + " - " + ps;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMajorOperationNumber() {
/* 49 */     String no = (String)getProperty((SITOCProperty)CTOCProperty.MajorOperation);
/* 50 */     return (no == null) ? null : no.substring(0, 7);
/*    */   }
/*    */   
/*    */   public boolean getChangeFlag() {
/* 54 */     return hasProperty((SITOCProperty)CTOCProperty.ChangeFlag);
/*    */   }
/*    */   
/*    */   public String getPaintingStage() {
/* 58 */     return (String)getProperty((SITOCProperty)CTOCProperty.PaintingStage);
/*    */   }
/*    */   
/*    */   public boolean isQualified(LocaleInfo locale, VCR vcr) {
/* 62 */     return (this.vcr == null) ? true : this.vcr.match(locale, vcr);
/*    */   }
/*    */   
/*    */   public SIOLTElement(Integer id, int order, int labelID, VCR vcr, ILVCAdapter adapter) {
/* 66 */     super(id.intValue(), Integer.valueOf(labelID), order, CTOCType.MajorOperation.ord(), false, false, vcr, adapter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\SIOLTElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */