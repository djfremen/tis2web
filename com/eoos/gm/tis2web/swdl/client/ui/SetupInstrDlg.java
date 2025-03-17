/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class SetupInstrDlg extends JDialog {
/*     */   public SetupInstrDlg(Dialog parent, boolean modal) {
/*  20 */     super(parent, modal);
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
/* 153 */     this.ignore = false;
/* 154 */     this.resourceBundle = null;
/*     */     initComponents();
/*     */     setDefaultCloseOperation(0);
/*     */   }
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JButton jButton1;
/*     */   private JButton jButton2;
/*     */   private JLabel jLabel1;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanel4;
/*     */   private JPanel jPanel5;
/*     */   private JPanel jPanel6;
/*     */   private JPanel jPanel7;
/*     */   private JPanel jPanel8;
/*     */   private JPanel jPanel9;
/*     */   private boolean ignore;
/*     */   private ResourceBundle resourceBundle;
/*     */   
/*     */   private void initComponents() {
/*     */     this.jPanel1 = new JPanel();
/*     */     this.jPanel2 = new JPanel();
/*     */     this.jPanel3 = new JPanel();
/*     */     this.jPanel4 = new JPanel();
/*     */     this.jPanel5 = new JPanel();
/*     */     this.jPanel6 = new JPanel();
/*     */     this.jLabel1 = new JLabel();
/*     */     this.jPanel9 = new JPanel();
/*     */     this.jPanel7 = new JPanel();
/*     */     this.jButton1 = new JButton();
/*     */     this.jPanel8 = new JPanel();
/*     */     this.jButton2 = new JButton();
/*     */     setTitle(getResourceBundle().getString("IDS_TITLE_SWDL"));
/*     */     setModal(true);
/*     */     setResizable(false);
/*     */     getContentPane().add(this.jPanel1, "North");
/*     */     getContentPane().add(this.jPanel2, "East");
/*     */     getContentPane().add(this.jPanel3, "South");
/*     */     getContentPane().add(this.jPanel4, "West");
/*     */     this.jPanel5.setLayout(new GridBagLayout());
/*     */     this.jPanel6.setLayout(new GridLayout(1, 0));
/*     */     String msg = getResourceBundle().getString("IDS_ERR_E1037");
/*     */     msg = MessageFormater.FormatMsgBR(msg, Integer.parseInt((String)SDCurrentContext.getInstance().getLocSettings().get("msgbox.line.charnr")));
/*     */     this.jLabel1.setText(msg);
/*     */     this.jLabel1.setHorizontalAlignment(0);
/*     */     this.jPanel6.add(this.jLabel1);
/*     */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanel5.add(this.jPanel6, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 2;
/*     */     this.jPanel5.add(this.jPanel9, gridBagConstraints);
/*     */     this.jPanel7.setLayout(new FlowLayout(2));
/*     */     this.jButton1.setText(getResourceBundle().getString("IDS_BTN_OK"));
/*     */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SetupInstrDlg.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanel7.add(this.jButton1);
/*     */     this.jPanel7.add(this.jPanel8);
/*     */     this.jButton2.setText(getResourceBundle().getString("IDS_BTN_IGNORE"));
/*     */     this.jButton2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SetupInstrDlg.this.jButton2ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanel7.add(this.jButton2);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 2;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanel5.add(this.jPanel7, gridBagConstraints);
/*     */     getContentPane().add(this.jPanel5, "Center");
/*     */     pack();
/*     */   }
/*     */   
/*     */   private void jButton2ActionPerformed(ActionEvent evt) {
/*     */     this.ignore = true;
/*     */     setVisible(false);
/*     */     dispose();
/*     */   }
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/*     */     this.ignore = false;
/*     */     setVisible(false);
/*     */     dispose();
/*     */   }
/*     */   
/*     */   public boolean Ignore() {
/*     */     return this.ignore;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/*     */     this.jLabel1.setText(text);
/*     */   }
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/*     */     if (this.resourceBundle == null)
/*     */       this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings"); 
/*     */     return this.resourceBundle;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SetupInstrDlg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */