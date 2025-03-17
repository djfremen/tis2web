/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.TypeDecorator;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.util.FileManager;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.io.File;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SettingsCachePanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  39 */   protected JPanel softCachePanel = null;
/*  40 */   protected JPanel softCacheWestPanel = null;
/*  41 */   protected JTextField txtCachePath = null;
/*  42 */   protected JTextField txtFileName = null;
/*  43 */   protected JTextField txtCacheSize = null;
/*  44 */   protected ClientSettings clientSettings = null;
/*  45 */   protected ClientAppContext appContext = null;
/*  46 */   protected TypeDecorator typeDecorator = null;
/*  47 */   protected static final Logger log = Logger.getLogger(SettingsCachePanel.class);
/*  48 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*  49 */   protected static Locale locale = null;
/*     */   
/*     */   public SettingsCachePanel(ClientSettings clientSettings) {
/*  52 */     this.clientSettings = clientSettings;
/*  53 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  57 */     setLayout(new BorderLayout());
/*  58 */     add(getSoftCachePanel(), "Center");
/*     */   }
/*     */   
/*     */   private JPanel getSoftCachePanel() {
/*  62 */     if (this.softCachePanel == null) {
/*     */       try {
/*  64 */         this.softCachePanel = new JPanel();
/*  65 */         this.softCachePanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "settingsDialog.softTab.title"), 0, 0, this.softCachePanel.getFont().deriveFont(1), Color.gray));
/*  66 */         this.softCachePanel.setLayout(new BorderLayout());
/*  67 */         this.softCachePanel.add(getSoftCacheWestPanel(), "North");
/*  68 */         this.softCachePanel.add(new JPanel());
/*     */       }
/*  70 */       catch (Throwable e) {
/*  71 */         log.error("getSoftCachePanel() methode, exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return this.softCachePanel;
/*     */   }
/*     */   
/*     */   private JPanel getSoftCacheWestPanel() {
/*  79 */     if (this.softCacheWestPanel == null) {
/*     */       try {
/*  81 */         this.softCacheWestPanel = new JPanel();
/*  82 */         this.softCacheWestPanel.setLayout(new GridBagLayout());
/*  83 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/*  85 */         c.insets = new Insets(5, 5, 5, 5);
/*  86 */         c.gridx = 0;
/*  87 */         c.gridy = 0;
/*  88 */         c.anchor = 18;
/*  89 */         JLabel lblCache = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.cacheSize"));
/*  90 */         lblCache.setFont(lblCache.getFont().deriveFont(1));
/*  91 */         this.softCacheWestPanel.add(lblCache, c);
/*     */         
/*  93 */         c.gridx = 2;
/*  94 */         c.gridy = 0;
/*  95 */         c.gridwidth = 1;
/*  96 */         c.fill = 2;
/*  97 */         this.softCacheWestPanel.add(getTextCacheSize(), c);
/*     */         
/*  99 */         c.gridx = 3;
/* 100 */         c.gridy = 0;
/* 101 */         c.weightx = 0.0D;
/* 102 */         c.anchor = 18;
/* 103 */         JLabel lblUnit = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.mb"));
/* 104 */         lblUnit.setFont(lblUnit.getFont().deriveFont(1));
/* 105 */         this.softCacheWestPanel.add(lblUnit, c);
/*     */         
/* 107 */         c.gridx = 0;
/* 108 */         c.gridy = 2;
/* 109 */         c.gridwidth = 1;
/* 110 */         c.anchor = 18;
/* 111 */         JLabel lblLocation = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.location"));
/* 112 */         lblLocation.setFont(lblLocation.getFont().deriveFont(1));
/* 113 */         this.softCacheWestPanel.add(lblLocation, c);
/*     */         
/* 115 */         c.gridx = 1;
/* 116 */         c.gridy = 2;
/* 117 */         c.weightx = 1.0D;
/* 118 */         c.gridwidth = 4;
/* 119 */         c.fill = 2;
/* 120 */         c.anchor = 18;
/* 121 */         this.softCacheWestPanel.add(getTextCachePath(), c);
/*     */         
/* 123 */         c.gridx = 5;
/* 124 */         c.gridy = 2;
/* 125 */         c.weightx = 0.0D;
/* 126 */         c.insets = new Insets(5, 5, 5, 40);
/* 127 */         JButton btnLocation = new JButton("...");
/* 128 */         btnLocation.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 130 */                 SettingsCachePanel.this.cachePathAction();
/*     */               }
/*     */             });
/* 133 */         this.softCacheWestPanel.add(btnLocation, c);
/*     */         
/* 135 */         c.gridx = 0;
/* 136 */         c.gridy = 3;
/* 137 */         c.weightx = 0.0D;
/* 138 */         c.gridwidth = 1;
/* 139 */         JLabel lblClear = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.clearcache"));
/* 140 */         lblClear.setFont(lblClear.getFont().deriveFont(1));
/* 141 */         this.softCacheWestPanel.add(lblClear, c);
/*     */         
/* 143 */         c.gridx = 2;
/* 144 */         c.gridy = 3;
/* 145 */         c.weightx = 0.0D;
/* 146 */         c.fill = 0;
/* 147 */         JButton btnClear = new JButton(resourceProvider.getLabel(locale, "settingsDialog.clear"));
/* 148 */         btnClear.setFont(btnClear.getFont().deriveFont(1));
/* 149 */         btnClear.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 151 */                 SettingsCachePanel.this.clearAction();
/*     */               }
/*     */             });
/* 154 */         this.softCacheWestPanel.add(btnClear, c);
/*     */       }
/* 156 */       catch (Throwable e) {
/*     */         
/* 158 */         log.error("unable to load resources, exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 161 */     return this.softCacheWestPanel;
/*     */   }
/*     */   
/*     */   private JTextField getTextCacheSize() {
/* 165 */     if (this.txtCacheSize == null) {
/*     */       try {
/* 167 */         this.txtCacheSize = new JTextField();
/* 168 */         String cachSizeValue = this.clientSettings.getProperty("cacheSize");
/* 169 */         if (cachSizeValue != null) {
/* 170 */           this.txtCacheSize.setText(cachSizeValue);
/*     */         }
/* 172 */         this.txtCacheSize.addFocusListener(new FocusAdapter() {
/*     */               String initialValue;
/*     */               
/*     */               public void focusGained(FocusEvent e) {
/* 176 */                 this.initialValue = SettingsCachePanel.this.txtCacheSize.getText();
/*     */               }
/*     */               
/*     */               public void focusLost(FocusEvent e) {
/* 180 */                 if (!this.initialValue.equals(SettingsCachePanel.this.txtCacheSize.getText())) {
/* 181 */                   SettingsCachePanel.this.clientSettings.setProperty("cacheSize", SettingsCachePanel.this.txtCacheSize.getText());
/*     */                 }
/*     */               }
/*     */             });
/* 185 */       } catch (Exception e) {
/* 186 */         log.error("getTextCacheSize() methode, exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 189 */     return this.txtCacheSize;
/*     */   }
/*     */   
/*     */   public boolean isCacheValueNumber() {
/* 193 */     if (!SwingUtils.isNumber(this.txtCacheSize.getText())) {
/* 194 */       JOptionPane.showMessageDialog(this.softCachePanel, resourceProvider.getMessage(null, "sps.exception.invalid-cachesize-input"), "Exception", 0);
/* 195 */       this.txtCacheSize.requestFocus();
/* 196 */       return false;
/*     */     } 
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   private JTextField getTextCachePath() {
/* 202 */     if (this.txtCachePath == null) {
/*     */       try {
/* 204 */         this.txtCachePath = new JTextField();
/* 205 */         this.txtCachePath.setEnabled(false);
/* 206 */         String cachPathValue = this.clientSettings.getSPSCache();
/* 207 */         if (cachPathValue != null) {
/* 208 */           this.txtCachePath.setText(cachPathValue);
/*     */         }
/* 210 */       } catch (Exception e) {
/* 211 */         log.error("getTextCachePath() methode, exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 214 */     return this.txtCachePath;
/*     */   }
/*     */   
/*     */   public void cachePathAction() {
/* 218 */     String initialValue = getTextCachePath().getText();
/* 219 */     FileManager.openFileChooserActionPerformed(determineDefaultFile(getTextCachePath()), getTextCachePath(), this.softCachePanel, null);
/* 220 */     String actualValue = getTextCachePath().getText();
/* 221 */     if (!initialValue.equals(actualValue)) {
/* 222 */       boolean isCorrectly = this.clientSettings.setSPSCache(getTextCachePath().getText());
/* 223 */       if (!isCorrectly) {
/* 224 */         JOptionPane.showMessageDialog(this.softCachePanel, "Invalid  Cache Path ", "Exception", 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private File determineDefaultFile(JTextField jTextField) {
/* 230 */     File logFile = null;
/*     */     try {
/* 232 */       logFile = new File(jTextField.getText());
/* 233 */     } catch (Exception e) {}
/*     */     
/* 235 */     return logFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAction() {
/*     */     try {
/* 241 */       String[] choices = { resourceProvider.getLabel(null, "ok"), resourceProvider.getLabel(null, "cancel") };
/* 242 */       int response = JOptionPane.showOptionDialog(this, resourceProvider.getMessage(null, "sps.clear-cache"), null, 0, -1, null, (Object[])choices, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       if (response == 0) {
/* 253 */         this.clientSettings.clearSPSCache();
/* 254 */         log.debug("clear Action");
/*     */       } 
/* 256 */     } catch (Exception except) {
/* 257 */       log.error("clearAction, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SettingsCachePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */