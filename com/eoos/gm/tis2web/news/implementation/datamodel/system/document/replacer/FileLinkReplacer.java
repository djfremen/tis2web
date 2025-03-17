/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer;
/*    */ 
/*    */ import com.eoos.datatype.SectionIndex;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import org.apache.regexp.RE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileLinkReplacer
/*    */   extends Replacer
/*    */ {
/*    */   private String content;
/* 15 */   private SectionIndex indexName = null;
/*    */ 
/*    */   
/*    */   public FileLinkReplacer(String content) {
/* 19 */     super(7);
/* 20 */     this.content = content;
/*    */     
/*    */     try {
/* 23 */       this.indexName = StringUtilities.getSectionIndex(content, new RE(fileNameStart), new RE(fileNameEnd), 0, false, false);
/* 24 */     } catch (Exception e) {
/* 25 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getName() {
/* 30 */     return StringUtilities.getSectionContent(this.content, this.indexName);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\system\document\replacer\FileLinkReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */