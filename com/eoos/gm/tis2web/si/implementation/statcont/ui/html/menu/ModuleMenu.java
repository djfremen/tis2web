/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu;
/*     */ 
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleMenu
/*     */   extends HtmlElementContainerBase
/*     */   implements IContext
/*     */ {
/*     */   protected ClientContext context;
/*     */   protected HomeUIIconLink linkHome;
/*     */   protected StandardUIIconLink linkStandard;
/*     */   protected BackLink linkBack;
/*     */   protected PrintViewLink linkPrint;
/*     */   protected Connector connector;
/*     */   private Map contextualObjects;
/*     */   
/*     */   public static interface Connector
/*     */   {
/*     */     boolean enableBack();
/*     */     
/*     */     void back(Map param1Map);
/*     */     
/*     */     String getPageCode(Map param1Map);
/*     */   }
/*     */   
/*     */   public Connector getConnector() {
/*     */     return this.connector;
/*     */   }
/*     */   
/*     */   public ClientContext getClientContext() {
/*     */     return this.context;
/*     */   }
/*     */   
/*     */   public ModuleMenu(ClientContext context, Connector connector) {
/* 133 */     this.contextualObjects = new HashMap<Object, Object>(); this.context = context; this.connector = connector; this.context.addLogoutListener(new ClientContextBase.LogoutListener() { public void onLogout() { ModuleMenu.this.context = null; ModuleMenu.this.contextualObjects.clear(); ModuleMenu.this.elements.clear(); } }
/*     */       ); this.linkHome = new HomeUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkHome); this.linkStandard = new StandardUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkStandard); this.linkBack = new BackLink(context) {
/*     */         public Object onClick(Map submitParams) { ModuleMenu.this.getConnector().back(submitParams); return null; } public boolean isDisabled() { return !ModuleMenu.this.getConnector().enableBack(); }
/*     */       }; addElement((HtmlElement)this.linkBack); this.linkPrint = new PrintViewLink(context) {
/*     */         public String getCode(Map params) { return ModuleMenu.this.getConnector().getPageCode(params); }
/*     */       };
/*     */     addElement((HtmlElement)this.linkPrint);
/* 142 */   } public void storeObject(Object identifier, Object data) { this.contextualObjects.put(identifier, data); } public String getHtmlCode(Map params) { StringBuffer retValue = new StringBuffer("<table><tr>{LINK}</tr></table>");
/*     */     for (Iterator<HtmlElement> iter = this.elements.iterator(); iter.hasNext(); ) {
/*     */       HtmlElement element = iter.next();
/*     */       StringUtilities.replace(retValue, "{LINK}", "<td>" + element.getHtmlCode(params) + "</td>{LINK}");
/*     */     } 
/*     */     StringUtilities.replace(retValue, "{LINK}", "");
/*     */     return retValue.toString(); } protected void hookOnClick(HtmlElement element) {}
/*     */   protected boolean isDisabled(HtmlElement element) { return false; }
/*     */   public Object getObject(Object identifier) {
/* 151 */     return this.contextualObjects.get(identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getObjects(Filter filter) {
/* 161 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void removeObject(Object identifier) {
/* 165 */     this.contextualObjects.remove(identifier);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\menu\ModuleMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */