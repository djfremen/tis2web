/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class ServerDownloadDlg extends JDialog implements NotificationHandler {
/*     */   public ServerDownloadDlg(Frame parent, boolean modal) {
/*  23 */     super(parent, modal);
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
/* 374 */     this.isAborted = false;
/* 375 */     this.resourceBundle = null;
/*     */     SDNotificationServer.getInstance().register(24L, this);
/*     */     SDNotificationServer.getInstance().register(25L, this);
/*     */     SDNotificationServer.getInstance().register(26L, this);
/*     */     SDNotificationServer.getInstance().register(27L, this);
/*     */     SDNotificationServer.getInstance().register(28L, this);
/*     */     SDNotificationServer.getInstance().register(29L, this);
/*     */     initComponents();
/*     */     setDefaultCloseOperation(0);
/*     */   }
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JLabel jBytesread;
/*     */   private JButton jCancelButton;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jMessage;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanel4;
/*     */   private JPanel jPanel5;
/*     */   private JPanel jPanel6;
/*     */   private JPanel jPanel7;
/*     */   private JPanel jPanelC;
/*     */   private JPanel jPanelC1;
/*     */   private JPanel jPanelDown;
/*     */   private JPanel jPanelE;
/*     */   private JPanel jPanelE1;
/*     */   private JPanel jPanelN;
/*     */   private JPanel jPanelN1;
/*     */   private JPanel jPanelS;
/*     */   private JPanel jPanelS1;
/*     */   private JPanel jPanelUp;
/*     */   private JPanel jPanelW;
/*     */   private JPanel jPanelW1;
/*     */   private JProgressBar jProgressDown;
/*     */   private JProgressBar jProgressFile;
/*     */   private boolean isAborted;
/*     */   private ResourceBundle resourceBundle;
/*     */   
/*     */   private void initComponents() {
/*     */     this.jPanelUp = new JPanel();
/*     */     this.jPanelN = new JPanel();
/*     */     this.jPanelW = new JPanel();
/*     */     this.jPanelS = new JPanel();
/*     */     this.jPanelE = new JPanel();
/*     */     this.jPanelC = new JPanel();
/*     */     this.jPanel1 = new JPanel();
/*     */     this.jLabel1 = new JLabel();
/*     */     this.jProgressFile = new JProgressBar();
/*     */     this.jPanel2 = new JPanel();
/*     */     this.jLabel2 = new JLabel();
/*     */     this.jProgressDown = new JProgressBar();
/*     */     this.jPanel3 = new JPanel();
/*     */     this.jPanelDown = new JPanel();
/*     */     this.jPanelN1 = new JPanel();
/*     */     this.jPanelW1 = new JPanel();
/*     */     this.jPanelS1 = new JPanel();
/*     */     this.jPanelE1 = new JPanel();
/*     */     this.jPanelC1 = new JPanel();
/*     */     this.jPanel4 = new JPanel();
/*     */     this.jMessage = new JLabel();
/*     */     this.jPanel6 = new JPanel();
/*     */     this.jBytesread = new JLabel();
/*     */     this.jPanel5 = new JPanel();
/*     */     this.jPanel7 = new JPanel();
/*     */     this.jCancelButton = new JButton();
/*     */     getContentPane().setLayout(new GridBagLayout());
/*     */     setTitle(getResourceBundle().getString("IDS_TITLE_SRV_DOWN"));
/*     */     setModal(true);
/*     */     setResizable(false);
/*     */     this.jPanelUp.setLayout(new BorderLayout());
/*     */     this.jPanelUp.setBorder(new TitledBorder(getResourceBundle().getString("IDS_SRVDOWN_PROGRESS_PANEL")));
/*     */     this.jPanelUp.add(this.jPanelN, "North");
/*     */     this.jPanelUp.add(this.jPanelW, "West");
/*     */     this.jPanelUp.add(this.jPanelS, "South");
/*     */     this.jPanelUp.add(this.jPanelE, "East");
/*     */     this.jPanelC.setLayout(new GridBagLayout());
/*     */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 11;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanelC.add(this.jPanel1, gridBagConstraints);
/*     */     this.jLabel1.setText(getResourceBundle().getString("IDS_FILE_DOWN_PROGRESS"));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jLabel1, gridBagConstraints);
/*     */     this.jProgressFile.setStringPainted(true);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanelC.add(this.jProgressFile, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanelC.add(this.jPanel2, gridBagConstraints);
/*     */     this.jLabel2.setText(getResourceBundle().getString("IDS_DOWNLOAD_PROGRESS"));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jLabel2, gridBagConstraints);
/*     */     this.jProgressDown.setStringPainted(true);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanelC.add(this.jProgressDown, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 15;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     this.jPanelC.add(this.jPanel3, gridBagConstraints);
/*     */     this.jPanelUp.add(this.jPanelC, "Center");
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 11;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     getContentPane().add(this.jPanelUp, gridBagConstraints);
/*     */     this.jPanelDown.setLayout(new BorderLayout());
/*     */     this.jPanelDown.setBorder(new TitledBorder(getResourceBundle().getString("IDS_STATUS_PANEL")));
/*     */     this.jPanelDown.add(this.jPanelN1, "North");
/*     */     this.jPanelDown.add(this.jPanelW1, "West");
/*     */     this.jPanelDown.add(this.jPanelS1, "South");
/*     */     this.jPanelDown.add(this.jPanelE1, "East");
/*     */     this.jPanelC1.setLayout(new GridBagLayout());
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 2;
/*     */     gridBagConstraints.anchor = 11;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC1.add(this.jPanel4, gridBagConstraints);
/*     */     this.jMessage.setText("                                      ");
/*     */     this.jMessage.setBorder(new BevelBorder(1));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 3.0D;
/*     */     gridBagConstraints.weighty = 3.0D;
/*     */     this.jPanelC1.add(this.jMessage, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 2;
/*     */     this.jPanelC1.add(this.jPanel6, gridBagConstraints);
/*     */     this.jBytesread.setText("                                                                                                                   ");
/*     */     this.jBytesread.setBorder(new BevelBorder(1));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weighty = 3.0D;
/*     */     this.jPanelC1.add(this.jBytesread, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 2;
/*     */     gridBagConstraints.anchor = 15;
/*     */     this.jPanelC1.add(this.jPanel5, gridBagConstraints);
/*     */     this.jPanelDown.add(this.jPanelC1, "Center");
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weighty = 1.0D;
/*     */     getContentPane().add(this.jPanelDown, gridBagConstraints);
/*     */     this.jPanel7.setLayout(new FlowLayout(2));
/*     */     this.jCancelButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jCancelButton.setText(getResourceBundle().getString("IDS_BTN_CANCEL"));
/*     */     this.jCancelButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             ServerDownloadDlg.this.jCancelButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanel7.add(this.jCancelButton);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 15;
/*     */     getContentPane().add(this.jPanel7, gridBagConstraints);
/*     */     pack();
/*     */   }
/*     */   
/*     */   private void jCancelButtonActionPerformed(ActionEvent evt) {
/*     */     this.jCancelButton.setEnabled(false);
/*     */     this.isAborted = true;
/*     */   }
/*     */   
/*     */   public boolean OnEndDown() {
/*     */     setVisible(false);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean OnEndFileDown(int iFiles) {
/*     */     if (this.isAborted)
/*     */       return false; 
/*     */     this.jProgressDown.getMaximum();
/*     */     this.jProgressFile.setValue(this.jProgressFile.getMaximum());
/*     */     this.jProgressDown.setValue(iFiles);
/*     */     String msg = getResourceBundle().getString("IDS_BYTESDOWNFROM");
/*     */     String[] params = new String[2];
/*     */     params[0] = Integer.toString(this.jProgressFile.getMaximum());
/*     */     params[1] = Integer.toString(this.jProgressFile.getMaximum());
/*     */     this.jBytesread.setText(MessageFormater.FormatMsg(msg, params));
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean OnProgressFileDown(int bytes) {
/*     */     if (this.isAborted)
/*     */       return false; 
/*     */     this.jProgressFile.setValue(bytes);
/*     */     String msg = getResourceBundle().getString("IDS_BYTESDOWNFROM");
/*     */     String[] params = new String[2];
/*     */     params[0] = Integer.toString(bytes);
/*     */     params[1] = Integer.toString(this.jProgressFile.getMaximum());
/*     */     this.jBytesread.setText(MessageFormater.FormatMsg(msg, params));
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean OnStartDown(int countFiles) {
/*     */     this.isAborted = false;
/*     */     this.jProgressDown.setMinimum(0);
/*     */     this.jProgressDown.setMaximum(countFiles);
/*     */     this.jProgressDown.setValue(0);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean OnStartFileDown(int totalBytes, String file) {
/*     */     if (this.isAborted)
/*     */       return false; 
/*     */     this.jProgressFile.setMinimum(0);
/*     */     this.jProgressFile.setMaximum(totalBytes);
/*     */     this.jProgressFile.setValue(0);
/*     */     String msg = getResourceBundle().getString("IDS_DOWNLOAD_FILE");
/*     */     String[] params = new String[1];
/*     */     params[0] = file;
/*     */     this.jMessage.setText(MessageFormater.FormatMsg(msg, params));
/*     */     msg = getResourceBundle().getString("IDS_BYTESDOWNFROM");
/*     */     params = new String[2];
/*     */     params[0] = "0";
/*     */     params[1] = Integer.toString(totalBytes);
/*     */     this.jBytesread.setText(MessageFormater.FormatMsg(msg, params));
/*     */     return true;
/*     */   }
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/*     */     if (this.resourceBundle == null)
/*     */       this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings"); 
/*     */     return this.resourceBundle;
/*     */   }
/*     */   
/*     */   public boolean OnStartDown() {
/*     */     this.isAborted = false;
/*     */     this.jCancelButton.setEnabled(true);
/*     */     setVisible(true);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void handleNotification(Notification msg) {
/*     */     try {
/*     */       SDEvent eve = (SDEvent)msg;
/*     */       switch ((int)eve.getType()) {
/*     */         case 24:
/*     */           eve.addParam(new Boolean(OnStartDown()));
/*     */           break;
/*     */         case 25:
/*     */           eve.addParam(new Boolean(OnStartDown(((Integer)eve.getParam(0)).intValue())));
/*     */           break;
/*     */         case 26:
/*     */           eve.addParam(new Boolean(OnStartFileDown(((Integer)eve.getParam(0)).intValue(), (String)eve.getParam(1))));
/*     */           break;
/*     */         case 27:
/*     */           eve.addParam(new Boolean(OnProgressFileDown(((Integer)eve.getParam(0)).intValue())));
/*     */           break;
/*     */         case 28:
/*     */           eve.addParam(new Boolean(OnEndFileDown(((Integer)eve.getParam(0)).intValue())));
/*     */           break;
/*     */         case 29:
/*     */           OnEndDown();
/*     */           break;
/*     */       } 
/*     */     } catch (Exception e) {
/*     */       System.out.println(getClass().getName() + ".handleNotification() - Exception caught: " + e.toString());
/*     */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ServerDownloadDlg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */