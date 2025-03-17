/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class SelectDevicePanel extends WizardStepScreen {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private ButtonGroup buttonGroup1;
/*     */   private JRadioButton jCustom;
/*     */   private JLabel jLabel1;
/*     */   
/*     */   public SelectDevicePanel() {
/*  25 */     initComponents();
/*  26 */     init();
/*     */   }
/*     */   private JLabel jLabel2; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JPanel jPanel5; private JPanel jPanel6; private JPanel jPanelDown; private JPanel jPanelE;
/*     */   private JPanel jPanelN;
/*     */   private JPanel jPanelS;
/*     */   private JPanel jPanelUp;
/*     */   private JPanel jPanelW;
/*     */   private JRadioButton jStandard;
/*     */   private JList jTools;
/*     */   
/*     */   private void initComponents() {
/*  37 */     this.buttonGroup1 = new ButtonGroup();
/*  38 */     this.jPanelUp = new JPanel();
/*  39 */     this.jTools = new JList();
/*  40 */     this.jPanelN = new JPanel();
/*  41 */     this.jPanelS = new JPanel();
/*  42 */     this.jPanelW = new JPanel();
/*  43 */     this.jPanelE = new JPanel();
/*  44 */     this.jPanelDown = new JPanel();
/*  45 */     this.jPanel1 = new JPanel();
/*  46 */     this.jStandard = new JRadioButton();
/*  47 */     this.jPanel5 = new JPanel();
/*  48 */     this.jLabel1 = new JLabel();
/*  49 */     this.jPanel2 = new JPanel();
/*  50 */     this.jPanel6 = new JPanel();
/*  51 */     this.jCustom = new JRadioButton();
/*  52 */     this.jPanel4 = new JPanel();
/*  53 */     this.jLabel2 = new JLabel();
/*  54 */     this.jPanel3 = new JPanel();
/*     */     
/*  56 */     setLayout(new GridLayout(2, 0));
/*     */     
/*  58 */     this.jPanelUp.setLayout(new BorderLayout());
/*     */     
/*  60 */     this.jPanelUp.setBorder(new TitledBorder(null, getResourceBundle().getString("IDS_SEL_TOOL"), 0, 0, new Font("Dialog", 0, 12)));
/*  61 */     this.jTools.setBorder(new EtchedBorder());
/*  62 */     this.jTools.setPreferredSize(new Dimension(350, 50));
/*  63 */     this.jTools.addListSelectionListener(new ListSelectionListener() {
/*     */           public void valueChanged(ListSelectionEvent evt) {
/*  65 */             SelectDevicePanel.this.jToolsValueChanged(evt);
/*     */           }
/*     */         });
/*     */     
/*  69 */     this.jPanelUp.add(this.jTools, "Center");
/*     */     
/*  71 */     this.jPanelUp.add(this.jPanelN, "North");
/*     */     
/*  73 */     this.jPanelUp.add(this.jPanelS, "South");
/*     */     
/*  75 */     this.jPanelUp.add(this.jPanelW, "West");
/*     */     
/*  77 */     this.jPanelUp.add(this.jPanelE, "East");
/*     */     
/*  79 */     add(this.jPanelUp);
/*     */     
/*  81 */     this.jPanelDown.setLayout(new GridBagLayout());
/*     */     
/*  83 */     this.jPanelDown.setBorder(new TitledBorder(null, getResourceBundle().getString("IDS_UPD_MODE"), 0, 0, new Font("Dialog", 0, 12)));
/*  84 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  85 */     gridBagConstraints.gridwidth = 0;
/*  86 */     gridBagConstraints.fill = 1;
/*  87 */     this.jPanelDown.add(this.jPanel1, gridBagConstraints);
/*     */     
/*  89 */     this.jStandard.setSelected(true);
/*  90 */     this.jStandard.setFont(new Font("Dialog", 0, 12));
/*  91 */     this.jStandard.setText(getResourceBundle().getString("IDS_BTNR_STANDARD"));
/*  92 */     this.jStandard.setPreferredSize(new Dimension(350, 35));
/*  93 */     gridBagConstraints = new GridBagConstraints();
/*  94 */     gridBagConstraints.gridwidth = 0;
/*  95 */     gridBagConstraints.fill = 1;
/*  96 */     gridBagConstraints.weightx = 1.0D;
/*  97 */     gridBagConstraints.weighty = 1.0D;
/*  98 */     this.jPanelDown.add(this.jStandard, gridBagConstraints);
/*     */     
/* 100 */     gridBagConstraints = new GridBagConstraints();
/* 101 */     gridBagConstraints.fill = 1;
/* 102 */     this.jPanelDown.add(this.jPanel5, gridBagConstraints);
/*     */     
/* 104 */     this.jLabel1.setText(MessageFormater.FormatCR2BR(getResourceBundle().getString("IDS_STANDARD_COMM")));
/* 105 */     this.jLabel1.setForeground(new Color(0, 0, 0));
/* 106 */     this.jLabel1.setFont(new Font("Dialog", 0, 12));
/* 107 */     gridBagConstraints = new GridBagConstraints();
/* 108 */     gridBagConstraints.gridwidth = 0;
/* 109 */     gridBagConstraints.fill = 1;
/* 110 */     this.jPanelDown.add(this.jLabel1, gridBagConstraints);
/*     */     
/* 112 */     gridBagConstraints = new GridBagConstraints();
/* 113 */     gridBagConstraints.gridwidth = 0;
/* 114 */     gridBagConstraints.fill = 1;
/* 115 */     this.jPanelDown.add(this.jPanel2, gridBagConstraints);
/*     */     
/* 117 */     gridBagConstraints = new GridBagConstraints();
/* 118 */     gridBagConstraints.gridwidth = 0;
/* 119 */     gridBagConstraints.fill = 1;
/* 120 */     this.jPanelDown.add(this.jPanel6, gridBagConstraints);
/*     */     
/* 122 */     this.jCustom.setFont(new Font("Dialog", 0, 12));
/* 123 */     this.jCustom.setText(getResourceBundle().getString("IDS_BTNR_CUSTOM"));
/* 124 */     this.jCustom.setPreferredSize(new Dimension(350, 35));
/* 125 */     gridBagConstraints = new GridBagConstraints();
/* 126 */     gridBagConstraints.gridwidth = 0;
/* 127 */     gridBagConstraints.fill = 1;
/* 128 */     gridBagConstraints.weightx = 1.0D;
/* 129 */     gridBagConstraints.weighty = 1.0D;
/* 130 */     this.jPanelDown.add(this.jCustom, gridBagConstraints);
/*     */     
/* 132 */     gridBagConstraints = new GridBagConstraints();
/* 133 */     gridBagConstraints.fill = 1;
/* 134 */     this.jPanelDown.add(this.jPanel4, gridBagConstraints);
/*     */     
/* 136 */     this.jLabel2.setText(MessageFormater.FormatCR2BR(getResourceBundle().getString("IDS_CUSTOM_COMM")));
/* 137 */     this.jLabel2.setForeground(new Color(0, 0, 0));
/* 138 */     this.jLabel2.setFont(new Font("Dialog", 0, 12));
/* 139 */     gridBagConstraints = new GridBagConstraints();
/* 140 */     gridBagConstraints.gridwidth = 0;
/* 141 */     gridBagConstraints.fill = 1;
/* 142 */     this.jPanelDown.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 144 */     gridBagConstraints = new GridBagConstraints();
/* 145 */     gridBagConstraints.gridwidth = 0;
/* 146 */     gridBagConstraints.fill = 1;
/* 147 */     this.jPanelDown.add(this.jPanel3, gridBagConstraints);
/*     */     
/* 149 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentShown(ComponentEvent evt) {
/* 151 */             SelectDevicePanel.this.formComponentShown(evt);
/*     */           }
/*     */         });
/*     */     
/* 155 */     add(this.jPanelDown);
/*     */   }
/*     */ 
/*     */   
/*     */   private void formComponentShown(ComponentEvent evt) {
/* 160 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(12L, new Byte((byte)8), "IDS_BTN_NEXT"));
/* 161 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)8), new Boolean(true)));
/*     */   }
/*     */   
/*     */   private void jToolsValueChanged(ListSelectionEvent evt) {
/* 165 */     SDCurrentContext.getInstance().setCurrDevice(this.jTools.getSelectedValue());
/*     */   }
/*     */   
/*     */   private void init() {
/* 169 */     this.jStandard.setActionCommand("Standard");
/* 170 */     this.jCustom.setActionCommand("Custom");
/* 171 */     this.buttonGroup1.add(this.jStandard);
/* 172 */     this.buttonGroup1.add(this.jCustom);
/*     */     
/* 174 */     this.jTools.setListData(SDCurrentContext.getInstance().getDevices());
/* 175 */     this.jTools.setSelectedValue(SDCurrentContext.getInstance().getSelectedTool(), true);
/*     */   }
/*     */   
/*     */   byte showOrHide_Callback() {
/* 179 */     return 4;
/*     */   }
/*     */   
/*     */   void onBtnPressed(byte btn) {
/* 183 */     switch (btn) {
/*     */       case 8:
/* 185 */         onNext();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onNext() {
/* 191 */     String[] driverInfo = new String[2];
/*     */     
/* 193 */     driverInfo[0] = ((Device)this.jTools.getSelectedValue()).getDescription();
/* 194 */     driverInfo[1] = this.buttonGroup1.getSelection().getActionCommand();
/* 195 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(6L, driverInfo));
/*     */   }
/*     */   
/*     */   void onActivate() {
/* 199 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(1L, "IDS_TITLE_SEL_TOOL"));
/* 200 */     String image = (String)SDCurrentContext.getInstance().getLocSettings().get(ImgKeyParser.getImgKey(SelectDevicePanel.class.getName()));
/* 201 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(8L, image));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SelectDevicePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */