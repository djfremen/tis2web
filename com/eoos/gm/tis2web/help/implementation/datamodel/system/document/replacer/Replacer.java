/*    */ package com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer;
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
/*    */   public static final int TYPE_HELP_DOC_LINK = 1;
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
/*    */       
/* 43 */       idStart = comp.compile("ID:");
/* 44 */       idEnd = comp.compile(":|\\}");
/* 45 */       bookmarkStart = comp.compile("BM:");
/* 46 */       bookmarkEnd = comp.compile(":|\\}");
/* 47 */       fileNameStart = comp.compile("FILE:");
/* 48 */       fileNameEnd = comp.compile(":|\\}");
/*    */     }
/* 50 */     catch (Exception e) {
/* 51 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Replacer(int type) {
/* 59 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 63 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\system\document\replacer\Replacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */