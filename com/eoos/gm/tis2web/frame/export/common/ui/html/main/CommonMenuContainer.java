/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
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
/*    */ public class CommonMenuContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(CommonMenuContainer.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 29 */       template = ApplicationContext.getInstance().loadFile(CommonMenuContainer.class, "commonmenucontainer.html", null).toString();
/* 30 */     } catch (Exception e) {
/* 31 */       log.error("error loading template - error:" + e, e);
/* 32 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected HtmlCodeRenderer contentElement;
/*    */   
/*    */   private TopLinksMenu topLinksMenu;
/*    */   
/*    */   private ModuleSelectionMenu moduleSelectionMenu;
/*    */ 
/*    */   
/*    */   public CommonMenuContainer(ClientContext context, HtmlCodeRenderer contentElement, List topLinks, MainPage main) {
/* 48 */     this.context = context;
/* 49 */     this.contentElement = contentElement;
/*    */     
/* 51 */     this.topLinksMenu = new TopLinksMenu(this.context, topLinks, main);
/* 52 */     addElement((HtmlElement)this.topLinksMenu);
/*    */     
/* 54 */     this.moduleSelectionMenu = new ModuleSelectionMenu(this.context, main) {
/*    */         protected HtmlElement getRemainder() {
/* 56 */           return (HtmlElement)CommonMenuContainer.this.topLinksMenu;
/*    */         }
/*    */       };
/* 59 */     addElement((HtmlElement)this.moduleSelectionMenu);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 64 */     StringBuffer code = new StringBuffer(template);
/*    */ 
/*    */     
/* 67 */     StringUtilities.replace(code, "{MODULE_SELECTION_MENU}", this.moduleSelectionMenu.getHtmlCode(params));
/* 68 */     StringUtilities.replace(code, "{MODULE_UI}", this.contentElement.getHtmlCode(params));
/*    */     
/* 70 */     return code.toString();
/*    */   }
/*    */   
/*    */   public ModuleSelectionMenu getModuleSelectionMenu() {
/* 74 */     return this.moduleSelectionMenu;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\CommonMenuContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */