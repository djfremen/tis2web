/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ public class HtmlDisplayRequestCodeImpl
/*    */   extends ConfirmationRequestImpl
/*    */   implements HtmlDisplayRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String htmlCode;
/*    */   
/*    */   public HtmlDisplayRequestCodeImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, String htmlCode) {
/* 17 */     super(requestGroup, attribute, confirmationValue);
/* 18 */     this.htmlCode = htmlCode;
/*    */   }
/*    */   
/*    */   public String getHtmlCode() {
/* 22 */     return this.htmlCode;
/*    */   }
/*    */   
/*    */   public String getURL() {
/* 26 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\HtmlDisplayRequestCodeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */