/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.ServerRequestor;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.ImgKeyParser;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DownloadPanel
/*     */   extends WizardStepScreen implements SDNotifHandler {
/*     */   private static final long serialVersionUID = 1L;
/*  29 */   private Logger log = Logger.getLogger(DownloadPanel.class);
/*     */   private boolean isError = false;
/*  31 */   private long startTime = 0L;
/*  32 */   private long subtract = 0L; private boolean downloadEnded = false; private JLabel jBytesread; private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JLabel jLabel4; private JLabel jMessage;
/*  33 */   private long lastTimeRefresh = System.currentTimeMillis(); private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JPanel jPanel5;
/*     */   private JPanel jPanel6;
/*     */   private JPanel jPanelC;
/*     */   
/*     */   public DownloadPanel() {
/*  38 */     initComponents();
/*     */   }
/*     */   private JPanel jPanelC1; private JPanel jPanelDown; private JPanel jPanelE; private JPanel jPanelE1; private JPanel jPanelN; private JPanel jPanelN1; private JPanel jPanelS; private JPanel jPanelS1;
/*     */   private JPanel jPanelUp;
/*     */   private JPanel jPanelW;
/*     */   private JPanel jPanelW1;
/*     */   private JProgressBar jProgressDown;
/*     */   private JProgressBar jProgressErase;
/*     */   private JLabel jTimeleft;
/*     */   
/*     */   private void initComponents() {
/*  49 */     this.jPanelUp = new JPanel();
/*  50 */     this.jPanelN = new JPanel();
/*  51 */     this.jPanelW = new JPanel();
/*  52 */     this.jPanelS = new JPanel();
/*  53 */     this.jPanelE = new JPanel();
/*  54 */     this.jPanelC = new JPanel();
/*  55 */     this.jPanel1 = new JPanel();
/*  56 */     this.jLabel1 = new JLabel();
/*  57 */     this.jProgressErase = new JProgressBar();
/*  58 */     this.jPanel2 = new JPanel();
/*  59 */     this.jLabel2 = new JLabel();
/*  60 */     this.jProgressDown = new JProgressBar();
/*  61 */     this.jPanel3 = new JPanel();
/*  62 */     this.jPanelDown = new JPanel();
/*  63 */     this.jPanelN1 = new JPanel();
/*  64 */     this.jPanelW1 = new JPanel();
/*  65 */     this.jPanelS1 = new JPanel();
/*  66 */     this.jPanelE1 = new JPanel();
/*  67 */     this.jPanelC1 = new JPanel();
/*  68 */     this.jPanel4 = new JPanel();
/*  69 */     this.jLabel3 = new JLabel();
/*  70 */     this.jLabel4 = new JLabel();
/*  71 */     this.jMessage = new JLabel();
/*  72 */     this.jTimeleft = new JLabel();
/*  73 */     this.jPanel5 = new JPanel();
/*  74 */     this.jBytesread = new JLabel();
/*  75 */     this.jPanel6 = new JPanel();
/*     */     
/*  77 */     setLayout(new GridBagLayout());
/*     */     
/*  79 */     this.jPanelUp.setLayout(new BorderLayout());
/*     */     
/*  81 */     this.jPanelUp.setBorder(new TitledBorder(getResourceBundle().getString("IDS_PROGRESS_PANEL")));
/*  82 */     this.jPanelUp.add(this.jPanelN, "North");
/*     */     
/*  84 */     this.jPanelUp.add(this.jPanelW, "West");
/*     */     
/*  86 */     this.jPanelUp.add(this.jPanelS, "South");
/*     */     
/*  88 */     this.jPanelUp.add(this.jPanelE, "East");
/*     */     
/*  90 */     this.jPanelC.setLayout(new GridBagLayout());
/*     */     
/*  92 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  93 */     gridBagConstraints.gridwidth = 0;
/*  94 */     gridBagConstraints.fill = 1;
/*  95 */     gridBagConstraints.anchor = 11;
/*  96 */     gridBagConstraints.weightx = 1.0D;
/*  97 */     gridBagConstraints.weighty = 1.0D;
/*  98 */     this.jPanelC.add(this.jPanel1, gridBagConstraints);
/*     */     
/* 100 */     this.jLabel1.setText(getResourceBundle().getString("IDS_ERASE_PROGRESS"));
/* 101 */     gridBagConstraints = new GridBagConstraints();
/* 102 */     gridBagConstraints.gridwidth = 0;
/* 103 */     gridBagConstraints.fill = 1;
/* 104 */     gridBagConstraints.weightx = 1.0D;
/* 105 */     this.jPanelC.add(this.jLabel1, gridBagConstraints);
/*     */     
/* 107 */     this.jProgressErase.setStringPainted(true);
/* 108 */     gridBagConstraints = new GridBagConstraints();
/* 109 */     gridBagConstraints.gridwidth = 0;
/* 110 */     gridBagConstraints.fill = 1;
/* 111 */     gridBagConstraints.weightx = 1.0D;
/* 112 */     gridBagConstraints.weighty = 1.0D;
/* 113 */     this.jPanelC.add(this.jProgressErase, gridBagConstraints);
/*     */     
/* 115 */     gridBagConstraints = new GridBagConstraints();
/* 116 */     gridBagConstraints.gridwidth = 0;
/* 117 */     gridBagConstraints.fill = 1;
/* 118 */     gridBagConstraints.weightx = 1.0D;
/* 119 */     gridBagConstraints.weighty = 1.0D;
/* 120 */     this.jPanelC.add(this.jPanel2, gridBagConstraints);
/*     */     
/* 122 */     this.jLabel2.setText(getResourceBundle().getString("IDS_DOWNLOAD_PROGRESS"));
/* 123 */     gridBagConstraints = new GridBagConstraints();
/* 124 */     gridBagConstraints.gridwidth = 0;
/* 125 */     gridBagConstraints.fill = 1;
/* 126 */     gridBagConstraints.weightx = 1.0D;
/* 127 */     this.jPanelC.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 129 */     this.jProgressDown.setStringPainted(true);
/* 130 */     gridBagConstraints = new GridBagConstraints();
/* 131 */     gridBagConstraints.gridwidth = 0;
/* 132 */     gridBagConstraints.fill = 1;
/* 133 */     gridBagConstraints.weightx = 1.0D;
/* 134 */     gridBagConstraints.weighty = 1.0D;
/* 135 */     this.jPanelC.add(this.jProgressDown, gridBagConstraints);
/*     */     
/* 137 */     gridBagConstraints = new GridBagConstraints();
/* 138 */     gridBagConstraints.gridwidth = 0;
/* 139 */     gridBagConstraints.fill = 1;
/* 140 */     gridBagConstraints.anchor = 15;
/* 141 */     gridBagConstraints.weightx = 1.0D;
/* 142 */     gridBagConstraints.weighty = 1.0D;
/* 143 */     this.jPanelC.add(this.jPanel3, gridBagConstraints);
/*     */     
/* 145 */     this.jPanelUp.add(this.jPanelC, "Center");
/*     */     
/* 147 */     gridBagConstraints = new GridBagConstraints();
/* 148 */     gridBagConstraints.gridwidth = 0;
/* 149 */     gridBagConstraints.fill = 1;
/* 150 */     gridBagConstraints.weightx = 1.0D;
/* 151 */     gridBagConstraints.weighty = 1.0D;
/* 152 */     add(this.jPanelUp, gridBagConstraints);
/*     */     
/* 154 */     this.jPanelDown.setLayout(new BorderLayout());
/*     */     
/* 156 */     this.jPanelDown.setBorder(new TitledBorder(getResourceBundle().getString("IDS_STATUS_PANEL")));
/* 157 */     this.jPanelDown.add(this.jPanelN1, "North");
/*     */     
/* 159 */     this.jPanelDown.add(this.jPanelW1, "West");
/*     */     
/* 161 */     this.jPanelDown.add(this.jPanelS1, "South");
/*     */     
/* 163 */     this.jPanelDown.add(this.jPanelE1, "East");
/*     */     
/* 165 */     this.jPanelC1.setLayout(new GridBagLayout());
/*     */     
/* 167 */     gridBagConstraints = new GridBagConstraints();
/* 168 */     gridBagConstraints.gridwidth = 0;
/* 169 */     gridBagConstraints.fill = 1;
/* 170 */     gridBagConstraints.anchor = 11;
/* 171 */     gridBagConstraints.weightx = 1.0D;
/* 172 */     gridBagConstraints.weighty = 1.0D;
/* 173 */     this.jPanelC1.add(this.jPanel4, gridBagConstraints);
/*     */     
/* 175 */     this.jLabel3.setText(getResourceBundle().getString("IDS_STATUS"));
/* 176 */     gridBagConstraints = new GridBagConstraints();
/* 177 */     gridBagConstraints.fill = 1;
/* 178 */     gridBagConstraints.weightx = 3.0D;
/* 179 */     this.jPanelC1.add(this.jLabel3, gridBagConstraints);
/*     */     
/* 181 */     this.jLabel4.setText(getResourceBundle().getString("IDS_TIME"));
/* 182 */     gridBagConstraints = new GridBagConstraints();
/* 183 */     gridBagConstraints.gridwidth = 0;
/* 184 */     gridBagConstraints.fill = 1;
/* 185 */     this.jPanelC1.add(this.jLabel4, gridBagConstraints);
/*     */     
/* 187 */     this.jMessage.setBorder(new BevelBorder(1));
/* 188 */     gridBagConstraints = new GridBagConstraints();
/* 189 */     gridBagConstraints.fill = 1;
/* 190 */     gridBagConstraints.weightx = 3.0D;
/* 191 */     gridBagConstraints.weighty = 3.0D;
/* 192 */     this.jPanelC1.add(this.jMessage, gridBagConstraints);
/*     */     
/* 194 */     this.jTimeleft.setBorder(new BevelBorder(1));
/* 195 */     gridBagConstraints = new GridBagConstraints();
/* 196 */     gridBagConstraints.gridwidth = 0;
/* 197 */     gridBagConstraints.fill = 1;
/* 198 */     gridBagConstraints.weighty = 3.0D;
/* 199 */     this.jPanelC1.add(this.jTimeleft, gridBagConstraints);
/*     */     
/* 201 */     gridBagConstraints = new GridBagConstraints();
/* 202 */     gridBagConstraints.gridwidth = 0;
/* 203 */     gridBagConstraints.fill = 1;
/* 204 */     this.jPanelC1.add(this.jPanel5, gridBagConstraints);
/*     */     
/* 206 */     this.jBytesread.setBorder(new BevelBorder(1));
/* 207 */     gridBagConstraints = new GridBagConstraints();
/* 208 */     gridBagConstraints.gridwidth = 0;
/* 209 */     gridBagConstraints.fill = 1;
/* 210 */     gridBagConstraints.weighty = 3.0D;
/* 211 */     this.jPanelC1.add(this.jBytesread, gridBagConstraints);
/*     */     
/* 213 */     gridBagConstraints = new GridBagConstraints();
/* 214 */     gridBagConstraints.gridwidth = 0;
/* 215 */     gridBagConstraints.fill = 1;
/* 216 */     gridBagConstraints.anchor = 15;
/* 217 */     gridBagConstraints.weighty = 2.0D;
/* 218 */     this.jPanelC1.add(this.jPanel6, gridBagConstraints);
/*     */     
/* 220 */     this.jPanelDown.add(this.jPanelC1, "Center");
/*     */     
/* 222 */     gridBagConstraints = new GridBagConstraints();
/* 223 */     gridBagConstraints.fill = 1;
/* 224 */     gridBagConstraints.anchor = 15;
/* 225 */     gridBagConstraints.weighty = 1.0D;
/* 226 */     add(this.jPanelDown, gridBagConstraints);
/*     */   }
/*     */ 
/*     */   
/*     */   void onActivate() {
/* 231 */     this.isError = false;
/* 232 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(1L, "IDS_TITLE_DOWNLOAD"));
/* 233 */     String image = (String)SDCurrentContext.getInstance().getLocSettings().get(ImgKeyParser.getImgKey(DownloadPanel.class.getName(), SDCurrentContext.getInstance().getSelectedTool().getDescription()));
/* 234 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(8L, image));
/*     */     
/* 236 */     this.jMessage.setText(" ");
/* 237 */     this.jTimeleft.setText(" ");
/* 238 */     this.jBytesread.setText(" ");
/*     */     
/* 240 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(23L, this));
/* 241 */     DeviceInfo info = SDCurrentContext.getInstance().getNewAppInfo();
/* 242 */     String deviceName = SDCurrentContext.getInstance().getCurrDevice().getDescription();
/* 243 */     String application = info.getAppName();
/* 244 */     String version = info.getVersion();
/* 245 */     String strLanguage = null;
/*     */     try {
/* 247 */       strLanguage = info.getLanguage();
/* 248 */     } catch (Exception e) {}
/*     */ 
/*     */     
/*     */     try {
/* 252 */       Command command = new Command(11);
/* 253 */       command.addParameter("devicename", deviceName);
/* 254 */       command.addParameter("applicationdesc", application);
/* 255 */       command.addParameter("versionno", version);
/* 256 */       command.addParameter("languageid", String.valueOf(strLanguage));
/*     */       
/* 258 */       ServerRequestor.getInstance().sendRequest(command);
/* 259 */     } catch (Throwable e) {
/* 260 */       this.log.error("Error sending download notification to server - error:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void onBtnPressed(byte btn) {}
/*     */ 
/*     */   
/*     */   byte showOrHide_Callback() {
/* 269 */     return 13;
/*     */   }
/*     */   
/*     */   public void OnBuildAppFile() {
/* 273 */     this.log.debug("Notification from device driver: OnBuildAppFile()");
/* 274 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)2), new Boolean(false)));
/* 275 */     if (this.isError) {
/* 276 */       this.log.error("Error in OnBuildAppFile(); SWDL2TECH-ERROR");
/*     */       return;
/*     */     } 
/* 279 */     this.jMessage.setText(getResourceBundle().getString("IDS_BUILDING_APPLIST"));
/*     */   }
/*     */   
/*     */   public void OnDataTransmitted(int progress) {
/* 283 */     this.log.debug("Notification from device driver: OnDataTransmitted(" + Integer.toString(progress) + ")");
/* 284 */     if (this.isError) {
/* 285 */       this.log.error("Error in OnDataTransmitted(); SWDL2TECH-ERROR");
/*     */       
/*     */       return;
/*     */     } 
/* 289 */     long endTime = System.currentTimeMillis();
/* 290 */     long updateSpan = endTime - this.lastTimeRefresh;
/* 291 */     if (updateSpan / 1000L >= 4L) {
/* 292 */       long span = endTime - this.startTime;
/*     */       
/* 294 */       long seconds = span / 1000L - this.subtract;
/* 295 */       double timeLeftFactor = seconds / progress;
/* 296 */       long timeLeft = (long)(timeLeftFactor * (this.jProgressDown.getMaximum() - progress));
/*     */       
/* 298 */       int hours = (int)timeLeft / 3600;
/* 299 */       int minutes = (int)(timeLeft - (hours * 3600)) / 60;
/* 300 */       int timeSeconds = (int)(timeLeft - (hours * 3600) - (minutes * 60));
/*     */       
/* 302 */       minutes += timeSeconds / 60;
/* 303 */       timeSeconds %= 60;
/* 304 */       hours += minutes / 60;
/* 305 */       minutes %= 60;
/* 306 */       DecimalFormat nrFormat = new DecimalFormat();
/* 307 */       nrFormat.setMinimumIntegerDigits(2);
/* 308 */       this.jTimeleft.setText(nrFormat.format(hours) + ":" + nrFormat.format(minutes) + ":" + nrFormat.format(timeSeconds));
/* 309 */       this.lastTimeRefresh = endTime;
/*     */     } 
/*     */     
/* 312 */     String oldMsg = this.jMessage.getText();
/* 313 */     String newMsg = getResourceBundle().getString("IDS_DOWNLOAD");
/* 314 */     if (oldMsg.compareTo(newMsg) != 0)
/* 315 */       this.jMessage.setText(newMsg); 
/* 316 */     this.jProgressDown.setValue(progress);
/*     */ 
/*     */     
/* 319 */     String msg = getResourceBundle().getString("IDS_BYTESREADFROM");
/* 320 */     String[] params = new String[2];
/* 321 */     params[0] = Integer.toString(progress);
/* 322 */     params[1] = Integer.toString(this.jProgressDown.getMaximum());
/* 323 */     this.jBytesread.setText(MessageFormater.FormatMsg(msg, params));
/*     */   }
/*     */   
/*     */   public void OnDirRelearn() {
/* 327 */     this.log.debug("Notification from device driver: OnDirRelearn()");
/* 328 */     if (this.isError) {
/* 329 */       this.log.error("Error in OnDirRelearn(); SWDL2TECH-ERROR");
/*     */       return;
/*     */     } 
/* 332 */     this.jMessage.setText(getResourceBundle().getString("IDS_DIRRELEARN"));
/*     */   }
/*     */   
/*     */   public void OnDownAborted(int lError) {
/* 336 */     this.log.debug("Notification from device driver: OnDownAborted(" + Integer.toString(lError) + ")");
/* 337 */     String msg = null;
/* 338 */     this.isError = true;
/* 339 */     if (lError >= 2) {
/* 340 */       msg = getResourceBundle().getString("IDS_ERROR");
/*     */     } else {
/* 342 */       msg = getResourceBundle().getString("IDS_SD_ERROR_BASE" + Integer.toString(lError));
/*     */     } 
/* 344 */     this.jMessage.setText(msg);
/* 345 */     this.log.error("msg");
/* 346 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)2), new Boolean(true)));
/*     */   }
/*     */   
/*     */   public void OnEndDownload() {
/* 350 */     this.log.debug("Notification from device driver: OnEndDownload()");
/* 351 */     if (this.downloadEnded) {
/*     */       return;
/*     */     }
/* 354 */     this.downloadEnded = true;
/*     */     
/* 356 */     if (this.isError) {
/* 357 */       this.log.error("Error in OnEndDownload(); SWDL2TECH-ERROR");
/*     */       
/*     */       return;
/*     */     } 
/* 361 */     this.jMessage.setText(getResourceBundle().getString("IDS_END_DOWNLOAD"));
/* 362 */     this.jTimeleft.setText("");
/* 363 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)2), new Boolean(true)));
/* 364 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(12L, new Byte((byte)2), "IDS_BTN_CLOSE"));
/* 365 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(11L));
/*     */   }
/*     */   
/*     */   public void OnEndErase() {
/* 369 */     this.log.debug("Notification from device driver: OnEndErase()");
/* 370 */     this.jTimeleft.setText("");
/*     */   }
/*     */   
/*     */   public void OnEraseProgress(int progress) {
/* 374 */     this.log.debug("Notification from device driver: OnEraseProgress(" + Integer.toString(progress) + ")");
/* 375 */     if (this.isError) {
/* 376 */       this.log.error("Error in OnEraseProgress(); SWDL2TECH-ERROR");
/*     */       
/*     */       return;
/*     */     } 
/* 380 */     long endTime = System.currentTimeMillis();
/* 381 */     long updateSpan = endTime - this.lastTimeRefresh;
/* 382 */     if (updateSpan / 1000L >= 4L) {
/* 383 */       long span = endTime - this.startTime;
/*     */       
/* 385 */       long seconds = span / 1000L - this.subtract;
/* 386 */       double timeLeftFactor = seconds / progress;
/* 387 */       long timeLeft = (long)(timeLeftFactor * (this.jProgressErase.getMaximum() - progress));
/*     */       
/* 389 */       int hours = (int)timeLeft / 3600;
/* 390 */       int minutes = (int)(timeLeft - (hours * 3600)) / 60;
/* 391 */       int timeSeconds = (int)(timeLeft - (hours * 3600) - (minutes * 60));
/*     */       
/* 393 */       minutes += timeSeconds / 60;
/* 394 */       timeSeconds %= 60;
/* 395 */       hours += minutes / 60;
/* 396 */       minutes %= 60;
/* 397 */       DecimalFormat nrFormat = new DecimalFormat();
/* 398 */       nrFormat.setMinimumIntegerDigits(2);
/* 399 */       this.jTimeleft.setText(nrFormat.format(hours) + ":" + nrFormat.format(minutes) + ":" + nrFormat.format(timeSeconds));
/* 400 */       this.lastTimeRefresh = endTime;
/*     */     } 
/*     */     
/* 403 */     String oldMsg = this.jMessage.getText();
/* 404 */     String newMsg = getResourceBundle().getString("IDS_MSG_ERASE_PROGRESS");
/* 405 */     if (oldMsg.compareTo(newMsg) != 0)
/* 406 */       this.jMessage.setText(newMsg); 
/* 407 */     this.jProgressErase.setValue(progress);
/*     */   }
/*     */   
/*     */   public void OnSeeking() {
/* 411 */     this.log.debug("Notification from device driver: OnSeeking()");
/* 412 */     if (this.isError) {
/* 413 */       this.log.error("Error in OnSeeking(); SWDL2TECH-ERROR");
/*     */       return;
/*     */     } 
/* 416 */     this.jMessage.setText(getResourceBundle().getString("IDS_SEEK"));
/*     */   }
/*     */   
/*     */   public void OnStartDownload(int maxCount) {
/* 420 */     this.log.debug("Notification from device driver: OnStartDownload(" + Integer.toString(maxCount) + ")");
/* 421 */     this.startTime = System.currentTimeMillis();
/* 422 */     if (this.isError) {
/* 423 */       this.log.error("Error in OnStartDownload(); SWDL2TECH-ERROR");
/*     */       return;
/*     */     } 
/* 426 */     this.jMessage.setText(getResourceBundle().getString("IDS_STARTING_DOWNLOAD"));
/*     */     
/* 428 */     this.jProgressDown.setMinimum(0);
/* 429 */     this.jProgressDown.setMaximum(maxCount);
/* 430 */     this.jProgressDown.setValue(0);
/*     */     
/* 432 */     String msg = getResourceBundle().getString("IDS_BYTESREADFROM");
/* 433 */     String[] params = new String[2];
/* 434 */     params[0] = "0";
/* 435 */     params[1] = Integer.toString(maxCount);
/* 436 */     this.jBytesread.setText(MessageFormater.FormatMsg(msg, params));
/*     */   }
/*     */   
/*     */   public void OnStartErase(int maxCount) {
/* 440 */     this.log.debug("Notification from device driver: OnStartErase(" + Integer.toString(maxCount) + ")");
/* 441 */     this.startTime = System.currentTimeMillis();
/* 442 */     if (this.isError) {
/* 443 */       this.log.error("Error in OnStartErase(); SWDL2TECH-ERROR");
/*     */       return;
/*     */     } 
/* 446 */     this.jMessage.setText(getResourceBundle().getString("IDS_STARTING_ERASE"));
/* 447 */     this.jProgressErase.setMinimum(0);
/* 448 */     this.jProgressErase.setMaximum(maxCount);
/* 449 */     this.jProgressErase.setValue(0);
/*     */   }
/*     */   
/*     */   public boolean OnTechReset() {
/* 453 */     this.log.debug("Notification from device driver: OnTechReset()");
/* 454 */     boolean ret = false;
/* 455 */     long stTime = System.currentTimeMillis();
/*     */     
/* 457 */     String msg = getResourceBundle().getString("IDS_TECH2_MESSAGE");
/* 458 */     int messageLineLength = Integer.parseInt((String)SDCurrentContext.getInstance().getLocSettings().get("msgbox.line.charnr"));
/* 459 */     msg = MessageFormater.FormatMsgBR(msg, messageLineLength);
/* 460 */     int res = JOptionPane.showConfirmDialog(this, msg, getResourceBundle().getString("IDS_TITLE_SWDL"), 2);
/* 461 */     if (res == 0) {
/* 462 */       if (SDCurrentContext.getInstance().getSelectedTool().equals(Device.TECH1)) {
/* 463 */         this.jMessage.setText(getResourceBundle().getString("IDS_RELEARN_DELAY"));
/*     */         try {
/* 465 */           Thread.sleep(25000L);
/* 466 */         } catch (Exception e) {}
/*     */       } 
/*     */       
/* 469 */       ret = true;
/* 470 */       long endTime = System.currentTimeMillis();
/* 471 */       long span = endTime - stTime;
/* 472 */       if (isVisible()) {
/* 473 */         this.subtract = span / 1000L;
/*     */       }
/*     */     } else {
/* 476 */       OnDownAborted(10);
/*     */     } 
/* 478 */     return ret;
/*     */   }
/*     */   
/*     */   public void PDTBIOSMessage() {
/* 482 */     this.log.debug("Notification from device driver: PDTBIOSMessage()");
/* 483 */     this.jMessage.setText(getResourceBundle().getString("IDS_BIOS_DOWNLOAD"));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\DownloadPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */