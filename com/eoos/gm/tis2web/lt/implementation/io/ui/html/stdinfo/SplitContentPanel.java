/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.LTViewHook;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc.TocIFramePanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SplitContentPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(SplitContentPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 29 */       template = ApplicationContext.getInstance().loadFile(SplitContentPanel.class, "splitcontentpanel.html", null).toString();
/* 30 */     } catch (Exception e) {
/* 31 */       log.error("unable to load template - error:" + e, e);
/* 32 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private TocIFramePanel tocPanel;
/*    */ 
/*    */   
/*    */   private LTViewHook docPanel;
/*    */   
/*    */   private RatioSelection ratioSelection;
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public SplitContentPanel(ClientContext context) {
/* 49 */     this.context = context;
/*    */     
/* 51 */     this.tocPanel = new TocIFramePanel(context, 1);
/* 52 */     addElement((HtmlElement)this.tocPanel);
/*    */     
/* 54 */     this.docPanel = new LTViewHook(context);
/* 55 */     addElement((HtmlElement)this.docPanel);
/*    */     
/* 57 */     this.ratioSelection = new RatioSelection(this);
/* 58 */     addElement((HtmlElement)this.ratioSelection);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ClientContext getContext() {
/* 65 */     return this.context;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 69 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 71 */     StringUtilities.replace(code, "{IFRAME_TOC}", this.tocPanel.getHtmlCode(params));
/* 72 */     StringUtilities.replace(code, "{IFRAME_DOC}", this.docPanel.getHtmlCode(params));
/*    */     
/* 74 */     Pair pair = (Pair)this.ratioSelection.getValue();
/* 75 */     StringUtilities.replace(code, "{WIDTH_TOC}", pair.getFirst().toString() + "%");
/* 76 */     StringUtilities.replace(code, "{WIDTH_DOC}", pair.getSecond().toString() + "%");
/*    */ 
/*    */ 
/*    */     
/* 80 */     StringUtilities.replace(code, "{HEIGHT_IFRAME}", LTUIContext.getInstance(this.context).getDisplayHeight().toString() + "px");
/*    */     
/* 82 */     StringUtilities.replace(code, "{TEXT_RATIO_SELECTION}", this.context.getMessage("si.stdinfo.select.ratio"));
/* 83 */     StringUtilities.replace(code, "{SELECTION_RATIO}", this.ratioSelection.getHtmlCode(params));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 88 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\SplitContentPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */