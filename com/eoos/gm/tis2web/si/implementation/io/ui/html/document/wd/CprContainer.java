/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.CprBackUIIconLink;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.ProtocolUIIconLink;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol.ProtocolDialog;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.TimezoneOffsetInputElement;
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
/*    */ 
/*    */ public class CprContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(CprContainer.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 35 */       template = ApplicationContext.getInstance().loadFile(CprContainer.class, "cprContainer.html", null).toString();
/* 36 */     } catch (Exception e) {
/* 37 */       log.error("unable to load template - error:" + e, e);
/* 38 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private CprPage page;
/*    */   
/*    */   private CprlIFrame docCont;
/*    */   
/*    */   private ProtocolUIIconLink protocolLink;
/*    */   
/*    */   private OptionsLink optionsLink;
/*    */   
/*    */   private TimezoneOffsetInputElement timezoneDiff;
/*    */   
/*    */   CprBackUIIconLink backLink;
/*    */ 
/*    */   
/*    */   public CprContainer(ClientContext context, SIO docCont, CprPage page) {
/* 57 */     ProtocolDialog.Callback callback = new ProtocolDialog.Callback() {
/*    */         public Object onReturn(Map submitParams) {
/* 59 */           return CprContainer.this.page;
/*    */         }
/*    */       };
/* 62 */     this.protocolLink = new ProtocolUIIconLink(context, callback);
/* 63 */     this.protocolLink.setDisabled(new Boolean(false));
/* 64 */     this.protocolLink.setHistory(DocumentPage.getInstance(context).getCprHistory());
/* 65 */     addElement((HtmlElement)this.protocolLink);
/*    */     
/* 67 */     DocumentPage dp = new DocumentPage(context);
/* 68 */     this.backLink = new CprBackUIIconLink(context, dp);
/* 69 */     this.backLink.setDisabled(new Boolean(false));
/* 70 */     this.backLink.setHistory(DocumentPage.getInstance(context).getCprHistory());
/* 71 */     addElement((HtmlElement)this.backLink);
/*    */     
/* 73 */     this.optionsLink = new OptionsLink(context, (Page)page);
/* 74 */     this.optionsLink.setNode(docCont);
/*    */     
/* 76 */     addElement((HtmlElement)this.optionsLink);
/* 77 */     this.page = page;
/* 78 */     this.docCont = new CprlIFrame(context, docCont, dp);
/* 79 */     this.timezoneDiff = new TimezoneOffsetInputElement(context.createID());
/* 80 */     addElement((HtmlElement)this.timezoneDiff);
/* 81 */     this.protocolLink.setTimezoneDiff(this.timezoneDiff);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 85 */     StringBuffer code = new StringBuffer(template);
/* 86 */     StringUtilities.replace(code, "{protocolLink}", this.protocolLink.getHtmlCode(params));
/* 87 */     StringUtilities.replace(code, "{optionsLink}", this.optionsLink.getHtmlCode(params));
/* 88 */     StringUtilities.replace(code, "{backLink}", this.backLink.getHtmlCode(params));
/*    */     
/* 90 */     StringUtilities.replace(code, "{DOCUMENT}", this.docCont.getHtmlCode(params));
/* 91 */     StringUtilities.replace(code, "{timezoneDiff}", this.timezoneDiff.getHtmlCode(params));
/* 92 */     return code.toString();
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 96 */     this.docCont.unregister();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\wd\CprContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */