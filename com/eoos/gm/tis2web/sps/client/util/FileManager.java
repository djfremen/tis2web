/*    */ package com.eoos.gm.tis2web.sps.client.util;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.FontMetrics;
/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileManager
/*    */ {
/*    */   public static void openFileChooserActionPerformed(File defaultFile, JTextField u_txtArea, JPanel mainPanel, String[] extensionsName) {
/* 21 */     String currDir = "";
/* 22 */     if (defaultFile != null) {
/* 23 */       currDir = defaultFile.getParent();
/*    */     }
/* 25 */     SecurityManager secMan = System.getSecurityManager();
/* 26 */     System.setSecurityManager(null);
/* 27 */     JFileChooser dlgOpen = new JFileChooser(currDir);
/* 28 */     if (extensionsName != null && extensionsName[0].length() == 0) {
/* 29 */       dlgOpen.setFileSelectionMode(1);
/* 30 */       extensionsName = null;
/*    */     } else {
/* 32 */       dlgOpen.setFileSelectionMode(2);
/*    */     } 
/* 34 */     if (extensionsName != null) {
/* 35 */       ExtensionFileFilter filter = new ExtensionFileFilter(extensionsName);
/* 36 */       FileFilter allFileFilter = dlgOpen.getAcceptAllFileFilter();
/* 37 */       dlgOpen.removeChoosableFileFilter(allFileFilter);
/* 38 */       dlgOpen.addChoosableFileFilter(filter);
/*    */     } 
/*    */     
/* 41 */     if (defaultFile != null) {
/* 42 */       dlgOpen.setSelectedFile(defaultFile);
/*    */     }
/*    */     
/* 45 */     if (dlgOpen.showOpenDialog(mainPanel) == 0) {
/* 46 */       String path = dlgOpen.getSelectedFile().getPath();
/* 47 */       u_txtArea.setText(path);
/* 48 */       u_txtArea.setToolTipText(path);
/* 49 */       u_txtArea.setHorizontalAlignment(2);
/* 50 */       u_txtArea.setCaretPosition(0);
/* 51 */       Font cFont = u_txtArea.getFont();
/* 52 */       FontMetrics fm = u_txtArea.getFontMetrics(cFont);
/*    */       
/* 54 */       int width = fm.stringWidth(u_txtArea.getText());
/* 55 */       int max = 0;
/* 56 */       max = Math.max(max, width);
/*    */     } 
/*    */     
/* 59 */     System.setSecurityManager(secMan);
/*    */   }
/*    */   
/*    */   public static boolean deleteDirectory(File path) {
/* 63 */     if (path.exists()) {
/* 64 */       File[] files = path.listFiles();
/* 65 */       for (int i = 0; i < files.length; i++) {
/* 66 */         if (files[i].isDirectory()) {
/* 67 */           deleteDirectory(files[i]);
/*    */         } else {
/* 69 */           files[i].delete();
/*    */         } 
/*    */       } 
/*    */     } 
/* 73 */     return path.delete();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\FileManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */