/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class StringVersionComparator
/*    */   implements Comparator<String> {
/* 11 */   private String delims = ".";
/* 12 */   private int radix = 10;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StringVersionComparator(String delims) {
/* 18 */     this.delims = delims;
/*    */   }
/*    */   
/*    */   public StringVersionComparator(int radix) {
/* 22 */     this.radix = radix;
/*    */   }
/*    */   
/*    */   public StringVersionComparator(String delims, int radix) {
/* 26 */     this.delims = delims;
/* 27 */     this.radix = radix;
/*    */   }
/*    */   
/*    */   public int compare(String v1, String v2) {
/*    */     try {
/* 32 */       List<String> p1 = getParts(v1);
/* 33 */       List<String> p2 = getParts(v2);
/* 34 */       int diff = p1.size() - p2.size();
/* 35 */       if (diff != 0) {
/* 36 */         return diff;
/*    */       }
/* 38 */       Iterator<String> it1 = p1.iterator();
/* 39 */       Iterator<String> it2 = p2.iterator();
/* 40 */       while (it1.hasNext() && it2.hasNext()) {
/* 41 */         diff = Integer.parseInt(it1.next(), this.radix) - Integer.parseInt(it2.next(), this.radix);
/* 42 */         if (diff != 0) {
/* 43 */           return diff;
/*    */         }
/*    */       } 
/* 46 */       return diff;
/* 47 */     } catch (Exception e) {
/* 48 */       return -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setDelimiters(String delims) {
/* 53 */     this.delims = delims;
/*    */   }
/*    */   
/*    */   public void setRadix(int radix) {
/* 57 */     this.radix = radix;
/*    */   }
/*    */   
/*    */   private List<String> getParts(String v) {
/* 61 */     List<String> p = new ArrayList<String>();
/*    */     try {
/* 63 */       StringTokenizer t = new StringTokenizer(v, this.delims);
/* 64 */       while (t.hasMoreTokens()) {
/* 65 */         p.add(t.nextToken());
/*    */       }
/* 67 */     } catch (Exception x) {}
/*    */     
/* 69 */     return p;
/*    */   }
/*    */   
/*    */   public StringVersionComparator() {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\StringVersionComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */