/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.SIServiceImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
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
/*     */ import com.eoos.html.element.input.IDLinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ public class StandardUIIconLink
/*     */   extends IDLinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*     */   
/*     */   public StandardUIIconLink(final ClientContext context) {
/*  39 */     super("smenu:" + context.createID(), null, "AssemblyGroups");
/*  40 */     this.context = context;
/*  41 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  43 */           String image = "si/stdinfo2.gif";
/*  44 */           if (StandardUIIconLink.this.isDisabled()) {
/*  45 */             image = "si/stdinfo2-disabled.gif";
/*     */           }
/*  47 */           return "pic/" + image;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getAlternativeText() {
/*  52 */           return context.getLabel("si.tooltip.standard.info");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  56 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  64 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  68 */     if (!VCRUtil.checkMandatory(VCFacade.getInstance(this.context).getCfg())) {
/*  69 */       HtmlCodeRenderer codeRenderer = VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback()
/*     */           {
/*     */             public Object onClose(boolean cancelled) {
/*  72 */               if (!cancelled) {
/*  73 */                 MainHook.getInstance(StandardUIIconLink.this.context).switchUI(1, false);
/*  74 */                 MainHook.getInstance(StandardUIIconLink.this.context).switchUI(1);
/*     */               } 
/*  76 */               return SIServiceImpl.getInstance().getUI(StandardUIIconLink.this.context.getSessionID(), null);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean isReadonly(Object key) {
/*  81 */               return false;
/*     */             }
/*     */             
/*     */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*  85 */               boolean ret = (key == VehicleConfigurationUtil.KEY_MAKE);
/*  86 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODEL);
/*  87 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODELYEAR);
/*  88 */               return ret;
/*     */             }
/*     */             
/*     */             public VINResolver getVINResolver() {
/*  92 */               return (VINResolver)GlobalVINResolver.getInstance(StandardUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public VehicleCfgStorage getStorage() {
/*  96 */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(StandardUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public CfgDataProvider getDataProvider() {
/* 100 */               return GlobalVCDataProvider.getInstance(StandardUIIconLink.this.context);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 105 */       return new ResultObject(0, codeRenderer.getHtmlCode(submitParams));
/*     */     } 
/*     */ 
/*     */     
/* 109 */     MainHook.getInstance(this.context).switchUI(1);
/* 110 */     return MainPage.getInstance(this.context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clicked() {
/* 116 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\StandardUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */