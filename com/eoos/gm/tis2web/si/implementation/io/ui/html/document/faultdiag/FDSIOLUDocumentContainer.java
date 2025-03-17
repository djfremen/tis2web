/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.SIOLUDocumentContainer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
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
/*    */ 
/*    */ 
/*    */ public class FDSIOLUDocumentContainer
/*    */   extends SIOLUDocumentContainer
/*    */ {
/*    */   public static class FDSIOLUContainerCreator
/*    */     implements DocumentPage.SIOLUContainerCreator
/*    */   {
/*    */     public DocumentContainer create(ClientContext context, SIOLU sio, DocumentPage page) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 32 */       return (DocumentContainer)new FDSIOLUDocumentContainer(context, sio, page);
/*    */     }
/*    */   }
/*    */   
/*    */   public FDSIOLUDocumentContainer(ClientContext context, SIOLU node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 37 */     super(context, node, docPage);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\FDSIOLUDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */