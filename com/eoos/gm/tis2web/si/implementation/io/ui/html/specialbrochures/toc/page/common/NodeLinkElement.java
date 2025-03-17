/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NodeLinkElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected Node node;
/*    */   protected SpecialBrochuresContext spc;
/*    */   
/*    */   public NodeLinkElement(ClientContext context, Node node) {
/* 40 */     super(context.createID(), null);
/* 41 */     this.node = node;
/* 42 */     this.context = context;
/* 43 */     this.spc = SpecialBrochuresContext.getInstance(context);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 47 */     return "_top";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object onClick(Map params) {
/* 53 */     if (SpecialBrochuresContext.isInspectionNode(this.node) && !this.spc.inspectionMandatorySet()) {
/* 54 */       HtmlCodeRenderer vcDialog = VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback()
/*    */           {
/*    */             public Object onClose(boolean cancelled) {
/* 57 */               if (!cancelled) {
/* 58 */                 SharedContextProxy.getInstance(NodeLinkElement.this.context).update();
/* 59 */                 NodeLinkElement.this.onReturnVCDialog(true);
/*    */               } else {
/* 61 */                 NodeLinkElement.this.onReturnVCDialog(false);
/*    */               } 
/* 63 */               return MainPage.getInstance(NodeLinkElement.this.context).getHtmlCode(null);
/*    */             }
/*    */             
/*    */             public boolean isReadonly(Object key) {
/* 67 */               return false;
/*    */             }
/*    */             
/*    */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 71 */               boolean ret = (key == VehicleConfigurationUtil.KEY_MAKE);
/* 72 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODEL);
/* 73 */               ret = (ret || key == VehicleConfigurationUtil.KEY_MODELYEAR);
/* 74 */               ret = (ret || key == VehicleConfigurationUtil.KEY_ENGINE);
/* 75 */               return ret;
/*    */             }
/*    */             
/*    */             public VINResolver getVINResolver() {
/* 79 */               return (VINResolver)GlobalVINResolver.getInstance(NodeLinkElement.this.context);
/*    */             }
/*    */             
/*    */             public VehicleCfgStorage getStorage() {
/* 83 */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(NodeLinkElement.this.context);
/*    */             }
/*    */             
/*    */             public CfgDataProvider getDataProvider() {
/* 87 */               return GlobalVCDataProvider.getInstance(NodeLinkElement.this.context);
/*    */             }
/*    */           });
/*    */ 
/*    */       
/* 92 */       return new ResultObject(0, vcDialog.getHtmlCode(params));
/*    */     } 
/* 94 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract void onReturnVCDialog(boolean paramBoolean);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\common\NodeLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */