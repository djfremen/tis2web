/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.CustomizeTableCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.text.View;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HistoryDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   private JPanel jContentPane = null;
/*     */   
/*  42 */   private JPanel historyPanel = null;
/*     */   
/*     */   private JTable m_table;
/*     */   
/*  46 */   private DefaultTableModel m_model = null;
/*     */   
/*  48 */   private int origX = 0;
/*     */   
/*  50 */   private int origY = 0;
/*     */   
/*  52 */   protected Vector dataValue = new Vector();
/*     */   
/*  54 */   protected Vector colHeader = new Vector();
/*     */   
/*  56 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  58 */   protected JScrollPane scrollPane = null;
/*     */   
/*  60 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  62 */   private static final Logger log = Logger.getLogger(HistoryDialog.class);
/*     */ 
/*     */   
/*     */   protected JFrame frame;
/*     */ 
/*     */ 
/*     */   
/*     */   public HistoryDialog(JFrame frame, Vector data) {
/*  70 */     super(frame, frame.getTitle(), true);
/*  71 */     this.frame = frame;
/*  72 */     this.dataValue = data;
/*  73 */     initialize();
/*  74 */     this.origX = (getSize()).width;
/*  75 */     this.origY = (getSize()).height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/*  86 */       setSize(SwingUtils.getDialog_Width(), SwingUtils.getDialog_Height());
/*  87 */       setLocation(UIUtil.getCenterLocation(this, this.frame));
/*  88 */       setContentPane(getJContentPane());
/*  89 */       addComponentListener(new ComponentAdapter() {
/*     */             public void componentResized(ComponentEvent event) {
/*  91 */               HistoryDialog.this.componentResizedAction();
/*     */             }
/*     */           });
/*     */       
/*  95 */       addWindowListener(new WindowAdapter() {
/*     */             public void windowClosing(WindowEvent evt) {
/*  97 */               HistoryDialog.this.closeDialog();
/*     */             }
/*     */           });
/*     */     }
/* 101 */     catch (Throwable e) {
/* 102 */       log.error("initialize() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/* 112 */     if (this.jContentPane == null) {
/* 113 */       this.jContentPane = new JPanel();
/* 114 */       this.jContentPane.setLayout(new BorderLayout());
/* 115 */       this.jContentPane.add(getHistoryPanel(), "Center");
/*     */     } 
/*     */     
/* 118 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getHistoryPanel() {
/* 127 */     if (this.historyPanel == null) {
/*     */       
/*     */       try {
/* 130 */         this.historyPanel = new JPanel(new BorderLayout());
/* 131 */         this.colHeader.add(resourceProvider.getLabel(null, "historyDialog.historyTable.releaseDate"));
/* 132 */         this.colHeader.add(resourceProvider.getLabel(null, "historyDialog.historyTable.ecuSoftware"));
/* 133 */         this.colHeader.add(resourceProvider.getLabel(null, "historyDialog.historyTable.description"));
/* 134 */         this.m_model = new DefaultTableModel(this.dataValue, this.colHeader);
/*     */         
/* 136 */         this.m_table = new JTable(this.m_model);
/* 137 */         this.m_table.getTableHeader().setReorderingAllowed(false);
/* 138 */         this.m_table.setRowHeight(TableUtilities.getHeightFont(this.m_table));
/* 139 */         this.m_table.setDefaultRenderer(Object.class, (TableCellRenderer)new CustomizeTableCellRenderer());
/* 140 */         this.m_table.setShowGrid(false);
/* 141 */         this.m_table.setShowHorizontalLines(true);
/* 142 */         this.scrollPane = new JScrollPane();
/* 143 */         this.scrollPane.getViewport().add(this.m_table);
/* 144 */         this.historyPanel.add(this.scrollPane, "Center");
/*     */         
/* 146 */         JButton btnOK = new JButton(resourceProvider.getLabel(null, "ok"));
/* 147 */         btnOK.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 149 */                 HistoryDialog.this.closeDialog();
/*     */               }
/*     */             });
/* 152 */         JPanel btnPanel = new JPanel(new GridBagLayout());
/* 153 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/* 155 */         c.gridx = 3;
/* 156 */         c.gridy = 0;
/* 157 */         btnPanel.add(btnOK, c);
/* 158 */         this.historyPanel.add(btnPanel, "South");
/* 159 */         calculateRowsHeight();
/* 160 */       } catch (Throwable e) {
/* 161 */         log.error("getHistoryPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 164 */     return this.historyPanel;
/*     */   }
/*     */   
/*     */   public void calculateRowsHeight() {
/*     */     try {
/* 169 */       if (this.m_table == null || this.m_table.getRowCount() == 0) {
/*     */         return;
/*     */       }
/* 172 */       int lastColIndex = this.m_table.getColumnCount() - 1;
/* 173 */       int table_width = this.m_table.getWidth();
/* 174 */       if (table_width == 0)
/* 175 */         table_width = SwingUtils.getDialog_Width(); 
/* 176 */       TableUtilities.setAllColumnsMaxMin(this.m_table, table_width);
/* 177 */       int lastColWidth = this.m_table.getColumnModel().getColumn(lastColIndex).getMinWidth();
/*     */       
/* 179 */       for (int i = 0; i < this.m_table.getRowCount(); i++) {
/* 180 */         JLabel lbl1 = new JLabel();
/* 181 */         lbl1.setText((String)this.m_model.getValueAt(i, lastColIndex - 1));
/* 182 */         int height1 = (int)lbl1.getPreferredSize().getHeight();
/*     */         
/* 184 */         View view = BasicHTML.createHTMLView(new JLabel(), this.m_model.getValueAt(i, lastColIndex).toString());
/* 185 */         view.setSize((lastColWidth - 5), 10000.0F);
/* 186 */         int height2 = (int)view.getMinimumSpan(1);
/*     */         
/* 188 */         int height = Math.max(height1, height2);
/* 189 */         this.m_table.setRowHeight(i, height);
/*     */       }
/*     */     
/* 192 */     } catch (Exception e) {
/* 193 */       log.error("calculateRowsHeight() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeDialog() {
/* 199 */     setVisible(false);
/* 200 */     dispose();
/*     */   }
/*     */   
/*     */   private void componentResizedAction() {
/* 204 */     calculateRowsHeight();
/* 205 */     setSize((getWidth() < this.origX - 100) ? (this.origX - 50) : getWidth(), (getHeight() < this.origY - 100) ? (this.origY - 50) : getHeight());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\HistoryDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */