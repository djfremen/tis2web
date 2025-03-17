/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
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
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECUDataDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   protected JPanel jContentPane = null;
/*     */   
/*  42 */   protected JPanel enginePanel = null;
/*     */   
/*  44 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  46 */   protected Vector dataValue = new Vector();
/*     */   
/*  48 */   protected static Locale locale = null;
/*     */   
/*  50 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  52 */   private static final Logger log = Logger.getLogger(ECUDataDialog.class);
/*     */   
/*  54 */   protected String selectedController = null;
/*     */ 
/*     */   
/*     */   protected JFrame frame;
/*     */ 
/*     */ 
/*     */   
/*     */   public ECUDataDialog(JFrame frame, Vector value) {
/*  62 */     super(frame, frame.getTitle(), true);
/*  63 */     this.frame = frame;
/*  64 */     this.dataValue = value;
/*  65 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  75 */     setSize(SwingUtils.getDialogECU_Width(), SwingUtils.getDialogECU_Height());
/*  76 */     setLocation(UIUtil.getCenterLocation(this, this.frame));
/*  77 */     setContentPane(getJContentPane());
/*  78 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent event) {
/*  80 */             ECUDataDialog.this.componentResizedAction();
/*     */           }
/*     */         });
/*  83 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent evt) {
/*  85 */             ECUDataDialog.this.closeDialog();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector getTableHeader() {
/*  92 */     Vector<String> colHeader = new Vector();
/*     */     try {
/*  94 */       colHeader.add(resourceProvider.getLabel(locale, "ecuDataDialog.table.attribute"));
/*  95 */       colHeader.add(resourceProvider.getLabel(locale, "ecuDataDialog.table.value"));
/*  96 */     } catch (Exception e) {
/*  97 */       log.error("unable to load resources, -exception: " + e.getMessage());
/*     */     } 
/*  99 */     return colHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/* 108 */     if (this.jContentPane == null) {
/* 109 */       this.jContentPane = new JPanel();
/* 110 */       this.jContentPane.setLayout(new BorderLayout());
/* 111 */       this.jContentPane.add(getJTabbedPane(), "Center");
/*     */     } 
/*     */     
/* 114 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTabbedPane getJTabbedPane() {
/* 123 */     if (this.jTabbedPane == null) {
/*     */       try {
/* 125 */         this.jTabbedPane = new JTabbedPane();
/* 126 */         String controller = System.getProperty(CommonAttribute.CONTROLLER.toString());
/* 127 */         String vin = System.getProperty(CommonAttribute.VIN.toString());
/* 128 */         if (controller == null) {
/* 129 */           this.jTabbedPane.addTab("VIN: " + vin, null, getEnginePanel(), null);
/*     */         } else {
/* 131 */           this.jTabbedPane.addTab(controller, null, getEnginePanel(), null);
/*     */         } 
/* 133 */       } catch (Throwable e) {
/* 134 */         log.error("unable to load resource, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 137 */     return this.jTabbedPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getEnginePanel() {
/* 146 */     if (this.enginePanel == null) {
/*     */       
/*     */       try {
/* 149 */         this.enginePanel = new JPanel(new BorderLayout());
/* 150 */         DefaultTableModel model = new DefaultTableModel(this.dataValue, getTableHeader());
/* 151 */         JTable table = new JTable(model) {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public TableCellEditor getCellEditor(int row, int col) {
/* 155 */               return null;
/*     */             }
/*     */           };
/* 158 */         table.setRowHeight(TableUtilities.getHeightFont(table));
/* 159 */         table.setShowGrid(false);
/* 160 */         JScrollPane scrollPane = new JScrollPane();
/* 161 */         scrollPane.getViewport().add(table);
/* 162 */         this.enginePanel.add(scrollPane, "Center");
/*     */         
/* 164 */         JButton btnOK = new JButton(resourceProvider.getLabel(locale, "ok"));
/* 165 */         btnOK.setFont(btnOK.getFont().deriveFont(1));
/* 166 */         btnOK.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 168 */                 ECUDataDialog.this.closeDialog();
/*     */               }
/*     */             });
/* 171 */         JPanel btnPanel = new JPanel(new GridBagLayout());
/* 172 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/* 174 */         c.gridx = 3;
/* 175 */         c.gridy = 0;
/* 176 */         btnPanel.add(btnOK, c);
/* 177 */         this.enginePanel.add(btnPanel, "South");
/*     */       }
/* 179 */       catch (Throwable e) {
/*     */         
/* 181 */         log.error("getEnginePanel, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 184 */     return this.enginePanel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeDialog() {
/* 189 */     setVisible(false);
/* 190 */     dispose();
/*     */   }
/*     */   
/*     */   private void componentResizedAction() {
/* 194 */     setSize((getWidth() < SwingUtils.getDialogECU_Width() - 100) ? (SwingUtils.getDialogECU_Width() - 50) : getWidth(), (getHeight() < SwingUtils.getDialogECU_Height() - 100) ? (SwingUtils.getDialogECU_Height() - 50) : getHeight());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ECUDataDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */