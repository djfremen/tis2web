/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.ImgKeyParser;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class DownloadCompletePanel extends WizardStepScreen {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public DownloadCompletePanel() {
/*  22 */     initComponents();
/*     */   }
/*     */   private JLabel jContain; private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanelE;
/*     */   private JPanel jPanelN;
/*     */   private JPanel jPanelS;
/*     */   private JPanel jPanelW;
/*     */   
/*     */   private void initComponents() {
/*  33 */     this.jPanel1 = new JPanel();
/*  34 */     this.jPanel2 = new JPanel();
/*  35 */     this.jPanelN = new JPanel();
/*  36 */     this.jPanelS = new JPanel();
/*  37 */     this.jPanelW = new JPanel();
/*  38 */     this.jPanelE = new JPanel();
/*  39 */     this.jContain = new JLabel();
/*  40 */     this.jPanel3 = new JPanel();
/*     */     
/*  42 */     setLayout(new GridBagLayout());
/*     */     
/*  44 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentShown(ComponentEvent evt) {
/*  46 */             DownloadCompletePanel.this.formComponentShown(evt);
/*     */           }
/*     */         });
/*     */     
/*  50 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  51 */     gridBagConstraints.gridwidth = 0;
/*  52 */     gridBagConstraints.fill = 1;
/*  53 */     gridBagConstraints.anchor = 11;
/*  54 */     gridBagConstraints.weightx = 1.0D;
/*  55 */     gridBagConstraints.weighty = 1.0D;
/*  56 */     add(this.jPanel1, gridBagConstraints);
/*     */     
/*  58 */     this.jPanel2.setLayout(new BorderLayout());
/*     */     
/*  60 */     this.jPanel2.setBorder(new TitledBorder(getResourceBundle().getString("IDS_DOWN_FINISH")));
/*  61 */     this.jPanel2.add(this.jPanelN, "North");
/*     */     
/*  63 */     this.jPanel2.add(this.jPanelS, "South");
/*     */     
/*  65 */     this.jPanel2.add(this.jPanelW, "West");
/*     */     
/*  67 */     this.jPanel2.add(this.jPanelE, "East");
/*     */     
/*  69 */     this.jContain.setVerticalAlignment(1);
/*  70 */     this.jContain.setBorder(new BevelBorder(1));
/*  71 */     this.jPanel2.add(this.jContain, "Center");
/*     */     
/*  73 */     gridBagConstraints = new GridBagConstraints();
/*  74 */     gridBagConstraints.gridwidth = 0;
/*  75 */     gridBagConstraints.fill = 1;
/*  76 */     gridBagConstraints.weightx = 1.0D;
/*  77 */     gridBagConstraints.weighty = 3.0D;
/*  78 */     add(this.jPanel2, gridBagConstraints);
/*     */     
/*  80 */     gridBagConstraints = new GridBagConstraints();
/*  81 */     gridBagConstraints.gridwidth = 0;
/*  82 */     gridBagConstraints.fill = 1;
/*  83 */     gridBagConstraints.anchor = 15;
/*  84 */     gridBagConstraints.weightx = 1.0D;
/*  85 */     gridBagConstraints.weighty = 1.0D;
/*  86 */     add(this.jPanel3, gridBagConstraints);
/*     */   }
/*     */ 
/*     */   
/*     */   private void formComponentShown(ComponentEvent evt) {
/*  91 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(12L, new Byte((byte)2), "IDS_BTN_CLOSE"));
/*     */   }
/*     */   
/*     */   void onActivate() {
/*  95 */     SDCurrentContext currCon = SDCurrentContext.getInstance();
/*     */     
/*  97 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(1L, "IDS_TITLE_DOWN_FINISH"));
/*  98 */     String image = (String)currCon.getLocSettings().get(ImgKeyParser.getImgKey(DownloadCompletePanel.class.getName()));
/*  99 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(8L, image));
/*     */     
/* 101 */     this.jContain.setText(currCon.getNewAppInfo().getAppName() + "  " + currCon.getNewAppInfo().getVersion() + "  " + currCon.getNewAppInfo().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   void onBtnPressed(byte btn) {}
/*     */   
/*     */   byte showOrHide_Callback() {
/* 108 */     return 13;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\DownloadCompletePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */