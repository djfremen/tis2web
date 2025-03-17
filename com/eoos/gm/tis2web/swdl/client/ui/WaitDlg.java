/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class WaitDlg extends JDialog {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   
/*     */   public WaitDlg(Frame parent, boolean modal) {
/*  18 */     super(parent, modal);
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
/* 116 */     this.resourceBundle = null;
/*     */     initComponents();
/*     */     setDefaultCloseOperation(0);
/*     */   }
/*     */   
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanelC;
/*     */   private JPanel jPanelE;
/*     */   private JPanel jPanelN;
/*     */   private JPanel jPanelS;
/*     */   private JPanel jPanelW;
/*     */   private ResourceBundle resourceBundle;
/*     */   
/*     */   private void initComponents() {
/*     */     this.jPanelN = new JPanel();
/*     */     this.jPanelE = new JPanel();
/*     */     this.jPanelW = new JPanel();
/*     */     this.jPanelS = new JPanel();
/*     */     this.jPanelC = new JPanel();
/*     */     this.jPanel1 = new JPanel();
/*     */     this.jLabel1 = new JLabel();
/*     */     this.jPanel2 = new JPanel();
/*     */     this.jLabel2 = new JLabel();
/*     */     this.jPanel3 = new JPanel();
/*     */     setTitle(getResourceBundle().getString("IDS_TITLE_SWDL"));
/*     */     setResizable(false);
/*     */     getContentPane().add(this.jPanelN, "North");
/*     */     getContentPane().add(this.jPanelE, "East");
/*     */     getContentPane().add(this.jPanelW, "West");
/*     */     getContentPane().add(this.jPanelS, "South");
/*     */     this.jPanelC.setLayout(new GridBagLayout());
/*     */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jPanel1, gridBagConstraints);
/*     */     this.jLabel1.setText(MessageFormater.FormatCR2BR(getResourceBundle().getString("IDS_PLEASE_WAIT")));
/*     */     this.jLabel1.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jLabel1, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelC.add(this.jPanel2, gridBagConstraints);
/*     */     this.jLabel2.setText(MessageFormater.FormatCR2BR(getResourceBundle().getString("IDS_READ_DEVICE")));
/*     */     this.jLabel2.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jLabel2, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelC.add(this.jPanel3, gridBagConstraints);
/*     */     getContentPane().add(this.jPanelC, "Center");
/*     */     pack();
/*     */   }
/*     */   
/*     */   public void setTextLabel2(String msg) {
/*     */     this.jLabel2.setText(MessageFormater.FormatCR2BR(msg));
/*     */     this.jLabel2.updateUI();
/*     */   }
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/*     */     if (this.resourceBundle == null)
/*     */       this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings"); 
/*     */     return this.resourceBundle;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\WaitDlg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */