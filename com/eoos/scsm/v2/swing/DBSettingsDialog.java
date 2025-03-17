/*     */ package com.eoos.scsm.v2.swing;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class DBSettingsDialog
/*     */   extends JDialog
/*     */ {
/*  25 */   public static final Object URL = DBSettingsPanel.URL;
/*     */   
/*  27 */   public static final Object DRIVER = DBSettingsPanel.DRIVER;
/*     */   
/*  29 */   public static final Object USER = DBSettingsPanel.USER;
/*     */   
/*  31 */   public static final Object PWD = DBSettingsPanel.PWD;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DBSettingsPanel settingsPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DBSettingsDialog(Callback callback) {
/*  55 */     init(callback);
/*     */   }
/*     */   
/*     */   private DBSettingsDialog(Dialog parent, Callback callback) {
/*  59 */     super(parent);
/*  60 */     init(callback);
/*     */   }
/*     */   
/*     */   private DBSettingsDialog(Frame parent, Callback callback) {
/*  64 */     super(parent);
/*  65 */     init(callback);
/*     */   }
/*     */   
/*     */   private void init(Callback callback) {
/*  69 */     this.callback = callback;
/*  70 */     getContentPane().setLayout(new GridBagLayout());
/*  71 */     GridBagConstraints c = new GridBagConstraints();
/*  72 */     c.fill = 1;
/*  73 */     c.weightx = 1.0D;
/*  74 */     c.weighty = 1.0D;
/*  75 */     getContentPane().add(getRootPanel(), c);
/*     */     
/*  77 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e) {
/*  80 */             DBSettingsDialog.this.onCancel();
/*     */           }
/*     */         });
/*     */     
/*  84 */     setTitle(callback.getMessageResource().getText("specify.db"));
/*     */   }
/*     */   public static interface Callback2 extends Callback, DBSettingsPanel.Callback.ConnectionTest, DBSettingsPanel.Callback.Defaults {}
/*     */   
/*     */   private JPanel getRootPanel() {
/*  89 */     JPanel ret = new JPanel(new GridBagLayout());
/*  90 */     GridBagConstraints c = new GridBagConstraints();
/*  91 */     c.fill = 2;
/*  92 */     c.weightx = 1.0D;
/*     */     
/*  94 */     ret.add(getSettingsPanel(), c);
/*     */     
/*  96 */     c.gridy++;
/*  97 */     ret.add(new JSeparator(), c);
/*     */     
/*  99 */     c.gridy++;
/* 100 */     ret.add(getButtonPanel(), c);
/* 101 */     return ret;
/*     */   } public static interface Callback extends DBSettingsPanel.Callback {
/*     */     void onFinished(Properties param1Properties); }
/*     */   private JPanel getSettingsPanel() {
/* 105 */     this.settingsPanel = new DBSettingsPanel(this.callback);
/* 106 */     return this.settingsPanel;
/*     */   }
/*     */   
/*     */   private JPanel getButtonPanel() {
/* 110 */     JPanel ret = new JPanel(new FlowLayout(1, 10, 10));
/*     */     
/* 112 */     JButton buttonOK = new JButton(this.callback.getLabelResource().getText("ok"));
/* 113 */     buttonOK.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 116 */             final Properties properties = DBSettingsDialog.this.settingsPanel.getProperties();
/* 117 */             Util.createAndStartThread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*     */                     try {
/* 121 */                       DBSettingsDialog.this.callback.onFinished(properties);
/* 122 */                       UIUtil.close(DBSettingsDialog.this);
/* 123 */                     } catch (Exception e) {
/* 124 */                       UIUtil.showErrorPopup(DBSettingsDialog.this, DBSettingsDialog.this.callback.getMessage(e));
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 131 */     ret.add(buttonOK);
/*     */     
/* 133 */     JButton buttonCancel = new JButton(this.callback.getLabelResource().getText("cancel"));
/* 134 */     buttonCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 137 */             Util.createAndStartThread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*     */                     try {
/* 141 */                       DBSettingsDialog.this.callback.onFinished((Properties)null);
/* 142 */                       UIUtil.close(DBSettingsDialog.this);
/* 143 */                     } catch (Exception e) {
/* 144 */                       UIUtil.showErrorPopupAndClose(DBSettingsDialog.this, DBSettingsDialog.this.callback.getMessage(e));
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 151 */     ret.add(buttonCancel);
/*     */     
/* 153 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCancel() {
/* 158 */     dispose();
/*     */   }
/*     */   public static void show(Window owner, Callback callback) {
/*     */     JDialog dialog;
/* 162 */     Util.ensureAWTThread();
/*     */     
/* 164 */     if (owner != null && owner instanceof Frame) {
/* 165 */       dialog = new DBSettingsDialog((Frame)owner, callback);
/* 166 */     } else if (owner != null && owner instanceof Dialog) {
/* 167 */       dialog = new DBSettingsDialog((Dialog)owner, callback);
/*     */     } else {
/* 169 */       dialog = new DBSettingsDialog(callback);
/*     */     } 
/* 171 */     dialog.setModal(true);
/* 172 */     dialog.pack();
/* 173 */     dialog.setLocationRelativeTo(owner);
/* 174 */     dialog.setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Properties getUserInput(final DBSettingsPanel.Callback callback) throws InterruptedException {
/* 179 */     Util.ensureNotAWTThread();
/* 180 */     final Properties[] ret = new Properties[1];
/* 181 */     synchronized (ret) {
/* 182 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 185 */               DBSettingsDialog.show((Window)null, new DBSettingsDialog.Callback()
/*     */                   {
/*     */                     public String getPropertyKey(Object identifier) {
/* 188 */                       return callback.getPropertyKey(identifier);
/*     */                     }
/*     */                     
/*     */                     public I18NSupport.FixedLocale getMessageResource() {
/* 192 */                       return callback.getMessageResource();
/*     */                     }
/*     */                     
/*     */                     public String getMessage(Exception e) {
/* 196 */                       return callback.getMessage(e);
/*     */                     }
/*     */                     
/*     */                     public I18NSupport.FixedLocale getLabelResource() {
/* 200 */                       return callback.getLabelResource();
/*     */                     }
/*     */                     
/*     */                     public void onFinished(Properties properties) {
/* 204 */                       synchronized (ret) {
/* 205 */                         ret[0] = properties;
/* 206 */                         ret.notify();
/*     */                       } 
/*     */                     }
/*     */                   });
/*     */             }
/*     */           });
/* 212 */       ret.wait();
/*     */     } 
/* 214 */     return ret[0];
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\DBSettingsDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */