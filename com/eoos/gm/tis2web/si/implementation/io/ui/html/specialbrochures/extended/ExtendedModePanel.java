/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.extended;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.DocumentPageRetrieval;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.DocumentPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedModePanel
/*    */   extends HtmlElementContainerBase
/*    */   implements DocumentPageRetrieval
/*    */ {
/*    */   private static String template;
/*    */   protected ClientContext context;
/*    */   protected TOCIFrame tocFrame;
/*    */   protected DocumentIFrame docFrame;
/*    */   protected RatioSelection ieRatio;
/*    */   
/*    */   static {
/*    */     try {
/* 28 */       template = ApplicationContext.getInstance().loadFile(ExtendedModePanel.class, "extendedmodepanel.html", null).toString();
/* 29 */     } catch (Exception e) {
/* 30 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExtendedModePanel(ClientContext context) {
/* 40 */     this.context = context;
/*    */     
/* 42 */     this.tocFrame = new TOCIFrame(context, 1);
/* 43 */     addElement((HtmlElement)this.tocFrame);
/*    */     
/* 45 */     this.docFrame = new DocumentIFrame(context);
/* 46 */     addElement((HtmlElement)this.docFrame);
/*    */     
/* 48 */     this.ieRatio = new RatioSelection(context);
/* 49 */     addElement((HtmlElement)this.ieRatio);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 53 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 55 */     StringUtilities.replace(code, "{TOCIFRAME}", this.tocFrame.getHtmlCode(params));
/* 56 */     StringUtilities.replace(code, "{DOCIFRAME}", this.docFrame.getHtmlCode(params));
/*    */     
/* 58 */     StringUtilities.replace(code, "{LABEL_RATIO_SELECTION}", this.context.getMessage("select.ratio"));
/* 59 */     StringUtilities.replace(code, "{RATIO_SELECTION}", this.ieRatio.getHtmlCode(params));
/*    */     
/* 61 */     Pair ratio = (Pair)this.ieRatio.getValue();
/* 62 */     StringUtilities.replace(code, "{RATIO1}", (String)ratio.getFirst());
/* 63 */     StringUtilities.replace(code, "{RATIO2}", (String)ratio.getSecond());
/*    */     
/* 65 */     return code.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 73 */     return this.docFrame.getDocumentPage();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\extended\ExtendedModePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */