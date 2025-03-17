/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.ControllerUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.DisplaySummaryRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.text.View;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SummaryPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  51 */   protected JPanel titlePanel = null;
/*     */   
/*  53 */   protected List summaryTabPanelList = new ArrayList();
/*     */   
/*  55 */   protected BaseCustomizeJPanel prevPanel = null;
/*     */   
/*  57 */   protected static Locale locale = null;
/*     */   
/*  59 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  61 */   protected JPanel mainPanel = null;
/*     */   
/*  63 */   protected List newButtons = null;
/*     */   
/*  65 */   protected JPanel vehDataPanel = null;
/*     */   
/*  67 */   protected JTable vehDataTable = null;
/*     */   
/*  69 */   protected TableModel summaryTableModel = null;
/*     */   
/*  71 */   protected TableModel vehDataTableModel = null;
/*     */   
/*  73 */   protected TableModel descriptTableModel = null;
/*     */   
/*  75 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  77 */   protected static final Logger log = Logger.getLogger(SummaryPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummaryPanel(BaseCustomizeJPanel prevPanel) {
/*  83 */     super(prevPanel);
/*  84 */     this.prevPanel = prevPanel;
/*  85 */     initialize();
/*  86 */     this.newButtons = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  96 */     setLayout(new BorderLayout());
/*  97 */     add(getTitlePanel(), "North");
/*  98 */     getVehDataPanel();
/*     */   }
/*     */   
/*     */   private JPanel getTitlePanel() {
/* 102 */     if (this.titlePanel == null) {
/*     */       try {
/* 104 */         this.titlePanel = new JPanel();
/* 105 */         this.titlePanel.setLayout(new GridBagLayout());
/* 106 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/* 107 */         JLabel titleLabel = new JLabel();
/* 108 */         titleLabel.setText(resourceProvider.getLabel(locale, "summaryScreen.title"));
/* 109 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 110 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 111 */         titelPanelConstraint.gridx = 3;
/* 112 */         titelPanelConstraint.gridy = 0;
/* 113 */         this.titlePanel.add(titleLabel, titelPanelConstraint);
/* 114 */       } catch (Throwable e) {
/* 115 */         log.error("unable to load resource summaryScreen.title, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 118 */     return this.titlePanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTabbedPane getJTabbedPane() {
/* 127 */     if (this.jTabbedPane == null) {
/*     */       try {
/* 129 */         this.jTabbedPane = new JTabbedPane();
/* 130 */         this.jTabbedPane.setFont(this.jTabbedPane.getFont().deriveFont(1));
/* 131 */         this.jTabbedPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
/*     */         
/* 133 */         this.jTabbedPane.addChangeListener(new ChangeListener()
/*     */             {
/*     */               public void stateChanged(ChangeEvent evt) {
/* 136 */                 JTabbedPane pane = (JTabbedPane)evt.getSource();
/* 137 */                 int sel = pane.getSelectedIndex();
/* 138 */                 SummaryPanel.this.selectedTabbedAction(sel);
/*     */               }
/*     */             });
/*     */       }
/* 142 */       catch (Throwable e) {
/* 143 */         log.error("getJTabbedPane() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 146 */     return this.jTabbedPane;
/*     */   }
/*     */   
/*     */   private void selectedTabbedAction(int index) {
/* 150 */     boolean hasHistory = (((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getHistory() != null);
/* 151 */     boolean hasModuleInfo = (((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getModuleInfo() != null && ((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getModuleInfo().size() != 0);
/* 152 */     SPSFrame.getInstance().triggerSummaryButtons(hasHistory, hasModuleInfo);
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/* 156 */     if (this.mainPanel == null) {
/*     */       try {
/* 158 */         this.mainPanel = new JPanel();
/* 159 */         this.mainPanel.setFont(this.mainPanel.getFont().deriveFont(1));
/* 160 */         this.mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
/*     */         
/* 162 */         this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*     */       }
/* 164 */       catch (Throwable e) {
/* 165 */         log.error("getMainPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 168 */     return this.mainPanel;
/*     */   }
/*     */   
/*     */   private JPanel getVehDataPanel() {
/* 172 */     if (this.vehDataPanel == null) {
/*     */       
/*     */       try {
/* 175 */         this.vehDataPanel = new JPanel(new BorderLayout());
/* 176 */         this.vehDataPanel.setBorder(new TitledBorder(null, null, 0, 0, null));
/* 177 */         this.vehDataTable = new JTable();
/* 178 */         this.vehDataTable.setEnabled(false);
/* 179 */         this.vehDataTable.setShowGrid(false);
/* 180 */         this.vehDataTable.setShowHorizontalLines(true);
/* 181 */         this.vehDataTable.getTableHeader().setReorderingAllowed(false);
/* 182 */         JScrollPane scrollPane = new JScrollPane(this.vehDataTable);
/* 183 */         scrollPane.getViewport().setBackground(Color.white);
/* 184 */         scrollPane.getViewport().setOpaque(true);
/* 185 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "summaryScreen.vehDataTable.title"));
/* 186 */         lbl.setFont(lbl.getFont().deriveFont(1));
/* 187 */         this.vehDataPanel.add(lbl, "North");
/* 188 */         this.vehDataPanel.add(scrollPane, "Center");
/* 189 */         this.vehDataPanel.setPreferredSize(new Dimension(SwingUtils.getScreenWidth(), SwingUtils.getScreenHeight() * 3 / 10));
/*     */       }
/* 191 */       catch (Throwable e) {
/* 192 */         log.error("unable to load resource, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 195 */     return this.vehDataPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getNewButtons() {
/* 202 */     return this.newButtons;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {
/* 207 */     for (int i = 0; i < this.summaryTabPanelList.size(); i++) {
/* 208 */       ((SummaryTabPanel)this.summaryTabPanelList.get(i)).onOpenPanel();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResizeForm() {
/* 214 */     for (int i = 0; i < this.summaryTabPanelList.size(); i++)
/* 215 */       ((SummaryTabPanel)this.summaryTabPanelList.get(i)).onResizeForm(); 
/* 216 */     resizeVehDataTable();
/*     */   }
/*     */   
/*     */   public List getHistory() {
/* 220 */     return ((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getHistory();
/*     */   }
/*     */   
/*     */   public Vector getModuleInfoData() {
/* 224 */     return ((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getModuleInfo();
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 229 */       setRequestGroup(req.getRequestGroup());
/*     */       
/* 231 */       DefaultTableModel vehicleModel = (DefaultTableModel)((DisplaySummaryRequest)req).getVehicleData();
/* 232 */       if (vehicleModel != null) {
/* 233 */         vehicleModel.setColumnIdentifiers(getVehicleTableColumnsName());
/* 234 */         this.vehDataTable.setModel(vehicleModel);
/* 235 */         this.vehDataTableModel = vehicleModel;
/*     */       } 
/*     */       
/* 238 */       if (((DisplaySummaryRequest)req).getModuleDataList() != null) {
/*     */         
/* 240 */         RequestBuilderImpl requestBuilderImpl = new RequestBuilderImpl();
/* 241 */         DefaultTableModel mtable = null;
/* 242 */         List<E> controllers = ((DisplaySummaryRequest)req).getControllers();
/* 243 */         for (int i = 0; i < controllers.size(); i++) {
/* 244 */           String label = controllers.get(i).toString();
/* 245 */           int index = label.indexOf("\t");
/* 246 */           if (index != -1) {
/* 247 */             label = label.substring(index + 1, label.length());
/*     */           }
/* 249 */           DefaultTableModel tableModel = ((DisplaySummaryRequest)req).getModuleDataList().get(i);
/* 250 */           if (mtable == null) {
/* 251 */             mtable = new DefaultTableModel();
/* 252 */             mtable.setColumnCount(tableModel.getColumnCount());
/*     */           } 
/*     */           
/* 255 */           Iterator<Vector> iterator = tableModel.getDataVector().iterator();
/* 256 */           while (iterator.hasNext()) {
/* 257 */             Vector row = iterator.next();
/* 258 */             mtable.addRow(row.toArray());
/*     */           } 
/*     */         } 
/*     */         
/* 262 */         DisplaySummaryRequestImpl requestSummary = (DisplaySummaryRequestImpl)requestBuilderImpl.makeDisplaySummaryRequest(CommonAttribute.SUMMARY);
/* 263 */         requestSummary.setModuleData(mtable);
/* 264 */         SummaryTabPanel summaryTabPanel1 = new SummaryTabPanel(this, this.prevPanel);
/* 265 */         summaryTabPanel1.setFlagProgrammingSequence(true);
/* 266 */         summaryTabPanel1.handleRequest((AssignmentRequest)requestSummary);
/* 267 */         this.summaryTabPanelList.add(summaryTabPanel1);
/* 268 */         this.summaryTableModel = summaryTabPanel1.getSummaryTableModel();
/* 269 */         this.descriptTableModel = summaryTabPanel1.getDescriptTableModel();
/* 270 */         getJTabbedPane().addTab(((DisplaySummaryRequest)req).getControllerLabel(), (Icon)null, (Component)summaryTabPanel1, (String)null);
/* 271 */         getMainPanel().add(getJTabbedPane());
/* 272 */         getMainPanel().add(getVehDataPanel());
/* 273 */         add(getMainPanel(), "Center");
/* 274 */         resizeVehDataTable();
/*     */         return;
/*     */       } 
/* 277 */       if (((DisplaySummaryRequest)req).isGMEMoreSequence()) {
/*     */         
/* 279 */         RequestBuilderImpl requestBuilderImpl = new RequestBuilderImpl();
/*     */         
/* 281 */         for (int i = 0; i < ((DisplaySummaryRequest)req).getModuleDataListGME().size(); i++) {
/* 282 */           DisplaySummaryRequestImpl requestSummary = (DisplaySummaryRequestImpl)requestBuilderImpl.makeDisplaySummaryRequest(CommonAttribute.SUMMARY);
/* 283 */           SummaryTabPanel summaryTabPanel1 = new SummaryTabPanel(this, this.prevPanel);
/* 284 */           requestSummary.setModuleData(((DisplaySummaryRequest)req).getModuleDataListGME().get(i));
/* 285 */           requestSummary.setDescriptionData(((DisplaySummaryRequest)req).getDescriptionDataListGME().get(i));
/* 286 */           requestSummary.setSummary(((DisplaySummaryRequest)req).getSequenceSummary().get(i));
/* 287 */           summaryTabPanel1.setFlagProgrammingSequence(false);
/* 288 */           summaryTabPanel1.handleRequest((AssignmentRequest)requestSummary);
/* 289 */           this.summaryTabPanelList.add(summaryTabPanel1);
/* 290 */           this.summaryTableModel = summaryTabPanel1.getSummaryTableModel();
/* 291 */           this.descriptTableModel = summaryTabPanel1.getDescriptTableModel();
/* 292 */           String controller = ((DisplaySummaryRequest)req).getControllers().get(i);
/* 293 */           getJTabbedPane().addTab(controller, (Icon)null, (Component)summaryTabPanel1, (String)null);
/*     */         } 
/* 295 */         getMainPanel().add(getJTabbedPane());
/* 296 */         getMainPanel().add(getVehDataPanel());
/* 297 */         add(getMainPanel(), "Center");
/* 298 */         if (((SummaryTabPanel)getJTabbedPane().getSelectedComponent()).getHistory() == null) {
/* 299 */           getNewButtons().remove("history");
/*     */         }
/* 301 */         resizeVehDataTable();
/*     */         
/*     */         return;
/*     */       } 
/* 305 */       SummaryTabPanel summaryTabPanel = new SummaryTabPanel(this, this.prevPanel);
/* 306 */       summaryTabPanel.setFlagProgrammingSequence(false);
/* 307 */       summaryTabPanel.handleRequest(req);
/* 308 */       this.summaryTabPanelList.add(summaryTabPanel);
/* 309 */       this.summaryTableModel = summaryTabPanel.getSummaryTableModel();
/* 310 */       this.descriptTableModel = summaryTabPanel.getDescriptTableModel();
/* 311 */       if (((DisplaySummaryRequest)req).getSummary() != null) {
/*     */ 
/*     */         
/* 314 */         String controller = ControllerUtils.getShortNameController(System.getProperty(CommonAttribute.CONTROLLER.toString()));
/* 315 */         getJTabbedPane().addTab(controller, (Icon)null, (Component)summaryTabPanel, (String)null);
/* 316 */         getMainPanel().add(getJTabbedPane());
/* 317 */         getMainPanel().add(getVehDataPanel());
/* 318 */         add(getMainPanel(), "Center");
/* 319 */         resizeVehDataTable();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 324 */         String controller = ControllerUtils.getShortNameController(System.getProperty(CommonAttribute.CONTROLLER.toString()));
/* 325 */         getJTabbedPane().addTab(controller, (Icon)null, (Component)summaryTabPanel, (String)null);
/* 326 */         getMainPanel().add(getJTabbedPane());
/* 327 */         getMainPanel().add(getVehDataPanel());
/* 328 */         add(getMainPanel(), "Center");
/* 329 */         resizeVehDataTable();
/*     */       } 
/* 331 */     } catch (Exception e) {
/* 332 */       log.error("handleRequest() method, -exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vector getVehicleTableColumnsName() {
/* 337 */     Vector<String> nameHeaderVehicle = new Vector();
/*     */     
/*     */     try {
/* 340 */       nameHeaderVehicle.add(resourceProvider.getLabel(locale, "summaryScreen.vehDataTable.attribute"));
/* 341 */       nameHeaderVehicle.add(resourceProvider.getLabel(locale, "summaryScreen.vehDataTable.value"));
/*     */     }
/* 343 */     catch (Throwable e) {
/* 344 */       log.error("unable to load vehicle table columns name, -exception: " + e.getMessage());
/*     */     } 
/* 346 */     return nameHeaderVehicle;
/*     */   }
/*     */   
/*     */   public TableModel getVehicleDataTableModel() {
/* 350 */     return this.vehDataTableModel;
/*     */   }
/*     */   
/*     */   public TableModel getSummaryTableModel() {
/* 354 */     SummaryTabPanel summaryTabPanel = (SummaryTabPanel)getJTabbedPane().getSelectedComponent();
/* 355 */     return summaryTabPanel.getSummaryTableModel();
/*     */   }
/*     */ 
/*     */   
/*     */   public TableModel getDescriptionTableModel() {
/* 360 */     SummaryTabPanel summaryTabPanel = (SummaryTabPanel)getJTabbedPane().getSelectedComponent();
/* 361 */     return summaryTabPanel.getDescriptTableModel();
/*     */   }
/*     */   
/*     */   protected void resizeVehDataTable() {
/* 365 */     resizeRowHeight(getVehDataPanel(), this.vehDataTable);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resizeRowHeight(JPanel panel, JTable table) {
/*     */     try {
/* 371 */       int lastColIndex = table.getColumnCount() - 1;
/*     */       
/* 373 */       int table_width = table.getWidth();
/* 374 */       int lastColWidth = 0;
/*     */       
/* 376 */       if (table_width == 0) {
/* 377 */         table_width = (int)panel.getPreferredSize().getWidth() - 40;
/*     */       }
/* 379 */       lastColWidth = table_width;
/* 380 */       for (int i = 0; i < table.getRowCount(); i++) {
/*     */         
/* 382 */         View view = BasicHTML.createHTMLView(new JLabel(), table.getValueAt(i, lastColIndex).toString());
/* 383 */         view.setSize((lastColWidth - 5), 10000.0F);
/* 384 */         int height = (int)view.getMinimumSpan(1);
/* 385 */         table.setRowHeight(i, height + 5);
/*     */       } 
/* 387 */     } catch (Exception e) {
/* 388 */       log.error("Exception in resizeRowHeight() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SummaryPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */