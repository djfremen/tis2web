/*      */ package com.eoos.gm.tis2web.sps.client.ui;
/*      */ 
/*      */ import ca.beq.util.win32.registry.RegistryKey;
/*      */ import ca.beq.util.win32.registry.RegistryValue;
/*      */ import ca.beq.util.win32.registry.RootKey;
/*      */ import com.eoos.datatype.ExceptionWrapper;
/*      */ import com.eoos.datatype.gtwo.Pair;
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*      */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*      */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*      */ import com.eoos.gm.tis2web.frame.dwnld.install.Installer;
/*      */ import com.eoos.gm.tis2web.frame.export.common.KeepAliveTask;
/*      */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*      */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*      */ import com.eoos.gm.tis2web.frame.mail.relay.client.api.MailRelayingService;
/*      */ import com.eoos.gm.tis2web.frame.mail.relay.client.api.MailRelayingServiceFactory;
/*      */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*      */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*      */ import com.eoos.gm.tis2web.sps.client.common.ExecutionMode;
/*      */ import com.eoos.gm.tis2web.sps.client.dtc.impl.DTCServiceImpl;
/*      */ import com.eoos.gm.tis2web.sps.client.lt.SPSTime;
/*      */ import com.eoos.gm.tis2web.sps.client.serveracces.SPSClientFacadeProvider;
/*      */ import com.eoos.gm.tis2web.sps.client.settings.WarrantyClaimCodeStore;
/*      */ import com.eoos.gm.tis2web.sps.client.system.ServerTaskExecution;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ProgrammingResult;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ECUDataReadException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.MissingHardwareKeyException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.NativeSubsystemException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ReprogrammingException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolInitException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.VINReadException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534Tool;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.DTCUploadProvider;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.TestDriverImpl;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.TestDriverLog;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoMoreFilesException;
/*      */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoValidVIT1Exception;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.pdf.PDFStarter;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*      */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TransferDataPanel;
/*      */ import com.eoos.gm.tis2web.sps.client.util.FileManager;
/*      */ import com.eoos.gm.tis2web.sps.client.util.SPSClientUtil;
/*      */ import com.eoos.gm.tis2web.sps.client.vci.impl.VCICalculationImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*      */ import com.eoos.gm.tis2web.sps.common.SequenceSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.TypeSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VINReadRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ReprogramFunctionSequenceRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSEvent;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSLaborTimeConfiguration;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.BulletinDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramControllerRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramSequenceRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VIT1Request;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VehicleAttributeSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.SPSClientFacade;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ExceptionImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.SPSArchive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.SPSPartFile;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap.AttributeValueMapImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VIT1HistoryRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VehicleAttributeDefaultSelectionRequestImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VehicleAttributeSelectionRequestImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.DisplaySummaryRequestImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.VIT1DataImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapRI;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.mail.DataSource;
/*      */ import com.eoos.mail.FileDataSource;
/*      */ import com.eoos.propcfg.Configuration;
/*      */ import com.eoos.propcfg.util.ConfigurationUtil;
/*      */ import com.eoos.scsm.v2.util.Util;
/*      */ import com.eoos.util.PeriodicTask;
/*      */ import com.eoos.util.StringUtilities;
/*      */ import com.eoos.util.Task;
/*      */ import com.eoos.util.Transforming;
/*      */ import com.eoos.util.ZipUtil;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.text.DateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.table.DefaultTableModel;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SPSClientController
/*      */ {
/*  160 */   public static final Integer XML_DEVICE_ID = Integer.valueOf(253);
/*      */   
/*      */   public static final String BUILD_FILENAME = "BuildRecord.bld";
/*      */   
/*      */   public static final String TYPE4_FILENAME = "Type4App.dll";
/*      */   
/*      */   public static final String XML_FILENAME = "XMLFile.xml";
/*      */   
/*      */   public static final String VCI_ARCHIVE_A = "1001";
/*      */   
/*      */   public static final String VCI_ARCHIVE_B = "1002";
/*      */   
/*      */   public static final String ARCHIVE_EXTENSION = ".zip";
/*      */   
/*      */   public static final String PART_FILE_EXTENSION = ".prt";
/*      */   
/*      */   public static final String WARRANTY_CLAIM_INFO = "<tr><td>&nbsp;</td></tr><tr><td><b>{LABEL} {CODE}</b></td></tr><tr><td>{INSTRUCTION}</td></tr><tr><td>&nbsp;</td></tr>";
/*      */   
/*  178 */   private static final RootKey[] rootKeys = new RootKey[] { RootKey.HKEY_CLASSES_ROOT, RootKey.HKEY_CURRENT_CONFIG, RootKey.HKEY_CURRENT_USER, RootKey.HKEY_DYN_DATA, RootKey.HKEY_LOCAL_MACHINE, RootKey.HKEY_PERFORMANCE_DATA, RootKey.HKEY_USERS };
/*      */   
/*      */   protected static LabelResource resourceProvider;
/*      */   
/*  182 */   public final RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*      */   
/*      */   private boolean bHasBldFile = false;
/*      */   
/*      */   private boolean bHasPinFile = false;
/*  187 */   private AttributeValueMapExt data = null;
/*      */   
/*  189 */   private IUIAgent ui = null;
/*      */   
/*  191 */   private SPSRobot robot = null;
/*      */   
/*  193 */   private SPSClientFacade facade = null;
/*      */   
/*  195 */   private static final Logger log = Logger.getLogger(SPSClientController.class);
/*      */   
/*  197 */   private PeriodicTask ptKeepAlive = null;
/*      */   
/*  199 */   public static VIT1 lastVIT1 = null;
/*      */   
/*      */   private boolean checkSoftwareInstallation = true;
/*      */   
/*  203 */   private VINDisplayRequest lastVINDisplayRequest = null;
/*  204 */   private Object savePointProceedSameVIN = null;
/*      */   
/*  206 */   private List sequenceSummary = null;
/*      */   
/*  208 */   private int type4DownloadTime = 0;
/*      */   
/*      */   private final Pattern P_ERRORCODE;
/*      */ 
/*      */   
/*      */   public void start() {
/*  214 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*      */     try {
/*  216 */       appContext.init();
/*  217 */     } catch (Exception e) {
/*  218 */       System.err.println(e);
/*      */       
/*      */       return;
/*      */     } 
/*  222 */     stopKeepAliveTask();
/*  223 */     resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*      */     
/*  225 */     File pdfDir = new File(PDFStarter.PATH_TO_PDF_DIR);
/*  226 */     FileManager.deleteDirectory(pdfDir);
/*  227 */     if (!pdfDir.exists()) {
/*  228 */       pdfDir.mkdir();
/*      */     }
/*      */     
/*      */     try {
/*  232 */       DTCUploadProvider.getDTCUpload().upload(null);
/*  233 */     } catch (Exception e) {
/*  234 */       log.error("unable to upload dtc - exception : " + e, e);
/*      */     } 
/*      */ 
/*      */     
/*  238 */     this.data = (AttributeValueMapExt)new AttributeValueMapRI();
/*  239 */     this.data.set(CommonAttribute.CLIENT, CommonValue.JAVA_CLIENT);
/*  240 */     this.data.set(CommonAttribute.SESSION_ID, (Value)new ValueAdapter(appContext.getSessionID()));
/*  241 */     long timestamp = System.currentTimeMillis();
/*  242 */     this.data.set(CommonAttribute.SESSION_TAG, (Value)new ValueAdapter(Long.valueOf(timestamp)));
/*  243 */     log.debug("sps session timestamp (ms): client=" + timestamp);
/*  244 */     if (this.robot == null) {
/*  245 */       if (ExecutionMode.isLoadTest()) {
/*  246 */         this.ui = new IUIAgent.ImplAdapter()
/*      */           {
/*      */             public void execute(AssignmentRequest request, AttributeValueMapExt data) {
/*  249 */               if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/*  250 */                 data.set(CommonAttribute.DEVICE, (Value)new ValueAdapter("Test Driver"));
/*  251 */                 SPSClientController.this.handleToolSelection(data);
/*      */               } else {
/*      */                 
/*  254 */                 super.execute(request, data);
/*      */               } 
/*      */             }
/*      */ 
/*      */             
/*      */             public void showTestSummary(DefaultTableModel summary) {
/*  260 */               super.showTestSummary(summary);
/*      */             }
/*      */           };
/*      */       } else {
/*      */         
/*  265 */         final ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/*  266 */         if (this.checkSoftwareInstallation) {
/*      */           
/*      */           try {
/*  269 */             final String oldVersion = context.getClientSettings().getProperty("allow.j2534.oldversion");
/*  270 */             File dir = new File(System.getProperty("user.home") + "\\sps\\spsCache");
/*  271 */             Installer sdh = new Installer(new Installer.Callback()
/*      */                 {
/*      */                   public String getSessionID() {
/*  274 */                     return context.getSessionID();
/*      */                   }
/*      */                   
/*      */                   public JComponent getParentComponent() {
/*  278 */                     return null;
/*      */                   }
/*      */                   
/*      */                   public Installer.Callback.Mode getMode() {
/*  282 */                     if (oldVersion != null && oldVersion.trim().equalsIgnoreCase("true")) {
/*  283 */                       return Installer.Callback.ALL_VERSIONS;
/*      */                     }
/*  285 */                     return Installer.Callback.UPDATE_VERSIONS_ONLY;
/*      */                   }
/*      */ 
/*      */                   
/*      */                   public Locale getLocale() {
/*  290 */                     if (context != null && context.getLocale() != null) {
/*  291 */                       return context.getLocale();
/*      */                     }
/*  293 */                     return Locale.ENGLISH;
/*      */                   }
/*      */                   
/*      */                   public String getLabel(String key) {
/*  297 */                     return SPSClientController.resourceProvider.getLabel(context.getLocale(), key);
/*      */                   }
/*      */                   
/*      */                   public IInstalledVersionLookup.IRegistryLookup.RegistryValue getRegistryValue(String key, String name) {
/*  301 */                     IInstalledVersionLookup.IRegistryLookup.RegistryValue ret = null;
/*      */                     try {
/*  303 */                       while (key.startsWith("\\")) {
/*  304 */                         key = key.substring(1);
/*      */                       }
/*      */                       
/*  307 */                       RegistryKey.initialize();
/*      */                       
/*  309 */                       int index = key.indexOf("\\");
/*  310 */                       String firstPart = key.substring(0, index).toUpperCase(Locale.ENGLISH);
/*  311 */                       String remainder = key.substring(index + 1);
/*      */                       
/*  313 */                       RootKey rootKey = null;
/*  314 */                       for (int i = 0; i < SPSClientController.rootKeys.length && rootKey == null; i++) {
/*  315 */                         if (firstPart.equals(SPSClientController.rootKeys[i].toString().toUpperCase(Locale.ENGLISH))) {
/*  316 */                           rootKey = SPSClientController.rootKeys[i];
/*      */                         }
/*      */                       } 
/*      */                       
/*  320 */                       if (rootKey == null) {
/*  321 */                         rootKey = RootKey.HKEY_LOCAL_MACHINE;
/*  322 */                         remainder = firstPart + "\\" + remainder;
/*      */                       } 
/*      */                       
/*  325 */                       RegistryKey regKey = new RegistryKey(rootKey, remainder);
/*  326 */                       if (regKey.exists()) {
/*  327 */                         final RegistryValue value = regKey.getValue(name);
/*  328 */                         if (value != null) {
/*  329 */                           ret = new IInstalledVersionLookup.IRegistryLookup.RegistryValue()
/*      */                             {
/*      */                               public String getType() {
/*  332 */                                 return value.getType().toString();
/*      */                               }
/*      */                               
/*      */                               public Object getData() {
/*  336 */                                 return value.getData();
/*      */                               }
/*      */                             };
/*      */                         }
/*      */                       }
/*      */                     
/*  342 */                     } catch (Exception e) {
/*  343 */                       SPSClientController.log.error("unable to access key: " + key + " and/or name: " + name + " - exception:" + e, e);
/*      */                     } 
/*  345 */                     return ret;
/*      */                   }
/*      */                 }dir);
/*      */ 
/*      */             
/*  350 */             sdh.processInstall(Arrays.asList(new Object[] { ClassificationFilter.create("SPS"), ClassificationFilter.J2534_DEVICE_DRIVER }));
/*      */           }
/*  352 */           catch (Exception ex) {
/*  353 */             log.error("Unable to start download Units - Exception :" + ex, ex);
/*      */           } 
/*  355 */           this.checkSoftwareInstallation = false;
/*  356 */           context.updateJ2534Tools();
/*      */         } 
/*  358 */         this.ui = new UIAgent(this, this.data);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     ListValue localTools = acquireLocalToolList(appContext);
/*  367 */     this.data.set(CommonAttribute.TOOLS, (Value)localTools);
/*  368 */     this.data.set(CommonAttribute.BACCODE, (Value)new ValueAdapter(appContext.getBACCode()));
/*  369 */     this.facade = SPSClientFacadeProvider.getInstance().getFacade();
/*  370 */     if (this.robot != null) {
/*  371 */       this.robot.setVinRead(false);
/*      */     }
/*      */ 
/*      */     
/*  375 */     log.debug("Check/Create type4 subdir");
/*  376 */     File homeDirectory = new File(ClientAppContextProvider.getClientAppContext().getHomeDir());
/*  377 */     File type4Directory = new File(homeDirectory, "type4");
/*  378 */     if (!type4Directory.exists()) {
/*  379 */       type4Directory.mkdir();
/*  380 */       log.debug("Subdir type4 created");
/*      */     } else {
/*  382 */       log.debug("Subdir type4 already exists");
/*      */     } 
/*      */     
/*  385 */     this.lastVINDisplayRequest = null;
/*  386 */     this.savePointProceedSameVIN = null;
/*  387 */     this.sequenceSummary = null;
/*  388 */     this.type4DownloadTime = 0;
/*  389 */     invokeServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isProceedPossible() {
/*  394 */     return (this.savePointProceedSameVIN != null && this.lastVINDisplayRequest != null);
/*      */   }
/*      */   
/*      */   public void proceed() {
/*  398 */     if (this.savePointProceedSameVIN == null || this.lastVINDisplayRequest == null) {
/*  399 */       throw new IllegalArgumentException("No valid save point for 'proceed w/ same VIN'");
/*      */     }
/*      */     try {
/*  402 */       Tool tool = getTool();
/*  403 */       tool.resetVIT1History();
/*  404 */     } catch (Exception e) {
/*  405 */       log.warn("...unable to reset vit1 history", e);
/*      */     } 
/*  407 */     Value vin = this.data.getValue(CommonAttribute.VIN);
/*  408 */     this.data.restoreSavePoint(this.savePointProceedSameVIN);
/*  409 */     long timestamp = System.currentTimeMillis();
/*  410 */     this.data.set(CommonAttribute.SESSION_TAG, (Value)new ValueAdapter(Long.valueOf(timestamp)));
/*  411 */     log.debug("sps session timestamp (ms): client=" + timestamp);
/*  412 */     this.ui.execute((AssignmentRequest)this.builder.makeVINDisplayRequest(CommonAttribute.VIN, vin), this.data);
/*  413 */     this.ui.triggerNextRequest();
/*      */   }
/*      */   
/*      */   public static void main(String[] args) throws Exception {
/*  417 */     if (ExecutionMode.isLoadTest()) {
/*      */       
/*  419 */       File startLock = new File(System.getProperty("file.start"));
/*  420 */       if (!startLock.delete()) {
/*  421 */         System.err.println("unable to delete startup lock file (load test)");
/*  422 */         System.exit(-1);
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/*  427 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.sps.client.common.message") != 0) {
/*  428 */         System.exit(0);
/*      */       }
/*  430 */       SPSClientController cc = new SPSClientController();
/*  431 */       cc.start();
/*      */     }
/*  433 */     catch (Exception e) {
/*  434 */       log.error("Unable to execute client - exception: " + e, e);
/*  435 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected ListValue acquireLocalToolList(ClientAppContext appContext) {
/*  441 */     List<ValueAdapter> tools = new ArrayList();
/*  442 */     List<Tool> diagtools = appContext.getTools();
/*  443 */     for (int i = 0; i < diagtools.size(); i++) {
/*  444 */       Tool tool = diagtools.get(i);
/*  445 */       String ttype = (String)tool.getType().getAdaptee();
/*  446 */       tools.add(new ValueAdapter(tool.getId() + ":" + ttype));
/*      */     } 
/*  448 */     return (ListValue)new ListValueImpl(tools);
/*      */   }
/*      */   
/*      */   protected ProcessSelectionRequest makeProcessSelectionRequest(List processes) {
/*  452 */     return this.builder.makeProcessSelectionRequest(CommonAttribute.MODE, processes, null);
/*      */   }
/*      */   
/*      */   protected List getProcessList(Tool tool) {
/*  456 */     List<Value> list = new ArrayList();
/*  457 */     list.add(CommonValue.REPROGRAM);
/*  458 */     list.add(CommonValue.REPLACE_AND_REPROGRAM);
/*  459 */     return list;
/*      */   }
/*      */   
/*      */   protected Tool getTool() {
/*  463 */     Value value = this.data.getValue(CommonAttribute.TOOL);
/*  464 */     if (value != null && value instanceof ValueAdapter) {
/*  465 */       return (Tool)((ValueAdapter)value).getAdaptee();
/*      */     }
/*  467 */     return null;
/*      */   }
/*      */   
/*      */   private void handleVIT1HistoryRequest(VIT1HistoryRequest request, AttributeValueMap data) {
/*  471 */     log.debug("handling vit1 history request...");
/*  472 */     Tool tool = getTool();
/*      */     
/*  474 */     Map value = null;
/*      */     
/*  476 */     try { value = tool.getVIT1History(); }
/*  477 */     catch (UnsupportedOperationException e) {  }
/*  478 */     catch (Exception e)
/*  479 */     { log.warn("...unable to retrieve vit1 history"); }
/*      */ 
/*      */     
/*  482 */     if (value == null) {
/*  483 */       log.debug("...history not available, returning empty map");
/*  484 */       value = Collections.EMPTY_MAP;
/*      */     }
/*  486 */     else if (log.isDebugEnabled()) {
/*  487 */       log.debug("...retrieved " + value.size() + " mappings (ecuAddress->VIT1 list)");
/*      */     } 
/*      */     
/*  490 */     data.set(CommonAttribute.VIT1_HISTORY, (Value)new ValueAdapter(value));
/*  491 */     invokeServer();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleVINReadRequest(VINReadRequest request) {
/*  496 */     stopKeepAliveTask();
/*  497 */     Tool tool = getTool();
/*      */     try {
/*  499 */       if (request != null) {
/*  500 */         initTool();
/*      */       }
/*  502 */       DeviceCommunicationCallback dcb = null;
/*  503 */       if (!tool.getType().getAdaptee().equals("TEST_DRIVER") || tool.getId().equals("RMITestTool")) {
/*  504 */         Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  505 */         String message = resourceProvider.getMessage(locale, "sps.device-communication");
/*  506 */         dcb = new DeviceCommunicationCallbackImpl(this.ui, message);
/*      */       } 
/*  508 */       if (this.robot != null) {
/*  509 */         this.ui.notify(CommonAttribute.TOOL_VIN);
/*      */       }
/*  511 */       VIT1 vit1 = null;
/*  512 */       Object response = tool.getVIN(dcb, this.data);
/*      */ 
/*      */ 
/*      */       
/*  516 */       if (this.robot != null) {
/*  517 */         this.ui.removeMessageDialog();
/*  518 */         this.robot.setVinRead(true);
/*      */       } 
/*      */       
/*  521 */       if (response instanceof VIT1) {
/*  522 */         vit1 = (VIT1)response;
/*  523 */         String vin = (String)vit1.getVIN();
/*  524 */         if (vin == null) {
/*  525 */           vin = "";
/*      */         }
/*  527 */         this.data.set(CommonAttribute.TOOL_VIN, (Value)new ValueAdapter(vin));
/*  528 */       } else if (tool.getId().equals("RMITestTool")) {
/*  529 */         this.data.set(CommonAttribute.TOOL_VIN, (Value)response);
/*  530 */         vit1 = (VIT1)tool.getECUData(dcb, null, (AttributeValueMap)this.data);
/*      */       } else {
/*  532 */         this.data.set(CommonAttribute.TOOL_VIN, (Value)tool.getVIN(null, this.data));
/*  533 */         vit1 = (VIT1)tool.getECUData(null, null, (AttributeValueMap)this.data);
/*      */       } 
/*  535 */       if (this.robot != null) {
/*  536 */         this.robot.setVIT1(vit1, null);
/*      */       } else {
/*  538 */         this.ui.setVIT1(vit1, (Integer)null);
/*      */       } 
/*  540 */       VIT1DataImpl vit = new VIT1DataImpl(vit1, true);
/*  541 */       List devices = vit.getDeviceList();
/*  542 */       if (devices != null) {
/*  543 */         this.data.set(CommonAttribute.VIT1_DEVICE_LIST, (Value)new ValueAdapter(devices));
/*      */       }
/*  545 */       if (vit1 != null) {
/*  546 */         lastVIT1 = vit1;
/*      */       }
/*  548 */       String deviceType = vit.getDeviceType();
/*  549 */       if (deviceType != null) {
/*  550 */         this.data.set(CommonAttribute.VIT1_DEVICE_TYPE, (Value)new ValueAdapter(deviceType));
/*      */       }
/*  552 */       int seeds = vit.getSeedCount();
/*  553 */       this.data.set(CommonAttribute.VIT1_SEED_COUNT, (Value)new ValueAdapter(Integer.valueOf(seeds)));
/*  554 */       Map optionBytes = vit.getOptionBytes();
/*  555 */       if (optionBytes != null) {
/*  556 */         this.data.set(CommonAttribute.VIT1_OPTION_BYTES, (Value)new ValueAdapter(optionBytes));
/*      */       }
/*  558 */       Map adaptiveBytes = vit.getAdaptiveBytes();
/*  559 */       if (adaptiveBytes != null) {
/*  560 */         this.data.set(CommonAttribute.VIT1_ADAPTIVE_BYTES, (Value)new ValueAdapter(adaptiveBytes));
/*      */       }
/*  562 */       String navInfo = vit.getNavigationInfo();
/*  563 */       if (navInfo != null) {
/*      */         try {
/*  565 */           this.data.set(CommonAttribute.VIT1_NAVIGATION_INFO, (Value)new ValueAdapter(Long.valueOf(navInfo, 16)));
/*  566 */         } catch (Exception n) {
/*  567 */           log.error("failed to convert navigation info: " + navInfo);
/*      */         } 
/*      */       }
/*  570 */       invokeServer();
/*  571 */     } catch (Exception e) {
/*  572 */       if (this.robot != null) {
/*  573 */         this.ui.removeMessageDialog();
/*      */       }
/*  575 */       if (e instanceof RequestException && !(((RequestException)e).getRequest() instanceof VINDisplayRequest)) {
/*  576 */         VehicleAttributeSelectionRequestImpl vehicleAttributeSelectionRequestImpl; Request selection = ((RequestException)e).getRequest();
/*  577 */         VehicleAttributeSelectionRequest guiRequest = null;
/*  578 */         if (selection instanceof SelectionRequest && ((SelectionRequest)selection).getOptions().size() == 1) {
/*  579 */           VehicleAttributeDefaultSelectionRequestImpl vehicleAttributeDefaultSelectionRequestImpl = new VehicleAttributeDefaultSelectionRequestImpl((SelectionRequest)selection, ((SelectionRequest)selection).getOptions().get(0));
/*      */         } else {
/*  581 */           vehicleAttributeSelectionRequestImpl = new VehicleAttributeSelectionRequestImpl((SelectionRequest)selection);
/*      */         } 
/*  583 */         this.ui.execute((AssignmentRequest)vehicleAttributeSelectionRequestImpl, this.data);
/*      */       } else {
/*  585 */         handle(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleVIT1Request(VIT1Request request, AttributeValueMapExt data) {
/*      */     try {
/*  593 */       Tool tool = getTool();
/*  594 */       if (tool != null) {
/*  595 */         String vin = (String)AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.VIN);
/*  596 */         List<RequestMethodData> rmds = request.getRequestMethodData();
/*  597 */         Integer device = null;
/*  598 */         logReqMethId(rmds);
/*  599 */         if (rmds.size() == 1 && ((RequestMethodData)rmds.get(0)).getRequestMethodID() < 0) {
/*  600 */           device = Integer.valueOf(((RequestMethodData)rmds.get(0)).getDeviceID());
/*  601 */           rmds = null;
/*      */         } 
/*  603 */         Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  604 */         String message = resourceProvider.getMessage(locale, "sps.device-communication");
/*  605 */         DeviceCommunicationCallback dcb = new DeviceCommunicationCallbackImpl(this.ui, message);
/*  606 */         if (this.robot == null) {
/*  607 */           this.ui.notify(CommonAttribute.VIT1);
/*      */         }
/*  609 */         VIT1 vit1 = (VIT1)tool.getECUData(dcb, rmds, (AttributeValueMap)this.data);
/*  610 */         DTCServiceImpl.getInstance().evaluateVIT1ClearDTC(vit1, (AttributeValueMap)data);
/*      */         try {
/*  612 */           if (rmds == null) {
/*  613 */             VIT1DataImpl vIT1DataImpl; VIT1Data vit1d = null;
/*  614 */             if (device != null && vit1.getControlModuleBlock(device) != null) {
/*  615 */               vIT1DataImpl = new VIT1DataImpl(device, vin, vit1);
/*      */             } else {
/*  617 */               vIT1DataImpl = new VIT1DataImpl(vit1, true);
/*      */             } 
/*  619 */             this.data.set(CommonAttribute.VIT1, (Value)vIT1DataImpl);
/*  620 */           } else if (rmds.size() == 1) {
/*  621 */             RequestMethodData rmd = rmds.get(0);
/*  622 */             device = Integer.valueOf(rmd.getDeviceID());
/*  623 */             if (device.intValue() == 0) {
/*  624 */               device = selectDeviceCMB(rmd, vit1);
/*      */             }
/*  626 */             this.data.set(CommonAttribute.VIT1, (Value)new VIT1DataImpl(device, vin, vit1));
/*      */           }
/*  628 */           else if (supportSPSFunctions()) {
/*  629 */             this.data.set(CommonAttribute.VIT1, (Value)new VIT1DataImpl(vit1, false));
/*      */           } else {
/*  631 */             this.data.set(CommonAttribute.VIT1, (Value)new VIT1DataImpl(vit1, true));
/*      */           } 
/*      */           
/*  634 */           if (this.robot != null) {
/*  635 */             this.robot.setVIT1(vit1, device);
/*      */           } else {
/*  637 */             this.ui.setVIT1(vit1, device);
/*      */           } 
/*  639 */           lastVIT1 = vit1;
/*  640 */         } catch (Exception x) {
/*  641 */           this.data.set(CommonAttribute.VIT1, (Value)new VIT1DataImpl(Integer.valueOf(16), vin, vit1));
/*      */         } 
/*  643 */         if (!isUnsupportedController()) {
/*  644 */           if (this.robot == null) {
/*  645 */             this.ui.lockOn();
/*      */           }
/*  647 */           invokeServer();
/*      */         } 
/*      */       } 
/*  650 */     } catch (Exception e) {
/*  651 */       if (this.robot == null) {
/*  652 */         this.ui.removeMessageDialog();
/*      */       }
/*  654 */       handle(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isUnsupportedController() {
/*      */     try {
/*  661 */       VIT1Data vit1d = (VIT1Data)this.data.getValue(CommonAttribute.VIT1);
/*  662 */       if (vit1d != null && "0".equals(vit1d.getVIT().getAttrValue("controller_supported"))) {
/*  663 */         if (this.robot == null) {
/*  664 */           String message = resourceProvider.getMessage(null, "sps.exception.no-supported-controller");
/*  665 */           this.ui.showMessage("error", "Exception", message);
/*      */         } 
/*  667 */         this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/*  668 */         start();
/*  669 */         return true;
/*      */       } 
/*  671 */     } catch (Exception x) {}
/*      */     
/*  673 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isGME() {
/*  677 */     Value value = this.data.getValue(CommonAttribute.SPS_ADAPTER_TYPE);
/*  678 */     return CommonValue.SPS_GME.equals(value);
/*      */   }
/*      */   
/*      */   public boolean isNAO() {
/*  682 */     Attribute ATTR_SERVICE_ID = AttributeImpl.getInstance("attribute.service.id");
/*  683 */     Value value = this.data.getValue(ATTR_SERVICE_ID);
/*  684 */     if (value != null && value.toString().toUpperCase(Locale.ENGLISH).indexOf("NAO") != -1) {
/*  685 */       return true;
/*      */     }
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isGlobalAdapter() {
/*  691 */     Attribute ATTR_SERVICE_ID = AttributeImpl.getInstance("attribute.service.id");
/*  692 */     Value value = this.data.getValue(ATTR_SERVICE_ID);
/*  693 */     if (value != null && value.toString().toUpperCase(Locale.ENGLISH).indexOf("GLOBAL") != -1) {
/*  694 */       return true;
/*      */     }
/*  696 */     return false;
/*      */   }
/*      */   
/*      */   public boolean supportSPSFunctions() {
/*  700 */     Value value = this.data.getValue(CommonAttribute.SPS_FUNCTION_SUPPORTED);
/*  701 */     if (value != null && value.equals(CommonValue.ENABLE)) {
/*  702 */       return true;
/*      */     }
/*  704 */     return false;
/*      */   }
/*      */   
/*      */   protected Integer selectDeviceCMB(RequestMethodData rmd, VIT1 vit1) {
/*      */     try {
/*  709 */       List<Integer> devices = (List)rmd.getValue("DeviceIDs".toLowerCase(Locale.ENGLISH));
/*  710 */       if (devices != null && devices.size() > 0) {
/*  711 */         for (int i = 0; i < devices.size(); i++) {
/*  712 */           Integer device = devices.get(i);
/*  713 */           if (vit1.getControlModuleBlock(device) != null) {
/*  714 */             return device;
/*      */           }
/*      */         } 
/*      */       }
/*  718 */     } catch (Exception e) {}
/*      */     
/*  720 */     return Integer.valueOf(0);
/*      */   }
/*      */   
/*      */   protected void handleProgrammingByFile(Request request, AttributeValueMapExt data) {
/*      */     try {
/*  725 */       Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  726 */       String prefix = (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingByFileRequest) ? "sps.programming-by-file" : "sps.programming-by-archive";
/*  727 */       String filter = resourceProvider.getLabel(locale, prefix + ".filter");
/*  728 */       String title = resourceProvider.getLabel(locale, prefix + ".title");
/*  729 */       List<String> fileTypes = new ArrayList();
/*  730 */       fileTypes.add(".zip");
/*  731 */       if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingByFileRequest) {
/*  732 */         fileTypes.add(".prt");
/*      */       }
/*  734 */       String vci = (String)AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.VCI);
/*  735 */       String initialDir = vci.equals("1001") ? "a:\\" : "b:\\";
/*  736 */       String filename = this.ui.getFileName(title, filter, fileTypes, initialDir);
/*  737 */       if (filename == null) {
/*  738 */         this.data.remove(CommonAttribute.VCI);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  743 */       else if (filename.toLowerCase(Locale.ENGLISH).endsWith(".zip")) {
/*  744 */         if (!isNAO() && !isGlobalAdapter()) {
/*  745 */           checkVCI(vci, filename);
/*      */         }
/*  747 */         this.data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(locale, filename)));
/*      */       } else {
/*  749 */         this.data.set(CommonAttribute.PART_FILE, (Value)new ValueAdapter(new SPSPartFile(filename)));
/*      */       } 
/*      */       
/*  752 */       invokeServer();
/*  753 */     } catch (Exception e) {
/*  754 */       if (e instanceof RequestException) {
/*  755 */         handle(e);
/*  756 */       } else if (e instanceof SPSException) {
/*  757 */         handle(e);
/*      */       } else {
/*  759 */         this.ui.handleException(CommonException.LOADING_PROGRAMMING_FILE_FAILED.getID());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkVCI(String vci, String filename) throws Exception {
/*  765 */     Integer checksum = VCICalculationImpl.getInstance().calculateVCI(filename);
/*  766 */     if (!vci.endsWith(checksum.toString())) {
/*  767 */       throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */     }
/*      */   }
/*      */   
/*      */   public void execute(Request request) {
/*  772 */     execute(request, (AttributeValueMap)this.data);
/*      */   }
/*      */   
/*      */   public void execute(Request request, AttributeValueMap data) {
/*  776 */     UIRequestHandler handler = (this.robot != null) ? this.robot : this.ui;
/*  777 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/*  778 */       handler.execute((AssignmentRequest)request, (AttributeValueMapExt)data);
/*      */ 
/*      */     
/*      */     }
/*  782 */     else if (request instanceof VIT1Request) {
/*  783 */       handleVIT1Request((VIT1Request)request, (AttributeValueMapExt)data);
/*  784 */     } else if (request instanceof VINReadRequest) {
/*  785 */       handleVINReadRequest((VINReadRequest)request);
/*  786 */     } else if (request instanceof VINDisplayRequest) {
/*  787 */       this.lastVINDisplayRequest = (VINDisplayRequest)request;
/*  788 */       handler.execute((AssignmentRequest)request, (AttributeValueMapExt)data);
/*  789 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.VINConfirmationRequest) {
/*      */ 
/*      */       
/*  792 */       invokeServer();
/*  793 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.HardwareKeyRequest) {
/*  794 */       ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*  795 */       String hwk = null;
/*  796 */       String hwk32 = null;
/*      */       try {
/*  798 */         hwk = appContext.getHardwareKey();
/*  799 */         hwk32 = appContext.getHardwareKey32();
/*  800 */       } catch (SystemDriverNotInstalledException e) {
/*      */         
/*  802 */         if (this.robot == null) {
/*  803 */           String message = resourceProvider.getMessage(null, "sps.hwk.system.driver.not.installed");
/*  804 */           String title = resourceProvider.getLabel(null, "app.title");
/*  805 */           this.ui.showMessage("error", title, message);
/*      */         } 
/*  807 */         this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/*  808 */         start();
/*      */       } 
/*  810 */       if (hwk != null && hwk32 != null) {
/*      */ 
/*      */         
/*  813 */         this.data.set(CommonAttribute.HARDWARE_KEY, (Value)new ValueAdapter(hwk));
/*  814 */         this.data.set(CommonAttribute.HARDWARE_KEY32, (Value)new ValueAdapter(hwk32));
/*  815 */         invokeServer();
/*      */       } 
/*  817 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest) {
/*  818 */       checkPermissionProceedSameVIN(data);
/*  819 */       handler.execute((AssignmentRequest)request, (AttributeValueMapExt)data);
/*  820 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingByFileRequest) {
/*  821 */       handleProgrammingByFile(request, (AttributeValueMapExt)data);
/*  822 */     } else if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.LoadArchiveRequest) {
/*  823 */       handleProgrammingByFile(request, (AttributeValueMapExt)data);
/*  824 */     } else if (request instanceof ReprogramRequest) {
/*  825 */       handleReprogram((ReprogramRequest)request, (AttributeValueMapExt)data);
/*  826 */     } else if (request instanceof BulletinDisplayRequest) {
/*      */       try {
/*  828 */         Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  829 */         String html = this.facade.getBulletin(locale.toString(), ((BulletinDisplayRequest)request).getBulletin());
/*  830 */         if (html != null) {
/*  831 */           this.ui.displayHTML(html);
/*      */           return;
/*      */         } 
/*  834 */       } catch (Exception e) {}
/*      */       
/*  836 */       this.ui.handleException(CommonException.BulletinNotAvailable.getID());
/*  837 */     } else if (request instanceof VIT1HistoryRequest) {
/*  838 */       handleVIT1HistoryRequest((VIT1HistoryRequest)request, data);
/*  839 */     } else if (request instanceof AssignmentRequest) {
/*      */       
/*  841 */       if (request instanceof DownloadProgressDisplayRequest) {
/*  842 */         trackType4DownloadNAO((DownloadProgressDisplayRequest)request);
/*      */       }
/*  844 */       checkPermissionProceedSameVIN(data);
/*  845 */       handler.execute((AssignmentRequest)request, (AttributeValueMapExt)data);
/*  846 */       if (request instanceof InstructionDisplayRequest) {
/*  847 */         handlePrePostInstructions((InstructionDisplayRequest)request);
/*  848 */       } else if (request instanceof com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest && (
/*  849 */         (DisplaySummaryRequestImpl)request).getSequenceSummary() != null) {
/*  850 */         Tool tool = getTool();
/*  851 */         if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/*  852 */           this.sequenceSummary = ((DisplaySummaryRequestImpl)request).getSequenceSummary();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void trackType4DownloadNAO(DownloadProgressDisplayRequest request) {
/*  860 */     if (AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.TYPE4_DATA) instanceof ListValue) {
/*  861 */       request.addObserver(new DownloadProgressDisplayRequest.Observer() {
/*  862 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public synchronized void onRead(List blobs, ProgrammingDataUnit dataUnit, long byteCount) {}
/*      */             
/*      */             public synchronized void onFinished(Status status) {
/*  868 */               if (status == null || status.equals(FINISHED)) {
/*  869 */                 SPSClientController.this.type4DownloadTime = (int)((System.currentTimeMillis() - this.startTime) / 1000L);
/*  870 */                 SPSClientController.this.invokeServer();
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   protected String handleWarrantyClaimCode(String html) {
/*      */     try {
/*  879 */       String wcc = (String)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.WARRANTY_CLAIM_CODE);
/*  880 */       if (wcc == null) {
/*  881 */         Integer dealerVCI = (Integer)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.DEALER_VCI);
/*  882 */         if (dealerVCI == null)
/*      */         {
/*      */           
/*  885 */           return StringUtilities.replace(html, "{WARRANTY_CODE_INFO}", "&nbsp;");
/*      */         }
/*  887 */         Integer deviceID = (Integer)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.DEVICE_ID);
/*  888 */         String device = Integer.toHexString(deviceID.intValue());
/*  889 */         wcc = Integer.toHexString(dealerVCI.intValue());
/*  890 */         if (wcc.length() > 3) {
/*  891 */           wcc = wcc.substring(wcc.length() - 3);
/*      */         } else {
/*  893 */           while (wcc.length() < 3) {
/*  894 */             wcc = "0" + wcc;
/*      */           }
/*      */         } 
/*  897 */         wcc = device + wcc;
/*      */       } 
/*  899 */       wcc = wcc.toUpperCase(Locale.ENGLISH);
/*  900 */       this.data.set(CommonAttribute.WARRANTY_CLAIM_CODE, (Value)new ValueAdapter(wcc));
/*  901 */       WarrantyClaimCodeStore.recordWarrantyClaimCode((String)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.VIN), wcc);
/*  902 */       String warrantyInfo = StringUtilities.replace("<tr><td>&nbsp;</td></tr><tr><td><b>{LABEL} {CODE}</b></td></tr><tr><td>{INSTRUCTION}</td></tr><tr><td>&nbsp;</td></tr>", "{LABEL}", resourceProvider.getMessage(null, "sps.warranty-claim-code.label"));
/*  903 */       warrantyInfo = StringUtilities.replace(warrantyInfo, "{CODE}", wcc);
/*  904 */       warrantyInfo = StringUtilities.replace(warrantyInfo, "{INSTRUCTION}", resourceProvider.getMessage(null, "sps.warranty-claim-code.instruction"));
/*  905 */       return StringUtilities.replace(html, "{WARRANTY_CODE_INFO}", warrantyInfo);
/*  906 */     } catch (Exception e) {
/*  907 */       return html;
/*      */     } 
/*      */   }
/*      */   
/*      */   public String handleSPSLabourTime(String html) {
/*      */     try {
/*  913 */       boolean displayLTFactor = false;
/*  914 */       Value configuration = this.data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION);
/*  915 */       if (configuration != null && configuration instanceof ValueAdapter) {
/*  916 */         SPSLaborTimeConfiguration cfg = (SPSLaborTimeConfiguration)((ValueAdapter)configuration).getAdaptee();
/*  917 */         displayLTFactor = cfg.isDisplay();
/*      */       } 
/*  919 */       if (displayLTFactor && this.data.getValue(CommonAttribute.LABOR_TIME_TRACKING) != null) {
/*  920 */         Pair pair = (Pair)((ValueAdapter)this.data.getValue(CommonAttribute.LABOR_TIME_TRACKING)).getAdaptee();
/*  921 */         String ltfactor = (String)pair.getSecond();
/*  922 */         String label = resourceProvider.getLabel(null, "sps.labor-time.add-factor.label");
/*  923 */         return StringUtilities.replace(html, "{ADD_LABOR_TIME}", "<b>" + label + "</b> = " + ltfactor);
/*      */       } 
/*  925 */     } catch (Exception e) {
/*  926 */       log.error("failed to handle display of SPS Labor Time factor", e);
/*      */     } 
/*  928 */     return StringUtilities.replace(html, "{ADD_LABOR_TIME}", "");
/*      */   }
/*      */   
/*      */   public String getInstructionHTML(String instructionID, boolean offline) throws Exception {
/*  932 */     Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  933 */     String html = this.facade.getHTML(locale.toString(), instructionID);
/*  934 */     if (instructionID.indexOf("final-instructions") >= 0) {
/*  935 */       html = handleWarrantyClaimCode(html);
/*  936 */       if (!offline) {
/*  937 */         html = handleSPSLabourTime(html);
/*      */       }
/*      */     } 
/*  940 */     return html;
/*      */   }
/*      */   
/*      */   public byte[] getInstructionImage(String imageID) throws Exception {
/*  944 */     return this.facade.getImage(imageID);
/*      */   }
/*      */   
/*      */   protected File createType4Directory() throws Exception {
/*  948 */     File homeDirectory = new File(ClientAppContextProvider.getClientAppContext().getHomeDir());
/*  949 */     File type4Directory = new File(homeDirectory, "type4");
/*  950 */     if (!type4Directory.exists()) {
/*  951 */       type4Directory.mkdir();
/*      */     }
/*  953 */     return type4Directory;
/*      */   }
/*      */   
/*      */   protected List transferType4Executables(ListValue files, List calibrations) throws Exception {
/*  957 */     this.bHasBldFile = false;
/*  958 */     this.bHasPinFile = false;
/*  959 */     File type4Directory = createType4Directory();
/*  960 */     int calibration = 0;
/*  961 */     List<byte[]> blobs = new ArrayList();
/*  962 */     for (int i = 0; i < files.getItems().size(); i++) {
/*  963 */       Pair pair = files.getItems().get(i);
/*  964 */       String name = (String)pair.getFirst();
/*  965 */       if (name.toLowerCase().indexOf(".txt") >= 0) {
/*  966 */         File file = new File(type4Directory, name);
/*  967 */         FileOutputStream fos = new FileOutputStream(file);
/*  968 */         fos.write((byte[])pair.getSecond());
/*  969 */         fos.close();
/*  970 */         if (name.equalsIgnoreCase("ECUPIN.TXT")) {
/*  971 */           this.bHasPinFile = true;
/*  972 */           log.debug("wrote type-4 data file (pin): " + file.getName());
/*      */         } else {
/*  974 */           log.debug("wrote type-4 data file: " + file.getName());
/*      */         }
/*      */       
/*  977 */       } else if (name.toLowerCase(Locale.ENGLISH).indexOf(".bld") >= 0) {
/*  978 */         File file = new File(type4Directory, name);
/*  979 */         FileOutputStream fos = new FileOutputStream(file);
/*  980 */         fos.write((byte[])pair.getSecond());
/*  981 */         fos.close();
/*      */         
/*  983 */         this.bHasBldFile = true;
/*  984 */         log.debug("bHasBldFile is set to true. ");
/*      */         
/*  986 */         log.debug("wrote type-4 data file (bld): " + file.getName());
/*  987 */       } else if (isSPSControllerXMLData(name, calibration)) {
/*  988 */         byte[] xml = ZipUtil.gunzip(extract((byte[])pair.getSecond(), calibrations.get(calibration)));
/*  989 */         File file = new File(type4Directory, "XMLFile.xml");
/*  990 */         FileOutputStream fos = new FileOutputStream(file);
/*  991 */         fos.write(xml);
/*  992 */         fos.close();
/*  993 */         log.debug("wrote type-4 data file (" + pair.getFirst() + "): " + "XMLFile.xml");
/*  994 */         calibration++;
/*      */       } else {
/*  996 */         blobs.add(extract((byte[])pair.getSecond(), calibrations.get(calibration)));
/*  997 */         if (this.bHasBldFile && calibration == 0) {
/*  998 */           name = "Type4App.dll";
/*  999 */           log.debug("received type-4 executable (" + pair.getFirst() + "): " + name);
/*      */         } else {
/* 1001 */           log.debug("received type-4 executable: " + name);
/*      */         } 
/* 1003 */         calibration++;
/*      */       } 
/*      */     } 
/*      */     
/* 1007 */     return blobs;
/*      */   }
/*      */   
/*      */   protected byte[] extract(byte[] data, Object calibration) {
/* 1011 */     return (data != null) ? data : (byte[])calibration;
/*      */   }
/*      */   
/*      */   protected boolean isSPSControllerXMLData(String name, int calibration) {
/* 1015 */     if (name.toLowerCase(Locale.ENGLISH).indexOf(".xml") >= 0)
/* 1016 */       return true; 
/* 1017 */     if (this.bHasBldFile && calibration == 1) {
/* 1018 */       return true;
/*      */     }
/* 1020 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeBuildFile(String buildfile) throws Exception {
/* 1025 */     File type4Directory = createType4Directory();
/* 1026 */     File file = new File(type4Directory, "BuildRecord.bld");
/*      */     
/* 1028 */     FileOutputStream fos = new FileOutputStream(file);
/* 1029 */     fos.write(buildfile.getBytes());
/* 1030 */     fos.close();
/* 1031 */     log.debug("wrote type-4 data file (bld): " + file.getName());
/*      */   }
/*      */   
/*      */   protected void writeType4Strings(byte[] type4strings) throws Exception {
/* 1035 */     File type4Directory = createType4Directory();
/* 1036 */     File file = new File(type4Directory, "type4_string.txt");
/*      */     
/* 1038 */     FileOutputStream fos = new FileOutputStream(file);
/* 1039 */     fos.write(type4strings);
/* 1040 */     fos.close();
/* 1041 */     log.debug("wrote type-4 strings: " + file.getName());
/*      */   }
/*      */   
/*      */   protected void removePinFile() {
/*      */     try {
/* 1046 */       File type4Directory = createType4Directory();
/* 1047 */       File file = new File(type4Directory, "ECUPIN.TXT");
/* 1048 */       if (file.exists()) {
/* 1049 */         file.delete();
/*      */       }
/* 1051 */     } catch (Exception e) {
/* 1052 */       log.error("failed to remove PIN file");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void adjustType4CalibrationFiles(List calibrations) {
/*      */     try {
/* 1059 */       if (this.bHasBldFile) {
/* 1060 */         log.debug("DEBUGGING:NAO -  bHasBldFile is true,so remove the xml bld file....... ");
/* 1061 */         calibrations.remove(1);
/*      */       } 
/*      */ 
/*      */       
/* 1065 */       if (this.bHasPinFile) {
/* 1066 */         log.debug("DEBUGGING:NAO -  bHasPinFile is true,so remove the ECUPIN file....... ");
/* 1067 */         calibrations.remove(1);
/*      */       }
/*      */     
/* 1070 */     } catch (Exception x) {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean handleType4Executables(Tool tool, String securityCode, String securityCodeCtrl, ReprogramRequest request, AttributeValueMapExt data, SPSTime lttrack) throws Exception {
/* 1075 */     VIT1 vit1 = lastVIT1;
/* 1076 */     if (vit1 != null) {
/* 1077 */       DTCServiceImpl.getInstance().evaluateVIT1ClearDTC(vit1, (AttributeValueMap)data);
/*      */     }
/* 1079 */     boolean isNAO = false;
/* 1080 */     List blobs = null;
/* 1081 */     Object type4data = AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.TYPE4_DATA);
/* 1082 */     if (type4data instanceof ListValue) {
/* 1083 */       isNAO = true;
/* 1084 */       ProgrammingData pdata = ((ReprogramControllerRequest)request).getProgrammingData();
/* 1085 */       blobs = transferType4Executables((ListValue)type4data, handleCalibrationFiles(pdata));
/*      */     } else {
/* 1087 */       writeBuildFile((String)type4data);
/* 1088 */       byte[] type4strings = (byte[])AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.TYPE4_STRINGS);
/* 1089 */       if (type4strings != null) {
/* 1090 */         writeType4Strings(type4strings);
/*      */       }
/*      */     } 
/* 1093 */     if (!tool.getType().getAdaptee().equals("TEST_DRIVER") || tool.getId().equals("RMITestTool")) {
/* 1094 */       ProgrammingData pdata = ((ReprogramControllerRequest)request).getProgrammingData();
/* 1095 */       List calibrations = pdata.getCalibrationFiles();
/* 1096 */       if (isNAO) {
/* 1097 */         adjustType4CalibrationFiles(calibrations);
/*      */       } else {
/* 1099 */         blobs = handleCalibrationFiles(pdata);
/*      */       } 
/*      */       
/* 1102 */       startKeepAliveTask();
/* 1103 */       boolean failure = false;
/*      */       try {
/* 1105 */         ProgrammingResult result = (ProgrammingResult)tool.reprogram(new MessageCallbackImpl(this.ui), pdata, blobs, securityCode, securityCodeCtrl);
/* 1106 */         failure = !result.getStatus().booleanValue();
/* 1107 */       } catch (Exception e) {
/* 1108 */         failure = true;
/*      */       } finally {
/* 1110 */         stopKeepAliveTask();
/*      */       } 
/* 1112 */       if (this.bHasPinFile) {
/* 1113 */         removePinFile();
/*      */       }
/* 1115 */       if (failure) {
/* 1116 */         String vin = (String)AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.VIN);
/* 1117 */         sendFailureMail(vin, null);
/* 1118 */         this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 1119 */         this.ui.notify(CommonAttribute.FINISH);
/* 1120 */         start();
/* 1121 */         return false;
/* 1122 */       }  if (lttrack != null) {
/* 1123 */         int downloadTime = (this.type4DownloadTime == 0) ? TransferDataPanel.getDownloadTime() : this.type4DownloadTime;
/* 1124 */         if (tool instanceof PtTool) {
/*      */           try {
/* 1126 */             int programmingTime = (int)(Long.parseLong(((PtTool)tool).getSPSTool().getToolProperty("sps_event_time")) / 1000L);
/* 1127 */             recordLaborTimeData(lttrack, downloadTime, programmingTime, true);
/* 1128 */           } catch (Exception e) {
/* 1129 */             log.error("failed to handle labor time tracking", e);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1134 */     this.ui.notify(CommonAttribute.REPROGRAM);
/* 1135 */     this.data.set(CommonAttribute.REPROGRAM, CommonValue.OK);
/* 1136 */     return true;
/*      */   }
/*      */   
/*      */   protected void handleRepairShopCode(ProgrammingData pdata) {
/* 1140 */     if (pdata.getRepairShopCode() != null) {
/*      */       
/* 1142 */       String rsc = ClientAppContextProvider.getClientAppContext().getRepairShopCode();
/* 1143 */       pdata.setRepairShopCode(rsc);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected List handleTransfer(List<ProgrammingDataUnit> calibrations) throws Exception {
/* 1148 */     List<byte[]> blobs = new ArrayList();
/* 1149 */     for (int i = 0; i < calibrations.size(); i++) {
/* 1150 */       ProgrammingDataUnit file = calibrations.get(i);
/* 1151 */       byte[] bytes = this.facade.getSPSBlob(file, (AttributeValueMap)this.data);
/* 1152 */       blobs.add(bytes);
/*      */     } 
/* 1154 */     return blobs;
/*      */   }
/*      */   
/*      */   protected List handleCalibrationFiles(ProgrammingData pdata) throws Exception {
/* 1158 */     handleRepairShopCode(pdata);
/* 1159 */     List<ProgrammingDataUnit> calibrations = null;
/* 1160 */     List<byte[]> blobs = null;
/* 1161 */     boolean compress = false;
/* 1162 */     if (this.data.getValue(CommonAttribute.ARCHIVE) != null) {
/* 1163 */       Archive archive = (Archive)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.ARCHIVE);
/* 1164 */       if (archive instanceof SPSArchive) {
/* 1165 */         blobs = ((SPSArchive)archive).getCalibrationFiles();
/*      */       } else {
/* 1167 */         calibrations = archive.getCalibrationUnits();
/*      */       } 
/* 1169 */       compress = true;
/*      */     } else {
/* 1171 */       calibrations = pdata.getCalibrationFiles();
/*      */     } 
/* 1173 */     if (blobs == null) {
/* 1174 */       blobs = handleTransfer(calibrations);
/*      */     }
/* 1176 */     if (compress) {
/* 1177 */       calibrations = pdata.getCalibrationFiles();
/* 1178 */       for (int i = 0; i < blobs.size(); i++) {
/* 1179 */         ProgrammingDataUnit unit = calibrations.get(i);
/* 1180 */         byte[] bytes = blobs.get(i);
/* 1181 */         bytes = gzip(bytes, unit.getBlobName());
/* 1182 */         unit.setBlobSize(Integer.valueOf(bytes.length));
/* 1183 */         blobs.set(i, bytes);
/*      */       } 
/*      */     } 
/* 1186 */     return blobs;
/*      */   }
/*      */   
/*      */   protected boolean reprogram(Tool tool, String securityCode, String securityCodeCtrl, ProgrammingData pdata, List blobs, boolean updateStatus) throws Exception {
/* 1190 */     MessageCallback mcb = new MessageCallbackImpl(this.ui);
/* 1191 */     if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1192 */       TestDriverImpl.setDataInformation(this.data);
/*      */     }
/* 1194 */     startKeepAliveTask();
/* 1195 */     ProgrammingResult result = null;
/* 1196 */     boolean failure = false;
/*      */     try {
/* 1198 */       result = (ProgrammingResult)tool.reprogram(mcb, pdata, blobs, securityCode, securityCodeCtrl);
/* 1199 */       failure = !result.getStatus().booleanValue();
/* 1200 */     } catch (Exception e) {
/* 1201 */       failure = true;
/*      */     } finally {
/* 1203 */       stopKeepAliveTask();
/*      */     } 
/* 1205 */     if (failure) {
/* 1206 */       String vin = (String)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.VIN);
/* 1207 */       if (this.robot != null) {
/* 1208 */         this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 1209 */         return false;
/*      */       } 
/* 1211 */       sendFailureMail(vin, null);
/* 1212 */       this.data.set(CommonAttribute.REPROGRAM, (Value)new ValueAdapter(result.getData()));
/* 1213 */       this.ui.indicateReprogrammingFailed();
/* 1214 */       if (!updateStatus) {
/* 1215 */         return false;
/*      */       }
/*      */     }
/* 1218 */     else if (updateStatus) {
/* 1219 */       this.data.set(CommonAttribute.REPROGRAM, CommonValue.OK);
/*      */     } 
/* 1221 */     return true;
/*      */   }
/*      */   
/*      */   protected void dumpVIT(VIT vit) {
/* 1225 */     List<Pair> attributes = vit.getAttributes();
/* 1226 */     for (int i = 0; i < attributes.size(); i++) {
/* 1227 */       Pair pair = attributes.get(i);
/* 1228 */       String attribute = (String)pair.getFirst();
/* 1229 */       String value = (String)pair.getSecond();
/* 1230 */       log.debug(attribute + "=" + value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void dumpVIT1(VIT1 vit1) {
/* 1235 */     VIT vit = vit1.getHeader();
/* 1236 */     dumpVIT(vit);
/* 1237 */     List<VIT> cmbs = vit1.getControlModuleBlocks();
/* 1238 */     for (int i = 0; cmbs != null && i < cmbs.size(); i++) {
/* 1239 */       vit = cmbs.get(i);
/* 1240 */       dumpVIT(vit);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handlePrePostInstructions(InstructionDisplayRequest request) {
/*      */     try {
/* 1246 */       if (this.robot == null && request.getAttribute().equals(CommonAttribute.FINAL_INSTRUCTIONS) && !isGME()) {
/* 1247 */         if (lastVIT1 == null) {
/* 1248 */           throw new RuntimeException("No VIT1 available");
/*      */         }
/* 1250 */         Integer deviceID = (Integer)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.DEVICE_ID);
/* 1251 */         if (deviceID == null) {
/* 1252 */           throw new RuntimeException("No Device-ID available");
/*      */         }
/* 1254 */         VIT vit = lastVIT1.getControlModuleBlock(deviceID);
/* 1255 */         if (vit == null) {
/*      */           try {
/* 1257 */             dumpVIT1(lastVIT1);
/* 1258 */           } catch (Exception x) {}
/*      */           
/* 1260 */           throw new RuntimeException("No VIT1-CMB available (DeviceID=" + deviceID + ")");
/*      */         } 
/* 1262 */         int onstar = 0;
/* 1263 */         if (vit.getAttrValue("StID") != null) {
/* 1264 */           onstar++;
/*      */         }
/* 1266 */         if (vit.getAttrValue("ESN") != null) {
/* 1267 */           onstar++;
/*      */         }
/* 1269 */         if (vit.getAttrValue("MEID") != null) {
/* 1270 */           onstar++;
/*      */         }
/* 1272 */         if (vit.getAttrValue("MIN") != null) {
/* 1273 */           onstar++;
/*      */         }
/* 1275 */         if (vit.getAttrValue("MDN") != null) {
/* 1276 */           onstar++;
/*      */         }
/* 1278 */         if (onstar > 0 && onstar != 4) {
/*      */           try {
/* 1280 */             dumpVIT1(lastVIT1);
/* 1281 */           } catch (Exception x) {}
/*      */           
/* 1283 */           log.info("OnStar attribute check failed: count=" + onstar);
/* 1284 */           Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 1285 */           String msg = resourceProvider.getMessage(locale, "sps.onstar.auto-activation-incomplete");
/* 1286 */           SPSFrame.displayErrorMessage(msg);
/*      */         } 
/*      */       } 
/* 1289 */     } catch (Exception e) {
/*      */       try {
/* 1291 */         dumpVIT1(lastVIT1);
/* 1292 */       } catch (Exception x) {}
/*      */       
/* 1294 */       log.error("unable to handle OnStar attribute check", e);
/*      */     } 
/*      */     try {
/* 1297 */       Tool tool = getTool();
/*      */       
/* 1299 */       if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1300 */         if (request.getAttribute().equals(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 1301 */           this.data.set(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS_DATA, (Value)new ListValueImpl(request.getInstructions()));
/*      */         }
/* 1303 */         if (request.getAttribute().equals(CommonAttribute.FINAL_INSTRUCTIONS)) {
/* 1304 */           this.data.set(CommonAttribute.POST_PROGRAMMING_INSTRUCTIONS_DATA, (Value)new ListValueImpl(request.getInstructions()));
/* 1305 */           TestDriverLog.getInstance().writePrePostAttr();
/*      */         }
/*      */       
/*      */       } 
/* 1309 */     } catch (Exception e) {
/* 1310 */       log.error("unable to write in VIT1 File Pre/Post Instructions");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void displayIntermediateProgrammingInstructions(List<Pair> instructions, boolean pre, int pos) {
/* 1315 */     List<String> messages = null;
/* 1316 */     if (pre) {
/* 1317 */       messages = (List)((Pair)instructions.get(pos)).getFirst();
/*      */     } else {
/* 1319 */       messages = (List)((Pair)instructions.get(pos)).getSecond();
/*      */     } 
/* 1321 */     if (messages != null) {
/* 1322 */       for (int i = 0; i < messages.size(); i++) {
/* 1323 */         String content = messages.get(i);
/* 1324 */         DisplayRequest irequest = this.builder.makeDisplayRequest(CommonAttribute.INTERMEDIATE_PROGRAMMING_INSTRUCTIONS, null, content);
/* 1325 */         irequest.setAutoSubmit(false);
/* 1326 */         this.ui.execute((AssignmentRequest)irequest, this.data);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void displayProgrammingInstructions(String instructions, boolean pre) {
/* 1332 */     if (instructions != null && instructions.length() > 0) {
/* 1333 */       Attribute attribute = pre ? CommonAttribute.INTERMEDIATE_PRE_PROGRAMMING_INSTRUCTIONS : CommonAttribute.INTERMEDIATE_POST_PROGRAMMING_INSTRUCTIONS;
/* 1334 */       DisplayRequest irequest = this.builder.makeDisplayRequest(attribute, null, instructions);
/* 1335 */       irequest.setAutoSubmit(false);
/* 1336 */       this.ui.execute((AssignmentRequest)irequest, this.data);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleType4Data(List blobs, List<ListValue> type4, int pos) throws Exception {
/* 1341 */     if (type4 == null || pos >= type4.size() || type4.get(pos) == null) {
/*      */       return;
/*      */     }
/* 1344 */     ListValue files = null;
/* 1345 */     if (type4.get(pos) instanceof ListValue) {
/* 1346 */       files = type4.get(pos);
/*      */     } else {
/* 1348 */       Pair pair = (Pair)type4.get(pos);
/* 1349 */       files = (ListValue)pair.getSecond();
/*      */     } 
/* 1351 */     if (files != null) {
/* 1352 */       transferType4Executables(files, blobs);
/* 1353 */       adjustType4CalibrationFiles(blobs);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected int determineFailureReaction(List<Integer> reactions, int pos) {
/* 1358 */     return ((Integer)reactions.get(pos)).intValue();
/*      */   }
/*      */   
/*      */   protected List initStatusPS(List<E> controllers) {
/* 1362 */     List<PairImpl> state = new ArrayList();
/* 1363 */     for (int i = 0; i < controllers.size(); i++) {
/* 1364 */       PairImpl pairImpl = new PairImpl(controllers.get(i).toString(), ProgrammingSequence.SKIP);
/* 1365 */       state.add(pairImpl);
/*      */     } 
/* 1367 */     return state;
/*      */   }
/*      */   
/*      */   protected Pair updateStatusPS(Object state, Integer result) {
/* 1371 */     String label = (String)((Pair)state).getFirst();
/* 1372 */     return (Pair)new PairImpl(label, result);
/*      */   }
/*      */   
/*      */   protected void displayReprogramStatusPS(List state) {
/* 1376 */     if (this.robot == null) {
/* 1377 */       this.ui.displayReprogramStatusPS(state);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleSummaryDigest(int position) {
/* 1382 */     if (this.sequenceSummary != null) {
/* 1383 */       Summary summary = this.sequenceSummary.get(position);
/* 1384 */       List<History> digest = new ArrayList();
/* 1385 */       digest.add(summary.getCurrentSoftware());
/* 1386 */       digest.add(summary.getSelectedSoftware());
/* 1387 */       this.data.set(CommonAttribute.SUMMARY_DIGEST, (Value)new ListValueImpl(digest));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String extractSecurityCode(AttributeValueMapExt data) {
/* 1392 */     Value securityCode = data.getValue(CommonAttribute.SECURITY_CODE);
/* 1393 */     if (securityCode != null && securityCode instanceof ValueAdapter) {
/* 1394 */       return ((String)((ValueAdapter)securityCode).getAdaptee()).toUpperCase(Locale.ENGLISH);
/*      */     }
/* 1396 */     return null;
/*      */   }
/*      */   
/*      */   protected String extractSecurityCodeCtrl(AttributeValueMapExt data) {
/* 1400 */     Value securityCode = data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE);
/* 1401 */     if (securityCode != null && securityCode instanceof ValueAdapter) {
/* 1402 */       return ((String)((ValueAdapter)securityCode).getAdaptee()).toUpperCase(Locale.ENGLISH);
/*      */     }
/* 1404 */     return null;
/*      */   }
/*      */   
/*      */   protected SPSTime handleLaborTimeConfiguration(AttributeValueMapExt data) {
/* 1408 */     Value configuration = data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION);
/* 1409 */     if (configuration != null && configuration instanceof ValueAdapter) {
/* 1410 */       SPSLaborTimeConfiguration cfg = (SPSLaborTimeConfiguration)((ValueAdapter)configuration).getAdaptee();
/* 1411 */       return new SPSTime(cfg);
/*      */     } 
/* 1413 */     return null;
/*      */   }
/*      */   
/*      */   protected void flagSequenceProgramming(Tool tool, int step) {
/* 1417 */     if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1418 */       TestDriverImpl.handleProgrammingSequenceStep(new Integer(step));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleReprogram(ReprogramRequest request, AttributeValueMapExt data) {
/*      */     try {
/* 1424 */       prepareOfflineFinalInstructions(data);
/* 1425 */       SPSTime lttrack = handleLaborTimeConfiguration(data);
/* 1426 */       String securityCode = extractSecurityCode(data);
/* 1427 */       String securityCodeCtrl = extractSecurityCodeCtrl(data);
/* 1428 */       Tool tool = getTool();
/* 1429 */       if (request instanceof ReprogramFunctionSequenceRequest) {
/* 1430 */         ReprogramFunctionSequenceRequest rsr = (ReprogramFunctionSequenceRequest)request;
/* 1431 */         if (data.getValue(CommonAttribute.TYPE4_DATA) != null) {
/* 1432 */           String type4data = (String)AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.TYPE4_DATA);
/* 1433 */           writeBuildFile(type4data);
/* 1434 */           byte[] type4strings = (byte[])AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.TYPE4_STRINGS);
/* 1435 */           if (type4strings != null) {
/* 1436 */             writeType4Strings(type4strings);
/*      */           }
/*      */         } 
/* 1439 */         List events = (lttrack == null) ? null : new ArrayList();
/* 1440 */         List<String> functions = rsr.getFunctionLabels();
/* 1441 */         List<Pair> state = initStatusPS(functions);
/* 1442 */         List<ProgrammingData> plist = rsr.getProgrammingDataList();
/* 1443 */         boolean success = true;
/* 1444 */         for (int i = 0; i < plist.size(); i++) {
/* 1445 */           ProgrammingData pdata = plist.get(i);
/* 1446 */           List blobs = handleCalibrationFiles(pdata);
/* 1447 */           if (this.robot == null) {
/* 1448 */             if (i == 0) {
/* 1449 */               this.ui.notify(CommonAttribute.REPROGRAM);
/*      */             }
/* 1451 */             if (plist.size() > 1) {
/* 1452 */               this.ui.setReprogrammingController(functions.get(i), "<" + (i + 1) + "/" + plist.size() + ">");
/*      */             }
/* 1454 */             if (i > 0) {
/* 1455 */               displayProgrammingInstructions(rsr.getPreProgrammingInstructions().get(i), true);
/*      */             }
/*      */           } 
/* 1458 */           handleSummaryDigest(i);
/* 1459 */           flagSequenceProgramming(tool, i);
/* 1460 */           if (!reprogram(tool, securityCode, securityCodeCtrl, pdata, blobs, false)) {
/* 1461 */             state.set(i, updateStatusPS(state.get(i), ProgrammingSequence.FAIL));
/* 1462 */             displayProgrammingInstructions(rsr.getPostProgrammingInstructions().get(i), false);
/* 1463 */             success = false; break;
/*      */           } 
/* 1465 */           if (this.robot == null) {
/* 1466 */             state.set(i, updateStatusPS(state.get(i), ProgrammingSequence.SUCCESS));
/* 1467 */             if (i != plist.size() - 1) {
/* 1468 */               displayProgrammingInstructions(rsr.getPostProgrammingInstructions().get(i), false);
/*      */             }
/* 1470 */             if (lttrack != null) {
/* 1471 */               boolean isType4 = XML_DEVICE_ID.equals(pdata.getDeviceID());
/*      */               
/* 1473 */               int downloadTime = (i == 0) ? TransferDataPanel.getDownloadTime() : 0;
/* 1474 */               if (tool instanceof PtTool) {
/*      */                 try {
/* 1476 */                   int programmingTime = (int)(Long.parseLong(((PtTool)tool).getSPSTool().getToolProperty("sps_event_time")) / 1000L);
/* 1477 */                   trackLaborTimeData(events, downloadTime, programmingTime, isType4);
/* 1478 */                 } catch (Exception e) {
/* 1479 */                   log.error("failed to handle labor time tracking", e);
/*      */                 } 
/* 1481 */               } else if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1482 */                 trackLaborTimeData(events, downloadTime, 102, isType4);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1487 */         if (plist.size() > 1) {
/* 1488 */           displayReprogramStatusPS(state);
/*      */         }
/* 1490 */         if (this.robot == null && lttrack != null && events != null && events.size() > 0) {
/* 1491 */           recordLaborTimeData(lttrack, events);
/*      */         }
/* 1493 */         data.set(CommonAttribute.REPROGRAM_STATUS, (Value)new ValueAdapter(state));
/* 1494 */         this.data.set(CommonAttribute.REPROGRAM, success ? CommonValue.OK : CommonValue.FAIL);
/* 1495 */       } else if (data.getValue(CommonAttribute.TYPE4_DATA) != null) {
/* 1496 */         if (!handleType4Executables(tool, securityCode, securityCodeCtrl, request, data, lttrack)) {
/*      */           return;
/*      */         }
/* 1499 */       } else if (request instanceof ReprogramSequenceRequest) {
/* 1500 */         ReprogramSequenceRequest rsr = (ReprogramSequenceRequest)request;
/* 1501 */         List events = (lttrack == null) ? null : new ArrayList();
/* 1502 */         List<E> controllers = rsr.getControllers();
/* 1503 */         List<Pair> state = initStatusPS(controllers);
/* 1504 */         List<ProgrammingData> plist = rsr.getProgrammingDataList();
/* 1505 */         for (int i = 0; i < plist.size(); i++) {
/* 1506 */           ProgrammingData pdata = plist.get(i);
/* 1507 */           if (!pdata.skipSameCalibration()) {
/*      */ 
/*      */             
/* 1510 */             List blobs = handleCalibrationFiles(pdata);
/* 1511 */             handleType4Data(blobs, rsr.getType4Data(), i);
/* 1512 */             adjustType4ProgrammingData(pdata, blobs.size());
/* 1513 */             if (i == 0 && this.robot == null) {
/* 1514 */               this.ui.notify(CommonAttribute.REPROGRAM);
/*      */             }
/* 1516 */             if (this.robot == null) {
/* 1517 */               displayIntermediateProgrammingInstructions(rsr.getInstructions(), true, i);
/* 1518 */               String label = controllers.get(i).toString();
/* 1519 */               this.ui.setReprogrammingController(label);
/*      */             } 
/* 1521 */             String securityCodePS = securityCode;
/* 1522 */             String securityCodeCtrlPS = securityCodeCtrl;
/* 1523 */             if (!pdata.isSecurityCodeRequired()) {
/* 1524 */               securityCodePS = null;
/* 1525 */               securityCodeCtrlPS = null;
/*      */             } 
/* 1527 */             flagSequenceProgramming(tool, i);
/* 1528 */             if (!reprogram(tool, securityCodePS, securityCodeCtrlPS, pdata, blobs, false)) {
/* 1529 */               state.set(i, updateStatusPS(state.get(i), ProgrammingSequence.FAIL));
/* 1530 */               int reaction = determineFailureReaction(rsr.getFailureHandling(), i);
/* 1531 */               if (reaction < 0) {
/* 1532 */                 displayReprogramStatusPS(state);
/* 1533 */                 start();
/* 1534 */               } else if (reaction > 0) {
/* 1535 */                 i = reaction;
/*      */               } 
/* 1537 */             } else if (this.robot == null) {
/* 1538 */               state.set(i, updateStatusPS(state.get(i), ProgrammingSequence.SUCCESS));
/* 1539 */               displayIntermediateProgrammingInstructions(rsr.getInstructions(), false, i);
/* 1540 */               if (lttrack != null) {
/* 1541 */                 boolean isType4 = ((ControllerReference)controllers.get(i)).isType4Application();
/*      */                 
/* 1543 */                 int downloadTime = (i == 0) ? TransferDataPanel.getDownloadTime() : 0;
/* 1544 */                 if (tool instanceof PtTool) {
/*      */                   try {
/* 1546 */                     int programmingTime = (int)(Long.parseLong(((PtTool)tool).getSPSTool().getToolProperty("sps_event_time")) / 1000L);
/* 1547 */                     trackLaborTimeData(events, downloadTime, programmingTime, isType4);
/* 1548 */                   } catch (Exception e) {
/* 1549 */                     log.error("failed to handle labor time tracking", e);
/*      */                   } 
/* 1551 */                 } else if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1552 */                   trackLaborTimeData(events, downloadTime, 112, isType4);
/*      */                 } 
/*      */               } 
/* 1555 */               if (this.bHasPinFile) {
/* 1556 */                 removePinFile();
/* 1557 */                 this.bHasPinFile = false;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1561 */         }  displayReprogramStatusPS(state);
/* 1562 */         if (this.robot == null && lttrack != null && events != null && events.size() > 0) {
/* 1563 */           recordLaborTimeData(lttrack, events);
/*      */         }
/* 1565 */         this.data.set(CommonAttribute.REPROGRAM, CommonValue.OK);
/*      */       } else {
/* 1567 */         if (data.getValue(CommonAttribute.TYPE4_STRINGS) != null) {
/* 1568 */           byte[] type4strings = (byte[])AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.TYPE4_STRINGS);
/* 1569 */           if (type4strings != null) {
/* 1570 */             writeType4Strings(type4strings);
/*      */           }
/*      */         } 
/* 1573 */         ProgrammingData pdata = ((ReprogramControllerRequest)request).getProgrammingData();
/* 1574 */         List blobs = handleCalibrationFiles(pdata);
/* 1575 */         if (this.robot == null) {
/* 1576 */           this.ui.notify(CommonAttribute.REPROGRAM);
/*      */         }
/* 1578 */         if (!reprogram(tool, securityCode, securityCodeCtrl, pdata, blobs, true)) {
/* 1579 */           if (this.robot == null) {
/* 1580 */             start();
/*      */           } else {
/* 1582 */             this.robot.handle(new Exception("reprogram failed"));
/*      */             return;
/*      */           } 
/* 1585 */         } else if (lttrack != null) {
/* 1586 */           int downloadTime = TransferDataPanel.getDownloadTime();
/* 1587 */           if (tool instanceof PtTool) {
/*      */             try {
/* 1589 */               int programmingTime = (int)(Long.parseLong(((PtTool)tool).getSPSTool().getToolProperty("sps_event_time")) / 1000L);
/* 1590 */               recordLaborTimeData(lttrack, downloadTime, programmingTime, false);
/* 1591 */             } catch (Exception e) {
/* 1592 */               log.error("failed to handle labor time tracking", e);
/*      */             } 
/* 1594 */           } else if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1595 */             recordLaborTimeData(lttrack, downloadTime, 100, false);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1599 */       invokeServer();
/* 1600 */     } catch (Exception e) {
/* 1601 */       handle(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void adjustType4ProgrammingData(ProgrammingData pdata, int blobs) {
/* 1606 */     List calibrations = pdata.getCalibrationFiles();
/* 1607 */     while (calibrations.size() > blobs) {
/* 1608 */       calibrations.remove(calibrations.size() - 1);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void recordLaborTimeData(SPSTime lttrack, int downloadTime, int programmingTime, boolean isType4) {
/* 1613 */     SPSEvent event = null;
/* 1614 */     if (isType4) {
/* 1615 */       event = SPSEvent.createSPSType4Event(downloadTime, programmingTime);
/*      */     } else {
/* 1617 */       event = SPSEvent.createSPSProgrammingEvent(downloadTime, programmingTime);
/*      */     } 
/* 1619 */     int totalTime = lttrack.computeTotal(event);
/* 1620 */     String ltfactor = lttrack.computeLTFactor(totalTime);
/* 1621 */     log.info("sps event: " + event.getActualDownloadTime() + "/" + event.getActualProgrammingTime() + "/" + event.getActualType4Time() + " => " + totalTime + "/" + ltfactor);
/* 1622 */     this.data.set(CommonAttribute.LABOR_TIME_TRACKING, (Value)new ValueAdapter(new PairImpl(event, ltfactor)));
/*      */   }
/*      */   
/*      */   protected void recordLaborTimeData(SPSTime lttrack, List events) {
/* 1626 */     int totalTime = lttrack.computeTotal(events);
/* 1627 */     String ltfactor = lttrack.computeLTFactor(totalTime);
/* 1628 */     this.data.set(CommonAttribute.LABOR_TIME_TRACKING, (Value)new ValueAdapter(new PairImpl(events, ltfactor)));
/*      */   }
/*      */   
/*      */   protected void trackLaborTimeData(List<SPSEvent> events, int downloadTime, int programmingTime, boolean isType4) {
/* 1632 */     if (isType4) {
/* 1633 */       events.add(SPSEvent.createSPSType4Event(downloadTime, programmingTime));
/*      */     } else {
/* 1635 */       events.add(SPSEvent.createSPSProgrammingEvent(downloadTime, programmingTime));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static byte[] gzip(byte[] blob, String blobName) throws Exception {
/* 1640 */     int BUFSIZE = 10000;
/* 1641 */     byte[] result = null;
/* 1642 */     GZIPOutputStream gzos = null;
/*      */     try {
/* 1644 */       ByteArrayInputStream bis = new ByteArrayInputStream(blob);
/* 1645 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 1646 */       gzos = new GZIPOutputStream(bos);
/* 1647 */       byte[] buf = new byte[BUFSIZE];
/* 1648 */       int pos = 0;
/* 1649 */       int cnt = 0;
/* 1650 */       while ((cnt = bis.read(buf, pos, BUFSIZE)) != -1) {
/* 1651 */         gzos.write(buf, 0, cnt);
/*      */       }
/* 1653 */       gzos.close();
/* 1654 */       gzos = null;
/* 1655 */       result = bos.toByteArray();
/* 1656 */       log.debug("Compressed: " + blobName + " Size: " + result.length);
/* 1657 */     } catch (Exception e) {
/* 1658 */       if (gzos != null) {
/*      */         try {
/* 1660 */           gzos.close();
/* 1661 */         } catch (Exception x) {}
/*      */       }
/*      */       
/* 1664 */       log.error("Fatal error in gzip.");
/* 1665 */       throw e;
/*      */     } 
/* 1667 */     return result;
/*      */   }
/*      */   
/*      */   public void triggerNextRequest() {
/* 1671 */     if (this.data.getValue(CommonAttribute.FINISH) != null) {
/* 1672 */       start();
/*      */     } else {
/* 1674 */       invokeServer();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute(Result result, AttributeValueMapExt data) {
/*      */     try {
/* 1683 */       Attribute selection = ((SelectionResult)result).getAttribute();
/* 1684 */       if (selection.equals(CommonAttribute.DEVICE)) {
/* 1685 */         handleToolSelection(data);
/* 1686 */       } else if (selection.equals(CommonAttribute.MODE)) {
/* 1687 */         Tool tool = getTool();
/* 1688 */         if (!tool.getType().getAdaptee().equals("TEST_DRIVER") || this.robot != null) {
/* 1689 */           if (data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/* 1690 */             startKeepAliveTask();
/*      */           }
/* 1692 */           invokeServer();
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1699 */       else if (isVINReadRequestRelatedSelection(selection)) {
/* 1700 */         handleVINReadRequest(null);
/* 1701 */       } else if (selection.equals(CommonAttribute.SEQUENCE)) {
/* 1702 */         ControllerReference reference = (ControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/* 1703 */         Value sequence = data.getValue(selection);
/* 1704 */         List methods = reference.getProgrammingMethods((String)((ValueAdapter)sequence).getAdaptee());
/* 1705 */         TypeSelectionRequest typeSelectionRequest = this.builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, methods, null);
/* 1706 */         this.ui.execute((AssignmentRequest)typeSelectionRequest, data);
/* 1707 */       } else if (selection.equals(CommonAttribute.FUNCTION)) {
/* 1708 */         ControllerReference reference = (ControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/* 1709 */         TypeSelectionRequest typeSelectionRequest = this.builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, reference.getProgrammingMethods(), null);
/* 1710 */         this.ui.execute((AssignmentRequest)typeSelectionRequest, data);
/* 1711 */       } else if (selection.equals(CommonAttribute.CONTROLLER)) {
/* 1712 */         if (supportSPSFunctions()) {
/* 1713 */           ControllerReference reference = (ControllerReference)data.getValue(selection);
/* 1714 */           if (isPassThru((AttributeValueMap)data)) {
/* 1715 */             List sequences = reference.getProgrammingSequences();
/* 1716 */             SequenceSelectionRequest sequenceSelectionRequest = this.builder.makeSequenceSelectionRequest(CommonAttribute.SEQUENCE, sequences, null);
/* 1717 */             this.ui.execute((AssignmentRequest)sequenceSelectionRequest, data);
/*      */           } else {
/* 1719 */             List functions = reference.getProgrammingFunctions();
/* 1720 */             FunctionSelectionRequest functionSelectionRequest = this.builder.makeFunctionSelectionRequest(CommonAttribute.FUNCTION, functions, null);
/* 1721 */             this.ui.execute((AssignmentRequest)functionSelectionRequest, data);
/*      */           } 
/*      */         } else {
/* 1724 */           Value controller = data.getValue(selection);
/* 1725 */           ControllerReference reference = (ControllerReference)controller;
/* 1726 */           TypeSelectionRequest typeSelectionRequest = this.builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, reference.getProgrammingMethods(), null);
/* 1727 */           this.ui.execute((AssignmentRequest)typeSelectionRequest, data);
/*      */         } 
/* 1729 */       } else if (selection.equals(CommonAttribute.CONTROLLER_METHOD)) {
/* 1730 */         invokeServer();
/*      */       } else {
/* 1732 */         invokeServer();
/*      */       } 
/* 1734 */     } catch (Exception e) {
/* 1735 */       handle(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isPassThru(AttributeValueMap data) throws Exception {
/* 1740 */     String tool = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 1741 */     if (tool == null || (!tool.equals("T2_REMOTE") && !tool.equals("TEST_DRIVER")))
/* 1742 */       return true; 
/* 1743 */     if (tool.equals("TEST_DRIVER")) {
/* 1744 */       String dtype = (String)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_TYPE);
/* 1745 */       if (dtype != null && (dtype.equalsIgnoreCase("pass-thru") || dtype.equalsIgnoreCase("J2534"))) {
/* 1746 */         return true;
/*      */       }
/*      */     } 
/* 1749 */     return false;
/*      */   }
/*      */   
/*      */   protected void checkPermissionProceedSameVIN(AttributeValueMap data) {
/* 1753 */     if (!(data instanceof AttributeValueMapExt) || this.savePointProceedSameVIN != null) {
/*      */       return;
/*      */     }
/* 1756 */     Value permission = data.getValue(CommonAttribute.PROCEED_SAME_VIN);
/* 1757 */     if (permission != null && 
/* 1758 */       permission.equals(CommonValue.OK)) {
/* 1759 */       this.savePointProceedSameVIN = ((AttributeValueMapExt)data).getSavePoint();
/* 1760 */       this.ui.notify(CommonAttribute.PROCEED_SAME_VIN);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleToolSelection(AttributeValueMapExt data) {
/* 1767 */     ValueAdapter selection = (ValueAdapter)data.getValue(CommonAttribute.DEVICE);
/* 1768 */     String key = (String)selection.getAdaptee();
/*      */     
/* 1770 */     List processes = null;
/* 1771 */     List<Tool> tools = ClientAppContextProvider.getClientAppContext().getTools();
/* 1772 */     for (int i = 0; i < tools.size(); i++) {
/* 1773 */       Tool tool = tools.get(i);
/* 1774 */       if (tool.getId().equals(key)) {
/* 1775 */         this.data.set(CommonAttribute.DEVICE, (Value)tool.getType());
/* 1776 */         this.data.set(CommonAttribute.TOOL, (Value)new ValueAdapter(tool));
/* 1777 */         this.data.set(CommonAttribute.TOOL_ID, (Value)new ValueAdapter(tool.getId()));
/* 1778 */         if (tool instanceof J2534Tool) {
/* 1779 */           this.data.set(CommonAttribute.DEVICE_J2534_NAME, (Value)new ValueAdapter(((J2534Tool)tool).getDriverId()));
/* 1780 */           this.data.set(CommonAttribute.DEVICE_J2534_VERSION, (Value)new ValueAdapter(((J2534Tool)tool).getDriverVersion()));
/*      */         } 
/* 1782 */         processes = getProcessList(tool);
/* 1783 */         if (this.robot == null && tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 1784 */           TestDriverImpl td = (TestDriverImpl)tool;
/* 1785 */           if (td.isAutomaticMode()) {
/* 1786 */             this.robot = new SPSRobot(this, td, this.ui);
/* 1787 */             this.robot.reset();
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1791 */         tool.reset();
/*      */       } 
/*      */     } 
/* 1794 */     ProcessSelectionRequest request = makeProcessSelectionRequest(processes);
/* 1795 */     if (this.robot != null) {
/* 1796 */       this.robot.execute((AssignmentRequest)request, data);
/*      */     } else {
/* 1798 */       this.ui.execute((AssignmentRequest)request, data);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initTool() throws Exception {
/* 1803 */     ValueAdapter selection = (ValueAdapter)this.data.getValue(CommonAttribute.TOOL);
/* 1804 */     Tool tool = (Tool)selection.getAdaptee();
/* 1805 */     tool.init();
/*      */   }
/*      */   
/*      */   protected boolean isVINReadRequestRelatedSelection(Attribute selection) {
/* 1809 */     return selection instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.ToolAttribute;
/*      */   }
/*      */   
/*      */   protected String getHardwareInfo() {
/*      */     try {
/* 1814 */       VIT1Data vit1 = (VIT1Data)this.data.getValue(CommonAttribute.VIT1);
/* 1815 */       return resourceProvider.getLabel(null, "summaryScreen.hardware-number") + " " + vit1.getHWNumber();
/* 1816 */     } catch (Exception x) {
/*      */       
/* 1818 */       return "";
/*      */     } 
/*      */   }
/*      */   protected boolean confirmSameCalibration() {
/* 1822 */     String question = resourceProvider.getMessage(null, "sps.warning.same-calibration");
/* 1823 */     if (this.ui.displayQuestionDialog(question)) {
/* 1824 */       question = resourceProvider.getMessage(null, "sps.warning.confirm-same-calibration");
/* 1825 */       return this.ui.displayQuestionDialog(question);
/*      */     } 
/* 1827 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void prepareOfflineFinalInstructions(AttributeValueMapExt data) {
/* 1832 */     if (this.robot != null || !(this.ui instanceof UIAgent)) {
/*      */       return;
/*      */     }
/* 1835 */     if (data.getValue(CommonAttribute.INSTRUCTION_DOWNLOAD) != null) {
/* 1836 */       AssignmentRequest request = (AssignmentRequest)AVUtil.accessValue((AttributeValueMap)data, CommonAttribute.INSTRUCTION_DOWNLOAD);
/* 1837 */       String content = ((InstructionDisplayRequest)request).getContent();
/* 1838 */       int pos = content.indexOf("final-instructions");
/* 1839 */       int length = pos + "final-instructions".length();
/* 1840 */       String id = content.substring(0, length);
/* 1841 */       ((UIAgent)this.ui).prepareOfflineFinalInstructions(id);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean handleLostServerConnection() {
/* 1846 */     if (this.robot != null || !(this.ui instanceof UIAgent)) {
/* 1847 */       return false;
/*      */     }
/* 1849 */     Value success = this.data.getValue(CommonAttribute.REPROGRAM);
/* 1850 */     if (success != null && success.equals(CommonValue.OK) && 
/* 1851 */       this.data.getValue(CommonAttribute.FINAL_INSTRUCTIONS) == null) {
/* 1852 */       AssignmentRequest request = (AssignmentRequest)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.INSTRUCTION_DOWNLOAD);
/* 1853 */       this.ui.execute(request, this.data);
/* 1854 */       this.ui.notify(CommonAttribute.FAILURE);
/*      */       
/* 1856 */       return true;
/*      */     } 
/*      */     
/* 1859 */     return false;
/*      */   }
/*      */   
/*      */   public void handle(Exception e) {
/* 1863 */     if (this.robot != null) {
/* 1864 */       if (e instanceof RequestException) {
/* 1865 */         Request request = ((RequestException)e).getRequest();
/* 1866 */         if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/* 1867 */           this.robot.execute((AssignmentRequest)request, this.data);
/* 1868 */           handleToolSelection(this.data); return;
/*      */         } 
/* 1870 */         if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingDataDownloadContinuationRequest) {
/* 1871 */           this.robot.execute(request, this.data);
/* 1872 */           invokeServer();
/*      */           return;
/*      */         } 
/* 1875 */       } else if (e instanceof SPSException && (
/* 1876 */         (SPSException)e).getMessage().equals(CommonException.ConfirmSameCalibration.getID())) {
/* 1877 */         this.data.set(CommonAttribute.SAME_CALIBRATIONS, CommonValue.OK);
/* 1878 */         invokeServer();
/*      */         
/*      */         return;
/*      */       } 
/* 1882 */       this.robot.handle(e);
/* 1883 */     } else if (e instanceof com.eoos.gm.tis2web.frame.export.common.InvalidSessionException) {
/* 1884 */       boolean offline = handleLostServerConnection();
/* 1885 */       this.ui.handleException(CommonException.SessionExpired.getID());
/* 1886 */       if (!offline)
/* 1887 */         System.exit(-1); 
/* 1888 */     } else if (e instanceof com.eoos.gm.tis2web.sps.client.system.CommunicationException) {
/* 1889 */       boolean offline = handleLostServerConnection();
/* 1890 */       this.ui.handleException(e);
/* 1891 */       if (!offline)
/* 1892 */         System.exit(-1); 
/* 1893 */     } else if (e instanceof SPSException) {
/* 1894 */       handleSPSException((SPSException)e);
/*      */     }
/* 1896 */     else if (e instanceof RequestException) {
/* 1897 */       execute(((RequestException)e).getRequest(), (AttributeValueMap)this.data);
/* 1898 */       if (((RequestException)e).getRequest() instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.HardwareKeyRequest && 
/* 1899 */         this.data.getValue(CommonAttribute.HARDWARE_KEY) == null) {
/* 1900 */         handle((Exception)new MissingHardwareKeyException());
/*      */       }
/*      */     } else {
/*      */       
/* 1904 */       if (e instanceof MissingHardwareKeyException) {
/* 1905 */         this.ui.handleException(((MissingHardwareKeyException)e).getMessage());
/* 1906 */       } else if (e instanceof ECUDataReadException) {
/* 1907 */         this.ui.handleException(((ECUDataReadException)e).getMessage());
/* 1908 */       } else if (e instanceof NativeSubsystemException) {
/* 1909 */         this.ui.handleException(((NativeSubsystemException)e).getMessage());
/* 1910 */       } else if (e instanceof ReprogrammingException) {
/* 1911 */         this.ui.handleException(((ReprogrammingException)e).getMessage());
/* 1912 */       } else if (e instanceof ToolInitException) {
/* 1913 */         this.ui.handleException(((ToolInitException)e).getMessage());
/* 1914 */       } else if (e instanceof VINReadException) {
/* 1915 */         this.ui.handleException(((VINReadException)e).getMessage());
/* 1916 */       } else if (!(e instanceof com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolCommunicationException)) {
/*      */ 
/*      */         
/* 1919 */         if (e instanceof NoValidVIT1Exception) {
/* 1920 */           this.ui.handleException(((NoValidVIT1Exception)e).getMessage());
/* 1921 */         } else if (e instanceof NoMoreFilesException) {
/* 1922 */           this.ui.handleException(((NoMoreFilesException)e).getMessage());
/* 1923 */         } else if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoTestDriverFileFoundException) {
/*      */           
/* 1925 */           this.ui.handleException("Missing TestDriver Input File");
/*      */         
/*      */         }
/* 1928 */         else if (e instanceof ExceptionWrapper) {
/* 1929 */           Throwable wrapped = ((ExceptionWrapper)e).getWrappedException();
/* 1930 */           if (wrapped instanceof java.io.IOException) {
/* 1931 */             boolean offline = handleLostServerConnection();
/* 1932 */             this.ui.handleException((Exception)wrapped);
/* 1933 */             if (!offline)
/* 1934 */               System.exit(-1); 
/*      */           } 
/*      */         } else {
/* 1937 */           log.error(e.getMessage(), e);
/* 1938 */           handleLostServerConnection();
/* 1939 */           this.ui.handleException(CommonException.ServerSideFailure.getID());
/*      */         } 
/*      */       } 
/* 1942 */       this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 1943 */       start();
/*      */     } 
/*      */   }
/*      */   public SPSClientController() {
/* 1947 */     this.P_ERRORCODE = Pattern.compile("^\\s*(E\\d+)\\D*");
/*      */   }
/*      */   private String parseErrorCode(SPSException e) {
/* 1950 */     String msg = ExceptionImpl.getInstance(e.getMessage()).getDenotation(null);
/* 1951 */     Matcher matcher = this.P_ERRORCODE.matcher(msg);
/* 1952 */     if (matcher.find()) {
/* 1953 */       return matcher.group(1);
/*      */     }
/* 1955 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void handleSPSException(SPSException e) {
/* 1960 */     Value mode = this.data.getValue(CommonAttribute.MODE);
/* 1961 */     if (!mode.equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/* 1962 */       log.debug("checking automatic error notification....");
/* 1963 */       String errorCode = parseErrorCode(e);
/* 1964 */       if (errorCode != null) {
/* 1965 */         log.debug("...error code: " + errorCode);
/* 1966 */         AttributeValueMapImpl attributeValueMapImpl = new AttributeValueMapImpl((AttributeValueMap)this.data);
/* 1967 */         log.debug("...retrieving message");
/* 1968 */         String message = this.facade.getMessage(errorCode, (AttributeValueMap)attributeValueMapImpl);
/* 1969 */         if (message != null) {
/* 1970 */           boolean sendEmail = true;
/* 1971 */           if (message.length() > 0) {
/* 1972 */             log.debug("....displaying the retrieved message");
/* 1973 */             sendEmail = this.ui.displayQuestionDialog(message);
/*      */           } else {
/* 1975 */             log.debug("...message length==0, skipping confirmation");
/*      */           } 
/* 1977 */           if (sendEmail) {
/* 1978 */             log.debug("...sending error notification");
/* 1979 */             this.facade.sendErrorNotificationEmail(errorCode, (AttributeValueMap)attributeValueMapImpl);
/*      */           } 
/*      */         } else {
/* 1982 */           log.debug("...no message assigned, skipping");
/*      */         } 
/*      */       } else {
/* 1985 */         log.debug("....unable to extract error code for exception, skipping");
/*      */       } 
/*      */     } 
/* 1988 */     if (e.getMessage().equals(CommonException.ConfirmSameCalibration.getID())) {
/* 1989 */       if (!confirmSameCalibration()) {
/* 1990 */         this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 1991 */         this.ui.displayFinishButtons();
/*      */       } else {
/* 1993 */         this.data.set(CommonAttribute.SAME_CALIBRATIONS, CommonValue.OK);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1999 */     String hardware = null;
/* 2000 */     if (e.getMessage().equals(CommonException.InvalidHardware.getID())) {
/* 2001 */       hardware = getHardwareInfo();
/* 2002 */       this.ui.handleException(e.getMessage(), hardware);
/* 2003 */     } else if (e.getMessage().equals(CommonException.UnknownHardware.getID())) {
/* 2004 */       hardware = getHardwareInfo();
/* 2005 */       this.ui.handleException(e.getMessage(), hardware);
/*      */     } else {
/* 2007 */       this.ui.handleException(e.getMessage());
/*      */     } 
/* 2009 */     if (!e.getMessage().equals(CommonException.InvalidVIN.getID()))
/*      */     {
/* 2011 */       if (!e.getMessage().equals(CommonException.InvalidVCIInput.getID()))
/*      */       {
/* 2013 */         if (!e.getMessage().equals(CommonException.INVALID_ARCHIVE_VCI.getID()))
/*      */         {
/* 2015 */           if (!e.getMessage().equals(CommonException.BulletinNotAvailable.getID())) {
/*      */ 
/*      */             
/*      */             try {
/* 2019 */               Tool tool = getTool();
/* 2020 */               if (tool.getType().getAdaptee().equals("TEST_DRIVER")) {
/* 2021 */                 TestDriverImpl.setDataInformation(this.data);
/* 2022 */                 ((TestDriverImpl)tool).writeLog();
/* 2023 */                 String msg = ExceptionImpl.getInstance(e.getMessage()).getDenotation(null);
/* 2024 */                 if (hardware != null) {
/* 2025 */                   msg = msg + " " + hardware;
/*      */                 }
/* 2027 */                 ((TestDriverImpl)tool).writeExceptionLog(msg);
/* 2028 */                 ((TestDriverImpl)tool).writeVIT1();
/*      */               } 
/* 2030 */             } catch (Exception x) {}
/*      */             
/* 2032 */             this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 2033 */             this.ui.displayFinishButtons();
/*      */           }  }  }  } 
/*      */   }
/*      */   
/*      */   private void startKeepAliveTask() {
/* 2038 */     log.debug("starting keep-alive task ...");
/*      */     try {
/* 2040 */       if (this.ptKeepAlive == null) {
/* 2041 */         String sessionID = ClientAppContextProvider.getClientAppContext().getSessionID();
/* 2042 */         final KeepAliveTask task = new KeepAliveTask(sessionID);
/*      */         
/* 2044 */         Runnable executeKeepAlive = new Runnable() {
/*      */             public void run() {
/* 2046 */               SPSClientController.log.debug("sending keep alive");
/* 2047 */               ServerTaskExecution.getInstance().execute((Task)task);
/*      */             }
/*      */           };
/*      */ 
/*      */         
/* 2052 */         this.ptKeepAlive = new PeriodicTask(executeKeepAlive, 300000L, 12);
/*      */         
/* 2054 */         this.ptKeepAlive.start();
/* 2055 */         log.debug("...started (scheduled) keep alive task ");
/*      */       } else {
/* 2057 */         log.debug("...ignoring keep alive task start request, since it already exists (runs)");
/*      */       }
/*      */     
/* 2060 */     } catch (Exception e) {
/* 2061 */       log.error("unable to start keep alive task, ignoring - exception:" + e, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void stopKeepAliveTask() {
/* 2067 */     log.debug("stopping keep alive task ...");
/*      */     try {
/* 2069 */       if (this.ptKeepAlive != null) {
/* 2070 */         this.ptKeepAlive.stop();
/* 2071 */         log.debug("...done stopping keep alive task");
/*      */       } else {
/* 2073 */         log.debug("...keep alive task has not been started yet, ignoring request");
/*      */       }
/*      */     
/* 2076 */     } catch (Exception e) {
/* 2077 */       log.error("unable to stop keep alive task, ignoring - exception: " + e, e);
/*      */     } 
/* 2079 */     this.ptKeepAlive = null;
/*      */   }
/*      */   
/*      */   protected void invokeServer() {
/*      */     try {
/* 2084 */       if (log.isDebugEnabled()) {
/* 2085 */         log.debug("invoking server with value map: ");
/* 2086 */         if (this.data instanceof AttributeValueMapRI) {
/* 2087 */           ((AttributeValueMapRI)this.data).printSequence(new AttributeValueMapRI.OutputCallback() {
/* 2088 */                 private int counter = 0;
/*      */                 
/*      */                 public void println(String string) {
/* 2091 */                   SPSClientController.log.debug("entry " + ++this.counter + ": " + string);
/*      */                 }
/*      */               });
/*      */         } else {
/* 2095 */           log.debug(AVUtil.toMap((AttributeValueMap)this.data));
/*      */         } 
/*      */       } 
/* 2098 */       Boolean response = this.facade.execute((AttributeValueMap)this.data);
/*      */       
/* 2100 */       if (response.equals(Boolean.TRUE)) {
/* 2101 */         if (this.robot == null) {
/* 2102 */           this.data.set(CommonAttribute.FINISH, CommonValue.OK);
/* 2103 */           this.ui.notify(CommonAttribute.FINISH);
/*      */         } else {
/* 2105 */           this.robot.notify(CommonAttribute.FINISH);
/* 2106 */           start();
/*      */         } 
/*      */       }
/* 2109 */     } catch (Exception e) {
/*      */       
/* 2111 */       handle(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void logReqMethId(List rmds) {
/*      */     try {
/* 2117 */       if (rmds != null) {
/* 2118 */         Iterator it = rmds.iterator();
/* 2119 */         StringBuffer output = null;
/* 2120 */         while (it.hasNext()) {
/* 2121 */           Object element = it.next();
/* 2122 */           if (element instanceof RequestMethodData) {
/* 2123 */             String deviceID = String.valueOf(((RequestMethodData)element).getDeviceID());
/* 2124 */             String reqMetID = String.valueOf(((RequestMethodData)element).getRequestMethodID());
/* 2125 */             if (output == null)
/* 2126 */               output = new StringBuffer("Request Method Data: "); 
/* 2127 */             output.append("[ RM_ID:" + reqMetID + "," + "DeviceID:" + deviceID + "], ");
/*      */           } 
/*      */         } 
/*      */         
/* 2131 */         if (output != null) {
/* 2132 */           String logString = output.toString().substring(0, output.toString().length() - 2);
/*      */ 
/*      */ 
/*      */           
/* 2136 */           log.debug(logString);
/*      */         } 
/*      */       } 
/* 2139 */     } catch (Exception e) {
/* 2140 */       log.error("unable to build log String for Request Method Data, e: " + e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void sendFailureMail(String vin, String errorMessage) {
/* 2145 */     log.debug("...preparing failure email....");
/*      */     try {
/* 2147 */       ClientAppContext context = ClientAppContextProvider.getClientAppContext();
/* 2148 */       String sessionID = context.getSessionID();
/* 2149 */       String bac = context.getBACCode();
/* 2150 */       String subject = "programming failure: " + sessionID + "/" + vin;
/*      */       
/* 2152 */       StringWriter sw = new StringWriter();
/* 2153 */       PrintWriter pw = new PrintWriter(sw);
/*      */       
/* 2155 */       pw.print("USER ID: ");
/* 2156 */       pw.println(sessionID);
/* 2157 */       pw.print("BAC: ");
/* 2158 */       pw.println(bac);
/* 2159 */       pw.print("VIN: ");
/* 2160 */       pw.println(vin);
/* 2161 */       pw.print("TIMESTAMP: ");
/* 2162 */       pw.println(DateFormat.getDateTimeInstance(3, 3, Locale.ENGLISH).format(new Date()));
/* 2163 */       if (errorMessage != null) {
/* 2164 */         pw.print("MESSAGE: ");
/* 2165 */         pw.println(errorMessage);
/*      */       } 
/*      */       
/* 2168 */       Configuration cfg = SPSClientUtil.getConfiguration();
/* 2169 */       List<String> recipients = ConfigurationUtil.getList(cfg, "failure.mail.recipients", null);
/* 2170 */       if (!Util.isNullOrEmpty(recipients)) {
/* 2171 */         List<Pattern> patterns = ConfigurationUtil.getList(cfg, "failure.mail.file.name.filters", new Util.ObjectCreation<Pattern>()
/*      */             {
/*      */               public Pattern createObject(String string) {
/* 2174 */                 return Pattern.compile(string);
/*      */               }
/*      */             });
/*      */         
/* 2178 */         Collection<DataSource> attachments = null;
/* 2179 */         if (!Util.isNullOrEmpty(patterns)) {
/* 2180 */           final File root = new File(context.getHomeDir());
/* 2181 */           Util.MatchingCollector collector = new Util.MatchingCollector(root, patterns);
/* 2182 */           Util.visitFiles(root, (Util.FileVisitor)collector);
/*      */           
/* 2184 */           Collection<File> files = collector.getFiles();
/* 2185 */           attachments = Util.transformCollection(files, new Transforming<File, DataSource>()
/*      */               {
/*      */                 public DataSource transform(File file) {
/* 2188 */                   if (file.isFile()) {
/* 2189 */                     return (DataSource)new FileDataSource(file)
/*      */                       {
/*      */                         public String getContentType() {
/* 2192 */                           return "application/octet-stream";
/*      */                         }
/*      */                         
/*      */                         public String getName() {
/* 2196 */                           return Util.relativize(this.file, root).getPath();
/*      */                         }
/*      */                       };
/*      */                   }
/*      */                   
/* 2201 */                   return null;
/*      */                 }
/*      */               });
/*      */           
/* 2205 */           while (attachments.remove(null));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2210 */         MailRelayingService mailRelay = MailRelayingServiceFactory.createService(null);
/* 2211 */         mailRelay.send(null, null, recipients, subject, sw.toString(), attachments);
/* 2212 */         log.debug("successfully send failure mail");
/*      */       } else {
/*      */         
/* 2215 */         log.debug("unable to send failure mail - no recipients configured");
/*      */       } 
/* 2217 */     } catch (Exception e) {
/* 2218 */       log.error("unable to send failure email - exception: ", e);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\SPSClientController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */