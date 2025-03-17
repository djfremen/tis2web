/*    */ package com.eoos.gm.tis2web.frame.dwnld.install.ui;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Frame;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ 
/*    */ public class SummaryDialog
/*    */   extends JDialog
/*    */ {
/*    */   static final long serialVersionUID = 2008112500001L;
/*    */   private SummaryPanel summaryPanel;
/*    */   
/*    */   private SummaryDialog(Frame owner, SummaryPanel.Callback callback) {
/* 19 */     super(owner);
/* 20 */     setTitle(callback.getLabel("installation.summary"));
/*    */     
/* 22 */     addWindowListener(new WindowAdapter()
/*    */         {
/*    */           public void windowClosing(WindowEvent e) {
/* 25 */             SummaryDialog.this.onClose();
/*    */           }
/*    */         });
/*    */     
/* 29 */     getContentPane().setLayout(new BorderLayout());
/* 30 */     getContentPane().add(getSummaryPanel(callback), "Center");
/*    */     
/* 32 */     pack();
/*    */   }
/*    */   
/*    */   private SummaryPanel getSummaryPanel(SummaryPanel.Callback callback) {
/* 36 */     if (this.summaryPanel == null) {
/* 37 */       this.summaryPanel = new SummaryPanel(callback) {
/*    */           private static final long serialVersionUID = 1L;
/*    */           
/*    */           protected void onClose() {
/* 41 */             SummaryDialog.this.onClose();
/*    */           }
/*    */         };
/*    */     }
/* 45 */     return this.summaryPanel;
/*    */   }
/*    */   
/*    */   protected void onClose() {
/* 49 */     dispose();
/*    */   }
/*    */   
/*    */   public static void show(final JFrame owner, final SummaryPanel.Callback callback) {
/* 53 */     final Object sync = new Object();
/* 54 */     synchronized (sync) {
/* 55 */       SwingUtilities.invokeLater(new Runnable()
/*    */           {
/*    */             public void run() {
/* 58 */               SummaryDialog dialog = new SummaryDialog(owner, callback) {
/*    */                   private static final long serialVersionUID = 1L;
/*    */                   
/*    */                   protected void onClose() {
/* 62 */                     super.onClose();
/* 63 */                     synchronized (sync) {
/* 64 */                       sync.notify();
/*    */                     } 
/*    */                   }
/*    */                 };
/* 68 */               dialog.setModal(true);
/* 69 */               dialog.setLocationRelativeTo(owner);
/* 70 */               dialog.setVisible(true);
/*    */             }
/*    */           });
/*    */       try {
/* 74 */         sync.wait();
/* 75 */       } catch (InterruptedException e) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\instal\\ui\SummaryDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */