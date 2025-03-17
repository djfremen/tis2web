/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.CalibrationPanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ProgrammingDataController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.CalibrationTreeModel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.IconCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.render.PROMIconCellRenderer;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CalibrationTreePanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  50 */   protected JTree jTree = null;
/*     */   
/*  52 */   protected JPanel m_eastPanel = null;
/*     */   
/*  54 */   protected JPanel m_bulletinsPanel = null;
/*     */   
/*  56 */   protected JPanel m_textPanel = null;
/*     */   
/*  58 */   protected JPanel m_indexPanel = null;
/*     */   
/*  60 */   protected JPanel m_outterPanel = null;
/*     */   
/*  62 */   protected JList m_listbox = null;
/*     */   
/*  64 */   protected JTextField txtCrtCalibration = null;
/*     */   
/*     */   protected ProgrammingDataController controller;
/*     */   
/*     */   protected CalibrationPanelController calibController;
/*     */   
/*     */   protected boolean moduleTyp;
/*     */   
/*  72 */   protected static Locale locale = null;
/*     */   
/*  74 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  76 */   private static final Logger log = Logger.getLogger(CalibrationTreePanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CalibrationTreePanel(ProgrammingDataController controller, CalibrationPanelController calibController) {
/*  83 */     this.controller = controller;
/*  84 */     this.calibController = calibController;
/*  85 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  94 */     setLayout(new BorderLayout());
/*  95 */     setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "calibrationScreen.index"), 0, 0, getFont().deriveFont(1)));
/*  96 */     JScrollPane scroll = new JScrollPane(getJTree());
/*  97 */     add(scroll, "Center");
/*  98 */     add(getEastPanel(), "East");
/*     */   }
/*     */ 
/*     */   
/*     */   public JPanel getBulletinsPanel() {
/* 103 */     if (this.m_bulletinsPanel == null) {
/*     */       try {
/* 105 */         this.m_bulletinsPanel = new JPanel(new BorderLayout());
/* 106 */         this.m_bulletinsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*     */         
/* 108 */         this.m_listbox = new JList();
/* 109 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.bulletins"));
/* 110 */         this.m_bulletinsPanel.add(lbl, "North");
/* 111 */         lbl.setFont(this.m_bulletinsPanel.getFont().deriveFont(1));
/* 112 */         this.m_listbox.addMouseListener(new MouseAdapter() {
/*     */               public void mouseClicked(MouseEvent e) {
/* 114 */                 if (e.getClickCount() == 2) {
/* 115 */                   int index = CalibrationTreePanel.this.m_listbox.locationToIndex(e.getPoint());
/* 116 */                   ListModel dlm = CalibrationTreePanel.this.m_listbox.getModel();
/* 117 */                   Object item = dlm.getElementAt(index);
/*     */                   
/* 119 */                   CalibrationTreePanel.this.m_listbox.ensureIndexIsVisible(index);
/* 120 */                   CalibrationTreePanel.this.controller.requestBulletinDisplay((String)item);
/*     */                 } 
/*     */               }
/*     */             });
/* 124 */         JScrollPane scroll = new JScrollPane(this.m_listbox);
/* 125 */         this.m_bulletinsPanel.add(scroll, "Center");
/*     */       }
/* 127 */       catch (Exception e) {
/* 128 */         log.error("getBulletinsPanel() methode, - exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 131 */     return this.m_bulletinsPanel;
/*     */   }
/*     */   
/*     */   public JPanel getTextPanel() {
/* 135 */     if (this.m_textPanel == null) {
/*     */       try {
/* 137 */         this.m_textPanel = new JPanel(new GridLayout(2, 1));
/* 138 */         this.m_textPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/* 139 */         JLabel lbl = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.current"));
/* 140 */         lbl.setFont(lbl.getFont().deriveFont(1));
/* 141 */         this.txtCrtCalibration = new JTextField();
/* 142 */         this.txtCrtCalibration.setEditable(false);
/* 143 */         this.txtCrtCalibration.setBackground(Color.white);
/* 144 */         this.m_textPanel.add(lbl);
/* 145 */         this.m_textPanel.add(this.txtCrtCalibration);
/* 146 */       } catch (Throwable e) {
/* 147 */         log.error("unable to load label calibrationScreen.current, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 150 */     return this.m_textPanel;
/*     */   }
/*     */   
/*     */   public JPanel getIndexPanel() {
/* 154 */     if (this.m_indexPanel == null) {
/*     */       try {
/* 156 */         this.m_indexPanel = new JPanel();
/*     */         
/* 158 */         this.m_indexPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "calibrationScreen.index"), 0, 0, this.m_indexPanel.getFont().deriveFont(1)));
/* 159 */         this.m_indexPanel.setLayout(new GridBagLayout());
/* 160 */         GridBagConstraints c = new GridBagConstraints();
/* 161 */         c.gridx = 0;
/* 162 */         c.gridy = 0;
/*     */         
/* 164 */         JLabel lblImg1 = new JLabel();
/* 165 */         lblImg1.setIcon(TreeNodeIcon.ICON_SELECTED);
/* 166 */         this.m_indexPanel.add(lblImg1, c);
/*     */         
/* 168 */         c.gridx = 0;
/* 169 */         c.gridy = 1;
/*     */         
/* 171 */         JLabel lblImg2 = new JLabel();
/* 172 */         lblImg2.setIcon(TreeNodeIcon.ICON_NOSELECTION);
/* 173 */         this.m_indexPanel.add(lblImg2, c);
/*     */         
/* 175 */         c.gridx = 0;
/* 176 */         c.gridy = 2;
/*     */         
/* 178 */         JLabel lblImg3 = new JLabel();
/* 179 */         lblImg3.setIcon(TreeNodeIcon.ICON_UNSELECTED);
/* 180 */         this.m_indexPanel.add(lblImg3, c);
/*     */         
/* 182 */         c.gridx = 1;
/* 183 */         c.gridy = 0;
/* 184 */         c.gridwidth = 2;
/* 185 */         c.fill = 2;
/* 186 */         JLabel lblSelected = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.selected"));
/* 187 */         this.m_indexPanel.add(lblSelected, c);
/*     */         
/* 189 */         c.gridx = 1;
/* 190 */         c.gridy = 1;
/* 191 */         c.gridwidth = 2;
/* 192 */         c.fill = 2;
/* 193 */         JLabel lblNoselection = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.noselection"));
/* 194 */         this.m_indexPanel.add(lblNoselection, c);
/*     */         
/* 196 */         c.gridx = 1;
/* 197 */         c.gridy = 2;
/* 198 */         c.gridwidth = 2;
/* 199 */         c.fill = 2;
/* 200 */         JLabel lblUnselected = new JLabel(resourceProvider.getLabel(locale, "calibrationScreen.unselected"));
/* 201 */         this.m_indexPanel.add(lblUnselected, c);
/*     */       }
/* 203 */       catch (Exception e) {
/* 204 */         log.error("unable to load labels, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 207 */     return this.m_indexPanel;
/*     */   }
/*     */   
/*     */   public JPanel getEastPanel() {
/* 211 */     if (this.m_eastPanel == null) {
/*     */       try {
/* 213 */         this.m_eastPanel = new JPanel();
/* 214 */         this.m_eastPanel.setLayout(new GridBagLayout());
/* 215 */         GridBagConstraints c = new GridBagConstraints();
/* 216 */         c.insets = new Insets(0, 10, 2, 10);
/*     */         
/* 218 */         c.gridx = 0;
/* 219 */         c.gridy = 0;
/* 220 */         c.gridheight = 1;
/* 221 */         c.gridwidth = 1;
/* 222 */         c.weightx = 1.0D;
/* 223 */         c.weighty = 1.0D;
/* 224 */         c.fill = 1;
/*     */         
/* 226 */         this.m_eastPanel.add(getTextPanel(), c);
/* 227 */         c.gridx = 0;
/* 228 */         c.gridy = 1;
/* 229 */         c.gridheight = 1;
/* 230 */         c.gridwidth = 1;
/* 231 */         c.weightx = 1.0D;
/* 232 */         c.weighty = 1.0D;
/* 233 */         c.fill = 1;
/* 234 */         this.m_eastPanel.add(getBulletinsPanel(), c);
/*     */         
/* 236 */         c.gridx = 0;
/* 237 */         c.gridy = 2;
/* 238 */         c.gridheight = 2;
/* 239 */         c.gridwidth = 2;
/* 240 */         c.weightx = 1.0D;
/* 241 */         c.weighty = 1.0D;
/* 242 */         c.fill = 1;
/* 243 */         this.m_eastPanel.add(getIndexPanel(), c);
/* 244 */       } catch (Exception e) {
/* 245 */         log.error("getEastPanel() methode, - exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 248 */     return this.m_eastPanel;
/*     */   }
/*     */   
/*     */   private JTree getJTree() {
/* 252 */     if (this.jTree == null) {
/*     */       try {
/* 254 */         this.jTree = new JTree();
/* 255 */         this.jTree.addTreeSelectionListener(new TreeSelectionListener() {
/*     */               public void valueChanged(TreeSelectionEvent e) {
/* 257 */                 CalibrationTreePanel.this.onSelectedTreeNodeAction();
/*     */               }
/*     */             });
/*     */       }
/* 261 */       catch (Throwable e) {
/* 262 */         log.error("getJTree() methode, - exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 265 */     return this.jTree;
/*     */   }
/*     */   
/*     */   public void cleanBulletinsList() {
/* 269 */     this.m_listbox.setModel(new DefaultListModel());
/* 270 */     this.m_listbox.invalidate();
/*     */   }
/*     */   
/*     */   protected void setBulletinsModel(Object node) {
/*     */     try {
/* 275 */       CalibrationTreeModel model = (CalibrationTreeModel)this.jTree.getModel();
/*     */       
/* 277 */       List bullList = model.getBulletins(node);
/* 278 */       if (bullList != null) {
/* 279 */         DefaultListModel modelList = new DefaultListModel();
/* 280 */         Iterator iter = bullList.iterator();
/* 281 */         while (iter.hasNext()) {
/* 282 */           modelList.addElement(iter.next());
/*     */         }
/* 284 */         this.m_listbox.setModel(modelList);
/*     */       } else {
/* 286 */         this.m_listbox.setModel(new DefaultListModel());
/*     */       } 
/* 288 */     } catch (Exception e) {
/* 289 */       log.error("setBulletinsModel, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setHistoryModel(Object node) {
/*     */     try {
/* 295 */       CalibrationTreeModel model = (CalibrationTreeModel)this.jTree.getModel();
/* 296 */       Vector<Vector<String>> result = new Vector();
/* 297 */       Vector<String> columns = new Vector();
/*     */       
/* 299 */       if (isPROM()) {
/* 300 */         columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.partNumber"));
/* 301 */         columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.description"));
/* 302 */         Vector<String> row = new Vector();
/* 303 */         row.add(model.getPartNumber(node));
/* 304 */         row.add(model.getDescription(node));
/* 305 */         result.add(row);
/*     */       }
/*     */       else {
/*     */         
/* 309 */         columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.partNumber"));
/* 310 */         columns.add(resourceProvider.getLabel(null, "calibrationScreen.historyTable.description"));
/*     */         
/* 312 */         List list = model.getHistory(node);
/* 313 */         if (list != null) {
/*     */           
/* 315 */           Iterator iterator = list.iterator();
/* 316 */           while (iterator.hasNext()) {
/* 317 */             Vector<String> temp = new Vector();
/* 318 */             Object partNo = iterator.next();
/* 319 */             temp.add(((Part)partNo).getPartNumber());
/* 320 */             String description = ((Part)partNo).getDescription(null);
/* 321 */             String newString = Transform.convertStringToHtml(description);
/* 322 */             temp.add(newString);
/* 323 */             result.add(temp);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 329 */       this.calibController.fillHistoryTable(result, columns);
/* 330 */     } catch (Exception e) {
/* 331 */       log.error("unable to load label calibrationScreen.current, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onSelectedTreeNodeAction() {
/*     */     try {
/* 339 */       Object node = this.jTree.getLastSelectedPathComponent();
/* 340 */       if (node == null) {
/*     */         return;
/*     */       }
/* 343 */       CalibrationTreeModel model = (CalibrationTreeModel)this.jTree.getModel();
/* 344 */       if (!isPROM() && !model.isSelectable(node)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 349 */       setBulletinsModel(node);
/* 350 */       setHistoryModel(node);
/*     */       
/* 352 */       if (model.isSelectable(node))
/*     */       {
/* 354 */         Object actualSelection = model.getSelectedNode();
/* 355 */         if (actualSelection != null) {
/* 356 */           model.setSelected(actualSelection, false);
/*     */         }
/* 358 */         if (model.isSelected(node)) {
/* 359 */           model.setSelected(node, false);
/*     */         } else {
/* 361 */           model.setSelected(node, true);
/* 362 */           this.controller.handleProgrammingSelection();
/* 363 */           this.calibController.repaintModuleList();
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 368 */     } catch (Exception e) {
/* 369 */       log.error("onSelectedTreeNodeAction, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTreeModel(CalibrationTreeModel model) {
/*     */     try {
/* 375 */       this.jTree.setModel((TreeModel)model);
/*     */       
/* 377 */       if (isPROM()) {
/* 378 */         PROMIconCellRenderer renderer = new PROMIconCellRenderer();
/* 379 */         this.jTree.setCellRenderer((TreeCellRenderer)renderer);
/*     */       } else {
/* 381 */         IconCellRenderer renderer = new IconCellRenderer();
/* 382 */         this.jTree.setCellRenderer((TreeCellRenderer)renderer);
/*     */       } 
/*     */       
/* 385 */       for (int row = 0; row < this.jTree.getRowCount(); row++) {
/* 386 */         this.jTree.expandRow(row);
/*     */       }
/*     */       
/* 389 */       if (model.hasDefaultSelection() || model.getSelectedNode() != null) {
/* 390 */         Object selectedNode = model.getSelectedNode();
/* 391 */         this.jTree.setSelectionPath(model.buildTreePath(selectedNode));
/* 392 */         setBulletinsModel(selectedNode);
/* 393 */         setHistoryModel(selectedNode);
/*     */       }
/*     */     
/* 396 */     } catch (Exception e) {
/* 397 */       log.error("setTreeModel, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCurrentCalibration(CalibrationTreeModel module) {
/* 402 */     this.txtCrtCalibration.setText(module.getCalibration());
/*     */   }
/*     */   
/*     */   public void setModuleTyp(boolean status) {
/* 406 */     this.moduleTyp = status;
/*     */   }
/*     */   
/*     */   protected boolean isPROM() {
/* 410 */     return this.moduleTyp;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\CalibrationTreePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */