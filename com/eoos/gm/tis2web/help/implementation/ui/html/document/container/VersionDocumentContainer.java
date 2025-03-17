/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.version.VersionDocument;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class VersionDocumentContainer
/*    */   extends DocumentContainer
/*    */ {
/* 16 */   private String styleSheet = null;
/*    */   
/* 18 */   private String title = null;
/*    */   
/* 20 */   private String encoding = null;
/*    */   
/* 22 */   private VersionDocument document = null;
/*    */   
/*    */   private DocumentPage docPage;
/*    */   
/*    */   private SIO sio;
/*    */   
/*    */   public VersionDocumentContainer(ClientContext context, IIOElement node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 29 */     super(context);
/* 30 */     this.sio = (SIO)node;
/* 31 */     this.docPage = docPage;
/* 32 */     this.encoding = "utf-8";
/* 33 */     LocaleInfo li = SharedContext.getInstance(context).getLocaleInfo();
/* 34 */     this.title = this.sio.getLabel(li);
/* 35 */     this.document = new VersionDocument(context, this.title);
/*    */   }
/*    */   
/*    */   public synchronized String getHtmlCode(Map params) {
/* 39 */     return this.document.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public synchronized String getStyleSheet() {
/* 43 */     return this.styleSheet;
/*    */   }
/*    */   
/*    */   public synchronized String getTitle() {
/* 47 */     return this.title;
/*    */   }
/*    */   
/*    */   public synchronized String getEncoding() {
/* 51 */     return this.encoding;
/*    */   }
/*    */   
/*    */   public synchronized DocumentPage getDocumentPage() {
/* 55 */     return this.docPage;
/*    */   }
/*    */   
/*    */   public SIO getSIO() {
/* 59 */     return this.sio;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\VersionDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */