/*     */ package com.eoos.gm.tis2web.swdl.client.ui;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ 
/*     */ public class SelectApplicationPanel extends WizardStepScreen {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JButton jButtonRem;
/*     */   private JButton jButtonSel;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel10;
/*     */   private JPanel jPanel11;
/*     */   private JPanel jPanel12;
/*     */   private JPanel jPanel13;
/*     */   private JPanel jPanel14;
/*  40 */   private static final Logger log = Logger.getLogger(SelectApplicationPanel.class); private JPanel jPanel15; private JPanel jPanel16; private JPanel jPanel17; private JPanel jPanel18; private JPanel jPanel19; private JPanel jPanel2;
/*     */   private JPanel jPanel20;
/*     */   
/*     */   public SelectApplicationPanel() {
/*  44 */     initComponents();
/*  45 */     init();
/*     */   }
/*     */   private JPanel jPanel21; private JPanel jPanel22; private JPanel jPanel3; private JPanel jPanel4; private JPanel jPanel5; private JPanel jPanel6; private JPanel jPanel7; private JPanel jPanel8; private JPanel jPanel9; private JScrollPane jScrollTreeApps;
/*     */   private JTabbedPane jTabApps;
/*     */   private JTree jTreeApps;
/*     */   private JScrollPane jScrollTreeSelApp;
/*     */   private JTree jTreeSelApp;
/*     */   private JScrollPane jScrollTreeCurrApp;
/*     */   private JTree jTreeCurrApp;
/*     */   
/*     */   private void initComponents() {
/*  56 */     this.jPanel1 = new JPanel();
/*  57 */     this.jPanel2 = new JPanel();
/*  58 */     this.jPanel3 = new JPanel();
/*  59 */     this.jPanel4 = new JPanel();
/*  60 */     this.jPanel5 = new JPanel();
/*  61 */     this.jScrollTreeApps = new JScrollPane();
/*  62 */     this.jTreeApps = new JTree();
/*  63 */     this.jPanel6 = new JPanel();
/*  64 */     this.jPanel7 = new JPanel();
/*  65 */     this.jPanel8 = new JPanel();
/*  66 */     this.jButtonSel = new JButton();
/*  67 */     this.jPanel10 = new JPanel();
/*  68 */     this.jButtonRem = new JButton();
/*  69 */     this.jPanel11 = new JPanel();
/*  70 */     this.jPanel9 = new JPanel();
/*  71 */     this.jPanel12 = new JPanel();
/*  72 */     this.jTabApps = new JTabbedPane();
/*  73 */     this.jPanel13 = new JPanel();
/*  74 */     this.jPanel14 = new JPanel();
/*  75 */     this.jPanel15 = new JPanel();
/*  76 */     this.jPanel16 = new JPanel();
/*  77 */     this.jPanel17 = new JPanel();
/*  78 */     this.jPanel18 = new JPanel();
/*  79 */     this.jPanel19 = new JPanel();
/*  80 */     this.jPanel21 = new JPanel();
/*  81 */     this.jPanel20 = new JPanel();
/*  82 */     this.jPanel22 = new JPanel();
/*     */     
/*  84 */     setLayout(new GridBagLayout());
/*     */     
/*  86 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentShown(ComponentEvent evt) {
/*  88 */             SelectApplicationPanel.this.formComponentShown(evt);
/*     */           }
/*     */         });
/*     */     
/*  92 */     this.jPanel1.setLayout(new BorderLayout());
/*     */     
/*  94 */     this.jPanel1.setBorder(new TitledBorder(getResourceBundle().getString("IDS_APP_FOR_DOWN")));
/*  95 */     this.jPanel1.add(this.jPanel2, "North");
/*     */     
/*  97 */     this.jPanel1.add(this.jPanel3, "South");
/*     */     
/*  99 */     this.jPanel1.add(this.jPanel4, "East");
/*     */     
/* 101 */     this.jPanel1.add(this.jPanel5, "West");
/*     */     
/* 103 */     this.jTreeApps.setShowsRootHandles(true);
/* 104 */     this.jTreeApps.setRootVisible(false);
/* 105 */     this.jTreeApps.addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent evt) {
/* 107 */             SelectApplicationPanel.this.jTreeAppsMouseClicked(evt);
/*     */           }
/*     */         });
/* 110 */     this.jTreeApps.addTreeSelectionListener(new TreeSelectionListener() {
/*     */           public void valueChanged(TreeSelectionEvent evt) {
/* 112 */             SelectApplicationPanel.this.jTreeAppsValueChanged(evt);
/*     */           }
/*     */         });
/*     */     
/* 116 */     this.jScrollTreeApps.setViewportView(this.jTreeApps);
/*     */     
/* 118 */     this.jPanel1.add(this.jScrollTreeApps, "Center");
/*     */     
/* 120 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 121 */     gridBagConstraints.fill = 1;
/* 122 */     gridBagConstraints.anchor = 17;
/* 123 */     gridBagConstraints.weightx = 1.0D;
/* 124 */     gridBagConstraints.weighty = 1.0D;
/* 125 */     add(this.jPanel1, gridBagConstraints);
/*     */     
/* 127 */     gridBagConstraints = new GridBagConstraints();
/* 128 */     gridBagConstraints.fill = 1;
/* 129 */     add(this.jPanel6, gridBagConstraints);
/*     */     
/* 131 */     this.jPanel7.setLayout(new GridBagLayout());
/*     */     
/* 133 */     gridBagConstraints = new GridBagConstraints();
/* 134 */     gridBagConstraints.gridwidth = 0;
/* 135 */     gridBagConstraints.fill = 1;
/* 136 */     gridBagConstraints.weighty = 1.0D;
/* 137 */     this.jPanel7.add(this.jPanel8, gridBagConstraints);
/*     */     
/*     */     try {
/* 140 */       ImageIcon icon = new ImageIcon(ResourceDataProvider.getInstance().getData("select_app.jpg"));
/* 141 */       this.jButtonSel.setIcon(icon);
/*     */     }
/* 143 */     catch (Exception e) {
/* 144 */       log.error("unable to set icon - exception :" + e, e);
/*     */     } 
/* 146 */     this.jButtonSel.setFont(new Font("Dialog", 0, 12));
/* 147 */     this.jButtonSel.setText(getResourceBundle().getString("IDS_SELECT"));
/* 148 */     this.jButtonSel.setVerticalTextPosition(3);
/* 149 */     this.jButtonSel.setHorizontalTextPosition(0);
/* 150 */     this.jButtonSel.setMargin(new Insets(0, 0, 0, 0));
/* 151 */     this.jButtonSel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 153 */             SelectApplicationPanel.this.jButtonSelActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 157 */     gridBagConstraints = new GridBagConstraints();
/* 158 */     gridBagConstraints.gridwidth = 0;
/* 159 */     this.jPanel7.add(this.jButtonSel, gridBagConstraints);
/*     */     
/* 161 */     gridBagConstraints = new GridBagConstraints();
/* 162 */     gridBagConstraints.gridwidth = 0;
/* 163 */     gridBagConstraints.fill = 1;
/* 164 */     gridBagConstraints.weighty = 1.0D;
/* 165 */     this.jPanel7.add(this.jPanel10, gridBagConstraints);
/*     */     
/* 167 */     this.jButtonRem.setIcon(new ImageIcon(getClass().getResource("/com/eoos/gm/tis2web/swdl/client/resources/remove_app.jpg")));
/* 168 */     this.jButtonRem.setFont(new Font("Dialog", 0, 12));
/* 169 */     this.jButtonRem.setText(getResourceBundle().getString("IDS_REMOVE"));
/* 170 */     this.jButtonRem.setVerticalTextPosition(3);
/* 171 */     this.jButtonRem.setHorizontalTextPosition(0);
/* 172 */     this.jButtonRem.setMargin(new Insets(0, 0, 0, 0));
/* 173 */     this.jButtonRem.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 175 */             SelectApplicationPanel.this.jButtonRemActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 179 */     gridBagConstraints = new GridBagConstraints();
/* 180 */     gridBagConstraints.gridwidth = 0;
/* 181 */     this.jPanel7.add(this.jButtonRem, gridBagConstraints);
/*     */     
/* 183 */     gridBagConstraints = new GridBagConstraints();
/* 184 */     gridBagConstraints.fill = 1;
/* 185 */     gridBagConstraints.weighty = 1.0D;
/* 186 */     this.jPanel7.add(this.jPanel11, gridBagConstraints);
/*     */     
/* 188 */     gridBagConstraints = new GridBagConstraints();
/* 189 */     gridBagConstraints.fill = 1;
/* 190 */     add(this.jPanel7, gridBagConstraints);
/*     */     
/* 192 */     gridBagConstraints = new GridBagConstraints();
/* 193 */     gridBagConstraints.fill = 1;
/* 194 */     add(this.jPanel9, gridBagConstraints);
/*     */     
/* 196 */     this.jPanel12.setLayout(new BorderLayout());
/*     */     
/* 198 */     this.jPanel12.setBorder(new TitledBorder(""));
/* 199 */     this.jPanel12.add(this.jTabApps, "Center");
/*     */     
/* 201 */     this.jPanel12.add(this.jPanel13, "North");
/*     */     
/* 203 */     this.jPanel12.add(this.jPanel14, "East");
/*     */     
/* 205 */     this.jPanel12.add(this.jPanel15, "West");
/*     */     
/* 207 */     this.jPanel12.add(this.jPanel16, "South");
/*     */     
/* 209 */     gridBagConstraints = new GridBagConstraints();
/* 210 */     gridBagConstraints.gridwidth = 0;
/* 211 */     gridBagConstraints.fill = 1;
/* 212 */     gridBagConstraints.anchor = 13;
/* 213 */     gridBagConstraints.weightx = 1.0D;
/* 214 */     gridBagConstraints.weighty = 1.0D;
/* 215 */     add(this.jPanel12, gridBagConstraints);
/*     */     
/* 217 */     this.jPanel17.setLayout(new GridBagLayout());
/*     */     
/* 219 */     gridBagConstraints = new GridBagConstraints();
/* 220 */     gridBagConstraints.fill = 2;
/* 221 */     gridBagConstraints.anchor = 17;
/* 222 */     gridBagConstraints.weightx = 1.0D;
/* 223 */     this.jPanel17.add(this.jPanel18, gridBagConstraints);
/*     */     
/* 225 */     this.jPanel17.add(this.jPanel19, new GridBagConstraints());
/*     */     
/* 227 */     gridBagConstraints = new GridBagConstraints();
/* 228 */     gridBagConstraints.fill = 2;
/* 229 */     gridBagConstraints.weightx = 1.0D;
/* 230 */     this.jPanel17.add(this.jPanel21, gridBagConstraints);
/*     */     
/* 232 */     this.jPanel17.add(this.jPanel20, new GridBagConstraints());
/*     */     
/* 234 */     gridBagConstraints = new GridBagConstraints();
/* 235 */     gridBagConstraints.gridwidth = 0;
/* 236 */     gridBagConstraints.fill = 2;
/* 237 */     add(this.jPanel17, gridBagConstraints);
/*     */     
/* 239 */     gridBagConstraints = new GridBagConstraints();
/* 240 */     gridBagConstraints.gridwidth = 0;
/* 241 */     gridBagConstraints.fill = 2;
/* 242 */     add(this.jPanel22, gridBagConstraints);
/*     */   }
/*     */ 
/*     */   
/*     */   private void jTreeAppsMouseClicked(MouseEvent evt) {
/* 247 */     if (evt.getClickCount() == 2 && ((DefaultMutableTreeNode)this.jTreeApps.getLastSelectedPathComponent()).isLeaf())
/*     */     {
/* 249 */       selectApp(); } 
/*     */   }
/*     */   
/*     */   private void jButtonRemActionPerformed(ActionEvent evt) {
/* 253 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
/* 254 */     DefaultTreeModel treeModel = new DefaultTreeModel(root);
/* 255 */     this.jTreeSelApp.setModel(treeModel);
/* 256 */     this.jButtonRem.setEnabled(false);
/*     */   }
/*     */   
/*     */   private void jButtonSelActionPerformed(ActionEvent evt) {
/* 260 */     selectApp();
/*     */   }
/*     */   
/*     */   private void jTreeAppsValueChanged(TreeSelectionEvent evt) {
/* 264 */     DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.jTreeApps.getLastSelectedPathComponent();
/* 265 */     if (node == null)
/*     */       return; 
/* 267 */     if (node.isLeaf() && SDCurrentContext.getInstance().getDeviceInfo() != null) {
/* 268 */       this.jButtonSel.setEnabled(true);
/*     */     } else {
/* 270 */       this.jButtonSel.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void formComponentShown(ComponentEvent evt) {
/* 275 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(12L, new Byte((byte)8), "IDS_BTN_DOWNLOAD"));
/* 276 */     if (this.jTreeSelApp.getRowCount() < 3 || SDCurrentContext.getInstance().getDeviceInfo() == null)
/* 277 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)8), new Boolean(false))); 
/*     */   }
/*     */   
/*     */   private void jTreeSelAppValueChanged(TreeSelectionEvent evt) {
/* 281 */     DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.jTreeSelApp.getLastSelectedPathComponent();
/* 282 */     if (node == null)
/*     */       return; 
/* 284 */     if (node.isLeaf()) {
/* 285 */       this.jButtonRem.setEnabled(true);
/*     */     } else {
/* 287 */       this.jButtonRem.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void selectApp() {
/* 292 */     DefaultMutableTreeNode nodeSelLang = (DefaultMutableTreeNode)this.jTreeApps.getLastSelectedPathComponent();
/* 293 */     DeviceInfo devInf = (DeviceInfo)nodeSelLang.getUserObject();
/*     */     
/* 295 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
/* 296 */     DefaultMutableTreeNode nodeName = new DefaultMutableTreeNode(devInf.getAppName());
/* 297 */     root.add(nodeName);
/* 298 */     String versName = devInf.getVersion();
/* 299 */     if (devInf.getVersionSize() > 10) {
/* 300 */       versName = versName + "-" + Integer.toString(devInf.getVersionSize()) + "MB";
/*     */     }
/* 302 */     DefaultMutableTreeNode nodeVers = new DefaultMutableTreeNode(versName);
/* 303 */     nodeName.add(nodeVers);
/* 304 */     DefaultMutableTreeNode nodeLang = new DefaultMutableTreeNode(devInf);
/* 305 */     nodeVers.add(nodeLang);
/*     */     
/* 307 */     DefaultTreeModel treeModel = new DefaultTreeModel(root);
/* 308 */     this.jTreeSelApp.setModel(treeModel);
/* 309 */     this.jTreeSelApp.expandRow(0);
/* 310 */     this.jTreeSelApp.expandRow(1);
/*     */     
/* 312 */     this.jButtonRem.setEnabled(false);
/* 313 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(10L, new Byte((byte)8), new Boolean(true)));
/*     */   }
/*     */   
/*     */   private DeviceInfo getNewestAppForOrg(String org, String lan) {
/* 317 */     DeviceInfo newInf = null;
/* 318 */     Set applications = SDCurrentContext.getInstance().getApplications();
/* 319 */     Iterator<Application> itApp = applications.iterator();
/* 320 */     while (itApp.hasNext()) {
/* 321 */       Application app = itApp.next();
/* 322 */       if (org.compareTo(app.getDescription()) == 0) {
/* 323 */         Set versions = app.getVersions();
/* 324 */         TreeSet sortVersions = new TreeSet((Comparator<?>)new VersDateComparator());
/* 325 */         sortVersions.addAll(versions);
/* 326 */         Iterator<Version> itVers = sortVersions.iterator();
/* 327 */         if (itVers.hasNext()) {
/* 328 */           Version vers = itVers.next();
/* 329 */           Language lang = new Language(lan);
/* 330 */           if (vers.getLanguages().contains(lang)) {
/* 331 */             newInf = DomainUtil.Version2DeviceInfo(vers, lang);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 337 */     return newInf;
/*     */   }
/*     */   
/*     */   private void init() {
/* 341 */     this.jTreeApps.getSelectionModel().setSelectionMode(1);
/* 342 */     this.jTreeSelApp = new JTree();
/* 343 */     this.jTreeSelApp.getSelectionModel().setSelectionMode(1);
/* 344 */     this.jTreeSelApp.setShowsRootHandles(true);
/* 345 */     this.jTreeSelApp.setRootVisible(false);
/* 346 */     this.jTreeSelApp.addTreeSelectionListener(new TreeSelectionListener() {
/*     */           public void valueChanged(TreeSelectionEvent evt) {
/* 348 */             SelectApplicationPanel.this.jTreeSelAppValueChanged(evt);
/*     */           }
/*     */         });
/* 351 */     this.jScrollTreeSelApp = new JScrollPane(this.jTreeSelApp);
/* 352 */     this.jTreeCurrApp = new JTree();
/* 353 */     this.jTreeCurrApp.getSelectionModel().setSelectionMode(1);
/* 354 */     this.jTreeCurrApp.setShowsRootHandles(true);
/* 355 */     this.jTreeCurrApp.setRootVisible(false);
/* 356 */     this.jScrollTreeCurrApp = new JScrollPane(this.jTreeCurrApp);
/* 357 */     this.jTabApps.addTab(getResourceBundle().getString("IDS_SELECTED"), this.jScrollTreeSelApp);
/* 358 */     this.jTabApps.addTab(getResourceBundle().getString("IDS_CURRENT"), this.jScrollTreeCurrApp);
/*     */   }
/*     */   
/*     */   private void initTreeApps() {
/* 362 */     DeviceInfo deviceInfo = SDCurrentContext.getInstance().getDeviceInfo();
/* 363 */     Set applications = SDCurrentContext.getInstance().getApplications();
/* 364 */     TreeSet sortApps = new TreeSet((Comparator<?>)new ApplicationComparator());
/* 365 */     sortApps.addAll(applications);
/* 366 */     Iterator<Application> itApp = sortApps.iterator();
/* 367 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
/* 368 */     while (itApp.hasNext()) {
/* 369 */       Application app = itApp.next();
/* 370 */       DefaultMutableTreeNode nodeApp = new DefaultMutableTreeNode(app.getDescription());
/* 371 */       Set versions = app.getVersions();
/* 372 */       TreeSet sortVersions = new TreeSet((Comparator<?>)new VersionComparator());
/* 373 */       sortVersions.addAll(versions);
/* 374 */       boolean hasValidVersion = false;
/* 375 */       Iterator<Version> itVers = sortVersions.iterator();
/* 376 */       while (itVers.hasNext()) {
/* 377 */         Version vers = itVers.next();
/* 378 */         String versName = vers.getNumber();
/* 379 */         if (vers.getSize().longValue() > 10L) {
/* 380 */           versName = versName + "-" + vers.getSize().toString() + "MB";
/* 381 */         } else if (deviceInfo != null && 
/* 382 */           deviceInfo.isStrataCard()) {
/* 383 */           String addInfo = vers.getAdditionalInfo();
/* 384 */           if (addInfo != null) {
/* 385 */             addInfo = addInfo.toUpperCase(Locale.ENGLISH);
/* 386 */             if (addInfo.indexOf("SUPPORTS_ALL_CARD_TYPES") == -1) {
/*     */               continue;
/*     */             }
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/* 394 */         hasValidVersion = true;
/* 395 */         DefaultMutableTreeNode nodeVers = new DefaultMutableTreeNode(versName);
/* 396 */         Set languages = vers.getLanguages();
/* 397 */         TreeSet sortLanguages = new TreeSet((Comparator<?>)new LanguageComparator(SDCurrentContext.getInstance().getUILanguage()));
/* 398 */         sortLanguages.addAll(languages);
/* 399 */         Iterator<Language> itLang = sortLanguages.iterator();
/* 400 */         while (itLang.hasNext()) {
/* 401 */           Language lang = itLang.next();
/* 402 */           DeviceInfo devInf = DomainUtil.Version2DeviceInfo(vers, lang);
/* 403 */           DefaultMutableTreeNode nodeLang = new DefaultMutableTreeNode(devInf);
/* 404 */           nodeVers.add(nodeLang);
/*     */         } 
/* 406 */         nodeApp.add(nodeVers);
/*     */       } 
/* 408 */       if (hasValidVersion) {
/* 409 */         root.add(nodeApp);
/*     */       }
/*     */     } 
/*     */     
/* 413 */     DefaultTreeModel treeModel = new DefaultTreeModel(root);
/* 414 */     this.jTreeApps.setModel(treeModel);
/* 415 */     for (int i = root.getChildCount() - 1; i > -1; i--) {
/* 416 */       DeviceInfo devInf = SDCurrentContext.getInstance().getDeviceInfo();
/* 417 */       Object[] treePath = this.jTreeApps.getPathForRow(i).getPath();
/* 418 */       String elem = treePath[treePath.length - 1].toString();
/* 419 */       if (devInf != null && devInf.getAppName().compareTo(elem) == 0)
/* 420 */         this.jTreeApps.setSelectionRow(i); 
/* 421 */       this.jTreeApps.expandRow(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initTreeCurrApp() {
/* 426 */     DeviceInfo devInf = SDCurrentContext.getInstance().getDeviceInfo();
/* 427 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
/* 428 */     if (devInf != null) {
/* 429 */       DefaultMutableTreeNode nodeName = new DefaultMutableTreeNode(devInf.getAppName());
/* 430 */       root.add(nodeName);
/* 431 */       String versName = devInf.getVersion();
/* 432 */       if (devInf.getVersionSize() > 10) {
/* 433 */         versName = versName + "-" + Integer.toString(devInf.getVersionSize()) + "MB";
/*     */       }
/* 435 */       DefaultMutableTreeNode nodeVers = new DefaultMutableTreeNode(versName);
/* 436 */       nodeName.add(nodeVers);
/* 437 */       DefaultMutableTreeNode nodeLang = new DefaultMutableTreeNode(devInf);
/* 438 */       nodeVers.add(nodeLang);
/*     */     } 
/* 440 */     DefaultTreeModel treeModel = new DefaultTreeModel(root);
/* 441 */     this.jTreeCurrApp.setModel(treeModel);
/* 442 */     this.jTreeCurrApp.expandRow(0);
/* 443 */     this.jTreeCurrApp.expandRow(1);
/*     */   }
/*     */   
/*     */   private void initTreeSelApp() {
/* 447 */     DeviceInfo devInf = SDCurrentContext.getInstance().getDeviceInfo();
/* 448 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
/* 449 */     if (devInf != null && devInf.getAppName().compareTo("Unknown Software Orgin") != 0 && !devInf.EmptyCard()) {
/* 450 */       DeviceInfo newInf = getNewestAppForOrg(devInf.getAppName(), devInf.getLanguage());
/* 451 */       if (newInf != null) {
/* 452 */         DefaultMutableTreeNode nodeName = new DefaultMutableTreeNode(newInf.getAppName());
/* 453 */         root.add(nodeName);
/* 454 */         String versName = newInf.getVersion();
/* 455 */         if (newInf.getVersionSize() > 10) {
/* 456 */           versName = versName + "-" + Integer.toString(newInf.getVersionSize()) + "MB";
/*     */         }
/* 458 */         DefaultMutableTreeNode nodeVers = new DefaultMutableTreeNode(versName);
/* 459 */         nodeName.add(nodeVers);
/* 460 */         DefaultMutableTreeNode nodeLang = new DefaultMutableTreeNode(newInf);
/* 461 */         nodeVers.add(nodeLang);
/*     */       } 
/*     */     } 
/* 464 */     DefaultTreeModel treeModel = new DefaultTreeModel(root);
/* 465 */     this.jTreeSelApp.setModel(treeModel);
/* 466 */     this.jTreeSelApp.expandRow(0);
/* 467 */     this.jTreeSelApp.expandRow(1);
/*     */   }
/*     */   
/*     */   void onActivate() {
/* 471 */     ((TitledBorder)this.jPanel12.getBorder()).setTitle(SDCurrentContext.getInstance().getSelectedTool().getDescription());
/* 472 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(1L, "IDS_TITLE_SEL_APP"));
/* 473 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(8L, null));
/* 474 */     initTreeApps();
/* 475 */     initTreeCurrApp();
/* 476 */     initTreeSelApp();
/* 477 */     this.jButtonSel.setEnabled(false);
/* 478 */     this.jButtonRem.setEnabled(false);
/*     */   }
/*     */   
/*     */   void onBtnPressed(byte btn) {
/* 482 */     switch (btn) {
/*     */       case 8:
/* 484 */         onNext();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   byte showOrHide_Callback() {
/* 490 */     return 1;
/*     */   }
/*     */   
/*     */   private void onNext() {
/* 494 */     DefaultMutableTreeNode nodeSelLang = ((DefaultMutableTreeNode)this.jTreeSelApp.getModel().getRoot()).getFirstLeaf();
/* 495 */     DeviceInfo devInf = (DeviceInfo)nodeSelLang.getUserObject();
/* 496 */     SDCurrentContext.getInstance().setNewAppInfo(devInf);
/*     */     
/* 498 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(9L));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\SelectApplicationPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */