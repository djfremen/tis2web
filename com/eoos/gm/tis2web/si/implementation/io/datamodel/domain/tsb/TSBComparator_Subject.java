/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TSBComparator_Subject
/*    */   extends TSBComparator
/*    */ {
/*    */   protected ClientContext context;
/*    */   
/*    */   public TSBComparator_Subject(ClientContext context, int direction) {
/* 23 */     super(direction);
/* 24 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected TSBComparator_Subject(int direction) {
/* 28 */     super(direction);
/*    */   }
/*    */   
/*    */   protected int compare(SIOTSB tsb, SIOTSB tsb2) {
/*    */     try {
/* 33 */       LocaleInfo localeInfo = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 34 */       return tsb.getSubject(localeInfo).compareTo(tsb2.getSubject(localeInfo));
/* 35 */     } catch (Exception e) {
/* 36 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBComparator_Subject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */