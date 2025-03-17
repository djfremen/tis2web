/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkDialog;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkListElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ public class CprList
/*    */   extends LinkListElement
/*    */ {
/*    */   private ClientContext context;
/*    */   private HtmlLabel header;
/*    */   private LocaleInfo locale;
/*    */   
/*    */   public CprList(ClientContext context, SIOWD sioCpr) {
/* 38 */     super(context, (sioCpr.getRelatedCheckingProcedures() != null) ? sioCpr.getRelatedCheckingProcedures() : new LinkedList(), (SIO)sioCpr);
/* 39 */     setDataCallback((DataRetrievalAbstraction.DataCallback)this);
/* 40 */     this.context = context;
/* 41 */     this.header = new HtmlLabel(context.getLabel("wd.Diagnostic.Tests"));
/* 42 */     this.locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getColumnCount() {
/* 50 */     return 1;
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 54 */     if (data instanceof SIOCPR) {
/*    */       
/* 56 */       final SIOCPR node = (SIOCPR)data;
/* 57 */       return (HtmlElement)new LinkElement(this.context.createID(), null) {
/*    */           protected String getLabel() {
/* 59 */             return node.getElectronicSystemLabel(CprList.this.locale);
/*    */           }
/*    */           
/*    */           public Object onClick(Map submitParams) {
/* 63 */             CprList.this.setDocument(node);
/* 64 */             return CprList.this.page; }
/*    */         };
/*    */     } 
/* 67 */     if (data instanceof SIO) {
/* 68 */       SIO sio = (SIO)data;
/* 69 */       return (HtmlElement)new HtmlLabel("noLink: sio=" + sio.getID());
/*    */     } 
/* 71 */     return (HtmlElement)new HtmlLabel("noLink: (no sio) -> " + data.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement getHeader(int columnIndex) {
/* 76 */     return (HtmlElement)this.header;
/*    */   }
/*    */   
/*    */   protected boolean enableHeader() {
/* 80 */     return true;
/*    */   }
/*    */   
/*    */   public void setDocument(Object obj) {
/* 84 */     if (obj instanceof SIOCPR)
/*    */     {
/* 86 */       setDocument((SIOCPR)obj);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setDocument(SIOCPR node) {
/* 91 */     this.stack.push((HtmlElement)new CprContainer(this.context, (SIO)node, (CprPage)this.page));
/* 92 */     this.page.setTitle(node.getElectronicSystemLabel(this.locale));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\wd\CprList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */