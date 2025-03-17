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
/*    */ public class AWLinkReplacer
/*    */   extends Replacer
/*    */ {
/*    */   private String content;
/* 21 */   private SectionIndex indexID = null;
/*    */ 
/*    */   
/*    */   public AWLinkReplacer(String content) {
/* 25 */     super(6);
/* 26 */     this.content = content;
/*    */     
/*    */     try {
/* 29 */       this.indexID = StringUtilities.getSectionIndex(content, new RE(idStart), new RE(idEnd), 0, false, false);
/* 30 */     } catch (Exception e) {
/* 31 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getID() {
/* 36 */     return StringUtilities.getSectionContent(this.content, this.indexID);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\document\replacer\AWLinkReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */