/*     */ package com.eoos.scsm.v2.swing;
/*     */ 
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public final class DBSettingsPanel
/*     */   extends JPanel
/*     */ {
/*  27 */   public static final Object URL = "url";
/*     */   
/*  29 */   public static final Object DRIVER = "driver";
/*     */   
/*  31 */   public static final Object USER = "user";
/*     */   
/*  33 */   public static final Object PWD = "password";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final Logger log = Logger.getLogger(DBSettingsPanel.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*  58 */   private Map keyToInput = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/*     */   
/*     */   private Callback callback;
/*     */   
/*     */   public DBSettingsPanel(Callback callback) {
/*  63 */     this.callback = callback;
/*     */     
/*  65 */     Callback.Defaults defaults = (Callback.Defaults)Util.prepareCast(callback, Callback.Defaults.class);
/*  66 */     Properties defaultValues = (defaults != null) ? defaults.getDefaults() : CollectionUtil.EMPTY_PROPERTIES;
/*     */     
/*  68 */     this.keyToInput.put(URL, new JTextField(getProperty(defaultValues, URL)));
/*  69 */     this.keyToInput.put(DRIVER, new JTextField(getProperty(defaultValues, DRIVER)));
/*  70 */     this.keyToInput.put(USER, new JTextField(getProperty(defaultValues, USER)));
/*  71 */     this.keyToInput.put(PWD, new JPasswordField(getProperty(defaultValues, URL)));
/*     */     
/*  73 */     setLayout(new GridBagLayout());
/*     */     
/*  75 */     GridBagConstraints c = new GridBagConstraints();
/*  76 */     c.fill = 2;
/*  77 */     c.insets = new Insets(2, 5, 2, 5);
/*  78 */     c.gridy = 0;
/*     */     
/*  80 */     for (Iterator iter = this.keyToInput.keySet().iterator(); iter.hasNext(); ) {
/*  81 */       Object id = iter.next();
/*  82 */       JLabel label = new JLabel(callback.getLabelResource().getText(id.toString()) + ":", 4);
/*     */       
/*  84 */       JTextField input = (JTextField)this.keyToInput.get(id);
/*  85 */       input.setColumns(Math.max(30, input.getColumns()));
/*  86 */       c.gridy++;
/*     */       
/*  88 */       c.anchor = 13;
/*  89 */       c.weightx = 0.3D;
/*  90 */       add(label, c);
/*     */       
/*  92 */       c.weightx = 0.6D;
/*  93 */       c.anchor = 17;
/*  94 */       add(input, c);
/*     */     } 
/*     */     
/*  97 */     Callback.ConnectionTest ctest = (Callback.ConnectionTest)Util.prepareCast(callback, Callback.ConnectionTest.class);
/*  98 */     if (ctest != null) {
/*  99 */       c.gridy = -1;
/* 100 */       c.gridx = 1;
/* 101 */       c.fill = 0;
/* 102 */       c.anchor = 13;
/* 103 */       add(getTestButton(), c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getProperty(Properties properties, Object id) {
/* 108 */     return properties.getProperty(this.callback.getPropertyKey(id));
/*     */   }
/*     */   
/*     */   private void setProperty(Properties properties, Object id, String value) {
/* 112 */     properties.put(this.callback.getPropertyKey(id), value);
/*     */   } public static interface Callback { String getPropertyKey(Object param1Object); I18NSupport.FixedLocale getLabelResource(); I18NSupport.FixedLocale getMessageResource(); String getMessage(Exception param1Exception); public static interface Defaults {
/*     */       Properties getDefaults(); } public static interface ConnectionTest {
/*     */       boolean testDBConnection(Properties param2Properties); } } private JButton getTestButton() {
/* 116 */     JButton ret = new JButton(this.callback.getLabelResource().getText("test.connection"));
/* 117 */     ret.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 120 */             DBSettingsPanel.this.onTestConnection();
/*     */           }
/*     */         });
/* 123 */     return ret;
/*     */   }
/*     */   
/*     */   private void onTestConnection() {
/* 127 */     Util.createAndStartThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 131 */               boolean success = false;
/* 132 */               UIUtil.ProgressObserver observer = UIUtil.showProgressObserver(UIUtil.getWindow(DBSettingsPanel.this), DBSettingsPanel.this.callback.getLabelResource(), DBSettingsPanel.this.callback.getMessageResource(), null);
/*     */               try {
/* 134 */                 observer.setProgress("testing.db.connection");
/* 135 */                 Properties dbSettings = DBSettingsPanel.this.getProperties();
/* 136 */                 DBSettingsPanel.Callback.ConnectionTest ctest = (DBSettingsPanel.Callback.ConnectionTest)DBSettingsPanel.this.callback;
/* 137 */                 success = ctest.testDBConnection(dbSettings);
/*     */               } finally {
/* 139 */                 observer.close();
/*     */               } 
/* 141 */               if (success) {
/* 142 */                 UIUtil.showSuccessPopup(DBSettingsPanel.this, DBSettingsPanel.this.callback.getMessageResource().getText("connection.test.successful"));
/*     */               } else {
/* 144 */                 UIUtil.showErrorPopup(DBSettingsPanel.this, DBSettingsPanel.this.callback.getMessageResource().getText("connection.test.failed"));
/*     */               } 
/* 146 */             } catch (Exception e) {
/* 147 */               DBSettingsPanel.log.error("unable to test db connection - exception : " + e, e);
/* 148 */               UIUtil.showErrorPopup(DBSettingsPanel.this, DBSettingsPanel.this.callback.getMessage(e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   public static interface Defaults {
/*     */     Properties getDefaults(); }
/*     */   public static interface ConnectionTest {
/*     */     boolean testDBConnection(Properties param1Properties); }
/*     */   public Properties getProperties() {
/* 158 */     final Properties[] result = new Properties[1];
/* 159 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 162 */             result[0] = new Properties();
/* 163 */             for (Iterator iter = DBSettingsPanel.this.keyToInput.keySet().iterator(); iter.hasNext(); ) {
/* 164 */               Object key = iter.next();
/* 165 */               DBSettingsPanel.this.setProperty(result[0], key, ((JTextField)DBSettingsPanel.this.keyToInput.get(key)).getText());
/*     */             } 
/*     */           }
/*     */         }true);
/*     */     
/* 170 */     return result[0];
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\DBSettingsPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */