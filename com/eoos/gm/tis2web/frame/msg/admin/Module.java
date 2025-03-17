/*    */ package com.eoos.gm.tis2web.frame.msg.admin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class Module
/*    */ {
/* 12 */   public static final Comparator COMPARATOR = new Comparator()
/*    */     {
/*    */       public int compare(Object o1, Object o2) {
/*    */         try {
/* 16 */           return ((Module)o1).toString().compareTo(((Module)o2).toString());
/* 17 */         } catch (NullPointerException e) {
/* 18 */           return 0;
/*    */         } 
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private String denotation;
/*    */   
/*    */   private Module(String denotation) {
/* 27 */     this.denotation = denotation;
/*    */   }
/*    */   
/*    */   public String toExternal() {
/* 31 */     return this.denotation;
/*    */   }
/*    */   
/*    */   private static String toLowerCase(String string) {
/* 35 */     if (string == null) {
/* 36 */       return null;
/*    */     }
/* 38 */     return string.toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String normalize(String denotation) {
/* 43 */     String ret = Util.trim(denotation);
/* 44 */     ret = toLowerCase(ret);
/* 45 */     return StringUtilities.removeWhitespace(ret);
/*    */   }
/*    */   
/*    */   public static Module getInstance(String denotation) {
/* 49 */     return new Module(normalize(denotation));
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 53 */     if (this == obj)
/* 54 */       return true; 
/* 55 */     if (obj instanceof Module) {
/* 56 */       Module other = (Module)obj;
/* 57 */       return this.denotation.equals(other.denotation);
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     int ret = Module.class.hashCode();
/* 66 */     ret = HashCalc.addHashCode(ret, this.denotation);
/* 67 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 71 */     return this.denotation;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 75 */     return ApplicationContext.getInstance().getLabel(locale, "module.type." + this.denotation + ".abbreviation");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\Module.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */