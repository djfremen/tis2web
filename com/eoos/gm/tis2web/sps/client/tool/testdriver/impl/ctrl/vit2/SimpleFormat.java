/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2;
/*    */ 
/*    */ import java.text.FieldPosition;
/*    */ import java.text.Format;
/*    */ import java.text.ParsePosition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleFormat
/*    */   extends Format
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 14 */   private int noOfPos = 0;
/*    */ 
/*    */   
/*    */   public SimpleFormat(int noOfPos) {
/* 18 */     this.noOfPos = noOfPos;
/*    */   }
/*    */   
/*    */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
/* 22 */     return new StringBuffer(format((String)obj));
/*    */   }
/*    */   
/*    */   public Object parseObject(String source, ParsePosition pos) {
/* 26 */     return null;
/*    */   }
/*    */   
/*    */   private String format(String src) {
/* 30 */     String result = src;
/* 31 */     for (int i = 0; i < this.noOfPos - src.length(); i++) {
/* 32 */       result = "0" + result;
/*    */     }
/* 34 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\vit2\SimpleFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */