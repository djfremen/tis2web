/*    */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*    */ 
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.util.Stack;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PanelStackImpl
/*    */   extends JPanel
/*    */   implements PanelStack
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private Stack panelHistory = new Stack();
/*    */   
/* 18 */   private JPanel current = null;
/*    */   
/* 20 */   private final GridBagConstraints CONSTRAINTS = new GridBagConstraints();
/*    */ 
/*    */   
/*    */   public PanelStackImpl() {
/* 24 */     GridBagLayout layout = new GridBagLayout();
/* 25 */     setLayout(layout);
/* 26 */     this.CONSTRAINTS.fill = 1;
/* 27 */     this.CONSTRAINTS.weightx = 1.0D;
/* 28 */     this.CONSTRAINTS.weighty = 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public void push(JPanel panel) {
/* 33 */     if (this.current != null) {
/* 34 */       remove(this.current);
/* 35 */       this.panelHistory.push(this.current);
/*    */     } 
/* 37 */     this.current = panel;
/* 38 */     add(this.current, this.CONSTRAINTS);
/*    */     
/* 40 */     validate();
/*    */   }
/*    */   
/*    */   public JPanel pop() {
/* 44 */     JPanel retValue = null;
/* 45 */     if (this.current != null) {
/* 46 */       remove(this.current);
/* 47 */       retValue = this.current;
/* 48 */       if (this.panelHistory.size() > 0) {
/* 49 */         this.current = this.panelHistory.pop();
/* 50 */         add(this.current, this.CONSTRAINTS);
/*    */       } else {
/* 52 */         this.current = null;
/*    */       } 
/*    */     } 
/* 55 */     validate();
/* 56 */     invalidate();
/* 57 */     repaint();
/*    */     
/* 59 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 64 */     this.panelHistory.clear();
/* 65 */     removeAll();
/* 66 */     validate();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     return super.toString();
/*    */   }
/*    */   
/*    */   public JPanel peek() {
/* 74 */     return this.current;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 78 */     while (pop() != null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\PanelStackImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */