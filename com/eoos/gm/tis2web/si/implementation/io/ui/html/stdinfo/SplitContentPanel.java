/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserver;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextObserverAdapter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentIFrame;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.toc.TocIFramePanel;
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
/* 26 */   private static final Logger log = Logger.getLogger(SplitContentPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 31 */       template = ApplicationContext.getInstance().loadFile(SplitContentPanel.class, "splitcontentpanel.html", null).toString();
/* 32 */     } catch (Exception e) {
/* 33 */       log.error("unable to load template - error:" + e, e);
/* 34 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private TocIFramePanel tocPanel;
/*    */   
/*    */   private DocumentIFrame docPanel;
/*    */   
/*    */   private RatioSelection ratioSelection;
/* 44 */   private Integer height = null;
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public SplitContentPanel(ClientContext context) {
/* 51 */     this.context = context;
/*    */     
/* 53 */     this.tocPanel = new TocIFramePanel(context, 1);
/* 54 */     addElement((HtmlElement)this.tocPanel);
/*    */     
/* 56 */     this.docPanel = new DocumentIFrame(context);
/* 57 */     addElement((HtmlElement)this.docPanel);
/*    */     
/* 59 */     this.ratioSelection = new RatioSelection(this);
/* 60 */     addElement((HtmlElement)this.ratioSelection);
/*    */     
/* 62 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/* 63 */     this.height = scp.getDisplayHeight();
/* 64 */     scp.addObserver((SharedContextObserver)new SharedContextObserverAdapter() {
/*    */           public void onDisplayHeightChange(Integer oldHeight, Integer newHeight) {
/* 66 */             SplitContentPanel.this.height = newHeight;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext getContext() {
/* 73 */     return this.context;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 77 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 79 */     StringUtilities.replace(code, "{IFRAME_TOC}", this.tocPanel.getHtmlCode(params));
/* 80 */     StringUtilities.replace(code, "{IFRAME_DOC}", this.docPanel.getHtmlCode(params));
/*    */     
/* 82 */     Pair pair = (Pair)this.ratioSelection.getValue();
/* 83 */     if (pair == null || pair.getFirst() == null) {
/* 84 */       pair = this.ratioSelection.getDefaultValue();
/*    */     }
/* 86 */     StringUtilities.replace(code, "{WIDTH_TOC}", pair.getFirst().toString() + "%");
/* 87 */     StringUtilities.replace(code, "{WIDTH_DOC}", pair.getSecond().toString() + "%");
/*    */     
/* 89 */     StringUtilities.replace(code, "{HEIGHT_IFRAME}", String.valueOf(this.height) + "px");
/*    */     
/* 91 */     StringUtilities.replace(code, "{TEXT_RATIO_SELECTION}", this.context.getMessage("si.stdinfo.select.ratio"));
/* 92 */     StringUtilities.replace(code, "{SELECTION_RATIO}", this.ratioSelection.getHtmlCode(params));
/*    */     
/* 94 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\stdinfo\SplitContentPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */