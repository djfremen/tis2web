/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.TypeDecorator;
/*     */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.SupportedProtocols;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.util.FileManager;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SettingsDiagnosticPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  67 */   protected JPanel j2534VersionPanel = null;
/*  68 */   protected JPanel diaDevicePanel = null;
/*  69 */   protected JPanel northWestPanel = null;
/*  70 */   protected JPanel northEastPanel = null;
/*  71 */   protected JPanel southWestPanel = null;
/*  72 */   protected JPanel southEastPanel = null;
/*     */   
/*  74 */   protected JPanel inputOptionPanel = null;
/*  75 */   protected JPanel selectOptionPanel = null;
/*  76 */   protected JPanel booleanOptionPanel = null;
/*     */   
/*  78 */   protected JScrollPane inputOptionScrollPane = null;
/*  79 */   protected JScrollPane selectOptionScrollPane = null;
/*  80 */   protected JScrollPane booleanOptionScrollPane = null;
/*     */   
/*  82 */   protected JList txtArea = null;
/*  83 */   protected JButton btnTestConnection = null;
/*  84 */   protected JButton btnConfiguration = new JButton(resourceProvider.getLabel(locale, "configuration"));
/*  85 */   protected JComboBox cboDevice = null;
/*  86 */   protected GridBagConstraints constrDiaDevice = null;
/*  87 */   protected ClientAppContext appContext = null;
/*  88 */   protected ClientSettings clientSettings = null;
/*  89 */   protected TypeDecorator typeDecorator = null;
/*     */   
/*  91 */   protected static int selectGridy = 0;
/*  92 */   protected static int booleanGridy = 0;
/*  93 */   protected static int inputGridy = 0;
/*  94 */   protected static Locale locale = null;
/*     */   
/*     */   protected SPSFrame frame;
/*  97 */   protected HashMap compKey = new HashMap<Object, Object>();
/*  98 */   protected HashMap btnComp = new HashMap<Object, Object>();
/*  99 */   protected List tools = null;
/* 100 */   protected Tool tool = null;
/*     */   
/* 102 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/* 104 */   private static final Logger log = Logger.getLogger(SettingsDiagnosticPanel.class);
/*     */   
/*     */   public SettingsDiagnosticPanel(SPSFrame frame, ClientSettings clientSettings) {
/* 107 */     this.frame = frame;
/* 108 */     this.tools = frame.getTools();
/* 109 */     this.appContext = ClientAppContextProvider.getClientAppContext();
/* 110 */     this.clientSettings = clientSettings;
/* 111 */     this.typeDecorator = new TypeDecorator(this.clientSettings);
/* 112 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/* 116 */     setLayout(new BorderLayout());
/* 117 */     add(getJ2534VersionPanel(), "North");
/* 118 */     add(getDiaDevicePanel(), "Center");
/*     */   }
/*     */   
/*     */   private JPanel getJ2534VersionPanel() {
/* 122 */     if (this.j2534VersionPanel == null) {
/*     */       try {
/* 124 */         this.j2534VersionPanel = new JPanel();
/* 125 */         this.j2534VersionPanel.setLayout(new BoxLayout(this.j2534VersionPanel, 1));
/* 126 */         boolean value = Boolean.valueOf(this.clientSettings.getProperty("allow.j2534.oldversion")).booleanValue();
/* 127 */         JCheckBox oldVersion = new JCheckBox(resourceProvider.getLabel(null, "settingsDialog.j2534OldVersion"), value);
/* 128 */         this.j2534VersionPanel.add(oldVersion);
/* 129 */         oldVersion.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 131 */                 JCheckBox chkFlag = (JCheckBox)e.getSource();
/* 132 */                 if (chkFlag.isSelected()) {
/* 133 */                   SettingsDiagnosticPanel.this.clientSettings.setProperty("allow.j2534.oldversion", "true");
/*     */                 } else {
/* 135 */                   SettingsDiagnosticPanel.this.clientSettings.setProperty("allow.j2534.oldversion", "false");
/*     */                 } 
/*     */               }
/*     */             });
/* 139 */       } catch (Throwable e) {
/* 140 */         log.error("unable to build j2534 version panel , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 143 */     return this.j2534VersionPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getDiaDevicePanel() {
/* 152 */     if (this.diaDevicePanel == null) {
/*     */       try {
/* 154 */         this.diaDevicePanel = new JPanel();
/* 155 */         this.diaDevicePanel.setLayout(new GridBagLayout());
/* 156 */         this.constrDiaDevice = new GridBagConstraints();
/* 157 */         this.constrDiaDevice.insets = new Insets(25, 5, 5, 5);
/*     */         
/* 159 */         this.constrDiaDevice.gridx = 0;
/* 160 */         this.constrDiaDevice.gridy = 0;
/* 161 */         this.constrDiaDevice.weightx = 0.0D;
/* 162 */         this.constrDiaDevice.weighty = 0.0D;
/* 163 */         this.constrDiaDevice.gridwidth = 2;
/* 164 */         this.constrDiaDevice.gridheight = 1;
/* 165 */         this.constrDiaDevice.anchor = 18;
/* 166 */         this.constrDiaDevice.fill = 1;
/* 167 */         this.diaDevicePanel.add(getNorthWestPanel(), this.constrDiaDevice);
/*     */         
/* 169 */         this.constrDiaDevice.gridx = 2;
/* 170 */         this.constrDiaDevice.gridwidth = 2;
/* 171 */         this.diaDevicePanel.add(getNorthEastPanel(), this.constrDiaDevice);
/*     */         
/* 173 */         this.constrDiaDevice.insets = new Insets(0, 5, 5, 5);
/* 174 */         this.constrDiaDevice.gridy = 1;
/* 175 */         this.constrDiaDevice.gridx = 0;
/* 176 */         this.constrDiaDevice.weightx = 1.0D;
/* 177 */         this.constrDiaDevice.weighty = 1.0D;
/* 178 */         this.constrDiaDevice.gridheight = 4;
/* 179 */         this.constrDiaDevice.gridwidth = 2;
/* 180 */         this.constrDiaDevice.fill = 1;
/* 181 */         this.constrDiaDevice.anchor = 17;
/* 182 */         this.diaDevicePanel.add(getSouthWestPanel(), this.constrDiaDevice);
/*     */         
/* 184 */         this.constrDiaDevice.gridx = 2;
/* 185 */         this.constrDiaDevice.gridwidth = 2;
/* 186 */         this.diaDevicePanel.add(getSouthEastPanel(), this.constrDiaDevice);
/*     */       }
/* 188 */       catch (Throwable e) {
/* 189 */         log.error("getDiaDevicePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 192 */     return this.diaDevicePanel;
/*     */   }
/*     */   
/*     */   private JPanel getSouthWestPanel() {
/* 196 */     if (this.southWestPanel == null) {
/*     */       try {
/* 198 */         this.southWestPanel = new JPanel();
/* 199 */         this.southWestPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null, Color.gray));
/* 200 */         this.southWestPanel.setLayout(new BoxLayout(this.southWestPanel, 1));
/* 201 */       } catch (Throwable e) {
/* 202 */         log.error("getSouthWestPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 205 */     return this.southWestPanel;
/*     */   }
/*     */   
/*     */   private JPanel getNorthEastPanel() {
/* 209 */     if (this.northEastPanel == null) {
/*     */       try {
/* 211 */         this.northEastPanel = new JPanel();
/* 212 */         this.northEastPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12), Color.red));
/* 213 */         this.northEastPanel.setLayout(new GridBagLayout());
/* 214 */         GridBagConstraints c = new GridBagConstraints();
/* 215 */         c.insets = new Insets(5, 5, 5, 5);
/* 216 */         c.gridx = 0;
/* 217 */         c.gridy = 0;
/* 218 */         c.gridheight = 1;
/* 219 */         c.gridwidth = 1;
/* 220 */         c.weightx = 1.0D;
/* 221 */         c.weighty = 1.0D;
/* 222 */         c.fill = 2;
/* 223 */         c.anchor = 18;
/* 224 */         this.btnTestConnection = new JButton(resourceProvider.getLabel(locale, "settingsDialog.test"));
/* 225 */         this.btnTestConnection.setFont(this.btnTestConnection.getFont().deriveFont(1));
/* 226 */         this.btnTestConnection.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 228 */                 SettingsDiagnosticPanel.this.testConnectActionPerformed(e);
/*     */               }
/*     */             });
/* 231 */         this.northEastPanel.add(this.btnTestConnection, c);
/*     */         
/* 233 */         c.gridy = 1;
/* 234 */         this.northEastPanel.add(this.btnConfiguration, c);
/* 235 */         this.btnConfiguration.setFont(this.btnConfiguration.getFont().deriveFont(1));
/* 236 */         this.btnConfiguration.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 238 */                 (new Thread() {
/*     */                     public void run() {
/* 240 */                       SettingsDiagnosticPanel.this.onConfigurationAction();
/*     */                     }
/*     */                   }).start();
/*     */               }
/*     */             });
/*     */       }
/* 246 */       catch (Throwable e) {
/* 247 */         log.error("getNorthEastPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 250 */     return this.northEastPanel;
/*     */   }
/*     */   
/*     */   private JPanel getNorthWestPanel() {
/* 254 */     if (this.northWestPanel == null) {
/*     */       try {
/* 256 */         this.northWestPanel = new JPanel();
/* 257 */         this.northWestPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12), Color.gray));
/* 258 */         this.northWestPanel.setLayout(new GridBagLayout());
/* 259 */         GridBagConstraints c = new GridBagConstraints();
/* 260 */         c.insets = new Insets(5, 5, 5, 5);
/* 261 */         c.gridx = 0;
/* 262 */         c.gridy = 0;
/* 263 */         c.gridheight = 1;
/* 264 */         c.gridwidth = 1;
/* 265 */         c.anchor = 18;
/*     */         
/* 267 */         JLabel lblDevice = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.device"));
/* 268 */         lblDevice.setFont(lblDevice.getFont().deriveFont(1));
/* 269 */         this.northWestPanel.add(lblDevice, c);
/*     */         
/* 271 */         c.insets = new Insets(5, 27, 5, 8);
/* 272 */         c.gridx = 2;
/* 273 */         c.gridy = 0;
/* 274 */         c.weightx = 1.0D;
/* 275 */         c.weighty = 1.0D;
/* 276 */         c.gridheight = 1;
/* 277 */         c.gridwidth = 2;
/* 278 */         c.anchor = 18;
/* 279 */         c.fill = 2;
/* 280 */         this.cboDevice = new JComboBox(Transform.createVector(this.tools));
/* 281 */         this.cboDevice.setName("Device");
/* 282 */         this.cboDevice.setSelectedIndex(0);
/*     */         
/* 284 */         deviceActionPerformed();
/* 285 */         this.cboDevice.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 287 */                 SettingsDiagnosticPanel.this.deviceActionPerformed();
/*     */               }
/*     */             });
/* 290 */         this.northWestPanel.add(this.cboDevice, c);
/* 291 */       } catch (Throwable e) {
/* 292 */         log.error("getNorthWestPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 295 */     return this.northWestPanel;
/*     */   }
/*     */   
/*     */   private JPanel getSouthEastPanel() {
/* 299 */     if (this.southEastPanel == null) {
/*     */       try {
/* 301 */         this.southEastPanel = new JPanel();
/* 302 */         this.southEastPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12), Color.red));
/* 303 */         this.southEastPanel.setLayout(new GridBagLayout());
/* 304 */         GridBagConstraints c = new GridBagConstraints();
/* 305 */         c.insets = new Insets(5, 5, 5, 5);
/* 306 */         c.gridx = 0;
/* 307 */         c.gridy = 0;
/* 308 */         c.gridheight = 1;
/* 309 */         c.gridwidth = 1;
/* 310 */         c.anchor = 13;
/*     */         
/* 312 */         JLabel lblDevice = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.status"));
/* 313 */         lblDevice.setFont(lblDevice.getFont().deriveFont(1));
/* 314 */         lblDevice.setPreferredSize(new Dimension(140, 20));
/* 315 */         this.southEastPanel.add(lblDevice, c);
/*     */         
/* 317 */         c.insets = new Insets(5, 5, 5, 5);
/* 318 */         c.gridx = 0;
/* 319 */         c.gridy = 1;
/* 320 */         c.weightx = 1.0D;
/* 321 */         c.weighty = 1.0D;
/* 322 */         c.gridheight = 2;
/* 323 */         c.gridwidth = 2;
/* 324 */         c.anchor = 13;
/* 325 */         c.fill = 1;
/* 326 */         this.txtArea = new JList();
/* 327 */         JScrollPane scroll = new JScrollPane(this.txtArea);
/* 328 */         this.southEastPanel.add(scroll, c);
/* 329 */       } catch (Throwable e) {
/* 330 */         log.error("getSouthEastPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 333 */     return this.southEastPanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputOptionPanel() {
/* 337 */     if (this.inputOptionPanel == null) {
/*     */       try {
/* 339 */         this.inputOptionPanel = new JPanel();
/* 340 */         this.inputOptionPanel.setLayout(new GridBagLayout());
/* 341 */         this.inputOptionPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12), Color.gray));
/* 342 */       } catch (Throwable e) {
/* 343 */         log.error("getInputOptionPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 346 */     return this.inputOptionPanel;
/*     */   }
/*     */   
/*     */   private JPanel getSelectOptionPanel() {
/* 350 */     if (this.selectOptionPanel == null) {
/*     */       try {
/* 352 */         this.selectOptionPanel = new JPanel();
/* 353 */         this.selectOptionPanel.setLayout(new GridBagLayout());
/* 354 */         this.selectOptionPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null));
/* 355 */       } catch (Throwable e) {
/* 356 */         log.error("getSelectOptionPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 359 */     return this.selectOptionPanel;
/*     */   }
/*     */   
/*     */   private JPanel getBooleanOptionPanel() {
/* 363 */     if (this.booleanOptionPanel == null) {
/*     */       try {
/* 365 */         this.booleanOptionPanel = new JPanel();
/* 366 */         this.booleanOptionPanel.setLayout(new GridBagLayout());
/* 367 */         this.booleanOptionPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(null, "settingsDialog.configPanel.title"), 0, 0, this.booleanOptionPanel.getFont().deriveFont(1)));
/* 368 */       } catch (Throwable e) {
/* 369 */         log.error("getBooleanOptionPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 372 */     return this.booleanOptionPanel;
/*     */   }
/*     */   
/*     */   private void cleanPanels() {
/* 376 */     getSouthWestPanel().removeAll();
/* 377 */     getSelectOptionPanel().removeAll();
/* 378 */     getBooleanOptionPanel().removeAll();
/* 379 */     getInputOptionPanel().removeAll();
/*     */   }
/*     */ 
/*     */   
/*     */   private void deviceActionPerformed() {
/* 384 */     cleanPanels();
/*     */     try {
/* 386 */       this.tool = this.appContext.getTool(this.cboDevice.getSelectedItem().toString());
/*     */       
/* 388 */       if (this.tool instanceof ToolOptions) {
/*     */         
/* 390 */         List<ToolOption> options = ((ToolOptions)this.tool).getOptions();
/* 391 */         for (int i = 0; i < options.size(); i++) {
/* 392 */           Vector<String> allValues = new Vector();
/* 393 */           ToolOption option = options.get(i);
/* 394 */           Iterator<OptionValue> it2 = option.getValues().iterator();
/* 395 */           while (it2.hasNext()) {
/* 396 */             OptionValue value = it2.next();
/* 397 */             allValues.add(value.getDenotation(null));
/*     */           } 
/*     */           
/* 400 */           if (option instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.BooleanSelectOption) {
/* 401 */             createCheckBox(option, allValues);
/*     */           }
/* 403 */           else if (option instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.SelectOption) {
/* 404 */             createComboBox(option, allValues);
/*     */           }
/* 406 */           else if (option instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.InputOption) {
/* 407 */             createInput(option, allValues);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 412 */       if (this.txtArea != null) {
/* 413 */         this.txtArea.setModel(new DefaultListModel());
/* 414 */         this.txtArea.validate();
/*     */       } 
/* 416 */       if (getSelectOptionPanel().getComponentCount() != 0) {
/* 417 */         this.selectOptionScrollPane = new JScrollPane(getSelectOptionPanel());
/* 418 */         getSouthWestPanel().add(this.selectOptionScrollPane);
/*     */       } 
/* 420 */       if (getInputOptionPanel().getComponentCount() != 0) {
/* 421 */         this.inputOptionScrollPane = new JScrollPane(getInputOptionPanel());
/* 422 */         getSouthWestPanel().add(this.inputOptionScrollPane);
/*     */       } 
/* 424 */       if (getBooleanOptionPanel().getComponentCount() != 0) {
/* 425 */         this.booleanOptionScrollPane = new JScrollPane(getBooleanOptionPanel());
/* 426 */         getSouthWestPanel().add(this.booleanOptionScrollPane);
/*     */       } 
/*     */       
/* 429 */       if (this.tool.getType().getAdaptee().equals("PT_J2534")) {
/* 430 */         this.btnConfiguration.setEnabled(true);
/* 431 */         getSouthWestPanel().add(new JPanel());
/*     */       } else {
/* 433 */         this.btnConfiguration.setEnabled(false);
/*     */       } 
/*     */       
/* 436 */       this.diaDevicePanel.validate();
/* 437 */     } catch (Exception e) {
/* 438 */       log.error("deviceActionPerformed() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onConfigurationAction() {
/* 444 */     Starter.getInstance().startProcess(((J2534Tool)this.tool).getConfigApplication() + "  " + this.appContext.getLCID());
/*     */   }
/*     */   
/*     */   private void createComboBox(ToolOption opt, Vector<?> values) {
/*     */     try {
/* 449 */       JLabel lbl = new JLabel(opt.getDenotation(null));
/* 450 */       lbl.setFont(lbl.getFont().deriveFont(1));
/* 451 */       JComponent comp = new JComboBox(values);
/* 452 */       ((JComboBox)comp).addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 454 */               SettingsDiagnosticPanel.this.cboBoxAction(e);
/*     */             }
/*     */           });
/* 457 */       this.compKey.put(comp, opt.getPropertyKey());
/* 458 */       addRow(getSelectOptionPanel(), lbl, comp, selectGridy);
/* 459 */       selectGridy++;
/* 460 */     } catch (Exception e) {
/* 461 */       log.error("createComboBox() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createCheckBox(ToolOption opt, Vector<String> values) {
/*     */     try {
/* 467 */       JLabel lbl = new JLabel(opt.getDenotation(null));
/* 468 */       lbl.setFont(lbl.getFont().deriveFont(1));
/* 469 */       JComponent comp = new JCheckBox();
/* 470 */       Boolean val = Boolean.valueOf(values.get(0));
/* 471 */       ((JCheckBox)comp).setSelected(val.booleanValue());
/* 472 */       ((JCheckBox)comp).addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 474 */               SettingsDiagnosticPanel.this.checkBoxAction(e);
/*     */             }
/*     */           });
/* 477 */       this.compKey.put(comp, opt.getPropertyKey());
/* 478 */       addRow(getBooleanOptionPanel(), lbl, comp, booleanGridy);
/* 479 */       booleanGridy++;
/* 480 */     } catch (Exception e) {
/* 481 */       log.error("createCheckBox() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createInput(ToolOption opt, Vector<String> values) {
/*     */     try {
/* 487 */       JLabel lbl = new JLabel(opt.getDenotation(null));
/* 488 */       lbl.setFont(lbl.getFont().deriveFont(1));
/* 489 */       JComponent comp = new JTextField(values.get(0));
/* 490 */       comp.setEnabled(false);
/* 491 */       JButton btn = new JButton("...");
/* 492 */       this.btnComp.put(btn, comp);
/* 493 */       btn.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 495 */               SettingsDiagnosticPanel.this.pathAction(e);
/*     */             }
/*     */           });
/* 498 */       this.compKey.put(comp, opt.getPropertyKey());
/* 499 */       addInputRow(getInputOptionPanel(), lbl, comp, btn, inputGridy);
/* 500 */       inputGridy++;
/* 501 */     } catch (Exception e) {
/* 502 */       log.error("createInput() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addRow(JPanel panel, JLabel lblKey, JComponent comp, int gridy) {
/*     */     try {
/* 508 */       GridBagConstraints u_constraints = new GridBagConstraints();
/*     */       
/* 510 */       u_constraints.gridx = 0;
/* 511 */       u_constraints.gridy = gridy;
/* 512 */       u_constraints.weightx = 0.0D;
/* 513 */       u_constraints.gridwidth = 1;
/* 514 */       u_constraints.insets = new Insets(2, 4, 2, 4);
/* 515 */       u_constraints.fill = 2;
/* 516 */       panel.add(lblKey, u_constraints);
/*     */       
/* 518 */       u_constraints.gridx = 2;
/* 519 */       u_constraints.gridy = gridy;
/* 520 */       u_constraints.weightx = 1.0D;
/* 521 */       u_constraints.gridwidth = 3;
/* 522 */       u_constraints.fill = 2;
/* 523 */       panel.add(comp, u_constraints);
/*     */     }
/* 525 */     catch (Exception e) {
/* 526 */       log.error("addRow() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addInputRow(JPanel panel, JLabel lbl, JComponent comp, JButton btn, int gridy) {
/*     */     try {
/* 532 */       GridBagConstraints u_constraints = new GridBagConstraints();
/* 533 */       Dimension valuesDim = new Dimension(140, 20);
/*     */       
/* 535 */       u_constraints.gridx = 0;
/* 536 */       u_constraints.gridy = gridy;
/* 537 */       u_constraints.weightx = 0.0D;
/* 538 */       lbl.setPreferredSize(valuesDim);
/* 539 */       panel.add(lbl, u_constraints);
/* 540 */       gridy++;
/*     */       
/* 542 */       u_constraints.gridx = 0;
/* 543 */       u_constraints.gridy = gridy;
/* 544 */       u_constraints.weightx = 1.0D;
/* 545 */       u_constraints.gridwidth = 3;
/* 546 */       u_constraints.fill = 1;
/* 547 */       comp.setPreferredSize(valuesDim);
/* 548 */       panel.add(comp, u_constraints);
/*     */       
/* 550 */       valuesDim = new Dimension(40, 20);
/* 551 */       u_constraints.gridx = 5;
/* 552 */       u_constraints.gridy = gridy;
/* 553 */       u_constraints.weightx = 0.0D;
/* 554 */       u_constraints.gridwidth = 3;
/* 555 */       u_constraints.fill = 1;
/* 556 */       comp.setPreferredSize(valuesDim);
/* 557 */       panel.add(btn, u_constraints);
/* 558 */       inputGridy = gridy;
/*     */     }
/* 560 */     catch (Exception e) {
/* 561 */       log.error("addInputRow() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void pathAction(ActionEvent e) {
/*     */     try {
/* 568 */       JButton btn = (JButton)e.getSource();
/* 569 */       JTextField txtField = (JTextField)this.btnComp.get(btn);
/* 570 */       String initialValue = txtField.getText();
/* 571 */       String key = (String)this.compKey.get(txtField);
/* 572 */       String[] extensions = { "vit1", "txt" };
/* 573 */       if (isVIT1PathOption(key, "automatictest")) {
/* 574 */         extensions = new String[] { "" };
/*     */       }
/* 576 */       File defaultFile = determineDefaultFile(txtField);
/* 577 */       FileManager.openFileChooserActionPerformed(defaultFile, txtField, this.diaDevicePanel, extensions);
/* 578 */       String actualValue = txtField.getText();
/* 579 */       if (!initialValue.equals(actualValue)) {
/* 580 */         this.frame.resetSelection();
/* 581 */         this.clientSettings.setProperty((String)this.compKey.get(txtField), txtField.getText());
/* 582 */         if (isVIT1PathOption(key, "automatictest")) {
/* 583 */           resetVIT1PathOption("vehicle");
/* 584 */           resetVIT1PathOption("controller");
/* 585 */         } else if (isVIT1PathOption(key, "vehicle")) {
/* 586 */           resetVIT1PathOption("controller");
/* 587 */           resetVIT1PathOption("automatictest");
/* 588 */         } else if (isVIT1PathOption(key, "controller")) {
/* 589 */           resetVIT1PathOption("automatictest");
/*     */         }
/*     */       
/*     */       } 
/* 593 */     } catch (Exception ex) {
/* 594 */       log.error("pathAction() method, -exception: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private File determineDefaultFile(JTextField jTextField) {
/* 600 */     File vit1File = null;
/*     */     try {
/* 602 */       vit1File = isSet(jTextField) ? new File(jTextField.getText()) : null;
/* 603 */     } catch (Exception e) {}
/*     */     
/* 605 */     if (vit1File == null) {
/* 606 */       Iterator it = this.compKey.keySet().iterator();
/* 607 */       while (vit1File == null && it.hasNext()) {
/* 608 */         Object value = it.next();
/* 609 */         if (value instanceof JTextField) {
/* 610 */           String key = (String)this.compKey.get(value);
/* 611 */           if (isVIT1PathOption(key, "vehicle") || isVIT1PathOption(key, "controller") || isVIT1PathOption(key, "automatictest")) {
/*     */             try {
/* 613 */               vit1File = isSet((JTextField)value) ? new File(((JTextField)value).getText()) : null;
/* 614 */             } catch (Exception x) {}
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 620 */     return vit1File;
/*     */   }
/*     */   
/*     */   private boolean isSet(JTextField jTextField) {
/* 624 */     return (jTextField != null && jTextField.getText() != null && jTextField.getText().trim().length() > 0);
/*     */   }
/*     */   
/*     */   private void resetVIT1PathOption(String target) {
/* 628 */     Iterator it = this.compKey.keySet().iterator();
/* 629 */     while (it.hasNext()) {
/* 630 */       Object value = it.next();
/* 631 */       if (value instanceof JTextField) {
/* 632 */         String key = (String)this.compKey.get(value);
/* 633 */         if (isVIT1PathOption(key, target)) {
/* 634 */           ((JTextField)value).setText("");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 639 */     Set settings = this.clientSettings.getKeys();
/* 640 */     it = settings.iterator();
/* 641 */     while (it.hasNext()) {
/* 642 */       Object key = it.next();
/* 643 */       if (isVIT1PathOption(key.toString(), target)) {
/* 644 */         this.clientSettings.setProperty(key.toString(), "");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isVIT1PathOption(String key, String target) {
/* 650 */     if (key.indexOf("vit1path") >= 0) {
/* 651 */       return (key.indexOf(target) >= 0);
/*     */     }
/* 653 */     return false;
/*     */   }
/*     */   
/*     */   private void checkBoxAction(ActionEvent e) {
/*     */     try {
/* 658 */       JCheckBox chkFlag = (JCheckBox)e.getSource();
/* 659 */       if (chkFlag.isSelected()) {
/* 660 */         this.clientSettings.setProperty((String)this.compKey.get(chkFlag), "true");
/*     */       } else {
/* 662 */         this.clientSettings.setProperty((String)this.compKey.get(chkFlag), "false");
/*     */       } 
/* 664 */     } catch (Exception ex) {
/* 665 */       log.error("checkBoxAction() method, -exception: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cboBoxAction(ActionEvent e) {
/*     */     try {
/* 671 */       JComboBox combo = (JComboBox)e.getSource();
/* 672 */       this.clientSettings.setProperty((String)this.compKey.get(combo), combo.getSelectedItem().toString());
/* 673 */     } catch (Exception ex) {
/* 674 */       log.error("cboBoxAction() method, -exception: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void testConnectActionPerformed(ActionEvent e) {
/*     */     try {
/* 680 */       Tool tool = this.appContext.getTool(this.cboDevice.getSelectedItem().toString());
/*     */       
/* 682 */       Map<Object, Object> currentSettings = null;
/*     */       try {
/* 684 */         if (tool instanceof com.eoos.gm.tis2web.sps.client.tool.passthru.legacy.LegacyTech2PT || tool instanceof com.eoos.gm.tis2web.sps.client.tool.tech2remote.impl.Tech2Remote) {
/* 685 */           currentSettings = new HashMap<Object, Object>();
/* 686 */           String port = this.clientSettings.getProperty("tech2.option.port");
/* 687 */           if (port != null) {
/* 688 */             currentSettings.put("Port", port);
/*     */           }
/* 690 */           String baudRate = this.clientSettings.getProperty("tech2.option.baudrate");
/* 691 */           if (baudRate != null) {
/* 692 */             currentSettings.put("Baud Rate", baudRate);
/*     */           }
/*     */         } 
/* 695 */       } catch (Exception x) {}
/*     */ 
/*     */       
/* 698 */       boolean succes = tool.testConnection(currentSettings);
/* 699 */       StringBuffer buffer = new StringBuffer();
/* 700 */       DefaultListModel<String> result = new DefaultListModel();
/*     */       
/* 702 */       if (succes) {
/* 703 */         result.addElement(resourceProvider.getMessage(null, "sps.successful"));
/* 704 */         buffer.append(resourceProvider.getMessage(null, "sps.successful"));
/*     */       } else {
/* 706 */         result.addElement(resourceProvider.getMessage(null, "sps.no-successful"));
/* 707 */         buffer.append(resourceProvider.getMessage(null, "sps.no-successful"));
/*     */       } 
/*     */       
/* 710 */       if (tool instanceof SupportedProtocols) {
/* 711 */         List supportedProtocols = ((SupportedProtocols)tool).getSupportedProtocols();
/* 712 */         Iterator<String> protocolIterator = supportedProtocols.iterator();
/* 713 */         while (protocolIterator.hasNext()) {
/* 714 */           result.addElement(protocolIterator.next());
/*     */         }
/*     */       } 
/*     */       
/* 718 */       this.txtArea.setModel(result);
/* 719 */     } catch (Exception ex) {
/* 720 */       log.error("testConnectActionPerformed() method, -exception: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SettingsDiagnosticPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */