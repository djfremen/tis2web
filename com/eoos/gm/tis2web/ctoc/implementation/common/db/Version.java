/*    */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Version
/*    */ {
/* 14 */   private List tuple = new LinkedList(); public Version(String version) {
/* 15 */     StringTokenizer tokenizer = new StringTokenizer(version, ".", false);
/* 16 */     while (tokenizer.hasMoreTokens()) {
/* 17 */       this.tuple.add(Integer.valueOf(tokenizer.nextToken()));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 22 */     if (object instanceof Version) {
/* 23 */       Version version = (Version)object;
/* 24 */       normalize(this, version);
/* 25 */       normalize(version, this);
/* 26 */       for (int i = 0; i < this.tuple.size(); i++) {
/* 27 */         if (!this.tuple.get(i).equals(version.tuple.get(i))) {
/* 28 */           return false;
/*    */         }
/*    */       } 
/* 31 */       return true;
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     int ret = Version.class.hashCode();
/* 39 */     ret = HashCalc.addHashCode(ret, this.tuple);
/* 40 */     return ret;
/*    */   }
/*    */   
/*    */   public int compareTo(Version version) {
/* 44 */     normalize(this, version);
/* 45 */     normalize(version, this);
/* 46 */     for (int i = 0; i < this.tuple.size(); i++) {
/* 47 */       Integer a = this.tuple.get(i);
/* 48 */       Integer b = version.tuple.get(i);
/* 49 */       int diff = a.intValue() - b.intValue();
/* 50 */       if (diff != 0) {
/* 51 */         return diff;
/*    */       }
/*    */     } 
/* 54 */     return 0;
/*    */   }
/*    */   
/*    */   protected void normalize(Version a, Version b) {
/* 58 */     int diff = b.tuple.size() - a.tuple.size();
/* 59 */     if (diff > 0) {
/* 60 */       for (int i = 0; i < diff; i++) {
/* 61 */         a.tuple.add(Integer.valueOf(0));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuffer version = new StringBuffer();
/* 68 */     for (int i = 0; i < this.tuple.size(); i++) {
/* 69 */       if (i > 0) {
/* 70 */         version.append(".");
/*    */       }
/* 72 */       version.append(this.tuple.get(i).toString());
/*    */     } 
/* 74 */     return version.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */