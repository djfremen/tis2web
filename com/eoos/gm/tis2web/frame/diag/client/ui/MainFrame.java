/*     */ package com.eoos.gm.tis2web.frame.diag.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.diag.client.DisplayException;
/*     */ import com.eoos.gm.tis2web.frame.diag.client.IApplicationCallback;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.swing.v2.ProgressDialog;
/*     */ import com.eoos.scsm.v2.swing.v2.ProgressInfoRI;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.progress.v2.CancellationListener;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressObserver;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MainFrame
/*     */   extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = 23102008001L;
/*  47 */   private static final Logger log = Logger.getLogger(MainFrame.class);
/*     */   
/*     */   private IApplicationCallback callback;
/*     */ 
/*     */   
/*     */   private MainFrame(IApplicationCallback applicationCallback, Locale locale) {
/*  53 */     this.callback = applicationCallback;
/*  54 */     setTitle(applicationCallback.getText("diagnostic.tool"));
/*     */     
/*  56 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent e) {
/*  58 */             MainFrame.this.close();
/*     */           }
/*     */         });
/*     */     
/*  62 */     getContentPane().add(getRootPanel());
/*     */   }
/*     */   
/*     */   public IApplicationCallback getApplicationInterface() {
/*  66 */     return this.callback;
/*     */   }
/*     */   
/*     */   private JPanel createPanel(String name) {
/*  70 */     JPanel ret = new JPanel();
/*     */     
/*  72 */     return ret;
/*     */   }
/*     */   
/*     */   private JPanel getRootPanel() {
/*  76 */     JPanel panel = createPanel("root");
/*  77 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  79 */     GridBagConstraints c = new GridBagConstraints();
/*  80 */     c.fill = 1;
/*  81 */     c.insets = new Insets(2, 2, 2, 2);
/*  82 */     c.weightx = 1.0D;
/*  83 */     c.weighty = 1.0D;
/*     */     
/*  85 */     c.gridy = 0;
/*  86 */     c.gridx = 0;
/*  87 */     c.anchor = 17;
/*  88 */     c.fill = 0;
/*  89 */     panel.add(getIconPanel(), c);
/*     */     
/*  91 */     c.fill = 1;
/*  92 */     c.gridx = 1;
/*  93 */     c.anchor = 13;
/*  94 */     panel.add(getActionPanel(), c);
/*     */     
/*  96 */     c.gridx = 0;
/*  97 */     c.gridwidth = 2;
/*  98 */     c.gridy = 1;
/*  99 */     c.anchor = 10;
/* 100 */     panel.add(getFooterPanel(), c);
/*     */     
/* 102 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getIconPanel() {
/* 107 */     JPanel panel = createPanel("icon");
/* 108 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 110 */     GridBagConstraints c = new GridBagConstraints();
/* 111 */     c.fill = 0;
/* 112 */     c.weightx = 0.0D;
/*     */     
/* 114 */     c.anchor = 13;
/* 115 */     ImageIcon icon = new ImageIcon(getClass().getResource("imageicon.jpg"));
/* 116 */     JLabel wrapper = new JLabel(icon, 4);
/* 117 */     panel.add(wrapper, c);
/* 118 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getActionPanel() {
/* 123 */     JPanel panel = createPanel("action");
/* 124 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 126 */     GridBagConstraints c = new GridBagConstraints();
/* 127 */     c.weightx = 1.0D;
/* 128 */     c.insets = new Insets(5, 10, 5, 10);
/* 129 */     c.fill = 2;
/* 130 */     c.gridx = 0;
/*     */     
/* 132 */     panel.add(getButton1(), c);
/*     */     
/* 134 */     panel.add(getButton2(), c);
/*     */     
/* 136 */     panel.add(getButton3(), c);
/*     */     
/* 138 */     panel.add(getButton4(), c);
/*     */     
/* 140 */     panel.add(getButton5(), c);
/*     */     
/* 142 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel getFooterPanel() {
/* 146 */     JPanel panel = new JPanel();
/* 147 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 149 */     GridBagConstraints c = new GridBagConstraints();
/* 150 */     c.fill = 2;
/*     */     
/* 152 */     c.weightx = 1.0D;
/* 153 */     c.gridy = 0;
/* 154 */     panel.add(new JSeparator(), c);
/*     */     
/* 156 */     c.weightx = 0.0D;
/* 157 */     c.gridy = 1;
/* 158 */     c.fill = 0;
/* 159 */     c.anchor = 13;
/* 160 */     c.insets = new Insets(5, 10, 5, 10);
/* 161 */     panel.add(getExitButton(), c);
/*     */     
/* 163 */     return panel;
/*     */   }
/*     */   
/*     */   private JButton getButton1() {
/* 167 */     JButton button = new JButton(getApplicationInterface().getText("send.log.archive"));
/* 168 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 171 */             MainFrame.this.sendLogArchive();
/*     */           }
/*     */         });
/* 174 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getButton2() {
/* 178 */     JButton button = new JButton(getApplicationInterface().getText("save.log.archive"));
/* 179 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 182 */             MainFrame.this.saveLogArchive();
/*     */           }
/*     */         });
/*     */     
/* 186 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getButton3() {
/* 190 */     JButton button = new JButton(getApplicationInterface().getText("delete.swk"));
/* 191 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 194 */             MainFrame.this.deleteSWK();
/*     */           }
/*     */         });
/*     */     
/* 198 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getButton4() {
/* 202 */     JButton button = new JButton(getApplicationInterface().getText("show.system.info"));
/* 203 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 206 */             MainFrame.this.showSysInfo();
/*     */           }
/*     */         });
/*     */     
/* 210 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getButton5() {
/* 214 */     JButton button = new JButton(getApplicationInterface().getText("test.connection.speed"));
/* 215 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 218 */             MainFrame.this.testConnectionSpeed();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 223 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getExitButton() {
/* 227 */     JButton button = new JButton(getApplicationInterface().getText("finish"));
/* 228 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 231 */             MainFrame.this.close();
/*     */           }
/*     */         });
/* 234 */     return button;
/*     */   }
/*     */   
/*     */   private ProgressObserver createProgressObserver(final String initialMsg) {
/* 238 */     Util.ensureNotAWTThread();
/* 239 */     ProgressObserver ret = ProgressDialog.create(getOwner(), new ProgressDialog.Callback()
/*     */         {
/*     */           public String getLabel(String key) {
/* 242 */             return MainFrame.this.getApplicationInterface().getText(key);
/*     */           }
/*     */           
/*     */           public int getEstimatedMessageWidth() {
/* 246 */             return 20;
/*     */           }
/*     */         },  true);
/* 249 */     ret.setCancellationListener((CancellationListener)new CancellationListener.Interrupter(Thread.currentThread()));
/* 250 */     ret.onProgress(new ProgressInfo() {
/*     */           public String toString() {
/* 252 */             return (initialMsg != null) ? initialMsg : "";
/*     */           }
/*     */         });
/* 255 */     return ret;
/*     */   }
/*     */   
/*     */   private void sendLogArchive() {
/* 259 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 263 */               String[] mailProperties = MailDataForm.show(MainFrame.this, MainFrame.this.getApplicationInterface());
/* 264 */               if (mailProperties == null) {
/* 265 */                 throw new InterruptedException("user.abortion");
/*     */               }
/* 267 */               ProgressObserver progressObserver = MainFrame.this.createProgressObserver((String)null);
/*     */               try {
/* 269 */                 progressObserver.onProgress((ProgressInfo)new ProgressInfoRI(MainFrame.this.getApplicationInterface().getText("please.wait")));
/* 270 */                 MainFrame.this.getApplicationInterface().sendLogArchive(progressObserver, mailProperties[0], mailProperties[1], mailProperties[2], mailProperties[3]);
/* 271 */                 MainFrame.this.showPopup("successfully.send.log.archive", 1);
/*     */               } finally {
/*     */                 
/* 274 */                 ((ProgressDialog)progressObserver).dispose();
/*     */               } 
/* 276 */             } catch (InterruptedException e) {
/* 277 */               Thread.currentThread().interrupt();
/* 278 */             } catch (Exception e) {
/* 279 */               MainFrame.log.error("unable to send mail - exception: " + e, e);
/* 280 */               MainFrame.this.showErrorPopup(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveLogArchive() {
/* 289 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 293 */               String input = MainFrame.this.fileSaveDialog(MainFrame.this.getApplicationInterface().getText("please.specify.target.file"));
/*     */               
/* 295 */               if (input == null || input.endsWith("null")) {
/* 296 */                 throw new InterruptedException("user.abortion");
/*     */               }
/* 298 */               File targetFile = new File(input);
/*     */               
/* 300 */               ProgressObserver progressObserver = MainFrame.this.createProgressObserver((String)null);
/*     */               try {
/* 302 */                 progressObserver.onProgress((ProgressInfo)new ProgressInfoRI(MainFrame.this.getApplicationInterface().getText("please.wait")));
/* 303 */                 MainFrame.this.getApplicationInterface().saveLogArchive(progressObserver, targetFile);
/* 304 */                 MainFrame.this.showPopup("successfully.saved.log.archive", 1);
/*     */               } finally {
/* 306 */                 ((ProgressDialog)progressObserver).dispose();
/*     */               } 
/* 308 */             } catch (InterruptedException e) {
/* 309 */               Thread.currentThread().interrupt();
/* 310 */             } catch (Exception e) {
/* 311 */               MainFrame.this.showErrorPopup(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void deleteSWK() {
/* 318 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 322 */               ProgressObserver progressObserver = MainFrame.this.createProgressObserver((String)null);
/*     */               try {
/* 324 */                 progressObserver.onProgress((ProgressInfo)new ProgressInfoRI(MainFrame.this.getApplicationInterface().getText("please.wait")));
/* 325 */                 MainFrame.this.getApplicationInterface().deleteSoftwareKey(progressObserver);
/* 326 */                 MainFrame.this.showPopup("successfully.deleted.swk", 1);
/*     */               } finally {
/* 328 */                 ((ProgressDialog)progressObserver).dispose();
/*     */               } 
/* 330 */             } catch (InterruptedException e) {
/* 331 */               Thread.currentThread().interrupt();
/*     */             }
/* 333 */             catch (Exception e) {
/* 334 */               MainFrame.this.showErrorPopup(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showSysInfo() {
/* 344 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/* 349 */               ProgressObserver progressObserver = MainFrame.this.createProgressObserver((String)null);
/*     */               try {
/* 351 */                 progressObserver.onProgress((ProgressInfo)new ProgressInfoRI(MainFrame.this.getApplicationInterface().getText("please.wait")));
/* 352 */                 Properties sysInfo = MainFrame.this.getApplicationInterface().retrieveSystemInformation(progressObserver);
/* 353 */                 if (Util.isNullOrEmpty(sysInfo)) {
/* 354 */                   throw new DisplayException("no.info.available");
/*     */                 }
/* 356 */                 progressObserver.onProgress(ProgressInfo.FINSIHED);
/* 357 */                 SystemInfoDialog.show(MainFrame.this, sysInfo, (I18NSupport.FixedLocale)MainFrame.this.getApplicationInterface());
/*     */               } finally {
/*     */                 
/* 360 */                 ((ProgressDialog)progressObserver).dispose();
/*     */               } 
/* 362 */             } catch (InterruptedException e) {
/* 363 */               Thread.currentThread().interrupt();
/* 364 */             } catch (Exception e) {
/* 365 */               MainFrame.this.showErrorPopup(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showErrorPopup(final Exception e) {
/* 375 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             String msg;
/* 379 */             if (e instanceof DisplayException) {
/* 380 */               msg = e.getMessage();
/*     */             } else {
/* 382 */               msg = MainFrame.this.getApplicationInterface().getText("error.see.log");
/*     */             } 
/* 384 */             JOptionPane.showMessageDialog(MainFrame.this, msg, null, 0);
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void showPopup(final String msgKey, final int popupDecoration) {
/* 391 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 394 */             JOptionPane.showMessageDialog(MainFrame.this, MainFrame.this.getApplicationInterface().getText(msgKey), null, popupDecoration);
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void show(final IApplicationCallback applicationCallback) throws InterruptedException {
/* 403 */     final Object sync = new Object();
/* 404 */     synchronized (sync) {
/* 405 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 408 */               JFrame frame = new MainFrame(applicationCallback, Locale.ENGLISH)
/*     */                 {
/*     */                   private static final long serialVersionUID = 1L;
/*     */                   
/*     */                   protected void close() {
/* 413 */                     dispose();
/* 414 */                     synchronized (sync) {
/* 415 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/*     */               
/* 420 */               frame.pack();
/* 421 */               frame.setLocationRelativeTo(null);
/* 422 */               frame.setVisible(true);
/*     */             }
/*     */           });
/*     */       
/* 426 */       sync.wait();
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
/*     */   private String fileSaveDialog(String title) {
/* 445 */     FileDialog fdSave = new FileDialog(this, title, 1);
/* 446 */     fdSave.setFile("diag.piz");
/* 447 */     fdSave.setDirectory(System.getProperty("user.home"));
/*     */ 
/*     */ 
/*     */     
/* 451 */     fdSave.setModal(true);
/* 452 */     fdSave.setLocationRelativeTo(getContentPane());
/* 453 */     fdSave.setVisible(true);
/*     */     
/* 455 */     return fdSave.getDirectory() + fdSave.getFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void testConnectionSpeed() {
/* 462 */     (new Thread()
/*     */       {
/*     */         public void run() {
/*     */           try {
/*     */             final IApplicationCallback.SpeedTestResult result;
/* 467 */             UIUtil.ProgressObserver observer = UIUtil.showProgressObserverV2(MainFrame.this, (I18NSupport.FixedLocale)MainFrame.this.callback);
/*     */             try {
/* 469 */               result = MainFrame.this.callback.executeConnectionSpeedTest(observer);
/*     */             } finally {
/* 471 */               observer.close();
/*     */             } 
/* 473 */             SwingUtilities.invokeLater(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 476 */                     if (!Util.isNullOrEmpty(result.getTestedServers())) {
/* 477 */                       JDialog dialog = MainFrame.this.createSpeedTestResultDialog(result);
/* 478 */                       dialog.pack();
/* 479 */                       dialog.setLocationRelativeTo(MainFrame.this);
/* 480 */                       UIUtil.showDialog(dialog);
/*     */                     } else {
/* 482 */                       UIUtil.showErrorPopup(MainFrame.this, MainFrame.this.callback.getText("empty.test.result"));
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 });
/* 487 */           } catch (InterruptedException e) {}
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JDialog createSpeedTestResultDialog(IApplicationCallback.SpeedTestResult result) {
/* 496 */     final JDialog ret = new JDialog(this);
/* 497 */     ret.setModal(true);
/* 498 */     ret.setTitle(this.callback.getText("speed.test.result"));
/* 499 */     JPanel panel = new JPanel(new GridBagLayout());
/* 500 */     GridBagConstraints c = new GridBagConstraints();
/* 501 */     c.gridy = 0;
/* 502 */     c.weightx = 0.0D;
/* 503 */     c.weighty = 0.0D;
/* 504 */     c.insets = new Insets(5, 5, 2, 2);
/* 505 */     c.fill = 2;
/*     */     
/* 507 */     for (Iterator<IDownloadServer> iter = result.getTestedServers().iterator(); iter.hasNext(); ) {
/* 508 */       IDownloadServer server = iter.next();
/*     */       
/* 510 */       JLabel label = new JLabel(server.getDescription() + ":", 4);
/* 511 */       panel.add(label, c);
/*     */       
/* 513 */       panel.add(Box.createGlue(), c);
/*     */       
/* 515 */       BigDecimal speed = result.getConnectionSpeed(server);
/* 516 */       speed = speed.setScale(2, 4);
/* 517 */       String unit = "KB/s";
/* 518 */       if (speed.compareTo(BigDecimal.valueOf(1000L)) > 0) {
/* 519 */         unit = "MB/s";
/* 520 */         speed = speed.divide(BigDecimal.valueOf(1000L));
/* 521 */         speed = speed.setScale(2, 4);
/*     */       } 
/*     */       
/* 524 */       JLabel label2 = new JLabel(speed.toString() + " " + unit, 2);
/*     */       
/* 526 */       panel.add(label2, c);
/* 527 */       c.gridy++;
/*     */     } 
/*     */     
/* 530 */     c.anchor = 10;
/* 531 */     c.fill = 2;
/* 532 */     c.gridwidth = 3;
/* 533 */     c.weightx = 1.0D;
/*     */     
/* 535 */     panel.add(new JSeparator(), c);
/*     */     
/* 537 */     c.gridy++;
/* 538 */     c.anchor = 10;
/* 539 */     c.fill = 0;
/* 540 */     c.weightx = 0.0D;
/*     */     
/* 542 */     JButton closeButton = new JButton(this.callback.getText("ok"));
/* 543 */     closeButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 546 */             ret.dispose();
/*     */           }
/*     */         });
/* 549 */     panel.add(closeButton, c);
/*     */     
/* 551 */     ret.getContentPane().add(panel);
/*     */     
/* 553 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/* 558 */     show(IApplicationCallback.TEST);
/*     */   }
/*     */   
/*     */   protected abstract void close();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\clien\\ui\MainFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */