/*      */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*      */ 
/*      */ import com.eoos.datatype.gtwo.Pair;
/*      */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*      */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*      */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*      */ import com.eoos.gm.tis2web.sps.client.dtc.impl.DTCServiceImpl;
/*      */ import com.eoos.gm.tis2web.sps.client.system.CommunicationException;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.IUIAgent;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResultImpl;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.action.ButtonsAction;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.ctrl.RequestHandler;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.ECUData;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.HistoryData;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.event.EventsBlocker;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.pdf.PDFStarter;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.print.PrintableComponent;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.CalibrationSelectPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.ECUDataDialog;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.HardwarePanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.HistoryDialog;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.KeyCodePanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.MessageDialog;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.ModuleDialog;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.OdometerPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.ProgrammingVCIPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.SecurityCodePanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.SelectControllerPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.SelectDiagnosticToolPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.SettingsDialog;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.SummaryPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TestSummary;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TransferDataPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.ValidateVINPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.VehicleDataPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayUtil;
/*      */ import com.eoos.gm.tis2web.sps.client.util.ImportFileFilter;
/*      */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*      */ import com.eoos.gm.tis2web.sps.common.ControllerSecurityCodeRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VCExtRequestGroup;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.CustomException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.scsm.v2.util.StringUtilities;
/*      */ import com.eoos.scsm.v2.util.Util;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.table.JTableHeader;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SPSFrame
/*      */   extends JFrame
/*      */   implements Controller, RequestHandler, ValueRetrieval, ButtonsAction
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*  106 */   private static final Logger log = Logger.getLogger(SPSFrame.class);
/*      */   
/*  108 */   protected JPanel currentPanel = null;
/*      */   
/*  110 */   protected JPanel jContentPane = null;
/*      */   
/*  112 */   protected JPanel startPanel = null;
/*      */   
/*  114 */   protected BaseCustomizeJPanel topPanel = null;
/*      */   
/*  116 */   protected BaseCustomizeJPanel prevPanel = null;
/*      */   
/*      */   protected IUIAgent agent;
/*      */   
/*      */   protected VIT1 vit1;
/*      */   
/*      */   protected Integer device;
/*      */   
/*  124 */   protected static Stack stackForms = new Stack();
/*      */ 
/*      */   
/*      */   protected static LabelResource resourceProvider;
/*      */   
/*  129 */   protected EventsBlocker eventsBlocker = EventsBlocker.getInstance();
/*      */   
/*  131 */   protected SPSButtons buttons = null;
/*      */   
/*  133 */   protected int ORIGIN_X = 0;
/*      */   
/*  135 */   protected int ORIGIN_Y = 0;
/*      */   
/*  137 */   protected static SPSFrame instance = null;
/*      */ 
/*      */   
/*      */   protected boolean isBackAction = false;
/*      */   
/*      */   protected boolean activeXClose = true;
/*      */   
/*      */   protected boolean initialStatusNextButton;
/*      */   
/*      */   protected boolean alreadyStarted = false;
/*      */   
/*      */   protected boolean isActiveBlockingEvents = false;
/*      */   
/*      */   protected boolean isActiveTestSummary = false;
/*      */   
/*  152 */   protected MessageDialog dialog = null;
/*      */   
/*  154 */   protected WindowAdapter windowAdapter = null;
/*      */   
/*  156 */   protected List tools = new ArrayList();
/*      */   
/*      */   boolean startDeviceInstallScreen = true;
/*      */   
/*  160 */   protected String vin = null;
/*      */   private boolean delayedLockOff;
/*      */   
/*      */   private SPSFrame() {
/*  164 */     resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*  165 */     SwingUtils.setWindowComponent(this);
/*  166 */     FontUtils.initializeFont(this);
/*  167 */     this.buttons = new SPSButtons(this);
/*  168 */     initialize();
/*  169 */     pack();
/*      */   }
/*      */ 
/*      */   
/*      */   public static SPSFrame getInstance() {
/*  174 */     if (instance == null) {
/*  175 */       instance = new SPSFrame();
/*      */     }
/*  177 */     return instance;
/*      */   }
/*      */   
/*      */   private void initialize() {
/*  181 */     setContentPane(getJContentPane());
/*  182 */     settingAppTitle();
/*  183 */     this.eventsBlocker.addPermittedComponent(this.buttons.getCancelButton());
/*  184 */     setCloseState(true);
/*  185 */     WindowAdapter windowAdapter = new WindowAdapter() {
/*      */         public void windowClosing(WindowEvent e) {
/*  187 */           if (SPSFrame.this.activeXClose) {
/*  188 */             SPSFrame.this.onCloseWindow();
/*      */           }
/*      */         }
/*      */       };
/*  192 */     addWindowListener(windowAdapter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JPanel getJContentPane() {
/*  201 */     if (this.jContentPane == null) {
/*  202 */       this.jContentPane = new JPanel();
/*  203 */       this.jContentPane.setLayout(new BorderLayout());
/*  204 */       this.jContentPane.add(this.buttons, "South");
/*      */     } 
/*  206 */     return this.jContentPane;
/*      */   }
/*      */   
/*      */   public void start(IUIAgent agent) {
/*      */     try {
/*  211 */       this.agent = agent;
/*  212 */       if (!this.alreadyStarted) {
/*      */         
/*  214 */         setLocation(SwingUtils.getXInitial(this), SwingUtils.getYInitial(this));
/*  215 */         Thread thread = new Thread() {
/*      */             public void run() {
/*  217 */               SPSFrame.this.setCursor(new Cursor(3));
/*  218 */               SPSFrame.this.displayStartScreen();
/*      */             }
/*      */           };
/*  221 */         thread.start();
/*  222 */         this.alreadyStarted = true;
/*  223 */         pack();
/*      */       } else {
/*      */         
/*  226 */         restart();
/*      */       }
/*      */     
/*      */     }
/*  230 */     catch (Exception e) {
/*  231 */       log.error("unable to start application, - exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void restart() {
/*      */     try {
/*  237 */       stackForms = new Stack();
/*  238 */       if (this.currentPanel != null)
/*  239 */         getJContentPane().remove(this.currentPanel); 
/*  240 */       this.initialStatusNextButton = false;
/*  241 */       this.topPanel = null;
/*  242 */       this.currentPanel = null;
/*  243 */       this.prevPanel = null;
/*  244 */       this.isBackAction = false;
/*  245 */       setVINOnBarStatus((String)null);
/*  246 */       this.buttons.getNewButton().setVisible(false);
/*  247 */       this.buttons.getProceedButton().setVisible(false);
/*  248 */       this.buttons.getClearDTCsButton().setVisible(false);
/*  249 */       this.buttons.getNextButton().setVisible(true);
/*  250 */       setBackButtonState(true);
/*  251 */       setCancelButtonState(true);
/*  252 */       setCloseState(true);
/*  253 */     } catch (Exception e) {
/*  254 */       log.error("unable to restart application, - exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void settingAppTitle() {
/*      */     try {
/*  260 */       setTitle(resourceProvider.getLabel(null, "app.title"));
/*  261 */       ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/sps_icon.jpg"));
/*  262 */       setIconImage(icon.getImage());
/*  263 */     } catch (Exception e) {
/*  264 */       log.warn("Image sps_icon.jpg not found, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void componentResizedAction() {
/*  269 */     if (this.startPanel != null) {
/*      */       
/*  271 */       int width = (int)this.jContentPane.getSize().getWidth();
/*  272 */       if (width < SwingUtils.getDriverInstallDialog_Width()) {
/*  273 */         width = (int)SwingUtils.getDriverInstallDialog_Width();
/*      */       }
/*  275 */       int height = (int)this.jContentPane.getSize().getHeight();
/*  276 */       if (height < SwingUtils.getDriverInstallDialog_Height())
/*  277 */         height = (int)SwingUtils.getDriverInstallDialog_Height(); 
/*  278 */       this.jContentPane.setPreferredSize(new Dimension(width, height));
/*  279 */       this.jContentPane.setSize(width, height);
/*      */     } else {
/*      */       
/*  282 */       int width = (int)this.jContentPane.getSize().getWidth();
/*  283 */       if (width < SwingUtils.getMinimalScreenSize_Width()) {
/*  284 */         width = SwingUtils.getMinimalScreenSize_Width();
/*      */       }
/*  286 */       int height = (int)this.jContentPane.getSize().getHeight();
/*  287 */       if (height < SwingUtils.getMinimalScreenSize_Height())
/*  288 */         height = SwingUtils.getMinimalScreenSize_Height(); 
/*  289 */       this.jContentPane.setPreferredSize(new Dimension(width, height));
/*  290 */       this.jContentPane.setSize(width, height);
/*  291 */       SwingUtils.saveNewScreenSize(width, height);
/*  292 */       if (this.currentPanel != null) {
/*  293 */         ((BaseCustomizeJPanel)this.currentPanel).onResizeForm();
/*      */       }
/*      */     } 
/*  296 */     pack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void switchPanel(JPanel panel) {
/*      */     try {
/*  308 */       if (this.currentPanel != null) {
/*  309 */         getJContentPane().remove(this.currentPanel);
/*  310 */         ((BaseCustomizeJPanel)this.currentPanel).onClosePanel(this.isBackAction);
/*      */       } 
/*      */       
/*  313 */       this.currentPanel = panel;
/*  314 */       getJContentPane().add(this.currentPanel, "Center");
/*      */       
/*  316 */       setVisibleButtons(this.currentPanel);
/*  317 */       pack();
/*  318 */       repaint();
/*  319 */       ((BaseCustomizeJPanel)this.currentPanel).onOpenPanel();
/*  320 */     } catch (Exception e) {
/*  321 */       log.error("unable to switch to the new Panel:, -exception " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateUI(JPanel newPanel, AssignmentRequest req) {
/*      */     try {
/*  333 */       if (newPanel == null)
/*      */         return; 
/*  335 */       this.topPanel = (BaseCustomizeJPanel)newPanel;
/*  336 */       ((BaseCustomizeJPanel)newPanel).handleRequest(req);
/*  337 */       switchPanel(newPanel);
/*  338 */       stackForms.push(this.topPanel);
/*  339 */     } catch (Exception e) {
/*  340 */       log.error("unable to update UI with the new panel: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setVisibleButtons(JPanel panel) {
/*  352 */     this.buttons.visibleAllTemporaryButtons(false);
/*  353 */     this.buttons.getPrintButton().setVisible(true);
/*  354 */     this.buttons.getWestButtonPanel().revalidate();
/*  355 */     List buttons = ((BaseCustomizeJPanel)panel).getNewButtons();
/*  356 */     if (buttons == null)
/*      */       return; 
/*  358 */     Iterator<String> iterator = buttons.iterator();
/*  359 */     while (iterator.hasNext()) {
/*  360 */       this.buttons.visibleTemporaryButton(iterator.next(), true);
/*      */     }
/*  362 */     this.buttons.getWestButtonPanel().revalidate();
/*      */   }
/*      */ 
/*      */   
/*      */   public void triggerSummaryButtons(boolean visibleHistory, boolean visibleModuleInfo) {
/*  367 */     if (visibleHistory) {
/*  368 */       this.buttons.getHistoryButton().setVisible(true);
/*      */     } else {
/*  370 */       this.buttons.getHistoryButton().setVisible(false);
/*      */     } 
/*      */     
/*  373 */     if (visibleModuleInfo) {
/*  374 */       this.buttons.getModuleInfoButton().setVisible(true);
/*      */     } else {
/*  376 */       this.buttons.getModuleInfoButton().setVisible(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public JLabel getSplash() {
/*      */     try {
/*  383 */       ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/loading.jpg"));
/*  384 */       return new JLabel(image, 0);
/*  385 */     } catch (Exception e) {
/*  386 */       log.error("unable to load Splash Image, -exception: " + e.getMessage());
/*  387 */       JLabel ret = new JLabel("");
/*  388 */       ret.setPreferredSize(new Dimension(SwingUtils.getSplash_Width(), SwingUtils.getSplash_Height()));
/*  389 */       return ret;
/*      */     } 
/*      */   }
/*      */   
/*      */   private JPanel createStartPanel() {
/*  394 */     JPanel ret = new JPanel(new GridBagLayout());
/*  395 */     GridBagConstraints c = new GridBagConstraints();
/*  396 */     c.weightx = 1.0D;
/*  397 */     c.weighty = 1.0D;
/*  398 */     c.fill = 1;
/*  399 */     c.anchor = 10;
/*  400 */     ret.add(getSplash(), c);
/*  401 */     return ret;
/*      */   }
/*      */   
/*      */   public void displayStartScreen() {
/*      */     try {
/*  406 */       this.alreadyStarted = true;
/*  407 */       setLocation(SwingUtils.getXInitial(this), SwingUtils.getYInitial(this));
/*  408 */       if (this.startPanel != null) {
/*  409 */         getJContentPane().remove(this.startPanel);
/*  410 */         this.startPanel = null;
/*      */       } 
/*  412 */       this.startPanel = createStartPanel();
/*  413 */       setContentPane(this.startPanel);
/*  414 */       this.buttons.setVisible(false);
/*  415 */       pack();
/*  416 */       setVisible(true);
/*  417 */       addComponentListener(new ComponentAdapter() {
/*      */             public void componentResized(ComponentEvent event) {
/*  419 */               SPSFrame.this.componentResizedAction();
/*      */             }
/*      */           });
/*  422 */     } catch (Exception e) {
/*  423 */       log.error("unable to display start screen, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeStartScreen() {
/*      */     try {
/*  429 */       setContentPane(this.jContentPane);
/*  430 */       setCursor(new Cursor(0));
/*  431 */       getJContentPane().remove(this.startPanel);
/*  432 */       this.startPanel = null;
/*  433 */       this.buttons.getInfoBarStatus().setText("  ");
/*  434 */       setLocation(SwingUtils.getX_POS(this), SwingUtils.getY_POS(this));
/*  435 */       this.buttons.setVisible(true);
/*  436 */       this.jContentPane.setSize(SwingUtils.getScreenSize(this));
/*  437 */       this.jContentPane.setPreferredSize(SwingUtils.getScreenSize(this));
/*  438 */       setSize(SwingUtils.getScreenSize(this));
/*  439 */       setCloseState(true);
/*  440 */       this.ORIGIN_X = (int)getPreferredSize().getWidth();
/*  441 */       this.ORIGIN_Y = (int)getPreferredSize().getHeight();
/*  442 */       pack();
/*      */       
/*  444 */       addComponentListener(new ComponentAdapter() {
/*      */             public void componentResized(ComponentEvent event) {
/*  446 */               SPSFrame.this.componentResizedAction();
/*      */             }
/*      */           });
/*  449 */       setResizable(true);
/*  450 */     } catch (Exception e) {
/*  451 */       log.error("unable to remove the start screen, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean existsValidateVINPanel() {
/*  456 */     for (int i = 0; i < stackForms.size(); i++) {
/*  457 */       Object panel = stackForms.get(i);
/*  458 */       if (panel instanceof ValidateVINPanel) {
/*  459 */         return true;
/*      */       }
/*      */     } 
/*  462 */     return false;
/*      */   }
/*      */   
/*      */   protected ValidateVINPanel reuseValidateVINPanel() {
/*  466 */     ValidateVINPanel target = null;
/*  467 */     List<Object> panels = new ArrayList();
/*  468 */     for (int i = 0; i < stackForms.size(); i++) {
/*  469 */       Object panel = stackForms.get(i);
/*  470 */       if (panel instanceof ValidateVINPanel) {
/*  471 */         target = (ValidateVINPanel)panel;
/*      */         break;
/*      */       } 
/*  474 */       panels.add(panel);
/*      */     } 
/*  476 */     stackForms.retainAll(panels);
/*  477 */     this.buttons.getProceedButton().setVisible(false);
/*  478 */     this.buttons.getNewButton().setVisible(false);
/*  479 */     this.buttons.getClearDTCsButton().setVisible(false);
/*  480 */     this.buttons.getNextButton().setVisible(true);
/*  481 */     setBackButtonState(false);
/*  482 */     setCancelButtonState(true);
/*  483 */     setCloseState(true);
/*  484 */     target.disableInput();
/*  485 */     return target;
/*      */   }
/*      */   
/*      */   protected void executeRequest(AssignmentRequest req) {
/*      */     try {
/*  490 */       JPanel newPanel = null;
/*  491 */       setNextButtonState(false);
/*  492 */       this.prevPanel = this.topPanel;
/*      */       
/*  494 */       if (req instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/*  495 */         this.tools = (List)DisplayUtil.createDisplayAdapter(((SelectionRequest)req).getOptions());
/*  496 */         SelectDiagnosticToolPanel selectDiagnosticToolPanel = new SelectDiagnosticToolPanel(this, null);
/*      */       }
/*  498 */       else if (req instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) {
/*  499 */         SelectControllerPanel selectControllerPanel = new SelectControllerPanel(this, this.prevPanel);
/*      */       }
/*  501 */       else if (req instanceof com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest) {
/*  502 */         CalibrationSelectPanel calibrationSelectPanel = new CalibrationSelectPanel(this, this.prevPanel);
/*      */       }
/*  504 */       else if (req instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest || req instanceof com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest) {
/*  505 */         if (this.topPanel instanceof TransferDataPanel) {
/*      */           return;
/*      */         }
/*  508 */         TransferDataPanel transferDataPanel = new TransferDataPanel(this, this.prevPanel);
/*      */       }
/*  510 */       else if (req instanceof com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest) {
/*  511 */         HardwarePanel hardwarePanel = new HardwarePanel(this, this.prevPanel, req);
/*      */       }
/*  513 */       else if (req instanceof SelectionRequest) {
/*  514 */         if (req.getRequestGroup() instanceof VCExtRequestGroup) {
/*  515 */           setInitialNextButtonState(this.buttons.getNextButton().isEnabled());
/*  516 */           VehicleDataPanel vehicleDataPanel = new VehicleDataPanel(this, this.prevPanel, (VCExtRequestGroup)req.getRequestGroup());
/*      */         } else {
/*  518 */           VehicleDataPanel vehicleDataPanel = new VehicleDataPanel(this, this.prevPanel);
/*      */         }
/*      */       
/*  521 */       } else if (req instanceof com.eoos.gm.tis2web.sps.common.VINDisplayRequest) {
/*  522 */         if (this.agent.isProceedPossible() && existsValidateVINPanel()) {
/*  523 */           ValidateVINPanel validateVINPanel = reuseValidateVINPanel();
/*      */         } else {
/*  525 */           ValidateVINPanel validateVINPanel = new ValidateVINPanel(this, this.prevPanel);
/*      */         } 
/*      */       } else {
/*  528 */         if (req instanceof com.eoos.gm.tis2web.sps.common.VINConfirmationRequest) {
/*  529 */           throw new IllegalArgumentException();
/*      */         }
/*  531 */         if (req instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest) {
/*  532 */           if (req instanceof ControllerSecurityCodeRequest) {
/*  533 */             SecurityCodePanel securityCodePanel = new SecurityCodePanel(this.prevPanel, (ControllerSecurityCodeRequest)req, this.vin, isNAO());
/*  534 */           } else if (req.getAttribute().equals(CommonAttribute.KEYCODE)) {
/*  535 */             KeyCodePanel keyCodePanel = new KeyCodePanel(this.prevPanel);
/*      */           }
/*  537 */           else if (req.getAttribute().equals(CommonAttribute.SECURITY_CODE)) {
/*  538 */             SecurityCodePanel securityCodePanel = new SecurityCodePanel(this.prevPanel, this.vin, isNAO());
/*      */           }
/*  540 */           else if (req.getAttribute().equals(CommonAttribute.CONTROLLER_SECURITY_CODE)) {
/*  541 */             SecurityCodePanel securityCodePanel = new SecurityCodePanel(this.prevPanel, null, isNAO());
/*      */           }
/*  543 */           else if (req.getAttribute().equals(CommonAttribute.ODOMETER)) {
/*  544 */             OdometerPanel odometerPanel = new OdometerPanel(this.prevPanel);
/*      */           }
/*  546 */           else if (req.getAttribute().equals(CommonAttribute.VCI)) {
/*  547 */             ProgrammingVCIPanel programmingVCIPanel = new ProgrammingVCIPanel(this.prevPanel);
/*      */           }
/*      */         
/*  550 */         } else if (req instanceof com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest) {
/*  551 */           lockOn();
/*  552 */           this.delayedLockOff = true;
/*      */ 
/*      */           
/*  555 */           SummaryPanel summaryPanel = new SummaryPanel(this.prevPanel);
/*      */         }
/*  557 */         else if (req instanceof InstructionDisplayRequest) {
/*  558 */           String content = ((InstructionDisplayRequest)req).getContent();
/*  559 */           List instructions = ((InstructionDisplayRequest)req).getInstructions();
/*  560 */           newPanel = new ViewerPanel(content, instructions, this, this.prevPanel);
/*  561 */         } else if (req instanceof HtmlDisplayRequest) {
/*  562 */           String urlString = ((HtmlDisplayRequest)req).getHtmlCode();
/*  563 */           newPanel = new ViewerPanel(urlString, "title: " + urlString, this, this.prevPanel);
/*      */         } 
/*  565 */       }  this.buttons.getPrevButton().setVisible(!(req instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest));
/*  566 */       updateUI(newPanel, req);
/*      */     }
/*  568 */     catch (Exception e) {
/*  569 */       log.error("unable to display request panel: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRequest(AssignmentRequest req) {
/*  575 */     if (this.isActiveTestSummary) {
/*      */       return;
/*      */     }
/*  578 */     if (this.startPanel != null) {
/*  579 */       removeStartScreen();
/*      */     }
/*  581 */     conditionalLockOff();
/*      */     try {
/*  583 */       if (req == null) {
/*      */         return;
/*      */       }
/*  586 */       log.debug("handle request: " + req.getAttribute());
/*  587 */       if (this.topPanel == null) {
/*  588 */         executeRequest(req);
/*  589 */       } else if (req instanceof com.eoos.gm.tis2web.sps.common.ReprogramProgressDisplayRequest) {
/*  590 */         if (this.topPanel instanceof TransferDataPanel) {
/*  591 */           req.setRequestGroup(this.topPanel.getRequestGroup());
/*      */         } else {
/*  593 */           executeRequest(req);
/*      */           return;
/*      */         } 
/*      */       } 
/*  597 */       if (req.getRequestGroup().equals(this.topPanel.getRequestGroup())) {
/*  598 */         setNextButtonState(false);
/*  599 */         this.topPanel.handleRequest(req);
/*      */       } else {
/*      */         
/*  602 */         executeRequest(req);
/*      */       } 
/*  604 */     } catch (Exception e) {
/*  605 */       log.error("unable to handle request: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleException(String msg) {
/*      */     try {
/*  611 */       lockOff();
/*  612 */       QuestionDialog dialog = new QuestionDialog(this);
/*  613 */       dialog.displayMessage(msg, getTitle(), 0);
/*      */     }
/*  615 */     catch (Exception except) {
/*  616 */       log.error("unable to handle Custom Exception from Server, -exception: " + except.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleException(CustomException e) {
/*  621 */     String msg = e.getDenotation(null);
/*      */     try {
/*  623 */       lockOff();
/*  624 */       QuestionDialog dialog = new QuestionDialog(this);
/*  625 */       dialog.displayMessage(msg, getTitle(), 0);
/*      */     }
/*  627 */     catch (Exception except) {
/*  628 */       log.error("unable to handle Custom Exception from Server, -exception: " + except.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleException(Exception e) {
/*      */     try {
/*  634 */       String msg = null;
/*  635 */       if (e instanceof CommunicationException) {
/*  636 */         msg = CommonException.CommunicationException.getDenotation(null);
/*  637 */         String servername = ((CommunicationException)e).getServerName();
/*  638 */         if (Util.isNullOrEmpty(servername)) {
/*  639 */           servername = System.getProperty("server.name");
/*      */         }
/*  641 */         msg = StringUtilities.replace(msg, "${server.name}", servername);
/*      */       } else {
/*      */         
/*  644 */         msg = e.getLocalizedMessage();
/*  645 */         log.error("handleException ,initial Error -typ Exception" + msg);
/*  646 */         if (msg == null) {
/*  647 */           msg = e.getMessage();
/*      */         }
/*  649 */         if (msg == null) {
/*  650 */           msg = e.toString();
/*      */         }
/*      */       } 
/*  653 */       lockOff();
/*  654 */       QuestionDialog dialog = new QuestionDialog(this);
/*  655 */       dialog.displayMessage(msg, getTitle(), 0);
/*      */     }
/*  657 */     catch (Exception except) {
/*  658 */       log.error("unable to handle Exception from Server, -exception: " + except, except);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void displayErrorMessage(String title, String msg) {
/*      */     try {
/*  664 */       JOptionPane.showMessageDialog(null, msg, title, 0);
/*  665 */     } catch (Exception except) {
/*  666 */       log.error("unable to display error message, -exception: " + except, except);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void displayErrorMessage(String msg) {
/*      */     try {
/*  672 */       JOptionPane.showMessageDialog(null, msg, resourceProvider.getLabel(null, "app.title"), 0);
/*  673 */     } catch (Exception except) {
/*  674 */       log.error("unable to display error message, -exception: " + except, except);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void displayMessage(String msg) {
/*      */     try {
/*  680 */       lockOff();
/*  681 */       QuestionDialog dialog = new QuestionDialog(this);
/*  682 */       dialog.displayMessage(msg, getTitle(), 0);
/*      */     }
/*  684 */     catch (Exception except) {
/*  685 */       log.error("unable to display message, -exception: " + except, except);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void displayMessage(String severity, String title, String msg) {
/*      */     try {
/*  691 */       lockOff();
/*  692 */       int level = severity.equalsIgnoreCase("error") ? 0 : (severity.equalsIgnoreCase("warning") ? 2 : (severity.equalsIgnoreCase("info") ? 1 : -1));
/*  693 */       QuestionDialog dialog = new QuestionDialog(this);
/*  694 */       if (title == null || title.trim().length() == 0) {
/*  695 */         title = getTitle();
/*      */       }
/*  697 */       dialog.displayMessage(msg, title, level);
/*      */     }
/*  699 */     catch (Exception except) {
/*  700 */       log.error("unable to display message, -exception: " + except, except);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean displayQuestionDialog(String message) {
/*      */     try {
/*  706 */       lockOff();
/*  707 */       QuestionDialog dialog = new QuestionDialog(this);
/*  708 */       return dialog.dialogQuestionMessage(message, getTitle());
/*      */     }
/*  710 */     catch (Exception except) {
/*  711 */       log.error("unable to display question message, -exception: " + except, except);
/*      */       
/*  713 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayMessageDialog(String message) {
/*  719 */     if (this.dialog == null) {
/*  720 */       lockOn();
/*  721 */       this.dialog = new MessageDialog(this, message);
/*  722 */       this.dialog.setVisible(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateMessageDialog(String message) {
/*  727 */     if (this.dialog == null) {
/*      */       return;
/*      */     }
/*  730 */     if (this.dialog.isVisible()) {
/*  731 */       this.dialog.updateJText(message);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeMessageDialog() {
/*      */     try {
/*  737 */       if (this.dialog == null)
/*      */         return; 
/*  739 */       lockOff();
/*  740 */       if (this.dialog.isVisible()) {
/*  741 */         this.dialog.closeDialog();
/*  742 */         if (!this.dialog.isVisible()) {
/*  743 */           this.dialog = null;
/*      */         }
/*      */       } 
/*  746 */     } catch (Exception e) {
/*  747 */       log.error("Error in removeMessageDialog: " + e, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleProgrammingSelection() {
/*  753 */     lockOn();
/*  754 */     this.agent.execute((SelectionResult)new SelectionResultImpl(CommonAttribute.PROGRAMMING_DATA_SELECTION), (ValueRetrieval)null);
/*  755 */     lockOff();
/*      */   }
/*      */   
/*      */   public void handleFinishDownload() {
/*  759 */     lockOn();
/*      */     try {
/*  761 */       this.agent.execute((SelectionResult)new SelectionResultImpl(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED), (ValueRetrieval)null);
/*  762 */     } catch (Exception x) {}
/*      */     
/*  764 */     lockOff();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSelection(final Attribute attribute, final Value value) {
/*  769 */     Thread worker = new Thread() {
/*      */         public void run() {
/*  771 */           SPSFrame.this.executeHandleSelection(attribute, value);
/*      */         }
/*      */       };
/*  774 */     lockOn();
/*  775 */     worker.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public void executeHandleSelection(Attribute attribute, Value value) {
/*  780 */     this.agent.execute((SelectionResult)new SelectionResultImpl(attribute), this);
/*  781 */     if (this.isActiveTestSummary)
/*      */       return; 
/*  783 */     lockOff();
/*      */   }
/*      */   
/*      */   private void activeBlockingEvents(boolean active) {
/*  787 */     if (active) {
/*  788 */       this.eventsBlocker.addPermittedComponent(this.buttons.getNewButton());
/*  789 */       this.eventsBlocker.addPermittedComponent(this.buttons.getEcuDataButton());
/*  790 */       this.eventsBlocker.setBlockingEnabled(true);
/*  791 */       log.debug("Activate Blocking Events");
/*      */     } else {
/*      */       
/*  794 */       this.eventsBlocker.setBlockingEnabled(false);
/*  795 */       this.eventsBlocker.removePermittedComponent(this.buttons.getEcuDataButton());
/*  796 */       this.eventsBlocker.removePermittedComponent(this.buttons.getNewButton());
/*  797 */       log.debug("Inactivate Blocking Events");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void lockOn() {
/*  802 */     this.delayedLockOff = false;
/*  803 */     setInfoOnBarStatus(resourceProvider.getMessage(null, "sps.server.connect"));
/*  804 */     this.eventsBlocker.setBlockingEnabled(true);
/*  805 */     setCursor(new Cursor(3));
/*  806 */     log.debug("LOCK ON");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDelayedLockOff() {
/*  812 */     if (this.delayedLockOff) {
/*  813 */       lockOff();
/*  814 */       this.delayedLockOff = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void blockGUI() {
/*  819 */     if (this.buttons.getNextButton().isVisible() && !this.buttons.getNextButton().isEnabled()) {
/*  820 */       lockOn();
/*  821 */       this.delayedLockOff = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void unblockGUI() {
/*  826 */     this.eventsBlocker.setBlockingEnabled(false);
/*  827 */     setInfoOnBarStatus("  ");
/*  828 */     setCursor(new Cursor(0));
/*  829 */     log.debug("FORCE LOCK OFF");
/*      */   }
/*      */   
/*      */   private void conditionalLockOff() {
/*  833 */     if (this.buttons.getNextButton().isVisible() && !this.buttons.getNextButton().isEnabled()) {
/*  834 */       this.delayedLockOff = true;
/*  835 */       log.debug("DELAY LOCK OFF");
/*      */     } else {
/*  837 */       lockOff();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void lockOff() {
/*  842 */     this.eventsBlocker.setBlockingEnabled(false);
/*  843 */     setInfoOnBarStatus("  ");
/*  844 */     setCursor(new Cursor(0));
/*  845 */     log.debug("LOCK OFF");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Value getValue(Attribute attr) {
/*  852 */     if (this.topPanel != null) {
/*  853 */       return this.topPanel.getValue(attr);
/*      */     }
/*  855 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCloseWindow() {
/*  862 */     System.exit(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCancelAction() {
/*  869 */     removeMessageDialog();
/*  870 */     if (this.currentPanel != null && 
/*  871 */       this.currentPanel instanceof BaseCustomizeJPanel)
/*  872 */       ((BaseCustomizeJPanel)this.currentPanel).onCancelAction(); 
/*  873 */     onCloseWindow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNewAction() {
/*  880 */     Thread thread = new Thread() {
/*      */         public void run() {
/*  882 */           SPSFrame.this.isActiveBlockingEvents = false;
/*  883 */           SPSFrame.this.activeBlockingEvents(false);
/*  884 */           SPSFrame.this.agent.restart();
/*      */         }
/*      */       };
/*  887 */     thread.start();
/*      */   }
/*      */   
/*      */   public void onProceedAction() {
/*  891 */     Thread thread = new Thread() {
/*      */         public void run() {
/*  893 */           SPSFrame.this.isActiveBlockingEvents = false;
/*  894 */           SPSFrame.this.activeBlockingEvents(false);
/*  895 */           SPSFrame.this.agent.proceed();
/*      */         }
/*      */       };
/*  898 */     thread.start();
/*      */   }
/*      */   
/*      */   protected boolean handleNextInstruction() {
/*  902 */     if (!((ViewerPanel)this.currentPanel).isLast()) {
/*  903 */       ((ViewerPanel)this.currentPanel).advance();
/*  904 */       if (((ViewerPanel)this.currentPanel).isFinalInstruction()) {
/*  905 */         if (((ViewerPanel)this.currentPanel).isLast()) {
/*  906 */           finish();
/*      */         } else {
/*  908 */           setBackButtonState(true);
/*      */         } 
/*      */       }
/*  911 */       return true;
/*      */     } 
/*  913 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNextAction() {
/*  920 */     if (this.currentPanel instanceof ViewerPanel && (
/*  921 */       (ViewerPanel)this.currentPanel).isInstructionRequest() && handleNextInstruction()) {
/*      */       return;
/*      */     }
/*      */     
/*  925 */     this.isBackAction = false;
/*  926 */     Thread thread = new Thread() {
/*      */         public void run() {
/*  928 */           if (SPSFrame.this.currentPanel != null && 
/*  929 */             !((BaseCustomizeJPanel)SPSFrame.this.currentPanel).onNextAction()) {
/*      */             return;
/*      */           }
/*  932 */           SPSFrame.this.lockOn();
/*  933 */           SPSFrame.this.agent.triggerNextRequest();
/*      */         }
/*      */       };
/*  936 */     thread.start();
/*      */   }
/*      */   
/*      */   protected boolean handleBackInstruction() {
/*  940 */     if (!((ViewerPanel)this.currentPanel).isFirst()) {
/*  941 */       setCursor(new Cursor(3));
/*  942 */       ((ViewerPanel)this.currentPanel).moveBack();
/*  943 */       setCursor(new Cursor(0));
/*  944 */       if (((ViewerPanel)this.currentPanel).isFinalInstruction()) {
/*  945 */         this.buttons.getNextButton().setEnabled(true);
/*  946 */         if (((ViewerPanel)this.currentPanel).isFirst()) {
/*  947 */           setBackButtonState(false);
/*      */         }
/*      */       } 
/*  950 */       return true;
/*      */     } 
/*  952 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBackAction() {
/*      */     try {
/*  960 */       if (this.currentPanel instanceof ViewerPanel && (
/*  961 */         (ViewerPanel)this.currentPanel).isInstructionRequest() && handleBackInstruction()) {
/*  962 */         displayClearDTCsButton();
/*      */         
/*      */         return;
/*      */       } 
/*  966 */       if (this.isActiveBlockingEvents)
/*      */       {
/*  968 */         activeBlockingEvents(false);
/*      */       }
/*  970 */       this.isBackAction = true;
/*  971 */       setNextButtonState(true);
/*  972 */       if (!stackForms.empty()) {
/*  973 */         if (this.currentPanel != null)
/*  974 */           ((BaseCustomizeJPanel)this.currentPanel).onBackAction(); 
/*  975 */         BaseCustomizeJPanel pnl = stackForms.pop();
/*  976 */         lockOn();
/*  977 */         this.agent.undo();
/*  978 */         lockOff();
/*  979 */         this.topPanel = pnl.getPreviousPanel();
/*  980 */         if (this.topPanel != null) {
/*  981 */           switchPanel(this.topPanel);
/*  982 */           if (this.topPanel.getPreviousPanel() == null) {
/*  983 */             this.buttons.getPrevButton().setVisible(false);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  988 */     } catch (Exception e) {
/*  989 */       log.error("back Action , -exception: " + e.getMessage());
/*  990 */       lockOff();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSettingsAction() {
/*  998 */     SettingsDialog setDialog = new SettingsDialog(this);
/*  999 */     setDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetSelection() {
/* 1007 */     if (this.currentPanel != null && this.currentPanel instanceof SelectDiagnosticToolPanel) {
/* 1008 */       ((SelectDiagnosticToolPanel)this.currentPanel).resetSelection();
/* 1009 */       setNextButtonState(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPrintAction() {
/* 1015 */     if (this.currentPanel instanceof SummaryPanel) {
/* 1016 */       PDFStarter pdf = null;
/*      */       try {
/* 1018 */         String vin = this.buttons.getVINBarStatus().getText();
/* 1019 */         SummaryPanel summary = (SummaryPanel)this.currentPanel;
/* 1020 */         pdf = new PDFStarter(summary.getSummaryTableModel(), summary.getVehicleDataTableModel(), summary.getDescriptionTableModel(), vin);
/* 1021 */         pdf.printSummaryPDF();
/* 1022 */         pdf.openPDF();
/* 1023 */       } catch (FileNotFoundException e) {
/* 1024 */         log.error("onPrintAction, exception: " + e.getMessage());
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1029 */     PrintableComponent printComponent = new PrintableComponent(getContentPane());
/*      */     try {
/* 1031 */       ClientSettings clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/* 1032 */       String paperFormat = clientSettings.getProperty("print.paper.format");
/* 1033 */       String pageOrient = clientSettings.getProperty("print.page.orientation");
/* 1034 */       printComponent.print(pageOrient, paperFormat);
/* 1035 */     } catch (Exception e) {
/* 1036 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVIT1(VIT1 vit1, Integer device) {
/* 1044 */     this.vit1 = vit1;
/* 1045 */     this.device = device;
/*      */   }
/*      */   
/*      */   public void onECUDataAction() {
/*      */     try {
/* 1050 */       if (this.isActiveBlockingEvents) {
/* 1051 */         activeBlockingEvents(false);
/*      */       }
/* 1053 */       ECUData ecuData = new ECUData(this.vit1, this.device);
/* 1054 */       ECUDataDialog ecuDataDialog = new ECUDataDialog(this, ecuData.getECUData());
/* 1055 */       ecuDataDialog.setVisible(true);
/*      */       
/* 1057 */       if (this.isActiveBlockingEvents)
/* 1058 */         activeBlockingEvents(true); 
/* 1059 */     } catch (Exception e) {
/* 1060 */       log.error("ecuData Action, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onResetAction() {
/*      */     try {
/* 1070 */       if (this.currentPanel != null) {
/* 1071 */         ((BaseCustomizeJPanel)this.currentPanel).onResetAction();
/* 1072 */         this.agent.reset();
/*      */       } 
/* 1074 */     } catch (Exception e) {
/* 1075 */       log.error("reset Action, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onHistoryAction() {
/*      */     try {
/* 1081 */       HistoryData historyData = new HistoryData(((SummaryPanel)this.currentPanel).getHistory());
/* 1082 */       HistoryDialog history = new HistoryDialog(this, historyData.getHistoryData());
/* 1083 */       history.setVisible(true);
/* 1084 */     } catch (Exception e) {
/* 1085 */       log.error("History Action, -exception: " + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onModuleInfoAction() {
/* 1090 */     Vector modInfoData = ((SummaryPanel)this.currentPanel).getModuleInfoData();
/* 1091 */     if (modInfoData != null) {
/* 1092 */       ModuleDialog module = new ModuleDialog(this, modInfoData);
/* 1093 */       module.setVisible(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onClearDTCsAction() {
/* 1098 */     setCursor(new Cursor(3));
/* 1099 */     DTCServiceImpl.getInstance().executeClearDTCs();
/* 1100 */     displayClearDTCsButton();
/* 1101 */     setCursor(new Cursor(0));
/*      */   }
/*      */   
/*      */   public void setInitialNextButtonState(boolean state) {
/* 1105 */     this.initialStatusNextButton = state;
/*      */   }
/*      */   
/*      */   public boolean getInitialNextButtonState() {
/* 1109 */     return this.initialStatusNextButton;
/*      */   }
/*      */   
/*      */   public void setNextButtonState(final boolean state) {
/* 1113 */     Runnable doWorkRunnable = new Runnable() {
/*      */         public void run() {
/* 1115 */           SPSFrame.log.debug(" set next button state: " + state);
/* 1116 */           SPSFrame.this.buttons.getNextButton().setEnabled(state);
/* 1117 */           if (state) {
/* 1118 */             SPSFrame.this.checkDelayedLockOff();
/*      */           }
/*      */         }
/*      */       };
/* 1122 */     SwingUtilities.invokeLater(doWorkRunnable);
/*      */   }
/*      */   
/*      */   public void setBackButtonState(boolean state) {
/* 1126 */     log.debug("set back button state: " + state);
/* 1127 */     this.buttons.getPrevButton().setEnabled(state);
/*      */   }
/*      */   
/*      */   public void setCancelButtonState(boolean state) {
/* 1131 */     log.debug("set cancel button state: " + state);
/* 1132 */     this.buttons.getCancelButton().setEnabled(state);
/*      */   }
/*      */   
/*      */   public void setCloseState(boolean state) {
/* 1136 */     this.activeXClose = state;
/* 1137 */     if (state) {
/* 1138 */       log.debug("Close X aktiv");
/* 1139 */       setDefaultCloseOperation(1);
/*      */     } else {
/*      */       
/* 1142 */       log.debug("Close X inaktiv");
/* 1143 */       setDefaultCloseOperation(0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setInfoOnBarStatus(String info) {
/* 1148 */     this.buttons.setInfoBarStatus(info);
/*      */   }
/*      */   
/*      */   public void setVINOnBarStatus(String vin) {
/* 1152 */     if (vin == null) {
/* 1153 */       this.buttons.getVINBarStatus().setText("");
/*      */     } else {
/* 1155 */       this.vin = vin;
/* 1156 */       this.buttons.getVINBarStatus().setText("VIN: " + vin);
/*      */     } 
/* 1158 */     this.buttons.getBarStatusPanel();
/*      */   }
/*      */ 
/*      */   
/*      */   public void showTestSummary(DefaultTableModel summary) {
/* 1163 */     if (summary == null) {
/* 1164 */       log.debug("showTestSummary DONE");
/* 1165 */       this.isActiveTestSummary = false;
/* 1166 */       this.buttons.getPrevButton().setVisible(false);
/* 1167 */       this.buttons.getCancelButton().setText("Done");
/* 1168 */       lockOff();
/*      */     } else {
/*      */       
/* 1171 */       this.isActiveTestSummary = true;
/* 1172 */       log.debug("showTestSummary START");
/* 1173 */       lockOn();
/* 1174 */       if (this.currentPanel != null) {
/* 1175 */         getJContentPane().remove(this.currentPanel);
/*      */       }
/* 1177 */       TestSummary testSummary = new TestSummary(summary, null);
/* 1178 */       this.currentPanel = (JPanel)testSummary;
/* 1179 */       getJContentPane().add(this.currentPanel, "Center");
/* 1180 */       testSummary.moveScroll();
/* 1181 */       this.buttons.getWestButtonPanel().setVisible(false);
/* 1182 */       this.buttons.getPrevButton().setVisible(false);
/* 1183 */       this.buttons.getNextButton().setVisible(false);
/* 1184 */       this.buttons.getCancelButton().setVisible(true);
/* 1185 */       pack();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void requestBulletinDisplay(String bulletin) {
/* 1191 */     this.agent.requestBulletinDisplay(bulletin);
/*      */   }
/*      */   
/*      */   public void displayHTML(String html) {
/* 1195 */     new ViewerHTMLDialog(this, html);
/*      */   }
/*      */   
/*      */   public void finish() {
/* 1199 */     displayClearDTCsButton();
/* 1200 */     if (this.currentPanel instanceof ViewerPanel) {
/* 1201 */       if (!((ViewerPanel)this.currentPanel).isLast())
/*      */         return; 
/* 1203 */       if (this.buttons.getPrevButton().isEnabled()) {
/*      */ 
/*      */ 
/*      */         
/* 1207 */         this.buttons.getNextButton().setEnabled(false);
/* 1208 */         this.buttons.getNewButton().setVisible(true);
/*      */         return;
/*      */       } 
/* 1211 */     } else if (!(this.currentPanel instanceof SummaryPanel)) {
/* 1212 */       this.isActiveBlockingEvents = true;
/* 1213 */       activeBlockingEvents(true);
/*      */     } 
/* 1215 */     if (this.agent.isProceedPossible()) {
/* 1216 */       this.buttons.getProceedButton().setVisible(true);
/*      */     }
/* 1218 */     this.buttons.getNewButton().setVisible(true);
/* 1219 */     this.buttons.getNextButton().setVisible(false);
/* 1220 */     this.buttons.getPrevButton().setVisible(false);
/* 1221 */     this.buttons.hideTemporaryButtonsExceptOne(this.buttons.getEcuDataButton());
/* 1222 */     this.buttons.getPrintButton().setVisible(true);
/*      */   }
/*      */   
/*      */   public void changeStateButtons() {
/* 1226 */     this.isActiveBlockingEvents = true;
/* 1227 */     activeBlockingEvents(true);
/* 1228 */     this.buttons.getNewButton().setVisible(true);
/* 1229 */     this.buttons.getNextButton().setVisible(false);
/* 1230 */     this.buttons.getPrevButton().setVisible(true);
/* 1231 */     this.buttons.getPrevButton().setEnabled(true);
/* 1232 */     this.buttons.hideTemporaryButtonsExceptOne(this.buttons.getEcuDataButton());
/* 1233 */     this.buttons.getPrintButton().setVisible(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public String requestInstructionHTML(String instructionID) throws Exception {
/* 1238 */     return this.agent.requestInstructionHTML(instructionID);
/*      */   }
/*      */   
/*      */   public byte[] requestInstructionImage(String imageID) throws Exception {
/* 1242 */     return this.agent.requestInstructionImage(imageID);
/*      */   }
/*      */   
/*      */   public String displayFileDialog(String title, String filter, List fileTypes, String initialDir) {
/* 1246 */     SecurityManager secMan = null;
/*      */     try {
/* 1248 */       lockOff();
/* 1249 */       secMan = System.getSecurityManager();
/* 1250 */       System.setSecurityManager(null);
/* 1251 */       JFileChooser dlg = new JFileChooser(new File(initialDir));
/* 1252 */       dlg.setDialogTitle(title);
/* 1253 */       ImportFileFilter importFileFilter = new ImportFileFilter(filter, fileTypes);
/* 1254 */       dlg.setFileFilter((FileFilter)importFileFilter);
/* 1255 */       if (dlg.showOpenDialog(null) == 0) {
/* 1256 */         return dlg.getSelectedFile().getPath();
/*      */       }
/*      */     } finally {
/* 1259 */       if (secMan != null) {
/* 1260 */         System.setSecurityManager(secMan);
/*      */       }
/* 1262 */       lockOn();
/*      */     } 
/* 1264 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayReprogramStatusPS(List<Pair> state) {
/* 1269 */     JPanel panel = new JPanel(new BorderLayout());
/* 1270 */     JTable table = new JTable();
/*      */     
/* 1272 */     DefaultTableModel mtable = new DefaultTableModel() {
/*      */         private static final long serialVersionUID = 1L;
/*      */         
/*      */         public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 1276 */           return false;
/*      */         }
/*      */       };
/* 1279 */     mtable.setColumnCount(2);
/*      */     
/* 1281 */     for (int i = 0; i < state.size(); i++) {
/* 1282 */       Pair s = state.get(i);
/* 1283 */       String label = null;
/* 1284 */       Integer result = (Integer)s.getSecond();
/* 1285 */       if (result.equals(ProgrammingSequence.FAIL)) {
/* 1286 */         label = resourceProvider.getLabel(null, "programming-sequence.fail");
/* 1287 */       } else if (result.equals(ProgrammingSequence.SKIP)) {
/* 1288 */         label = resourceProvider.getLabel(null, "programming-sequence.skip");
/*      */       } else {
/* 1290 */         label = resourceProvider.getLabel(null, "programming-sequence.success");
/*      */       } 
/* 1292 */       String controller = (String)s.getFirst();
/* 1293 */       controller = controller.replace('\t', ' ');
/* 1294 */       mtable.addRow(new Object[] { label, controller });
/*      */     } 
/* 1296 */     table.setModel(mtable);
/* 1297 */     TableUtilities.setColumnMaxMin(table, 0);
/* 1298 */     table.setShowGrid(false);
/* 1299 */     table.setSelectionMode(0);
/* 1300 */     table.setTableHeader((JTableHeader)null);
/* 1301 */     JScrollPane scrollPane = new JScrollPane();
/* 1302 */     scrollPane.getViewport().setBackground(Color.WHITE);
/* 1303 */     scrollPane.setViewportView(table);
/* 1304 */     Dimension d = scrollPane.getPreferredSize();
/* 1305 */     scrollPane.setPreferredSize(new Dimension(d.width, (table.getPreferredSize()).height + 20));
/* 1306 */     panel.add(scrollPane, "Center");
/*      */     
/* 1308 */     String title = resourceProvider.getLabel(null, "programming-sequence.report");
/*      */     
/*      */     try {
/* 1311 */       lockOff();
/* 1312 */       JOptionPane.showMessageDialog(this, scrollPane, title, 1);
/* 1313 */     } catch (Exception except) {
/* 1314 */       log.error("unable to display programming sequence status report, -exception: " + except.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean displayQuestionHTMLMessage(String errMsg, List traceInfoList) {
/* 1319 */     lockOff();
/* 1320 */     HTMLMessageDialog msgDialog = new HTMLMessageDialog(this);
/* 1321 */     return msgDialog.displayQuestionHTMLMessage(errMsg, traceInfoList);
/*      */   }
/*      */   
/*      */   public void displayHTMLMessage(String errMsg, List traceInfoList) {
/* 1325 */     lockOff();
/* 1326 */     HTMLMessageDialog msgDialog = new HTMLMessageDialog(this);
/* 1327 */     msgDialog.displayHTMLMessage(errMsg, traceInfoList);
/*      */   }
/*      */   
/*      */   public List getTools() {
/* 1331 */     return this.tools;
/*      */   }
/*      */   
/*      */   public boolean isNAO() {
/* 1335 */     return this.agent.isNAO();
/*      */   }
/*      */   
/*      */   public boolean isGlobalAdapter() {
/* 1339 */     return this.agent.isGlobalAdapter();
/*      */   }
/*      */   
/*      */   public boolean supportSPSFunctions() {
/* 1343 */     return this.agent.supportSPSFunctions();
/*      */   }
/*      */   
/*      */   public boolean isFinalInstructionDisplayed() {
/* 1347 */     return this.agent.isFinalInstructionDisplayed();
/*      */   }
/*      */   
/*      */   protected void displayClearDTCsButton() {
/* 1351 */     this.buttons.getClearDTCsButton().setVisible(false);
/* 1352 */     if (isFinalInstructionDisplayed()) {
/* 1353 */       if (this.currentPanel instanceof ViewerPanel)
/*      */       {
/*      */         
/* 1356 */         if (!((ViewerPanel)this.currentPanel).isLast()) {
/*      */           return;
/*      */         }
/*      */       }
/* 1360 */       if (DTCServiceImpl.getInstance().isAutomaticallyClearDTCsMode()) {
/* 1361 */         this.buttons.getClearDTCsButton().setVisible(true);
/* 1362 */         if (DTCServiceImpl.getInstance().isAlreadyClearDTCsExecuted()) {
/* 1363 */           this.buttons.getClearDTCsButton().setEnabled(false);
/*      */         } else {
/* 1365 */           this.buttons.getClearDTCsButton().setEnabled(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateFontOnButtons() {
/* 1372 */     FontUtils.setFontSize(this.buttons);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\SPSFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */