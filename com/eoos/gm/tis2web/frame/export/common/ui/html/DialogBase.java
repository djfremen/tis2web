/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ public abstract class DialogBase
/*    */   extends Page
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(DialogBase.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 27 */       template = ApplicationContext.getInstance().loadFile(DialogBase.class, "dialog.html", null).toString();
/* 28 */     } catch (Exception e) {
/* 29 */       log.error("unable to load template - error:" + e, e);
/* 30 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/* 34 */   private static DialogLayout layout = new DialogLayout() {
/*    */       public String getHorizontalAlignment() {
/* 36 */         return "center";
/*    */       }
/*    */       
/*    */       public String getWidth() {
/* 40 */         return null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public DialogBase(ClientContext context) {
/* 46 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 51 */     StringBuffer code = new StringBuffer(template);
/* 52 */     DialogLayout layout = (DialogLayout)getLayout();
/* 53 */     if (layout != null) {
/* 54 */       String hAlignment = layout.getHorizontalAlignment();
/* 55 */       if (hAlignment != null) {
/* 56 */         StringUtilities.replace(code, "{LAYOUT}", "align=\"" + hAlignment + "\" {LAYOUT}");
/*    */       }
/* 58 */       String width = layout.getWidth();
/* 59 */       if (width != null) {
/* 60 */         StringUtilities.replace(code, "{LAYOUT}", "width=\"" + width + "\" {LAYOUT}");
/*    */       }
/*    */     } 
/* 63 */     StringUtilities.replace(code, "{LAYOUT}", "");
/* 64 */     StringUtilities.replace(code, "{TITLE}", getTitle(params));
/* 65 */     StringUtilities.replace(code, "{CONTENT}", getContent(params));
/* 66 */     StringUtilities.replace(code, "{TITLE_ALIGN}", getTitleAlign());
/* 67 */     StringUtilities.replace(code, "{CONTENT_ALIGN}", getContentAlign());
/*    */     
/* 69 */     String id = getID();
/* 70 */     if (id == null) {
/* 71 */       StringUtilities.replace(code, "{ID}", "");
/*    */     } else {
/* 73 */       StringUtilities.replace(code, "{ID}", "id=\"" + id + "\"");
/*    */     } 
/*    */     
/* 76 */     return code.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HtmlLayout getLayout() {
/* 84 */     return layout;
/*    */   }
/*    */   
/*    */   protected String getTitleAlign() {
/* 88 */     return "center";
/*    */   }
/*    */   
/*    */   protected String getContentAlign() {
/* 92 */     return "center";
/*    */   }
/*    */   
/*    */   protected String getID() {
/* 96 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract String getTitle(Map paramMap);
/*    */   
/*    */   protected abstract String getContent(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\DialogBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */