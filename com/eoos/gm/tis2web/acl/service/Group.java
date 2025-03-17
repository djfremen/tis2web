/*    */ package com.eoos.gm.tis2web.acl.service;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class Group
/*    */ {
/* 11 */   public static final Comparator COMPARATOR = new Comparator()
/*    */     {
/*    */       public int compare(Object o1, Object o2) {
/*    */         try {
/* 15 */           return ((Group)o1).toString().compareTo(((Group)o2).toString());
/* 16 */         } catch (NullPointerException e) {
/* 17 */           return 0;
/*    */         } 
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 23 */   public static final Group ALL = getInstance("all");
/*    */   
/*    */   private String denotation;
/*    */   
/*    */   private Group(String denotation) {
/* 28 */     this.denotation = denotation;
/*    */   }
/*    */   
/*    */   public String toExternal() {
/* 32 */     return this.denotation;
/*    */   }
/*    */   
/*    */   private static String toLowerCase(String string) {
/* 36 */     if (string == null) {
/* 37 */       return null;
/*    */     }
/* 39 */     return string.toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String normalize(String denotation) {
/* 44 */     String ret = Util.trim(denotation);
/* 45 */     ret = toLowerCase(ret);
/* 46 */     return StringUtilities.removeWhitespace(ret);
/*    */   }
/*    */   
/*    */   public static Group getInstance(String denotation) {
/* 50 */     return new Group(normalize(denotation));
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (this == obj)
/* 55 */       return true; 
/* 56 */     if (obj instanceof Group) {
/* 57 */       Group other = (Group)obj;
/* 58 */       return this.denotation.equals(other.denotation);
/*    */     } 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     int ret = Group.class.hashCode();
/* 67 */     ret = HashCalc.addHashCode(ret, this.denotation);
/* 68 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     return this.denotation;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\service\Group.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */