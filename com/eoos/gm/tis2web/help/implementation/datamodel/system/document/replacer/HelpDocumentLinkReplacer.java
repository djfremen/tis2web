/*    */ package com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer;
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
/*    */ public class HelpDocumentLinkReplacer
/*    */   extends Replacer
/*    */ {
/*    */   private String content;
/* 21 */   private SectionIndex indexID = null;
/*    */   
/* 23 */   private SectionIndex indexBookmark = null;
/*    */ 
/*    */   
/*    */   public HelpDocumentLinkReplacer(String content) {
/* 27 */     super(1);
/* 28 */     this.content = content;
/*    */     
/*    */     try {
/* 31 */       this.indexID = StringUtilities.getSectionIndex(content, new RE(idStart), new RE(idEnd), 0, false, false);
/*    */     }
/* 33 */     catch (Exception e) {
/* 34 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getID() {
/* 39 */     return StringUtilities.getSectionContent(this.content, this.indexID);
/*    */   }
/*    */   
/*    */   public String getBookmark() {
/* 43 */     return (this.indexBookmark == null) ? null : StringUtilities.getSectionContent(this.content, this.indexBookmark);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\system\document\replacer\HelpDocumentLinkReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */