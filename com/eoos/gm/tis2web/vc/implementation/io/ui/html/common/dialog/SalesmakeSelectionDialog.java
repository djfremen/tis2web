/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.dialog;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*    */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.selectbox.SelectBox;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public abstract class SalesmakeSelectionDialog
/*    */   extends DialogBase
/*    */ {
/* 27 */   private static final Logger log = Logger.getLogger(SalesmakeSelectionDialog.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 32 */       template = ApplicationContext.getInstance().loadFile(SalesmakeSelectionDialog.class, "salesmakeselection.html", null).toString();
/* 33 */     } catch (Exception e) {
/* 34 */       log.error("could not load template - error:" + e, e);
/*    */     } 
/*    */   }
/*    */   
/* 38 */   private List salesmakes = null;
/*    */   
/* 40 */   private HtmlElement selectbox = null;
/*    */   
/* 42 */   private HtmlElement buttonOK = null;
/*    */ 
/*    */   
/*    */   public SalesmakeSelectionDialog(ClientContext context, List salesmakes) {
/* 46 */     super(context);
/* 47 */     this.salesmakes = salesmakes;
/* 48 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 52 */     this.selectbox = (HtmlElement)new SelectBox("salesmakeselection", this.salesmakes);
/* 53 */     addElement(this.selectbox);
/*    */     
/* 55 */     this.buttonOK = (HtmlElement)new ClickButtonElement(this.context.createID(), null) {
/*    */         public String getLabel() {
/* 57 */           return SalesmakeSelectionDialog.this.context.getLabel("button.ok");
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 61 */           return SalesmakeSelectionDialog.this.onOK();
/*    */         }
/*    */       };
/* 64 */     addElement(this.buttonOK);
/*    */   }
/*    */   
/*    */   protected String getTitle(Map params) {
/* 68 */     return this.context.getMessage("vc.message.select.salesmake");
/*    */   }
/*    */   
/*    */   protected String getContent(Map params) {
/* 72 */     StringBuffer code = new StringBuffer(template);
/* 73 */     StringUtilities.replace(code, "{CONTENT}", this.selectbox.getHtmlCode(params));
/* 74 */     StringUtilities.replace(code, "{BUTTON}", this.buttonOK.getHtmlCode(params));
/* 75 */     return code.toString();
/*    */   }
/*    */   
/*    */   public String getSelectedSalesMake() {
/* 79 */     String selection = (String)((SelectBox)this.selectbox).getValue();
/* 80 */     if (selection == null) {
/* 81 */       return null;
/*    */     }
/* 83 */     if (selection.length() == 0) {
/* 84 */       return null;
/*    */     }
/* 86 */     return selection;
/*    */   }
/*    */   
/*    */   protected abstract Object onOK();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\dialog\SalesmakeSelectionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */