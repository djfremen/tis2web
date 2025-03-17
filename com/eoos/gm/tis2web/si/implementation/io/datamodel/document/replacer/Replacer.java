/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer;
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
/*    */ 
/*    */ public class Replacer
/*    */ {
/*    */   public static final int TYPE_SIO_DOC_LINK = 1;
/*    */   public static final int TYPE_IMAGE_FILE_LINK = 2;
/*    */   public static final int TYPE_IMAGE_LINK = 3;
/*    */   public static final int TYPE_STYLESHEET = 4;
/*    */   public static final int TYPE_DETAIL_VIEW = 5;
/*    */   public static final int TYPE_AW_LINK = 6;
/*    */   public static final int TYPE_CELL_LINK = 7;
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
/* 43 */       RECompiler comp = new RECompiler();
/* 44 */       idStart = comp.compile("ID:");
/* 45 */       idEnd = comp.compile(":|\\}");
/* 46 */       bookmarkStart = comp.compile("BM:");
/* 47 */       bookmarkEnd = comp.compile(":|\\}");
/* 48 */       fileNameStart = comp.compile("FILE:");
/* 49 */       fileNameEnd = comp.compile(":|\\}");
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Replacer(int type) {
/* 60 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 64 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\document\replacer\Replacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */