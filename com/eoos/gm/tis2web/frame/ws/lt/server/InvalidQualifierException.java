/*    */ package com.eoos.gm.tis2web.frame.ws.lt.server;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class InvalidQualifierException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 13 */   private List<Pair> invalidList = new ArrayList<Pair>();
/* 14 */   private int invalidCode = 0;
/*    */   
/*    */   public int getInvalidCode() {
/* 17 */     return this.invalidCode;
/*    */   }
/*    */   
/*    */   public List<Pair> getInvalidAttributes() {
/* 21 */     return this.invalidList;
/*    */   }
/*    */   
/*    */   public void addToInvalidList(Pair attr) {
/* 25 */     this.invalidList.add(attr);
/*    */   }
/*    */   
/*    */   public void addToInvalidList(List<Pair> lst) {
/* 29 */     this.invalidList.addAll(lst);
/*    */   }
/*    */   
/*    */   public void addInvalidCode(int code) {
/* 33 */     this.invalidCode |= code;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     String result = new String("No details available.");
/* 38 */     result = new String("invalidList[");
/* 39 */     Iterator<Pair> it = this.invalidList.iterator();
/* 40 */     while (it.hasNext()) {
/* 41 */       Pair attr = it.next();
/* 42 */       result = result.concat(attr.getFirst() + "=" + attr.getSecond() + ",");
/*    */     } 
/* 44 */     if (result.endsWith(",")) {
/* 45 */       result = result.substring(0, result.length() - 1);
/*    */     }
/* 47 */     result = result.concat("]");
/*    */     
/* 49 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\InvalidQualifierException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */