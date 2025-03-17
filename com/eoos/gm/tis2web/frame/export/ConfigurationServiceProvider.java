/*   */ package com.eoos.gm.tis2web.frame.export;
/*   */ 
/*   */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*   */ 
/*   */ public class ConfigurationServiceProvider {
/*   */   public static ConfigurationService getService() {
/* 7 */     return (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*   */   }
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\ConfigurationServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */