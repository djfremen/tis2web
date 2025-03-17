/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.ImgKeyParser;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class ConfirmSWChangePanel extends WizardStepScreen {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JLabel jContain;
/*     */   private JPanel jPanelDown;
/*     */   
/*     */   public ConfirmSWChangePanel() {
/*  22 */     initComponents();
/*     */   }
/*     */   private JPanel jPanelE; private JPanel jPanelE1; private JPanel jPanelN; private JPanel jPanelN1; private JPanel jPanelS; private JPanel jPanelS1;
/*     */   private JPanel jPanelUp;
/*     */   private JPanel jPanelW;
/*     */   private JPanel jPanelW1;
/*     */   private JLabel jWillContain;
/*     */   
/*     */   private void initComponents() {
/*  31 */     this.jPanelUp = new JPanel();
/*  32 */     this.jPanelW = new JPanel();
/*  33 */     this.jPanelN = new JPanel();
/*  34 */     this.jPanelE = new JPanel();
/*  35 */     this.jPanelS = new JPanel();
/*  36 */     this.jContain = new JLabel();
/*  37 */     this.jPanelDown = new JPanel();
/*  38 */     this.jPanelW1 = new JPanel();
/*  39 */     this.jPanelN1 = new JPanel();
/*  40 */     this.jPanelE1 = new JPanel();
/*  41 */     this.jPanelS1 = new JPanel();
/*  42 */     this.jWillContain = new JLabel();
/*     */     
/*  44 */     setLayout(new GridLayout(2, 0));
/*     */     
/*  46 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentShown(ComponentEvent evt) {
/*  48 */             ConfirmSWChangePanel.this.formComponentShown(evt);
/*     */           }
/*     */         });
/*     */     
/*  52 */     this.jPanelUp.setLayout(new BorderLayout());
/*     */     
/*  54 */     this.jPanelUp.setBorder(new TitledBorder(getResourceBundle().getString("IDS_TOOL_CONTAINS")));
/*  55 */     this.jPanelUp.add(this.jPanelW, "West");
/*     */     
/*  57 */     this.jPanelUp.add(this.jPanelN, "North");
/*     */     
/*  59 */     this.jPanelUp.add(this.jPanelE, "East");
/*     */     
/*  61 */     this.jPanelUp.add(this.jPanelS, "South");
/*     */     
/*  63 */     this.jContain.setVerticalAlignment(1);
/*  64 */     this.jContain.setBorder(new BevelBorder(1));
/*  65 */     this.jPanelUp.add(this.jContain, "Center");
/*     */     
/*  67 */     add(this.jPanelUp);
/*     */     
/*  69 */     this.jPanelDown.setLayout(new BorderLayout());
/*     */     
/*  71 */     this.jPanelDown.setBorder(new TitledBorder(getResourceBundle().getString("IDS_TOOL_WILL_CONTAIN")));
/*  72 */     this.jPanelDown.add(this.jPanelW1, "West");
/*     */     
/*  74 */     this.jPanelDown.add(this.jPanelN1, "North");
/*     */     
/*  76 */     this.jPanelDown.add(this.jPanelE1, "East");
/*     */     
/*  78 */     this.jPanelDown.add(this.jPanelS1, "South");
/*     */     
/*  80 */     this.jWillContain.setVerticalAlignment(1);
/*  81 */     this.jWillContain.setBorder(new BevelBorder(1));
/*  82 */     this.jPanelDown.add(this.jWillContain, "Center");
/*     */     
/*  84 */     add(this.jPanelDown);
/*     */   }
/*     */ 
/*     */   
/*     */   private void formComponentShown(ComponentEvent evt) {
/*  89 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(12L, new Byte((byte)8), "IDS_BTN_DOWNLOAD"));
/*     */   }
/*     */   
/*     */   void onActivate() {
/*  93 */     SDCurrentContext currCon = SDCurrentContext.getInstance();
/*     */     
/*  95 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(1L, "IDS_TITLE_CONFIRM"));
/*  96 */     String image = (String)currCon.getLocSettings().get(ImgKeyParser.getImgKey(ConfirmSWChangePanel.class.getName(), currCon.getSelectedTool().getDescription()));
/*  97 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(8L, image));
/*     */     
/*  99 */     this.jContain.setText(currCon.getDeviceInfo().getAppName() + "  " + currCon.getDeviceInfo().getVersion() + "  " + currCon.getDeviceInfo().toString());
/* 100 */     this.jWillContain.setText(currCon.getNewAppInfo().getAppName() + "  " + currCon.getNewAppInfo().getVersion() + "  " + currCon.getNewAppInfo().toString());
/*     */   }
/*     */   
/*     */   void onBtnPressed(byte btn) {
/* 104 */     switch (btn) {
/*     */       case 8:
/* 106 */         onNext();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   byte showOrHide_Callback() {
/* 112 */     return 1;
/*     */   }
/*     */   
/*     */   private void onNext() {
/* 116 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(9L));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ConfirmSWChangePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */