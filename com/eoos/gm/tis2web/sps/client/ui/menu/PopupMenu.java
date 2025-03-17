/*    */ package com.eoos.gm.tis2web.sps.client.ui.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.VINJComboBox;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.awt.Component;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.JTextField;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PopupMenu
/*    */ {
/*    */   protected JPopupMenu pMenu;
/*    */   protected JMenuItem menuItemCopy;
/*    */   protected JMenuItem menuItemPaste;
/*    */   protected Component component;
/* 27 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/* 29 */   private static final Logger log = Logger.getLogger(PopupMenu.class);
/*    */   
/*    */   public PopupMenu(Component comp) {
/*    */     try {
/* 33 */       this.component = comp;
/* 34 */       if (comp instanceof VINJComboBox) {
/* 35 */         this.component = ((VINJComboBox)comp).getEditor().getEditorComponent();
/*    */       }
/* 37 */       createPopupMenu();
/* 38 */       addActionListener();
/* 39 */     } catch (Exception ex) {
/* 40 */       log.error("unable to create a pop up menu-error:" + ex, ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void createPopupMenu() {
/* 45 */     this.pMenu = new JPopupMenu();
/* 46 */     this.menuItemCopy = new JMenuItem(resourceProvider.getLabel(null, "copy"));
/* 47 */     this.pMenu.add(this.menuItemCopy);
/* 48 */     this.menuItemPaste = new JMenuItem(resourceProvider.getLabel(null, "paste"));
/* 49 */     this.pMenu.add(this.menuItemPaste);
/*    */   }
/*    */   
/*    */   protected void addActionListener() {
/* 53 */     this.menuItemCopy.addActionListener(new ActionListener() {
/*    */           public void actionPerformed(ActionEvent e) {
/*    */             try {
/* 56 */               ((JTextField)PopupMenu.this.component).selectAll();
/* 57 */               if (((JTextField)PopupMenu.this.component).getSelectedText() != null) {
/* 58 */                 ((JTextField)PopupMenu.this.component).copy();
/*    */               }
/* 60 */             } catch (Exception ex) {
/* 61 */               PopupMenu.log.error("unable to execute action copy -error:" + ex, ex);
/*    */             } 
/*    */           }
/*    */         });
/*    */     
/* 66 */     this.menuItemPaste.addActionListener(new ActionListener() {
/*    */           public void actionPerformed(ActionEvent e) {
/*    */             try {
/* 69 */               ((JTextField)PopupMenu.this.component).paste();
/* 70 */             } catch (Exception ex) {
/* 71 */               PopupMenu.log.error("unable to execute action paste -error:" + ex, ex);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public JPopupMenu getJPopupMenu() {
/* 78 */     return this.pMenu;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\menu\PopupMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */