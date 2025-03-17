/*     */ package com.eoos.gm.tis2web.home.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.VisualModuleProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.CalIdLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.KDRLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.LTLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.RPOLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.SASLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.SILink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.SPSLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.SWDLLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.SnapshotLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.Tech2ViewLink;
/*     */ import com.eoos.gm.tis2web.home.implementation.ui.html.common.ie.TechlinePrintLink;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.html.renderer.HtmlTagRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ public class HomePanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*     */   private static final int LINKS_PER_ROW = 4;
/*     */   private static String template;
/*     */   
/*     */   private class TableRendererCallback extends HtmlTableRenderer.CallbackAdapter {
/*     */     public int getColumnCount() {
/*  48 */       return 4;
/*     */     }
/*     */     private TableRendererCallback() {}
/*     */     public String getContent(int rowIndex, int columnIndex) {
/*  52 */       int index = rowIndex * 4 + columnIndex;
/*  53 */       if (index >= HomePanel.this.moduleLinks.size()) {
/*  54 */         return "&nbsp;";
/*     */       }
/*  56 */       LinkElement element = HomePanel.this.moduleLinks.get(index);
/*  57 */       return element.getHtmlCode(null);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowCount() {
/*  62 */       int div = HomePanel.this.moduleLinks.size() / 4;
/*  63 */       int mod = HomePanel.this.moduleLinks.size() % 4;
/*  64 */       return div + ((mod != 0) ? 1 : 0);
/*     */     }
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(int rowIndex, int columnIndex) {
/*  68 */       return new HtmlTagRenderer.AdditionalAttributes() {
/*     */           public void getAdditionalAttributes(Map<String, String> map) {
/*  70 */             map.put("align", "left");
/*  71 */             map.put("valign", "bottom");
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  77 */       map.put("width", "90%");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  85 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/*  86 */     } catch (Exception e) {
/*  87 */       log.error("unable to load template - error:" + e, e);
/*  88 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  92 */   private List moduleLinks = new LinkedList();
/*     */   
/*     */   private TableRendererCallback linksRenderingCallback;
/*     */ 
/*     */   
/*     */   private HomePanel(ClientContext context) {
/*  98 */     Collection<String> modules = new HashSet();
/*  99 */     for (Iterator<VisualModule> iter = VisualModuleProvider.getInstance(context).getVisualModules().iterator(); iter.hasNext();) {
/* 100 */       modules.add(((VisualModule)iter.next()).getType());
/*     */     }
/*     */     
/* 103 */     if (modules != null) {
/* 104 */       for (int i = 0; i < 11; i++) {
/* 105 */         RPOLink rPOLink; LinkElement linkElement = null;
/* 106 */         switch (i) {
/*     */           case 0:
/* 108 */             if (modules.contains("si")) {
/* 109 */               SILink sILink = new SILink(context);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 1:
/* 114 */             if (modules.contains("lt")) {
/* 115 */               LTLink lTLink = new LTLink(context);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 2:
/* 120 */             if (modules.contains("swdl")) {
/* 121 */               SWDLLink sWDLLink = new SWDLLink(context);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 3:
/* 126 */             if (modules.contains("sps")) {
/* 127 */               SPSLink sPSLink = new SPSLink(context);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 4:
/* 132 */             if (modules.contains("sps_ci")) {
/* 133 */               CalIdLink calIdLink = new CalIdLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 5:
/* 139 */             if (modules.contains("sas")) {
/* 140 */               SASLink sASLink = new SASLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 6:
/* 146 */             if (modules.contains("snapshot")) {
/* 147 */               SnapshotLink snapshotLink = new SnapshotLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 7:
/* 153 */             if (modules.contains("tech2view")) {
/* 154 */               Tech2ViewLink tech2ViewLink = new Tech2ViewLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 8:
/* 160 */             if (modules.contains("techlineprint")) {
/* 161 */               TechlinePrintLink techlinePrintLink = new TechlinePrintLink(context);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 9:
/* 166 */             if (modules.contains("kdr")) {
/* 167 */               KDRLink kDRLink = new KDRLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 10:
/* 174 */             if (modules.contains("rpo")) {
/* 175 */               rPOLink = new RPOLink(context);
/*     */             }
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 182 */             throw new IllegalArgumentException();
/*     */         } 
/*     */         
/* 185 */         if (rPOLink != null) {
/* 186 */           addElement((HtmlElement)rPOLink);
/* 187 */           this.moduleLinks.add(rPOLink);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 192 */     this.linksRenderingCallback = new TableRendererCallback();
/*     */   }
/*     */ 
/*     */   
/*     */   public static HomePanel getInstance(ClientContext context) {
/* 197 */     synchronized (context.getLockObject()) {
/* 198 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/* 199 */       if (instance == null) {
/* 200 */         instance = new HomePanel(context);
/* 201 */         context.storeObject(HomePanel.class, instance);
/*     */       } 
/* 203 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 208 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 210 */     StringUtilities.replace(code, "{APPLICATION_LINKS}", HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)this.linksRenderingCallback, params));
/*     */     
/* 212 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementatio\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */