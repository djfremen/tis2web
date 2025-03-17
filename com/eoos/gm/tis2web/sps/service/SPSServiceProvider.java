/*   */ package com.eoos.gm.tis2web.sps.service;
/*   */ 
/*   */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*   */ import com.eoos.gm.tis2web.sps.server.implementation.service.SPSServiceImpl;
/*   */ 
/*   */ public class SPSServiceProvider
/*   */ {
/*   */   public static SPSServiceImpl getSPSService() {
/* 9 */     return (SPSServiceImpl)ConfiguredServiceProvider.getInstance().getService(SPSServiceImpl.class);
/*   */   }
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\SPSServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */