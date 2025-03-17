/*    */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SubscriberIDInputElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private static final String TEMPLATE = "<table class=\"structural\"><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table>";
/*    */   private ClientContext context;
/*    */   private TextInputElement inputID;
/*    */   
/*    */   public SubscriberIDInputElement(ClientContext context) {
/* 18 */     this.context = context;
/* 19 */     this.inputID = new TextInputElement(context.createID(), 10, 30);
/* 20 */     addElement((HtmlElement)this.inputID);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 24 */     StringBuffer ret = new StringBuffer("<table class=\"structural\"><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table>");
/* 25 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("subscriber.id"));
/* 26 */     StringUtilities.replace(ret, "{INPUT}", this.inputID.getHtmlCode(params));
/* 27 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 31 */     return this.inputID.getValue();
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 35 */     this.inputID.setValue(value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\SubscriberIDInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */