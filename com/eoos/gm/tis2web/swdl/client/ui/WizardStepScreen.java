/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WizardStepScreen
/*     */   extends JPanel
/*     */   implements ButtonPanelListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String SCREEN_ID_SEL_DEV = "SelectDevice";
/*     */   public static final String SCREEN_ID_SEL_APP = "SelectApplication";
/*     */   public static final String SCREEN_ID_DOWNLOAD = "Download";
/*     */   public static final String SCREEN_ID_CONFIRM = "ConfirmSWChange";
/*     */   public static final String SCREEN_ID_DOWN_COMPLETE = "DownloadComplete";
/*     */   private boolean blockBtnNotification;
/*     */   private ResourceBundle resourceBundle;
/*     */   
/*     */   public class ButtonInfo
/*     */   {
/*     */     private long id;
/*     */     private String title;
/*     */     private byte index;
/*     */     
/*     */     public ButtonInfo(long id, String title, byte index) {
/*  33 */       this.id = id;
/*  34 */       this.title = title;
/*  35 */       this.index = index;
/*     */     }
/*     */     
/*     */     public ButtonInfo() {
/*  39 */       this.id = -1L;
/*  40 */       this.title = null;
/*  41 */       this.index = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getId() {
/*  50 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setId(long id) {
/*  60 */       this.id = id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTitle() {
/*  69 */       return this.title;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTitle(String title) {
/*  79 */       this.title = title;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte getIndex() {
/*  88 */       return this.index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setIndex(byte index) {
/*  98 */       this.index = index;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WizardStepScreen() {
/* 137 */     this.resourceBundle = null;
/*     */     this.blockBtnNotification = false;
/*     */   }
/*     */   
/*     */   public void blockBtnNotification(boolean doBlock) {
/*     */     this.blockBtnNotification = doBlock;
/*     */   }
/*     */   
/*     */   public boolean isButtonNotificationBlocked() {
/*     */     return this.blockBtnNotification;
/*     */   }
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/*     */     if (this.resourceBundle == null)
/*     */       this.resourceBundle = (ResourceBundle)GlobalInstanceProvider.getInstance("sdstrings"); 
/*     */     return this.resourceBundle;
/*     */   }
/*     */   
/*     */   public List getAdditionalButtons() {
/*     */     return null;
/*     */   }
/*     */   
/*     */   void onAdditionalBtnPressed(long lBtnId) {}
/*     */   
/*     */   abstract void onActivate();
/*     */   
/*     */   abstract void onBtnPressed(byte paramByte);
/*     */   
/*     */   abstract byte showOrHide_Callback();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\WizardStepScreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */