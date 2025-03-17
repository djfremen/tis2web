/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.AWLinkReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.DetailViewReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.ImageFileReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.ImageReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.SIOCellLinkReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.SIODocumentLinkReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.StyleSheetReplacer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.replacer.UnknownReplacer;
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
/*    */ public class SIODocumentFacade
/*    */   extends HtmlDocumentManipulationFacade
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(SIODocumentFacade.class);
/*    */   
/*    */   protected static final String patternImageFile = "IMAGE:FILE:";
/*    */   
/*    */   protected static final String patternImage = "LINK_IMAGE:ID:";
/*    */   
/*    */   protected static final String patternDocumentLink = "LINK_SIO:ID:";
/*    */   
/*    */   protected static final String patternStylesheet = "STYLE_SHEET:FILE:";
/*    */   
/*    */   protected static final String patternDetail = "LINK_DETAIL:ID:";
/*    */   
/*    */   protected static final String patternAWLink = "LINK_AW:ID:";
/*    */   
/*    */   protected static final String patternCellLink = "LINK_CELL:ID:";
/*    */   
/*    */   protected static final String patternStart = "/?\\{(IMAGE:FILE:|LINK_CELL:ID:|LINK_SIO:ID:|STYLE_SHEET:FILE:|LINK_IMAGE:ID:|LINK_DETAIL:ID:|LINK_AW:ID:)";
/*    */   protected static final String patternEnd = "\\}";
/*    */   
/*    */   public class ReplacerIterator
/*    */     extends PatternIterator
/*    */   {
/*    */     public ReplacerIterator() {
/* 47 */       super(SIODocumentFacade.this.document, "/?\\{(IMAGE:FILE:|LINK_CELL:ID:|LINK_SIO:ID:|STYLE_SHEET:FILE:|LINK_IMAGE:ID:|LINK_DETAIL:ID:|LINK_AW:ID:)", "\\}");
/*    */     }
/*    */     
/*    */     public Object next() {
/* 51 */       Object retValue = null;
/* 52 */       String tmp = (String)super.next();
/* 53 */       if (tmp.indexOf("IMAGE:FILE:") != -1) {
/* 54 */         retValue = new ImageFileReplacer(tmp);
/* 55 */       } else if (tmp.indexOf("LINK_IMAGE:ID:") != -1) {
/* 56 */         retValue = new ImageReplacer(tmp);
/* 57 */       } else if (tmp.indexOf("LINK_SIO:ID:") != -1) {
/* 58 */         retValue = new SIODocumentLinkReplacer(tmp);
/* 59 */       } else if (tmp.indexOf("STYLE_SHEET:FILE:") != -1) {
/* 60 */         retValue = new StyleSheetReplacer(tmp);
/* 61 */       } else if (tmp.indexOf("LINK_DETAIL:ID:") != -1) {
/* 62 */         retValue = new DetailViewReplacer(tmp);
/* 63 */       } else if (tmp.indexOf("LINK_AW:ID:") != -1) {
/* 64 */         retValue = new AWLinkReplacer(tmp);
/* 65 */       } else if (tmp.indexOf("LINK_CELL:ID:") != -1) {
/* 66 */         retValue = new SIOCellLinkReplacer(tmp);
/*    */       } else {
/* 68 */         SIODocumentFacade.log.warn("unknown replacer: " + tmp);
/* 69 */         retValue = new UnknownReplacer(tmp);
/*    */       } 
/* 71 */       return retValue;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SIODocumentFacade(StringBuffer document) {
/* 78 */     super(document);
/*    */   }
/*    */   
/*    */   public ReplacerIterator iterator() {
/* 82 */     return new ReplacerIterator();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\document\SIODocumentFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */