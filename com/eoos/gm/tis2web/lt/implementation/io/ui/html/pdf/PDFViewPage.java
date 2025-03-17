/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.pdf;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.DynamicResourceController;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.IFrameElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
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
/*    */ 
/*    */ 
/*    */ public class PDFViewPage
/*    */   extends Page
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(PDFViewPage.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 31 */       template = ApplicationContext.getInstance().loadFile(PDFViewPage.class, "pdfviewpage.html", null).toString();
/* 32 */     } catch (Exception e) {
/* 33 */       log.error("unable to load template - error:" + e, e);
/* 34 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClickButtonElement backButton;
/*    */   
/*    */   private String dataURL;
/*    */   
/*    */   public boolean closeButton = false;
/*    */   
/*    */   private IFrameElement iFrame;
/*    */   
/*    */   public PDFViewPage(final ClientContext context, byte[] data) {
/* 48 */     super(context);
/*    */     
/* 50 */     this.backButton = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 52 */           return context.getLabel("back");
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 56 */           return MainPage.getInstance(context);
/*    */         }
/*    */       };
/* 59 */     addElement((HtmlElement)this.backButton);
/*    */     
/* 61 */     if (data != null) {
/* 62 */       String type = "application/pdf";
/* 63 */       this.dataURL = DynamicResourceController.getInstance(context).getURL(data, type, context.createID());
/*    */     } else {
/* 65 */       this.dataURL = "";
/*    */     } 
/*    */     
/* 68 */     final String url = this.dataURL;
/* 69 */     this.iFrame = new IFrameElement("pdf")
/*    */       {
/*    */         protected String getHeight() {
/* 72 */           return "90%";
/*    */         }
/*    */         
/*    */         protected String getSourceURL(Map params) {
/* 76 */           return url;
/*    */         }
/*    */         
/*    */         protected String getWidth() {
/* 80 */           return "100%";
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 88 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 90 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", this.backButton.getHtmlCode(params));
/*    */ 
/*    */ 
/*    */     
/* 94 */     StringUtilities.replace(code, "{IFRAME_CODE}", this.iFrame.getHtmlCode(params));
/* 95 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\pdf\PDFViewPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */