/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OperatorCacheElement
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private char op;
/*    */   private String text;
/*    */   
/*    */   public char getOp() {
/* 16 */     return this.op;
/*    */   }
/*    */   
/*    */   public void setOp(char op) {
/* 20 */     this.op = op;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 24 */     return this.text;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 28 */     this.text = text;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\OperatorCacheElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */