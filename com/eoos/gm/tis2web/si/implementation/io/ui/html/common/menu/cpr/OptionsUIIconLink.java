/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ public class OptionsUIIconLink
/*     */   extends LinkElement
/*     */   implements NodeChangeListener
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(OptionsUIIconLink.class);
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*  36 */   protected List nodeList = new LinkedList();
/*     */   
/*  38 */   protected Integer curSysCode = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionsUIIconLink(final ClientContext context) {
/*  44 */     super(context.createID(), null);
/*  45 */     setDisabled(new Boolean(true));
/*  46 */     this.context = context;
/*  47 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  49 */           String image = "si/options.gif";
/*  50 */           if (OptionsUIIconLink.this.isDisabled()) {
/*  51 */             image = "si/options-disabled.gif";
/*     */           }
/*  53 */           return "pic/" + image;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getAlternativeText() {
/*  58 */           return context.getLabel("cpr.vcrdialog");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  62 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  67 */     setDisabled(new Boolean(false));
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  71 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/*  75 */     return SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  79 */     if (this.curSysCode != null) {
/*     */       
/*     */       try {
/*  82 */         ILVCAdapter.ReturnHandler returnHandler = new ILVCAdapter.ReturnHandler()
/*     */           {
/*     */             public Object onOK() {
/*  85 */               DocumentPage.getInstance(OptionsUIIconLink.this.context).onOptionsChanged();
/*  86 */               MainHook.getInstance(OptionsUIIconLink.this.context).switchUI(1);
/*  87 */               return MainPage.getInstance(OptionsUIIconLink.this.context).getHtmlCode(null);
/*     */             }
/*     */             
/*     */             public Object onCancel() {
/*  91 */               return MainPage.getInstance(OptionsUIIconLink.this.context).getHtmlCode(null);
/*     */             }
/*     */           };
/*  94 */         return new ResultObject(0, getLVCAdapter().getVehicleOptionsDialog(this.context.getSessionID(), this.nodeList, returnHandler));
/*  95 */       } catch (Exception e) {
/*  96 */         log.error("unable to open vehicle options dialog, ignoring (no content result)- exception: " + e, e);
/*  97 */         return new ResultObject(9, new Object());
/*     */       } 
/*     */     }
/* 100 */     return new ResultObject(9, new Object());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*     */     String ret;
/* 106 */     if (this.curSysCode == null) {
/* 107 */       ret = getLabel();
/*     */     } else {
/* 109 */       ret = super.getHtmlCode(params);
/*     */     } 
/* 111 */     return ret;
/*     */   }
/*     */   
/*     */   public synchronized boolean show() {
/* 115 */     return (this.curSysCode != null);
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 119 */     return !show();
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
/*     */   public void setNode(Object node) {
/* 135 */     if (node != null && node instanceof SIOCPR) {
/* 136 */       SIOCPR sioCpr = (SIOCPR)node;
/* 137 */       synchronized (this) {
/* 138 */         this.curSysCode = sioCpr.getElectronicSystemCode();
/*     */       } 
/* 140 */       this.nodeList.remove(this.curSysCode);
/* 141 */       this.nodeList.add(0, this.curSysCode);
/*     */     } else {
/* 143 */       synchronized (this) {
/* 144 */         this.curSysCode = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetNodes() {
/* 150 */     Object first = null;
/* 151 */     if (this.nodeList.size() > 0) {
/* 152 */       first = this.nodeList.get(0);
/*     */     }
/* 154 */     this.nodeList.clear();
/* 155 */     if (first != null) {
/* 156 */       this.nodeList.add(first);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearNodes() {
/* 162 */     this.nodeList.clear();
/* 163 */     this.curSysCode = null;
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 167 */     return (this.clicked && !isDisabled());
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
/*     */   public void onSetNode(Object node, History history) {
/* 179 */     setNode(node);
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
/*     */   public void onResetNodes() {
/* 191 */     resetNodes();
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
/*     */   public void onClearNodes() {
/* 203 */     clearNodes();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\cpr\OptionsUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */