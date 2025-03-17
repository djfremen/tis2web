/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.util;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Window;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.FontUIResource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public static String fontName;
/*     */   public static Font selectedFont;
/*     */   public static Font specialFont;
/*     */   public static int specialMaxFontSize;
/*     */   public static int generalMaxFontSize;
/*     */   public static int generalDefaultFontSize;
/*     */   public static Window windowComponent;
/*     */   
/*     */   public static void setDefaultFont(Font ft) {
/*  40 */     UIDefaults uid = UIManager.getDefaults();
/*  41 */     FontUIResource fuir = new FontUIResource(ft);
/*     */ 
/*     */     
/*  44 */     for (Enumeration<E> e = uid.keys(); e.hasMoreElements(); ) {
/*  45 */       String strKey = e.nextElement().toString();
/*  46 */       Object obj = uid.get(strKey);
/*  47 */       if (obj instanceof FontUIResource) {
/*  48 */         uid.put(strKey, fuir); continue;
/*  49 */       }  if (obj instanceof Font) {
/*  50 */         uid.put(strKey, ft);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void initializeFont(Window window) {
/*  56 */     windowComponent = window;
/*  57 */     ClientSettings clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*     */     
/*  59 */     fontName = clientSettings.getProperty("font.name");
/*  60 */     if (fontName == null) {
/*  61 */       fontName = "Dialog";
/*     */     }
/*     */     
/*  64 */     String fontSizeString = clientSettings.getProperty("font.size");
/*  65 */     int fontSize = getGeneralDefaultFontSize();
/*  66 */     if (fontSizeString != null) {
/*  67 */       fontSize = Integer.parseInt(fontSizeString);
/*  68 */       int maxFontSize = getMaxFontSize();
/*  69 */       if (fontSize > maxFontSize) {
/*  70 */         fontSize = maxFontSize;
/*     */       }
/*     */     } 
/*  73 */     selectedFont = new Font(fontName, 0, fontSize);
/*  74 */     setDefaultFont(selectedFont);
/*  75 */     int fontSizeTitle = fontSize + 1;
/*  76 */     System.setProperty("font.size.title", String.valueOf(fontSizeTitle));
/*     */   }
/*     */   
/*     */   public static void setFontSize(JPanel panel) {
/*  80 */     Component[] comp = panel.getComponents();
/*  81 */     for (int i = 0; i < comp.length; i++) {
/*  82 */       if (comp[i] instanceof JPanel) {
/*  83 */         setFontSize((JPanel)comp[i]);
/*     */       } else {
/*  85 */         Font font = new Font(comp[i].getFont().getFontName(), comp[i].getFont().getStyle(), getSelectedFont().getSize());
/*  86 */         comp[i].setFont(font);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Font getSelectedFont() {
/*  92 */     return selectedFont;
/*     */   }
/*     */   
/*     */   public static Font getSpecialFont() {
/*  96 */     specialFont = new Font(fontName, 0, getSpecialFontSize());
/*  97 */     return specialFont;
/*     */   }
/*     */   
/*     */   public static int getSpecialFontSize() {
/* 101 */     int maxFontSize = getSpecialMaxFontSize();
/* 102 */     int specialFontSize = 0;
/* 103 */     if (getSelectedFont().getSize() > maxFontSize) {
/* 104 */       specialFontSize = maxFontSize;
/*     */     } else {
/* 106 */       specialFontSize = getSelectedFont().getSize();
/* 107 */     }  return specialFontSize;
/*     */   }
/*     */   
/*     */   public static int getMaxFontSize() {
/* 111 */     determineFontSize();
/* 112 */     return generalMaxFontSize;
/*     */   }
/*     */   
/*     */   public static int getSpecialMaxFontSize() {
/* 116 */     determineFontSize();
/* 117 */     return specialMaxFontSize;
/*     */   }
/*     */   
/*     */   public static int getGeneralDefaultFontSize() {
/* 121 */     determineFontSize();
/* 122 */     return generalDefaultFontSize;
/*     */   }
/*     */   
/*     */   protected static void determineFontSize() {
/* 126 */     if (windowComponent == null)
/*     */       return; 
/* 128 */     Dimension screenSize = windowComponent.getToolkit().getScreenSize();
/* 129 */     generalDefaultFontSize = 14;
/* 130 */     generalMaxFontSize = 26;
/* 131 */     specialMaxFontSize = 15;
/*     */     
/* 133 */     if (screenSize.getWidth() == 800.0D && screenSize.getHeight() == 600.0D) {
/* 134 */       generalDefaultFontSize = 14;
/* 135 */       generalMaxFontSize = 26;
/* 136 */       specialMaxFontSize = 13;
/*     */       return;
/*     */     } 
/* 139 */     if (screenSize.getWidth() == 1024.0D && screenSize.getHeight() == 768.0D) {
/* 140 */       generalDefaultFontSize = 17;
/* 141 */       generalMaxFontSize = 33;
/* 142 */       specialMaxFontSize = 20;
/*     */       return;
/*     */     } 
/* 145 */     if (screenSize.getWidth() == 1152.0D && screenSize.getHeight() == 864.0D) {
/* 146 */       generalDefaultFontSize = 16;
/* 147 */       generalMaxFontSize = 34;
/* 148 */       specialMaxFontSize = 23;
/*     */       return;
/*     */     } 
/* 151 */     if (screenSize.getWidth() == 1280.0D && screenSize.getHeight() == 768.0D) {
/* 152 */       generalDefaultFontSize = 15;
/* 153 */       generalMaxFontSize = 33;
/* 154 */       specialMaxFontSize = 18;
/*     */       return;
/*     */     } 
/* 157 */     if (screenSize.getWidth() == 1280.0D && screenSize.getHeight() == 800.0D) {
/* 158 */       generalDefaultFontSize = 15;
/* 159 */       generalMaxFontSize = 33;
/* 160 */       specialMaxFontSize = 20;
/*     */       return;
/*     */     } 
/* 163 */     if (screenSize.getWidth() == 1280.0D && screenSize.getHeight() == 1024.0D) {
/* 164 */       generalDefaultFontSize = 15;
/* 165 */       generalMaxFontSize = 35;
/* 166 */       specialMaxFontSize = 26;
/*     */       return;
/*     */     } 
/* 169 */     if (screenSize.getWidth() == 1600.0D && screenSize.getHeight() == 1000.0D) {
/* 170 */       generalDefaultFontSize = 15;
/* 171 */       generalMaxFontSize = 35;
/* 172 */       specialMaxFontSize = 27;
/*     */       return;
/*     */     } 
/* 175 */     if (screenSize.getWidth() >= 1600.0D && screenSize.getHeight() >= 1200.0D) {
/* 176 */       generalDefaultFontSize = 15;
/* 177 */       generalMaxFontSize = 45;
/* 178 */       specialMaxFontSize = 33;
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swin\\util\FontUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */