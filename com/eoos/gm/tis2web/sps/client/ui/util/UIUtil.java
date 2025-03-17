/*    */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Point;
/*    */ import java.awt.Window;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIUtil
/*    */ {
/*    */   public static void setEnabled(Component component, boolean enabled) {
/* 19 */     if (component instanceof Container) {
/* 20 */       for (int i = 0; i < ((Container)component).getComponentCount(); i++) {
/* 21 */         setEnabled(((Container)component).getComponent(i), enabled);
/*    */       }
/*    */     }
/*    */     
/* 25 */     component.setEnabled(enabled);
/*    */   }
/*    */   
/*    */   public static Point getScreenCenterLocation(Window window) {
/* 29 */     Dimension dimScreen = window.getToolkit().getScreenSize();
/* 30 */     return getCenterLocation(window, dimScreen, null);
/*    */   }
/*    */   
/*    */   public static Point getCenterLocation(Window window, Window container) {
/* 34 */     return getCenterLocation(window, container.getSize(), container.getLocation());
/*    */   }
/*    */   
/*    */   public static Point getCenterLocation(Window window, Dimension containerDimension, Point containerLocation) {
/* 38 */     Dimension dimWindow = window.getSize();
/* 39 */     Point retValue = new Point();
/*    */     
/* 41 */     retValue.x = (containerDimension.width - dimWindow.width) / 2;
/* 42 */     retValue.y = (containerDimension.height - dimWindow.height) / 2;
/* 43 */     if (containerLocation != null) {
/* 44 */       retValue.x += containerLocation.x;
/* 45 */       retValue.y += containerLocation.y;
/*    */     } 
/* 47 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\UIUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */