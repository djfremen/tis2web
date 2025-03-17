/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.BaudRate;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class SDSettingsDlg extends JDialog {
/*     */   public SDSettingsDlg(Frame parent, boolean modal, Device device) {
/*  30 */     super(parent, modal);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 636 */     this.resourceBundle = null;
/* 637 */     this.dev2port = new Hashtable<Object, Object>();
/* 638 */     this.dev2baudrate = new Hashtable<Object, Object>();
/*     */     initComponents();
/*     */     init(device);
/*     */   }
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JTextField jCachePath;
/*     */   private JTextField jCacheSize;
/*     */   private JButton jCancelButton;
/*     */   private JCheckBox jChk2Times;
/*     */   private JCheckBox jChkDebug;
/*     */   private JCheckBox jChkLog;
/*     */   private JComboBox jDevices;
/*     */   private JButton jDirButton;
/*     */   private JButton jDirCacheButton;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JTextField jLogFileName;
/*     */   private JButton jOKButton;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel10;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanel4;
/*     */   private JPanel jPanel5;
/*     */   private JPanel jPanel6;
/*     */   private JPanel jPanel7;
/*     */   private JPanel jPanel8;
/*     */   private JPanel jPanel9;
/*     */   private JPanel jPanelC;
/*     */   private JPanel jPanelCache;
/*     */   private JPanel jPanelE;
/*     */   private JPanel jPanelLog;
/*     */   private JPanel jPanelN;
/*     */   private JPanel jPanelS;
/*     */   private JPanel jPanelTool;
/*     */   private JPanel jPanelW;
/*     */   private JButton jTestButton;
/*     */   private JComboBox portComboBox;
/*     */   private JComboBox speedComboBox;
/*     */   private JButton jDelCacheButton;
/*     */   private JPanel jPanel23;
/*     */   private ResourceBundle resourceBundle;
/*     */   private Map dev2port;
/*     */   private Map dev2baudrate;
/*     */   
/*     */   private void initComponents() {
/*     */     this.jPanelS = new JPanel();
/*     */     this.jOKButton = new JButton();
/*     */     this.jCancelButton = new JButton();
/*     */     this.jPanel1 = new JPanel();
/*     */     this.jPanelN = new JPanel();
/*     */     this.jPanelE = new JPanel();
/*     */     this.jPanelW = new JPanel();
/*     */     this.jPanelC = new JPanel();
/*     */     this.jPanelTool = new JPanel();
/*     */     this.jLabel1 = new JLabel();
/*     */     this.jDevices = new JComboBox();
/*     */     this.jPanel3 = new JPanel();
/*     */     this.jPanel7 = new JPanel();
/*     */     this.jLabel2 = new JLabel();
/*     */     this.portComboBox = new JComboBox();
/*     */     this.jPanel2 = new JPanel();
/*     */     this.jPanel6 = new JPanel();
/*     */     this.jLabel3 = new JLabel();
/*     */     this.speedComboBox = new JComboBox();
/*     */     this.jPanel5 = new JPanel();
/*     */     this.jPanel9 = new JPanel();
/*     */     this.jPanel8 = new JPanel();
/*     */     this.jTestButton = new JButton();
/*     */     this.jPanel4 = new JPanel();
/*     */     this.jPanelLog = new JPanel();
/*     */     this.jChkLog = new JCheckBox();
/*     */     this.jChkDebug = new JCheckBox();
/*     */     this.jLabel4 = new JLabel();
/*     */     this.jLogFileName = new JTextField();
/*     */     this.jDirButton = new JButton();
/*     */     this.jPanelCache = new JPanel();
/*     */     this.jLabel5 = new JLabel();
/*     */     this.jCacheSize = new JTextField();
/*     */     this.jLabel6 = new JLabel();
/*     */     this.jPanel10 = new JPanel();
/*     */     this.jLabel7 = new JLabel();
/*     */     this.jDirCacheButton = new JButton();
/*     */     this.jCachePath = new JTextField();
/*     */     this.jChk2Times = new JCheckBox();
/*     */     this.jDelCacheButton = new JButton();
/*     */     this.jPanel23 = new JPanel();
/*     */     setTitle(getResourceBundle().getString("IDS_BTN_SETTINGS"));
/*     */     setResizable(false);
/*     */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent evt) {
/*     */             SDSettingsDlg.this.closeDialog(evt);
/*     */           }
/*     */         });
/*     */     this.jPanelS.setLayout(new FlowLayout(2));
/*     */     this.jOKButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jOKButton.setText(getResourceBundle().getString("IDS_BTN_OK"));
/*     */     this.jOKButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jOKButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanelS.add(this.jOKButton);
/*     */     this.jCancelButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jCancelButton.setText(getResourceBundle().getString("IDS_BTN_CANCEL"));
/*     */     this.jCancelButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jCancelButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanelS.add(this.jCancelButton);
/*     */     this.jPanelS.add(this.jPanel1);
/*     */     getContentPane().add(this.jPanelS, "South");
/*     */     getContentPane().add(this.jPanelN, "North");
/*     */     getContentPane().add(this.jPanelE, "East");
/*     */     getContentPane().add(this.jPanelW, "West");
/*     */     this.jPanelC.setLayout(new GridBagLayout());
/*     */     this.jPanelC.setPreferredSize(new Dimension(450, 300));
/*     */     this.jPanelTool.setLayout(new GridBagLayout());
/*     */     this.jPanelTool.setBorder(new TitledBorder(getResourceBundle().getString("IDS_TITLE_SETTINGS_TOOL")));
/*     */     this.jLabel1.setText(getResourceBundle().getString("IDS_DIAG_TOOL"));
/*     */     this.jLabel1.setHorizontalAlignment(0);
/*     */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelTool.add(this.jLabel1, gridBagConstraints);
/*     */     this.jDevices.setFont(new Font("Dialog", 0, 12));
/*     */     this.jDevices.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jDevicesActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelTool.add(this.jDevices, gridBagConstraints);
/*     */     this.jPanelTool.add(this.jPanel3, new GridBagConstraints());
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     this.jPanelTool.add(this.jPanel7, gridBagConstraints);
/*     */     this.jLabel2.setText(getResourceBundle().getString("IDS_PORT"));
/*     */     this.jLabel2.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelTool.add(this.jLabel2, gridBagConstraints);
/*     */     this.portComboBox.setFont(new Font("Dialog", 0, 12));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelTool.add(this.portComboBox, gridBagConstraints);
/*     */     this.jPanelTool.add(this.jPanel2, new GridBagConstraints());
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     this.jPanelTool.add(this.jPanel6, gridBagConstraints);
/*     */     this.jLabel3.setText(getResourceBundle().getString("IDS_SPEED"));
/*     */     this.jLabel3.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelTool.add(this.jLabel3, gridBagConstraints);
/*     */     this.speedComboBox.setFont(new Font("Dialog", 0, 12));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelTool.add(this.speedComboBox, gridBagConstraints);
/*     */     this.jPanelTool.add(this.jPanel5, new GridBagConstraints());
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     this.jPanelTool.add(this.jPanel9, gridBagConstraints);
/*     */     this.jPanelTool.add(this.jPanel8, new GridBagConstraints());
/*     */     this.jTestButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jTestButton.setText(getResourceBundle().getString("IDS_BTN_TEST"));
/*     */     this.jTestButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jTestButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     this.jPanelTool.add(this.jTestButton, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelTool.add(this.jPanel4, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridx = 0;
/*     */     gridBagConstraints.gridy = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 17;
/*     */     this.jPanelC.add(this.jPanelTool, gridBagConstraints);
/*     */     this.jPanelLog.setLayout(new GridBagLayout());
/*     */     this.jPanelLog.setBorder(new TitledBorder(getResourceBundle().getString("IDS_TITLE_SETTINGS_LOG")));
/*     */     this.jChkLog.setFont(new Font("Dialog", 0, 12));
/*     */     this.jChkLog.setText(getResourceBundle().getString("IDS_PERFORM_LOG"));
/*     */     this.jChkLog.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jChkLogActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelLog.add(this.jChkLog, gridBagConstraints);
/*     */     this.jChkDebug.setFont(new Font("Dialog", 0, 12));
/*     */     this.jChkDebug.setText(getResourceBundle().getString("IDS_LOG_DEBUG"));
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelLog.add(this.jChkDebug, gridBagConstraints);
/*     */     this.jLabel4.setText(getResourceBundle().getString("IDS_LOGFILE"));
/*     */     this.jPanelLog.add(this.jLabel4, new GridBagConstraints());
/*     */     this.jLogFileName.setEnabled(false);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 2;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelLog.add(this.jLogFileName, gridBagConstraints);
/*     */     this.jDirButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jDirButton.setText("...");
/*     */     this.jDirButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jDirButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanelLog.add(this.jDirButton, new GridBagConstraints());
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridx = 0;
/*     */     gridBagConstraints.gridy = 1;
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jPanelLog, gridBagConstraints);
/*     */     this.jPanelCache.setLayout(new GridBagLayout());
/*     */     this.jPanelCache.setBorder(new TitledBorder(getResourceBundle().getString("IDS_TITLE_SETTINGS_CACHE")));
/*     */     this.jLabel5.setText(getResourceBundle().getString("IDS_CACHE_SIZE"));
/*     */     this.jLabel5.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 3.0D;
/*     */     this.jPanelCache.add(this.jLabel5, gridBagConstraints);
/*     */     this.jCacheSize.setText("10");
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 2.0D;
/*     */     this.jPanelCache.add(this.jCacheSize, gridBagConstraints);
/*     */     this.jLabel6.setText("MB");
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelCache.add(this.jLabel6, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelCache.add(this.jPanel10, gridBagConstraints);
/*     */     this.jLabel7.setText(getResourceBundle().getString("IDS_LOCATION"));
/*     */     this.jLabel7.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelCache.add(this.jLabel7, gridBagConstraints);
/*     */     this.jDirCacheButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jDirCacheButton.setText("...");
/*     */     this.jDirCacheButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jDirCacheButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     this.jPanelCache.add(this.jDirCacheButton, gridBagConstraints);
/*     */     this.jCachePath.setEnabled(false);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelCache.add(this.jCachePath, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     this.jPanelCache.add(this.jPanel23, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 3;
/*     */     this.jDelCacheButton.setFont(new Font("Dialog", 0, 12));
/*     */     this.jDelCacheButton.setText(getResourceBundle().getString("IDS_BTN_DELCACHE"));
/*     */     this.jDelCacheButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDSettingsDlg.this.jDelCacheButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.jPanelCache.add(this.jDelCacheButton, gridBagConstraints);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridx = 1;
/*     */     gridBagConstraints.gridy = 0;
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.fill = 1;
/*     */     gridBagConstraints.anchor = 13;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jPanelCache, gridBagConstraints);
/*     */     this.jChk2Times.setSelected(true);
/*     */     this.jChk2Times.setFont(new Font("Dialog", 0, 12));
/*     */     this.jChk2Times.setText(getResourceBundle().getString("IDS_CHK_2TIMES"));
/*     */     this.jChk2Times.setHorizontalAlignment(0);
/*     */     gridBagConstraints = new GridBagConstraints();
/*     */     gridBagConstraints.gridx = 0;
/*     */     gridBagConstraints.gridy = 2;
/*     */     gridBagConstraints.gridwidth = 0;
/*     */     gridBagConstraints.weightx = 1.0D;
/*     */     this.jPanelC.add(this.jChk2Times, gridBagConstraints);
/*     */     getContentPane().add(this.jPanelC, "Center");
/*     */     pack();
/*     */   }
/*     */   
/*     */   private void jDevicesActionPerformed(ActionEvent evt) {
/*     */     if (this.speedComboBox.getSelectedItem() != null)
/*     */       this.dev2baudrate.put(SDCurrentContext.getInstance().getCurrDevice(), this.speedComboBox.getSelectedItem()); 
/*     */     if (this.portComboBox.getSelectedItem() != null)
/*     */       this.dev2port.put(SDCurrentContext.getInstance().getCurrDevice(), this.portComboBox.getSelectedItem()); 
/*     */     SDCurrentContext.getInstance().setCurrDevice((Device)this.jDevices.getSelectedItem());
/*     */     initPorts((Device)this.jDevices.getSelectedItem());
/*     */     initBRates((Device)this.jDevices.getSelectedItem());
/*     */   }
/*     */   
/*     */   private void jDirCacheButtonActionPerformed(ActionEvent evt) {
/*     */     File cacheDir = null;
/*     */     try {
/*     */       cacheDir = new File(this.jCachePath.getText());
/*     */     } catch (Exception e) {}
/*     */     String currDir = "";
/*     */     if (cacheDir != null)
/*     */       currDir = cacheDir.getPath(); 
/*     */     SecurityManager secMan = System.getSecurityManager();
/*     */     System.setSecurityManager(null);
/*     */     JFileChooser dlgOpen = new JFileChooser(currDir);
/*     */     dlgOpen.setFileSelectionMode(1);
/*     */     if (cacheDir != null)
/*     */       dlgOpen.setSelectedFile(cacheDir); 
/*     */     if (dlgOpen.showOpenDialog(this) == 0) {
/*     */       this.jCachePath.setText(dlgOpen.getSelectedFile().getPath());
/*     */       enableOKButton();
/*     */     } 
/*     */     System.setSecurityManager(secMan);
/*     */   }
/*     */   
/*     */   private void jDirButtonActionPerformed(ActionEvent evt) {
/*     */     File logFile = null;
/*     */     try {
/*     */       logFile = new File(this.jLogFileName.getText());
/*     */     } catch (Exception e) {}
/*     */     String currDir = "";
/*     */     if (logFile != null)
/*     */       currDir = logFile.getParent(); 
/*     */     SecurityManager secMan = System.getSecurityManager();
/*     */     System.setSecurityManager(null);
/*     */     JFileChooser dlgOpen = new JFileChooser(currDir);
/*     */     if (logFile != null)
/*     */       dlgOpen.setSelectedFile(logFile); 
/*     */     if (dlgOpen.showOpenDialog(this) == 0) {
/*     */       this.jLogFileName.setText(dlgOpen.getSelectedFile().getPath());
/*     */       enableOKButton();
/*     */     } 
/*     */     System.setSecurityManager(secMan);
/*     */   }
/*     */   
/*     */   private void jChkLogActionPerformed(ActionEvent evt) {
/*     */     enableLogUI(this.jChkLog.isSelected());
/*     */     enableOKButton();
/*     */   }
/*     */   
/*     */   private void jTestButtonActionPerformed(ActionEvent evt) {
/*     */     SDEvent event = new SDEvent(17L, (String)this.portComboBox.getSelectedItem() + "," + ((BaudRate)this.speedComboBox.getSelectedItem()).getBaudRate());
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)event);
/*     */     String msgIDS = (String)event.getParam(1);
/*     */     int msgBoxIcon = 0;
/*     */     if (msgIDS.compareToIgnoreCase("IDS_TEST_SUCCESSFUL") == 0)
/*     */       msgBoxIcon = 1; 
/*     */     String message = getResourceBundle().getString(msgIDS);
/*     */     String title = getResourceBundle().getString("IDS_TITLE_SWDL");
/*     */     JOptionPane.showMessageDialog(this, message, title, msgBoxIcon);
/*     */   }
/*     */   
/*     */   private void jCancelButtonActionPerformed(ActionEvent evt) {
/*     */     setVisible(false);
/*     */     dispose();
/*     */   }
/*     */   
/*     */   private void jDelCacheButtonActionPerformed(ActionEvent evt) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(31L));
/*     */   }
/*     */   
/*     */   private void jOKButtonActionPerformed(ActionEvent evt) {
/*     */     try {
/*     */       if (Integer.parseInt(this.jCacheSize.getText()) < 10) {
/*     */         showMessageDialog("IDS_CACHESIZE_TO_SMALL", "IDS_TITLE_SWDL", 0);
/*     */         return;
/*     */       } 
/*     */     } catch (Exception e) {
/*     */       showMessageDialog("IDS_CACHESIZE_TO_SMALL", "IDS_TITLE_SWDL", 0);
/*     */       return;
/*     */     } 
/*     */     this.dev2baudrate.put((Device)this.jDevices.getSelectedItem(), this.speedComboBox.getSelectedItem());
/*     */     this.dev2port.put((Device)this.jDevices.getSelectedItem(), this.portComboBox.getSelectedItem());
/*     */     Device[] devices = Device.DOMAIN;
/*     */     for (int i = 0; i < devices.length; i++)
/*     */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(18L, devices[i].getDescription(), (String)this.dev2port.get(devices[i]) + "," + ((BaudRate)this.dev2baudrate.get(devices[i])).getBaudRate())); 
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(18L, "CachePath", this.jCachePath.getText()));
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(18L, "CacheSize", this.jCacheSize.getText()));
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(18L, "CheckTech2Times", this.jChk2Times.isSelected() ? "Yes" : "No"));
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(19L, new Boolean(this.jChkLog.isSelected()), new Boolean(this.jChkDebug.isSelected()), this.jLogFileName.getText()));
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(20L));
/*     */     setVisible(false);
/*     */     dispose();
/*     */   }
/*     */   
/*     */   private void closeDialog(WindowEvent evt) {
/*     */     setVisible(false);
/*     */     dispose();
/*     */   }
/*     */   
/*     */   private void enableOKButton() {
/*     */     if ((this.jLogFileName.getText().compareTo("") == 0 && this.jChkLog.isSelected()) || this.jCachePath.getText().compareTo("") == 0 || SDCurrentContext.getInstance().getComPorts() == null) {
/*     */       this.jOKButton.setEnabled(false);
/*     */     } else {
/*     */       this.jOKButton.setEnabled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initPorts(Device dev) {
/*     */     this.portComboBox.removeAllItems();
/*     */     String[] comPorts = SDCurrentContext.getInstance().getComPorts();
/*     */     if (comPorts != null) {
/*     */       if (dev.equals(Device.TECH2))
/*     */         this.portComboBox.addItem("AUTO"); 
/*     */       for (int i = 0; i < comPorts.length; i++)
/*     */         this.portComboBox.addItem(comPorts[i]); 
/*     */       this.portComboBox.setSelectedItem(this.dev2port.get(dev));
/*     */     } else {
/*     */       this.jOKButton.setEnabled(false);
/*     */       this.jTestButton.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initBRates(Device device) {
/*     */     this.speedComboBox.removeAllItems();
/*     */     BaudRate[] brates = ((Device)this.jDevices.getSelectedItem()).getBaudRates();
/*     */     for (int i = 0; i < brates.length; i++)
/*     */       this.speedComboBox.addItem(brates[i]); 
/*     */     this.speedComboBox.setSelectedItem(this.dev2baudrate.get(device));
/*     */   }
/*     */   
/*     */   private void initLogUI() {
/*     */     String logFileName = (String)SDCurrentContext.getInstance().getSettings().get("LogFileName");
/*     */     if (logFileName != null)
/*     */       this.jLogFileName.setText(logFileName); 
/*     */     if (((String)SDCurrentContext.getInstance().getSettings().get("Logging")).indexOf("OFF") != -1) {
/*     */       enableLogUI(false);
/*     */     } else {
/*     */       this.jChkLog.setSelected(true);
/*     */     } 
/*     */     if (((String)SDCurrentContext.getInstance().getSettings().get("LogDebug")).compareTo("YES") == 0)
/*     */       this.jChkDebug.setSelected(true); 
/*     */   }
/*     */   
/*     */   private void initSWCache() {
/*     */     String cachePath = (String)SDCurrentContext.getInstance().getSettings().get("CachePath");
/*     */     if (cachePath != null)
/*     */       this.jCachePath.setText(cachePath); 
/*     */     this.jCacheSize.setText((String)SDCurrentContext.getInstance().getSettings().get("CacheSize"));
/*     */   }
/*     */   
/*     */   private void initDevices(Device device) {
/*     */     Device[] devices = Device.DOMAIN;
/*     */     for (int i = 0; i < devices.length; i++) {
/*     */       this.dev2port.put(devices[i], SDCurrentContext.getInstance().getCurrDevicePort(devices[i].getDescription()));
/*     */       this.dev2baudrate.put(devices[i], new BaudRate(SDCurrentContext.getInstance().getCurrDeviceSpeed(devices[i].getDescription())));
/*     */       this.jDevices.addItem(devices[i]);
/*     */       if (devices[i].equals(device))
/*     */         this.jDevices.setSelectedItem(devices[i]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init(Device device) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(22L));
/*     */     initDevices(device);
/*     */     initPorts(device);
/*     */     initBRates(device);
/*     */     initLogUI();
/*     */     initSWCache();
/*     */     String check2Times = (String)SDCurrentContext.getInstance().getSettings().get("CheckTech2Times");
/*     */     if (check2Times.compareTo("Yes") == 0) {
/*     */       this.jChk2Times.setSelected(true);
/*     */     } else {
/*     */       this.jChk2Times.setSelected(false);
/*     */     } 
/*     */     enableOKButton();
/*     */     this.jChk2Times.setVisible(false);
/*     */   }
/*     */   
/*     */   private void enableLogUI(boolean enable) {
/*     */     this.jChkDebug.setEnabled(enable);
/*     */     this.jDirButton.setEnabled(enable);
/*     */     this.jLabel4.setEnabled(enable);
/*     */   }
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/*     */     if (this.resourceBundle == null)
/*     */       this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings"); 
/*     */     return this.resourceBundle;
/*     */   }
/*     */   
/*     */   private void showMessageDialog(String messageID, String titleID, int icon) {
/*     */     String message = getResourceBundle().getString(messageID);
/*     */     String title = getResourceBundle().getString(titleID);
/*     */     JOptionPane.showMessageDialog(this, message, title, icon);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SDSettingsDlg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */