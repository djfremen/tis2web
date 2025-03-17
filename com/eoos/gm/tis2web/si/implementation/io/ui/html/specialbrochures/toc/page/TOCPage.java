/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.basic.FlatTreeElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.extended.TreeElement;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class TOCPage
/*     */   extends Page
/*     */ {
/*     */   public static final int NAVIGATION_MODE_BASIC = 0;
/*     */   public static final int NAVIGATION_MODE_EXTENDED = 1;
/*     */   private HtmlElement treeElement;
/*     */   private ContentTreeControl control;
/*  36 */   protected Set expandedNodes = new HashSet();
/*     */ 
/*     */   
/*     */   public TOCPage(ClientContext context, int navigationMode) {
/*  40 */     super(context);
/*     */     
/*  42 */     this.control = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/*  43 */     switch (navigationMode) {
/*     */       case 0:
/*  45 */         this.treeElement = (HtmlElement)new FlatTreeElement(context, this.control, this);
/*     */         break;
/*     */       case 1:
/*  48 */         this.treeElement = (HtmlElement)new TreeElement(context, this.control, this);
/*     */         break;
/*     */       default:
/*  51 */         throw new IllegalArgumentException();
/*     */     } 
/*     */     
/*  54 */     addElement(this.treeElement);
/*  55 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  58 */             TOCPage.this.expandedNodes.clear();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURL(Map params) {
/*  65 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*  66 */     String requestedBookmark = (params != null) ? (String)params.get("bm") : null;
/*  67 */     if (requestedBookmark != null && containesBookmark(requestedBookmark)) {
/*  68 */       url = url + "#" + requestedBookmark;
/*     */     } else {
/*  70 */       url = url + "#selectednode";
/*     */     } 
/*  72 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/*  76 */     synchronized (this.context.getLockObject()) {
/*  77 */       return new ResultObject(0, getHtmlCode(params));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getFormContent(Map params) {
/*  82 */     return this.treeElement.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/*  86 */     return (this.expandedNodes.contains(node) && this.control.getChilds(node).size() != 0);
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/*  90 */     if (expanded) {
/*  91 */       this.expandedNodes.add(node);
/*     */     } else {
/*  93 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/*  98 */     if (isExpanded(node)) {
/*  99 */       setExpanded(node, false);
/*     */     } else {
/* 101 */       setExpanded(node, true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\TOCPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */