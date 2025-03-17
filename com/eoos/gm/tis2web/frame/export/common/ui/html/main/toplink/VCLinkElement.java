/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.html.element.input.IDLinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ public class VCLinkElement
/*     */   extends IDLinkElement
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(VCLinkElement.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCLinkElement(final ClientContext context, Callback callback) {
/*  45 */     super(context.createID(), null, "vc");
/*  46 */     this.context = context;
/*  47 */     this.callback = callback;
/*  48 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  50 */           return "pic/common/vehiclecontext.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  54 */           return context.getLabel("tooltip.toplink.vehicle.context");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  58 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  66 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   } public static interface Callback {
/*     */     Object getReturnUI(); boolean isReadonly(Object param1Object); boolean isMandatory(Object param1Object, IConfiguration param1IConfiguration); }
/*     */   public Object onClick(Map submitParams) {
/*     */     try {
/*  71 */       final VCService vcservice = VCServiceProvider.getInstance().getService(this.context);
/*  72 */       VCService.DialogCallback dialogCallback = new VCService.DialogCallback()
/*     */         {
/*     */           public Object onClose(boolean cancelled) {
/*  75 */             return VCLinkElement.this.callback.getReturnUI();
/*     */           }
/*     */           
/*     */           public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*  79 */             return VCLinkElement.this.callback.isMandatory(key, currentCfg);
/*     */           }
/*     */           
/*     */           public CfgDataProvider getDataProvider() {
/*  83 */             return GlobalVCDataProvider.getInstance(VCLinkElement.this.context);
/*     */           }
/*     */           
/*     */           public VehicleCfgStorage getStorage() {
/*  87 */             return (VehicleCfgStorage)vcservice;
/*     */           }
/*     */           
/*     */           public VINResolver getVINResolver() {
/*  91 */             return (VINResolver)GlobalVINResolver.getInstance(VCLinkElement.this.context);
/*     */           }
/*     */           
/*     */           public boolean isReadonly(Object key) {
/*  95 */             return VCLinkElement.this.callback.isReadonly(key);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 100 */       return vcservice.getDialog(dialogCallback);
/* 101 */     } catch (Exception e) {
/* 102 */       log.error("unable to switch to vc module - exception:" + e, e);
/* 103 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\VCLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */