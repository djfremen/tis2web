/*     */ package com.eoos.gm.tis2web.sps.client.tool.util;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.OptionValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder.VITBuilderProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolUtils
/*     */ {
/*     */   private static final String SPS_DEBUG_DIR = "spsDebug";
/*     */   private static final String TEST_DTC_FILENAME = "TEST-DTC";
/*  31 */   private static Logger log = Logger.getLogger(ToolUtils.class);
/*     */   
/*     */   public String trim(String str) {
/*  34 */     StringBuffer strBuf = new StringBuffer();
/*  35 */     for (int i = 0; i < str.length(); i++) {
/*  36 */       char nextChar = str.charAt(i);
/*  37 */       if (nextChar != ' ') {
/*  38 */         strBuf.append(nextChar);
/*     */       }
/*     */     } 
/*  41 */     return strBuf.toString().toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public List getElementList(String toolId, String[] elements) {
/*  45 */     List<OptionValueImpl> result = new ArrayList();
/*  46 */     for (int i = 0; i < elements.length; i++) {
/*  47 */       result.add(new OptionValueImpl(toolId, elements[i]));
/*     */     }
/*  49 */     return result;
/*     */   }
/*     */   
/*     */   public List getElementList(String toolId, List<String> elements) {
/*  53 */     List<OptionValueImpl> result = new ArrayList();
/*  54 */     for (int i = 0; i < elements.size(); i++) {
/*  55 */       result.add(new OptionValueImpl(toolId, elements.get(i)));
/*     */     }
/*  57 */     return result;
/*     */   }
/*     */   
/*     */   public VIT1 createVIT1(Pair[] rawData) {
/*  61 */     VIT1 result = null;
/*  62 */     List<Pair> in = new ArrayList();
/*  63 */     for (int i = 0; i < rawData.length; i++) {
/*  64 */       in.add(rawData[i]);
/*     */     }
/*  66 */     VITBuilder builder = VITBuilderProvider.getBuilder("VIT1 Builder", in, null);
/*  67 */     result = (VIT1)builder.build();
/*  68 */     return result;
/*     */   }
/*     */   
/*     */   public boolean extendedDebugEnabled() {
/*  72 */     boolean result = false;
/*  73 */     String value = ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("debug.extended");
/*  74 */     if (value != null && value.equalsIgnoreCase("true")) {
/*  75 */       result = true;
/*     */     }
/*  77 */     return result;
/*     */   }
/*     */   
/*     */   public void createDebugDir() {
/*  81 */     String spsDebugPath = ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug";
/*  82 */     File spsDebugDir = new File(spsDebugPath);
/*  83 */     if (!spsDebugDir.exists()) {
/*  84 */       spsDebugDir.mkdir();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDebugBlob(String name, byte[] data) {
/*  89 */     String debugDirPath = ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug";
/*  90 */     String blobPath = debugDirPath + System.getProperty("file.separator") + name;
/*  91 */     File blobFile = new File(blobPath);
/*     */     try {
/*  93 */       blobFile.createNewFile();
/*  94 */       FileOutputStream os = new FileOutputStream(blobFile);
/*  95 */       os.write(data);
/*  96 */     } catch (Exception e) {
/*  97 */       log.debug("Cannot write blobFile for verification: " + blobPath);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeDebugVIT(String type, Pair[] vit) {
/* 102 */     writeDebugVIT(type, vit, 0);
/*     */   }
/*     */   
/*     */   public void writeDebugVIT(String type, Pair[] vit, int step) {
/* 106 */     OutputStreamWriter osw = null;
/*     */     try {
/* 108 */       String spsDebugPath = ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug";
/* 109 */       File spsDebugDir = new File(spsDebugPath);
/* 110 */       if (!spsDebugDir.exists()) {
/* 111 */         spsDebugDir.mkdir();
/*     */       }
/* 113 */       String vitPath = spsDebugPath + System.getProperty("file.separator") + type + "_" + getEcuAddr(vit) + "_" + step + ".txt";
/* 114 */       File vitFile = new File(vitPath);
/*     */       
/* 116 */       vitFile.createNewFile();
/* 117 */       osw = new OutputStreamWriter(new FileOutputStream(vitFile));
/* 118 */       for (int i = 0; i < vit.length; i++) {
/* 119 */         osw.write((String)vit[i].getFirst() + " = " + (String)vit[i].getSecond() + "\r\n");
/*     */       }
/* 121 */       osw.close();
/* 122 */     } catch (Exception e) {
/* 123 */       log.debug("Cannot write debug VIT: " + e);
/*     */       try {
/* 125 */         if (osw != null) {
/* 126 */           osw.close();
/*     */         }
/* 128 */       } catch (Exception ee) {
/* 129 */         log.debug("This should never ever happen!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void installNativeToolCallback(ISPSTool spsTool) {
/* 135 */     boolean res = spsTool.installCallback();
/* 136 */     log.debug("InstallCallback() returned: " + res);
/*     */   }
/*     */   
/*     */   public void uninstallNativeToolCallback(ISPSTool spsTool, int cnt) {
/* 140 */     for (int i = 0; i < cnt; i++) {
/* 141 */       boolean res = spsTool.uninstallCallback();
/* 142 */       log.debug("UninstallCallback() returned: " + res);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized int getPairIndex(Pair[] pairs, String attr) {
/* 147 */     int result = -1;
/*     */     try {
/* 149 */       for (int i = 0; i < pairs.length; i++) {
/* 150 */         if (((String)pairs[i].getFirst()).compareToIgnoreCase(attr) == 0) {
/* 151 */           result = i;
/*     */           break;
/*     */         } 
/*     */       } 
/* 155 */     } catch (Exception e) {
/* 156 */       log.debug("Cannot determine Pair[] index for attribute: " + attr + ", " + e);
/*     */     } 
/* 158 */     return result;
/*     */   }
/*     */   
/*     */   public static String getEcuAddr(Pair[] vit) {
/* 162 */     return getElementValue(vit, "ecu_adr");
/*     */   }
/*     */   
/*     */   public static synchronized String getElementValue(Pair[] vit, String element) {
/* 166 */     String result = "notFound";
/* 167 */     for (int i = 0; i < vit.length; i++) {
/* 168 */       if (((String)vit[i].getFirst()).compareToIgnoreCase(element) == 0) {
/* 169 */         result = (String)vit[i].getSecond();
/*     */         break;
/*     */       } 
/*     */     } 
/* 173 */     return result;
/*     */   }
/*     */   public Pair[] getFileVIT1(String name) {
/*     */     PairImpl[] arrayOfPairImpl;
/* 177 */     Pair[] result = null;
/* 178 */     String vit1Path = null;
/* 179 */     BufferedReader fileVIT1 = null;
/* 180 */     ArrayList<PairImpl> tmp = new ArrayList();
/*     */     try {
/* 182 */       String spsDebugPath = ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug";
/* 183 */       vit1Path = spsDebugPath + System.getProperty("file.separator") + name;
/* 184 */       fileVIT1 = new BufferedReader(new FileReader(vit1Path));
/* 185 */       String line = null;
/* 186 */       while ((line = fileVIT1.readLine()) != null) {
/* 187 */         int j = line.indexOf('=');
/* 188 */         if (j >= 0) {
/* 189 */           String key = line.substring(0, j - 1).trim();
/* 190 */           String value = "";
/* 191 */           if (line.length() > j) {
/* 192 */             value = line.substring(j + 1).trim();
/*     */           }
/* 194 */           tmp.add(new PairImpl(key, value));
/*     */         } 
/*     */       } 
/* 197 */       int size = tmp.size();
/* 198 */       arrayOfPairImpl = new PairImpl[size];
/* 199 */       for (int i = 0; i < size; i++) {
/* 200 */         arrayOfPairImpl[i] = (PairImpl)tmp.get(i);
/*     */       }
/* 202 */     } catch (Exception e) {
/* 203 */       log.error("Cannot return VIT1 from file: " + vit1Path);
/*     */     } 
/* 205 */     return (Pair[])arrayOfPairImpl;
/*     */   }
/*     */   
/*     */   public void writeDebugDTC(byte[] dtcData) {
/* 209 */     String spsDebugPath = ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug";
/* 210 */     File file = new File(spsDebugPath, System.currentTimeMillis() + ".dtc");
/*     */     try {
/* 212 */       FileOutputStream fos = new FileOutputStream(file);
/* 213 */       fos.write(dtcData);
/* 214 */       fos.close();
/* 215 */     } catch (Exception e) {
/* 216 */       log.error("unable to write debug dtc - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getTestDTC() {
/* 221 */     byte[] result = null;
/* 222 */     int dtcSize = 0;
/*     */     try {
/* 224 */       File dtcFile = new File(ClientAppContextProvider.getClientAppContext().getHomeDir() + System.getProperty("file.separator") + "spsDebug" + System.getProperty("file.separator") + "TEST-DTC");
/* 225 */       dtcSize = (int)dtcFile.length();
/* 226 */       if (dtcSize > 0) {
/* 227 */         FileInputStream fis = new FileInputStream(dtcFile);
/* 228 */         result = new byte[dtcSize];
/* 229 */         fis.read(result);
/* 230 */         fis.close();
/*     */       } 
/* 232 */     } catch (Exception e) {
/* 233 */       log.debug("Cannot read DTC test file TEST-DTC");
/*     */     } 
/* 235 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\too\\util\ToolUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */