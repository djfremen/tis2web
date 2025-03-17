/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.PanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ctrl.RequestHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJComboBox;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.VCExtRequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VehicleDataPanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements PanelController, RequestHandler
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  46 */   private GridBagConstraints constr = new GridBagConstraints();
/*     */   
/*     */   private static int gridyIndex;
/*     */   
/*     */   private Controller controller;
/*     */   
/*  52 */   private JPanel tablePanel = null;
/*     */   
/*  54 */   private JPanel vehDataPanel = null;
/*     */   
/*  56 */   private JLabel titleLabel = null;
/*     */   
/*  58 */   private JTextField txtVIN = new JTextField();
/*     */   
/*     */   private Request request;
/*     */   
/*  62 */   private List newButtons = null;
/*     */   
/*  64 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  66 */   private static final Logger log = Logger.getLogger(VehicleDataPanel.class);
/*     */   protected SelectionRequest lastRequest;
/*     */   protected CustomizeJComboBox lastJComboBox;
/*     */   
/*  70 */   public VehicleDataPanel(Controller contr, BaseCustomizeJPanel previous, VCExtRequestGroup reqGroup) { super(previous);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     this.lastRequest = null;
/* 251 */     this.lastJComboBox = null; this.controller = contr; initialize(); initializeTable(reqGroup); createButtonsList(); } public VehicleDataPanel(Controller contr, BaseCustomizeJPanel previous) { super(previous); this.lastRequest = null; this.lastJComboBox = null; this.controller = contr; initialize(); this.newButtons = new ArrayList(); this.newButtons.add("reset"); }
/*     */   private void initialize() { setLayout(new BorderLayout()); add(getVehDataPanel(), "North"); gridyIndex = 0; addTitleTable(this.constr, gridyIndex); gridyIndex++; }
/*     */   private void initializeTable(VCExtRequestGroup reqGroup) { try { String vin = reqGroup.getVIN(); this.txtVIN.setText(vin); List attrs = reqGroup.getAttributes(); Iterator<Attribute> iterator = attrs.iterator(); while (iterator.hasNext()) { Attribute attr = iterator.next(); DisplayAdapter attrDisplay = new DisplayAdapter(attr); JLabel lblKey = new JLabel(attrDisplay.toString()); Value value = reqGroup.getValue(attr); DisplayAdapter valueDisplay = new DisplayAdapter(value); JLabel compValue = new JLabel(valueDisplay.toString()); addRow(lblKey, compValue, this.constr, gridyIndex); gridyIndex++; }  } catch (Exception e) { log.error("Error in initializeTable, -exception: " + e.getMessage()); }  }
/*     */   private void createButtonsList() { this.newButtons = new ArrayList(); this.newButtons.add("ecuData"); }
/* 255 */   private void addTitleTable(GridBagConstraints u_constraints, int u_gridy) { try { u_constraints.gridx = 0; u_constraints.gridy = u_gridy; u_constraints.gridwidth = 2; u_constraints.ipady = 40; JLabel lbl = new JLabel(resourceProvider.getLabel(null, "vehicledataScreen.vehDataTable.title")); lbl.setFont(lbl.getFont().deriveFont(1)); this.tablePanel.add(lbl, u_constraints); u_constraints.ipady = 0; } catch (Exception e) { log.error("unable to load resource in addTitleTable(), -exception: " + e.getMessage()); }  } private void addRow(JLabel lblKey, JComponent comp, GridBagConstraints u_constraints, int gridy) { try { u_constraints.gridx = 0; u_constraints.gridy = gridy; u_constraints.weightx = 0.0D; u_constraints.gridwidth = 2; u_constraints.fill = 2; u_constraints.insets = new Insets(2, 2, 2, 2); this.tablePanel.add(lblKey, u_constraints); lblKey.setFont(lblKey.getFont().deriveFont(1)); u_constraints.gridx = 2; u_constraints.gridy = gridy; u_constraints.weightx = 1.0D; u_constraints.gridwidth = 3; u_constraints.fill = 2; this.tablePanel.add(comp, u_constraints); } catch (Exception e) { log.error("addRow() method, -exception: " + e.getMessage()); }  } public void handleRequest(AssignmentRequest req) { try { setRequestGroup(req.getRequestGroup());
/* 256 */       this.request = (Request)req;
/*     */       
/* 258 */       if (req instanceof SelectionRequest)
/*     */       {
/* 260 */         if (this.lastRequest != null && this.lastRequest.getAttribute().equals(((SelectionRequest)req).getAttribute()) && 
/* 261 */           this.lastRequest instanceof DefaultValueRetrieval && 
/* 262 */           displaysLastComboBox(this.lastJComboBox)) {
/* 263 */           Object defaultValue = ((DefaultValueRetrieval)this.lastRequest).getDefaultValue();
/* 264 */           if (defaultValue != null) {
/* 265 */             this.lastJComboBox.setSelectedItem(null);
/* 266 */             this.lastJComboBox.showPopupList();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 272 */         CustomizeJComboBox customizeJComboBox = new CustomizeJComboBox(this, (Request)req);
/* 273 */         customizeJComboBox.setBackground(Color.white);
/* 274 */         Attribute attr = ((SelectionRequest)req).getAttribute();
/* 275 */         DisplayAdapter attrDisplay = new DisplayAdapter(attr);
/* 276 */         JLabel lbl = new JLabel(attrDisplay.toString());
/* 277 */         addRow(lbl, (JComponent)customizeJComboBox, this.constr, gridyIndex);
/* 278 */         gridyIndex++;
/* 279 */         validate();
/* 280 */         customizeJComboBox.showPopupList();
/* 281 */         this.lastRequest = (SelectionRequest)req;
/* 282 */         this.lastJComboBox = customizeJComboBox;
/*     */       }
/*     */        }
/* 285 */     catch (Exception e)
/* 286 */     { log.error("unable to handle Request, handleRequest() method, -exception: " + e.getMessage()); }  }
/*     */   public Attribute getAttribute() { return ((AssignmentRequest)this.request).getAttribute(); }
/*     */   public List getNewButtons() { return this.newButtons; }
/*     */   private JPanel getTablePanel() { if (this.tablePanel == null) try { this.tablePanel = new JPanel(); this.tablePanel.setLayout(new GridBagLayout()); this.tablePanel.setBorder(new EmptyBorder(new Insets(0, 20, 20, 20))); } catch (Throwable e) { log.error("tablePanel() method, -exception: " + e.getMessage()); }   return this.tablePanel; }
/*     */   private JPanel getVehDataPanel() { if (this.vehDataPanel == null) try { this.vehDataPanel = new JPanel(); this.vehDataPanel.setLayout(new BorderLayout()); this.vehDataPanel.add(getTablePanel(), "Center"); } catch (Throwable e) { log.error("getVehDataPanel() method, -exception: " + e.getMessage()); }   return this.vehDataPanel; }
/* 291 */   public void handleComboSelection(JComponent comp) { SwingUtils.deleteCompsGreaterIndex(getTablePanel(), SwingUtils.getIndexOfComp(getTablePanel(), comp)); } public void handleSelection(Attribute attribute, Value value) { this.controller.handleSelection(attribute, value); } public void setTitleLabel(String title) { this.titleLabel.setText(title); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {
/* 296 */     Component[] comp = getTablePanel().getComponents();
/* 297 */     for (int i = 0; i < comp.length; i++) {
/* 298 */       if (comp[i] instanceof CustomizeJComboBox) {
/* 299 */         ((CustomizeJComboBox)comp[i]).showPopupList();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean displaysLastComboBox(CustomizeJComboBox cb) {
/*     */     try {
/* 308 */       Component[] comp = getTablePanel().getComponents();
/* 309 */       for (int i = 0; i < comp.length; i++) {
/* 310 */         if (comp[i] instanceof CustomizeJComboBox && (
/* 311 */           (CustomizeJComboBox)comp[i]).equals(cb)) {
/* 312 */           return true;
/*     */         }
/*     */       }
/*     */     
/* 316 */     } catch (Exception x) {}
/*     */     
/* 318 */     return false;
/*     */   }
/*     */   
/*     */   public void onResetAction() {
/*     */     try {
/* 323 */       Component[] comp = getTablePanel().getComponents();
/*     */       
/* 325 */       int firstCompIndex = SwingUtils.getIndexOfComp(getTablePanel(), null);
/*     */       
/* 327 */       if (firstCompIndex != 0) {
/* 328 */         SwingUtils.deleteCompsGreaterIndex(getTablePanel(), firstCompIndex);
/* 329 */         ((CustomizeJComboBox)comp[firstCompIndex]).setSelectedIndex(-1);
/* 330 */         this.controller.setNextButtonState(this.controller.getInitialNextButtonState());
/*     */       } 
/*     */       
/* 333 */       getTablePanel().repaint();
/* 334 */     } catch (Exception e) {
/* 335 */       log.error("onResetAction() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\VehicleDataPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */