/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.html.element.HtmlLayout;
/*    */ import com.eoos.util.StringUtilities;
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
/*    */ public abstract class RefDocDialogBase
/*    */   extends Page
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(RefDocDialogBase.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 29 */       template = ApplicationContext.getInstance().loadFile(RefDocDialogBase.class, "dialog.html", null).toString();
/* 30 */     } catch (Exception e) {
/* 31 */       log.error("unable to load template - error:" + e, e);
/* 32 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/* 36 */   private static DialogLayout layout = new DialogLayout() {
/*    */       public String getHorizontalAlignment() {
/* 38 */         return "center";
/*    */       }
/*    */       
/*    */       public String getWidth() {
/* 42 */         return null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public RefDocDialogBase(ClientContext context) {
/* 48 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 53 */     StringBuffer code = new StringBuffer(template);
/* 54 */     DialogLayout layout = (DialogLayout)getLayout();
/* 55 */     if (layout != null) {
/* 56 */       String hAlignment = layout.getHorizontalAlignment();
/* 57 */       if (hAlignment != null) {
/* 58 */         StringUtilities.replace(code, "{LAYOUT}", "align=\"" + hAlignment + "\" {LAYOUT}");
/*    */       }
/* 60 */       String width = layout.getWidth();
/* 61 */       if (width != null) {
/* 62 */         StringUtilities.replace(code, "{LAYOUT}", "width=\"" + width + "\" {LAYOUT}");
/*    */       }
/*    */     } 
/* 65 */     StringUtilities.replace(code, "{STYLE}", getTableStyle());
/*    */     
/* 67 */     StringUtilities.replace(code, "{LAYOUT}", "");
/* 68 */     StringUtilities.replace(code, "{TITLE}", getTitle(params));
/* 69 */     StringUtilities.replace(code, "{CONTENT}", getContent(params));
/* 70 */     StringUtilities.replace(code, "{TITLE_ALIGN}", getTitleAlign());
/*    */     
/* 72 */     return code.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HtmlLayout getLayout() {
/* 80 */     return (HtmlLayout)layout;
/*    */   }
/*    */   
/*    */   protected String getTitleAlign() {
/* 84 */     return "center";
/*    */   }
/*    */   
/*    */   protected String getTableStyle() {
/* 88 */     return "class=\"dialog\"";
/*    */   }
/*    */   
/*    */   protected abstract String getTitle(Map paramMap);
/*    */   
/*    */   protected abstract String getContent(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\RefDocDialogBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */