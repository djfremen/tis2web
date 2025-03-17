/*    */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class AppParamConfigurations
/*    */   extends AppParamConfigurationBase
/*    */ {
/* 19 */   private ArrayList _arrlLogConfiguration = null;
/*    */ 
/*    */   
/*    */   public ArrayList get_vLogConfiguration() {
/* 23 */     return this._arrlLogConfiguration;
/*    */   }
/*    */   
/*    */   public void set_vLogConfiguration(ArrayList logConfiguration) {
/* 27 */     this._arrlLogConfiguration = logConfiguration;
/*    */   }
/*    */   
/*    */   public void addToConfigurationList(AppParamLogConfiguration appParamLogConfiguration) {
/* 31 */     if (this._arrlLogConfiguration == null)
/* 32 */       this._arrlLogConfiguration = new ArrayList(); 
/* 33 */     this._arrlLogConfiguration.add(appParamLogConfiguration);
/* 34 */     this._arrlLogConfiguration.trimToSize();
/*    */   }
/*    */   
/*    */   public AppParamLogConfiguration getAppParamLogConfigurationByIndex(int index) {
/* 38 */     return (numberOfElements() > 0) ? this._arrlLogConfiguration.get(index) : null;
/*    */   }
/*    */   
/*    */   public int numberOfElements() {
/* 42 */     return (this._arrlLogConfiguration != null) ? this._arrlLogConfiguration.size() : 0;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamConfigurations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */