/*     */ package com.eoos.scsm.v2.swing;
/*     */ 
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JFileInput
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JTextField input;
/*     */   private JButton buttonChooser;
/*  36 */   private transient IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */   
/*     */   public JFileInput(String file, final I18NSupport.FixedLocale i18n) {
/*  39 */     super(new GridBagLayout());
/*     */     
/*  41 */     GridBagConstraints c = new GridBagConstraints();
/*  42 */     c.gridy = 0;
/*  43 */     c.insets = new Insets(0, 0, 0, 0);
/*  44 */     c.fill = 2;
/*  45 */     c.weightx = 1.0D;
/*     */     
/*  47 */     this.input = new JTextField();
/*  48 */     this.input.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void removeUpdate(DocumentEvent e) {
/*  51 */             JFileInput.this.onModification();
/*     */           }
/*     */           
/*     */           public void insertUpdate(DocumentEvent e) {
/*  55 */             JFileInput.this.onModification();
/*     */           }
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {
/*  59 */             JFileInput.this.onModification();
/*     */           }
/*     */         });
/*  62 */     if (!Util.isNullOrEmpty(file)) {
/*  63 */       this.input.setText(file);
/*     */     }
/*  65 */     add(this.input, c);
/*     */     
/*  67 */     c.weightx = 0.0D;
/*  68 */     this.buttonChooser = new JButton("...")
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Dimension getPreferredSize() {
/*  73 */           Dimension dim = super.getPreferredSize();
/*  74 */           Dimension dim2 = JFileInput.this.input.getPreferredSize();
/*  75 */           return new Dimension(dim.width, dim2.height);
/*     */         }
/*     */       };
/*     */     
/*  79 */     this.buttonChooser.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  82 */             JFileChooser chooser = JFileInput.this.createFileChooser(JFileInput.this.input.getText());
/*  83 */             if (chooser.showDialog(JFileInput.this, i18n.getText("select")) == 0) {
/*  84 */               JFileInput.this.input.setText(chooser.getSelectedFile().getPath());
/*     */             }
/*     */           }
/*     */         });
/*  88 */     add(this.buttonChooser, c);
/*  89 */     Border border = this.input.getBorder();
/*     */     
/*  91 */     this.buttonChooser.setBorder(border);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JFileChooser createFileChooser(String currentValue) {
/*  97 */     JFileChooser ret = new JFileChooser(currentValue);
/*  98 */     ret.setFileSelectionMode(2);
/*  99 */     return ret;
/*     */   }
/*     */   public static interface Observer {
/*     */     void onModification(); }
/*     */   public void setFile(String file) {
/* 104 */     this.input.setText(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFile() {
/* 109 */     return this.input.getText();
/*     */   }
/*     */   
/*     */   private void onModification() {
/* 113 */     this.observableSupport.notifyObservers(new Notification()
/*     */         {
/*     */           public void notify(Object observer) {
/* 116 */             ((JFileInput.Observer)observer).onModification();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void addObserver(Observer observer) {
/* 122 */     this.observableSupport.addObserver(observer);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\JFileInput.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */