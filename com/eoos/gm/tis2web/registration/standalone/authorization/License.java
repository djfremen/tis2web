/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.common.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.AuthorizationStatus;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.gm.tis2web.util.FileStreamFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class License
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(License.class);
/*     */   
/*     */   private File license;
/*     */   private SoftwareKey swk;
/*     */   private String hwid;
/*  25 */   private long ts = Long.MAX_VALUE;
/*     */   private AuthorizationStatus status;
/*     */   
/*     */   public SoftwareKey getSoftwareKey() {
/*  29 */     return this.swk;
/*     */   }
/*     */   
/*     */   public void setSoftwareKey(SoftwareKey swk) {
/*  33 */     this.swk = swk;
/*     */   }
/*     */   
/*     */   public String getHardwareID() {
/*  37 */     return this.hwid;
/*     */   }
/*     */   
/*     */   public void setHardwareID(String hwid) {
/*  41 */     this.hwid = hwid;
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/*  45 */     return this.ts;
/*     */   }
/*     */   
/*     */   public void setTimeStamp(long ts) {
/*  49 */     this.ts = ts;
/*     */   }
/*     */   
/*     */   public AuthorizationStatus getStatus() {
/*  53 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(AuthorizationStatus status) {
/*  57 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setLicenseFile(File file) {
/*  61 */     this.license = file;
/*     */   }
/*     */   
/*     */   private License(File license) {
/*  65 */     this.license = license;
/*     */   }
/*     */   
/*     */   private void load() throws Exception {
/*  69 */     InputStream is = FileStreamFactory.getInstance().getInputStream(this.license);
/*  70 */     InputStreamReader ir = new InputStreamReader(is, Charset.forName("utf-8"));
/*  71 */     BufferedReader br = new BufferedReader(ir);
/*  72 */     String sid = null;
/*     */     String line;
/*  74 */     while ((line = br.readLine()) != null) {
/*  75 */       if (sid == null) {
/*  76 */         sid = line.trim(); continue;
/*  77 */       }  if (this.swk == null) {
/*  78 */         this.swk = new SoftwareKey(sid, line.trim()); continue;
/*  79 */       }  if (this.hwid == null) {
/*  80 */         this.hwid = line.trim();
/*  81 */         this.swk.setHardwareID(this.hwid); continue;
/*  82 */       }  if (this.ts == Long.MAX_VALUE) {
/*  83 */         this.ts = Long.parseLong(line.trim());
/*  84 */         this.swk.setTimestamp(this.ts); continue;
/*     */       } 
/*  86 */       this.status = AuthorizationStatus.get(line.trim());
/*     */     } 
/*     */     
/*  89 */     br.close();
/*     */   }
/*     */   
/*     */   public void store() {
/*     */     try {
/*  94 */       OutputStream os = FileStreamFactory.getInstance().getOutputStream(this.license, FileStreamFactory.ENCRYPED);
/*  95 */       OutputStreamWriter fw = new OutputStreamWriter(os, Charset.forName("utf-8"));
/*  96 */       fw.write(this.swk.getSubscriberID());
/*  97 */       fw.write("\r\n");
/*  98 */       fw.write(this.swk.getLicenseKey());
/*  99 */       fw.write("\r\n");
/* 100 */       fw.write(this.hwid);
/* 101 */       fw.write("\r\n");
/* 102 */       fw.write(Long.toString(this.ts));
/* 103 */       fw.write("\r\n");
/* 104 */       fw.write(this.status.toString());
/* 105 */       fw.flush();
/* 106 */       fw.close();
/* 107 */     } catch (Exception e) {
/* 108 */       log.error("failed to write license file", e);
/* 109 */       throw new InvalidLicenseException();
/*     */     } 
/*     */     
/*     */     try {
/* 113 */       File hardwareInfo = new File(this.license.getParentFile().getAbsolutePath() + "\\hwi.dat");
/* 114 */       OutputStream os = FileStreamFactory.getInstance().getOutputStream(hardwareInfo, FileStreamFactory.ENCRYPED);
/* 115 */       OutputStreamWriter fw = new OutputStreamWriter(os, Charset.forName("utf-8"));
/* 116 */       fw.write(this.swk.getHardwareInfo());
/* 117 */       fw.flush();
/* 118 */       fw.close();
/* 119 */     } catch (Exception e) {
/* 120 */       log.error("failed to write hwi.dat file", e);
/* 121 */       throw new InvalidLicenseException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static License load(File file) {
/*     */     try {
/* 127 */       License license = new License(file);
/* 128 */       license.load();
/* 129 */       if (license.getStatus() == null) {
/* 130 */         license = null;
/* 131 */         log.debug("invalid license file");
/*     */       } 
/* 133 */       return license;
/* 134 */     } catch (Exception e) {
/* 135 */       log.debug("failed to load license file", e);
/* 136 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static License create(String file, SoftwareKey swk) {
/*     */     try {
/* 142 */       License license = new License(new File(file));
/* 143 */       license.setSoftwareKey(swk);
/* 144 */       license.setHardwareID(swk.getHardwareID());
/* 145 */       license.setTimeStamp(swk.getTimestamp());
/* 146 */       license.setStatus(swk.getAuthorizationStatus());
/* 147 */       return license;
/* 148 */     } catch (Exception e) {
/* 149 */       log.debug("failed to create license file", e);
/* 150 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\License.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */