/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.ModuleInfoData;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.CustomizeTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.PSTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.PartNumberTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.PseudoTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeHtmlRender;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ViewerHTMLDialog;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.javio.webwindow.WebWindow;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.text.View;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SummaryTabPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  55 */   protected JPanel mainPanel = null;
/*     */   
/*  57 */   protected JPanel vehDataPanel = null;
/*     */   
/*  59 */   protected JPanel modulePanel = null;
/*     */   
/*  61 */   protected JPanel descriptionPanel = null;
/*     */   
/*  63 */   protected JPanel enginePanel = null;
/*     */   
/*  65 */   protected JPanel titlePanel = null;
/*     */   
/*  67 */   protected JTable descriptionTable = null;
/*     */   
/*  69 */   protected CustomizeHtmlRender webWindowHTML = null;
/*     */   
/*  71 */   protected JTable vehDataTable = null;
/*     */   
/*  73 */   protected JTable moduleTable = null;
/*     */   
/*  75 */   protected List history = null;
/*     */   
/*  77 */   protected Vector moduleInfo = null;
/*     */   
/*  79 */   protected static Locale locale = null;
/*     */   
/*  81 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  83 */   protected List newButtons = null;
/*     */   
/*  85 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  87 */   protected static final Logger log = Logger.getLogger(SummaryPanel.class);
/*     */   
/*  89 */   public SummaryPanel parent = null;
/*     */   
/*  91 */   protected String appName = null;
/*     */ 
/*     */   
/*     */   protected boolean flagPS = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public SummaryTabPanel(SummaryPanel parent, BaseCustomizeJPanel prevPanel) {
/*  99 */     super(prevPanel);
/* 100 */     this.parent = parent;
/* 101 */     initialize();
/* 102 */     this.newButtons = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/* 112 */     setLayout(new BorderLayout());
/* 113 */     add(getMainPanel(), "Center");
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/* 117 */     if (this.mainPanel == null) {
/*     */       
/*     */       try {
/* 120 */         this.mainPanel = new JPanel();
/* 121 */         this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 122 */         this.mainPanel.add(getEnginePanel());
/*     */       
/*     */       }
/* 125 */       catch (Throwable e) {
/* 126 */         log.error("getMainPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 129 */     return this.mainPanel;
/*     */   }
/*     */   
/*     */   private JPanel getEnginePanel() {
/*     */     try {
/* 134 */       if (this.enginePanel == null) {
/* 135 */         this.enginePanel = new JPanel();
/* 136 */         this.enginePanel.setLayout(new BoxLayout(this.enginePanel, 0));
/* 137 */         this.enginePanel.setPreferredSize(new Dimension(SwingUtils.getScreenWidth(), SwingUtils.getScreenHeight()));
/*     */         
/* 139 */         getModulePanel().setPreferredSize(new Dimension(SwingUtils.getScreenWidth() * 6 / 10, SwingUtils.getScreenHeight() * 7 / 10));
/* 140 */         this.enginePanel.add(getModulePanel());
/* 141 */         getDescriptionPanel().setPreferredSize(new Dimension(SwingUtils.getScreenWidth() * 4 / 10, SwingUtils.getScreenHeight() * 7 / 10));
/* 142 */         this.enginePanel.add(getDescriptionPanel());
/*     */       }
/*     */     
/* 145 */     } catch (Throwable e) {
/* 146 */       log.error("getEnginePanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 148 */     return this.enginePanel;
/*     */   }
/*     */   
/*     */   private JPanel getModulePanel() {
/* 152 */     if (this.modulePanel == null) {
/*     */       
/*     */       try {
/* 155 */         this.modulePanel = new JPanel(new BorderLayout());
/* 156 */         this.moduleTable = new JTable() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public TableCellEditor getCellEditor(int row, int col) {
/* 160 */               return null;
/*     */             }
/*     */           };
/* 163 */         this.moduleTable.setShowGrid(false);
/* 164 */         this.moduleTable.setName("Summary");
/* 165 */         this.moduleTable.setSelectionMode(0);
/* 166 */         this.moduleTable.getTableHeader().setReorderingAllowed(false);
/*     */         
/* 168 */         JScrollPane scrollPane = new JScrollPane(this.moduleTable);
/* 169 */         scrollPane.getViewport().setBackground(Color.white);
/* 170 */         scrollPane.getViewport().setOpaque(true);
/* 171 */         this.modulePanel.add(scrollPane, "Center");
/*     */       }
/* 173 */       catch (Throwable e) {
/* 174 */         log.error("getSoftwarePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 177 */     return this.modulePanel;
/*     */   }
/*     */   
/*     */   private JPanel getDescriptionPanel() {
/* 181 */     if (this.descriptionPanel == null) {
/*     */       try {
/* 183 */         JPanel contentPanel = new JPanel(new BorderLayout());
/* 184 */         this.descriptionPanel = new JPanel(new BorderLayout());
/* 185 */         this.descriptionTable = new JTable();
/* 186 */         this.descriptionTable.setShowGrid(false);
/* 187 */         this.descriptionTable.setEnabled(false);
/* 188 */         JScrollPane scrollPane = new JScrollPane(this.descriptionTable);
/* 189 */         scrollPane.getViewport().setBackground(Color.green);
/* 190 */         scrollPane.getViewport().setOpaque(true);
/* 191 */         scrollPane.getViewport().setPreferredSize(new Dimension(0, 0));
/* 192 */         scrollPane.setVerticalScrollBarPolicy(21);
/* 193 */         this.descriptionTable.setDefaultRenderer(Object.class, (TableCellRenderer)new PseudoTableCellRenderer());
/* 194 */         scrollPane.setBorder(BorderFactory.createEmptyBorder());
/* 195 */         contentPanel.add(scrollPane, "North");
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
/* 208 */         this.webWindowHTML = ViewerHTMLDialog.getWebWindow(null, null);
/* 209 */         this.webWindowHTML.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 210 */         Border border = BorderFactory.createLineBorder(Color.gray);
/* 211 */         border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 1, 1, 1), border);
/* 212 */         this.descriptionPanel.setBorder(border);
/* 213 */         contentPanel.add((Component)this.webWindowHTML, "Center");
/* 214 */         this.descriptionPanel.add(contentPanel, "Center");
/*     */       }
/* 216 */       catch (Throwable e) {
/* 217 */         log.error("getDescriptionTable() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 220 */     return this.descriptionPanel;
/*     */   }
/*     */   
/*     */   private JPanel getVehDataPanel() {
/* 224 */     if (this.vehDataPanel == null) {
/*     */       
/*     */       try {
/* 227 */         this.vehDataPanel = new JPanel(new BorderLayout());
/* 228 */         this.vehDataPanel.setBorder(new TitledBorder(null, null, 0, 0, null));
/* 229 */         this.vehDataTable = new JTable();
/* 230 */         this.vehDataTable.setEnabled(false);
/* 231 */         this.vehDataTable.setShowGrid(false);
/* 232 */         this.vehDataTable.setShowHorizontalLines(true);
/* 233 */         this.vehDataTable.getTableHeader().setReorderingAllowed(false);
/* 234 */         JScrollPane scrollPane = new JScrollPane(this.vehDataTable);
/* 235 */         scrollPane.getViewport().setBackground(Color.white);
/* 236 */         scrollPane.getViewport().setOpaque(true);
/* 237 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "summaryScreen.vehDataTable.title"));
/* 238 */         lbl.setFont(lbl.getFont().deriveFont(1));
/* 239 */         this.vehDataPanel.add(lbl, "North");
/* 240 */         this.vehDataPanel.add(scrollPane, "Center");
/* 241 */         this.vehDataPanel.setPreferredSize(new Dimension(SwingUtils.getScreenWidth(), SwingUtils.getScreenHeight() * 3 / 10));
/*     */       }
/* 243 */       catch (Throwable e) {
/* 244 */         log.error("unable to load resource, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 247 */     return this.vehDataPanel;
/*     */   }
/*     */   
/*     */   private Vector getSoftwareTableColumnsName(String appName) {
/* 251 */     Vector<String> nameHeaderSoftware = new Vector();
/*     */     try {
/* 253 */       if (appName.equalsIgnoreCase("nao")) {
/* 254 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.controller"));
/* 255 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.id"));
/* 256 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.current"));
/* 257 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.selected"));
/* 258 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.description"));
/*     */       }
/* 260 */       else if (appName.equalsIgnoreCase("gme")) {
/* 261 */         nameHeaderSoftware.add("");
/* 262 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.current-ecu"));
/* 263 */         nameHeaderSoftware.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.select-ecu"));
/*     */       }
/*     */     
/* 266 */     } catch (Throwable e) {
/* 267 */       log.error("unable to load module table columns name, -exception: " + e.getMessage());
/*     */     } 
/* 269 */     return nameHeaderSoftware;
/*     */   }
/*     */   
/*     */   private Vector getDescriptTableColumnsName(String appName) {
/* 273 */     Vector<String> nameHeaderDescription = new Vector();
/*     */     try {
/* 275 */       if (appName.equalsIgnoreCase("nao")) {
/* 276 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.controller"));
/* 277 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.id"));
/* 278 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.current"));
/* 279 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.selected"));
/* 280 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.descriptionTable.description"));
/*     */       }
/* 282 */       else if (appName.equalsIgnoreCase("gme")) {
/* 283 */         nameHeaderDescription.add(resourceProvider.getLabel(locale, "summaryScreen.descriptionTable.description"));
/*     */       }
/*     */     
/* 286 */     } catch (Throwable e) {
/* 287 */       log.error("unable to load module table columns name, -exception: " + e.getMessage());
/*     */     } 
/* 289 */     return nameHeaderDescription;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 294 */       setRequestGroup(req.getRequestGroup());
/*     */       
/* 296 */       Summary summary = ((DisplaySummaryRequest)req).getSummary();
/* 297 */       if (summary != null) {
/*     */ 
/*     */         
/* 300 */         this.appName = "gme";
/* 301 */         DefaultTableModel moduleModel = new DefaultTableModel() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 305 */               return false;
/*     */             }
/*     */           };
/* 308 */         moduleModel = (DefaultTableModel)((DisplaySummaryRequest)req).getModuleData();
/*     */         
/* 310 */         if (moduleModel != null) {
/* 311 */           moduleModel.setColumnIdentifiers(getSoftwareTableColumnsName("gme"));
/* 312 */           this.moduleTable.setModel(moduleModel);
/*     */         } 
/*     */         
/* 315 */         DefaultTableModel descriptModel = (DefaultTableModel)((DisplaySummaryRequest)req).getDescriptionData();
/* 316 */         if (descriptModel != null) {
/* 317 */           descriptModel.setColumnIdentifiers(getDescriptTableColumnsName("gme"));
/* 318 */           this.descriptionTable.setModel(descriptModel);
/* 319 */           ViewerHTMLDialog.loadHTML((WebWindow)this.webWindowHTML, fixHTML((String)descriptModel.getValueAt(0, 0)));
/*     */         } 
/*     */         
/* 322 */         ModuleInfoData modInfoData = new ModuleInfoData(summary);
/* 323 */         this.moduleInfo = modInfoData.getModuleInfoData(summary);
/* 324 */         if (this.moduleInfo != null && this.moduleInfo.size() != 0) {
/* 325 */           this.parent.getNewButtons().add("moduleInfo");
/*     */         }
/*     */ 
/*     */         
/* 329 */         this.history = summary.getHistory();
/* 330 */         if (this.history != null) {
/* 331 */           this.parent.getNewButtons().add("history");
/*     */         }
/*     */       } else {
/*     */         
/* 335 */         this.appName = "nao";
/* 336 */         DefaultTableModel moduleModel = (DefaultTableModel)((DisplaySummaryRequest)req).getModuleData();
/* 337 */         if (moduleModel != null) {
/* 338 */           Vector column = getSoftwareTableColumnsName("nao");
/* 339 */           moduleModel.setColumnIdentifiers(column);
/* 340 */           this.moduleTable.setModel(moduleModel);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 350 */       setRenderer();
/* 351 */       resize();
/*     */       
/* 353 */       if (summary == null) {
/* 354 */         getDescriptionPanel().setVisible(false);
/*     */       }
/* 356 */     } catch (Exception e) {
/* 357 */       log.error("handleRequest() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String fixHTML(String html) {
/* 363 */     html = StringUtilities.replace(html, "&lt;a href=&quot;", "<a href=\"");
/* 364 */     html = StringUtilities.replace(html, "&quot;&gt;", "\">");
/* 365 */     html = StringUtilities.replace(html, "&lt;/a&gt;", "</a>");
/* 366 */     html = StringUtilities.replace(html, "\n", "<br/>");
/* 367 */     return html;
/*     */   }
/*     */   
/*     */   public void onResizeForm() {
/* 371 */     resize();
/*     */   }
/*     */   protected void setRenderer() {
/*     */     try {
/*     */       PSTableCellRenderer pSTableCellRenderer;
/* 376 */       DefaultTableModel model = (DefaultTableModel)this.moduleTable.getModel();
/*     */       
/* 378 */       if (model.getRowCount() == 0) {
/*     */         return;
/*     */       }
/* 381 */       CustomizeTableCellRenderer customizeTableCellRenderer = new CustomizeTableCellRenderer();
/* 382 */       if (isProgrammingSequence()) {
/* 383 */         pSTableCellRenderer = new PSTableCellRenderer();
/*     */       }
/*     */       
/* 386 */       if (this.appName.equalsIgnoreCase("nao")) {
/* 387 */         for (int i = 0; i < this.moduleTable.getColumnCount(); i++) {
/* 388 */           this.moduleTable.getColumnModel().getColumn(i).setCellRenderer((TableCellRenderer)pSTableCellRenderer);
/*     */         }
/* 390 */         int index = this.moduleTable.getColumnModel().getColumnIndex(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.current"));
/* 391 */         this.moduleTable.getColumnModel().getColumn(index).setCellRenderer((TableCellRenderer)new PartNumberTableCellRenderer());
/* 392 */         index = this.moduleTable.getColumnModel().getColumnIndex(resourceProvider.getLabel(locale, "summaryScreen.moduleTable.selected"));
/* 393 */         this.moduleTable.getColumnModel().getColumn(index).setCellRenderer((TableCellRenderer)new PartNumberTableCellRenderer());
/*     */       } else {
/* 395 */         this.moduleTable.setDefaultRenderer(Object.class, (TableCellRenderer)new CustomizeTableCellRenderer());
/*     */       }
/*     */     
/* 398 */     } catch (Exception e) {
/* 399 */       log.debug("setRenderer() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resizeModuleDataTable() {
/*     */     try {
/* 405 */       DefaultTableModel model = (DefaultTableModel)this.moduleTable.getModel();
/*     */       
/* 407 */       if (model.getRowCount() == 0) {
/*     */         return;
/*     */       }
/* 410 */       int table_width = this.moduleTable.getWidth();
/* 411 */       if (table_width == 0) {
/* 412 */         if (this.appName.equalsIgnoreCase("nao")) {
/* 413 */           table_width = (int)getEnginePanel().getPreferredSize().getWidth() - 40;
/*     */         } else {
/*     */           
/* 416 */           table_width = (int)getModulePanel().getPreferredSize().getWidth();
/*     */         } 
/*     */       }
/*     */       
/* 420 */       TableUtilities.setAllColumnsMaxMin(this.moduleTable, table_width);
/* 421 */       int lastColIndex = this.moduleTable.getColumnCount() - 1;
/* 422 */       int lastColWidth = this.moduleTable.getColumnModel().getColumn(lastColIndex).getMinWidth();
/*     */ 
/*     */       
/* 425 */       for (int i = 0; i < this.moduleTable.getRowCount(); i++)
/*     */       {
/* 427 */         View view = BasicHTML.createHTMLView(new JLabel(), this.moduleTable.getValueAt(i, lastColIndex).toString());
/* 428 */         view.setSize((lastColWidth - 5), 10000.0F);
/* 429 */         int height = (int)view.getMinimumSpan(1);
/* 430 */         this.moduleTable.setRowHeight(i, height + 5);
/*     */       }
/*     */     
/* 433 */     } catch (Exception e) {
/* 434 */       log.debug("resizeModuleDataTable() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resizeDescriptionDataTable() {
/* 439 */     resizeRowHeight(getDescriptionPanel(), this.descriptionTable);
/*     */   }
/*     */   
/*     */   protected void resizeVehDataTable() {
/* 443 */     resizeRowHeight(getVehDataPanel(), this.vehDataTable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resizeRowHeight(JPanel panel, JTable table) {
/*     */     try {
/* 450 */       int lastColIndex = table.getColumnCount() - 1;
/*     */       
/* 452 */       int table_width = table.getWidth();
/* 453 */       int lastColWidth = 0;
/*     */       
/* 455 */       if (table_width == 0) {
/* 456 */         table_width = (int)panel.getPreferredSize().getWidth() - 40;
/*     */       }
/* 458 */       lastColWidth = table_width;
/* 459 */       for (int i = 0; i < table.getRowCount(); i++)
/*     */       {
/* 461 */         View view = BasicHTML.createHTMLView(new JLabel(), table.getValueAt(i, lastColIndex).toString());
/* 462 */         view.setSize((lastColWidth - 5), 10000.0F);
/* 463 */         int height = (int)view.getMinimumSpan(1);
/* 464 */         table.setRowHeight(i, height + 5);
/*     */       }
/*     */     
/* 467 */     } catch (Exception e) {
/* 468 */       log.error("Exception in resizeRowHeight() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resize() {
/* 473 */     int defaultRowHeight = this.moduleTable.getRowHeight();
/* 474 */     resizeModuleDataTable();
/* 475 */     int row = getEmptyRow();
/* 476 */     this.moduleTable.setRowHeight(row, defaultRowHeight);
/* 477 */     resizeDescriptionDataTable();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getEmptyRow() {
/* 482 */     for (int rowIndex = 0; rowIndex < this.moduleTable.getRowCount(); rowIndex++) {
/* 483 */       boolean isRowEmpty = true;
/* 484 */       for (int colIndex = 0; colIndex < this.moduleTable.getColumnCount(); colIndex++) {
/* 485 */         if (this.moduleTable.getModel().getValueAt(rowIndex, colIndex) != "") {
/* 486 */           isRowEmpty = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 490 */       if (isRowEmpty)
/* 491 */         return rowIndex; 
/*     */     } 
/* 493 */     return -1;
/*     */   }
/*     */   
/*     */   public void setFlagProgrammingSequence(boolean flag) {
/* 497 */     this.flagPS = flag;
/*     */   }
/*     */   
/*     */   protected boolean isProgrammingSequence() {
/* 501 */     return this.flagPS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {}
/*     */ 
/*     */   
/*     */   public TableModel getVehicleDataTableModel() {
/* 510 */     return this.vehDataTable.getModel();
/*     */   }
/*     */   
/*     */   public TableModel getSummaryTableModel() {
/* 514 */     return this.moduleTable.getModel();
/*     */   }
/*     */   
/*     */   public TableModel getDescriptTableModel() {
/* 518 */     return this.descriptionTable.getModel();
/*     */   }
/*     */   
/*     */   public List getHistory() {
/* 522 */     return this.history;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getModuleInfo() {
/* 527 */     return this.moduleInfo;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SummaryTabPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */