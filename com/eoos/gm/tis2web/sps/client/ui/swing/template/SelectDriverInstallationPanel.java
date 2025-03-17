/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.editor.ComboBoxEditor;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.ComboBoxRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTable;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Window;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SelectDriverInstallationPanel
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  43 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  45 */   private static final String DEVICE_COLUMNNAME = resourceProvider.getLabel(null, "deviceInstallScreen.deviceInstallTable.device");
/*     */   
/*  47 */   private static final String INSTALLED_VERSION_COLUMNNAME = resourceProvider.getLabel(null, "deviceInstallScreen.deviceInstallTable.installed-version");
/*     */   
/*  49 */   private static final String SERVER_VERSION_COLUMNNAME = resourceProvider.getLabel(null, "deviceInstallScreen.deviceInstallTable.server-version");
/*     */   
/*  51 */   private static final String INSTALL_COLUMNNAME = resourceProvider.getLabel(null, "deviceInstallScreen.deviceInstallTable.install");
/*     */   
/*  53 */   protected static Locale locale = null;
/*     */   
/*  55 */   private JPanel centerPanel = null;
/*     */   
/*  57 */   private JPanel northPanel = null;
/*     */   
/*  59 */   private JPanel southPanel = null;
/*     */   
/*  61 */   private JPanel selectDeviceInstallPanel = null;
/*     */   
/*  63 */   private JLabel titleLabel = null;
/*     */   
/*  65 */   private JButton cancelButton = new JButton(resourceProvider.getLabel(null, "cancel"));
/*     */   
/*  67 */   private JButton continueButton = new JButton(resourceProvider.getLabel(null, "continue"));
/*     */   
/*  69 */   private JTable table = null;
/*     */   
/*  71 */   private static final Logger log = Logger.getLogger(SelectDriverInstallationPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectDriverInstallationPanel(SPSFrame frame) {
/*  77 */     super((Frame)frame, frame.getTitle(), true);
/*  78 */     initialize();
/*  79 */     setSize(new Dimension((int)SwingUtils.getDialogHTML_Width(), (int)SwingUtils.getDialogHTML_Height()));
/*  80 */     setLocation(UIUtil.getCenterLocation(this, (Window)frame));
/*  81 */     setVisible(true);
/*  82 */     setModal(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  92 */     getContentPane().setLayout(new BorderLayout());
/*     */     
/*  94 */     getContentPane().add(getCenterPanel(), "Center");
/*  95 */     getContentPane().add(getNorthPanel(), "North");
/*  96 */     getContentPane().add(getSouthPanel(), "South");
/*  97 */     getSelectedInstallVersion();
/*     */   }
/*     */   
/*     */   private JPanel getNorthPanel() {
/* 101 */     if (this.northPanel == null) {
/*     */       try {
/* 103 */         this.northPanel = new JPanel();
/* 104 */         this.northPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/* 105 */         this.northPanel.setLayout(new GridBagLayout());
/* 106 */         GridBagConstraints northConstraints = new GridBagConstraints();
/* 107 */         this.titleLabel = new JLabel();
/* 108 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 109 */         this.titleLabel.setFont(new Font(this.titleLabel.getFont().getFontName(), 1, fontSize));
/* 110 */         this.northPanel.add(this.titleLabel, northConstraints);
/* 111 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/* 112 */         northConstraints.gridx = 1;
/* 113 */         northConstraints.gridy = 2;
/*     */       }
/* 115 */       catch (Throwable e) {
/* 116 */         log.error("getNorthPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/* 119 */     return this.northPanel;
/*     */   }
/*     */   
/*     */   private JPanel getSouthPanel() {
/* 123 */     if (this.southPanel == null) {
/*     */       try {
/* 125 */         this.southPanel = new JPanel();
/* 126 */         this.southPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/* 127 */         this.southPanel.setLayout(new GridBagLayout());
/* 128 */         GridBagConstraints northConstraints = new GridBagConstraints();
/* 129 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/* 130 */         this.southPanel.add(this.continueButton, northConstraints);
/* 131 */         this.southPanel.add(this.cancelButton, northConstraints);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 136 */       catch (Throwable e) {
/* 137 */         log.error("getSouthPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/* 140 */     return this.southPanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/* 144 */     if (this.centerPanel == null) {
/*     */       try {
/* 146 */         this.centerPanel = new JPanel();
/* 147 */         this.centerPanel.setLayout(new BorderLayout());
/* 148 */         this.centerPanel.add(getSelectDriverInstallPanel(), "Center");
/*     */       }
/* 150 */       catch (Throwable e) {
/* 151 */         log.error("getCenterPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 155 */     return this.centerPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getSelectDriverInstallPanel() {
/* 164 */     if (this.selectDeviceInstallPanel == null) {
/*     */       try {
/* 166 */         this.selectDeviceInstallPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Insets getInsets() {
/* 170 */               return new Insets(0, 20, 100, 20);
/*     */             }
/*     */           };
/* 173 */         this.selectDeviceInstallPanel.setLayout(new BorderLayout());
/*     */         
/* 175 */         this.selectDeviceInstallPanel.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/* 176 */         JScrollPane scrollPane = new JScrollPane(getDeviceInstallTable());
/* 177 */         this.selectDeviceInstallPanel.add(scrollPane, "Center");
/* 178 */         scrollPane.getViewport().setBackground(Color.white);
/* 179 */         scrollPane.getViewport().setOpaque(true);
/* 180 */       } catch (Throwable e) {
/* 181 */         log.error("getFirstListPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/* 184 */     return this.selectDeviceInstallPanel;
/*     */   }
/*     */   
/*     */   private JTable getDeviceInstallTable() {
/* 188 */     if (this.table == null) {
/*     */       
/*     */       try {
/* 191 */         final ArrayList<ComboBoxEditor> editors = new ArrayList(2);
/*     */         
/* 193 */         final ArrayList<ComboBoxRenderer> renderers = new ArrayList(2);
/* 194 */         String[] items1 = { "1.0.1", "1.1.1" };
/* 195 */         ComboBoxEditor dce1 = new ComboBoxEditor((Object[])items1);
/* 196 */         ComboBoxRenderer dcr1 = new ComboBoxRenderer((Object[])items1);
/* 197 */         editors.add(dce1);
/* 198 */         renderers.add(dcr1);
/*     */         
/* 200 */         String[] items2 = { "0.0.1", "0.1.1" };
/* 201 */         ComboBoxEditor dce2 = new ComboBoxEditor((Object[])items2);
/* 202 */         ComboBoxRenderer dcr2 = new ComboBoxRenderer((Object[])items2);
/* 203 */         editors.add(dce2);
/* 204 */         renderers.add(dcr2);
/*     */         
/* 206 */         this.table = (JTable)new CustomizeJTable()
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */ 
/*     */             
/*     */             public TableCellEditor getCellEditor(int row, int column) {
/* 212 */               int serverColIndex = getColumnModel().getColumnIndex(CustomizeJTable.resourceProvider.getLabel(SelectDriverInstallationPanel.locale, "deviceInstallScreen.deviceInstallTable.server-version"));
/* 213 */               if (column == serverColIndex) {
/* 214 */                 return editors.get(row);
/*     */               }
/* 216 */               return super.getCellEditor(row, column);
/*     */             }
/*     */             
/*     */             public TableCellRenderer getCellRenderer(int row, int column) {
/* 220 */               int serverColIndex = getColumnModel().getColumnIndex(SelectDriverInstallationPanel.SERVER_VERSION_COLUMNNAME);
/* 221 */               if (column == serverColIndex) {
/* 222 */                 return renderers.get(row);
/*     */               }
/* 224 */               return super.getCellRenderer(row, column);
/*     */             }
/*     */             
/*     */             public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 228 */               int serverColIndex = getColumnModel().getColumnIndex(SelectDriverInstallationPanel.SERVER_VERSION_COLUMNNAME);
/* 229 */               int installColIndex = getColumnModel().getColumnIndex(SelectDriverInstallationPanel.INSTALL_COLUMNNAME);
/*     */               
/* 231 */               if (columnIndex == serverColIndex || columnIndex == installColIndex) {
/* 232 */                 return true;
/*     */               }
/* 234 */               return false;
/*     */             }
/*     */           };
/*     */         
/* 238 */         this.table.setSelectionMode(0);
/* 239 */         this.table.setShowGrid(false);
/* 240 */         this.table.setModel(getDeviceInstallModel(getColumnIdentifiersTable(), getDataValues()));
/* 241 */         TableUtilities.setColumnsSize(this.table);
/*     */       
/*     */       }
/* 244 */       catch (Throwable e) {
/* 245 */         log.error("getDeviceInstallTable() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 249 */     return this.table;
/*     */   }
/*     */   
/*     */   public List getSelectedInstallVersion() {
/* 253 */     List<HashMap<Object, Object>> selected = new ArrayList();
/* 254 */     int installColIndex = getDeviceInstallTable().getColumnModel().getColumnIndex(INSTALL_COLUMNNAME);
/* 255 */     for (int i = 0; i < getDeviceInstallTable().getRowCount(); i++) {
/* 256 */       if (((Boolean)getDeviceInstallTable().getValueAt(i, installColIndex)).booleanValue()) {
/* 257 */         HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 258 */         for (int j = 0; j < getDeviceInstallTable().getColumnCount() - 1; j++) {
/* 259 */           String colName = getDeviceInstallTable().getColumnName(j);
/* 260 */           Object value = getDeviceInstallTable().getValueAt(i, j);
/* 261 */           map.put(colName, value);
/*     */         } 
/* 263 */         selected.add(map);
/*     */       } 
/*     */     } 
/* 266 */     return selected;
/*     */   }
/*     */   
/*     */   public DefaultTableModel getDeviceInstallModel(Vector colNames, Vector data) {
/* 270 */     DefaultTableModel model = new DefaultTableModel() {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Class getColumnClass(int c) {
/* 274 */           return getValueAt(0, c).getClass();
/*     */         }
/*     */       };
/* 277 */     model.setColumnIdentifiers(colNames);
/* 278 */     model.setDataVector(data, colNames);
/* 279 */     return model;
/*     */   }
/*     */   
/*     */   public Vector getColumnIdentifiersTable() {
/* 283 */     Vector<String> columnNames = new Vector();
/* 284 */     columnNames.add(DEVICE_COLUMNNAME);
/* 285 */     columnNames.add(INSTALLED_VERSION_COLUMNNAME);
/* 286 */     columnNames.add(SERVER_VERSION_COLUMNNAME);
/* 287 */     columnNames.add(INSTALL_COLUMNNAME);
/* 288 */     return columnNames;
/*     */   }
/*     */   
/*     */   public Vector getDataValues() {
/* 292 */     Vector<Vector<String>> data = new Vector();
/* 293 */     Vector<String> rowData = new Vector();
/* 294 */     rowData.add("Tech2");
/* 295 */     rowData.add("1.0.1");
/* 296 */     rowData.add("");
/* 297 */     rowData.add(new Boolean(false));
/* 298 */     data.add(rowData);
/* 299 */     Vector<String> rowData1 = new Vector();
/* 300 */     rowData1.add("VCI");
/* 301 */     rowData1.add("1.1.1");
/* 302 */     Vector<String> cbo = new Vector();
/* 303 */     cbo.add("1.1.1");
/* 304 */     cbo.add("1.1.0");
/* 305 */     rowData1.add(cbo);
/* 306 */     rowData1.add(new Boolean(true));
/* 307 */     data.add(rowData1);
/* 308 */     return data;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SelectDriverInstallationPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */