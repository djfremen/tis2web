/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ReprogramProgress;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.ReprogramProgressTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ProgrammingResult;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolCallback;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ECUDataReadException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ReprogrammingException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.ToolInitException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.VINReadException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ReProgrammer;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolContextImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.VIT1LoggingData;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.VCSNavigator;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.VCSNavigatorImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.SPSTool;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PtTool
/*     */   implements Tool, ToolCallback, ReprogramProgressTool
/*     */ {
/*  43 */   protected static final Integer REQUEST_EVENT = Integer.valueOf(3);
/*  44 */   protected static final Integer GEN_REQUEST_EVENT = Integer.valueOf(5);
/*     */   
/*     */   protected static final String SPSVCSDLL = "SPSVCS";
/*     */   
/*     */   protected static final String REPROGRAM = "REPROG";
/*     */   
/*     */   protected static final String REPLACE_AND_REPROGRAM = "REPLACE";
/*     */   private static final String GME_DATA_REQUEST = "gme";
/*     */   private static final String NAO_DATA_REQUEST = "nao";
/*     */   protected String id;
/*     */   protected String type;
/*  55 */   protected PtVinHandler vinHandler = null;
/*     */   protected SimpleVIT vinVIT1Data;
/*     */   protected Map ecuVIT1DataMap;
/*     */   private VIT1LoggingData loggingMap;
/*     */   protected VCSNavigator navigator;
/*     */   protected ISPSTool spsTool;
/*     */   protected ToolContext toolContext;
/*  62 */   private static Logger log = Logger.getLogger(PtTool.class);
/*     */   
/*     */   public String getId() {
/*  65 */     return this.id;
/*     */   }
/*     */   
/*     */   public ValueAdapter getType() {
/*  69 */     return new ValueAdapter(this.type);
/*     */   }
/*     */   
/*     */   public Object getVIN(DeviceCommunicationCallback dcb, Object requestData) throws Exception {
/*  73 */     log.info("Requesting VIN: (" + this.id + ", " + this.type + ")");
/*     */     
/*  75 */     Value programmingMode = ((AttributeValueMap)requestData).getValue(CommonAttribute.MODE);
/*  76 */     String progMode = "REPROG";
/*  77 */     if (programmingMode.equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/*  78 */       progMode = "REPLACE";
/*     */     }
/*  80 */     this.toolContext.setProgrammingMode(progMode);
/*  81 */     if (this.vinHandler == null) {
/*  82 */       this.vinHandler = new PtVinHandler();
/*     */     }
/*  84 */     VIT1 vinVIT1 = null;
/*     */     try {
/*  86 */       this.vinVIT1Data = this.vinHandler.getVinVITData(dcb, requestData, this.spsTool, this.navigator, this.toolContext);
/*  87 */       vinVIT1 = this.vinVIT1Data.getVIT1();
/*  88 */     } catch (RequestException e) {
/*  89 */       log.debug("Request exception received - continue navigation.");
/*  90 */       throw e;
/*  91 */     } catch (VINReadException e) {
/*  92 */       log.debug("VIN reading failed.");
/*  93 */       throw e;
/*  94 */     } catch (Exception e) {
/*  95 */       log.error("Cannot build VIT1 from raw data: " + e);
/*  96 */       e.printStackTrace();
/*  97 */       throw e;
/*     */     } 
/*  99 */     if (log.isDebugEnabled()) {
/* 100 */       String vinStr = (String)vinVIT1.getVIN();
/* 101 */       log.debug("VIN: " + vinStr);
/*     */     } 
/* 103 */     this.vinHandler = null;
/* 104 */     this.ecuVIT1DataMap = new HashMap<Object, Object>();
/* 105 */     this.loggingMap = new VIT1LoggingData();
/* 106 */     return vinVIT1;
/*     */   }
/*     */   
/*     */   public Object getECUData(DeviceCommunicationCallback dcb, Object requestData, AttributeValueMap context) throws Exception {
/* 110 */     VIT1 result = null;
/* 111 */     DataReadDelegate delegate = null;
/* 112 */     String adapterType = getAdapterType(requestData);
/* 113 */     String className = null;
/*     */     try {
/* 115 */       String pkg = getClass().getPackage().getName();
/* 116 */       className = pkg.substring(0, pkg.lastIndexOf('.') + 1) + adapterType + ".DataReadDelegate_" + adapterType.toUpperCase(Locale.ENGLISH);
/* 117 */       delegate = (DataReadDelegate)Class.forName(className).newInstance();
/* 118 */     } catch (Exception e) {
/* 119 */       log.error("Cannot create ECU DataReadHandler: " + className);
/* 120 */       throw new ECUDataReadException();
/*     */     } 
/*     */     
/* 123 */     SimpleVIT vit1Data = null;
/*     */     try {
/* 125 */       vit1Data = delegate.getECUData(dcb, requestData, context, this);
/* 126 */       if (vit1Data != null) {
/* 127 */         String key = vit1Data.getEcuAddr();
/* 128 */         if (key != null) {
/* 129 */           this.ecuVIT1DataMap.put(key, vit1Data);
/* 130 */           this.loggingMap.addSimpleVIT(key, vit1Data);
/*     */         } 
/*     */       } 
/* 133 */     } catch (Exception e) {
/* 134 */       log.error("Cannot get ECU Data from native interface.");
/* 135 */       throw new ECUDataReadException();
/*     */     } 
/*     */     
/*     */     try {
/* 139 */       result = (vit1Data != null) ? vit1Data.getVIT1() : null;
/* 140 */     } catch (Exception e) {
/* 141 */       log.error("Cannot build ECU-VIT1 from SimpleVIT");
/*     */     } 
/* 143 */     if (result == null || !vit1Data.hasData()) {
/* 144 */       log.error("Giving up. Cannot retrieve ECU data.");
/* 145 */       throw new ECUDataReadException();
/*     */     } 
/* 147 */     return result;
/*     */   }
/*     */   
/*     */   public Object reprogram(MessageCallback mcb, Object programmingData, Object dataUnits, String securityCode, String securityCodeCtrl) throws Exception {
/* 151 */     log.info("Reprogramming: (" + this.id + ", " + this.type + ")");
/* 152 */     ReProgrammer prog = new ReProgrammer();
/* 153 */     ProgrammingResult result = null;
/*     */     try {
/* 155 */       result = prog.reprogram(this, mcb, programmingData, dataUnits, securityCode, securityCodeCtrl);
/* 156 */       log.info("Reprogramming: " + result.getStatus().booleanValue());
/* 157 */     } catch (Exception e) {
/* 158 */       log.info("Reprogramming failed: " + e.toString());
/* 159 */       throw new ReprogrammingException();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VIT1 getVIT1(Integer deviceId) {
/* 187 */     VIT1 result = null;
/* 188 */     SimpleVIT vit1Data = null;
/*     */     
/*     */     try {
/* 191 */       vit1Data = (SimpleVIT)this.ecuVIT1DataMap.get(Integer.toHexString(deviceId.intValue()));
/* 192 */       result = vit1Data.getVIT1();
/* 193 */     } catch (Exception e) {
/* 194 */       log.info("No VIT1Data found for deviceID " + deviceId + ". Using VIN-VIT1 instead.");
/*     */     } 
/*     */     
/* 197 */     if (result == null) {
/* 198 */       result = this.vinVIT1Data.getVIT1();
/*     */     }
/* 200 */     return result;
/*     */   }
/*     */   
/*     */   public ISPSTool getSPSTool() {
/* 204 */     return this.spsTool;
/*     */   }
/*     */   
/*     */   public ToolContext getToolContext() {
/* 208 */     return this.toolContext;
/*     */   }
/*     */   
/*     */   public SimpleVIT getVINVITData() {
/* 212 */     return this.vinVIT1Data;
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/* 216 */     log.info("Initializing tool: (" + this.id + ", " + this.type + ")");
/* 217 */     this.toolContext = (ToolContext)new ToolContextImpl();
/* 218 */     reset();
/* 219 */     this.vinVIT1Data = null;
/*     */     try {
/* 221 */       this.navigator = (VCSNavigator)new VCSNavigatorImpl();
/* 222 */       if (!this.navigator.createNavigator()) {
/* 223 */         throw new Exception("Could not create navigator");
/*     */       }
/* 225 */     } catch (Exception e) {
/* 226 */       log.error("Could not create navigation subsystem: " + e);
/* 227 */       throw new ToolInitException();
/*     */     } 
/*     */     try {
/* 230 */       this.spsTool = SPSTool.getInstance("SPSVCS");
/* 231 */     } catch (Exception e) {
/* 232 */       log.error("Cannot initialize SPS VCS subsystem: " + e);
/* 233 */       throw new ToolInitException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 238 */     log.debug("Reset called: " + this.id);
/* 239 */     if (this.navigator != null) {
/* 240 */       log.debug("Giving up navigation");
/* 241 */       this.navigator.discardNavigator();
/* 242 */       this.navigator = null;
/*     */     } 
/* 244 */     if (this.spsTool != null) {
/* 245 */       log.debug("Giving up SPSVCS DLL.");
/* 246 */       discardSPSVCS();
/* 247 */       this.spsTool = null;
/*     */     } 
/* 249 */     this.ecuVIT1DataMap = new HashMap<Object, Object>();
/* 250 */     this.loggingMap = new VIT1LoggingData();
/* 251 */     this.vinVIT1Data = null;
/*     */   }
/*     */   
/*     */   private void discardSPSVCS() {
/* 255 */     boolean result = false;
/* 256 */     result = SPSTool.discardInstance("SPSVCS");
/* 257 */     log.debug("Discarding SPSVCS: " + result);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getAdapterType(Object requestData) {
/* 262 */     String result = "nao";
/* 263 */     RequestMethodData rmd = null;
/*     */     try {
/* 265 */       rmd = ((List<RequestMethodData>)requestData).get(0);
/* 266 */     } catch (Exception e) {}
/*     */     
/* 268 */     if (rmd != null) {
/*     */       try {
/* 270 */         rmd.getVINReadType();
/* 271 */       } catch (Exception e) {
/* 272 */         result = "gme";
/*     */       } 
/*     */     }
/* 275 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleVIT getEcuVIT1Data(String ecuAdr) {
/* 302 */     SimpleVIT result = null;
/* 303 */     if (ecuAdr != null) {
/* 304 */       result = (SimpleVIT)this.ecuVIT1DataMap.get(ecuAdr);
/*     */     }
/* 306 */     return result;
/*     */   }
/*     */   
/*     */   public void setReprogramProgress(ReprogramProgress reprogramProgress) {
/* 310 */     if (this.spsTool != null) {
/* 311 */       this.spsTool.setReprogramProgress(reprogramProgress);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getVIT1History() {
/* 321 */     return this.loggingMap.getVIT1History();
/*     */   }
/*     */   
/*     */   public void resetVIT1History() {
/* 325 */     this.loggingMap.resetVIT1History();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\common\PtTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */