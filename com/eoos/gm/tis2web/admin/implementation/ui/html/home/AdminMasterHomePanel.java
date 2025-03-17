/*     */ package com.eoos.gm.tis2web.admin.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.admin.implementation.AdminMasterServiceImpl;
/*     */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InconsistentSystemException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.menu.MenuRenderer;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.menu.MenuRenderer;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdminMasterHomePanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(AdminMasterHomePanel.class); protected ClientContext context; private MenuRenderer.Callback renderingCallback;
/*     */   private final class RenderingCallback implements MenuRenderer.Callback { private RenderingCallback() {}
/*     */     
/*     */     public String getCode(Object item) {
/*  36 */       return ((LinkElement)AdminMasterHomePanel.this.serviceToLinkElement.get(item)).getHtmlCode(null);
/*     */     }
/*     */     
/*     */     public boolean isActive(Object item) {
/*  40 */       return Util.equals(AdminMasterHomePanel.this.activeService, item);
/*     */     }
/*     */     
/*     */     public List getItems() {
/*  44 */       return AdminMasterHomePanel.this.services;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(Map params) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private AdminSubService activeService = null;
/*     */   
/*  58 */   private List services = new LinkedList();
/*     */   
/*  60 */   private Map serviceToLinkElement = new HashMap<Object, Object>();
/*     */   
/*     */   private HtmlElementHook hook;
/*     */   
/*  64 */   private int layoutConstraint = 0;
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
/*     */   private final HtmlElement DUMMY_ELEMENT;
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
/*     */   private static final String template = "<table id=\"adminpanel\" cellspacing=\"0\" border=\"0\" cellpadding=\"0\" {WIDTH}><tr><td valign=\"bottom\">{MENU}</td></tr><tr><td class=\"active\" width=\"100%\" >&nbsp;</td></tr><tr><td width=\"100%\">{CONTENT}</td></tr></table>";
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
/*     */   private AdminMasterHomePanel(final ClientContext context) {
/* 114 */     this.DUMMY_ELEMENT = (HtmlElement)new HtmlLabel(""); this.context = context; for (Iterator<AdminSubService> iter = AdminMasterServiceImpl.getInstance().getSubServices().iterator(); iter.hasNext(); ) { final AdminSubService subService = iter.next(); if (subService.isAvailable(context)) { this.services.add(subService); if (this.activeService == null) this.activeService = subService;  LinkElement linkElement = new LinkElement(context.createID(), null) { public Object onClick(Map submitParams) { AdminMasterHomePanel.this.activeService = subService; return null; } protected String getLabel() { return String.valueOf(subService.getName(context.getLocale())); } }
/*     */           ; addElement((HtmlElement)linkElement); this.serviceToLinkElement.put(subService, linkElement); }  }  this.renderingCallback = new RenderingCallback(); this.hook = new HtmlElementHook() { protected HtmlElement getActiveElement() { if (AdminMasterHomePanel.this.activeService != null) { HtmlElement panel = AdminMasterHomePanel.this.activeService.getEmbeddableUI(context); if (panel instanceof AdminLayoutControl) AdminMasterHomePanel.this.layoutConstraint = ((AdminLayoutControl)panel).getLayoutConstraint();  return panel; }  return AdminMasterHomePanel.this.DUMMY_ELEMENT; } }
/*     */       ; addElement((HtmlElement)this.hook);
/* 117 */   } public static AdminMasterHomePanel getInstance(ClientContext context) { synchronized (context.getLockObject()) {
/* 118 */       AdminMasterHomePanel instance = (AdminMasterHomePanel)context.getObject(AdminMasterHomePanel.class);
/* 119 */       if (instance == null) {
/* 120 */         instance = new AdminMasterHomePanel(context);
/* 121 */         context.storeObject(AdminMasterHomePanel.class, instance);
/*     */       } 
/* 123 */       return instance;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 130 */     StringBuffer retValue = new StringBuffer();
/* 131 */     retValue.append("<table id=\"adminpanel\" cellspacing=\"0\" border=\"0\" cellpadding=\"0\" {WIDTH}><tr><td valign=\"bottom\">{MENU}</td></tr><tr><td class=\"active\" width=\"100%\" >&nbsp;</td></tr><tr><td width=\"100%\">{CONTENT}</td></tr></table>");
/* 132 */     StringUtilities.replace(retValue, "{MENU}", MenuRenderer.getInstance(this.context).getHtmlCode(this.renderingCallback, params));
/*     */     try {
/* 134 */       StringUtilities.replace(retValue, "{CONTENT}", this.hook.getHtmlCode(params));
/* 135 */     } catch (RuntimeException e) {
/* 136 */       if (Util.hasCause(e, InconsistentSystemException.class)) {
/* 137 */         throw e;
/*     */       }
/* 139 */       log.error("unable to render active content - exception:" + e, e);
/* 140 */       StringUtilities.replace(retValue, "{CONTENT}", this.context.getMessage("error.see.log"));
/*     */     } 
/* 142 */     if (this.layoutConstraint == 1) {
/*     */       
/* 144 */       StringUtilities.replace(retValue, "{WIDTH}", "");
/*     */     } else {
/* 146 */       StringUtilities.replace(retValue, "{WIDTH}", "width=\"100%\"");
/*     */     } 
/* 148 */     return retValue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\implementatio\\ui\html\home\AdminMasterHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */