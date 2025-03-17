/*    */ package com.eoos.gm.tis2web.ctoc.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CTOCUtil
/*    */ {
/*    */   private static String normalize(String string) {
/* 15 */     String ret = null;
/* 16 */     if (string != null) {
/* 17 */       StringBuffer buffer = new StringBuffer(string.toLowerCase(Locale.ENGLISH));
/* 18 */       for (int i = 0; i < buffer.length(); i++) {
/* 19 */         if (!Character.isLetterOrDigit(buffer.charAt(i))) {
/* 20 */           buffer.deleteCharAt(i);
/*    */         }
/*    */       } 
/* 23 */       ret = buffer.toString();
/*    */     } 
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   public static boolean isTechnicalServiceBulletin(CTOCAccess ctocAccess, CTOCAccess.Node node) {
/* 29 */     return Util.equals("sit12", normalize(ctocAccess.getProperty(node, CTOCAccess.PropertyKey.SIT)));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\v2\CTOCUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */