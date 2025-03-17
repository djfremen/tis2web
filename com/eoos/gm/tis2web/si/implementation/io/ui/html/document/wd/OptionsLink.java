/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.OptionsUIIconLink;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import com.eoos.html.ResultObject;
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
/*    */ public class OptionsLink
/*    */   extends OptionsUIIconLink
/*    */ {
/*    */   private Page page;
/*    */   
/*    */   public OptionsLink(ClientContext context, Page page) {
/* 26 */     super(context);
/* 27 */     this.page = page;
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 31 */     if (this.curSysCode != null) {
/*    */       try {
/* 33 */         ILVCAdapter.ReturnHandler returnHandler = new ILVCAdapter.ReturnHandler()
/*    */           {
/*    */             public Object onOK() {
/* 36 */               DocumentPage.getInstance(OptionsLink.this.context).onOptionsChanged();
/* 37 */               return OptionsLink.this.page.getHtmlCode(null);
/*    */             }
/*    */             
/*    */             public Object onCancel() {
/* 41 */               return OptionsLink.this.page.getHtmlCode(null);
/*    */             }
/*    */           };
/*    */         
/* 45 */         return new ResultObject(0, getLVCAdapter().getVehicleOptionsDialog(this.context.getSessionID(), this.nodeList, returnHandler));
/* 46 */       } catch (Exception e) {
/* 47 */         return new ResultObject(9, new Object());
/*    */       } 
/*    */     }
/* 50 */     return new ResultObject(9, new Object());
/*    */   }
/*    */ 
/*    */   
/*    */   private ILVCAdapter getLVCAdapter() {
/* 55 */     return SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\wd\OptionsLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */