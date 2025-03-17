/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer;
/*    */ 
/*    */ import org.apache.regexp.RECompiler;
/*    */ import org.apache.regexp.REProgram;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Replacer
/*    */ {
/*    */   public static final int TYPE_DOC_LINK = 1;
/*    */   public static final int TYPE_IMAGE_FILE_LINK = 2;
/*    */   public static final int TYPE_STYLESHEET = 4;
/*    */   public static final int TYPE_DETAIL_VIEW = 5;
/*    */   public static final int TYPE_AW_LINK = 6;
/*    */   public static final int TYPE_FILE_LINK = 7;
/*    */   protected static REProgram idStart;
/*    */   protected static REProgram idEnd;
/*    */   protected static REProgram bookmarkStart;
/*    */   protected static REProgram bookmarkEnd;
/*    */   protected static REProgram fileNameStart;
/*    */   protected static REProgram fileNameEnd;
/*    */   private int type;
/*    */   
/*    */   static {
/*    */     try {
/* 41 */       RECompiler comp = new RECompiler();
/* 42 */       idStart = comp.compile("ID:");
/* 43 */       idEnd = comp.compile(":|\\}");
/* 44 */       bookmarkStart = comp.compile("BM:");
/* 45 */       bookmarkEnd = comp.compile(":|\\}");
/* 46 */       fileNameStart = comp.compile("FILE:");
/* 47 */       fileNameEnd = comp.compile(":|\\}");
/*    */     }
/* 49 */     catch (Exception e) {
/* 50 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Replacer(int type) {
/* 58 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 62 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\system\document\replacer\Replacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */