/*    */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class RequestIDDisplayElement
/*    */   extends ContextualElementContainerBase
/*    */ {
/*    */   private static final String TEMPLATE = "<table class=\"structural\"><tr><th>{LABEL}:</th><td>{OUTPUT}</td></tr></table>";
/*    */   private ClientContext context;
/*    */   private TextInputElement inputRequestID;
/*    */   
/*    */   public RequestIDDisplayElement(ClientContext context) {
/* 19 */     super(context);
/* 20 */     this.context = context;
/* 21 */     this.inputRequestID = new TextInputElement(context.createID());
/* 22 */     addElement((HtmlElement)this.inputRequestID);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 26 */     StringBuffer ret = new StringBuffer("<table class=\"structural\"><tr><th>{LABEL}:</th><td>{OUTPUT}</td></tr></table>");
/* 27 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("request.id"));
/* 28 */     StringUtilities.replace(ret, "{OUTPUT}", this.inputRequestID.getHtmlCode(params));
/* 29 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public void setValue(Object requestID) {
/* 33 */     this.inputRequestID.setValue(requestID);
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 37 */     return this.inputRequestID.getValue();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\RequestIDDisplayElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */