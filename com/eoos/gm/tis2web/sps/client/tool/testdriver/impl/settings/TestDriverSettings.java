/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import java.io.File;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestDriverSettings
/*     */   implements ITestDriverSettings
/*     */ {
/*     */   public static final short NO_JOB = 0;
/*     */   public static final short GET_ECU = 1;
/*     */   public static final short REPROGRAM_ECU = 2;
/*  27 */   private int statusLineNo = 0;
/*     */   private boolean isLastVIT1 = false;
/*  29 */   private String vit1FileName = null;
/*  30 */   private String vit2FileName = null;
/*  31 */   private String logFileName = null;
/*  32 */   private String currDir = null;
/*  33 */   private String outDir = null;
/*  34 */   private String vitID = "";
/*  35 */   private String status = "";
/*  36 */   private String writeModules = "";
/*  37 */   private String readHandling = "";
/*  38 */   private String renameVIT1 = "";
/*  39 */   private int modulesNo = 0;
/*  40 */   private ToolOptions options = null;
/*     */   private boolean readFromDir = false;
/*  42 */   private Map blobID2size = new Hashtable<Object, Object>();
/*     */ 
/*     */   
/*     */   public TestDriverSettings(ToolOptions options) {
/*  46 */     this.options = options;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  50 */     this.statusLineNo = 1;
/*  51 */     this.isLastVIT1 = false;
/*  52 */     this.vit1FileName = null;
/*  53 */     this.vit2FileName = null;
/*  54 */     this.logFileName = null;
/*  55 */     this.outDir = null;
/*  56 */     this.currDir = null;
/*  57 */     this.vitID = "";
/*  58 */     this.status = "";
/*  59 */     this.writeModules = "";
/*  60 */     this.readHandling = "";
/*  61 */     this.renameVIT1 = "";
/*  62 */     this.modulesNo = 0;
/*  63 */     this.readFromDir = false;
/*  64 */     this.blobID2size = new Hashtable<Object, Object>();
/*     */   }
/*     */   
/*     */   public int getStatusLineNo() {
/*  68 */     return this.statusLineNo;
/*     */   }
/*     */   
/*     */   public void setStatusLineNo(int statusLineNo) {
/*  72 */     this.statusLineNo = statusLineNo;
/*     */   }
/*     */   
/*     */   public boolean IsLastVIT1() {
/*  76 */     return this.isLastVIT1;
/*     */   }
/*     */   
/*     */   public void setIsLastVIT1(boolean isLastVIT1) {
/*  80 */     this.isLastVIT1 = isLastVIT1;
/*     */   }
/*     */   
/*     */   public String getVIT1FileName() {
/*  84 */     return this.vit1FileName;
/*     */   }
/*     */   
/*     */   public void setVIT1FileName(String vit1FileName) {
/*  88 */     this.vit1FileName = vit1FileName;
/*     */   }
/*     */   
/*     */   public void setCurrDir(String currDir) {
/*  92 */     this.currDir = currDir;
/*     */   }
/*     */   
/*     */   public String getCurrDir() {
/*  96 */     return this.currDir;
/*     */   }
/*     */   
/*     */   public String getOutDir() {
/* 100 */     if (this.outDir == null) {
/* 101 */       int dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".VIT1");
/* 102 */       if (dotIndx == -1) {
/* 103 */         dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".TXT");
/*     */       }
/* 105 */       if (dotIndx == -1) {
/* 106 */         this.outDir = this.currDir + File.separator + this.vit1FileName;
/*     */       } else {
/* 108 */         this.outDir = this.currDir + File.separator + this.vit1FileName.substring(0, dotIndx);
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return this.outDir;
/*     */   }
/*     */   
/*     */   public String getVITID() {
/* 116 */     return this.vitID;
/*     */   }
/*     */   
/*     */   public void setVITID(String vitID) {
/* 120 */     this.vitID = vitID;
/*     */   }
/*     */   
/*     */   public String getStatus() {
/* 124 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(String status) {
/* 128 */     this.status = status;
/*     */   }
/*     */   
/*     */   public String getWriteModules() {
/* 132 */     return this.writeModules;
/*     */   }
/*     */   
/*     */   public void setWriteModules(String writeModules) {
/* 136 */     this.writeModules = writeModules;
/*     */   }
/*     */   
/*     */   public String getReadHandling() {
/* 140 */     return this.readHandling;
/*     */   }
/*     */   
/*     */   public void setReadHandling(String readHandling) {
/* 144 */     this.readHandling = readHandling;
/*     */   }
/*     */   
/*     */   public String getRenameVIT1() {
/* 148 */     return this.renameVIT1;
/*     */   }
/*     */   
/*     */   public void setRenameVIT1(String renameVIT1) {
/* 152 */     this.renameVIT1 = renameVIT1;
/*     */   }
/*     */   
/*     */   public int getModulesNo() {
/* 156 */     return this.modulesNo;
/*     */   }
/*     */   
/*     */   public void setModulesNo(int modulesNo) {
/* 160 */     this.modulesNo = modulesNo;
/*     */   }
/*     */   
/*     */   public String getVIT2FileName() {
/* 164 */     if (this.vit2FileName == null) {
/* 165 */       int dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".VIT1");
/* 166 */       if (dotIndx == -1) {
/* 167 */         dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".TXT");
/*     */       }
/* 169 */       if (dotIndx == -1) {
/* 170 */         this.vit2FileName = getOutDir() + File.separator + this.vit1FileName + ".vit2";
/*     */       } else {
/* 172 */         this.vit2FileName = getOutDir() + File.separator + this.vit1FileName.substring(0, dotIndx) + ".vit2";
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return this.vit2FileName;
/*     */   }
/*     */   
/*     */   public String getLogFileName() {
/* 180 */     if (this.logFileName == null) {
/* 181 */       int dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".VIT1");
/* 182 */       if (dotIndx == -1) {
/* 183 */         dotIndx = this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".TXT");
/*     */       }
/* 185 */       if (dotIndx == -1) {
/* 186 */         this.logFileName = getOutDir() + File.separator + this.vit1FileName + ".txt";
/*     */       } else {
/* 188 */         this.logFileName = getOutDir() + File.separator + this.vit1FileName.substring(0, dotIndx) + ".txt";
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return this.logFileName;
/*     */   }
/*     */   
/*     */   public boolean isTXT() {
/* 196 */     if (this.vit1FileName.toUpperCase(Locale.ENGLISH).lastIndexOf(".TXT") == -1) {
/* 197 */       return false;
/*     */     }
/*     */     
/* 200 */     return true;
/*     */   }
/*     */   
/*     */   public String getSetting4Key(String key) {
/* 204 */     ToolUtils util = new ToolUtils();
/* 205 */     ToolOption opt = this.options.getOptionByPropertyKey(util.trim(this.options.getId()) + ".option." + util.trim(key));
/* 206 */     OptionValue optVal = opt.getValues().get(0);
/* 207 */     return optVal.getKey();
/*     */   }
/*     */   
/*     */   public boolean getBoolSetting4Key(String key) {
/* 211 */     ToolUtils util = new ToolUtils();
/* 212 */     ToolOption opt = this.options.getOptionByPropertyKey(util.trim(this.options.getId()) + ".option." + util.trim(key));
/* 213 */     OptionValue optVal = opt.getValues().get(0);
/* 214 */     return optVal.getKey().trim().toLowerCase(Locale.ENGLISH).equals("true");
/*     */   }
/*     */   
/*     */   public void setReadFromDir() {
/* 218 */     this.readFromDir = true;
/*     */   }
/*     */   
/*     */   public boolean readFromDir() {
/* 222 */     return this.readFromDir;
/*     */   }
/*     */   
/*     */   public boolean canWriteReadStatus(short job) {
/* 226 */     switch (job) {
/*     */       case 1:
/* 228 */         if (this.readFromDir || (getReadHandling().compareToIgnoreCase("After Download") != 0 && getBoolSetting4Key("Read Flag"))) {
/* 229 */           return true;
/*     */         }
/*     */         break;
/*     */       case 2:
/* 233 */         if (!this.readFromDir && getReadHandling().compareToIgnoreCase("After Download") == 0 && getBoolSetting4Key("Read Flag")) {
/* 234 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 239 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canRenameVIT1File() {
/* 243 */     if (this.isLastVIT1 && (this.renameVIT1.compareToIgnoreCase("Yes") == 0 || (this.renameVIT1.compareToIgnoreCase("No") != 0 && getBoolSetting4Key("Rename VIT1 File")))) {
/* 244 */       return !this.readFromDir;
/*     */     }
/* 246 */     return false;
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
/*     */   public Map getBlobID2Size() {
/* 259 */     return this.blobID2size;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\settings\TestDriverSettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */