/*    */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class SubscriberIDInputElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private static final String TEMPLATE = "<div id=\"subscriberid\"><table><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table></div>";
/*    */   private ClientContext context;
/*    */   private TextInputElement inputID;
/*    */   
/*    */   public SubscriberIDInputElement(ClientContext context) {
/* 19 */     this.context = context;
/*    */     
/* 21 */     this.inputID = new TextInputElement(context.createID(), 30, 30);
/* 22 */     addElement((HtmlElement)this.inputID);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 26 */     StringBuffer ret = new StringBuffer("<div id=\"subscriberid\"><table><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table></div>");
/* 27 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("subscriber.id"));
/* 28 */     StringUtilities.replace(ret, "{INPUT}", this.inputID.getHtmlCode(params));
/* 29 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 33 */     return this.inputID.getValue();
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 37 */     this.inputID.setValue(value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\SubscriberIDInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */