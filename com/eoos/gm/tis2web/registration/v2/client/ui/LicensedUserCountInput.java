/*    */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LicensedUserCountInput
/*    */   extends ContextualElementContainerBase
/*    */ {
/*    */   private TextInputElement ieCount;
/*    */   private static final String TEMPLATE = "<table class=\"structural\" ><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table>";
/*    */   
/*    */   public LicensedUserCountInput(ClientContext context) {
/* 17 */     super(context);
/* 18 */     this.ieCount = new TextInputElement(context.createID());
/* 19 */     addElement((HtmlElement)this.ieCount);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 23 */     StringBuffer ret = new StringBuffer("<table class=\"structural\" ><tr><th>{LABEL}:</th><td>{INPUT}</td></tr></table>");
/* 24 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("licensed.user.count"));
/* 25 */     StringUtilities.replace(ret, "{INPUT}", this.ieCount.getHtmlCode(params));
/* 26 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 30 */     Integer ret = null;
/*    */     try {
/* 32 */       ret = Integer.valueOf((String)this.ieCount.getValue());
/* 33 */     } catch (NumberFormatException e) {}
/*    */     
/* 35 */     return ret;
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 39 */     if (value != null && value instanceof Integer)
/* 40 */       this.ieCount.setValue(value.toString()); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\LicensedUserCountInput.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */