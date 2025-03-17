/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJScrollPane;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTable;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SelectDiagnosticToolPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   private JPanel westPanel = null;
/*     */   
/*  46 */   private JPanel centerPanel = null;
/*     */   
/*  48 */   private JPanel northPanel = null;
/*     */   
/*  50 */   private JPanel firstListPanel = null;
/*     */   
/*  52 */   private JPanel firstListTitle = null;
/*     */   
/*  54 */   private JPanel secondListPanel = null;
/*     */   
/*  56 */   private JPanel secondListTitlePanel = null;
/*     */   
/*  58 */   private CustomizeJTable firstTable = null;
/*     */   
/*  60 */   private CustomizeJTable secondTable = null;
/*     */   
/*  62 */   private JLabel imageLabel = null;
/*     */   
/*  64 */   private JLabel titleLabel = null;
/*     */   
/*  66 */   private JLabel lblFirstTitlePanel = null;
/*     */   
/*  68 */   private JLabel lblSecondTitlePanel = null;
/*     */   
/*     */   private Controller controller;
/*     */   
/*     */   private List newButtons;
/*     */   
/*  74 */   protected AssignmentRequest lastRequest = null;
/*     */   
/*  76 */   protected static Locale locale = null;
/*     */   
/*  78 */   private static final Logger log = Logger.getLogger(SelectDiagnosticToolPanel.class);
/*     */   
/*  80 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource(); protected boolean suppressSelectionNotification; private void initialize() { setLayout(new BorderLayout()); add(getWestPanel(), "West"); add(getCenterPanel(), "Center"); add(getNorthPanel(), "North"); setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); }
/*     */   private JPanel getWestPanel() { if (this.westPanel == null) try { this.westPanel = new JPanel(); this.westPanel.setLayout(new BorderLayout()); this.westPanel.setBorder(new EmptyBorder(new Insets(0, 5, 5, 5))); this.imageLabel = new JLabel(); this.imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/device.jpg"))); this.westPanel.add(this.imageLabel, "North"); this.westPanel.setPreferredSize(new Dimension(180, 100)); } catch (Throwable e) { log.warn("Image device.jpg not found, -exception: " + e.getMessage()); }   return this.westPanel; }
/*     */   private JPanel getNorthPanel() { if (this.northPanel == null)
/*     */       try { this.northPanel = new JPanel(); this.northPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0))); this.northPanel.setLayout(new GridBagLayout()); GridBagConstraints northConstraints = new GridBagConstraints(); this.titleLabel = new JLabel(); int fontSize = Integer.parseInt(System.getProperty("font.size.title")); this.titleLabel.setFont(new Font(this.titleLabel.getFont().getFontName(), 1, fontSize)); this.northPanel.add(this.titleLabel, northConstraints); northConstraints.insets = new Insets(20, 20, 20, 20); northConstraints.gridx = 1; northConstraints.gridy = 2; } catch (Throwable e) { log.error("getNorthPanel() method, - exception" + e.getMessage()); }   return this.northPanel; }
/*     */   private JPanel getCenterPanel() { if (this.centerPanel == null)
/*     */       try { this.centerPanel = new JPanel(); GridLayout lyt = new GridLayout(2, 0); lyt.setVgap(10); this.centerPanel.setLayout(lyt); this.centerPanel.add(getFirstListPanel()); this.centerPanel.add(getSecondListPanel()); } catch (Throwable e) { log.error("getCenterPanel() method, - exception" + e.getMessage()); }   return this.centerPanel; }
/*  86 */   public SelectDiagnosticToolPanel(Controller contr, BaseCustomizeJPanel prevPanel) { super(prevPanel);
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
/* 374 */     this.suppressSelectionNotification = false; this.controller = contr; initialize(); } private JPanel getFirstListPanel() { if (this.firstListPanel == null)
/*     */       try { this.firstListPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(20, 20, 20, 20); }
/*     */           }; this.firstListPanel.setLayout(new BorderLayout()); this.firstListPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null)); this.firstListPanel.setFont(new Font(getFont().getFontName(), 1, getFont().getSize())); CustomizeJScrollPane scrollPane = new CustomizeJScrollPane(getFirstTable()); this.firstListPanel.add((Component)scrollPane, "Center"); this.firstListPanel.add(getFirstListTitlePanel(), "North"); }
/*     */       catch (Throwable e) { log.error("getFirstListPanel() method, - exception" + e.getMessage()); }
/* 379 */         return this.firstListPanel; } private void onSelectedFirstTableAction() { try { this.suppressSelectionNotification = true;
/* 380 */       this.secondTable.clearSelection();
/* 381 */       this.suppressSelectionNotification = false;
/* 382 */       Value selectedValue = this.firstTable.getSelectedValue();
/* 383 */       if (this.firstTable.getAttribute().equals(CommonAttribute.CONTROLLER)) {
/* 384 */         System.setProperty(CommonAttribute.CONTROLLER.toString(), selectedValue.toString());
/* 385 */         log.debug("The selected supported Controller is:" + selectedValue.toString());
/*     */       } 
/*     */       
/* 388 */       this.controller.handleSelection(this.firstTable.getAttribute(), selectedValue); }
/*     */     
/* 390 */     catch (Exception e)
/* 391 */     { log.error("onSelectedFirstTableAction() method, - exception" + e.getMessage()); }  }
/*     */   private JPanel getFirstListTitlePanel() { if (this.firstListTitle == null) try { this.firstListTitle = new JPanel(); this.firstListTitle.setLayout(new BorderLayout()); this.firstListTitle.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0))); this.lblFirstTitlePanel = new JLabel(); this.lblFirstTitlePanel.setFont(this.lblFirstTitlePanel.getFont().deriveFont(1)); this.firstListTitle.add(this.lblFirstTitlePanel, "North"); } catch (Throwable e) { log.error("getFirstListTitlePanel() method, - exception" + e.getMessage()); }   return this.firstListTitle; }
/*     */   private JPanel getSecondListPanel() { if (this.secondListPanel == null) try { this.secondListPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(20, 20, 20, 20); }
/*     */           }; this.secondListPanel.setLayout(new BorderLayout()); this.secondListPanel.setBorder(new TitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12))); CustomizeJScrollPane scrollPane = new CustomizeJScrollPane(getSecondTable()); this.secondListPanel.add((Component)scrollPane, "Center"); this.secondListPanel.add(getSecondTitlePanel(), "North"); } catch (Throwable e) { log.error("getSecondListPanel() method, - exception" + e.getMessage()); }   return this.secondListPanel; } private JPanel getSecondTitlePanel() { if (this.secondListTitlePanel == null) try { this.secondListTitlePanel = new JPanel(); this.secondListTitlePanel.setLayout(new BorderLayout()); this.secondListTitlePanel.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0))); this.lblSecondTitlePanel = new JLabel(); this.lblSecondTitlePanel.setFont(this.lblSecondTitlePanel.getFont().deriveFont(1)); this.secondListTitlePanel.add(this.lblSecondTitlePanel, "North"); } catch (Throwable e) { log.error("getSecondTitlePanel() method, - exception" + e.getMessage()); }   return this.secondListTitlePanel; } private JTable getFirstTable() { if (this.firstTable == null) try { this.firstTable = new CustomizeJTable(); this.firstTable.setSelectionMode(0); this.firstTable.setShowGrid(false); ListSelectionModel rowSM = this.firstTable.getSelectionModel(); rowSM.addListSelectionListener(new ListSelectionListener() {
/*     */               public void valueChanged(ListSelectionEvent e) { SelectDiagnosticToolPanel.this.onValueChangedFirstTable(e); }
/*     */             }); } catch (Throwable e) { log.error("getFirstTable() method, - exception" + e.getMessage()); }   return (JTable)this.firstTable; } protected JTable getSecondTable() { if (this.secondTable == null)
/*     */       try { this.secondTable = new CustomizeJTable(); this.secondTable.setSelectionMode(0); this.secondTable.setShowGrid(false); ListSelectionModel rowSM = this.secondTable.getSelectionModel(); rowSM.addListSelectionListener(new ListSelectionListener() {
/*     */               public void valueChanged(ListSelectionEvent e) { SelectDiagnosticToolPanel.this.onValueChangedSecondTable(e); }
/* 400 */             }); } catch (Throwable e) { log.error("getSecondTable() method, - exception" + e.getMessage()); }   return (JTable)this.secondTable; } private void onSelectedSecondTableAction() { try { if (!this.suppressSelectionNotification) {
/* 401 */         this.controller.handleSelection(this.secondTable.getAttribute(), this.secondTable.getSelectedValue());
/*     */       } }
/* 403 */     catch (Exception e)
/* 404 */     { log.error("onSelectedSecondTableAction() method, - exception" + e.getMessage()); }  } public void handleRequest(AssignmentRequest req) { try { if (this.lastRequest != null && this.lastRequest.equals(req)) return;  setRequestGroup(req.getRequestGroup()); if (req instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) { boolean orderByASC = false; this.firstTable.setRequest((Request)req, orderByASC); if (this.firstTable.getModel().getRowCount() == 1) this.firstTable.setRowSelectionInterval(0, 0);  this.newButtons = new ArrayList(); this.newButtons.add("settings"); this.titleLabel.setText(resourceProvider.getLabel(locale, "toolsScreen.title")); this.lblFirstTitlePanel.setText(resourceProvider.getLabel(locale, "toolsScreen.toolsPanel.title")); this.lblSecondTitlePanel.setText(resourceProvider.getLabel(locale, "toolsScreen.processPanel.title")); } else if (req instanceof com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest) { this.secondTable.setRequest((Request)req); this.secondTable.setRowSelectionInterval(0, 0); }  this.lastRequest = req; } catch (Exception e) { log.error("handleRequest() method, - exception" + e.getMessage()); }  }
/*     */   public List getNewButtons() { return this.newButtons; }
/*     */   private void onValueChangedFirstTable(ListSelectionEvent e) { if (e.getValueIsAdjusting())
/*     */       return;  ListSelectionModel lsm = (ListSelectionModel)e.getSource(); if (!lsm.isSelectionEmpty())
/*     */       onSelectedFirstTableAction();  }
/*     */   private void onValueChangedSecondTable(ListSelectionEvent e) { if (e.getValueIsAdjusting())
/*     */       return;  ListSelectionModel lsm = (ListSelectionModel)e.getSource(); if (!lsm.isSelectionEmpty())
/*     */       onSelectedSecondTableAction();  }
/* 412 */   public void resetSelection() { this.firstTable.clearSelection();
/* 413 */     this.secondTable.clearSelection();
/* 414 */     this.secondTable.setModel(new DefaultTableModel()); }
/*     */ 
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SelectDiagnosticToolPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */