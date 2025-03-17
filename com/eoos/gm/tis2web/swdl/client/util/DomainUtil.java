/*     */ package com.eoos.gm.tis2web.swdl.client.util;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.File;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class DomainUtil
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(DomainUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceInfo Version2DeviceInfo(Version vers) {
/*  35 */     Language lang = null;
/*  36 */     Set languages = vers.getLanguages();
/*  37 */     Iterator<Language> itLang = languages.iterator();
/*  38 */     if (itLang.hasNext()) {
/*  39 */       lang = itLang.next();
/*     */     } else {
/*  41 */       return null;
/*     */     } 
/*  43 */     return Version2DeviceInfo(vers, lang);
/*     */   }
/*     */   
/*     */   public static DeviceInfo Version2DeviceInfo(Version vers, Language lang) {
/*  47 */     Date date = new Date(vers.getDate().longValue());
/*  48 */     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/*  49 */     DeviceInfo devInf = new DeviceInfo(vers.getApplication().getDescription(), vers.getNumber(), formatter.format(date), (String)lang.getIdentifier(), vers.getSize().intValue());
/*  50 */     devInf.setVersionObject(vers);
/*  51 */     return devInf;
/*     */   }
/*     */   
/*     */   public static SDFileInfo File2SDFileInfo(File file) throws Exception {
/*  55 */     SDFileInfo fileInfo = null;
/*     */     try {
/*  57 */       int fID = Integer.parseInt((String)file.getIdentifier());
/*  58 */       int fType = file.getType().intValue();
/*  59 */       int pNo = file.getPageNo().intValue();
/*  60 */       int bNo = file.getBlockNo().intValue();
/*  61 */       int addr = Integer.parseInt(file.getMemoryAddr(), 16);
/*  62 */       int bCount = file.getBlockCount().intValue();
/*  63 */       int sizeZip = file.getSize().intValue();
/*  64 */       short status = file.getStatus().shortValue();
/*  65 */       int size = sizeZip;
/*     */       try {
/*  67 */         byte[] buff = ZipUtil.gunzip(file.getContent());
/*  68 */         size = buff.length;
/*  69 */       } catch (Exception e) {
/*  70 */         log.error(("unable to unzip file content for file:" + file != null) ? file.getName() : "null");
/*  71 */         throw e;
/*     */       } 
/*  73 */       fileInfo = new SDFileInfo(fID, file.getName(), fType, pNo, bNo, addr, bCount, size, status, file.getRevision());
/*  74 */       fileInfo.setZipSize(sizeZip);
/*  75 */     } catch (Exception e) {
/*  76 */       log.error(("unable to retrieve SDFileInfo for file:" + file != null) ? file.getName() : "null");
/*  77 */       throw e;
/*     */     } 
/*  79 */     return fileInfo;
/*     */   }
/*     */   
/*     */   public static String SrvErr2IDS(ServerError srvErr) {
/*  83 */     if (srvErr == null) {
/*  84 */       return "IDS_SRVERR_UNKNOWN";
/*     */     }
/*  86 */     switch (srvErr.getError()) {
/*     */       case 3:
/*  88 */         return "IDS_SRVERR_CHECK_SESSION";
/*     */       case 1:
/*  90 */         return "IDS_INACTIVE_SESSION";
/*     */       case 4:
/*  92 */         return "IDS_SRVERR_PACK_RESPONSE";
/*     */       case 2:
/*  94 */         return "IDS_SRVERR_UNPACK_COMMAND";
/*     */       case 5:
/*  96 */         return "IDS_SRVERR_NULLOBJ";
/*     */       case 0:
/*  98 */         return "IDS_SRVERR_UNKNOWN";
/*     */       case 6:
/* 100 */         return "IDS_SRVERR_REINIT";
/*     */     } 
/* 102 */     return "IDS_SRVERR_UNKNOWN";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\DomainUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */