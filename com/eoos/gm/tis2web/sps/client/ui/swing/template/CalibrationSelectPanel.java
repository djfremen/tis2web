/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.TabPanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.ControllerUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CalibrationSelectPanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements TabPanelController
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   private JPanel titlePanel = null;
/*     */   
/*  42 */   private List calibTabPanelList = new ArrayList();
/*     */   
/*     */   protected Controller controller;
/*     */   
/*  46 */   protected static Locale locale = null;
/*     */   
/*  48 */   protected List newButtons = null;
/*     */   
/*  50 */   protected BaseCustomizeJPanel prevPanel = null;
/*     */   
/*  52 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  54 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  56 */   private static final Logger log = Logger.getLogger(CalibrationSelectPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CalibrationSelectPanel(Controller controller, BaseCustomizeJPanel prevPanel) {
/*  62 */     super(prevPanel);
/*  63 */     this.prevPanel = prevPanel;
/*  64 */     this.controller = controller;
/*  65 */     createButtonsList();
/*  66 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  75 */     FontUtils.setDefaultFont(FontUtils.getSpecialFont());
/*  76 */     setLayout(new BorderLayout());
/*  77 */     add(getTitlePanel(), "North");
/*  78 */     add(getJTabbedPane(), "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onNextAction() {
/*  83 */     FontUtils.setDefaultFont(FontUtils.getSelectedFont());
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public void onBackAction() {
/*  88 */     FontUtils.setDefaultFont(FontUtils.getSelectedFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {
/*  96 */     for (int i = 0; i < this.calibTabPanelList.size(); i++)
/*  97 */       ((CalibrationTabPanel)this.calibTabPanelList.get(i)).onOpenPanel(i); 
/*     */   }
/*     */   
/*     */   public void onResizeForm() {
/* 101 */     for (int i = 0; i < this.calibTabPanelList.size(); i++)
/* 102 */       ((CalibrationTabPanel)this.calibTabPanelList.get(i)).onResizeForm(); 
/*     */   }
/*     */   
/*     */   private JTabbedPane getJTabbedPane() {
/* 106 */     if (this.jTabbedPane == null) {
/*     */       try {
/* 108 */         this.jTabbedPane = new JTabbedPane();
/* 109 */         this.jTabbedPane.setFont(this.jTabbedPane.getFont().deriveFont(1));
/* 110 */         this.jTabbedPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
/*     */       }
/* 112 */       catch (Throwable e) {
/* 113 */         log.error("getJTabbedPane() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 116 */     return this.jTabbedPane;
/*     */   }
/*     */   
/*     */   private JPanel getTitlePanel() {
/* 120 */     if (this.titlePanel == null) {
/*     */       try {
/* 122 */         this.titlePanel = new JPanel();
/* 123 */         this.titlePanel.setLayout(new GridBagLayout());
/* 124 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/* 125 */         JLabel titleLabel = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.title"));
/* 126 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 127 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 128 */         titelPanelConstraint.gridx = 0;
/* 129 */         titelPanelConstraint.gridy = 0;
/* 130 */         this.titlePanel.add(titleLabel, titelPanelConstraint);
/* 131 */       } catch (Throwable e) {
/* 132 */         log.error("unable to load label calibrationScreen.title, - exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 135 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 140 */       setRequestGroup(req.getRequestGroup());
/*     */       
/* 142 */       ProgrammingDataSelectionRequest request = (ProgrammingDataSelectionRequest)req;
/* 143 */       if (request.getProgrammingSequence() != null) {
/* 144 */         ProgrammingSequence sequence = request.getProgrammingSequence();
/* 145 */         RequestBuilderImpl requestBuilderImpl = new RequestBuilderImpl();
/* 146 */         List<ControllerReference> controllers = sequence.getSequence();
/* 147 */         for (int i = 0; i < controllers.size(); i++) {
/* 148 */           ControllerReference controller = controllers.get(i);
/* 149 */           if (!controller.isType4Application()) {
/* 150 */             String label = controllers.get(i).toString();
/* 151 */             int index = label.indexOf("\t");
/* 152 */             if (index != -1) {
/* 153 */               label = label.substring(index + 1, label.length());
/*     */             }
/* 155 */             List modules = sequence.getProgrammingData(i);
/* 156 */             CalibrationTabPanel calibration = new CalibrationTabPanel(this.controller, this, this.prevPanel);
/* 157 */             this.calibTabPanelList.add(calibration);
/* 158 */             calibration.handleRequest((AssignmentRequest)requestBuilderImpl.makeProgrammingDataSelectionRequest(CommonAttribute.PROGRAMMING_DATA_SELECTION, modules));
/* 159 */             getJTabbedPane().addTab(label, (Icon)null, (Component)calibration, (String)null);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 163 */         request.getOptions();
/* 164 */         CalibrationTabPanel calibration = new CalibrationTabPanel(this.controller, this, this.prevPanel);
/* 165 */         calibration.handleRequest((AssignmentRequest)request);
/* 166 */         this.calibTabPanelList.add(calibration);
/*     */         
/* 168 */         getJTabbedPane().addTab(ControllerUtils.getShortNameController(System.getProperty(CommonAttribute.CONTROLLER.toString())), (Icon)null, (Component)calibration, (String)null);
/*     */       }
/*     */     
/* 171 */     } catch (Exception e) {
/* 172 */       log.error("handleRequest, - exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void changeTabIcon(int index, ImageIcon icon) {
/*     */     try {
/* 178 */       this.jTabbedPane.setIconAt(index, icon);
/* 179 */     } catch (Exception e) {
/* 180 */       log.error("changeTabIcon, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void changeSelectedTabIcon(ImageIcon icon) {
/*     */     try {
/* 186 */       this.jTabbedPane.setIconAt(this.jTabbedPane.getSelectedIndex(), icon);
/* 187 */     } catch (Exception e) {
/* 188 */       log.error("changeSelectedTabIcon, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createButtonsList() {
/*     */     try {
/* 197 */       this.newButtons = new ArrayList();
/* 198 */       this.newButtons.add("ecuData");
/* 199 */     } catch (Exception e) {
/* 200 */       log.warn("unable to create button list, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getNewButtons() {
/* 209 */     return this.newButtons;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\CalibrationSelectPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */