/*    */ package com.eoos.gm.tis2web.sps.client.ui.error;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.awt.Component;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ 
/*    */ public class ErrorPopupFacade
/*    */ {
/* 16 */   private static LabelResource RESOURCE_PROVIDER = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/* 18 */   private static final Locale LOCALE = ClientAppContextProvider.getClientAppContext().getLocale();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void showErrorPopup(final Component parent, final String messageKey) {
/*    */     try {
/* 25 */       SwingUtilities.invokeAndWait(new Runnable()
/*    */           {
/*    */             public void run() {
/* 28 */               JOptionPane.showMessageDialog(parent, ErrorPopupFacade.RESOURCE_PROVIDER.getMessage(ErrorPopupFacade.LOCALE, messageKey), "", 0);
/*    */             }
/*    */           });
/*    */     }
/* 32 */     catch (InterruptedException e) {
/* 33 */       Thread.currentThread().interrupt();
/*    */     }
/* 35 */     catch (InvocationTargetException e) {
/* 36 */       Util.rethrowUncheckedException(e.getTargetException());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\error\ErrorPopupFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */