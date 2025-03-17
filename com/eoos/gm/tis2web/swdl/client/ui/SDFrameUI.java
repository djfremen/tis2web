/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ 
/*     */ import ca.beq.util.win32.registry.RegistryKey;
/*     */ import ca.beq.util.win32.registry.RegistryValue;
/*     */ import ca.beq.util.win32.registry.RootKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.install.Installer;
/*     */ import com.eoos.gm.tis2web.swdl.client.driver.TechDriver;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.NotificationHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.starter.impl.Starter;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.MessageFormater;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.ResourceDataProvider;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.LCI;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.RollingFileAppender;
/*     */ 
/*     */ public class SDFrameUI
/*     */   extends JFrame implements NotificationHandler {
/*     */   private static final long serialVersionUID = 1L;
/*  51 */   private static Logger log = Logger.getLogger(SDFrameUI.class); private JPanel northPanel; private JPanel westPanel; private JLabel imageLabel; private JPanel eastPanel; private JLabel jTitle;
/*     */   private JPanel titleSouthPanel;
/*     */   private SDButtonPanel buttonPanel;
/*     */   private SDWizardPanel wizardPanel;
/*     */   
/*     */   public SDFrameUI() {
/*  57 */     SDNotificationServer.getInstance().register(1L, this);
/*  58 */     SDNotificationServer.getInstance().register(3L, this);
/*  59 */     SDNotificationServer.getInstance().register(2L, this);
/*  60 */     SDNotificationServer.getInstance().register(7L, this);
/*  61 */     SDNotificationServer.getInstance().register(8L, this);
/*  62 */     SDNotificationServer.getInstance().register(14L, this);
/*  63 */     SDNotificationServer.getInstance().register(30L, this);
/*     */     
/*  65 */     init();
/*     */     
/*  67 */     this.waitDlg = new WaitDlg(this, true);
/*  68 */     this.waitDlg.setLocationRelativeTo(this);
/*     */     
/*  70 */     this.srvDownDlg = new ServerDownloadDlg(this, true);
/*  71 */     this.srvDownDlg.setLocationRelativeTo(this);
/*     */     
/*  73 */     this.setupInstrDlg = new SetupInstrDlg(this.waitDlg, true);
/*  74 */     this.setupInstrDlg.setLocationRelativeTo(this);
/*     */   }
/*     */   private JPanel nPanel; private JPanel wPanel; private JPanel ePanel;
/*     */   private JPanel sPanel;
/*     */   
/*     */   private void init() {
/*  80 */     this.resourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/swdl/client/resources/sdstrings", SDCurrentContext.getInstance().getUILanguage());
/*  81 */     GlobalInstanceProvider.registerInstance("sdstrings", this.resourceBundle);
/*     */     
/*  83 */     setTitle(this.resourceBundle.getString("IDS_TITLE_SWDL"));
/*     */     
/*  85 */     SDFrameUI.class.getClassLoader();
/*     */     try {
/*  87 */       ImageIcon img = new ImageIcon(ResourceDataProvider.getInstance().getData("swdl_icon.jpg"));
/*  88 */       setIconImage(img.getImage());
/*  89 */     } catch (Exception e) {
/*  90 */       log.error("unable to set icon -exception:" + e, e);
/*     */     } 
/*  92 */     this.northPanel = new JPanel();
/*  93 */     this.northPanel.setLayout(new BorderLayout());
/*  94 */     getContentPane().add(this.northPanel, "North");
/*     */     
/*  96 */     this.jTitle = new JLabel();
/*  97 */     this.jTitle.setHorizontalAlignment(0);
/*  98 */     this.jTitle.setFont(new Font(null, 1, 16));
/*  99 */     this.northPanel.add(this.jTitle, "North");
/* 100 */     this.titleSouthPanel = new JPanel();
/* 101 */     this.titleSouthPanel.setLayout(new FlowLayout());
/* 102 */     this.northPanel.add(this.titleSouthPanel, "South");
/*     */     
/* 104 */     this.wizardPanel = new SDWizardPanel();
/* 105 */     getContentPane().add(this.wizardPanel, "Center");
/*     */     
/* 107 */     this.buttonPanel = new SDButtonPanel();
/* 108 */     this.buttonPanel.setRootPane(getRootPane());
/* 109 */     this.buttonPanel.setRequestFocusEnabled(true);
/* 110 */     this.buttonPanel.requestDefaultFocus();
/* 111 */     getContentPane().add(this.buttonPanel, "South");
/*     */     
/* 113 */     this.westPanel = new JPanel();
/* 114 */     this.westPanel.setLayout(new BorderLayout());
/* 115 */     getContentPane().add(this.westPanel, "West");
/* 116 */     this.nPanel = new JPanel();
/* 117 */     this.nPanel.setLayout(new FlowLayout());
/* 118 */     this.westPanel.add(this.nPanel, "North");
/* 119 */     this.sPanel = new JPanel();
/* 120 */     this.sPanel.setLayout(new FlowLayout());
/* 121 */     this.westPanel.add(this.sPanel, "South");
/* 122 */     this.wPanel = new JPanel();
/* 123 */     this.wPanel.setLayout(new FlowLayout());
/* 124 */     this.westPanel.add(this.wPanel, "West");
/* 125 */     this.ePanel = new JPanel();
/* 126 */     this.ePanel.setLayout(new FlowLayout());
/* 127 */     this.westPanel.add(this.ePanel, "East");
/* 128 */     this.cPanel = new JPanel();
/* 129 */     this.cPanel.setLayout(new FlowLayout());
/* 130 */     this.westPanel.add(this.cPanel, "Center");
/* 131 */     this.imageLabel = new JLabel();
/* 132 */     this.imageLabel.setBorder(new BevelBorder(1));
/* 133 */     this.cPanel.add(this.imageLabel);
/*     */     
/* 135 */     this.eastPanel = new JPanel();
/* 136 */     this.eastPanel.setLayout(new FlowLayout());
/* 137 */     getContentPane().add(this.eastPanel, "East");
/*     */     
/* 139 */     setDefaultCloseOperation(0);
/* 140 */     this.wizardPanel.switchView("SelectDevice", false);
/*     */     
/* 142 */     pack();
/*     */   }
/*     */   private JPanel cPanel; private WaitDlg waitDlg; private ServerDownloadDlg srvDownDlg; private ResourceBundle resourceBundle; private SetupInstrDlg setupInstrDlg;
/*     */   public void destroy() {
/* 146 */     GlobalInstanceProvider.unregisterInstance("sdstrings");
/* 147 */     SDNotificationServer.destroy();
/*     */   }
/*     */   
/*     */   public void handleNotification(Notification msg) {
/*     */     try {
/*     */       String mssg, message;
/* 153 */       SDEvent eve = (SDEvent)msg;
/* 154 */       switch ((int)eve.getType()) {
/*     */         case 1:
/* 156 */           this.jTitle.setText(this.resourceBundle.getString((String)eve.getParam(0)));
/*     */           break;
/*     */         
/*     */         case 3:
/* 160 */           onBtnPressed(((Byte)eve.getParam(0)).byteValue());
/*     */           break;
/*     */         
/*     */         case 2:
/* 164 */           mssg = MessageFormater.FormatMsg(this.resourceBundle.getString((String)eve.getParam(0)), (String[])eve.getParam(3));
/* 165 */           message = MessageFormater.FormatMsgBR(mssg, Integer.parseInt((String)SDCurrentContext.getInstance().getLocSettings().get("msgbox.line.charnr")));
/* 166 */           JOptionPane.showMessageDialog(this, message, this.resourceBundle.getString((String)eve.getParam(1)), ((Integer)eve.getParam(2)).intValue());
/* 167 */           log.error(msg);
/*     */           break;
/*     */         
/*     */         case 7:
/* 171 */           if (((Boolean)eve.getParam(0)).booleanValue()) {
/* 172 */             this.waitDlg.setTextLabel2(this.resourceBundle.getString((String)eve.getParam(1)));
/* 173 */             this.waitDlg.setVisible(true); break;
/*     */           } 
/* 175 */           this.waitDlg.setVisible(false);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 30:
/* 180 */           this.waitDlg.setTextLabel2(this.resourceBundle.getString((String)eve.getParam(0)));
/*     */           break;
/*     */         
/*     */         case 8:
/* 184 */           if (eve.getParam(0) != null) {
/* 185 */             String imageName = (String)eve.getParam(0);
/*     */             try {
/* 187 */               ImageIcon icon = new ImageIcon(ResourceDataProvider.getInstance().getData(imageName));
/* 188 */               this.imageLabel.setIcon(icon);
/* 189 */             } catch (Exception e) {
/* 190 */               log.error("unable to set icon - exception: " + e, e);
/*     */             } 
/* 192 */             this.imageLabel.setVisible(true); break;
/*     */           } 
/* 194 */           this.imageLabel.setVisible(false);
/*     */           break;
/*     */         
/*     */         case 14:
/* 198 */           this.setupInstrDlg.setVisible(true);
/* 199 */           eve.addParam(new Boolean(this.setupInstrDlg.Ignore()));
/* 200 */           log.error(this.resourceBundle.getString("IDS_ERR_E1037"));
/*     */           break;
/*     */       } 
/*     */     
/* 204 */     } catch (Exception e) {
/* 205 */       System.out.println(getClass().getName() + ".handleNotification() - Exception caught: " + e.toString());
/* 206 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onBtnPressed(byte btnCode) {
/* 211 */     if (btnCode == 1) {
/* 212 */       SDSettingsDlg settings = new SDSettingsDlg(this, true, SDCurrentContext.getInstance().getCurrDevice());
/* 213 */       settings.setLocationRelativeTo(this);
/* 214 */       settings.setVisible(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void centerFrameInScreen() {
/* 219 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 220 */     Dimension frameSize = getSize();
/* 221 */     if (frameSize.height > screenSize.height) {
/* 222 */       frameSize.height = screenSize.height;
/*     */     }
/* 224 */     if (frameSize.width > screenSize.width) {
/* 225 */       frameSize.width = screenSize.width;
/*     */     }
/* 227 */     setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/*     */   }
/*     */   
/*     */   public static boolean isStartAllowed() {
/* 231 */     boolean result = true;
/*     */     try {
/* 233 */       result = Starter.getInstance().isStartAllowed();
/* 234 */     } catch (Exception e) {
/* 235 */       log.error("Can not check for 1st instance. Continuing startup process");
/*     */     } 
/* 237 */     log.debug("Single instance check returned: " + result);
/*     */     
/* 239 */     return result;
/*     */   }
/*     */   
/*     */   private static void displayErrorMessage(String msg) {
/*     */     try {
/* 244 */       JOptionPane.showMessageDialog(null, msg, "Exception", 0);
/* 245 */     } catch (Exception except) {
/* 246 */       log.error("Unable to display error message. Exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 251 */     if (args.length > 0 && 
/* 252 */       args[0].compareToIgnoreCase("local_debug") == 0) {
/* 253 */       System.setProperty("server.installation", "central");
/* 254 */       System.setProperty("command.url", "http://alex.eoos-technologies.com:8020/tis2web/swdl/server");
/* 255 */       System.setProperty("task.execution.url", "http://alex.eoos-technologies.com:8020/tis2web/exectask");
/* 256 */       System.setProperty("session.id", "VSP.alex");
/* 257 */       System.setProperty("language.id", "de_DE");
/* 258 */       System.setProperty("bac.code", "00000012345");
/* 259 */       System.setProperty("country.code", "DE");
/* 260 */       System.setProperty("dtc.upload.disabled", "false");
/* 261 */       System.setProperty("register.swk", "false");
/* 262 */       System.setProperty("eoos.ignore.persistent.proxy.settings", "true");
/* 263 */       System.setProperty("cookie", "SlNFU1NJT05JRD03QTJCMjMxNkExQ0QwRDdCMEVDRUJBQkM4ODc5QjhGNw==");
/*     */     } 
/*     */ 
/*     */     
/* 267 */     File homeDir = new File(System.getProperty("user.home"));
/* 268 */     homeDir = new File(homeDir, "swdl");
/* 269 */     if (!homeDir.exists()) {
/* 270 */       homeDir.mkdir();
/*     */     }
/*     */     
/* 273 */     Log4jUtil.attachConsoleAppender(Logger.getRootLogger(), Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 274 */     RollingFileAppender appender = new RollingFileAppender();
/* 275 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 276 */     appender.setFile((new File(homeDir, "startup.log")).getAbsolutePath());
/* 277 */     appender.setAppend(false);
/* 278 */     appender.setImmediateFlush(true);
/* 279 */     appender.setMaximumFileSize(52428800L);
/* 280 */     appender.setMaxBackupIndex(10);
/* 281 */     appender.activateOptions();
/*     */     
/* 283 */     Logger.getRootLogger().addAppender((Appender)appender);
/*     */     try {
/* 285 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.swdl.client.resources.sdstrings") == 0) {
/* 286 */         LCI lci = (LCI)Util.fromExternal(System.getProperty("lci"));
/* 287 */         BigInteger[] parts = Util.splitTimespan(BigInteger.valueOf(lci.getLicenseDuration()));
/* 288 */         boolean round = false;
/* 289 */         for (int i = 1; i < 5 && !round; i++) {
/* 290 */           round = !parts[i].equals(BigInteger.ZERO);
/*     */         }
/* 292 */         if (round) {
/* 293 */           parts[0] = parts[0].add(BigInteger.ONE);
/*     */         }
/* 295 */         System.setProperty("license.expiration.delay", parts[0].toString());
/*     */         
/* 297 */         if (Math.abs(System.currentTimeMillis() - lci.getServerTimestamp()) > lci.getMaxClockDifference()) {
/* 298 */           log.info("the system clock/server clock difference is not acceptable, showing error notification");
/* 299 */           UIUtil.showErrorPopup(null, LabelResource.getInstance().getMessage("error.system.clock.difference"));
/* 300 */           throw new InterruptedException("clock difference abortion");
/*     */         } 
/* 302 */         checkTech2WinInstallation();
/*     */         
/* 304 */         final Object sync = new Object();
/*     */         
/* 306 */         synchronized (sync) {
/* 307 */           SwingUtilities.invokeLater(new Runnable() {
/*     */                 public void run() {
/* 309 */                   new SDUINotifHandler();
/* 310 */                   SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(16L));
/* 311 */                   SDFrameUI ui = new SDFrameUI();
/* 312 */                   ui.centerFrameInScreen();
/*     */                   
/* 314 */                   if (Starter.getInstance().setEnvironment() == true) {
/* 315 */                     if (SDFrameUI.isStartAllowed() == true) {
/* 316 */                       ui.setVisible(true);
/* 317 */                       ui.addWindowListener(new WindowAdapter() {
/*     */                             public void windowClosing(WindowEvent e) {
/* 319 */                               synchronized (sync) {
/*     */                                 
/* 321 */                                 SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)2)));
/*     */                               } 
/*     */                             }
/*     */                           });
/*     */                     } else {
/* 326 */                       SDFrameUI.displayErrorMessage(ui.resourceBundle.getString("IDS_SWDL_CLIENT_INSTANCE_ALLREADY_RUNNING"));
/* 327 */                       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)2)));
/*     */                     } 
/*     */                   } else {
/*     */                     
/* 331 */                     SDFrameUI.displayErrorMessage(ui.resourceBundle.getString("IDS_SET_ENVIRONMENT_FAILED"));
/* 332 */                     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(3L, new Byte((byte)2)));
/*     */                   } 
/*     */                 }
/*     */               });
/*     */           
/* 337 */           sync.wait();
/*     */         } 
/*     */       } 
/* 340 */     } catch (InterruptedException e) {
/* 341 */       log.warn("execution interrupted/aborted - " + e.getMessage());
/* 342 */     } catch (Throwable t) {
/* 343 */       log.error("unable  to execute client - exception: ", t);
/*     */     } finally {
/*     */       
/* 346 */       TechDriver.discardInstance();
/* 347 */       System.exit(0);
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
/*     */   private static void checkTech2WinInstallation() {
/*     */     try {
/* 375 */       final Locale locale = Util.parseLocale(System.getProperty("language.id"));
/* 376 */       File dir = new File(System.getProperty("user.home") + "\\swdl\\t2win");
/* 377 */       Installer installer = new Installer(new Installer.Callback()
/*     */           {
/*     */             public String getSessionID() {
/* 380 */               return System.getProperty("session.id");
/*     */             }
/*     */             
/*     */             public JComponent getParentComponent() {
/* 384 */               return null;
/*     */             }
/*     */             
/*     */             public Installer.Callback.Mode getMode() {
/* 388 */               return Installer.Callback.UPDATE_VERSIONS_ONLY;
/*     */             }
/*     */             
/*     */             public Locale getLocale() {
/* 392 */               return locale;
/*     */             }
/*     */             
/*     */             public String getLabel(String key) {
/* 396 */               return LabelResource.getInstance().getLabel(key);
/*     */             }
/*     */             
/* 399 */             private final RootKey[] rootKeys = new RootKey[] { RootKey.HKEY_CLASSES_ROOT, RootKey.HKEY_CURRENT_CONFIG, RootKey.HKEY_CURRENT_USER, RootKey.HKEY_DYN_DATA, RootKey.HKEY_LOCAL_MACHINE, RootKey.HKEY_PERFORMANCE_DATA, RootKey.HKEY_USERS }, ;
/*     */             
/*     */             public IInstalledVersionLookup.IRegistryLookup.RegistryValue getRegistryValue(String key, String name) {
/* 402 */               IInstalledVersionLookup.IRegistryLookup.RegistryValue ret = null;
/*     */               try {
/* 404 */                 while (key.startsWith("\\")) {
/* 405 */                   key = key.substring(1);
/*     */                 }
/*     */                 
/* 408 */                 RegistryKey.initialize();
/*     */                 
/* 410 */                 int index = key.indexOf("\\");
/* 411 */                 String firstPart = key.substring(0, index).toUpperCase(Locale.ENGLISH);
/* 412 */                 String remainder = key.substring(index + 1);
/*     */                 
/* 414 */                 RootKey rootKey = null;
/* 415 */                 for (int i = 0; i < this.rootKeys.length && rootKey == null; i++) {
/* 416 */                   if (firstPart.equals(this.rootKeys[i].toString().toUpperCase(Locale.ENGLISH))) {
/* 417 */                     rootKey = this.rootKeys[i];
/*     */                   }
/*     */                 } 
/*     */                 
/* 421 */                 if (rootKey == null) {
/* 422 */                   rootKey = RootKey.HKEY_LOCAL_MACHINE;
/* 423 */                   remainder = firstPart + "\\" + remainder;
/*     */                 } 
/*     */                 
/* 426 */                 RegistryKey regKey = new RegistryKey(rootKey, remainder);
/* 427 */                 if (regKey.exists()) {
/* 428 */                   final RegistryValue value = regKey.getValue(name);
/* 429 */                   if (value != null) {
/* 430 */                     ret = new IInstalledVersionLookup.IRegistryLookup.RegistryValue()
/*     */                       {
/*     */                         public String getType() {
/* 433 */                           return value.getType().toString();
/*     */                         }
/*     */                         
/*     */                         public Object getData() {
/* 437 */                           return value.getData();
/*     */                         }
/*     */                       };
/*     */                   }
/*     */                 }
/*     */               
/* 443 */               } catch (Exception e) {
/* 444 */                 SDFrameUI.log.error("unable to access key: " + key + " and/or name: " + name + " - exception:" + e, e);
/*     */               } 
/* 446 */               return ret;
/*     */             }
/*     */           }dir);
/*     */       
/* 450 */       installer.processInstall(Arrays.asList(new Object[] { ClassificationFilter.create("TECH2WIN") }));
/*     */     }
/* 452 */     catch (Exception e) {
/* 453 */       log.error("unable to install, continuing - exception :", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SDFrameUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */