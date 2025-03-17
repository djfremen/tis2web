/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*     */ 
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.SIOWISElementImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BookmarkUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BrochuresUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.BulletinsUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.FaultDiagUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.HomeUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.NumberSearchUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.StandardUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.TextSearchUIIconLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.CPRMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.lu.LUMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.wd.WDMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
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
/*     */   protected BulletinsUIIconLink linkBulletins;
/*     */   protected BrochuresUIIconLink linkBrochures;
/*     */   protected TextSearchUIIconLink linkTextSearch;
/*     */   protected NumberSearchUIIconLink linkNumberSearch;
/*     */   protected BookmarkUIIconLink linkBookmarks;
/*     */   protected NavigationToggleLink linkNavigationToggle;
/*     */   private FaultDiagUIIconLink faultDiag;
/*  70 */   protected SIO sio = null;
/*     */   
/*  72 */   protected SITMenu sitMenu = null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Connector
/*     */   {
/*     */     SIO getSIO();
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void back(Map param1Map);
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
/*     */ 
/*     */ 
/*     */     
/*     */     void registerNodeChangeListener(NodeChangeListener param1NodeChangeListener);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Connector getConnector() {
/* 211 */     return this.connector;
/*     */   }
/*     */   
/*     */   private void update() {
/* 215 */     SIO sio = this.connector.getSIO();
/* 216 */     if ((sio == null && this.sio != null) || (sio != null && !sio.equals(this.sio))) {
/*     */       CPRMenu cPRMenu;
/* 218 */       SITMenu newMenu = null;
/* 219 */       if (sio != null)
/*     */       {
/* 221 */         if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOLU || sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOProxy) {
/* 222 */           LUMenu lUMenu = LUMenu.getInstance(this);
/* 223 */         } else if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOWD) {
/* 224 */           WDMenu wDMenu = WDMenu.getInstance(this);
/* 225 */         } else if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/* 226 */           cPRMenu = CPRMenu.getInstance(this);
/*     */         } 
/*     */       }
/* 229 */       removeElement((HtmlElement)this.sitMenu);
/* 230 */       this.sitMenu = (SITMenu)cPRMenu;
/* 231 */       this.sio = sio;
/* 232 */       if (this.sitMenu != null) {
/* 233 */         addElement((HtmlElement)this.sitMenu);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SITMenu getMenuFromSio(SIO sio) {
/* 241 */     return null;
/*     */   }
/*     */   
/*     */   public ClientContext getClientContext() {
/* 245 */     return this.context;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 249 */     update();
/* 250 */     StringBuffer retValue = new StringBuffer("<table><tr>{LINK}</tr></table>");
/* 251 */     for (Iterator<HtmlElement> iter = this.elements.iterator(); iter.hasNext(); ) {
/* 252 */       HtmlElement element = iter.next();
/* 253 */       boolean showBulletinLink = (element instanceof BulletinsUIIconLink && ModuleContext.getInstance(this.context.getSessionID()).allowBulletinAccess());
/* 254 */       if ((!showBulletinLink && ((element == this.faultDiag && !FaultDiagPanel.getInstance(this.context).isSaabWis()) || (element instanceof BrochuresUIIconLink && WIS.hasSaabData(this.context)))) || (
/* 255 */         element instanceof BulletinsUIIconLink && !showBulletinLink)) {
/*     */         continue;
/*     */       }
/* 258 */       StringUtilities.replace(retValue, "{LINK}", "<td>" + element.getHtmlCode(params) + "</td>{LINK}");
/*     */     } 
/*     */ 
/*     */     
/* 262 */     StringUtilities.replace(retValue, "{LINK}", "");
/* 263 */     return retValue.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void hookOnClick(HtmlElement element) {}
/*     */   
/*     */   protected boolean isDisabled(HtmlElement element) {
/* 270 */     boolean ret = false;
/* 271 */     SIO sio = this.connector.getSIO();
/* 272 */     if ((element instanceof AddBookmarkLink || element instanceof com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.lu.PageInfoLink) && sio != null && sio instanceof SIOWISElementImpl) {
/* 273 */       SIOWISElementImpl sioWis = (SIOWISElementImpl)sio;
/* 274 */       if (sioWis.hasProperty((SITOCProperty)SIOProperty.WIS))
/*     */       {
/* 276 */         ret = (sioWis.getProperty((SITOCProperty)SIOProperty.WIS) == null);
/*     */       }
/*     */     } 
/* 279 */     return ret;
/*     */   }
/*     */   
/* 282 */   public ModuleMenu(final ClientContext context, final Connector connector) { this.contextualObjects = new HashMap<Object, Object>(); this.context = context; this.connector = connector; this.linkHome = new HomeUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkHome); this.linkStandard = new StandardUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkStandard); this.faultDiag = new FaultDiagUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } }
/*     */       ; addElement((HtmlElement)this.faultDiag); this.linkBulletins = new BulletinsUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkBulletins); this.linkBrochures = new BrochuresUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/*     */       ; addElement((HtmlElement)this.linkBrochures); this.linkTextSearch = new TextSearchUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }
/* 288 */       ; addElement((HtmlElement)this.linkTextSearch); this.linkNumberSearch = new NumberSearchUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }; addElement((HtmlElement)this.linkNumberSearch); this.linkBookmarks = new BookmarkUIIconLink(context) { public Object onClick(Map params) { ModuleMenu.this.hookOnClick((HtmlElement)this); return super.onClick(params); } public boolean isDisabled() { return ModuleMenu.this.isDisabled((HtmlElement)this); } }; addElement((HtmlElement)this.linkBookmarks); this.linkNavigationToggle = new NavigationToggleLink(context) { public Object onClick(Map submitParams) { ModuleMenu.this.hookOnClick((HtmlElement)this); connector.toggleNavigation(); return MainPage.getInstance(context); } public boolean isDisabled() { return !connector.enableNavToggle(); } }; addElement((HtmlElement)this.linkNavigationToggle); LUMenu.getInstance(this); WDMenu.getInstance(this); CPRMenu.getInstance(this); } public void storeObject(Object identifier, Object data) { this.contextualObjects.put(identifier, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject(Object identifier) {
/* 295 */     return this.contextualObjects.get(identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getObjects(Filter filter) {
/* 303 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void removeObject(Object identifier) {
/* 307 */     this.contextualObjects.remove(identifier);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\ModuleMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */