/*     */ package com.eoos.gm.tis2web.kdr.client;
/*     */ 
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SmartStartVersionVista
/*     */   extends SmartStartVersion
/*     */ {
/*     */   private static final String VERSION_FILE = "smartstart.version";
/*     */   private static final String SMARTSTART_VERSION_TAG = "<VersionNumber>";
/*     */   private static final String SMARTSTART_VERSION_END_TAG = "</VersionNumber>";
/*     */   private static final String SMARTSTART_VERSION_TEMPLATE = "<Version>\n\t<VersionNumber>V_E_R_S_I_O_N</VersionNumber>\n</Version>";
/*  19 */   private static final Logger log = Logger.getLogger(SmartStartVersionVista.class);
/*     */   
/*     */   public String getInstalledSmartStartVersion() {
/*  22 */     File sDir = new File(getGDSHomeDir(), "smartstart");
/*  23 */     return getInstalledSmartStartVersion(sDir);
/*     */   }
/*     */   
/*     */   public void setInstalledSmartStartVersion(String version) throws Exception {
/*  27 */     File sDir = new File(getGDSHomeDir(), "smartstart");
/*  28 */     setInstalledSmartStartVersion(sDir, version);
/*     */   }
/*     */   
/*     */   private String getInstalledSmartStartVersion(File sDir) {
/*  32 */     String ret = null;
/*  33 */     log.info("Determining installed SmartStart version...");
/*  34 */     if (sDir.isDirectory()) {
/*  35 */       File sVersion = new File(sDir, "smartstart.version");
/*  36 */       if (sVersion.isFile() && sVersion.canRead()) {
/*  37 */         String versionFileAsStr = readVersion(sVersion);
/*  38 */         ret = getVersionString(versionFileAsStr);
/*     */       } 
/*     */     } 
/*  41 */     log.info("Installed SmartStart version: " + ret);
/*  42 */     return ret;
/*     */   }
/*     */   
/*     */   private String readVersion(File versionFile) {
/*  46 */     String result = null;
/*  47 */     FileReader rdr = null;
/*     */     try {
/*  49 */       rdr = new FileReader(versionFile);
/*  50 */       char[] buffer = new char[1024];
/*  51 */       int cnt = rdr.read(buffer);
/*  52 */       log.debug("Read " + cnt + "characters from SmartStart version file.");
/*  53 */       if (cnt >= 0) {
/*  54 */         result = (new String(buffer)).trim();
/*     */       }
/*  56 */     } catch (Exception e) {
/*  57 */       log.error("Error when reading smartstart version file: " + e, e);
/*     */     } finally {
/*     */       try {
/*  60 */         rdr.close();
/*  61 */       } catch (Exception e) {}
/*     */     } 
/*     */     
/*  64 */     return result;
/*     */   }
/*     */   
/*     */   private void writeVersion(File vFile, String content) throws Exception {
/*  68 */     FileWriter wrtr = new FileWriter(vFile);
/*  69 */     wrtr.write(content);
/*  70 */     wrtr.flush();
/*  71 */     wrtr.close();
/*     */   }
/*     */   
/*     */   private String getVersionString(String versionFileAsStr) {
/*  75 */     String result = null;
/*     */     try {
/*  77 */       int vBegin = versionFileAsStr.indexOf("<VersionNumber>") + "<VersionNumber>".length();
/*  78 */       int vEnd = versionFileAsStr.indexOf("</VersionNumber>");
/*  79 */       result = versionFileAsStr.substring(vBegin, vEnd).trim();
/*  80 */     } catch (Exception e) {
/*  81 */       log.debug("Cannot extract version string: " + e, e);
/*     */     } 
/*  83 */     return result;
/*     */   }
/*     */   
/*     */   private void setInstalledSmartStartVersion(File sDir, String version) throws Exception {
/*  87 */     File sVersion = new File(sDir, "smartstart.version");
/*  88 */     String versionFileAsStr = null;
/*  89 */     if (sVersion.exists()) {
/*  90 */       versionFileAsStr = readVersion(sVersion);
/*  91 */       String currentVersion = getVersionString(versionFileAsStr);
/*  92 */       if (currentVersion != null && version.compareTo(currentVersion) == 0) {
/*  93 */         log.info("Version already in version file: " + version);
/*     */       } else {
/*  95 */         versionFileAsStr = "<Version>\n\t<VersionNumber>V_E_R_S_I_O_N</VersionNumber>\n</Version>".replaceFirst("V_E_R_S_I_O_N", version);
/*  96 */         writeVersion(sVersion, versionFileAsStr);
/*  97 */         log.info("Updated version [" + currentVersion + "] in version file to: " + version);
/*     */       } 
/*     */     } else {
/* 100 */       versionFileAsStr = "<Version>\n\t<VersionNumber>V_E_R_S_I_O_N</VersionNumber>\n</Version>".replaceFirst("V_E_R_S_I_O_N", version);
/* 101 */       writeVersion(sVersion, versionFileAsStr);
/* 102 */       log.info("Created new version file containing version: " + version);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"DMI_HARDCODED_ABSOLUTE_FILENAME"})
/*     */   public static void main(String[] args) {
/* 108 */     File sDir = new File("/home/mh/cdl-import-data/kdr-smartstart_1.4.0803-VISTA");
/* 109 */     SmartStartVersionVista vVista = new SmartStartVersionVista();
/* 110 */     if (args.length > 0) {
/*     */       try {
/* 112 */         (new SmartStartVersionVista()).setInstalledSmartStartVersion(sDir, args[0]);
/* 113 */         System.out.println("Version after update: " + vVista.getInstalledSmartStartVersion(sDir));
/* 114 */       } catch (Exception e) {
/* 115 */         System.out.println("Exception when writing SmartStart version: " + e.toString());
/* 116 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/* 119 */       System.out.println(vVista.getInstalledSmartStartVersion(sDir));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\SmartStartVersionVista.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */