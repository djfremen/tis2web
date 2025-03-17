/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.PanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.io.SettingsProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.io.impl.IOConfigurationImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.io.impl.SettingsProviderImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJComboBox;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.VINJComboBox;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidateVINPanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements PanelController
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  58 */   protected JPanel titlePanel = null;
/*     */   
/*  60 */   protected JPanel textPanel = null;
/*     */   
/*  62 */   protected JPanel imagePanel = null;
/*     */   
/*  64 */   protected JPanel mainPanel = null;
/*     */   
/*  66 */   protected JPanel temporaryPanel = null;
/*     */   
/*  68 */   protected VINJComboBox cboVIN = null;
/*     */   
/*  70 */   private List newButtons = null;
/*     */   
/*     */   protected Controller controller;
/*     */   
/*  74 */   protected Properties properties = new Properties();
/*     */   
/*  76 */   protected GridBagConstraints constr = new GridBagConstraints();
/*     */   
/*  78 */   protected SettingsProvider settProvider = (SettingsProvider)new SettingsProviderImpl();
/*     */   
/*  80 */   protected IOConfigurationImpl configProperties = new IOConfigurationImpl();
/*     */   
/*  82 */   protected ClientSettings clientSettings = null;
/*     */   
/*     */   protected static int gridyIndex;
/*     */   
/*  86 */   protected static Locale locale = null;
/*     */   
/*  88 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  90 */   private static final Logger log = Logger.getLogger(ValidateVINPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidateVINPanel(Controller controller, BaseCustomizeJPanel prevPanel) {
/*  96 */     super(prevPanel);
/*  97 */     this.clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*  98 */     initialize();
/*  99 */     createButtonsList();
/* 100 */     this.controller = controller;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/* 109 */     setLayout(new BorderLayout());
/* 110 */     add(getTitlePanel(), "North");
/* 111 */     add(getMainPanel(), "Center");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableInput() {
/* 117 */     this.cboVIN.setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/* 126 */     if (this.titlePanel == null) {
/*     */       try {
/* 128 */         this.titlePanel = new JPanel();
/* 129 */         this.titlePanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/* 130 */         this.titlePanel.setLayout(new GridBagLayout());
/* 131 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/* 132 */         JLabel titleLabel = new JLabel();
/* 133 */         titleLabel.setText(resourceProvider.getLabel(locale, "validatevinScreen.title"));
/* 134 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 135 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 136 */         titelPanelConstraint.gridx = 0;
/* 137 */         titelPanelConstraint.gridy = 5;
/* 138 */         this.titlePanel.add(titleLabel, titelPanelConstraint);
/* 139 */       } catch (Throwable e) {
/* 140 */         log.error("Exception in getTitlePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 143 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getImagePanel() {
/* 147 */     if (this.imagePanel == null) {
/*     */       
/*     */       try {
/* 150 */         this.imagePanel = new JPanel();
/* 151 */         JLabel imageLabel = new JLabel();
/* 152 */         imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/WizzVehData.jpg")));
/* 153 */         this.imagePanel.add(imageLabel, "North");
/*     */       }
/* 155 */       catch (Throwable e) {
/* 156 */         log.error("Exception in getImagePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 159 */     return this.imagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/* 163 */     if (this.mainPanel == null) {
/*     */       
/*     */       try {
/* 166 */         this.mainPanel = new JPanel();
/* 167 */         this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 0));
/* 168 */         this.mainPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null));
/* 169 */         this.mainPanel.add(getImagePanel());
/* 170 */         this.mainPanel.add(getTextPanel());
/*     */       }
/* 172 */       catch (Throwable e) {
/* 173 */         log.error("Exception in getMainPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 176 */     return this.mainPanel;
/*     */   }
/*     */   
/*     */   private JPanel getTemporaryPanel() {
/* 180 */     if (this.temporaryPanel == null) {
/*     */       try {
/* 182 */         this.temporaryPanel = new JPanel();
/* 183 */         this.temporaryPanel.setLayout(new GridBagLayout());
/* 184 */         this.temporaryPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null, Color.black));
/* 185 */         this.temporaryPanel.setVisible(false);
/* 186 */       } catch (Throwable e) {
/* 187 */         log.error("Exception in getTemporaryPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 190 */     return this.temporaryPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTextPanel() {
/* 199 */     if (this.textPanel == null) {
/*     */       try {
/* 201 */         this.textPanel = new JPanel();
/* 202 */         this.textPanel.setLayout(new BorderLayout());
/*     */         
/* 204 */         JPanel vinPanel = new JPanel();
/* 205 */         vinPanel.setLayout(new BorderLayout());
/* 206 */         vinPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "validatevinScreen.vinPanel.title"), 0, 0, vinPanel.getFont().deriveFont(1)));
/* 207 */         vinPanel.add((Component)getCboVIN(), "North");
/*     */         
/* 209 */         JPanel infoPanel = new JPanel();
/* 210 */         infoPanel.setLayout(new GridBagLayout());
/* 211 */         GridBagConstraints c = new GridBagConstraints();
/* 212 */         JTextArea txtArea = new JTextArea(resourceProvider.getMessage(locale, "validatevinScreen.vin.instruction"));
/* 213 */         txtArea.setFont(txtArea.getFont().deriveFont(1));
/* 214 */         txtArea.setEditable(false);
/* 215 */         txtArea.setBackground(getBackground());
/*     */         
/* 217 */         c.anchor = 18;
/* 218 */         c.gridx = 0;
/* 219 */         c.gridy = 0;
/* 220 */         infoPanel.add(txtArea, c);
/*     */         
/* 222 */         c.gridx = 0;
/* 223 */         c.gridy = 2;
/* 224 */         c.insets = new Insets(20, 0, 0, 0);
/* 225 */         c.weightx = 1.0D;
/* 226 */         c.gridwidth = 3;
/* 227 */         c.fill = 2;
/* 228 */         infoPanel.add(vinPanel, c);
/*     */         
/* 230 */         c.gridx = 0;
/* 231 */         c.gridy = 3;
/* 232 */         c.insets = new Insets(20, 0, 0, 0);
/* 233 */         c.weightx = 1.0D;
/* 234 */         c.gridwidth = 3;
/* 235 */         c.fill = 2;
/* 236 */         infoPanel.add(getTemporaryPanel(), c);
/*     */         
/* 238 */         this.textPanel.add(infoPanel, "North");
/*     */       }
/* 240 */       catch (Throwable e) {
/* 241 */         log.error("Exception in getTextPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 244 */     return this.textPanel;
/*     */   }
/*     */   
/*     */   private void addRow(JLabel lblKey, JComponent comp, GridBagConstraints u_constraints, int gridy) {
/*     */     try {
/* 249 */       u_constraints.gridx = 0;
/* 250 */       u_constraints.gridy = gridy;
/* 251 */       u_constraints.weightx = 0.0D;
/* 252 */       u_constraints.gridwidth = 1;
/* 253 */       u_constraints.insets = new Insets(0, 2, 0, 10);
/* 254 */       u_constraints.fill = 2;
/* 255 */       this.temporaryPanel.add(lblKey, u_constraints);
/*     */       
/* 257 */       u_constraints.gridx = 2;
/* 258 */       u_constraints.gridy = gridy;
/* 259 */       u_constraints.weightx = 1.0D;
/* 260 */       u_constraints.gridwidth = 3;
/* 261 */       u_constraints.fill = 2;
/* 262 */       u_constraints.insets = new Insets(0, 2, 0, 2);
/* 263 */       this.temporaryPanel.add(comp, u_constraints);
/* 264 */     } catch (Exception e) {
/* 265 */       log.error("Exception in addRow() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VINJComboBox getCboVIN() {
/* 276 */     if (this.cboVIN == null) {
/*     */       try {
/* 278 */         Vector vin = Transform.createVector(this.clientSettings.getVINs());
/* 279 */         this.cboVIN = new VINJComboBox(vin);
/* 280 */         this.cboVIN.setEditable(true);
/* 281 */         this.cboVIN.addKeyListener();
/* 282 */         this.cboVIN.addMouseListener();
/*     */       }
/* 284 */       catch (Throwable e) {
/* 285 */         log.error("unable to build VINComboBox, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 288 */     return this.cboVIN;
/*     */   }
/*     */   
/*     */   public void onOpenPanel() {
/* 292 */     this.controller.setVINOnBarStatus(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onNextAction() {
/* 297 */     if (getCboVIN().getSelectedItem().toString().equals("") || getCboVIN().getSelectedItem().toString().length() != 17) {
/* 298 */       JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(locale, "sps.exception.invalid-vin"), "Exception", 0);
/* 299 */       return false;
/*     */     } 
/*     */     
/* 302 */     return true;
/*     */   }
/*     */   
/*     */   public void onClosePanel(boolean isBackAction) {
/* 306 */     if (isBackAction) {
/*     */       
/* 308 */       this.controller.setVINOnBarStatus(null);
/*     */     }
/*     */     else {
/*     */       
/* 312 */       this.clientSettings.setVIN(this.cboVIN.getSelectedItem().toString());
/* 313 */       this.clientSettings.saveConfiguration();
/* 314 */       this.controller.setVINOnBarStatus(this.cboVIN.getSelectedItem().toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createButtonsList() {
/* 322 */     this.newButtons = new ArrayList();
/* 323 */     this.newButtons.add("ecuData");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getNewButtons() {
/* 332 */     return this.newButtons;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 337 */       setRequestGroup(req.getRequestGroup());
/* 338 */       if (req instanceof VINDisplayRequest) {
/* 339 */         this.cboVIN.setRequest((VINDisplayRequest)req);
/* 340 */         System.setProperty(CommonAttribute.VIN.toString(), this.cboVIN.getSelectedItem().toString());
/*     */       }
/* 342 */       else if (req instanceof SelectionRequest) {
/* 343 */         getTemporaryPanel().setVisible(true);
/* 344 */         if (existsAttributeOnPanel(req.getAttribute()))
/*     */           return; 
/* 346 */         CustomizeJComboBox customizeJComboBox = new CustomizeJComboBox(this, (Request)req);
/* 347 */         Attribute attr = ((SelectionRequest)req).getAttribute();
/* 348 */         DisplayAdapter display = new DisplayAdapter(attr);
/* 349 */         JLabel lbl = new JLabel(display.toString());
/* 350 */         addRow(lbl, (JComponent)customizeJComboBox, this.constr, gridyIndex);
/* 351 */         gridyIndex++;
/* 352 */         validate();
/*     */       }
/*     */     
/* 355 */     } catch (Exception e) {
/* 356 */       log.error("Exception in handleRequest() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean existsAttributeOnPanel(Attribute attr) {
/*     */     try {
/* 368 */       Component[] comp = getTemporaryPanel().getComponents();
/* 369 */       for (int i = 0; i < comp.length; i++)
/*     */       {
/* 371 */         if (comp[i] instanceof AttributeInput && (
/* 372 */           (AttributeInput)comp[i]).getAttribute() != null && 
/* 373 */           attr.equals(((AttributeInput)comp[i]).getAttribute())) {
/* 374 */           return true;
/*     */         }
/*     */       }
/*     */     
/* 378 */     } catch (Exception e) {
/* 379 */       log.error("Exception in existsAttributeOnPanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 381 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadProperties() {
/* 388 */     this.properties = this.configProperties.load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSelectedValueSaved(String selected) {
/* 398 */     return this.properties.containsValue(selected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveDefaultValue() {
/*     */     try {
/* 407 */       int index = this.cboVIN.getItemCount();
/* 408 */       Object selected = this.cboVIN.getSelectedItem();
/* 409 */       if (selected != null && 
/* 410 */         selected instanceof Value) {
/* 411 */         selected = selected.toString();
/*     */       }
/*     */ 
/*     */       
/* 415 */       if (isSelectedValueSaved((String)selected)) {
/*     */         return;
/*     */       }
/* 418 */       ArrayList allData = this.configProperties.insertsNewLine(index, (String)selected);
/* 419 */       this.settProvider.setVINs(allData);
/*     */     }
/* 421 */     catch (Exception e) {
/* 422 */       log.error("unable to save Default value of VIN , -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleComboSelection(JComponent comp) {
/* 428 */     SwingUtils.deleteCompsGreaterIndex(getTemporaryPanel(), SwingUtils.getIndexOfComp(getTemporaryPanel(), comp));
/*     */   }
/*     */   
/*     */   public void handleSelection(Attribute attribute, Value value) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ValidateVINPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */