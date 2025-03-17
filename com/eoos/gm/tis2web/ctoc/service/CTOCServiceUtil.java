/*    */ package com.eoos.gm.tis2web.ctoc.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.common.ConstraintFactory;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CTOCServiceUtil
/*    */ {
/*    */   public static Integer extractSITKey(CTOCNode sit) {
/* 14 */     Integer id = null;
/*    */     try {
/* 16 */       Object property = sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 17 */       if (property != null && property instanceof String) {
/* 18 */         int pos = ((String)property).indexOf('-');
/* 19 */         if (pos > 0) {
/* 20 */           id = Integer.valueOf(((String)property).substring(pos + 1));
/*    */         }
/*    */       } 
/* 23 */     } catch (Exception x) {}
/*    */     
/* 25 */     return id;
/*    */   }
/*    */   
/*    */   public static String translateSIT(String sit) {
/* 29 */     return ConstraintFactory.translateSIT(sit);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\CTOCServiceUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */