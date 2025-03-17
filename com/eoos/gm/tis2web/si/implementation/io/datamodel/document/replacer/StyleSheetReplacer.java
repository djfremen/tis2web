/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer;
/*    */ 
/*    */ import com.eoos.datatype.SectionIndex;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import org.apache.regexp.RE;
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
/*    */ public class StyleSheetReplacer
/*    */   extends Replacer
/*    */ {
/*    */   private String content;
/* 21 */   private SectionIndex indexName = null;
/*    */ 
/*    */   
/*    */   public StyleSheetReplacer(String content) {
/* 25 */     super(4);
/* 26 */     this.content = content;
/*    */     
/*    */     try {
/* 29 */       this.indexName = StringUtilities.getSectionIndex(content, new RE(fileNameStart), new RE(fileNameEnd), 0, false, false);
/* 30 */     } catch (Exception e) {
/* 31 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getName() {
/* 36 */     return StringUtilities.getSectionContent(this.content, this.indexName);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\document\replacer\StyleSheetReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */