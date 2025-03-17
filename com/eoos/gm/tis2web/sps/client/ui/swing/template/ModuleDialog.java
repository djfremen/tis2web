/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
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
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  36 */   protected JPanel jContentPane = null;
/*     */   
/*  38 */   protected JPanel mainPanel = null;
/*     */   
/*  40 */   protected JTabbedPane jTabbedPane = null;
/*     */   
/*  42 */   protected Vector dataValue = new Vector();
/*     */   
/*  44 */   protected static Locale locale = null;
/*     */   
/*  46 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  48 */   private static final Logger log = Logger.getLogger(ModuleDialog.class);
/*     */ 
/*     */   
/*     */   protected JFrame frame;
/*     */ 
/*     */ 
/*     */   
/*     */   public ModuleDialog(JFrame frame, Vector value) {
/*  56 */     super(frame, frame.getTitle(), true);
/*  57 */     this.frame = frame;
/*  58 */     this.dataValue = value;
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  69 */     setSize(SwingUtils.getDialogHistory_Width(), SwingUtils.getDialogHistory_Height());
/*  70 */     setLocation(UIUtil.getCenterLocation(this, this.frame));
/*  71 */     setContentPane(getJContentPane());
/*  72 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent event) {
/*  74 */             ModuleDialog.this.componentResizedAction();
/*     */           }
/*     */         });
/*  77 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent evt) {
/*  79 */             ModuleDialog.this.closeDialog();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector getTableHeader() {
/*  86 */     Vector<String> colHeader = new Vector();
/*     */     
/*     */     try {
/*  89 */       colHeader.add(resourceProvider.getLabel(locale, "moduleDialog.table.id"));
/*  90 */       colHeader.add(resourceProvider.getLabel(locale, "moduleDialog.table.selected"));
/*  91 */       colHeader.add(resourceProvider.getLabel(locale, "moduleDialog.table.description"));
/*     */     }
/*  93 */     catch (Exception e) {
/*  94 */       log.error("unable to load resources, -exception: " + e.getMessage());
/*     */     } 
/*  96 */     return colHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/* 105 */     if (this.jContentPane == null) {
/* 106 */       this.jContentPane = new JPanel();
/* 107 */       this.jContentPane.setLayout(new BorderLayout());
/* 108 */       this.jContentPane.add(getMainPanel(), "Center");
/*     */     } 
/*     */     
/* 111 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getMainPanel() {
/* 120 */     if (this.mainPanel == null) {
/*     */       
/*     */       try {
/* 123 */         this.mainPanel = new JPanel(new BorderLayout());
/* 124 */         DefaultTableModel model = new DefaultTableModel(this.dataValue, getTableHeader());
/* 125 */         JTable table = new JTable(model);
/* 126 */         table.setShowGrid(false);
/* 127 */         table.setEnabled(false);
/* 128 */         JScrollPane scrollPane = new JScrollPane();
/* 129 */         scrollPane.getViewport().add(table);
/* 130 */         this.mainPanel.add(scrollPane, "Center");
/*     */         
/* 132 */         JButton btnOK = new JButton(resourceProvider.getLabel(locale, "ok"));
/* 133 */         btnOK.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 135 */                 ModuleDialog.this.closeDialog();
/*     */               }
/*     */             });
/* 138 */         JPanel btnPanel = new JPanel(new GridBagLayout());
/* 139 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/* 141 */         c.gridx = 3;
/* 142 */         c.gridy = 0;
/* 143 */         btnPanel.add(btnOK, c);
/* 144 */         this.mainPanel.add(btnPanel, "South");
/*     */       }
/* 146 */       catch (Throwable e) {
/*     */         
/* 148 */         log.error("getEnginePanel, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 151 */     return this.mainPanel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeDialog() {
/* 156 */     setVisible(false);
/* 157 */     dispose();
/*     */   }
/*     */   
/*     */   private void componentResizedAction() {
/* 161 */     setSize((getWidth() < SwingUtils.getDialogECU_Width() - 100) ? (SwingUtils.getDialogECU_Width() - 50) : getWidth(), (getHeight() < SwingUtils.getDialogECU_Height() - 100) ? (SwingUtils.getDialogECU_Height() - 50) : getHeight());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ModuleDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */