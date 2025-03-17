/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Window;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SplashScreen
/*    */   extends Window
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   private static final Logger log = Logger.getLogger(SplashScreen.class);
/*    */   
/* 20 */   private static SplashScreen instance = null;
/*    */   
/*    */   private SplashScreen() {
/* 23 */     super(new JFrame());
/*    */     
/* 25 */     setLayout(new BorderLayout());
/*    */ 
/*    */     
/*    */     try {
/* 29 */       ImageIcon icon = getSplash();
/* 30 */       JLabel label = new JLabel(icon);
/* 31 */       add(label, "Center");
/*    */     }
/* 33 */     catch (Exception e) {
/* 34 */       log.error("unable to create image - exception:" + e, e);
/*    */     } 
/*    */     
/* 37 */     setCursor(new Cursor(3));
/*    */     
/* 39 */     pack();
/*    */     
/* 41 */     setLocation(UIUtil.getScreenCenterLocation(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageIcon getSplash() {
/* 46 */     ImageIcon image = null;
/*    */     try {
/* 48 */       image = new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/loading.jpg"));
/*    */     }
/* 50 */     catch (Exception e) {
/* 51 */       log.error("unable to load Splash Image, -exception: " + e.getMessage());
/*    */     } 
/* 53 */     return image;
/*    */   }
/*    */   
/*    */   public static synchronized SplashScreen getInstance() {
/* 57 */     if (instance == null) {
/* 58 */       instance = new SplashScreen();
/*    */     }
/* 60 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\SplashScreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */