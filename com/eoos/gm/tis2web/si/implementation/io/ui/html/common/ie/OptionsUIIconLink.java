/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*     */ public class OptionsUIIconLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   protected ClientContext context;
/*  31 */   protected List nodeList = new LinkedList();
/*     */   
/*  33 */   protected Integer curSysCode = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionsUIIconLink(final ClientContext context) {
/*  39 */     super(context.createID(), null);
/*  40 */     setDisabled(new Boolean(true));
/*  41 */     this.context = context;
/*  42 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  44 */           String image = "si/options.gif";
/*  45 */           if (OptionsUIIconLink.this.isDisabled()) {
/*  46 */             image = "si/options-disabled.gif";
/*     */           }
/*  48 */           return "pic/" + image;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getAlternativeText() {
/*  53 */           return context.getLabel("cpr.vcrdialog");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  57 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  62 */     setDisabled(new Boolean(false));
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  66 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdpater() {
/*  70 */     return SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  74 */     if (this.curSysCode != null) {
/*     */       
/*     */       try {
/*  77 */         ILVCAdapter.ReturnHandler returnHandler = new ILVCAdapter.ReturnHandler()
/*     */           {
/*     */             public Object onOK() {
/*  80 */               DocumentPage.getInstance(OptionsUIIconLink.this.context).onOptionsChanged();
/*  81 */               MainHook.getInstance(OptionsUIIconLink.this.context).switchUI(1);
/*  82 */               return MainPage.getInstance(OptionsUIIconLink.this.context).getHtmlCode(null);
/*     */             }
/*     */             
/*     */             public Object onCancel() {
/*  86 */               return MainPage.getInstance(OptionsUIIconLink.this.context).getHtmlCode(null);
/*     */             }
/*     */           };
/*     */ 
/*     */         
/*  91 */         return new ResultObject(0, getLVCAdpater().getVehicleOptionsDialog(this.context.getSessionID(), this.nodeList, returnHandler));
/*  92 */       } catch (Exception e) {
/*  93 */         return new ResultObject(9, new Object());
/*     */       } 
/*     */     }
/*  96 */     return new ResultObject(9, new Object());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*     */     String ret;
/* 102 */     if (this.curSysCode == null) {
/* 103 */       ret = getLabel();
/*     */     } else {
/* 105 */       ret = super.getHtmlCode(params);
/*     */     } 
/* 107 */     return ret;
/*     */   }
/*     */   
/*     */   public synchronized boolean show() {
/* 111 */     return (this.curSysCode != null);
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 115 */     return !show();
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
/* 131 */     if (node != null && node instanceof SIOCPR) {
/* 132 */       SIOCPR sioCpr = (SIOCPR)node;
/* 133 */       synchronized (this) {
/* 134 */         this.curSysCode = sioCpr.getElectronicSystemCode();
/*     */       } 
/* 136 */       this.nodeList.remove(this.curSysCode);
/* 137 */       this.nodeList.add(0, this.curSysCode);
/* 138 */       setDisabled(new Boolean(false));
/*     */     } else {
/* 140 */       synchronized (this) {
/* 141 */         this.curSysCode = null;
/*     */       } 
/* 143 */       setDisabled(new Boolean(true));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetNodes() {
/* 148 */     Object first = null;
/* 149 */     if (this.nodeList.size() > 0) {
/* 150 */       first = this.nodeList.get(0);
/*     */     }
/* 152 */     this.nodeList.clear();
/* 153 */     if (first != null) {
/* 154 */       this.nodeList.add(first);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearNodes() {
/* 160 */     this.nodeList.clear();
/* 161 */     this.curSysCode = null;
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 165 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\OptionsUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */