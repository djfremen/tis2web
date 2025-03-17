/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.TestSummaryTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJScrollPane;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTable;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestSummary
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   private JPanel westPanel = null;
/*     */   
/*  42 */   private JPanel centerPanel = null;
/*     */   
/*  44 */   private JPanel northPanel = null;
/*     */   
/*  46 */   private JPanel tablePanel = null;
/*     */   
/*  48 */   private CustomizeJTable firstTable = null;
/*     */   
/*  50 */   private JLabel imageLabel = null;
/*     */   
/*  52 */   private JLabel titleLabel = null;
/*     */   
/*  54 */   private CustomizeJScrollPane scrollPane = null;
/*     */   
/*  56 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  58 */   protected static Locale locale = null;
/*     */   
/*  60 */   protected DefaultTableModel summary = null;
/*     */   
/*  62 */   private static final Logger log = Logger.getLogger(TestSummary.class);
/*     */   
/*     */   public TestSummary(DefaultTableModel summary, BaseCustomizeJPanel prevPanel) {
/*  65 */     super(prevPanel);
/*  66 */     this.summary = summary;
/*  67 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  76 */     setLayout(new BorderLayout());
/*  77 */     add(getWestPanel(), "West");
/*  78 */     add(getCenterPanel(), "Center");
/*  79 */     add(getNorthPanel(), "North");
/*  80 */     setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
/*     */   }
/*     */   
/*     */   private JPanel getWestPanel() {
/*  84 */     if (this.westPanel == null) {
/*     */       try {
/*  86 */         this.westPanel = new JPanel();
/*  87 */         this.westPanel.setLayout(new BorderLayout());
/*  88 */         this.westPanel.setBorder(new EmptyBorder(new Insets(0, 5, 5, 5)));
/*  89 */         this.imageLabel = new JLabel();
/*     */         
/*  91 */         this.imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/device.jpg")));
/*  92 */         this.westPanel.add(this.imageLabel, "North");
/*  93 */         this.westPanel.setPreferredSize(new Dimension(180, 100));
/*  94 */       } catch (Throwable e) {
/*  95 */         log.warn("Image device.jpg not found, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*  98 */     return this.westPanel;
/*     */   }
/*     */   
/*     */   private JPanel getNorthPanel() {
/* 102 */     if (this.northPanel == null) {
/*     */       try {
/* 104 */         this.northPanel = new JPanel();
/* 105 */         this.northPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/* 106 */         this.northPanel.setLayout(new GridBagLayout());
/* 107 */         GridBagConstraints northConstraints = new GridBagConstraints();
/* 108 */         this.titleLabel = new JLabel();
/* 109 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 110 */         this.titleLabel.setFont(new Font(this.titleLabel.getFont().getFontName(), 1, fontSize));
/* 111 */         this.titleLabel.setText("Test Summary");
/* 112 */         this.northPanel.add(this.titleLabel, northConstraints);
/* 113 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/* 114 */         northConstraints.gridx = 1;
/* 115 */         northConstraints.gridy = 2;
/*     */       }
/* 117 */       catch (Throwable e) {
/* 118 */         log.error("getNorthPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/* 121 */     return this.northPanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/* 125 */     if (this.centerPanel == null) {
/*     */       try {
/* 127 */         this.centerPanel = new JPanel();
/* 128 */         GridLayout lyt = new GridLayout(1, 0);
/* 129 */         lyt.setVgap(10);
/* 130 */         this.centerPanel.setLayout(lyt);
/* 131 */         this.centerPanel.add(getTablePanel());
/*     */       }
/* 133 */       catch (Throwable e) {
/* 134 */         log.error("getCenterPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 138 */     return this.centerPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTablePanel() {
/* 147 */     if (this.tablePanel == null) {
/*     */       try {
/* 149 */         this.tablePanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Insets getInsets() {
/* 153 */               return new Insets(20, 20, 20, 20);
/*     */             }
/*     */           };
/* 156 */         this.tablePanel.setLayout(new BorderLayout());
/* 157 */         this.tablePanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null));
/* 158 */         this.tablePanel.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/*     */ 
/*     */         
/* 161 */         this.scrollPane = new CustomizeJScrollPane(getFirstTable());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         this.tablePanel.add((Component)this.scrollPane, "Center");
/*     */       }
/* 168 */       catch (Throwable e) {
/* 169 */         log.error("getFirstListPanel() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/* 172 */     return this.tablePanel;
/*     */   }
/*     */   
/*     */   public JTable getFirstTable() {
/* 176 */     if (this.firstTable == null) {
/*     */       try {
/* 178 */         this.firstTable = new CustomizeJTable() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public TableCellEditor getCellEditor(int row, int col) {
/* 182 */               return null;
/*     */             }
/*     */           };
/* 185 */         this.firstTable.setSelectionMode(0);
/* 186 */         this.firstTable.setShowGrid(false);
/* 187 */         this.firstTable.setModel(this.summary);
/* 188 */         this.firstTable.setDefaultRenderer(Object.class, (TableCellRenderer)new TestSummaryTableCellRenderer());
/* 189 */         TableUtilities.setColumnMaxMin((JTable)this.firstTable, 0);
/* 190 */         this.firstTable.getColumnModel().getColumn(0).setMaxWidth(300);
/* 191 */         this.firstTable.getColumnModel().getColumn(0).setMinWidth(300);
/*     */       }
/* 193 */       catch (Throwable e) {
/* 194 */         log.error("getFirstTable() method, - exception" + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 198 */     return (JTable)this.firstTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveScroll() {
/* 209 */     TableModelListener listener = new TableModelListener()
/*     */       {
/*     */         public void tableChanged(TableModelEvent e) {
/* 212 */           Runnable doWorkRunnable = new Runnable()
/*     */             {
/*     */               public void run() {
/* 215 */                 TestSummary.this.getFirstTable().scrollRectToVisible(TestSummary.this.getFirstTable().getCellRect(TestSummary.this.getFirstTable().getModel().getRowCount(), 0, true));
/*     */               }
/*     */             };
/* 218 */           SwingUtilities.invokeLater(doWorkRunnable);
/*     */         }
/*     */       };
/*     */     
/* 222 */     getFirstTable().getModel().addTableModelListener(listener);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\TestSummary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */