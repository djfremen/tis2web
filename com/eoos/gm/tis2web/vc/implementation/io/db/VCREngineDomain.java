/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class VCREngineDomain
/*    */   extends VCRDomainImpl {
/*    */   public VCREngineDomain(int domain_id, String domain_name, VCRLabel domain_label, boolean language_dependent) {
/* 10 */     super(domain_id, domain_name, domain_label, false);
/*    */   }
/*    */   
/*    */   public static String makeKey(String label) {
/* 14 */     if (label == null) {
/* 15 */       return null;
/*    */     }
/* 17 */     int length = label.length();
/* 18 */     StringBuffer buffer = new StringBuffer(length);
/*    */     
/* 20 */     for (int i = 0; i < length; i++) {
/* 21 */       char c = label.charAt(i);
/* 22 */       if (Character.isLetterOrDigit(c)) {
/* 23 */         buffer.append(c);
/*    */       }
/*    */     } 
/* 26 */     return buffer.toString();
/*    */   }
/*    */   
/*    */   public VCRValue lookup(Integer locale, String key) {
/* 30 */     if (this.values == null || key == null) {
/* 31 */       return null;
/*    */     }
/* 33 */     key = makeKey(key);
/* 34 */     Iterator<VCRValueImpl> it = getValues().iterator();
/* 35 */     while (it.hasNext()) {
/* 36 */       VCRValueImpl v = it.next();
/* 37 */       VCRLabel label = v.getLabel();
/* 38 */       if (label == null) {
/*    */         continue;
/*    */       }
/* 41 */       String encoding = makeKey(label.getLabel(locale));
/* 42 */       if (key.equals(encoding)) {
/* 43 */         return v;
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCREngineDomain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */