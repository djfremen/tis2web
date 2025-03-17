/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.TypeDecorator;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.mail.DefaultCalllback;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.mail.DefaultSubmitAction;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.mail.LogFilesMailDialog;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.util.FileManager;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
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
/*     */ public class SettingsLoggingPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   private JTextField txtFileName = null;
/*  41 */   private JCheckBox chkLogging = null;
/*  42 */   private JCheckBox chkDebug = null;
/*  43 */   private JCheckBox chkDebugExtended = null;
/*  44 */   private JButton buttonSendLogs = null;
/*  45 */   private JPanel loggingsPanel = null;
/*  46 */   private JPanel loggingsWestPanel = null;
/*  47 */   protected ClientSettings clientSettings = null;
/*  48 */   protected static Locale locale = null;
/*  49 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*  50 */   private static final Logger log = Logger.getLogger(SettingsLoggingPanel.class);
/*  51 */   protected TypeDecorator typeDecorator = null;
/*     */   
/*     */   public SettingsLoggingPanel(ClientSettings clientSettings) {
/*  54 */     this.clientSettings = clientSettings;
/*  55 */     this.typeDecorator = new TypeDecorator(this.clientSettings);
/*  56 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  60 */     setLayout(new BorderLayout());
/*  61 */     add(getLoggingsPanel(), "Center");
/*     */   }
/*     */   
/*     */   private JPanel getLoggingsPanel() {
/*  65 */     if (this.loggingsPanel == null) {
/*     */       try {
/*  67 */         this.loggingsPanel = new JPanel();
/*  68 */         this.loggingsPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "settingsDialog.logTab.title"), 0, 0, this.loggingsPanel.getFont().deriveFont(1), Color.gray));
/*  69 */         this.loggingsPanel.setLayout(new BorderLayout());
/*  70 */         this.loggingsPanel.add(getLoggingsWestPanel(), "North");
/*  71 */         this.loggingsPanel.add(new JPanel(), "South");
/*     */       }
/*  73 */       catch (Throwable e) {
/*  74 */         log.error("unable to load resource, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*  77 */     return this.loggingsPanel;
/*     */   }
/*     */   
/*     */   private JPanel getLoggingsWestPanel() {
/*  81 */     if (this.loggingsWestPanel == null) {
/*     */       try {
/*  83 */         this.loggingsWestPanel = new JPanel();
/*  84 */         this.loggingsWestPanel.setLayout(new GridBagLayout());
/*  85 */         GridBagConstraints c = new GridBagConstraints();
/*  86 */         c.insets = new Insets(5, 5, 5, 5);
/*     */         
/*  88 */         c.gridx = 0;
/*  89 */         c.gridy = 0;
/*  90 */         c.anchor = 18;
/*  91 */         this.loggingsWestPanel.add(getCheckLogLoggings(), c);
/*     */         
/*  93 */         c.gridx = 1;
/*  94 */         c.gridy = 0;
/*  95 */         c.gridwidth = 3;
/*  96 */         c.weightx = 0.0D;
/*  97 */         c.weighty = 0.0D;
/*  98 */         c.fill = 2;
/*  99 */         c.anchor = 18;
/* 100 */         JLabel lblLogging = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.peform"));
/* 101 */         lblLogging.setFont(lblLogging.getFont().deriveFont(1));
/* 102 */         this.loggingsWestPanel.add(lblLogging, c);
/*     */         
/* 104 */         c.gridx = 0;
/* 105 */         c.gridy = 1;
/* 106 */         c.gridwidth = 1;
/* 107 */         c.fill = 2;
/* 108 */         c.anchor = 18;
/* 109 */         this.loggingsWestPanel.add(getCheckDebugLoggings(), c);
/*     */         
/* 111 */         c.gridx = 1;
/* 112 */         c.gridy = 1;
/* 113 */         c.gridwidth = 3;
/* 114 */         c.weightx = 0.0D;
/* 115 */         c.weighty = 0.0D;
/* 116 */         c.fill = 2;
/* 117 */         c.anchor = 18;
/* 118 */         JLabel lblDebug = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.debug"));
/* 119 */         lblDebug.setFont(lblDebug.getFont().deriveFont(1));
/* 120 */         this.loggingsWestPanel.add(lblDebug, c);
/*     */         
/* 122 */         c.gridx = 0;
/* 123 */         c.gridy = 2;
/* 124 */         c.gridwidth = 1;
/* 125 */         c.fill = 2;
/* 126 */         c.anchor = 18;
/* 127 */         this.loggingsWestPanel.add(getDebugExtendedChkBox(), c);
/*     */         
/* 129 */         c.gridx = 1;
/* 130 */         c.gridy = 2;
/* 131 */         c.gridwidth = 3;
/* 132 */         c.weightx = 0.0D;
/* 133 */         c.weighty = 0.0D;
/* 134 */         c.fill = 2;
/* 135 */         c.anchor = 18;
/* 136 */         JLabel lblExtended = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.debug.extended"));
/* 137 */         lblExtended.setFont(lblDebug.getFont().deriveFont(1));
/* 138 */         this.loggingsWestPanel.add(lblExtended, c);
/*     */         
/* 140 */         c.gridx = 0;
/* 141 */         c.gridy = 3;
/* 142 */         c.gridwidth = 2;
/* 143 */         c.fill = 2;
/* 144 */         c.anchor = 18;
/* 145 */         JLabel lblFileName = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.file"));
/* 146 */         lblFileName.setFont(lblFileName.getFont().deriveFont(1));
/* 147 */         this.loggingsWestPanel.add(lblFileName, c);
/*     */         
/* 149 */         c.gridx = 2;
/* 150 */         c.gridy = 3;
/* 151 */         c.weightx = 1.0D;
/* 152 */         c.gridwidth = 4;
/* 153 */         c.fill = 2;
/* 154 */         c.anchor = 18;
/* 155 */         this.loggingsWestPanel.add(getTextFileName(), c);
/*     */         
/* 157 */         c.gridx = 6;
/* 158 */         c.gridy = 3;
/* 159 */         c.weightx = 0.0D;
/* 160 */         c.weighty = 0.0D;
/* 161 */         c.fill = 0;
/* 162 */         c.insets = new Insets(5, 5, 5, 80);
/* 163 */         c.anchor = 18;
/* 164 */         JButton btnFileName = new JButton("...");
/* 165 */         btnFileName.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 167 */                 SettingsLoggingPanel.this.loggPathAction();
/*     */               }
/*     */             });
/* 170 */         this.loggingsWestPanel.add(btnFileName, c);
/*     */         
/* 172 */         c.gridx = 0;
/* 173 */         c.gridwidth = 2;
/* 174 */         c.gridy = 4;
/* 175 */         this.buttonSendLogs = new JButton(resourceProvider.getLabel(locale, "send.logs"));
/* 176 */         this.buttonSendLogs.addActionListener(new ActionListener()
/*     */             {
/*     */               public void actionPerformed(ActionEvent e) {
/* 179 */                 LogFilesMailDialog dialog = new LogFilesMailDialog((Frame)SPSFrame.getInstance(), (LogFilesMailDialog.Callback)new DefaultCalllback(), (LogFilesMailDialog.Action)new DefaultSubmitAction());
/* 180 */                 dialog.setVisible(true);
/*     */               }
/*     */             });
/*     */ 
/*     */         
/* 185 */         this.loggingsWestPanel.add(this.buttonSendLogs, c);
/*     */       }
/* 187 */       catch (Exception e) {
/* 188 */         log.error("unable to load resources in getLoggingsWestPanel() , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 191 */     return this.loggingsWestPanel;
/*     */   }
/*     */   
/*     */   private JCheckBox getCheckLogLoggings() {
/* 195 */     if (this.chkLogging == null) {
/*     */       try {
/* 197 */         this.chkLogging = new JCheckBox();
/* 198 */         this.chkLogging.setSelected(this.typeDecorator.getBoolean("log.logging").booleanValue());
/* 199 */         this.chkLogging.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 201 */                 if (SettingsLoggingPanel.this.chkLogging.isSelected()) {
/* 202 */                   SettingsLoggingPanel.this.clientSettings.setProperty("log.logging", "TRUE");
/*     */                 } else {
/* 204 */                   SettingsLoggingPanel.this.clientSettings.setProperty("log.logging", "FALSE");
/*     */                 } 
/*     */               }
/*     */             });
/* 208 */       } catch (Exception e) {
/* 209 */         log.error("getCheckLogLoggings() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 213 */     return this.chkLogging;
/*     */   }
/*     */   
/*     */   private JCheckBox getCheckDebugLoggings() {
/* 217 */     if (this.chkDebug == null) {
/*     */       try {
/* 219 */         this.chkDebug = new JCheckBox();
/* 220 */         this.chkDebug.setSelected(this.typeDecorator.getBoolean("log.detailed").booleanValue());
/* 221 */         this.chkDebug.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 223 */                 if (SettingsLoggingPanel.this.chkDebug.isSelected()) {
/* 224 */                   SettingsLoggingPanel.this.clientSettings.setProperty("log.detailed", "TRUE");
/*     */                 } else {
/* 226 */                   SettingsLoggingPanel.this.clientSettings.setProperty("log.detailed", "FALSE");
/*     */                 } 
/*     */               }
/*     */             });
/* 230 */       } catch (Exception e) {
/* 231 */         log.error("getCheckDebugLoggings() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 235 */     return this.chkDebug;
/*     */   }
/*     */   
/*     */   private JCheckBox getDebugExtendedChkBox() {
/* 239 */     if (this.chkDebugExtended == null) {
/*     */       try {
/* 241 */         this.chkDebugExtended = new JCheckBox();
/* 242 */         this.chkDebugExtended.setSelected(this.typeDecorator.getBoolean("debug.extended").booleanValue());
/* 243 */         this.chkDebugExtended.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 245 */                 if (SettingsLoggingPanel.this.chkDebugExtended.isSelected()) {
/* 246 */                   SettingsLoggingPanel.this.clientSettings.setProperty("debug.extended", "TRUE");
/*     */                 } else {
/* 248 */                   SettingsLoggingPanel.this.clientSettings.setProperty("debug.extended", "FALSE");
/*     */                 } 
/*     */               }
/*     */             });
/* 252 */       } catch (Exception e) {
/* 253 */         log.error("getDebugExtendedChkBox() method, -exception: " + e.getMessage(), e);
/*     */       } 
/*     */     }
/*     */     
/* 257 */     return this.chkDebugExtended;
/*     */   }
/*     */   
/*     */   private JTextField getTextFileName() {
/* 261 */     if (this.txtFileName == null) {
/*     */       try {
/* 263 */         this.txtFileName = new JTextField();
/* 264 */         this.txtFileName.setEnabled(false);
/* 265 */         String fileNameValue = this.clientSettings.getLogPath();
/* 266 */         if (fileNameValue != null) {
/* 267 */           this.txtFileName.setText(fileNameValue);
/*     */         }
/* 269 */       } catch (Exception e) {
/* 270 */         log.error("getTextFileName() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 273 */     return this.txtFileName;
/*     */   }
/*     */   
/*     */   private void loggPathAction() {
/* 277 */     String initialValue = getTextFileName().getText();
/* 278 */     FileManager.openFileChooserActionPerformed(determineDefaultFile(getTextFileName()), getTextFileName(), this.loggingsPanel, null);
/* 279 */     String actualValue = getTextFileName().getText();
/* 280 */     if (!initialValue.equals(actualValue)) {
/* 281 */       this.clientSettings.setProperty("log.fileName", getTextFileName().getText());
/*     */     }
/*     */   }
/*     */   
/*     */   private File determineDefaultFile(JTextField jTextField) {
/* 286 */     File logFile = null;
/*     */     try {
/* 288 */       logFile = new File(jTextField.getText());
/* 289 */     } catch (Exception e) {}
/*     */     
/* 291 */     return logFile;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SettingsLoggingPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */