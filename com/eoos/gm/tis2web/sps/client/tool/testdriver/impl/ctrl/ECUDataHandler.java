/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT1DataHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2DataHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2.SimpleFormat;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.IOUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoTestDriverFileFoundException;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoValidVIT1Exception;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.VIT1Reader;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.VIT1Writer;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.VIT2Writer;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.TestDriverSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ReprogramProgressDisplay;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECUDataHandler
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(ECUDataHandler.class);
/*  42 */   private TestDriverSettings settings = null;
/*  43 */   private List vit1Lines = null;
/*  44 */   private List vit1Attrs = null;
/*  45 */   private Integer programmingSequenceStep = null;
/*     */ 
/*     */   
/*     */   public ECUDataHandler(ToolOptions options) {
/*  49 */     this.settings = new TestDriverSettings(options);
/*     */   }
/*     */   
/*     */   public String getLogFileName() {
/*  53 */     return this.settings.getLogFileName();
/*     */   }
/*     */   
/*     */   public boolean isAutomaticMode() {
/*     */     try {
/*  58 */       String fileName = (this.settings != null) ? this.settings.getSetting4Key("VIT1 Path (Automatic Test)") : null;
/*  59 */       if (fileName != null && fileName.trim().length() > 0) {
/*  60 */         File file = new File(fileName);
/*  61 */         return file.isDirectory();
/*     */       } 
/*  63 */       return false;
/*     */     }
/*  65 */     catch (Exception x) {
/*  66 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVIT1FileName() {
/*  71 */     return (this.settings != null) ? this.settings.getVIT1FileName() : null;
/*     */   }
/*     */   
/*     */   public String getCurrDir() {
/*  75 */     return (this.settings != null) ? this.settings.getCurrDir() : null;
/*     */   }
/*     */   
/*     */   public String getVITID() {
/*  79 */     return IOUtil.getLastVIT1File().toString() + ":" + this.settings.getVITID();
/*     */   }
/*     */   
/*     */   public List getECUFromFS(String vit1File) throws Exception {
/*  83 */     this.vit1Attrs = null;
/*  84 */     String vit1key = isAutomaticMode() ? "VIT1 Path (Automatic Test)" : "VIT1 Path (Vehicle)";
/*  85 */     String fileName = (vit1File == null) ? this.settings.getSetting4Key(vit1key) : vit1File;
/*     */     try {
/*  87 */       this.settings.reset();
/*  88 */       File file = new File(fileName);
/*  89 */       if (file.isDirectory()) {
/*  90 */         this.settings.setReadFromDir();
/*  91 */         if (IOUtil.getDirectory() == null) {
/*  92 */           file = IOUtil.getNextVIT1File(file);
/*     */         } else {
/*  94 */           file = IOUtil.getLastVIT1File();
/*     */         } 
/*     */       } 
/*  97 */       this.settings.setVIT1FileName(file.getName());
/*  98 */       this.settings.setCurrDir(file.getParent());
/*     */       
/* 100 */       log.debug("Get VIT1 attributes from file: " + fileName);
/* 101 */       VIT1Reader reader = new VIT1Reader(new FileReader(file));
/* 102 */       this.vit1Lines = reader.readVIT1((ITestDriverSettings)this.settings);
/* 103 */       reader.close();
/*     */       
/* 105 */       IOUtil.CreateDir(this.settings.getOutDir());
/*     */       
/* 107 */       log.debug("Extract VIT1 attributes");
/* 108 */       VIT1DataHandler vit1DataHandler = null;
/* 109 */       if (this.settings.isTXT()) {
/* 110 */         vit1DataHandler = new TxtVIT1DataHandlerImpl();
/*     */       } else {
/* 112 */         vit1DataHandler = new VIT1DataHandlerImpl();
/*     */       } 
/* 114 */       this.vit1Attrs = vit1DataHandler.extractVIT1Data(this.vit1Lines, (ITestDriverSettings)this.settings);
/* 115 */       if (!this.vit1Attrs.isEmpty() && this.settings.canWriteReadStatus((short)1)) {
/* 116 */         log.debug("Write \"read\" status to file: " + fileName);
/* 117 */         writeStatusAndRenFile(file, "\"read\"");
/*     */       } 
/* 119 */       if (this.vit1Lines.isEmpty()) {
/* 120 */         log.error("No valid attributes in VIT1 file: " + file.getPath());
/* 121 */         throw new NoValidVIT1Exception("No valid attributes in VIT1 file: " + file.getPath());
/*     */       } 
/* 123 */     } catch (Exception e) {
/* 124 */       if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoMoreFilesException)
/* 125 */         throw e; 
/* 126 */       if (e instanceof java.io.FileNotFoundException)
/* 127 */         throw new NoTestDriverFileFoundException(); 
/* 128 */       if (!this.settings.readFromDir()) {
/* 129 */         log.error("Error while trying to get ECU from file: " + fileName, e);
/*     */       } else {
/* 131 */         IOUtil.getNextVIT1File(null);
/* 132 */         return getECUFromFS(null);
/*     */       } 
/* 134 */       throw e;
/*     */     } 
/*     */     
/* 137 */     return this.vit1Attrs;
/*     */   }
/*     */   
/*     */   private String getSetting4Key(String vit1key) {
/*     */     try {
/* 142 */       String filename = this.settings.getSetting4Key(vit1key);
/* 143 */       if (filename != null && filename.trim().length() > 0) {
/* 144 */         return filename;
/*     */       }
/* 146 */     } catch (Exception x) {
/* 147 */       log.error("failed to access vit1 path property");
/*     */     } 
/* 149 */     return this.settings.getSetting4Key("VIT1 Path (Vehicle)");
/*     */   }
/*     */   
/*     */   public List getControllerECUFromFS(String vit1File) throws Exception {
/* 153 */     this.vit1Attrs = null;
/* 154 */     String vit1key = isAutomaticMode() ? "VIT1 Path (Automatic Test)" : "VIT1 Path (Controller)";
/* 155 */     String fileName = (vit1File == null) ? getSetting4Key(vit1key) : vit1File;
/*     */     try {
/* 157 */       this.settings.reset();
/* 158 */       File file = new File(fileName);
/* 159 */       if (file.isDirectory()) {
/* 160 */         this.settings.setReadFromDir();
/* 161 */         if (IOUtil.getDirectory() == null) {
/* 162 */           file = IOUtil.getNextVIT1File(file);
/*     */         } else {
/* 164 */           file = IOUtil.getLastVIT1File();
/*     */         } 
/*     */       } 
/* 167 */       this.settings.setVIT1FileName(file.getName());
/* 168 */       this.settings.setCurrDir(file.getParent());
/*     */       
/* 170 */       log.debug("Get VIT1 attributes from file: " + fileName);
/* 171 */       VIT1Reader reader = new VIT1Reader(new FileReader(file));
/* 172 */       this.vit1Lines = reader.readVIT1((ITestDriverSettings)this.settings);
/* 173 */       reader.close();
/*     */       
/* 175 */       IOUtil.CreateDir(this.settings.getOutDir());
/*     */       
/* 177 */       log.debug("Extract VIT1 attributes");
/* 178 */       VIT1DataHandler vit1DataHandler = null;
/* 179 */       if (this.settings.isTXT()) {
/* 180 */         vit1DataHandler = new TxtVIT1DataHandlerImpl();
/*     */       } else {
/* 182 */         vit1DataHandler = new VIT1DataHandlerImpl();
/*     */       } 
/* 184 */       this.vit1Attrs = vit1DataHandler.extractVIT1Data(this.vit1Lines, (ITestDriverSettings)this.settings);
/* 185 */       if (!this.vit1Attrs.isEmpty() && this.settings.canWriteReadStatus((short)1)) {
/* 186 */         log.debug("Write \"read\" status to file: " + fileName);
/* 187 */         writeStatusAndRenFile(file, "\"read\"");
/*     */       } 
/* 189 */       if (this.vit1Lines.isEmpty()) {
/* 190 */         log.error("No valid attributes in VIT1 file: " + file.getPath());
/* 191 */         throw new NoValidVIT1Exception("No valid attributes in VIT1 file: " + file.getPath());
/*     */       } 
/* 193 */     } catch (Exception e) {
/* 194 */       if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoMoreFilesException)
/* 195 */         throw e; 
/* 196 */       if (e instanceof java.io.FileNotFoundException)
/* 197 */         throw new NoTestDriverFileFoundException(); 
/* 198 */       if (!this.settings.readFromDir()) {
/* 199 */         log.error("Error while trying to get ECU from file: " + fileName, e);
/*     */       } else {
/* 201 */         IOUtil.getNextVIT1File(null);
/* 202 */         return getECUFromFS(null);
/*     */       } 
/* 204 */       throw e;
/*     */     } 
/*     */     
/* 207 */     return this.vit1Attrs;
/*     */   }
/*     */   
/*     */   public void handleProgrammingSequenceStep(Integer programmingSequenceStep) {
/* 211 */     this.programmingSequenceStep = programmingSequenceStep;
/*     */   }
/*     */   
/*     */   public Boolean reprogECU2FS(VIT1 vit1, VIT2 vit2, List<ProgrammingDataUnit> blobsInfo, List blobs) {
/*     */     try {
/* 216 */       long total = 0L;
/* 217 */       for (int i = 0; i < blobs.size(); i++) {
/* 218 */         total += ((ProgrammingDataUnit)blobsInfo.get(i)).getBlobSize().longValue();
/*     */       }
/* 220 */       if (isAutomaticMode()) {
/* 221 */         spoolOutBlobs(null, blobsInfo, blobs);
/*     */       } else {
/*     */         
/* 224 */         ReprogramProgressDisplay progress = new ReprogramProgressDisplay(total);
/* 225 */         spoolOutBlobs(progress, blobsInfo, blobs);
/* 226 */         progress.done();
/*     */       } 
/*     */       
/* 229 */       String fileName = this.settings.getCurrDir() + File.separator + this.settings.getVIT1FileName();
/* 230 */       if (this.settings.canWriteReadStatus((short)2)) {
/* 231 */         log.debug("Write \"read\" status to file: " + fileName);
/* 232 */         writeStatusAndRenFile(new File(fileName), "\"read\"");
/*     */       } 
/*     */       
/* 235 */       spoolOutVIT1();
/* 236 */       log.debug("Format VIT2 for writing process.");
/* 237 */       VIT2DataHandler vit2DataHandler = null;
/* 238 */       if (this.settings.isTXT()) {
/* 239 */         vit2DataHandler = new TxtVIT2DataHandlerImpl();
/*     */       } else {
/* 241 */         vit2DataHandler = new VIT2DataHandlerImpl();
/*     */       } 
/* 243 */       List attrLines = vit2DataHandler.getExVIT2Attrs(vit1, vit2, (ITestDriverSettings)this.settings, blobsInfo);
/* 244 */       String vit2name = this.settings.getVIT2FileName();
/* 245 */       if (this.programmingSequenceStep != null) {
/* 246 */         vit2name = vit2name.substring(0, vit2name.lastIndexOf(".")) + "-" + this.programmingSequenceStep + ".vit2";
/* 247 */         this.programmingSequenceStep = null;
/*     */       } 
/* 249 */       log.debug("Spool out VIT2 to file: " + vit2name);
/* 250 */       VIT2Writer writer = new VIT2Writer(new FileWriter(vit2name, true));
/* 251 */       writer.write(attrLines);
/* 252 */       writer.close();
/* 253 */     } catch (Exception e) {
/* 254 */       log.error("Error while trying to write ECU data to file system", e);
/* 255 */       return new Boolean(false);
/*     */     } 
/* 257 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   private void writeStatus2File(File file, String status) throws Exception {
/* 261 */     File tmpFile = new File(file.getPath() + "_TMP_");
/* 262 */     VIT1Writer writer = new VIT1Writer(new FileWriter(tmpFile));
/* 263 */     VIT1Reader reader = new VIT1Reader(new FileReader(file));
/*     */     try {
/* 265 */       writer.write(reader, this.settings.getStatusLineNo(), status);
/* 266 */     } catch (Exception e) {
/* 267 */       throw e;
/*     */     } finally {
/* 269 */       reader.close();
/* 270 */       writer.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renameVIT1File(File file) throws Exception {
/* 275 */     if (this.settings.canRenameVIT1File()) {
/* 276 */       File renFile = new File(this.settings.getCurrDir() + File.separator + "_" + this.settings.getVIT1FileName() + "_");
/* 277 */       log.debug("Rename file: " + file.getPath() + " to file: " + renFile.getPath());
/* 278 */       if (renFile.exists())
/* 279 */         renFile.delete(); 
/* 280 */       if (!file.renameTo(renFile)) {
/* 281 */         throw new Exception("Can't rename file: " + file.getPath() + " to file: " + renFile.getPath());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeStatusAndRenFile(File file, String status) throws Exception {
/* 287 */     writeStatus2File(file, "\"read\"");
/* 288 */     File tmpFile = new File(file.getPath() + "_TMP_");
/* 289 */     if (file.delete() && tmpFile.renameTo(file)) {
/* 290 */       renameVIT1File(file);
/*     */     }
/*     */   }
/*     */   
/*     */   public void spoolOutVIT1() throws Exception {
/* 295 */     String fileName = this.settings.getOutDir() + File.separator + this.settings.getVIT1FileName();
/* 296 */     log.debug("Spool out VIT1 to file: " + fileName);
/* 297 */     VIT1Writer writer = new VIT1Writer(new FileWriter(fileName, true));
/*     */     try {
/* 299 */       writer.write(this.vit1Lines, this.settings.getStatusLineNo(), "\"read\"");
/* 300 */     } catch (Exception e) {
/* 301 */       throw e;
/*     */     } finally {
/* 303 */       writer.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spoolOutBlobs(ReprogramProgressDisplay progress, List<ProgrammingDataUnit> blobsInfo, List<byte[]> blobs) throws Exception {
/* 308 */     this.settings.getBlobID2Size().clear();
/*     */     try {
/* 310 */       String dir = this.settings.getOutDir();
/* 311 */       if (!Util.isNullOrEmpty(this.settings.getVITID())) {
/* 312 */         dir = dir + File.separator + this.settings.getVITID();
/* 313 */         File directory = new File(dir);
/* 314 */         if (directory.exists()) {
/* 315 */           File[] files = directory.listFiles();
/* 316 */           for (int i = 0; i < files.length; i++) {
/* 317 */             File file = files[i];
/* 318 */             if (!file.getName().endsWith(".txt") && !file.getName().endsWith(".log") && !file.getName().endsWith(".vit1") && !file.getName().endsWith(".vit2")) {
/* 319 */               file.delete();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 324 */       if (!blobs.isEmpty() && (this.settings.getWriteModules().compareToIgnoreCase("Yes") == 0 || (this.settings.getWriteModules() == "" && this.settings.getBoolSetting4Key("Write Modules"))) && IOUtil.CreateDir(dir)) {
/* 325 */         log.debug("Created output directory: " + dir);
/* 326 */         log.debug("Spool out modules (blobs) to directory: " + dir);
/* 327 */         long actual = 0L;
/* 328 */         for (int i = 0; i < blobs.size(); i++) {
/* 329 */           ProgrammingDataUnit dataUnit = blobsInfo.get(i);
/* 330 */           String fileName = dir + File.separator + (new SimpleFormat(2)).format(Integer.toHexString(i).toUpperCase(Locale.ENGLISH)) + "_" + dataUnit.getBlobName();
/* 331 */           FileOutputStream fos = new FileOutputStream(fileName);
/* 332 */           byte[] buff = blobs.get(i);
/*     */           try {
/* 334 */             buff = ZipUtil.gunzip(buff);
/* 335 */           } catch (Exception e) {}
/*     */           
/* 337 */           if (!this.settings.getBlobID2Size().containsKey(dataUnit.getBlobID())) {
/* 338 */             this.settings.getBlobID2Size().put(dataUnit.getBlobID(), Integer.valueOf(buff.length));
/*     */           }
/* 340 */           fos.write(buff);
/* 341 */           fos.close();
/*     */           try {
/* 343 */             Thread.sleep(1000L);
/* 344 */           } catch (InterruptedException e) {
/* 345 */             e.printStackTrace();
/*     */           } 
/* 347 */           actual += dataUnit.getBlobSize().intValue();
/* 348 */           log.debug("reprogram progress: " + actual);
/* 349 */           if (progress != null) {
/* 350 */             progress.progress(actual);
/*     */           }
/*     */         } 
/* 353 */       } else if (progress != null) {
/* 354 */         long actual = 0L;
/* 355 */         for (int i = 0; i < blobs.size(); i++) {
/* 356 */           ProgrammingDataUnit dataUnit = blobsInfo.get(i);
/* 357 */           actual += dataUnit.getBlobSize().intValue();
/* 358 */           progress.progress(actual);
/*     */           try {
/* 360 */             Thread.sleep(1000L);
/* 361 */           } catch (InterruptedException e) {
/* 362 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/* 366 */     } catch (Exception e) {
/* 367 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\ECUDataHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */