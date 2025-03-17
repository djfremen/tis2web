/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.system.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.FileLinkReplacer;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.ImageFileReplacer;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.NewsDocumentLinkReplacer;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.StyleSheetReplacer;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.UnknownReplacer;
/*    */ import com.eoos.html.util.HtmlDocumentManipulationFacade;
/*    */ import com.eoos.util.PatternIterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewsDocumentFacade
/*    */   extends HtmlDocumentManipulationFacade
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(NewsDocumentFacade.class);
/*    */   
/*    */   protected static final String patternImageFile = "IMAGE:FILE:";
/*    */   
/*    */   protected static final String patternDocumentLink = "LINK:ID:";
/*    */   
/*    */   protected static final String patternStylesheet = "STYLE_SHEET:FILE:";
/*    */   
/*    */   protected static final String patternFileLink = "LINK:FILE:";
/*    */   
/*    */   protected static final String patternStart = "/?\\{(IMAGE:FILE:|LINK:ID:|STYLE_SHEET:FILE:|LINK:FILE:)";
/*    */   protected static final String patternEnd = "\\}";
/*    */   
/*    */   public class ReplacerIterator
/*    */     extends PatternIterator
/*    */   {
/*    */     public ReplacerIterator() {
/* 38 */       super(NewsDocumentFacade.this.document, "/?\\{(IMAGE:FILE:|LINK:ID:|STYLE_SHEET:FILE:|LINK:FILE:)", "\\}");
/*    */     }
/*    */     
/*    */     public Object next() {
/* 42 */       Object retValue = null;
/* 43 */       String tmp = (String)super.next();
/* 44 */       if (tmp.indexOf("IMAGE:FILE:") != -1) {
/* 45 */         retValue = new ImageFileReplacer(tmp);
/* 46 */       } else if (tmp.indexOf("LINK:ID:") != -1) {
/* 47 */         retValue = new NewsDocumentLinkReplacer(tmp);
/* 48 */       } else if (tmp.indexOf("STYLE_SHEET:FILE:") != -1) {
/* 49 */         retValue = new StyleSheetReplacer(tmp);
/* 50 */       } else if (tmp.indexOf("LINK:FILE:") != -1) {
/* 51 */         retValue = new FileLinkReplacer(tmp);
/*    */       } else {
/* 53 */         NewsDocumentFacade.log.warn("unknown replacer: " + tmp);
/* 54 */         retValue = new UnknownReplacer(tmp);
/*    */       } 
/* 56 */       return retValue;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NewsDocumentFacade(StringBuffer document) {
/* 63 */     super(document);
/*    */   }
/*    */   
/*    */   public ReplacerIterator iterator() {
/* 67 */     return new ReplacerIterator();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\system\document\NewsDocumentFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */