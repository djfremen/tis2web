/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ public class HtmlDisplayRequestURLImpl
/*    */   extends ConfirmationRequestImpl
/*    */   implements HtmlDisplayRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String url;
/*    */   
/*    */   public HtmlDisplayRequestURLImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, String url) {
/* 17 */     super(requestGroup, attribute, confirmationValue);
/* 18 */     this.url = url;
/*    */   }
/*    */   
/*    */   public String getHtmlCode() {
/* 22 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public String getURL() {
/* 26 */     return this.url;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\HtmlDisplayRequestURLImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */