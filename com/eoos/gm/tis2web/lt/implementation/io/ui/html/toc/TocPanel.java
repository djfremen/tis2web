/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TocPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements LTClientContext.LTClientContextObserver
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(TocPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       template = ApplicationContext.getInstance().loadFile(TocPanel.class, "tocpanel.html", null).toString();
/*  32 */     } catch (Exception e) {
/*  33 */       log.error("unable to load template - error:" + e, e);
/*  34 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  38 */   private SearchWorkLink link = null;
/*     */   
/*  40 */   private TextInputElement input = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TocTreeElement treeElement;
/*     */ 
/*     */   
/*     */   public TocPanel(ClientContext context, int tocMode) {
/*  48 */     this.context = context;
/*  49 */     LTClientContext.getInstance(context).addObserver(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     this.input = new TextInputElement("MWINPUT");
/*  57 */     this.input.setValue("");
/*  58 */     addElement((HtmlElement)this.input);
/*     */     
/*  60 */     this.link = new SearchWorkLink(this.input, context);
/*  61 */     addElement((HtmlElement)this.link);
/*     */     
/*  63 */     this.treeElement = new TocTreeElement(context, tocMode);
/*  64 */     addElement((HtmlElement)this.treeElement);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  68 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  70 */     this.input.setValue(LTUIContext.getInstance(this.context).getSearchNr());
/*     */     
/*  72 */     StringUtilities.replace(code, "{MW_LABEL}", this.context.getLabel("lt.search.mwork"));
/*  73 */     StringUtilities.replace(code, "{NR_INPUT}", this.input.getHtmlCode(params));
/*  74 */     StringUtilities.replace(code, "{LINK_SEARCH}", this.link.getHtmlCode(params));
/*     */     
/*  76 */     String status = null;
/*  77 */     switch (this.link.popStatus()) {
/*     */       case 1:
/*  79 */         status = "<span style=\"color:red\">" + this.context.getMessage("lt.search.mwork.not.found") + "</span>";
/*     */         break;
/*     */     } 
/*     */     
/*  83 */     StringUtilities.replace(code, "{SEARCH_STATUS}", status);
/*     */     
/*  85 */     StringUtilities.replace(code, "{TREE}", this.treeElement.getHtmlCode(params));
/*  86 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void toggleNavigationMode() {
/*  90 */     this.treeElement.toggleMode();
/*     */   }
/*     */   
/*     */   public void onVehicleChange(boolean bNewMC) {
/*  94 */     removeElement((HtmlElement)this.treeElement);
/*  95 */     this.treeElement = new TocTreeElement(this.context, this.treeElement.getMode());
/*  96 */     addElement((HtmlElement)this.treeElement);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onUnhandledSubmit(Map params) {
/* 117 */     this.link.onClick(params);
/* 118 */     HtmlElementContainer container = getContainer();
/* 119 */     while (container.getContainer() != null) {
/* 120 */       container = container.getContainer();
/*     */     }
/* 122 */     return container;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\TocPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */