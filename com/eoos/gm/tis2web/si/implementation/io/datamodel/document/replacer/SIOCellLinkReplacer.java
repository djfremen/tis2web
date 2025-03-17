/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer;
/*    */ 
/*    */ import com.eoos.datatype.SectionIndex;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import org.apache.regexp.RE;
/*    */ 
/*    */ 
/*    */ public class SIOCellLinkReplacer
/*    */   extends Replacer
/*    */ {
/*    */   private String content;
/* 12 */   private SectionIndex indexID = null;
/*    */   
/* 14 */   private SectionIndex indexBookmark = null;
/*    */   
/*    */   public SIOCellLinkReplacer(String content) {
/* 17 */     super(7);
/* 18 */     this.content = content;
/*    */     
/*    */     try {
/* 21 */       this.indexID = StringUtilities.getSectionIndex(content, new RE(idStart), new RE(idEnd), 0, false, false);
/* 22 */     } catch (Exception e) {
/* 23 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getID() {
/* 28 */     return StringUtilities.getSectionContent(this.content, this.indexID);
/*    */   }
/*    */   
/*    */   public String getBookmark() {
/* 32 */     return (this.indexBookmark == null) ? null : StringUtilities.getSectionContent(this.content, this.indexBookmark);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\document\replacer\SIOCellLinkReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */