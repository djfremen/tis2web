/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.util;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Window;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwingUtils
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(SwingUtils.class);
/*     */ 
/*     */   
/*     */   private static int screenWidth;
/*     */ 
/*     */   
/*     */   private static int screenHeight;
/*     */ 
/*     */   
/*     */   public static Window windowComponent;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteCompsGreaterIndex(JPanel panel, int index) {
/*     */     try {
/*  37 */       Component[] comp = panel.getComponents();
/*     */ 
/*     */       
/*  40 */       if (index != 0) {
/*  41 */         for (int i = comp.length - 1; i > index; i--) {
/*  42 */           panel.remove(i);
/*     */         }
/*     */       }
/*  45 */       panel.validate();
/*  46 */     } catch (Exception e) {
/*  47 */       log.error("Exception in method deleteComponents():" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndexOfComp(JPanel panel, JComponent crtComp) {
/*  60 */     Component[] comp = panel.getComponents();
/*  61 */     int indexOfComp = 0;
/*     */     
/*  63 */     if (crtComp == null) {
/*     */       
/*  65 */       for (int i = 0; i < comp.length; i++) {
/*  66 */         if (comp[i] instanceof com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJComboBox) {
/*  67 */           indexOfComp = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/*  73 */       for (int i = 0; i < comp.length; i++) {
/*  74 */         if (comp[i].equals(crtComp)) {
/*  75 */           indexOfComp = i;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     return indexOfComp;
/*     */   }
/*     */   
/*     */   public static int getSplash_Width() {
/*  84 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/*  85 */     return (int)screenSize.getWidth() * 5 / 32;
/*     */   }
/*     */   
/*     */   public static int getSplash_Height() {
/*  89 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/*  90 */     return (int)screenSize.getHeight() * 1 / 5;
/*     */   }
/*     */   
/*     */   public static int getDialogECU_Width() {
/*  94 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/*  95 */     return (int)screenSize.getWidth() * 5 / 14;
/*     */   }
/*     */   
/*     */   public static int getDialogECU_Height() {
/*  99 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 100 */     return (int)screenSize.getHeight() * 5 / 11;
/*     */   }
/*     */   
/*     */   public static int getDialogHistory_Width() {
/* 104 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 105 */     return (int)screenSize.getWidth() * 5 / 11;
/*     */   }
/*     */   
/*     */   public static int getDialogHistory_Height() {
/* 109 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 110 */     return (int)screenSize.getHeight() * 5 / 11;
/*     */   }
/*     */   
/*     */   public static int getViewerHTML_Width() {
/* 114 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 115 */     return (int)screenSize.getWidth() * 1 / 2;
/*     */   }
/*     */   
/*     */   public static int getViewerHTML_Height() {
/* 119 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 120 */     return (int)screenSize.getHeight() * 1 / 2;
/*     */   }
/*     */   
/*     */   public static double getDriverInstallDialog_Width() {
/* 124 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 125 */     return screenSize.getWidth() * 2.0D / 5.0D;
/*     */   }
/*     */   
/*     */   public static double getDriverInstallDialog_Height() {
/* 129 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 130 */     return screenSize.getWidth() * 1.0D / 3.0D;
/*     */   }
/*     */   
/*     */   public static double getDialogHTML_Width() {
/* 134 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 135 */     return screenSize.getWidth() * 1.0D / 3.0D;
/*     */   }
/*     */   
/*     */   public static double getDialogHTML_Height() {
/* 139 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 140 */     return screenSize.getHeight() * 1.0D / 3.0D;
/*     */   }
/*     */   
/*     */   public static Dimension getMessageDialog_Size() {
/* 144 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 145 */     int width = (int)screenSize.getSize().getWidth() * 5 / 18;
/* 146 */     int height = (int)screenSize.getSize().getHeight() * 1 / 4;
/* 147 */     return new Dimension(width, height);
/*     */   }
/*     */   
/*     */   public static int getDialogSettings_Width() {
/* 151 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 152 */     int width = (int)screenSize.getWidth() * 1 / 2;
/* 153 */     if (screenSize.getWidth() <= 1024.0D) {
/* 154 */       width = 100 + width;
/*     */     }
/* 156 */     return width;
/*     */   }
/*     */   
/*     */   public static int getDialogSettings_Height() {
/* 160 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 161 */     int height = (int)screenSize.getHeight() * 3 / 5;
/* 162 */     if (screenSize.getHeight() < 1000.0D) {
/* 163 */       height = 100 + height;
/*     */     }
/* 165 */     return height;
/*     */   }
/*     */   
/*     */   public static int getDialog_Width() {
/* 169 */     return getScreenWidth() * 6 / 10;
/*     */   }
/*     */   
/*     */   public static int getDialog_Height() {
/* 173 */     return getScreenHeight() / 2;
/*     */   }
/*     */   
/*     */   public static int getX_Dialog() {
/* 177 */     return screenWidth * 4 / 10;
/*     */   }
/*     */   
/*     */   public static int getY_Dialog() {
/* 181 */     return screenHeight * 4 / 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dimension getScreenSize(Window window) {
/* 191 */     windowComponent = window;
/* 192 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 193 */     screenWidth = screenSize.width * 9 / 10;
/* 194 */     screenHeight = screenSize.height * 8 / 10;
/* 195 */     return new Dimension(screenWidth, screenHeight);
/*     */   }
/*     */   
/*     */   public static Dimension getAppSize() {
/* 199 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 200 */     screenWidth = screenSize.width * 9 / 10;
/* 201 */     screenHeight = screenSize.height * 8 / 10;
/* 202 */     return new Dimension(screenWidth, screenHeight);
/*     */   }
/*     */   
/*     */   public static int getMinimalScreenSize_Width() {
/* 206 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 207 */     double width = screenSize.getWidth() * 5.0D / 8.0D;
/*     */     
/* 209 */     return (int)width;
/*     */   }
/*     */   
/*     */   public static int getMinimalScreenSize_Height() {
/* 213 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 214 */     double height = screenSize.getHeight() * 10.0D / 17.0D;
/*     */     
/* 216 */     return (int)height;
/*     */   }
/*     */   
/*     */   public static void setWindowComponent(Window window) {
/* 220 */     windowComponent = window;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveNewScreenSize(int width, int height) {
/* 225 */     screenWidth = width;
/* 226 */     screenHeight = height;
/*     */   }
/*     */   
/*     */   public static int getScreenWidth() {
/* 230 */     return screenWidth;
/*     */   }
/*     */   
/*     */   public static int getScreenHeight() {
/* 234 */     return screenHeight;
/*     */   }
/*     */   
/*     */   public static int getXInitial(Window window) {
/* 238 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 239 */     return screenSize.width * 1 / 3;
/*     */   }
/*     */   
/*     */   public static int getYInitial(Window window) {
/* 243 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 244 */     return screenSize.height * 1 / 3;
/*     */   }
/*     */   
/*     */   public static int getX_POS(Window window) {
/* 248 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 249 */     return screenSize.width * 1 / 18;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getY_POS(Window window) {
/* 254 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 255 */     return screenSize.height * 1 / 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNumber(String value) {
/*     */     try {
/* 261 */       Integer.parseInt(value);
/* 262 */       return true;
/* 263 */     } catch (NumberFormatException e) {
/* 264 */       log.error("Exception in isNumber method() :" + e.getMessage());
/* 265 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScreenSizeGreater(Window window) {
/* 276 */     Dimension screenSize = window.getToolkit().getScreenSize();
/* 277 */     if (screenSize.width >= 1280) {
/* 278 */       return true;
/*     */     }
/* 280 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swin\\util\SwingUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */