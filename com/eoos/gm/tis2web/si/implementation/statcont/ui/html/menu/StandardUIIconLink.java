/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.SIServiceImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardUIIconLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*     */   
/*     */   public StandardUIIconLink(final ClientContext context) {
/*  37 */     super(context.createID(), null);
/*  38 */     this.context = context;
/*  39 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  41 */           String image = "si/stdinfo2.gif";
/*  42 */           if (StandardUIIconLink.this.isDisabled()) {
/*  43 */             image = "si/stdinfo2-disabled.gif";
/*     */           }
/*  45 */           return "pic/" + image;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getAlternativeText() {
/*  50 */           return context.getLabel("si.tooltip.standard.info");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  54 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  62 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  66 */     if (!VCRUtil.checkMandatory(VCFacade.getInstance(this.context).getCfg())) {
/*  67 */       HtmlCodeRenderer codeRenderer = VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback()
/*     */           {
/*     */             public Object onClose(boolean cancelled) {
/*  70 */               if (!cancelled) {
/*  71 */                 MainHook.getInstance(StandardUIIconLink.this.context).switchUI(1, false);
/*  72 */                 MainHook.getInstance(StandardUIIconLink.this.context).switchUI(1);
/*     */               } 
/*  74 */               return SIServiceImpl.getInstance().getUI(StandardUIIconLink.this.context.getSessionID(), null);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean isReadonly(Object key) {
/*  79 */               return false;
/*     */             }
/*     */             
/*     */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*  83 */               boolean ret = (key == VehicleConfigurationUtil.KEY_MAKE);
/*  84 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODEL);
/*  85 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODELYEAR);
/*  86 */               return ret;
/*     */             }
/*     */             
/*     */             public VINResolver getVINResolver() {
/*  90 */               return (VINResolver)GlobalVINResolver.getInstance(StandardUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public VehicleCfgStorage getStorage() {
/*  94 */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(StandardUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public CfgDataProvider getDataProvider() {
/*  98 */               return GlobalVCDataProvider.getInstance(StandardUIIconLink.this.context);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 103 */       return new ResultObject(0, codeRenderer.getHtmlCode(submitParams));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     MainHook.getInstance(this.context).switchUI(1, false);
/* 109 */     return MainPage.getInstance(this.context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clicked() {
/* 115 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\menu\StandardUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */