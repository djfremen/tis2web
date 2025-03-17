/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*     */ 
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class ModuleMenu
/*     */   extends HtmlElementContainerBase
/*     */   implements IContext
/*     */ {
/*     */   protected ClientContext context;
/*     */   protected StandardUIIconLink linkStandard;
/*     */   protected TextSearchUIIconLink linkTextSearch;
/*     */   protected BookmarkUIIconLink linkBookmarks;
/*     */   protected LTSelectionListLink linkTAL;
/*     */   protected NavigationToggleLink linkNavigationToggle;
/*     */   protected ICOPClientLink linkICOPClient;
/*  45 */   protected SIOLT sio = null;
/*     */   
/*  47 */   protected SITMenu sitMenu = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Connector connector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map contextualObjects;
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
/*     */   public static interface Connector
/*     */   {
/*     */     SIOLT getSIOLTElement();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean enableBack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void back();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getPageCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean enableNavToggle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void toggleNavigation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LTListPage getLTListPage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connector getConnector() {
/* 152 */     return this.connector;
/*     */   }
/*     */   
/*     */   private void update() {
/* 156 */     SIOLT sio = this.connector.getSIOLTElement();
/* 157 */     if ((sio == null && this.sio != null) || (sio != null && !sio.equals(this.sio))) {
/*     */       
/* 159 */       SITMenu newMenu = null;
/* 160 */       if (sio != null) {
/* 161 */         newMenu = SITMenu.getInstance(this);
/*     */       }
/*     */       
/* 164 */       removeElement((HtmlElement)this.sitMenu);
/* 165 */       this.sitMenu = newMenu;
/* 166 */       this.sio = sio;
/* 167 */       if (this.sitMenu != null) {
/* 168 */         addElement((HtmlElement)this.sitMenu);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientContext getClientContext() {
/* 176 */     return this.context;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 180 */     update();
/* 181 */     StringBuffer retValue = new StringBuffer("<table><tr>{LINK}</tr></table>");
/* 182 */     for (Iterator<HtmlElement> iter = this.elements.iterator(); iter.hasNext(); ) {
/* 183 */       HtmlElement element = iter.next();
/* 184 */       StringUtilities.replace(retValue, "{LINK}", "<td>" + element.getHtmlCode(params) + "</td>{LINK}");
/*     */     } 
/* 186 */     StringUtilities.replace(retValue, "{LINK}", "");
/* 187 */     return retValue.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void hookOnClick(HtmlElement element) {}
/*     */   
/*     */   protected boolean isDisabled(HtmlElement element) {
/* 194 */     return false;
/*     */   }
/*     */   
/* 197 */   public ModuleMenu(final ClientContext context, final Connector connector) { this.contextualObjects = new HashMap<Object, Object>(); this.context = context; this.connector = connector; this.linkStandard = new StandardUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkStandard); this.linkTextSearch = new TextSearchUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkTextSearch); this.linkBookmarks = new BookmarkUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkBookmarks); this.linkTAL = new LTSelectionListLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkTAL); this.linkNavigationToggle = new NavigationToggleLink(context) { public Object onClick(Map submitParams) { ModuleMenu.this.hookOnClick((HtmlElement)this); connector.toggleNavigation(); return MainPage.getInstance(context); } public boolean isDisabled() { return !connector.enableNavToggle(); } }
/*     */       ; addElement((HtmlElement)this.linkNavigationToggle); this.linkICOPClient = new ICOPClientLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return (!ComponentAccessPermission.getInstance(context).check("icop.client") || ModuleMenu.this.isDisabled((HtmlElement)this)); } }
/* 203 */       ; addElement((HtmlElement)this.linkICOPClient); SITMenu.getInstance(this); } public void storeObject(Object identifier, Object data) { this.contextualObjects.put(identifier, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject(Object identifier) {
/* 210 */     return this.contextualObjects.get(identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getObjects(Filter filter) {
/* 218 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void removeObject(Object identifier) {
/* 222 */     this.contextualObjects.remove(identifier);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\ModuleMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */