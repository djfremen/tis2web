/*     */ package com.eoos.gm.tis2web.frame.dwnld.install.ui;
/*     */ 
/*     */ import com.eoos.datatype.IVersionNumber;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Group;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SelectionDialog
/*     */   extends JFrame
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(SelectionDialog.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final Object NULL_SELECTION = new Object();
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2008112500002L;
/*     */ 
/*     */   
/*     */   private static final int COL_ID = 0;
/*     */   
/*     */   private static final int COL_INSTALLED_VERSION = 1;
/*     */   
/*     */   private static final int COL_AVAILABLE_VERSIONS = 2;
/*     */   
/*     */   private Callback callback;
/*     */   
/*  94 */   private JTable softwareTable = null;
/*     */   
/*  96 */   private JPanel buttonPanel = null;
/*     */   private Map groupToComboBox;
/*     */   
/*     */   public static interface Callback
/*     */   {
/*     */     public static final String HEADER_SOFTWARE = "software";
/*     */     public static final String HEADER_INSTALLED_VERSION = "installed.version";
/*     */     public static final String HEADER_AVAILABLE_VERSIONS = "available.versions";
/*     */     public static final String BUTTON_INSTALL = "install";
/*     */     public static final String BUTTON_SKIP = "skip";
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     List getGroups();
/*     */     
/*     */     String getDescription(Group param1Group);
/*     */     
/*     */     IVersionNumber getInstalledVersion(Group param1Group);
/*     */     
/*     */     List getAvailableVersions(Group param1Group);
/*     */     
/*     */     String getErrorMessage(Exception param1Exception);
/*     */     
/*     */     SummaryPanel.Callback onInstall(SelectionCallback param1SelectionCallback, UIUtil.ProgressObserver param1ProgressObserver) throws Exception;
/*     */     
/*     */     public static interface SelectionCallback
/*     */     {
/*     */       Collection getSelectedGroups();
/*     */       
/*     */       IVersionNumber getSelectedVersion(Group param2Group);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onClose() {
/* 130 */     dispose();
/*     */   }
/*     */   
/*     */   private void onInstall() {
/* 134 */     final Map<Object, Object> groupToSelectedVersion = new HashMap<Object, Object>();
/* 135 */     for (Iterator<Map.Entry> iter = this.groupToComboBox.entrySet().iterator(); iter.hasNext(); ) {
/* 136 */       Map.Entry entry = iter.next();
/* 137 */       Group group = (Group)entry.getKey();
/* 138 */       JComboBox cb = (JComboBox)entry.getValue();
/* 139 */       Object selection = cb.getSelectedItem();
/* 140 */       if (selection != NULL_SELECTION) {
/* 141 */         groupToSelectedVersion.put(group, selection);
/*     */       }
/*     */     } 
/*     */     
/* 145 */     if (groupToSelectedVersion.size() == 0) {
/* 146 */       onClose();
/*     */     } else {
/* 148 */       Util.createAndStartThread(new Runnable()
/*     */           {
/*     */             SummaryPanel.Callback summaryCallback;
/*     */             
/*     */             public void run() {
/*     */               try {
/* 154 */                 UIUtil.ProgressObserver observer = UIUtil.showProgressObserver(SelectionDialog.this, new I18NSupport.FixedLocale()
/*     */                     {
/*     */                       public String getText(String key) {
/* 157 */                         return SelectionDialog.this.callback.getLabel(key);
/*     */                       }
/*     */                     },  SelectionDialog.this.callback.getLabel("please.wait"));
/*     */                 
/*     */                 try {
/* 162 */                   this.summaryCallback = SelectionDialog.this.callback.onInstall(new SelectionDialog.Callback.SelectionCallback()
/*     */                       {
/*     */                         public Collection getSelectedGroups() {
/* 165 */                           return new LinkedList(groupToSelectedVersion.keySet());
/*     */                         }
/*     */                         
/*     */                         public IVersionNumber getSelectedVersion(Group group) {
/* 169 */                           return (IVersionNumber)groupToSelectedVersion.get(group);
/*     */                         }
/*     */                       },  observer);
/*     */                 } finally {
/* 173 */                   observer.close();
/*     */                 } 
/* 175 */                 SelectionDialog.log.debug("installation done, displaying summary callback");
/* 176 */                 SummaryDialog.show(null, this.summaryCallback);
/*     */               }
/* 178 */               catch (InterruptedException e) {
/* 179 */                 SelectionDialog.log.info("installation has been interrupted");
/* 180 */               } catch (Exception e) {
/* 181 */                 SelectionDialog.log.error("unable to execute installation - exception: " + e, e);
/* 182 */                 UIUtil.showErrorPopup(SelectionDialog.this, SelectionDialog.this.callback.getErrorMessage(e));
/*     */               } finally {
/* 184 */                 Util.executeOnAWTThread(new Runnable()
/*     */                     {
/*     */                       public void run() {
/* 187 */                         SelectionDialog.this.onClose();
/*     */                       }
/*     */                     },  false);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JComponent addBorder(JComponent component) {
/* 199 */     component.setBorder(BorderFactory.createLineBorder(Color.BLUE));
/* 200 */     return component;
/*     */   }
/*     */   
/*     */   private JPanel getButtonPanel() {
/* 204 */     if (this.buttonPanel == null) {
/* 205 */       this.buttonPanel = new JPanel();
/* 206 */       this.buttonPanel.setLayout(new GridLayout(1, 2));
/*     */       
/* 208 */       JButton buttonInstall = new JButton(this.callback.getLabel("install"));
/* 209 */       buttonInstall.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 212 */               SelectionDialog.this.onInstall();
/*     */             }
/*     */           });
/*     */       
/* 216 */       this.buttonPanel.add(buttonInstall);
/*     */       
/* 218 */       JButton buttonSkip = new JButton(this.callback.getLabel("skip"));
/* 219 */       buttonSkip.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 222 */               SelectionDialog.this.onClose();
/*     */             }
/*     */           });
/*     */       
/* 226 */       this.buttonPanel.add(buttonSkip);
/*     */     } 
/*     */     
/* 229 */     return this.buttonPanel;
/*     */   }
/*     */   
/*     */   private JTable getSoftwareTable() {
/* 233 */     if (this.softwareTable == null) {
/* 234 */       final List groups = this.callback.getGroups();
/* 235 */       TableModel model = new AbstractTableModel() {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object getValueAt(int rowIndex, int columnIndex) {
/* 239 */             Group group = groups.get(rowIndex);
/* 240 */             switch (columnIndex) {
/*     */               case 0:
/* 242 */                 return SelectionDialog.this.callback.getDescription(group);
/*     */               case 1:
/* 244 */                 return SelectionDialog.this.callback.getInstalledVersion(group);
/*     */               case 2:
/* 246 */                 return SelectionDialog.this.getVersionSelection(group);
/*     */             } 
/* 248 */             throw new IllegalArgumentException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getRowCount() {
/* 253 */             return groups.size();
/*     */           }
/*     */           
/*     */           public int getColumnCount() {
/* 257 */             return 3;
/*     */           }
/*     */           
/*     */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 261 */             return (columnIndex == 2);
/*     */           }
/*     */           
/*     */           public Class getColumnClass(int columnIndex) {
/* 265 */             switch (columnIndex) {
/*     */               case 2:
/* 267 */                 return JComboBox.class;
/*     */               case 1:
/* 269 */                 return IVersionNumber.class;
/*     */             } 
/* 271 */             return super.getColumnClass(columnIndex);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 276 */       this.softwareTable = new JTable(model);
/* 277 */       TableColumnModel columnModel = this.softwareTable.getColumnModel();
/*     */       
/* 279 */       columnModel.getColumn(0).setHeaderValue(this.callback.getLabel("software"));
/* 280 */       columnModel.getColumn(1).setHeaderValue(this.callback.getLabel("installed.version"));
/* 281 */       columnModel.getColumn(2).setHeaderValue(this.callback.getLabel("available.versions"));
/* 282 */       this.softwareTable.setRowSelectionAllowed(false);
/*     */       
/* 284 */       this.softwareTable.setDefaultRenderer(IVersionNumber.class, new DefaultTableCellRenderer() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 288 */               String displayValue = null;
/* 289 */               if (value != null) {
/* 290 */                 displayValue = ((IVersionNumber)value).toString();
/*     */               } else {
/* 292 */                 displayValue = SelectionDialog.this.callback.getLabel("not.installed");
/*     */               } 
/*     */               
/* 295 */               JLabel ret = (JLabel)super.getTableCellRendererComponent(table, displayValue, isSelected, hasFocus, row, column);
/* 296 */               return ret;
/*     */             }
/*     */           });
/*     */       
/* 300 */       this.softwareTable.setDefaultRenderer(JComponent.class, new DefaultTableCellRenderer()
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 305 */               JComponent ret = (JComponent)value;
/* 306 */               ret.setBackground(table.getBackground());
/* 307 */               return ret;
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 312 */       this.softwareTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 317 */               return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 322 */       this.softwareTable.setDefaultEditor(JComponent.class, new AbstractTableCellEditor()
/*     */           {
/*     */             public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
/* 325 */               JComponent ret = (JComponent)value;
/* 326 */               return ret;
/*     */             }
/*     */             
/*     */             public Object getCellEditorValue() {
/* 330 */               return null;
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 335 */       this.softwareTable.setRowHeight(this.softwareTable.getRowHeight() + 10);
/*     */     } 
/*     */     
/* 338 */     return this.softwareTable;
/*     */   }
/*     */   
/* 341 */   private SelectionDialog(Callback callback) { this.groupToComboBox = new HashMap<Object, Object>(); this.callback = callback; setTitle(callback.getLabel("software.install")); addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { SelectionDialog.this.onClose(); } }
/*     */       ); getContentPane().setLayout(new GridBagLayout()); GridBagConstraints c1 = new GridBagConstraints(); c1.gridx = 0; c1.gridy = 0; c1.fill = 1;
/*     */     c1.weightx = 1.0D;
/*     */     c1.weighty = 1.0D;
/*     */     JScrollPane pane = new JScrollPane(addBorder(getSoftwareTable()));
/*     */     getContentPane().add(pane, c1);
/*     */     GridBagConstraints c2 = new GridBagConstraints();
/*     */     c2.gridx = 0;
/*     */     c2.gridy = 1;
/*     */     c2.anchor = 13;
/*     */     getContentPane().add(addBorder(getButtonPanel()), c2);
/* 352 */     pack(); } private JComboBox getVersionSelection(Group group) { JComboBox ret = (JComboBox)this.groupToComboBox.get(group);
/* 353 */     if (ret == null) {
/* 354 */       IVersionNumber installedVersion = this.callback.getInstalledVersion(group);
/* 355 */       this.callback.getAvailableVersions(group);
/*     */       
/* 357 */       List<Object> options = new LinkedList(this.callback.getAvailableVersions(group));
/* 358 */       if (installedVersion != null) {
/* 359 */         options.remove(installedVersion);
/*     */       }
/* 361 */       options.add(NULL_SELECTION);
/* 362 */       ret = new JComboBox(options.toArray());
/*     */       
/* 364 */       ret.setRenderer(new DefaultListCellRenderer() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 368 */               String label = null;
/* 369 */               if (value.hashCode() == SelectionDialog.NULL_SELECTION.hashCode()) {
/* 370 */                 label = SelectionDialog.this.callback.getLabel("do.not.install");
/*     */               } else {
/* 372 */                 label = ((IVersionNumber)value).toString();
/*     */               } 
/* 374 */               return super.getListCellRendererComponent(list, label, index, isSelected, cellHasFocus);
/*     */             }
/*     */           });
/* 377 */       ret.setSelectedIndex(0);
/* 378 */       this.groupToComboBox.put(group, ret);
/*     */     } 
/*     */     
/* 381 */     return ret; }
/*     */ 
/*     */   
/*     */   public static void execute(final Component parentComponent, final Callback callback) {
/* 385 */     Util.ensureNotAWTThread();
/* 386 */     final Object sync = new Object();
/* 387 */     synchronized (sync) {
/* 388 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 391 */               SelectionDialog dialog = new SelectionDialog(callback) {
/*     */                   private static final long serialVersionUID = 1L;
/*     */                   
/*     */                   protected void onClose() {
/* 395 */                     super.onClose();
/* 396 */                     synchronized (sync) {
/* 397 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/* 401 */               dialog.setLocationRelativeTo(parentComponent);
/* 402 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */       try {
/* 406 */         sync.wait();
/* 407 */       } catch (InterruptedException e) {
/* 408 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\instal\\ui\SelectionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */