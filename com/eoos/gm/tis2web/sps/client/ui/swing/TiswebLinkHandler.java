/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import com.javio.webwindow.event.LinkEvent;
/*    */ import com.javio.webwindow.event.LinkListener;
/*    */ import java.awt.Component;
/*    */ import java.awt.Cursor;
/*    */ import java.net.URI;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TiswebLinkHandler
/*    */   implements LinkListener
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(TiswebLinkHandler.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean linkClicked(LinkEvent event) {
/* 22 */     String url = event.getLink();
/* 23 */     Object source = event.getSource();
/* 24 */     Cursor cursor = ((Component)source).getCursor();
/* 25 */     ((Component)source).setCursor(Cursor.getPredefinedCursor(3));
/* 26 */     log.debug("load document reference " + url);
/* 27 */     LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/* 28 */     String status = resourceProvider.getLabel(null, "sps.document.loading");
/* 29 */     if (status.indexOf("...") >= 0) {
/* 30 */       status = StringUtilities.replace(status, "...", url + " ...");
/*    */     } else {
/* 32 */       status = status + " " + url;
/*    */     } 
/* 34 */     SPSFrame.getInstance().setInfoOnBarStatus(status);
/*    */     try {
/* 36 */       showDocument(url);
/* 37 */       SPSFrame.getInstance().setInfoOnBarStatus("  ");
/* 38 */     } catch (Exception e) {
/* 39 */       SPSFrame.getInstance().setInfoOnBarStatus("failed to load service information document");
/* 40 */       log.debug("failed to load document reference ", e);
/*    */     } 
/* 42 */     ((Component)source).getParent().setCursor(cursor);
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseEnteredLink(LinkEvent event) {}
/*    */ 
/*    */   
/*    */   public void mouseExitedLink(LinkEvent event) {}
/*    */ 
/*    */   
/*    */   private void showDocument(String url) throws Exception {
/*    */     try {
/* 55 */       Class<?> d = Class.forName("java.awt.Desktop");
/* 56 */       d.getDeclaredMethod("browse", new Class[] { URI.class }).invoke(d.getDeclaredMethod("getDesktop", new Class[0]).invoke(null, new Object[0]), new Object[] { URI.create(url) });
/* 57 */     } catch (Exception x) {
/*    */       try {
/* 59 */         Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
/* 60 */       } catch (Exception y) {
/* 61 */         String[] args = { "cmd", "/c", "start", "iexplore", url };
/* 62 */         Runtime.getRuntime().exec(args);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\TiswebLinkHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */