/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.ecm.ECMFaultCodeSelectionPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultCallback;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FilterCallback;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm.ECMCallback;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm.ECMToFault;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm.FilterCallback2;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Observable;
/*     */ import java.util.Observer;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FaultDiagPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements Observer
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(FaultDiagPanel.class);
/*     */   
/*  43 */   private Set nodeChangeListeners = new HashSet();
/*     */   
/*  45 */   private CTOCNode root = null;
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  50 */       template = ApplicationContext.getInstance().loadFile(FaultDiagPanel.class, "faultdiagpanel.html", null).toString();
/*  51 */     } catch (Exception e) {
/*  52 */       log.error("unable to load template - error:" + e, e);
/*  53 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected FaultDiagMenu ieMenu;
/*  59 */   private HtmlElementStack stack = new HtmlElementStack();
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  63 */   private DocumentPage documentPage = null;
/*     */ 
/*     */   
/*     */   public FaultDiagPanel(ClientContext context) {
/*  67 */     this.context = context;
/*  68 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/*  70 */           SIO ret = null;
/*     */           try {
/*  72 */             ret = FaultDiagPanel.this.documentPage.getSIO();
/*  73 */           } catch (NullPointerException e) {}
/*     */           
/*  75 */           return ret;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean enableBack() {
/*  80 */           return (FaultDiagPanel.this.stack.size() > 1);
/*     */         }
/*     */         
/*     */         public void back(Map submitParams) {
/*  84 */           if ((FaultDiagPanel.this.documentPage == null || !FaultDiagPanel.this.documentPage.onBack()) && FaultDiagPanel.this.stack.size() > 1) {
/*  85 */             FaultDiagPanel.this.stack.pop();
/*  86 */             FaultDiagPanel.this.documentPage = null;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPageCode() {
/*  92 */           return FaultDiagPanel.this.stack.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/*  96 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public void toggleNavigation() {}
/*     */ 
/*     */         
/*     */         public void registerNodeChangeListener(NodeChangeListener listener) {
/* 104 */           FaultDiagPanel.this.nodeChangeListeners.add(listener);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 109 */     this.ieMenu = new FaultDiagMenu(context, connector);
/* 110 */     addElement((HtmlElement)this.ieMenu);
/*     */     
/* 112 */     addElement((HtmlElement)this.stack);
/* 113 */     TocTree.getInstance(context).addRootObserver(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FaultDiagPanel getInstance(ClientContext context) {
/* 121 */     synchronized (context.getLockObject()) {
/* 122 */       FaultDiagPanel instance = (FaultDiagPanel)context.getObject(FaultDiagPanel.class);
/* 123 */       if (instance == null) {
/* 124 */         instance = new FaultDiagPanel(context);
/* 125 */         context.storeObject(FaultDiagPanel.class, instance);
/*     */       } 
/* 127 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 137 */     StringBuffer code = new StringBuffer(template);
/* 138 */     StringUtilities.replace(code, "{MODULE_MENU}", this.ieMenu.getHtmlCode(params));
/*     */     
/* 140 */     StringUtilities.replace(code, "{STACK}", (this.stack.size() > 0) ? this.stack.getHtmlCode(params) : "");
/* 141 */     return code.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(DataRetrievalAbstraction.DataCallback faultCodes, FilterCallback cprs) {
/* 149 */     this.stack.clear();
/* 150 */     this.stack.push((HtmlElement)new SimpleFaultCodeSelectionPanel(this.context, faultCodes, cprs, this.stack));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initECM(FilterCallback faultCodes, FilterCallback2 cprs, DataRetrievalAbstraction.DataCallback ecms) {
/* 158 */     this.stack.clear();
/* 159 */     this.stack.push((HtmlElement)new ECMFaultCodeSelectionPanel(this.context, faultCodes, cprs, this.stack, ecms));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaabWis() {
/* 164 */     if (this.root == null) {
/* 165 */       update((Observable)null, (Object)null);
/*     */     }
/* 167 */     return (this.stack.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasData() {
/* 175 */     return (TocTree.getInstance(this.context).getRootNode() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentPage(DocumentPage documentPage) {
/* 183 */     this.documentPage = documentPage;
/*     */   }
/*     */   
/*     */   public void update(Observable o, Object arg) {
/* 187 */     this.root = TocTree.getInstance(this.context).getRootNode();
/* 188 */     if (this.root != null) {
/* 189 */       ECMCallback ecm = new ECMCallback(this.root, (SITOCProperty)CTOCProperty.ECM_LIST);
/* 190 */       if (ecm.getData().size() > 0) {
/* 191 */         ECMToFault ecmToFault = new ECMToFault(this.context);
/* 192 */         initECM((FilterCallback)ecmToFault, (FilterCallback2)ecmToFault, (DataRetrievalAbstraction.DataCallback)ecm);
/*     */       }
/*     */       else {
/*     */         
/* 196 */         FaultCallback fcb = new FaultCallback((SITOCElement)this.root, (SITOCProperty)CTOCProperty.DTC_LIST, this.context);
/* 197 */         if (fcb.getData().size() > 0) {
/* 198 */           init((DataRetrievalAbstraction.DataCallback)fcb, (FilterCallback)fcb);
/*     */         } else {
/* 200 */           this.stack.clear();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 204 */       this.stack.clear();
/*     */     } 
/* 206 */     MainHook main = MainHook.getInstance(this.context);
/* 207 */     if (this.stack.size() == 0 && main.getCurrentUI() == 7) {
/* 208 */       main.switchUI(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 214 */     while (this.stack.size() > 1)
/*     */     {
/* 216 */       this.stack.pop();
/*     */     }
/* 218 */     if (this.stack.size() > 0)
/*     */       try {
/* 220 */         ((FaultCodeSelectionPanel)this.stack.peek()).reset();
/* 221 */       } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {} 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\FaultDiagPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */