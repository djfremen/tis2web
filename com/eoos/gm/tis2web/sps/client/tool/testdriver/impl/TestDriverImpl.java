/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ExecutionMode;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.BooleanSelectOptionImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.InputOptionImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ProgrammingResultImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.RemoteTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolOptionsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder.VITBuilderProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.ECUDataHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.TestDriverLog;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
/*     */ import com.eoos.gm.tis2web.sps.client.util.SPSClientUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.RequestMethodData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestDriverImpl
/*     */   extends RemoteTool
/*     */   implements ToolOptions, SettingsObserver
/*     */ {
/*  52 */   private static Logger log = Logger.getLogger(TestDriverImpl.class);
/*     */   
/*  54 */   private static final String[] vit1PathVehicle = new String[] { "" };
/*     */   
/*  56 */   private static final String[] vit1Controller = new String[] { "" };
/*     */   
/*  58 */   private static final String[] vit1Path = new String[] { "" };
/*     */   
/*  60 */   private static final String[] booleans = new String[] { "false", "true" };
/*     */   
/*  62 */   private static Integer programmingSequenceStep = null;
/*     */   
/*  64 */   private ECUDataHandler ecuMan = null;
/*     */   
/*  66 */   private ToolOptionsImpl toolOptions = null;
/*     */ 
/*     */   
/*  69 */   private VIT1 vit1 = null;
/*     */   
/*     */   public TestDriverImpl() {
/*  72 */     this.id = "Test Driver";
/*  73 */     this.type = "TEST_DRIVER";
/*  74 */     setToolOptions();
/*  75 */     this.ecuMan = new ECUDataHandler(this);
/*     */   }
/*     */   
/*     */   public String getToolId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public List getOptions() {
/*  83 */     return this.toolOptions.getOptions();
/*     */   }
/*     */   
/*     */   public void update(ObservableSubject savedSettings, Object o) {
/*  87 */     this.toolOptions.update(savedSettings, o);
/*     */   }
/*     */   
/*     */   public ToolOption getOptionByPropertyKey(String key) {
/*  91 */     return this.toolOptions.getOptionByPropertyKey(key);
/*     */   }
/*     */   
/*     */   public Object getVIN(DeviceCommunicationCallback dcb, Object requestData) throws Exception {
/*  95 */     if (this.vit1 == null) {
/*  96 */       getECUData((DeviceCommunicationCallback)null, (Object)null, (AttributeValueMap)null);
/*     */     }
/*  98 */     String vin = (String)this.vit1.getVIN();
/*  99 */     return new ValueAdapter((vin == null) ? "" : vin);
/*     */   }
/*     */   
/*     */   public static void setDataInformation(AttributeValueMapExt data) {
/* 103 */     TestDriverLog.getInstance().setDataInformation(data);
/*     */   }
/*     */   
/*     */   public static void handleProgrammingSequenceStep(Integer step) {
/* 107 */     programmingSequenceStep = step;
/*     */   }
/*     */   
/*     */   public boolean isAutomaticMode() {
/* 111 */     return (this.ecuMan == null) ? false : this.ecuMan.isAutomaticMode();
/*     */   }
/*     */   
/*     */   public String getVIT1FileName() {
/* 115 */     return (this.ecuMan == null) ? "" : this.ecuMan.getVIT1FileName();
/*     */   }
/*     */   
/*     */   public String getCurrDir() {
/* 119 */     return (this.ecuMan == null) ? "" : this.ecuMan.getCurrDir();
/*     */   }
/*     */   
/*     */   public String getVITID() {
/* 123 */     return (this.ecuMan == null) ? "---" : this.ecuMan.getVITID();
/*     */   }
/*     */   
/*     */   public void writeSummary(Vector summary) {
/*     */     try {
/* 128 */       TestDriverLog.getInstance().writeSummary(summary);
/* 129 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeVIT1() {
/*     */     try {
/* 135 */       if (this.ecuMan != null) {
/* 136 */         this.ecuMan.spoolOutVIT1();
/*     */       }
/* 138 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeExceptionLog(String msg) {
/*     */     try {
/* 144 */       TestDriverLog.getInstance().writeException(this.ecuMan.getLogFileName(), msg);
/* 145 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLog() {
/*     */     try {
/* 151 */       TestDriverLog.getInstance().writeReprogInfos(this.ecuMan.getLogFileName(), null);
/* 152 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isControllerVIT1Request(Object requestData) {
/*     */     try {
/* 158 */       return ((List)requestData).get(0) instanceof RequestMethodData;
/* 159 */     } catch (Exception x) {
/* 160 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterVIT1(VIT1 vit1, Object requestData) {
/*     */     try {
/* 166 */       Set<Integer> devices = new HashSet();
/* 167 */       List rmds = (List)requestData;
/* 168 */       for (int i = 0; i < rmds.size(); i++) {
/* 169 */         Object rmd = rmds.get(i);
/* 170 */         if (rmd instanceof RequestMethodData) {
/* 171 */           Integer deviceID = new Integer(((RequestMethodData)rmd).getDeviceID());
/* 172 */           log.debug("handle controller vit1 request (device=" + deviceID + ")");
/* 173 */           devices.add(deviceID);
/*     */         } 
/*     */       } 
/* 176 */       List<Integer> invalid = new ArrayList();
/* 177 */       List<VIT> cmbs = vit1.getControlModuleBlocks(); int j;
/* 178 */       for (j = 0; cmbs != null && j < cmbs.size(); j++) {
/* 179 */         VIT vit = cmbs.get(j);
/* 180 */         Integer ecu = new Integer(Integer.parseInt(vit.getAttrValue("ecu_adr"), 16));
/* 181 */         if (!devices.contains(ecu)) {
/* 182 */           invalid.add(ecu);
/*     */         }
/*     */       } 
/* 185 */       for (j = 0; j < invalid.size(); j++) {
/* 186 */         vit1.removeControlModuleBlock(invalid.get(j));
/*     */       }
/* 188 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getECUData(DeviceCommunicationCallback dcb, Object requestData, AttributeValueMap context) throws Exception {
/* 193 */     String vit1File = null;
/* 194 */     if (requestData != null && requestData instanceof String) {
/* 195 */       vit1File = (String)requestData;
/* 196 */     } else if (requestData != null && !isAutomaticMode() && isControllerVIT1Request(requestData)) {
/* 197 */       List lstAttrs = this.ecuMan.getControllerECUFromFS(vit1File);
/* 198 */       if (lstAttrs != null && !lstAttrs.isEmpty()) {
/* 199 */         VITBuilder builder = VITBuilderProvider.getBuilder("VIT1 Builder", lstAttrs, null);
/* 200 */         this.vit1 = (VIT1)builder.build();
/* 201 */         filterVIT1(this.vit1, requestData);
/* 202 */         TestDriverLog.getInstance().writeInfos(this.ecuMan.getLogFileName(), (VIT)this.vit1);
/*     */       }
/*     */     
/* 205 */     } else if (this.vit1 == null || vit1File != null) {
/* 206 */       List lstAttrs = this.ecuMan.getECUFromFS(vit1File);
/* 207 */       if (lstAttrs != null && !lstAttrs.isEmpty()) {
/* 208 */         VITBuilder builder = VITBuilderProvider.getBuilder("VIT1 Builder", lstAttrs, null);
/* 209 */         this.vit1 = (VIT1)builder.build();
/* 210 */         TestDriverLog.getInstance().writeInfos(this.ecuMan.getLogFileName(), (VIT)this.vit1);
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     return this.vit1;
/*     */   }
/*     */   
/*     */   public Object reprogram(MessageCallback mcb, Object programmingData, Object dataUnits, String securityCode, String securityCodeCtrl) throws Exception {
/* 218 */     Boolean response = new Boolean(false);
/* 219 */     if (this.vit1 != null) {
/* 220 */       ProgrammingData progData = (ProgrammingData)programmingData;
/* 221 */       TestDriverLog.getInstance().writeReprogInfos(this.ecuMan.getLogFileName(), progData.getCalibrationFiles());
/* 222 */       List lstAttrs = this.vit1.collectAttributes(progData.getDeviceID());
/* 223 */       if (!lstAttrs.isEmpty()) {
/* 224 */         VITBuilder builder = VITBuilderProvider.getBuilder("VIT2 Builder", lstAttrs, progData);
/* 225 */         VIT2 vit2 = (VIT2)builder.build();
/* 226 */         if (securityCode != null) {
/* 227 */           PairImpl pairImpl = new PairImpl("SecCodeVeh", securityCode);
/* 228 */           vit2.addAttribute((Pair)pairImpl);
/*     */         } 
/* 230 */         if (securityCodeCtrl != null) {
/* 231 */           PairImpl pairImpl = new PairImpl("SecCodeCtrl", securityCodeCtrl);
/* 232 */           vit2.addAttribute((Pair)pairImpl);
/*     */         } 
/* 234 */         this.ecuMan.handleProgrammingSequenceStep(programmingSequenceStep);
/* 235 */         programmingSequenceStep = null;
/* 236 */         response = this.ecuMan.reprogECU2FS(this.vit1, vit2, progData.getCalibrationFiles(), (List)dataUnits);
/*     */       } 
/*     */     } 
/* 239 */     if (ConfigurationUtil.getBoolean("simulate.programming.error", SPSClientUtil.getConfiguration()).booleanValue()) {
/* 240 */       return new ProgrammingResultImpl(false, "simulated test driver error");
/*     */     }
/* 242 */     return new ProgrammingResultImpl(response.booleanValue(), "Detailed message here in case of errors.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean testConnection(Object params) {
/* 248 */     return true;
/*     */   }
/*     */   
/*     */   public void init() {
/* 252 */     this.vit1 = null;
/*     */   }
/*     */   
/*     */   public void reset() {}
/*     */   
/*     */   private void setToolOptions() {
/*     */     InputOptionImpl inputOptionImpl;
/* 259 */     this.toolOptions = ToolOptionsImpl.getInstance(this.id);
/* 260 */     ToolUtils toolUtils = new ToolUtils();
/*     */ 
/*     */     
/* 263 */     if (!ExecutionMode.isLoadTest()) {
/* 264 */       inputOptionImpl = new InputOptionImpl(this.id, "VIT1 Path (Vehicle)", toolUtils.getElementList(this.id, vit1PathVehicle));
/* 265 */       this.toolOptions.addOption((ToolOption)inputOptionImpl);
/* 266 */       inputOptionImpl = new InputOptionImpl(this.id, "VIT1 Path (Controller)", toolUtils.getElementList(this.id, vit1Controller));
/* 267 */       this.toolOptions.addOption((ToolOption)inputOptionImpl);
/* 268 */       inputOptionImpl = new InputOptionImpl(this.id, "VIT1 Path (Automatic Test)", toolUtils.getElementList(this.id, vit1Path));
/*     */     } else {
/* 270 */       File dir = new File(System.getProperty("dir.vit"));
/* 271 */       inputOptionImpl = new InputOptionImpl(this.id, "VIT1 Path (Automatic Test)", toolUtils.getElementList(this.id, new String[] { dir.getAbsolutePath() }));
/*     */     } 
/* 273 */     this.toolOptions.addOption((ToolOption)inputOptionImpl);
/* 274 */     BooleanSelectOptionImpl booleanSelectOptionImpl = new BooleanSelectOptionImpl(this.id, "Read Flag", toolUtils.getElementList(this.id, booleans));
/* 275 */     this.toolOptions.addOption((ToolOption)booleanSelectOptionImpl);
/* 276 */     booleanSelectOptionImpl = new BooleanSelectOptionImpl(this.id, "Write Modules", toolUtils.getElementList(this.id, booleans));
/* 277 */     this.toolOptions.addOption((ToolOption)booleanSelectOptionImpl);
/* 278 */     booleanSelectOptionImpl = new BooleanSelectOptionImpl(this.id, "Rename VIT1 File", toolUtils.getElementList(this.id, booleans));
/* 279 */     this.toolOptions.addOption((ToolOption)booleanSelectOptionImpl);
/*     */ 
/*     */     
/* 282 */     this.toolOptions.adjustToolOptions(ClientAppContextProvider.getClientAppContext().getClientSettings().getProperties(toolUtils.trim(this.id)));
/* 283 */     ClientAppContextProvider.getClientAppContext().registerSettingsObserver((SettingsObserver)this.toolOptions);
/*     */   }
/*     */   
/*     */   public Map getVIT1History() {
/* 287 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void resetVIT1History() {
/* 291 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\TestDriverImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */