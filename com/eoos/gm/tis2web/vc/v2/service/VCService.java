/*    */ package com.eoos.gm.tis2web.vc.v2.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public interface VCService
/*    */   extends Service, VehicleCfgStorage {
/*    */   public static interface DialogCallback {
/* 16 */     public static final Object KEY_MAKE = VCService.KEY_MAKE;
/*    */     
/* 18 */     public static final Object KEY_MODEL = VCService.KEY_MODEL;
/*    */     
/* 20 */     public static final Object KEY_MODELYEAR = VCService.KEY_MODELYEAR;
/*    */     
/* 22 */     public static final Object KEY_ENGINE = VCService.KEY_ENGINE;
/*    */     
/* 24 */     public static final Object KEY_TRANSMISSION = VCService.KEY_TRANSMISSION;
/*    */     
/*    */     CfgDataProvider getDataProvider();
/*    */     
/*    */     VehicleCfgStorage getStorage();
/*    */     
/*    */     VINResolver getVINResolver();
/*    */     
/*    */     boolean isMandatory(Object param1Object, IConfiguration param1IConfiguration);
/*    */     
/*    */     boolean isReadonly(Object param1Object);
/*    */     
/*    */     Object onClose(boolean param1Boolean);
/*    */   }
/*    */   
/*    */   public static class ValueComparator
/*    */     implements Comparator {
/*    */     private ClientContext context;
/*    */     
/*    */     public ValueComparator(ClientContext context) {
/* 44 */       this.context = context;
/*    */     }
/*    */     
/*    */     public int compare(Object value1, Object value2) {
/* 48 */       VCService service = VCServiceProvider.getInstance().getService(this.context);
/*    */       
/* 50 */       String str1 = service.getDisplayValue(value1);
/* 51 */       String str2 = service.getDisplayValue(value2);
/*    */       
/* 53 */       return str1.compareToIgnoreCase(str2);
/*    */     }
/*    */     
/*    */     public Comparator toReverse() {
/* 57 */       return Util.reverseComparator(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 62 */   public static final Object KEY_MAKE = VehicleConfigurationUtil.KEY_MAKE;
/*    */   
/* 64 */   public static final Object KEY_MODEL = VehicleConfigurationUtil.KEY_MODEL;
/*    */   
/* 66 */   public static final Object KEY_MODELYEAR = VehicleConfigurationUtil.KEY_MODELYEAR;
/*    */   
/* 68 */   public static final Object KEY_ENGINE = VehicleConfigurationUtil.KEY_ENGINE;
/*    */   
/* 70 */   public static final Object KEY_TRANSMISSION = VehicleConfigurationUtil.KEY_TRANSMISSION;
/*    */   
/*    */   void addObserver(VehicleCfgStorage.Observer paramObserver);
/*    */   
/*    */   HtmlCodeRenderer getDialog(DialogCallback paramDialogCallback);
/*    */   
/*    */   String getDisplayValue(Object paramObject);
/*    */   
/*    */   void removeObserver(VehicleCfgStorage.Observer paramObserver);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VCService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */