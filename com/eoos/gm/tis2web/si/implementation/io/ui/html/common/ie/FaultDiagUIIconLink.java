/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.SIServiceImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagPanel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FaultDiagUIIconLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*     */   
/*     */   public FaultDiagUIIconLink(final ClientContext context) {
/*  42 */     super("smenu:" + context.createID(), null);
/*  43 */     this.context = context;
/*  44 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  46 */           return "pic/si/saabfaultdiagl.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  50 */           return context.getLabel("si.tooltip.fault.diag");
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
/*  66 */     final MainHook main = MainHook.getInstance(this.context);
/*     */     
/*  68 */     if (main.getCurrentUI() == 7 || main.getCurrentUI() == 0) {
/*  69 */       FaultDiagPanel.getInstance(this.context).reset();
/*     */     }
/*     */     
/*  72 */     if (!VCRUtil.checkMandatory(VCFacade.getInstance(this.context).getCfg())) {
/*  73 */       HtmlCodeRenderer codeRenderer = VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback()
/*     */           {
/*     */             public Object onClose(boolean cancelled) {
/*  76 */               if (VCRUtil.checkMandatory(VCFacade.getInstance(FaultDiagUIIconLink.this.context).getCfg())) {
/*  77 */                 main.switchUI(7, false);
/*  78 */                 main.switchUI(7);
/*     */               } 
/*  80 */               return SIServiceImpl.getInstance().getUI(FaultDiagUIIconLink.this.context.getSessionID(), null);
/*     */             }
/*     */             
/*     */             public boolean isReadonly(Object key) {
/*  84 */               return false;
/*     */             }
/*     */             
/*     */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*  88 */               boolean ret = (key == VehicleConfigurationUtil.KEY_MAKE);
/*  89 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODEL);
/*  90 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODELYEAR);
/*  91 */               return ret;
/*     */             }
/*     */             
/*     */             public VINResolver getVINResolver() {
/*  95 */               return (VINResolver)GlobalVINResolver.getInstance(FaultDiagUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public VehicleCfgStorage getStorage() {
/*  99 */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(FaultDiagUIIconLink.this.context);
/*     */             }
/*     */             
/*     */             public CfgDataProvider getDataProvider() {
/* 103 */               return GlobalVCDataProvider.getInstance(FaultDiagUIIconLink.this.context);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 108 */       return new ResultObject(0, codeRenderer.getHtmlCode(submitParams));
/*     */     } 
/*     */ 
/*     */     
/* 112 */     main.switchUI(7);
/* 113 */     return MainPage.getInstance(this.context);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\FaultDiagUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */