/*     */ package com.eoos.gm.tis2web.rpo.ui.html;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.rpo.RPOServiceImpl;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.util.Util;
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
/*     */ public class HomePanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  35 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/*  36 */     } catch (Exception e) {
/*  37 */       log.error("unable to load template - error:" + e, e);
/*  38 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement ieVIN;
/*     */   
/*     */   private ClickButtonElement buttonApply;
/*     */ 
/*     */   
/*     */   private HomePanel(final ClientContext context) {
/*  51 */     this.context = context;
/*     */     
/*  53 */     this.ieVIN = new TextInputElement(context.createID())
/*     */       {
/*     */         public String getHtmlCode(Map params) {
/*     */           try {
/*  57 */             if (Util.isNullOrEmpty(this.value)) {
/*  58 */               String vin = VCFacade.getInstance(context).getCurrentVIN();
/*  59 */               if (!Util.isNullOrEmpty(vin)) {
/*  60 */                 setValue(vin);
/*     */               }
/*     */             } 
/*  63 */           } catch (Exception e) {
/*  64 */             HomePanel.log.warn("unable to set vin, ignoring - exception: " + e, e);
/*     */           } 
/*     */           
/*  67 */           return super.getHtmlCode(params);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  72 */     addElement((HtmlElement)this.ieVIN);
/*     */     
/*  74 */     this.buttonApply = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  77 */           return context.getLabel("display.rpo.codes");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  82 */             return HomePanel.this.onApply();
/*  83 */           } catch (Exception e) {
/*  84 */             return ErrorMessageBox.create(context, e.getMessage(), getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  89 */     addElement((HtmlElement)this.buttonApply);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HomePanel getInstance(ClientContext context) {
/*  94 */     synchronized (context.getLockObject()) {
/*  95 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/*  96 */       if (instance == null) {
/*  97 */         instance = new HomePanel(context);
/*  98 */         context.storeObject(HomePanel.class, instance);
/*     */       } 
/* 100 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 105 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 107 */     StringUtilities.replace(code, "{LABEL_VIN}", this.context.getLabel("vin"));
/* 108 */     StringUtilities.replace(code, "{INPUT_VIN}", this.ieVIN.getHtmlCode(params));
/* 109 */     StringUtilities.replace(code, "{BUTTON_APPLY}", this.buttonApply.getHtmlCode(params));
/* 110 */     return code.toString();
/*     */   }
/*     */   
/*     */   private Object onApply() throws Exception {
/*     */     try {
/* 115 */       String vin = (String)this.ieVIN.getValue();
/* 116 */       RPOContainer container = RPOServiceImpl.getInstance().getRPOs(vin);
/* 117 */       VCFacade.getInstance(this.context).setVIN(vin);
/* 118 */       return new RPOCodesDisplay(this.context, vin, container);
/* 119 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException e) {
/* 120 */       throw new Exception(this.context.getMessage("invalid.vin"));
/* 121 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.UnsupportedVINException e) {
/* 122 */       throw new Exception(this.context.getMessage("unsupported.vin"));
/*     */     }
/* 124 */     catch (Exception e) {
/* 125 */       log.error("unable to display rpo - exception: " + e, e);
/* 126 */       throw new Exception(this.context.getMessage("unable.to.display.rpo"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rp\\ui\html\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */