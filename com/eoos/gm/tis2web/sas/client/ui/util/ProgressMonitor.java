/*     */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class ProgressMonitor {
/*     */   private Component parent;
/*     */   private String title;
/*     */   
/*     */   private class ProgressOptionPane extends JOptionPane {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     ProgressOptionPane(Object messageList) {
/*  33 */       super(messageList, 1, -1, (Icon)null, new Object[] { ProgressMonitor.access$000(this$0) }, (Object)null);
/*     */     }
/*     */     
/*     */     public int getMaxCharactersPerLineCount() {
/*  37 */       return 60;
/*     */     }
/*     */     
/*     */     public JDialog createDialog(Component parentComponent, String title) {
/*  41 */       Frame frame = JOptionPane.getFrameForComponent(parentComponent);
/*  42 */       final JDialog dialog = new JDialog(frame, title, false);
/*  43 */       Container contentPane = dialog.getContentPane();
/*     */       
/*  45 */       GridBagLayout layout = new GridBagLayout();
/*  46 */       contentPane.setLayout(layout);
/*     */       
/*  48 */       GridBagConstraints constraints = new GridBagConstraints();
/*  49 */       constraints.insets = new Insets(5, 5, 5, 5);
/*     */       
/*  51 */       contentPane.add(this, constraints);
/*  52 */       dialog.pack();
/*  53 */       dialog.setLocationRelativeTo(parentComponent);
/*  54 */       dialog.addWindowListener(new WindowAdapter() {
/*     */             boolean gotFocus = false;
/*     */             
/*     */             public void windowClosing(WindowEvent we) {
/*  58 */               ProgressMonitor.ProgressOptionPane.this.setValue((Object)null);
/*     */             }
/*     */ 
/*     */             
/*     */             public void windowActivated(WindowEvent we) {
/*  63 */               if (!this.gotFocus) {
/*  64 */                 ProgressMonitor.ProgressOptionPane.this.selectInitialValue();
/*  65 */                 this.gotFocus = true;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  70 */       addPropertyChangeListener(new PropertyChangeListener() {
/*     */             public void propertyChange(PropertyChangeEvent event) {
/*  72 */               if (dialog.isVisible() && event.getSource() == ProgressMonitor.ProgressOptionPane.this && (event.getPropertyName().equals("value") || event.getPropertyName().equals("inputValue"))) {
/*  73 */                 dialog.setVisible(false);
/*  74 */                 dialog.dispose();
/*     */               } 
/*     */             }
/*     */           });
/*  78 */       return dialog;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private JDialog dialog = null;
/*     */   
/*     */   private JLabel message;
/*     */   
/*     */   private JLabel note;
/*     */   
/*     */   private JProgressBar progressBar;
/*     */   
/*     */   private JOptionPane pane;
/*     */   
/*     */   private JButton cancel;
/*     */   
/*     */   private boolean cancelled = false;
/*     */   
/*     */   public ProgressMonitor(Component parent, String title) {
/* 101 */     this(parent, title, "", "", -1);
/*     */   }
/*     */   
/*     */   public ProgressMonitor(Component parent, String title, String message) {
/* 105 */     this(parent, title, message, "", -1);
/*     */   }
/*     */   
/*     */   public ProgressMonitor(Component parent, String title, String message, String note, int maximum) {
/* 109 */     this.parent = parent;
/* 110 */     this.title = title;
/* 111 */     this.message = new JLabel(message);
/* 112 */     this.message.setForeground(Color.black);
/* 113 */     this.note = new JLabel(note);
/* 114 */     this.progressBar = new JProgressBar();
/* 115 */     setMaximum(maximum);
/*     */     
/* 117 */     this.cancel = new JButton(UIManager.getString("OptionPane.cancelButtonText"));
/* 118 */     this.cancel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 120 */             ProgressMonitor.this.onCancel();
/*     */           }
/*     */         });
/*     */     
/* 124 */     this.pane = new ProgressOptionPane(new Object[] { this.message, this.note, this.progressBar });
/* 125 */     show();
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/* 129 */     this.message.setText(message);
/*     */   }
/*     */   
/*     */   public void setNote(String note) {
/* 133 */     this.note.setText(note);
/*     */   }
/*     */   
/*     */   public void setMaximum(int max) {
/* 137 */     this.progressBar.setMaximum(Math.max(10, max));
/*     */   }
/*     */   
/*     */   public int getMaximum() {
/* 141 */     return this.progressBar.getMaximum();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProgress(int progress) {
/* 146 */     this.progressBar.setValue(progress % (getMaximum() + 1));
/*     */   }
/*     */   
/*     */   public void incProgress() {
/* 150 */     setProgress(getProgress() + 1);
/*     */   }
/*     */   
/*     */   public void setFinished() {
/* 154 */     setProgress(getMaximum());
/*     */   }
/*     */   
/*     */   public int getProgress() {
/* 158 */     return this.progressBar.getValue();
/*     */   }
/*     */   
/*     */   private void onCancel() {
/* 162 */     this.cancelled = true;
/* 163 */     close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void show() {
/* 168 */     if (this.dialog == null) {
/* 169 */       this.dialog = this.pane.createDialog(this.parent, this.title);
/*     */     }
/*     */     
/* 172 */     this.dialog.setVisible(true);
/*     */   }
/*     */   
/*     */   public void close() {
/* 176 */     if (this.dialog != null) {
/* 177 */       this.dialog.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCanceled() {
/* 182 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws InterruptedException {
/* 187 */     JFrame testFrame = new JFrame();
/* 188 */     testFrame.setBounds(0, 0, 200, 200);
/* 189 */     ProgressMonitor pm = new ProgressMonitor(testFrame, "Progress...");
/* 190 */     pm.setMaximum(30);
/* 191 */     pm.setNote("Step1");
/*     */     int i;
/* 193 */     for (i = 0; i < 2000; i++) {
/* 194 */       pm.incProgress();
/* 195 */       Thread.sleep(10L);
/*     */     } 
/* 197 */     pm.setNote("step2");
/* 198 */     for (i = 0; i < 20; i++) {
/* 199 */       pm.incProgress();
/* 200 */       Thread.sleep(500L);
/*     */     } 
/*     */     
/* 203 */     pm.setNote("step3");
/* 204 */     for (i = 0; i < 20; i++) {
/* 205 */       pm.setProgress(i);
/* 206 */       Thread.sleep(500L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\ProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */