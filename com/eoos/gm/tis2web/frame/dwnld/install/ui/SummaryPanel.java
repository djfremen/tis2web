/*     */ package com.eoos.gm.tis2web.frame.dwnld.install.ui;
/*     */ 
/*     */ import com.eoos.datatype.IVersionNumber;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SummaryPanel
/*     */   extends JPanel
/*     */ {
/*     */   static final long serialVersionUID = 2008120200001L;
/*     */   private static final int COL_ID = 0;
/*     */   private static final int COL_STATUS = 1;
/*     */   private Callback callback;
/*  58 */   private JTable summaryTable = null;
/*     */   
/*  60 */   private JPanel buttonPanel = null;
/*     */   
/*     */   public SummaryPanel(Callback callback) {
/*  63 */     this.callback = callback;
/*     */     
/*  65 */     setLayout(new GridBagLayout());
/*     */     
/*  67 */     GridBagConstraints c1 = new GridBagConstraints();
/*  68 */     c1.gridx = 0;
/*  69 */     c1.gridy = 0;
/*  70 */     c1.fill = 1;
/*  71 */     c1.weightx = 1.0D;
/*  72 */     c1.weighty = 1.0D;
/*  73 */     JScrollPane pane = new JScrollPane(addBorder(getSummaryTable()));
/*     */     
/*  75 */     add(pane, c1);
/*     */     
/*  77 */     GridBagConstraints c2 = new GridBagConstraints();
/*  78 */     c2.gridx = 0;
/*  79 */     c2.gridy = 1;
/*  80 */     c2.anchor = 13;
/*     */     
/*  82 */     add(addBorder(getButtonPanel()), c2);
/*     */   }
/*     */ 
/*     */   
/*     */   private JComponent addBorder(JComponent component) {
/*  87 */     component.setBorder(BorderFactory.createLineBorder(Color.BLUE));
/*  88 */     return component;
/*     */   }
/*     */   
/*     */   private JTable getSummaryTable() {
/*  92 */     if (this.summaryTable == null) {
/*  93 */       final List softwareList = new LinkedList(this.callback.getInstalledSoftware());
/*  94 */       TableModel model = new AbstractTableModel() {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object getValueAt(int rowIndex, int columnIndex) {
/*     */             String result;
/*  99 */             IDownloadUnit unit = softwareList.get(rowIndex);
/* 100 */             IVersionNumber version = SummaryPanel.this.callback.getSelectedVersion(unit);
/* 101 */             switch (columnIndex) {
/*     */               case 0:
/* 103 */                 return SummaryPanel.this.callback.getDescriptionOfSelectedVersion(unit) + ", " + SummaryPanel.this.callback.getLabel("version") + ":" + version.toString();
/*     */               
/*     */               case 1:
/* 106 */                 result = SummaryPanel.this.callback.getInstallationResult(unit, version);
/* 107 */                 if (result.equalsIgnoreCase("0"))
/* 108 */                   return SummaryPanel.this.callback.getLabel("successfully.installed"); 
/* 109 */                 if (result.equalsIgnoreCase("-1")) {
/* 110 */                   return SummaryPanel.this.callback.getLabel("failure.not.installed");
/*     */                 }
/* 112 */                 return SummaryPanel.this.callback.getLabel("unknown");
/*     */             } 
/*     */ 
/*     */             
/* 116 */             throw new IllegalArgumentException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getRowCount() {
/* 121 */             return softwareList.size();
/*     */           }
/*     */           
/*     */           public int getColumnCount() {
/* 125 */             return 2;
/*     */           }
/*     */           
/*     */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 129 */             return false;
/*     */           }
/*     */         };
/*     */       
/* 133 */       this.summaryTable = new JTable(model);
/* 134 */       TableColumnModel columnModel = this.summaryTable.getColumnModel();
/*     */       
/* 136 */       columnModel.getColumn(0).setHeaderValue(this.callback.getLabel("software"));
/* 137 */       columnModel.getColumn(1).setHeaderValue(this.callback.getLabel("status"));
/* 138 */       this.summaryTable.setRowSelectionAllowed(false);
/*     */       
/* 140 */       this.summaryTable.setRowHeight(this.summaryTable.getRowHeight() + 10);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     return this.summaryTable;
/*     */   }
/*     */   
/*     */   private JPanel getButtonPanel() {
/* 148 */     if (this.buttonPanel == null) {
/* 149 */       this.buttonPanel = new JPanel();
/* 150 */       this.buttonPanel.setLayout(new GridLayout(1, 1));
/*     */       
/* 152 */       JButton buttonInstall = new JButton(this.callback.getLabel("ok"));
/* 153 */       buttonInstall.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 156 */               SummaryPanel.this.onClose();
/*     */             }
/*     */           });
/*     */       
/* 160 */       this.buttonPanel.add(buttonInstall);
/*     */     } 
/* 162 */     return this.buttonPanel;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     public static final String HEADER_SOFTWARE = "software";
/*     */     public static final String HEADER_STATUS = "status";
/*     */     public static final String BUTTON_OK = "ok";
/*     */     public static final String RESULT_SUCCESS = "0";
/*     */     public static final String RESULT_FAILURE = "-1";
/*     */     public static final String RESULT_UNKNOWN = "999";
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     Set getInstalledSoftware();
/*     */     
/*     */     IVersionNumber getSelectedVersion(IDownloadUnit param1IDownloadUnit);
/*     */     
/*     */     String getDescriptionOfSelectedVersion(IDownloadUnit param1IDownloadUnit);
/*     */     
/*     */     String getInstallationResult(IDownloadUnit param1IDownloadUnit, IVersionNumber param1IVersionNumber);
/*     */     
/*     */     Locale getLocale();
/*     */   }
/*     */   
/*     */   protected abstract void onClose();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\instal\\ui\SummaryPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */