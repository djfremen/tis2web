/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SITFilterWidget
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 18 */   private static String template = "<table id=\"sitfilterwidget\"><tr><td>{LABEL}</td><td>:</td><td>{SELECTBOX}</td></tr></table>";
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private SITSelectBox selectBox;
/*    */ 
/*    */   
/*    */   public SITFilterWidget(ClientContext context) {
/* 26 */     this.context = context;
/*    */     
/* 28 */     this.selectBox = new SITSelectBox(context);
/* 29 */     addElement((HtmlElement)this.selectBox);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 33 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 35 */     StringUtilities.replace(code, "{LABEL}", this.context.getLabel("filter"));
/* 36 */     StringUtilities.replace(code, "{SELECTBOX}", this.selectBox.getHtmlCode(params));
/*    */     
/* 38 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\SITFilterWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */