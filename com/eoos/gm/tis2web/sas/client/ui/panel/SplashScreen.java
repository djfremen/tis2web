/*    */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.system.ImageDataProvider;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.main.MainFrame;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.UIUtil;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Window;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SplashScreen
/*    */   extends Window
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   private static final Logger log = Logger.getLogger(SplashScreen.class);
/*    */   
/* 24 */   private static SplashScreen instance = null;
/*    */   
/*    */   private SplashScreen() {
/* 27 */     super((Frame)MainFrame.getInstance());
/*    */     
/* 29 */     setLayout(new BorderLayout());
/*    */ 
/*    */     
/*    */     try {
/* 33 */       ImageIcon icon = new ImageIcon(ImageDataProvider.getInstance().getData("loading.jpg"));
/* 34 */       JLabel label = new JLabel(icon);
/* 35 */       add(label, "Center");
/*    */     }
/* 37 */     catch (Exception e) {
/* 38 */       log.error("unable to create image - exception:" + e, e);
/*    */     } 
/*    */     
/* 41 */     setCursor(new Cursor(3));
/*    */     
/* 43 */     pack();
/*    */     
/* 45 */     setLocation(UIUtil.getScreenCenterLocation(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized SplashScreen getInstance() {
/* 50 */     if (instance == null) {
/* 51 */       instance = new SplashScreen();
/*    */     }
/* 53 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\SplashScreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */