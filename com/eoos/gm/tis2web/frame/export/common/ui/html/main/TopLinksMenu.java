/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.HtmlInputElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class TopLinksMenu
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(TopLinksMenu.class);
/*    */   public static final String KEY_CONTEXT = "context";
/*    */   private static final String template;
/*    */   private List menuItems;
/*    */   
/*    */   static {
/*    */     try {
/* 33 */       template = ApplicationContext.getInstance().loadFile(TopLinksMenu.class, "toplinksmenu.html", null).toString();
/* 34 */     } catch (Exception e) {
/* 35 */       log.error("unable to load template - error:" + e, e);
/* 36 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected MainPage main;
/*    */   
/*    */   private static final String TEMPLATE_HOSTINFO = "<span>{USER}@{IP}:{PORT}</span>";
/*    */ 
/*    */   
/*    */   public TopLinksMenu(ClientContext context, List menuItems, MainPage main) {
/* 49 */     this.context = context;
/* 50 */     this.main = main;
/*    */     
/* 52 */     this.menuItems = (menuItems != null) ? menuItems : new ArrayList(0);
/* 53 */     for (int i = 0; i < this.menuItems.size(); i++) {
/* 54 */       addElement((HtmlElement)this.menuItems.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 62 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 64 */     if (this.context.isSpecialAccess()) {
/* 65 */       StringBuffer tmp = new StringBuffer("<span>{USER}@{IP}:{PORT}</span>");
/* 66 */       StringUtilities.replace(tmp, "{USER}", this.context.getSessionID());
/* 67 */       StringUtilities.replace(tmp, "{NAME}", ApplicationContext.getInstance().getHostName());
/* 68 */       StringUtilities.replace(tmp, "{IP}", ApplicationContext.getInstance().getIPAddr());
/* 69 */       StringUtilities.replace(tmp, "{PORT}", String.valueOf(ApplicationContext.getInstance().getPort()));
/*    */       
/* 71 */       StringUtilities.replace(code, "{ACTIONS}", tmp.append("&nbsp;&nbsp;{ACTIONS}").toString());
/*    */     } 
/*    */     
/* 74 */     Iterator<LinkElement> iter = this.menuItems.iterator();
/* 75 */     while (iter.hasNext()) {
/* 76 */       LinkElement link = iter.next();
/* 77 */       StringUtilities.replace(code, "{ACTIONS}", link.getHtmlCode(params) + "&nbsp;&nbsp;{ACTIONS}");
/*    */     } 
/* 79 */     StringUtilities.replace(code, "{ACTIONS}", "");
/*    */ 
/*    */ 
/*    */     
/* 83 */     return code.toString();
/*    */   }
/*    */   
/*    */   public synchronized Object onClick(Map submitParams) {
/* 87 */     Iterator iter = this.elements.iterator();
/* 88 */     while (iter.hasNext()) {
/* 89 */       Object element = iter.next();
/* 90 */       if (element instanceof HtmlInputElement) {
/* 91 */         HtmlInputElement inputElement = (HtmlInputElement)element;
/* 92 */         if (inputElement.clicked()) {
/* 93 */           this.main.switchModuleHook(null);
/* 94 */           return inputElement.onClick(submitParams);
/*    */         } 
/*    */       } 
/*    */     } 
/* 98 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\TopLinksMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */