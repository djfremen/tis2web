/*    */ package com.eoos.gm.tis2web.sps.client.ui.mail;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class DefaultCalllback
/*    */   implements LogFilesMailDialog.Callback
/*    */ {
/* 12 */   private static final Locale LOCALE = ClientAppContextProvider.getClientAppContext().getLocale();
/* 13 */   private static final LabelResource RESPROV = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/*    */   public String getLabel(String key) {
/* 16 */     return RESPROV.getLabel(LOCALE, key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 20 */     return RESPROV.getMessage(LOCALE, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\mail\DefaultCalllback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */