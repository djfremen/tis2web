/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.document.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.document.Document;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DocumentPage
/*    */   extends Page
/*    */ {
/* 22 */   private Document doc = null;
/*    */   
/* 24 */   private ResultObject docResult = null;
/*    */ 
/*    */   
/*    */   private DocumentPage(ClientContext context) {
/* 28 */     super(context);
/*    */   }
/*    */   
/*    */   public static DocumentPage getInstance(ClientContext context) {
/* 32 */     synchronized (context.getLockObject()) {
/* 33 */       DocumentPage instance = (DocumentPage)context.getObject(DocumentPage.class);
/* 34 */       if (instance == null) {
/* 35 */         instance = new DocumentPage(context);
/* 36 */         context.storeObject(DocumentPage.class, instance);
/*    */       } 
/* 38 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getURL(Map params) {
/* 43 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*    */     
/* 45 */     String bookmark = null;
/* 46 */     CTOCNode node = (CTOCNode)TocTree.getInstance((ClientContext)this.context).getSelectedNode();
/* 47 */     if (node != null && node.hasProperty((SITOCProperty)CTOCProperty.LU)) {
/*    */ 
/*    */       
/* 50 */       bookmark = (String)node.getProperty((SITOCProperty)CTOCProperty.LU);
/*    */       
/* 52 */       if (bookmark != null) {
/* 53 */         int iIdx = bookmark.indexOf('#');
/* 54 */         if (iIdx > -1) {
/* 55 */           bookmark = bookmark.substring(iIdx);
/*    */         } else {
/* 57 */           bookmark = null;
/*    */         } 
/*    */       } 
/*    */     } 
/* 61 */     if (bookmark != null) {
/* 62 */       url = url + bookmark;
/*    */     }
/* 64 */     return url;
/*    */   }
/*    */   
/*    */   public ResultObject getPage(Map params) {
/* 68 */     synchronized (this.context) {
/*    */       
/* 70 */       CTOCNode node = (CTOCNode)TocTree.getInstance((ClientContext)this.context).getSelectedNode();
/* 71 */       if (node != null && node.hasProperty((SITOCProperty)CTOCProperty.LU)) {
/* 72 */         String oN = (String)node.getProperty((SITOCProperty)CTOCProperty.LU);
/* 73 */         Integer sio = null;
/* 74 */         int iIdx = oN.indexOf('#');
/* 75 */         if (iIdx > -1) {
/* 76 */           sio = Integer.valueOf(oN.substring(0, iIdx));
/*    */         } else {
/* 78 */           sio = Integer.valueOf(oN);
/*    */         } 
/* 80 */         if (this.doc == null || (this.doc != null && !this.doc.getKey().equals(Document.buildKey(sio, LTClientContext.getInstance((ClientContext)this.context).getLC())))) {
/*    */           
/* 82 */           this.doc = new Document((ClientContext)this.context, sio.intValue());
/*    */ 
/*    */ 
/*    */           
/* 86 */           this.docResult = this.doc.buildResultObject(params);
/*    */         } 
/*    */         
/* 89 */         if (this.docResult != null) {
/* 90 */           return this.docResult;
/*    */         }
/*    */       } 
/* 93 */       return new ResultObject(0, getHtmlCode(params));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 98 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\document\page\DocumentPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */