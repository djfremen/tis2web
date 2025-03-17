/*     */ package com.eoos.scsm.v2.swing;
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
/*     */ import javax.swing.SwingUtilities;
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
/*  34 */       super(messageList, 1, -1, (Icon)null, new Object[] { ProgressMonitor.access$000(this$0) }, (Object)null);
/*     */     }
/*     */     
/*     */     public int getMaxCharactersPerLineCount() {
/*  38 */       return 60;
/*     */     }
/*     */     
/*     */     public JDialog createDialog(Component parentComponent, String title) {
/*  42 */       Frame frame = JOptionPane.getFrameForComponent(parentComponent);
/*  43 */       final JDialog dialog = new JDialog(frame, title, false);
/*  44 */       Container contentPane = dialog.getContentPane();
/*     */       
/*  46 */       GridBagLayout layout = new GridBagLayout();
/*  47 */       contentPane.setLayout(layout);
/*     */       
/*  49 */       GridBagConstraints constraints = new GridBagConstraints();
/*  50 */       constraints.insets = new Insets(5, 5, 5, 5);
/*     */       
/*  52 */       contentPane.add(this, constraints);
/*  53 */       dialog.pack();
/*  54 */       dialog.setLocationRelativeTo(parentComponent);
/*  55 */       dialog.addWindowListener(new WindowAdapter() {
/*     */             boolean gotFocus = false;
/*     */             
/*     */             public void windowClosing(WindowEvent we) {
/*  59 */               ProgressMonitor.ProgressOptionPane.this.setValue((Object)null);
/*     */             }
/*     */ 
/*     */             
/*     */             public void windowActivated(WindowEvent we) {
/*  64 */               if (!this.gotFocus) {
/*  65 */                 ProgressMonitor.ProgressOptionPane.this.selectInitialValue();
/*  66 */                 this.gotFocus = true;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  71 */       addPropertyChangeListener(new PropertyChangeListener() {
/*     */             public void propertyChange(PropertyChangeEvent event) {
/*  73 */               if (dialog.isVisible() && event.getSource() == ProgressMonitor.ProgressOptionPane.this && (event.getPropertyName().equals("value") || event.getPropertyName().equals("inputValue"))) {
/*  74 */                 dialog.setVisible(false);
/*  75 */                 dialog.dispose();
/*     */               } 
/*     */             }
/*     */           });
/*  79 */       return dialog;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private JDialog dialog = null;
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
/*     */   private ProgressMonitor(Component parent, String title) {
/* 102 */     this(parent, title, "", "", -1);
/*     */   }
/*     */   
/*     */   public static ProgressMonitor create(final Component parent, final String title) {
/* 106 */     final Object sync = new Object();
/* 107 */     final ProgressMonitor[] ret = new ProgressMonitor[1];
/* 108 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 111 */             ret[0] = new ProgressMonitor(parent, title);
/* 112 */             synchronized (sync) {
/* 113 */               sync.notify();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 118 */     synchronized (sync) {
/*     */       try {
/* 120 */         sync.wait();
/* 121 */       } catch (InterruptedException e) {
/* 122 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return ret[0];
/*     */   }
/*     */ 
/*     */   
/*     */   private ProgressMonitor(Component parent, String title, String message, String note, int maximum) {
/* 132 */     this.parent = parent;
/* 133 */     this.title = title;
/* 134 */     this.message = new JLabel(message);
/* 135 */     this.message.setForeground(Color.black);
/* 136 */     this.note = new JLabel(note);
/* 137 */     this.progressBar = new JProgressBar();
/* 138 */     setMaximum(maximum);
/*     */     
/* 140 */     this.cancel = new JButton(UIManager.getString("OptionPane.cancelButtonText"));
/* 141 */     this.cancel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 143 */             ProgressMonitor.this.onCancel();
/*     */           }
/*     */         });
/*     */     
/* 147 */     this.pane = new ProgressOptionPane(new Object[] { this.message, this.note, this.progressBar });
/* 148 */     show();
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/* 152 */     this.message.setText(message);
/* 153 */     this.dialog.pack();
/*     */   }
/*     */   
/*     */   public void setNote(String note) {
/* 157 */     this.note.setText(note);
/* 158 */     this.dialog.pack();
/*     */   }
/*     */   
/*     */   public void setMaximum(int max) {
/* 162 */     this.progressBar.setMaximum(Math.max(10, max));
/*     */   }
/*     */   
/*     */   public int getMaximum() {
/* 166 */     return this.progressBar.getMaximum();
/*     */   }
/*     */   
/*     */   public void setProgress(int progress) {
/* 170 */     this.progressBar.setValue(progress % (getMaximum() + 1));
/*     */   }
/*     */   
/*     */   public void incProgress() {
/* 174 */     setProgress(getProgress() + 1);
/*     */   }
/*     */   
/*     */   public void setFinished() {
/* 178 */     setProgress(getMaximum());
/*     */   }
/*     */   
/*     */   public int getProgress() {
/* 182 */     return this.progressBar.getValue();
/*     */   }
/*     */   
/*     */   private void onCancel() {
/* 186 */     this.cancelled = true;
/* 187 */     close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void show() {
/* 192 */     if (this.dialog == null) {
/* 193 */       this.dialog = this.pane.createDialog(this.parent, this.title);
/*     */     }
/*     */     
/* 196 */     this.dialog.pack();
/* 197 */     this.dialog.setVisible(true);
/*     */   }
/*     */   
/*     */   public void close() {
/* 201 */     if (this.dialog != null) {
/* 202 */       this.dialog.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCanceled() {
/* 207 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws InterruptedException {
/* 212 */     JFrame testFrame = new JFrame();
/* 213 */     testFrame.setBounds(0, 0, 200, 200);
/* 214 */     ProgressMonitor pm = new ProgressMonitor(testFrame, "Progress...");
/* 215 */     pm.setMaximum(15);
/* 216 */     pm.setNote("Step1");
/*     */     int i;
/* 218 */     for (i = 0; i < 20; i++) {
/* 219 */       pm.incProgress();
/* 220 */       Thread.sleep(100L);
/*     */     } 
/* 222 */     pm.setNote("step2");
/* 223 */     for (i = 0; i < 20; i++) {
/* 224 */       pm.incProgress();
/* 225 */       Thread.sleep(500L);
/*     */     } 
/*     */     
/* 228 */     pm.setNote("step3");
/* 229 */     for (i = 0; i < 20; i++) {
/* 230 */       pm.setProgress(i);
/* 231 */       Thread.sleep(500L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\ProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */