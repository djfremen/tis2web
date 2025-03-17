/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.CalibrationPanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ProgrammingDataController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.TabPanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.CalibrationTreeModel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.CustomizeTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.ListRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.PartNumberTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJList;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.text.View;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CalibrationTabPanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements ProgrammingDataController, CalibrationPanelController {
/*     */   private static final long serialVersionUID = 1L;
/*  56 */   protected JPanel mainPanel = null;
/*     */   
/*  58 */   private JTable historyTable = null;
/*     */   
/*  60 */   private JPanel historyPanel = null;
/*     */   
/*  62 */   private JPanel modulesPanel = null;
/*     */   
/*     */   private CustomizeJList modulesList;
/*     */   
/*     */   private CalibrationTreePanel calibTreePanel;
/*     */   
/*  68 */   protected Map models = new HashMap<Object, Object>();
/*     */   
/*  70 */   protected List modules = null;
/*     */   
/*     */   protected Controller controller;
/*     */   
/*     */   protected TabPanelController tabPanelController;
/*     */   
/*     */   protected JScrollPane scroll;
/*     */   
/*     */   protected JScrollPane scrollTable;
/*     */   
/*  80 */   protected static Locale locale = null;
/*     */   
/*  82 */   protected List newButtons = null;
/*     */   
/*  84 */   protected static ImageIcon ICON_CONTROLLER_SELECTED = null;
/*     */   
/*  86 */   protected static ImageIcon ICON_CONTROLLER_NOSELECTED = null;
/*     */   
/*  88 */   protected static JTabbedPane jTabbedPane = null;
/*     */   
/*  90 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  92 */   private static final Logger log = Logger.getLogger(CalibrationSelectPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CalibrationTabPanel(Controller controller, TabPanelController tabController, BaseCustomizeJPanel prevPanel) {
/*  98 */     super(prevPanel);
/*  99 */     this.controller = controller;
/* 100 */     this.tabPanelController = tabController;
/* 101 */     createButtonsList();
/* 102 */     loadImages();
/* 103 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/* 112 */     FontUtils.setDefaultFont(FontUtils.getSpecialFont());
/* 113 */     setLayout(new BorderLayout());
/* 114 */     add(getMainPanel(), "Center");
/*     */   }
/*     */   
/*     */   private void loadImages() {
/*     */     try {
/* 119 */       ICON_CONTROLLER_SELECTED = new ImageIcon(CalibrationTreePanel.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/controller-selected.gif"));
/* 120 */       ICON_CONTROLLER_NOSELECTED = new ImageIcon(CalibrationTreePanel.class.getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/controller-noselected.gif"));
/*     */     }
/* 122 */     catch (Exception e) {
/* 123 */       log.warn("Image Star.gif, Transparent.gif not found, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenPanel(int index) {
/* 133 */     if (this.modulesList.getSelectedIndex() != -1) {
/* 134 */       this.modulesList.ensureIndexIsVisible(this.modulesList.getSelectedIndex());
/*     */     }
/* 136 */     if (allModuleSelected()) {
/* 137 */       this.tabPanelController.changeTabIcon(index, ICON_CONTROLLER_SELECTED);
/*     */     } else {
/*     */       
/* 140 */       this.tabPanelController.changeTabIcon(index, ICON_CONTROLLER_NOSELECTED);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onResizeForm() {
/* 145 */     resizeHistoryDataTable();
/*     */   }
/*     */   
/*     */   private JPanel getModulesPanel() {
/* 149 */     if (this.modulesPanel == null) {
/*     */       try {
/* 151 */         this.modulesPanel = new JPanel(new BorderLayout());
/* 152 */         this.modulesPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 325));
/*     */         
/* 154 */         this.modulesList = new CustomizeJList();
/* 155 */         this.modulesList.addListSelectionListener(new ListSelectionListener() {
/*     */               public void valueChanged(ListSelectionEvent evt) {
/* 157 */                 CalibrationTabPanel.this.onSelectedModulesListAction();
/*     */               }
/*     */             });
/* 160 */         this.modulesList.setCellRenderer((ListCellRenderer)new ListRenderer());
/* 161 */         this.scroll = new JScrollPane((Component)this.modulesList);
/* 162 */         this.scroll.setViewportView((Component)this.modulesList);
/* 163 */         this.modulesPanel.add(this.scroll, "Center");
/*     */         
/* 165 */         JPanel lblPanel = new JPanel();
/* 166 */         lblPanel.setLayout(new BorderLayout());
/* 167 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.calibration"));
/* 168 */         lbl.setFont(lbl.getFont().deriveFont(1));
/* 169 */         lblPanel.add(lbl, "Center");
/* 170 */         lblPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 40));
/* 171 */         this.modulesPanel.add(lblPanel, "West");
/*     */       }
/* 173 */       catch (Exception e) {
/* 174 */         log.error("getModulesPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 177 */     return this.modulesPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTable getHistoryTable() {
/* 188 */     if (this.historyTable == null) {
/*     */       
/*     */       try {
/* 191 */         this.historyTable = new JTable();
/* 192 */         this.historyTable.setShowGrid(false);
/* 193 */         clearHistoryTable();
/* 194 */         this.historyTable.setEnabled(false);
/* 195 */         this.historyTable.getTableHeader().setReorderingAllowed(false);
/* 196 */       } catch (Exception e) {
/* 197 */         log.error("getHistoryTable() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 200 */     return this.historyTable;
/*     */   }
/*     */   
/*     */   private JPanel getHistoryPanel() {
/* 204 */     if (this.historyPanel == null) {
/*     */       try {
/* 206 */         this.historyPanel = new JPanel(new BorderLayout());
/* 207 */         this.historyPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "calibrationScreen.historyPanel.title"), 0, 0, this.historyPanel.getFont().deriveFont(1)));
/* 208 */         this.scrollTable = new JScrollPane(getHistoryTable());
/* 209 */         this.scrollTable.getViewport().setBackground(Color.white);
/* 210 */         this.scrollTable.getViewport().setOpaque(true);
/* 211 */         this.scrollTable.revalidate();
/* 212 */         this.historyPanel.add(this.scrollTable);
/* 213 */       } catch (Exception e) {
/* 214 */         log.error("getHistoryPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 217 */     return this.historyPanel;
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/* 221 */     if (this.mainPanel == null) {
/*     */       try {
/* 223 */         this.mainPanel = new JPanel();
/*     */         
/* 225 */         this.calibTreePanel = new CalibrationTreePanel(this, this);
/* 226 */         this.mainPanel.setLayout(new GridBagLayout());
/* 227 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/* 229 */         c.insets = new Insets(0, 10, 2, 10);
/*     */         
/* 231 */         c.gridx = 0;
/* 232 */         c.gridy = 0;
/* 233 */         c.gridheight = 2;
/* 234 */         c.weightx = 1.0D;
/* 235 */         c.weighty = 1.0D;
/* 236 */         c.fill = 1;
/* 237 */         this.mainPanel.add(getModulesPanel(), c);
/*     */         
/* 239 */         c.gridx = 0;
/* 240 */         c.gridy = 2;
/* 241 */         c.gridheight = 3;
/* 242 */         c.weightx = 1.0D;
/* 243 */         c.weighty = 1.0D;
/* 244 */         c.fill = 1;
/* 245 */         this.mainPanel.add(this.calibTreePanel, c);
/*     */         
/* 247 */         c.gridx = 0;
/* 248 */         c.gridy = 6;
/* 249 */         c.gridheight = 2;
/* 250 */         c.weightx = 1.0D;
/* 251 */         c.weighty = 1.0D;
/* 252 */         c.fill = 1;
/* 253 */         this.mainPanel.add(getHistoryPanel(), c);
/*     */       }
/* 255 */       catch (Exception e) {
/* 256 */         log.error("getMainPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 259 */     return this.mainPanel;
/*     */   }
/*     */   
/*     */   private void clearHistoryTable() {
/* 263 */     Vector<String> columns = new Vector();
/* 264 */     columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.partNumber"));
/* 265 */     columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.description"));
/*     */     
/* 267 */     DefaultTableModel model = new DefaultTableModel(new Vector(), columns);
/* 268 */     getHistoryTable().setModel(model);
/* 269 */     getHistoryTable().invalidate();
/*     */   }
/*     */   
/*     */   private void onSelectedModulesListAction() {
/*     */     try {
/* 274 */       DisplayAdapter selected = (DisplayAdapter)this.modulesList.getSelectedValue();
/* 275 */       this.calibTreePanel.setModuleTyp(((Module)selected.getAdaptee()).isPROM());
/* 276 */       CalibrationTreeModel module = (CalibrationTreeModel)this.models.get(selected.getAdaptee());
/*     */       
/* 278 */       if (((Module)selected.getAdaptee()).getCurrentCalibration() != null && ((Module)selected.getAdaptee()).getCurrentCalibration().startsWith("*")) {
/* 279 */         this.controller.setInfoOnBarStatus(resourceProvider.getMessage(locale, "sps.info.invalid-current-calibration"));
/*     */       } else {
/*     */         
/* 282 */         this.controller.setInfoOnBarStatus(" ");
/*     */       } 
/*     */       
/* 285 */       this.calibTreePanel.setCurrentCalibration(module);
/* 286 */       this.calibTreePanel.cleanBulletinsList();
/* 287 */       clearHistoryTable();
/* 288 */       this.calibTreePanel.setTreeModel(module);
/* 289 */     } catch (Exception e) {
/* 290 */       log.error("onSelectedModulesListAction, - exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 297 */       ProgrammingDataSelectionRequest request = (ProgrammingDataSelectionRequest)req;
/* 298 */       List<Value> modules = ((ProgrammingDataSelectionRequest)req).getModules();
/*     */ 
/*     */       
/* 301 */       if (modules.size() == 1) {
/* 302 */         CalibrationTreeModel module = new CalibrationTreeModel(modules.get(0));
/*     */         
/* 304 */         this.controller.handleProgrammingSelection();
/* 305 */         this.models.put(modules.get(0), module);
/* 306 */         this.modulesList.setRequest((SelectionRequest)request, false);
/* 307 */         this.modulesList.setSelectedIndex(0);
/*     */         
/*     */         return;
/*     */       } 
/* 311 */       boolean hasNoModelDefault = false;
/*     */       
/* 313 */       int start = 1;
/* 314 */       if (!((Module)modules.get(0)).getID().equals("0"))
/* 315 */         start = 0; 
/*     */       int i;
/* 317 */       for (i = start; i < modules.size(); i++) {
/* 318 */         CalibrationTreeModel module = new CalibrationTreeModel(modules.get(i));
/* 319 */         this.models.put(modules.get(i), module);
/*     */       } 
/*     */       
/* 322 */       this.controller.handleProgrammingSelection();
/*     */       
/* 324 */       for (i = start; i < modules.size(); i++) {
/* 325 */         CalibrationTreeModel module = (CalibrationTreeModel)this.models.get(modules.get(i));
/* 326 */         if (!module.hasDefaultSelection()) {
/* 327 */           hasNoModelDefault = true;
/* 328 */           if (start == 1) {
/* 329 */             this.modulesList.setRequest((SelectionRequest)request, true);
/*     */           } else {
/* 331 */             this.modulesList.setRequest((SelectionRequest)request, false);
/* 332 */           }  this.modulesList.setSelectedIndex(i - 1);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 338 */       if (!hasNoModelDefault) {
/* 339 */         if (start == 1) {
/* 340 */           this.modulesList.setRequest((SelectionRequest)request, true);
/*     */         } else {
/* 342 */           this.modulesList.setRequest((SelectionRequest)request, false);
/* 343 */         }  this.modulesList.setSelectedIndex(0);
/*     */       }
/*     */     
/* 346 */     } catch (Exception e) {
/* 347 */       log.error("handleRequest, - exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean allModuleSelected() {
/* 353 */     for (int i = 0; i < this.modulesList.getModel().getSize(); i++) {
/* 354 */       CalibrationTreeModel module = new CalibrationTreeModel((Value)((DisplayAdapter)this.modulesList.getModel().getElementAt(i)).getAdaptee());
/* 355 */       if (module.getSelectedNode() == null) {
/* 356 */         return false;
/*     */       }
/*     */     } 
/* 359 */     return true;
/*     */   }
/*     */   
/*     */   public void handleProgrammingSelection() {
/* 363 */     this.controller.handleProgrammingSelection();
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillHistoryTable(Vector historyData, Vector columns) {
/*     */     try {
/* 369 */       DefaultTableModel model = new DefaultTableModel(historyData, columns);
/* 370 */       getHistoryTable().setModel(model);
/* 371 */       setRenderer();
/* 372 */       resizeHistoryDataTable();
/*     */     }
/* 374 */     catch (Exception e) {
/* 375 */       log.error("fillHistoryTable, - exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createButtonsList() {
/*     */     try {
/* 384 */       this.newButtons = new ArrayList();
/* 385 */       this.newButtons.add("ecuData");
/* 386 */     } catch (Exception e) {
/* 387 */       log.warn("unable to create button list, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void requestBulletinDisplay(String bulletin) {
/* 392 */     this.controller.requestBulletinDisplay(bulletin);
/*     */   }
/*     */   
/*     */   public void repaintModuleList() {
/* 396 */     this.modulesList.setCellRenderer((ListCellRenderer)new ListRenderer());
/*     */     
/* 398 */     if (allModuleSelected()) {
/* 399 */       this.tabPanelController.changeSelectedTabIcon(ICON_CONTROLLER_SELECTED);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRenderer() {
/*     */     try {
/* 406 */       DefaultTableModel model = (DefaultTableModel)getHistoryTable().getModel();
/* 407 */       if (model.getRowCount() == 0) {
/*     */         return;
/*     */       }
/* 410 */       getHistoryTable().getColumnModel().getColumn(0).setCellRenderer((TableCellRenderer)new PartNumberTableCellRenderer());
/* 411 */       for (int i = 1; i < getHistoryTable().getColumnCount(); i++) {
/* 412 */         getHistoryTable().getColumnModel().getColumn(i).setCellRenderer((TableCellRenderer)new CustomizeTableCellRenderer());
/*     */       }
/* 414 */       getHistoryTable().validate();
/*     */     }
/* 416 */     catch (Exception e) {
/* 417 */       log.debug("setRenderer() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resizeHistoryDataTable() {
/*     */     try {
/* 423 */       DefaultTableModel model = (DefaultTableModel)getHistoryTable().getModel();
/*     */       
/* 425 */       if (model.getRowCount() == 0) {
/*     */         return;
/*     */       }
/* 428 */       int table_width = getHistoryTable().getWidth();
/* 429 */       if (table_width == 0) {
/* 430 */         table_width = SwingUtils.getScreenWidth() - 15;
/*     */       }
/* 432 */       int lastColIndex = getHistoryTable().getColumnCount() - 1;
/* 433 */       TableUtilities.setAllColumnsMaxMin(getHistoryTable(), table_width);
/*     */       
/* 435 */       int lastColWidth = getHistoryTable().getColumnModel().getColumn(lastColIndex).getMinWidth();
/*     */       
/* 437 */       for (int i = 0; i < getHistoryTable().getRowCount(); i++) {
/* 438 */         View view = BasicHTML.createHTMLView(new JLabel(), getHistoryTable().getValueAt(i, lastColIndex).toString());
/* 439 */         view.setSize((lastColWidth - 5), 10000.0F);
/* 440 */         int height = (int)view.getMinimumSpan(1);
/* 441 */         getHistoryTable().setRowHeight(i, height + 5);
/*     */       }
/*     */     
/* 444 */     } catch (Exception e) {
/* 445 */       log.debug("resizeHistoryDataTable() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\CalibrationTabPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */