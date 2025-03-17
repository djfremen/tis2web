/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJScrollPane;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTable;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
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
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFrame;
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
/*     */ public class SelectControllerPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  46 */   private JPanel westPanel = null;
/*     */   
/*  48 */   private JPanel centerPanel = null;
/*     */   
/*  50 */   private JPanel northPanel = null;
/*     */   
/*  52 */   private JPanel functionListPanel = null;
/*     */   
/*  54 */   private JPanel functionListTitle = null;
/*     */   
/*  56 */   private JPanel firstListPanel = null;
/*     */   
/*  58 */   private JPanel firstListTitle = null;
/*     */   
/*  60 */   private JPanel secondListPanel = null;
/*     */   
/*  62 */   private JPanel secondListTitlePanel = null;
/*     */   
/*  64 */   private CustomizeJTable functionTable = null;
/*     */   
/*  66 */   private CustomizeJTable firstTable = null;
/*     */   
/*  68 */   private CustomizeJTable secondTable = null;
/*     */   
/*  70 */   private JLabel imageLabel = null;
/*     */   
/*  72 */   private JLabel titleLabel = null;
/*     */   
/*  74 */   private JLabel lblFunctionTitlePanel = null;
/*     */   
/*  76 */   private JLabel lblFirstTitlePanel = null;
/*     */   
/*  78 */   private JLabel lblSecondTitlePanel = null;
/*     */   
/*     */   private Controller controller;
/*     */   
/*     */   private List newButtons;
/*     */   
/*  84 */   protected AssignmentRequest lastRequest = null;
/*     */   
/*  86 */   protected static Locale locale = null;
/*     */   
/*  88 */   private static final Logger log = Logger.getLogger(SelectDiagnosticToolPanel.class);
/*     */   
/*  90 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*     */   protected boolean suppressSelectionNotification;
/*     */   
/*     */   public SelectControllerPanel(Controller contr, BaseCustomizeJPanel prevPanel)
/*     */   {
/*  96 */     super(prevPanel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     this.suppressSelectionNotification = false; this.controller = contr; initialize(); } private void initialize() { setLayout(new BorderLayout()); add(getWestPanel(), "West"); add(getCenterPanel(), "Center"); add(getNorthPanel(), "North"); setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); } private JPanel getWestPanel() { if (this.westPanel == null) try { this.westPanel = new JPanel(); this.westPanel.setLayout(new BorderLayout()); this.westPanel.setBorder(new EmptyBorder(new Insets(0, 5, 5, 5))); this.imageLabel = new JLabel(); this.imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/device.jpg"))); this.westPanel.add(this.imageLabel, "North"); this.westPanel.setPreferredSize(new Dimension(180, 100)); } catch (Throwable e) { log.warn("Image device.jpg not found, -exception: " + e.getMessage()); }   return this.westPanel; } private JPanel getNorthPanel() { if (this.northPanel == null) try { this.northPanel = new JPanel(); this.northPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0))); this.northPanel.setLayout(new GridBagLayout()); GridBagConstraints northConstraints = new GridBagConstraints(); this.titleLabel = new JLabel(); int fontSize = Integer.parseInt(System.getProperty("font.size.title")); this.titleLabel.setFont(new Font(this.titleLabel.getFont().getFontName(), 1, fontSize)); this.northPanel.add(this.titleLabel, northConstraints); northConstraints.insets = new Insets(20, 20, 20, 20); northConstraints.gridx = 1; northConstraints.gridy = 2; } catch (Throwable e) { log.error("getNorthPanel() method, - exception" + e.getMessage()); }   return this.northPanel; } private JPanel getCenterPanel() { if (this.centerPanel == null) try { this.centerPanel = new JPanel(); GridBagLayout layout = new GridBagLayout(); this.centerPanel.setLayout(layout); GridBagConstraints constraints = null; constraints = new GridBagConstraints(); constraints.gridx = 0; constraints.gridy = 0; constraints.weightx = 1.0D; constraints.weighty = 1.0D; constraints.anchor = 23; constraints.fill = 1; constraints.insets = new Insets(0, 0, 10, 0); this.centerPanel.add(getFirstListPanel(), constraints); getFirstListPanel().setPreferredSize(new Dimension(SwingUtils.getScreenWidth() * 1 / 8, SwingUtils.getScreenHeight() * 5 / 6)); if (this.controller.supportSPSFunctions()) { constraints.gridx = 0; constraints.gridy = 6; constraints.weightx = 0.2D; constraints.weighty = 0.2D; constraints.gridheight = 1; constraints.anchor = 10; constraints.fill = 1; this.centerPanel.add(getFunctionListPanel(), constraints); getFunctionListPanel().setPreferredSize(new Dimension(SwingUtils.getScreenWidth() * 1 / 8, SwingUtils.getScreenHeight() * 2 / 5)); }  constraints = new GridBagConstraints(); constraints.gridx = 0; constraints.gridy = 9; constraints.weightx = 0.1D; constraints.weighty = 0.1D; constraints.gridheight = 1; constraints.anchor = 25; constraints.fill = 1; this.centerPanel.add(getSecondListPanel(), constraints); getSecondListPanel().setPreferredSize(new Dimension(SwingUtils.getScreenWidth() * 1 / 8, SwingUtils.getScreenHeight() * 1 / 3)); } catch (Throwable e) { log.error("getCenterPanel() method, - exception" + e.getMessage()); }   return this.centerPanel; } private JPanel getFunctionListPanel() { if (this.functionListPanel == null) try { this.functionListPanel = new JPanel() { private static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(20, 20, 20, 20); } }; this.functionListPanel.setLayout(new BorderLayout()); this.functionListPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null)); this.functionListPanel.setFont(new Font(getFont().getFontName(), 1, getFont().getSize())); CustomizeJScrollPane scrollPane = new CustomizeJScrollPane(getFunctionTable()); this.functionListPanel.add((Component)scrollPane, "Center"); this.functionListPanel.add(getFunctionListTitlePanel(), "North"); } catch (Throwable e) { log.error("getFunctionListPanel() method, - exception" + e.getMessage()); }   return this.functionListPanel; } public SelectControllerPanel() { super(null); this.suppressSelectionNotification = false; initialize(); }
/*     */   private JPanel getFunctionListTitlePanel() { if (this.functionListTitle == null) try { this.functionListTitle = new JPanel(); this.functionListTitle.setLayout(new BorderLayout()); this.functionListTitle.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0))); this.lblFunctionTitlePanel = new JLabel(); this.lblFunctionTitlePanel.setFont(this.lblFunctionTitlePanel.getFont().deriveFont(1)); this.functionListTitle.add(this.lblFunctionTitlePanel, "North"); } catch (Throwable e) { log.error("getFunctionListTitlePanel() method, - exception" + e.getMessage()); }   return this.functionListTitle; } private JPanel getFirstListPanel() { if (this.firstListPanel == null) try { this.firstListPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(20, 20, 20, 20); }
/*     */           }; this.firstListPanel.setLayout(new BorderLayout()); this.firstListPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null)); this.firstListPanel.setFont(new Font(getFont().getFontName(), 1, getFont().getSize())); CustomizeJScrollPane scrollPane = new CustomizeJScrollPane(getFirstTable()); this.firstListPanel.add((Component)scrollPane, "Center"); this.firstListPanel.add(getFirstListTitlePanel(), "North"); } catch (Throwable e) { log.error("getFirstListPanel() method, - exception" + e.getMessage()); }   return this.firstListPanel; } private JPanel getFirstListTitlePanel() { if (this.firstListTitle == null) try { this.firstListTitle = new JPanel(); this.firstListTitle.setLayout(new BorderLayout()); this.firstListTitle.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0))); this.lblFirstTitlePanel = new JLabel(); this.lblFirstTitlePanel.setFont(this.lblFirstTitlePanel.getFont().deriveFont(1)); this.firstListTitle.add(this.lblFirstTitlePanel, "North"); } catch (Throwable e) { log.error("getFirstListTitlePanel() method, - exception" + e.getMessage()); }   return this.firstListTitle; } private JPanel getSecondListPanel() { if (this.secondListPanel == null) try { this.secondListPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(20, 20, 20, 20); }
/* 526 */           }; this.secondListPanel.setLayout(new BorderLayout()); this.secondListPanel.setBorder(new TitledBorder(null, null, 0, 0, new Font("Dialog", 0, 12))); CustomizeJScrollPane scrollPane = new CustomizeJScrollPane(getSecondTable()); this.secondListPanel.add((Component)scrollPane, "Center"); this.secondListPanel.add(getSecondTitlePanel(), "North"); } catch (Throwable e) { log.error("getSecondListPanel() method, - exception" + e.getMessage()); }   return this.secondListPanel; } private void onSelectedFunctionTableAction() { try { this.suppressSelectionNotification = true;
/* 527 */       this.secondTable.clearSelection();
/* 528 */       this.suppressSelectionNotification = false;
/* 529 */       Value selectedValue = this.functionTable.getSelectedValue();
/* 530 */       if (this.functionTable.getAttribute().equals(CommonAttribute.SEQUENCE)) {
/* 531 */         System.setProperty(CommonAttribute.SEQUENCE.toString(), selectedValue.toString());
/* 532 */         log.debug("The selected controller sequence is:" + selectedValue.toString());
/*     */       }
/* 534 */       else if (this.functionTable.getAttribute().equals(CommonAttribute.FUNCTION)) {
/* 535 */         System.setProperty(CommonAttribute.FUNCTION.toString(), selectedValue.toString());
/* 536 */         log.debug("The selected controller function is:" + selectedValue.toString());
/*     */       } 
/*     */       
/* 539 */       this.controller.handleSelection(this.functionTable.getAttribute(), selectedValue); }
/*     */     
/* 541 */     catch (Exception e)
/* 542 */     { log.error("onSelectedFunctionTableAction() method, - exception" + e.getMessage()); }  } private JPanel getSecondTitlePanel() { if (this.secondListTitlePanel == null) try { this.secondListTitlePanel = new JPanel(); this.secondListTitlePanel.setLayout(new BorderLayout()); this.secondListTitlePanel.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0))); this.lblSecondTitlePanel = new JLabel(); this.lblSecondTitlePanel.setFont(this.lblSecondTitlePanel.getFont().deriveFont(1)); this.secondListTitlePanel.add(this.lblSecondTitlePanel, "North"); } catch (Throwable e) { log.error("getSecondTitlePanel() method, - exception" + e.getMessage()); }   return this.secondListTitlePanel; } private JTable getFunctionTable() { if (this.functionTable == null) try { this.functionTable = new CustomizeJTable(); this.functionTable.setSelectionMode(0); this.functionTable.setShowGrid(false); ListSelectionModel rowSM = this.functionTable.getSelectionModel(); rowSM.addListSelectionListener(new ListSelectionListener() { public void valueChanged(ListSelectionEvent e) { SelectControllerPanel.this.onValueChangedFunctionTable(e); } }
/*     */           ); } catch (Throwable e) { log.error("getFunctionTable() method, - exception" + e.getMessage()); }   return (JTable)this.functionTable; } private JTable getFirstTable() { if (this.firstTable == null) try { this.firstTable = new CustomizeJTable(); this.firstTable.setSelectionMode(0); this.firstTable.setShowGrid(false); ListSelectionModel rowSM = this.firstTable.getSelectionModel(); rowSM.addListSelectionListener(new ListSelectionListener() { public void valueChanged(ListSelectionEvent e) { SelectControllerPanel.this.onValueChangedFirstTable(e); } }
/*     */           ); } catch (Throwable e) { log.error("getFirstTable() method, - exception" + e.getMessage()); }   return (JTable)this.firstTable; } protected JTable getSecondTable() { if (this.secondTable == null) try { this.secondTable = new CustomizeJTable(); this.secondTable.setSelectionMode(0); this.secondTable.setShowGrid(false); ListSelectionModel rowSM = this.secondTable.getSelectionModel(); rowSM.addListSelectionListener(new ListSelectionListener() { public void valueChanged(ListSelectionEvent e) { SelectControllerPanel.this.onValueChangedSecondTable(e); } }
/*     */           ); } catch (Throwable e) { log.error("getSecondTable() method, - exception" + e.getMessage()); }   return (JTable)this.secondTable; } public void handleRequest(AssignmentRequest req) { try { if (this.lastRequest != null && this.lastRequest.equals(req)) return;  setRequestGroup(req.getRequestGroup()); if (req instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) { boolean orderByASC = true; this.firstTable.setRequest((Request)req, orderByASC); if (this.firstTable.getModel().getRowCount() == 1) this.firstTable.setRowSelectionInterval(0, 0);  this.newButtons = new ArrayList(); this.newButtons.add("ecuData"); if (this.firstTable.getColumnCount() > 1) TableUtilities.setColumnMaxMin((JTable)this.firstTable, 0);  if (this.controller.supportSPSFunctions() && this.functionTable.getColumnCount() > 1) TableUtilities.setColumnMaxMin((JTable)this.functionTable, 0);  this.titleLabel.setText(resourceProvider.getLabel(locale, "controllerScreen.title")); this.lblFirstTitlePanel.setText(resourceProvider.getLabel(locale, "controllerScreen.controllerPanel.title")); if (this.controller.supportSPSFunctions()) this.lblFunctionTitlePanel.setText(resourceProvider.getLabel(locale, "controllerScreen.functionPanel.title"));  this.lblSecondTitlePanel.setText(resourceProvider.getLabel(locale, "controllerScreen.typePanel.title")); } else if (req instanceof com.eoos.gm.tis2web.sps.common.SequenceSelectionRequest) { this.functionTable.setRequest((Request)req); this.functionTable.setRowSelectionInterval(0, 0); } else if (req instanceof com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest) { this.functionTable.setRequest((Request)req); this.functionTable.setRowSelectionInterval(0, 0); } else if (req instanceof com.eoos.gm.tis2web.sps.common.TypeSelectionRequest) { this.secondTable.setRequest((Request)req); this.secondTable.setRowSelectionInterval(0, 0); }  this.lastRequest = req; } catch (Exception e) { log.error("handleRequest() method, - exception" + e.getMessage()); }  } public List getNewButtons() { return this.newButtons; }
/*     */   private void onValueChangedFunctionTable(ListSelectionEvent e) { if (e.getValueIsAdjusting()) return;  ListSelectionModel lsm = (ListSelectionModel)e.getSource(); if (!lsm.isSelectionEmpty()) onSelectedFunctionTableAction();  }
/*     */   private void onValueChangedFirstTable(ListSelectionEvent e) { if (e.getValueIsAdjusting()) return;  ListSelectionModel lsm = (ListSelectionModel)e.getSource(); if (!lsm.isSelectionEmpty()) onSelectedFirstTableAction();  }
/*     */   private void onValueChangedSecondTable(ListSelectionEvent e) { if (e.getValueIsAdjusting()) return;  ListSelectionModel lsm = (ListSelectionModel)e.getSource(); if (!lsm.isSelectionEmpty()) onSelectedSecondTableAction();  }
/* 549 */   private void onSelectedFirstTableAction() { try { this.suppressSelectionNotification = true;
/* 550 */       if (this.controller.supportSPSFunctions()) {
/* 551 */         this.functionTable.clearSelection();
/*     */       }
/* 553 */       this.secondTable.clearSelection();
/* 554 */       this.suppressSelectionNotification = false;
/* 555 */       Value selectedValue = this.firstTable.getSelectedValue();
/* 556 */       if (this.firstTable.getAttribute().equals(CommonAttribute.CONTROLLER)) {
/* 557 */         System.setProperty(CommonAttribute.CONTROLLER.toString(), selectedValue.toString());
/* 558 */         log.debug("The selected controller is:" + selectedValue.toString());
/*     */       } 
/*     */       
/* 561 */       this.controller.handleSelection(this.firstTable.getAttribute(), selectedValue); }
/*     */     
/* 563 */     catch (Exception e)
/* 564 */     { log.error("onSelectedFirstTableAction() method, - exception" + e.getMessage()); }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onSelectedSecondTableAction() {
/*     */     try {
/* 573 */       if (!this.suppressSelectionNotification) {
/* 574 */         this.controller.handleSelection(this.secondTable.getAttribute(), this.secondTable.getSelectedValue());
/*     */       }
/* 576 */     } catch (Exception e) {
/* 577 */       log.error("onSelectedSecondTableAction() method, - exception" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetSelection() {
/* 585 */     this.firstTable.clearSelection();
/* 586 */     this.functionTable.clearSelection();
/* 587 */     this.secondTable.clearSelection();
/* 588 */     this.secondTable.setModel(new DefaultTableModel());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 593 */     JFrame f = new JFrame("This is a test");
/* 594 */     f.setSize(1000, 800);
/* 595 */     SelectControllerPanel panel = new SelectControllerPanel();
/* 596 */     f.getContentPane().add((Component)panel, "Center");
/* 597 */     f.setVisible(true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SelectControllerPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */