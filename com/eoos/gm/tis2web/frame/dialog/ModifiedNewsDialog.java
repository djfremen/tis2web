/*    */ package com.eoos.gm.tis2web.frame.dialog;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ModifiedNewsDialog
/*    */   extends Page
/*    */ {
/* 19 */   private static final String IDS = ModifiedNewsDialog.class.getName();
/*    */   
/* 21 */   private static Logger log = Logger.getLogger(IDS);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(ModifiedNewsDialog.class, "modifiednews.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClickButtonElement ieOK;
/*    */   
/*    */   public ModifiedNewsDialog(final ClientContext context) {
/* 37 */     super(context);
/*    */     
/* 39 */     this.ieOK = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 41 */           return context.getLabel("ok");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 45 */           return ModifiedNewsDialog.this.onClose(submitParams);
/*    */         }
/*    */       };
/*    */     
/* 49 */     addElement((HtmlElement)this.ieOK);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 56 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 58 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.modified.news"));
/* 59 */     StringUtilities.replace(code, "{BUTTON_OK}", this.ieOK.getHtmlCode(params));
/* 60 */     return code.toString();
/*    */   }
/*    */   
/*    */   protected abstract ResultObject onClose(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dialog\ModifiedNewsDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */