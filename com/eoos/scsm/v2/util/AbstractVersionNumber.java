/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class AbstractVersionNumber
/*    */   implements VersionNumber {
/*  8 */   private static final Logger log = Logger.getLogger(AbstractVersionNumber.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract String[] getParts();
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(Object o) {
/* 17 */     int ret = 0;
/*    */     try {
/* 19 */       AbstractVersionNumber other = (AbstractVersionNumber)o;
/* 20 */       String[] thisParts = getParts();
/* 21 */       String[] otherParts = other.getParts();
/* 22 */       for (int i = 0; i < Math.min(thisParts.length, otherParts.length) && ret == 0; i++) {
/* 23 */         if (Util.onlyDigits(thisParts[i]) && Util.onlyDigits(otherParts[i])) {
/* 24 */           ret = Integer.parseInt(thisParts[i]) - Integer.parseInt(otherParts[i]);
/*    */         } else {
/* 26 */           ret = thisParts[i].compareTo(otherParts[i]);
/*    */         } 
/*    */       } 
/* 29 */       if (ret == 0) {
/* 30 */         ret = thisParts.length - otherParts.length;
/*    */       }
/* 32 */       return ret;
/* 33 */     } catch (Exception e) {
/* 34 */       log.warn("unable to compare - exception: " + e, e);
/* 35 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 40 */     int ret = getClass().hashCode();
/* 41 */     ret = HashCalc.addHashCode(ret, (Object[])getParts());
/* 42 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 46 */     if (this == obj)
/* 47 */       return true; 
/* 48 */     if (obj instanceof AbstractVersionNumber) {
/* 49 */       AbstractVersionNumber other = (AbstractVersionNumber)obj;
/* 50 */       boolean ret = Arrays.equals((Object[])getParts(), (Object[])other.getParts());
/* 51 */       return ret;
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\AbstractVersionNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */