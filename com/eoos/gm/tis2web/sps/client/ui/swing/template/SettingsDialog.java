/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.WarrantyClaimCodeStore;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SettingsDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  43 */   private JPanel jContentPane = null;
/*     */   
/*  45 */   private JTabbedPane jTabbedPane = null;
/*     */   
/*  47 */   private JPanel titlePanel = null;
/*     */   
/*  49 */   private JPanel buttonsPanel = null;
/*     */   
/*  51 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  53 */   protected static Locale locale = null;
/*     */   
/*  55 */   protected ClientSettings clientSettings = null;
/*     */   
/*  57 */   protected SettingsCachePanel settCachePanel = null;
/*     */   
/*  59 */   private static final Logger log = Logger.getLogger(SettingsDialog.class);
/*     */   
/*     */   protected SPSFrame frame;
/*     */   
/*  63 */   static int origX = 0;
/*     */   
/*  65 */   static int origY = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SettingsDialog(SPSFrame frame) {
/*  71 */     super((Frame)frame, frame.getTitle(), true);
/*  72 */     this.frame = frame;
/*  73 */     this.clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*  74 */     initialize();
/*  75 */     origX = (getSize()).width;
/*  76 */     origY = (getSize()).height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  86 */     setSize(SwingUtils.getDialogSettings_Width(), SwingUtils.getDialogSettings_Height());
/*  87 */     setLocation(UIUtil.getCenterLocation(this, (Window)this.frame));
/*  88 */     setContentPane(getJContentPane());
/*  89 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent event) {
/*  91 */             SettingsDialog.this.componentResizedAction();
/*     */           }
/*     */         });
/*  94 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent evt) {
/*  96 */             SettingsDialog.this.closeDialog();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/* 107 */     if (this.jContentPane == null) {
/* 108 */       this.jContentPane = new JPanel();
/* 109 */       this.jContentPane.setLayout(new BorderLayout());
/* 110 */       this.jContentPane.add(getTitlePanel(), "North");
/* 111 */       this.jContentPane.add(getJTabbedPane(), "Center");
/* 112 */       this.jContentPane.add(getButtonsPanel(), "South");
/*     */     } 
/* 114 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTabbedPane getJTabbedPane() {
/* 123 */     if (this.jTabbedPane == null) {
/*     */       try {
/* 125 */         this.jTabbedPane = new JTabbedPane();
/* 126 */         this.settCachePanel = new SettingsCachePanel(this.clientSettings);
/* 127 */         this.jTabbedPane.addTab(resourceProvider.getLabel(locale, "settingsDialog.commonTab.title"), (Icon)null, new SettingsCommonPanel(this.frame, this.clientSettings), (String)null);
/*     */         
/* 129 */         this.jTabbedPane.addTab(resourceProvider.getLabel(locale, "settingsDialog.diagnosticTab.title"), (Icon)null, new SettingsDiagnosticPanel(this.frame, this.clientSettings), (String)null);
/* 130 */         this.jTabbedPane.addTab(resourceProvider.getLabel(locale, "settingsDialog.softTab.title"), (Icon)null, this.settCachePanel, (String)null);
/* 131 */         this.jTabbedPane.addTab(resourceProvider.getLabel(locale, "settingsDialog.logTab.title"), (Icon)null, new SettingsLoggingPanel(this.clientSettings), (String)null);
/* 132 */         if (WarrantyClaimCodeStore.isWarrantyCodeListSupported()) {
/* 133 */           this.jTabbedPane.addTab(resourceProvider.getLabel(locale, "settingsDialog.wccTab.title"), (Icon)null, new WarrantyCodeListPanel(this.frame, this.clientSettings), (String)null);
/*     */         }
/* 135 */         this.jTabbedPane.setFont(this.jTabbedPane.getFont().deriveFont(1));
/* 136 */         this.jTabbedPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
/*     */       }
/* 138 */       catch (Throwable e) {
/* 139 */         log.error("unable to load labels, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 142 */     return this.jTabbedPane;
/*     */   }
/*     */   
/*     */   private JPanel getButtonsPanel() {
/* 146 */     if (this.buttonsPanel == null) {
/*     */       try {
/* 148 */         this.buttonsPanel = new JPanel();
/* 149 */         this.buttonsPanel.setLayout(new GridBagLayout());
/* 150 */         GridBagConstraints constr = new GridBagConstraints();
/* 151 */         constr.gridx = 2;
/* 152 */         constr.gridwidth = 1;
/* 153 */         constr.insets = new Insets(10, 20, 10, 20);
/* 154 */         JButton cancelButton = new JButton(resourceProvider.getLabel(locale, "cancel"));
/* 155 */         cancelButton.setFont(cancelButton.getFont().deriveFont(1));
/* 156 */         this.buttonsPanel.add(cancelButton, constr);
/* 157 */         cancelButton.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 159 */                 SettingsDialog.this.closeDialog();
/*     */               }
/*     */             });
/*     */ 
/*     */         
/* 164 */         JButton okButton = new JButton(resourceProvider.getLabel(locale, "ok"));
/* 165 */         okButton.setFont(okButton.getFont().deriveFont(1));
/* 166 */         constr.gridx = 6;
/* 167 */         constr.gridwidth = 1;
/* 168 */         constr.insets = new Insets(10, 20, 10, 20);
/* 169 */         this.buttonsPanel.add(okButton, constr);
/* 170 */         okButton.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 172 */                 SettingsDialog.this.saveAndExit();
/*     */               }
/*     */             });
/*     */       
/*     */       }
/* 177 */       catch (Throwable e) {
/* 178 */         log.error("getButtonsPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 181 */     return this.buttonsPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/* 190 */     if (this.titlePanel == null) {
/*     */       try {
/* 192 */         this.titlePanel = new JPanel();
/* 193 */         this.titlePanel.setLayout(new GridBagLayout());
/* 194 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/* 195 */         JLabel titleLabel = new JLabel();
/* 196 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 197 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 198 */         titleLabel.setText(resourceProvider.getLabel(locale, "settingsDialog.title"));
/* 199 */         titelPanelConstraint.gridx = 3;
/* 200 */         titelPanelConstraint.gridy = 0;
/* 201 */         this.titlePanel.add(titleLabel, titelPanelConstraint);
/* 202 */       } catch (Throwable e) {
/* 203 */         log.error("getTitlePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 206 */     return this.titlePanel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeDialog() {
/* 211 */     setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveAndExit() {
/* 218 */     if (!this.settCachePanel.isCacheValueNumber()) {
/* 219 */       getJTabbedPane().setSelectedComponent(this.settCachePanel);
/*     */       return;
/*     */     } 
/* 222 */     this.clientSettings.saveConfiguration();
/* 223 */     if (SettingsCommonPanel.isChangedFontSize()) {
/* 224 */       FontUtils.initializeFont((Window)this.frame);
/*     */       
/* 226 */       SPSFrame.getInstance().onNewAction();
/*     */       
/* 228 */       SPSFrame.getInstance().updateFontOnButtons();
/*     */     } 
/*     */     
/* 231 */     closeDialog();
/*     */   }
/*     */   
/*     */   private void componentResizedAction() {
/* 235 */     setSize((getWidth() < origX - 100) ? (origX - 50) : getWidth(), (getHeight() < origY - 100) ? (origY - 50) : getHeight());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SettingsDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */