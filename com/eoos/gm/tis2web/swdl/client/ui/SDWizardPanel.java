/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.DTCMonitoring;
/*     */ import com.eoos.gm.tis2web.swdl.client.driver.TechDriver;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.NotificationHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import java.awt.CardLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Stack;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ public class SDWizardPanel
/*     */   extends JPanel
/*     */   implements NotificationHandler, ButtonPanelListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String currentScreenID;
/*     */   private Map id2PanelMap;
/*     */   private Stack screenStack;
/*     */   
/*     */   public SDWizardPanel() {
/*  29 */     this.id2PanelMap = new HashMap<Object, Object>();
/*  30 */     this.screenStack = new Stack();
/*     */     
/*  32 */     initComponents();
/*  33 */     initOwn();
/*     */     
/*  35 */     SDNotificationServer.getInstance().register(3L, this);
/*  36 */     SDNotificationServer.getInstance().register(4L, this);
/*  37 */     SDNotificationServer.getInstance().register(15L, this);
/*  38 */     SDNotificationServer.getInstance().register(11L, this);
/*     */     
/*  40 */     reset();
/*     */   }
/*     */   private SelectDevicePanel selDevPanel; private SelectApplicationPanel selAppPanel; private DownloadPanel downloadPanel; private ConfirmSWChangePanel confirmPanel; private DownloadCompletePanel downCompletePanel;
/*     */   
/*     */   private void initOwn() {
/*  45 */     this.selDevPanel = new SelectDevicePanel();
/*  46 */     addScreen(this.selDevPanel, "SelectDevice");
/*     */ 
/*     */     
/*  49 */     this.confirmPanel = new ConfirmSWChangePanel();
/*  50 */     addScreen(this.confirmPanel, "ConfirmSWChange");
/*     */ 
/*     */     
/*  53 */     this.selAppPanel = new SelectApplicationPanel();
/*  54 */     addScreen(this.selAppPanel, "SelectApplication");
/*     */ 
/*     */     
/*  57 */     this.downloadPanel = new DownloadPanel();
/*  58 */     addScreen(this.downloadPanel, "Download");
/*     */ 
/*     */     
/*  61 */     this.downCompletePanel = new DownloadCompletePanel();
/*  62 */     addScreen(this.downCompletePanel, "DownloadComplete");
/*     */   }
/*     */   
/*     */   private void addScreen(WizardStepScreen panel, String id) {
/*  66 */     add(panel, id);
/*  67 */     this.id2PanelMap.put(id, panel);
/*  68 */     panel.blockBtnNotification(true);
/*     */   }
/*     */   
/*     */   public void switchView(String screenID, boolean clearStack) {
/*  72 */     if (clearStack) {
/*  73 */       reset();
/*     */     }
/*  75 */     if (!screenID.equals(this.currentScreenID)) {
/*  76 */       if (!this.currentScreenID.equals("<init>")) {
/*  77 */         WizardStepScreen wssold = (WizardStepScreen)this.id2PanelMap.get(this.currentScreenID);
/*  78 */         wssold.blockBtnNotification(true);
/*     */       } 
/*     */       
/*  81 */       WizardStepScreen wss = (WizardStepScreen)this.id2PanelMap.get(screenID);
/*  82 */       wss.blockBtnNotification(false);
/*  83 */       wss.onActivate();
/*     */ 
/*     */       
/*  86 */       ((CardLayout)getLayout()).show(this, screenID);
/*     */ 
/*     */       
/*  89 */       boolean pushIt = true;
/*     */       
/*  91 */       if (this.screenStack.size() > 0 && ((String)this.screenStack.peek()).equals(screenID)) {
/*  92 */         pushIt = false;
/*     */       }
/*  94 */       if (pushIt) {
/*  95 */         this.screenStack.push(new String(screenID));
/*     */       }
/*  97 */       this.currentScreenID = screenID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       byte btnMask = wss.showOrHide_Callback();
/* 106 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(4L, new Byte(btnMask)));
/*     */     } 
/*     */   }
/*     */   public void handleNotification(Notification msg) {
/*     */     try {
/*     */       boolean clearStack;
/* 112 */       SDEvent eve = (SDEvent)msg;
/* 113 */       switch ((int)eve.getType()) {
/*     */         case 3:
/* 115 */           onBtnPressed(((Byte)eve.getParam(0)).byteValue(), (eve.getParam(1) != null) ? ((Long)eve.getParam(1)).longValue() : 0L);
/*     */           break;
/*     */         case 15:
/* 118 */           clearStack = false;
/* 119 */           if (eve.getParam(1) != null) {
/* 120 */             clearStack = ((Boolean)eve.getParam(1)).booleanValue();
/*     */           }
/* 122 */           switchView((String)eve.getParam(0), clearStack);
/*     */           break;
/*     */         case 11:
/* 125 */           onDownloadComplete();
/*     */           break;
/*     */       } 
/*     */     
/* 129 */     } catch (Exception e) {
/* 130 */       System.out.println(getClass().getName() + ".handleNotification() - Caught exception: " + e.toString());
/* 131 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onBtnPressed(byte btnCode, long addBtnId) {
/* 136 */     if (btnCode == 2) {
/* 137 */       if (this.currentScreenID.compareToIgnoreCase("Download") == 0) {
/* 138 */         ResourceBundle resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings");
/* 139 */         String message = resourceBundle.getString("abort.software.download");
/* 140 */         String title = resourceBundle.getString("IDS_TITLE_SWDL");
/* 141 */         int res = JOptionPane.showConfirmDialog(getParent(), message, title, 0, 3);
/* 142 */         if (res == 0) {
/* 143 */           TechDriver.abortDeviceCommunication();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       } 
/* 148 */       DTCMonitoring.getInstance().stopMonitoring();
/* 149 */       synchronized (TechDriver.class) {
/* 150 */         TechDriver.discardInstance();
/*     */       } 
/* 152 */       System.exit(0); return;
/*     */     } 
/* 154 */     if (btnCode == 4) {
/* 155 */       this.screenStack.pop();
/* 156 */       String prevScreenID = this.screenStack.peek();
/* 157 */       System.out.println(getClass().getName() + ".onBtnPressed() - Navigate back to screen: " + prevScreenID);
/* 158 */       switchView(prevScreenID, false);
/*     */       
/*     */       return;
/*     */     } 
/* 162 */     this.id2PanelMap.entrySet().iterator();
/* 163 */     if (btnCode > 0) {
/* 164 */       ((WizardStepScreen)this.id2PanelMap.get(this.currentScreenID)).onBtnPressed(btnCode);
/*     */     } else {
/* 166 */       ((WizardStepScreen)this.id2PanelMap.get(this.currentScreenID)).onAdditionalBtnPressed(addBtnId);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onDownloadComplete() {
/* 171 */     switchView("DownloadComplete", false);
/*     */   }
/*     */   
/*     */   private void reset() {
/* 175 */     System.out.println("SDWizardPanel.reset()");
/* 176 */     this.currentScreenID = "<init>";
/* 177 */     this.screenStack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 187 */     setLayout(new CardLayout());
/*     */     
/* 189 */     setPreferredSize(new Dimension(500, 350));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SDWizardPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */