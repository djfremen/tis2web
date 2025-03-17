/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.NotificationHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SDButtonPanel
/*     */   extends JPanel
/*     */   implements NotificationHandler
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JPanel eastPanel;
/*     */   private JButton jButtonCancel;
/*     */   private JButton jButtonNext;
/*     */   private JButton jButtonPrev;
/*     */   private JButton jButtonSettings;
/*     */   private JPanel westPanel;
/*     */   private JRootPane rootPane;
/*     */   private ResourceBundle resourceBundle;
/*     */   private Map addButtonMap;
/*     */   private Map id2BtnMap;
/*     */   
/*     */   public SDButtonPanel() {
/* 350 */     this.rootPane = null;
/*     */ 
/*     */     
/* 353 */     this.addButtonMap = null;
/* 354 */     this.id2BtnMap = null;
/*     */     this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings");
/*     */     init();
/*     */     SDNotificationServer.getInstance().register(4L, this);
/*     */     SDNotificationServer.getInstance().register(5L, this);
/*     */     SDNotificationServer.getInstance().register(10L, this);
/*     */     SDNotificationServer.getInstance().register(12L, this);
/*     */   }
/*     */   
/*     */   public void handleNotification(Notification msg) {
/*     */     try {
/*     */       SDEvent eve = (SDEvent)msg;
/*     */       switch ((int)eve.getType()) {
/*     */         case 4:
/*     */           showOrHideButtons(((Byte)eve.getParam(0)).byteValue());
/*     */           break;
/*     */         case 5:
/*     */           showAddBtns((List)eve.getParam(0));
/*     */           break;
/*     */         case 10:
/*     */           enableButtons(((Byte)eve.getParam(0)).byteValue(), ((Boolean)eve.getParam(1)).booleanValue());
/*     */           break;
/*     */         case 12:
/*     */           changeButtonName(((Byte)eve.getParam(0)).byteValue(), (String)eve.getParam(1));
/*     */           break;
/*     */       } 
/*     */     } catch (Exception e) {
/*     */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRootPane(JRootPane rp) {
/*     */     this.rootPane = rp;
/*     */   }
/*     */   
/*     */   private void init() {
/*     */     int tempBtnListSize = 4;
/*     */     if (this.addButtonMap != null)
/*     */       tempBtnListSize += this.addButtonMap.size(); 
/*     */     ArrayList<JButton> tempBtnList = new ArrayList(tempBtnListSize);
/*     */     initComponents();
/*     */     tempBtnList.add(this.jButtonSettings);
/*     */     tempBtnList.add(this.jButtonPrev);
/*     */     tempBtnList.add(this.jButtonNext);
/*     */     tempBtnList.add(this.jButtonCancel);
/*     */     if (this.addButtonMap != null) {
/*     */       this.id2BtnMap = new HashMap<Object, Object>();
/*     */       Map.Entry entry = null;
/*     */       Iterator<Map.Entry> mapit = this.addButtonMap.entrySet().iterator();
/*     */       while (mapit.hasNext()) {
/*     */         entry = mapit.next();
/*     */         WizardStepScreen.ButtonInfo bi = (WizardStepScreen.ButtonInfo)entry.getValue();
/*     */         JButton jAddButton = new JButton();
/*     */         jAddButton.setText(bi.getTitle());
/*     */         jAddButton.setRequestFocusEnabled(false);
/*     */         jAddButton.addActionListener(new AddBtnActionListener(bi.getId(), this));
/*     */         tempBtnList.add(bi.getIndex(), jAddButton);
/*     */         this.id2BtnMap.put(Long.valueOf(bi.getId()), jAddButton);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*     */     this.westPanel = new JPanel();
/*     */     this.jButtonSettings = new JButton();
/*     */     this.eastPanel = new JPanel();
/*     */     this.jButtonPrev = new JButton();
/*     */     this.jButtonNext = new JButton();
/*     */     this.jButtonCancel = new JButton();
/*     */     setLayout(new BorderLayout());
/*     */     this.westPanel.setLayout(new FlowLayout(0));
/*     */     this.jButtonSettings.setFont(new Font("Dialog", 0, 12));
/*     */     this.jButtonSettings.setText(this.resourceBundle.getString("IDS_BTN_SETTINGS"));
/*     */     this.jButtonSettings.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDButtonPanel.this.jButtonSettingsActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.westPanel.add(this.jButtonSettings);
/*     */     add(this.westPanel, "West");
/*     */     this.eastPanel.setLayout(new FlowLayout(2));
/*     */     this.jButtonPrev.setFont(new Font("Dialog", 0, 12));
/*     */     this.jButtonPrev.setText(this.resourceBundle.getString("IDS_BTN_PREV"));
/*     */     this.jButtonPrev.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDButtonPanel.this.jButtonPrevActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.eastPanel.add(this.jButtonPrev);
/*     */     this.jButtonNext.setFont(new Font("Dialog", 0, 12));
/*     */     this.jButtonNext.setText(this.resourceBundle.getString("IDS_BTN_NEXT"));
/*     */     this.jButtonNext.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDButtonPanel.this.jButtonNextActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.eastPanel.add(this.jButtonNext);
/*     */     this.jButtonCancel.setFont(new Font("Dialog", 0, 12));
/*     */     this.jButtonCancel.setText(this.resourceBundle.getString("IDS_BTN_CANCEL"));
/*     */     this.jButtonCancel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*     */             SDButtonPanel.this.jButtonCancelActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     this.eastPanel.add(this.jButtonCancel);
/*     */     add(this.eastPanel, "East");
/*     */   }
/*     */   
/*     */   private void jButtonSettingsActionPerformed(ActionEvent evt) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)1)));
/*     */   }
/*     */   
/*     */   private void jButtonCancelActionPerformed(ActionEvent evt) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)2)));
/*     */   }
/*     */   
/*     */   private void jButtonNextActionPerformed(ActionEvent evt) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)8)));
/*     */   }
/*     */   
/*     */   private void jButtonPrevActionPerformed(ActionEvent evt) {
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)4)));
/*     */   }
/*     */   
/*     */   public void jButtonAdditionalActionPerformed(long addButtonID) {
/*     */     JButton curButton = null;
/*     */     if (this.id2BtnMap != null && this.id2BtnMap.size() > 0)
/*     */       curButton = (JButton)this.id2BtnMap.get(Long.valueOf(addButtonID)); 
/*     */     if (curButton != null)
/*     */       curButton.setEnabled(false); 
/*     */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte("0"), Long.valueOf(addButtonID)));
/*     */     if (curButton != null)
/*     */       curButton.setEnabled(true); 
/*     */   }
/*     */   
/*     */   public void showOrHideButtons(byte mask) {
/*     */     if ((mask & 0x1) == 1) {
/*     */       this.jButtonSettings.setVisible(false);
/*     */     } else {
/*     */       this.jButtonSettings.setVisible(true);
/*     */       if (this.rootPane != null)
/*     */         this.rootPane.setDefaultButton(this.jButtonSettings); 
/*     */     } 
/*     */     if ((mask & 0x2) == 2) {
/*     */       this.jButtonCancel.setVisible(false);
/*     */     } else {
/*     */       this.jButtonCancel.setVisible(true);
/*     */       if (this.rootPane != null)
/*     */         this.rootPane.setDefaultButton(this.jButtonCancel); 
/*     */     } 
/*     */     if ((mask & 0x4) == 4) {
/*     */       this.jButtonPrev.setVisible(false);
/*     */     } else {
/*     */       this.jButtonPrev.setVisible(true);
/*     */       if (this.rootPane != null)
/*     */         this.rootPane.setDefaultButton(this.jButtonPrev); 
/*     */     } 
/*     */     if ((mask & 0x8) == 8) {
/*     */       this.jButtonNext.setVisible(false);
/*     */     } else {
/*     */       this.jButtonNext.setVisible(true);
/*     */       if (this.rootPane != null)
/*     */         this.rootPane.setDefaultButton(this.jButtonNext); 
/*     */     } 
/*     */     updateUI();
/*     */   }
/*     */   
/*     */   public void showAddBtns(List addBtnList) {
/*     */     if (addBtnList != null) {
/*     */       WizardStepScreen.ButtonInfo obj = null;
/*     */       this.id2BtnMap = null;
/*     */       this.addButtonMap = new HashMap<Object, Object>();
/*     */       Iterator<WizardStepScreen.ButtonInfo> it = addBtnList.iterator();
/*     */       while (it.hasNext()) {
/*     */         obj = it.next();
/*     */         this.addButtonMap.put(new Byte(obj.getIndex()), obj);
/*     */       } 
/*     */     } else {
/*     */       this.addButtonMap = null;
/*     */     } 
/*     */     removeAll();
/*     */     init();
/*     */   }
/*     */   
/*     */   private void enableButtons(byte mask, boolean enable) {
/*     */     if ((mask & 0x1) == 1)
/*     */       this.jButtonSettings.setEnabled(enable); 
/*     */     if ((mask & 0x2) == 2)
/*     */       this.jButtonCancel.setEnabled(enable); 
/*     */     if ((mask & 0x4) == 4)
/*     */       this.jButtonPrev.setEnabled(enable); 
/*     */     if ((mask & 0x8) == 8)
/*     */       this.jButtonNext.setEnabled(enable); 
/*     */     updateUI();
/*     */   }
/*     */   
/*     */   private void changeButtonName(byte mask, String newNameIDS) {
/*     */     if ((mask & 0x1) == 1)
/*     */       this.jButtonSettings.setText(this.resourceBundle.getString(newNameIDS)); 
/*     */     if ((mask & 0x2) == 2)
/*     */       this.jButtonCancel.setText(this.resourceBundle.getString(newNameIDS)); 
/*     */     if ((mask & 0x4) == 4)
/*     */       this.jButtonPrev.setText(this.resourceBundle.getString(newNameIDS)); 
/*     */     if ((mask & 0x8) == 8)
/*     */       this.jButtonNext.setText(this.resourceBundle.getString(newNameIDS)); 
/*     */     updateUI();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SDButtonPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */