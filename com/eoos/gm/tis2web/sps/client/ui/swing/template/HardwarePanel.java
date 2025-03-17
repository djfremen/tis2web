/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.PanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJList;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJScrollPane;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.ControllerUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HardwarePanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements PanelController
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   protected JPanel mainPanel = null;
/*     */   
/*  46 */   protected JPanel titlePanel = null;
/*     */   
/*  48 */   protected HardwareSelectionRequest hardwareReq = null;
/*     */   
/*  50 */   protected CustomizeJList list = null;
/*     */   
/*  52 */   protected Controller controller = null;
/*     */   
/*  54 */   protected String shortControllerName = null;
/*     */   
/*  56 */   protected static Locale locale = null;
/*     */   
/*  58 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  60 */   private static final Logger log = Logger.getLogger(HardwarePanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HardwarePanel(Controller controller, BaseCustomizeJPanel previousPanel, AssignmentRequest req) {
/*  66 */     super(previousPanel);
/*  67 */     this.hardwareReq = (HardwareSelectionRequest)req;
/*  68 */     setRequestGroup(req.getRequestGroup());
/*  69 */     this.controller = controller;
/*  70 */     if (this.hardwareReq.getControllerID() != null) {
/*  71 */       this.shortControllerName = this.hardwareReq.getLabel();
/*     */     } else {
/*  73 */       this.shortControllerName = ControllerUtils.getShortNameController(System.getProperty(CommonAttribute.CONTROLLER.toString()));
/*     */     } 
/*  75 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  79 */     setLayout(new BorderLayout());
/*     */     
/*  81 */     add(getMainPanel(), "Center");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/*  91 */     if (this.titlePanel == null) {
/*     */       try {
/*  93 */         this.titlePanel = new JPanel();
/*  94 */         this.titlePanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/*  95 */         this.titlePanel.setLayout(new GridBagLayout());
/*  96 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/*     */         
/*  98 */         JLabel titleLabel = new JLabel();
/*  99 */         titleLabel.setText(resourceProvider.getLabel(locale, "hardwareScreen.title"));
/* 100 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 101 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 102 */         titelPanelConstraint.gridx = 0;
/* 103 */         titelPanelConstraint.gridy = 5;
/* 104 */         this.titlePanel.add(titleLabel, titelPanelConstraint);
/* 105 */       } catch (Throwable e) {
/* 106 */         log.error("unable to load resource, - exception:" + e.getMessage());
/*     */       } 
/*     */     }
/* 109 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/* 113 */     if (this.mainPanel == null) {
/*     */       try {
/* 115 */         this.mainPanel = new JPanel();
/* 116 */         this.mainPanel.setLayout(new GridBagLayout());
/* 117 */         this.mainPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null, Color.black));
/* 118 */         GridBagConstraints constr = new GridBagConstraints();
/* 119 */         JTextArea area1 = new JTextArea();
/* 120 */         if (this.hardwareReq.getHardwareDescription() != null) {
/* 121 */           area1.setEditable(false);
/* 122 */           area1.setText(this.hardwareReq.getHardwareDescription());
/* 123 */           area1.setLineWrap(true);
/* 124 */           area1.setWrapStyleWord(true);
/*     */         } 
/* 126 */         JScrollPane scroll = new JScrollPane(area1);
/* 127 */         constr.gridx = 0;
/* 128 */         constr.gridy = 0;
/* 129 */         constr.weightx = 1.0D;
/* 130 */         constr.weighty = 1.0D;
/* 131 */         constr.gridwidth = 3;
/* 132 */         constr.gridheight = 2;
/* 133 */         constr.insets = new Insets(20, 300, 10, 300);
/* 134 */         constr.fill = 1;
/* 135 */         this.mainPanel.add(scroll, constr);
/*     */         
/* 137 */         JLabel lblSoftware = new JLabel(resourceProvider.getLabel(locale, "hardwareScreen.prepare-software") + " " + this.shortControllerName);
/* 138 */         lblSoftware.setFont(lblSoftware.getFont().deriveFont(1));
/* 139 */         lblSoftware.setHorizontalTextPosition(2);
/* 140 */         constr.gridx = 0;
/* 141 */         constr.gridy = 2;
/* 142 */         constr.weightx = 0.0D;
/* 143 */         constr.weighty = 0.0D;
/* 144 */         constr.gridwidth = 2;
/* 145 */         constr.gridheight = 1;
/* 146 */         constr.fill = 0;
/*     */         
/* 148 */         this.mainPanel.add(lblSoftware, constr);
/*     */         
/* 150 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "hardwareScreen.select"));
/* 151 */         lbl.setFont(lbl.getFont().deriveFont(1));
/* 152 */         lbl.setHorizontalTextPosition(2);
/* 153 */         constr.gridx = 0;
/* 154 */         constr.gridy = 3;
/* 155 */         constr.weightx = 0.0D;
/* 156 */         constr.weighty = 0.0D;
/* 157 */         constr.gridwidth = 1;
/* 158 */         constr.gridheight = 1;
/* 159 */         constr.fill = 0;
/*     */         
/* 161 */         this.mainPanel.add(lbl, constr);
/*     */         
/* 163 */         this.list = new CustomizeJList();
/* 164 */         this.list.setRequest((SelectionRequest)this.hardwareReq);
/* 165 */         this.list.addListSelectionListener(new ListSelectionListener() {
/*     */               public void valueChanged(ListSelectionEvent evt) {
/* 167 */                 HardwarePanel.this.handleSelection(HardwarePanel.this.hardwareReq.getAttribute(), HardwarePanel.this.list.getValue(HardwarePanel.this.hardwareReq.getAttribute()));
/*     */               }
/*     */             });
/*     */         
/* 171 */         constr.gridx = 0;
/* 172 */         constr.gridy = 4;
/* 173 */         constr.weightx = 1.0D;
/* 174 */         constr.weighty = 1.0D;
/* 175 */         constr.gridwidth = 3;
/* 176 */         constr.gridheight = 2;
/* 177 */         constr.fill = 1;
/* 178 */         constr.insets = new Insets(20, 300, 100, 300);
/* 179 */         CustomizeJScrollPane scrollPane = new CustomizeJScrollPane((JComponent)this.list);
/* 180 */         this.mainPanel.add((Component)scrollPane, constr);
/*     */       }
/* 182 */       catch (Throwable e) {
/* 183 */         log.error("unable to load resource, - exception:" + e.getMessage());
/*     */       } 
/*     */     }
/* 186 */     return this.mainPanel;
/*     */   }
/*     */   
/*     */   public void handleSelection(Attribute attribute, Value value) {
/* 190 */     this.controller.handleSelection(attribute, value);
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {}
/*     */   
/*     */   public void handleComboSelection(JComponent comp) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\HardwarePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */