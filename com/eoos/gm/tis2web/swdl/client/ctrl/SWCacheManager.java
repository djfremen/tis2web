/*     */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.FilenameFilterImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.StringComparator;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.File;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.FileProxy;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.dtc.TroubleCode;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWCacheManager
/*     */ {
/*  41 */   private Logger log = Logger.getLogger(SWCacheManager.class);
/*  42 */   private Hashtable appsDate2Path = new Hashtable<Object, Object>();
/*  43 */   private Hashtable appsPath2Size = new Hashtable<Object, Object>();
/*  44 */   private String currSetPath = null;
/*     */ 
/*     */   
/*     */   public SWCacheManager() {
/*  48 */     this.currSetPath = null;
/*     */   }
/*     */   
/*     */   private String buildDTCPath() {
/*  52 */     String dtcPath = (String)SDCurrentContext.getInstance().getSettings().get("CachePath");
/*  53 */     dtcPath = dtcPath + File.separator + "DTC";
/*  54 */     return dtcPath;
/*     */   }
/*     */   
/*     */   private String buildDevPath(String dev) {
/*  58 */     String devPath = (String)SDCurrentContext.getInstance().getSettings().get("CachePath");
/*  59 */     devPath = devPath + File.separator + dev;
/*  60 */     return devPath;
/*     */   }
/*     */   
/*     */   public String buildAppPath(DeviceInfo appInf) {
/*  64 */     String appPath = buildDevPath(SDCurrentContext.getInstance().getSelectedTool().getDescription());
/*  65 */     appPath = appPath + File.separator + appInf.getAppName().replace('/', '-') + File.separator + appInf.getVersion();
/*  66 */     appPath = appPath + File.separator + appInf.getLanguage();
/*  67 */     return appPath;
/*     */   }
/*     */   
/*     */   public ArrayList loadFilesInfo(DeviceInfo appInf) {
/*  71 */     ArrayList filesInf = null;
/*  72 */     String appPath = buildAppPath(appInf);
/*     */     try {
/*  74 */       File appSettings = new File(appPath + File.separator + "files.inf");
/*  75 */       if (!appSettings.exists())
/*  76 */         return filesInf; 
/*  77 */       FileInputStream fis = new FileInputStream(appSettings);
/*  78 */       ObjectInputStream ois = new ObjectInputStream(fis);
/*  79 */       ois.readUTF();
/*  80 */       filesInf = (ArrayList)ois.readObject();
/*  81 */       ois.close();
/*  82 */       fis.close();
/*  83 */     } catch (Exception e) {
/*  84 */       this.log.error("Exception when load files information from: " + appPath + "files.inf, " + e, e);
/*  85 */       return filesInf;
/*     */     } 
/*  87 */     return filesInf;
/*     */   }
/*     */   
/*     */   public Set checkFilesIntegrity(Set files, ArrayList<SDFileInfo> filesInfo, String appPath) {
/*  91 */     Map<Object, Object> id2file = new Hashtable<Object, Object>();
/*  92 */     Iterator<FileProxy> it = files.iterator();
/*  93 */     while (it.hasNext()) {
/*  94 */       FileProxy fProxy = it.next();
/*  95 */       id2file.put(fProxy.getIdentifier(), fProxy);
/*     */     } 
/*  97 */     for (int i = 0; i < filesInfo.size(); i++) {
/*  98 */       SDFileInfo fileInfo = filesInfo.get(i);
/*  99 */       FileProxy file = (FileProxy)id2file.get(Integer.toString(fileInfo.getFileID()));
/* 100 */       if (file == null || !checkFileIntegrity(appPath, file, file.getFingerprint())) {
/* 101 */         filesInfo.remove(i);
/* 102 */         i--;
/*     */       } else {
/* 104 */         id2file.remove(Integer.toString(fileInfo.getFileID()));
/*     */       } 
/*     */     } 
/* 107 */     return new HashSet(id2file.values());
/*     */   }
/*     */   
/*     */   private boolean checkFileIntegrity(String appPath, FileProxy file, String fingerprint) {
/* 111 */     String path = appPath + File.separator + file.getIdentifier() + "_" + file.getName();
/*     */     try {
/* 113 */       byte[] buff = getFileFromCache(path);
/* 114 */       String md5Content = StringUtilities.bytesToHex(MessageDigest.getInstance("MD5").digest(buff));
/* 115 */       return md5Content.equals(fingerprint);
/* 116 */     } catch (Exception e) {
/* 117 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean createTreeDirs(DeviceInfo appInf, String appPath) {
/*     */     try {
/* 123 */       File appDir = new File(appPath);
/* 124 */       appDir.mkdirs();
/* 125 */       if (!appDir.exists()) {
/* 126 */         return false;
/*     */       }
/* 128 */     } catch (Exception e) {
/* 129 */       this.log.error("Exception when create the tree directories: " + appPath + "; " + e, e);
/* 130 */       return false;
/*     */     } 
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public void saveFileInCache(String appPath, File file) throws Exception {
/* 136 */     String path = appPath + File.separator + file.getIdentifier() + "_" + file.getName();
/*     */     try {
/* 138 */       FileOutputStream fos = new FileOutputStream(path);
/* 139 */       fos.write(file.getContent());
/* 140 */       fos.close();
/* 141 */     } catch (Exception e) {
/* 142 */       this.log.error("Exception when save file in cache: " + path, e);
/* 143 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getFileFromCache(String path) throws Exception {
/* 148 */     byte[] retValue = null;
/*     */     try {
/* 150 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 151 */       File fl = new File(path);
/* 152 */       FileInputStream fis = new FileInputStream(fl);
/* 153 */       byte[] buffer = new byte[10240];
/* 154 */       int count = 0;
/* 155 */       while ((count = fis.read(buffer)) != -1) {
/* 156 */         baos.write(buffer, 0, count);
/*     */       }
/* 158 */       fis.close();
/* 159 */       baos.close();
/* 160 */       retValue = baos.toByteArray();
/* 161 */     } catch (Exception e) {
/* 162 */       this.log.error(("Exception when read file from cache: " + path != null) ? path : "null", e);
/* 163 */       throw e;
/*     */     } 
/* 165 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean saveFilesInfo(DeviceInfo appInf, ArrayList filesInf, String appPath) {
/*     */     try {
/* 170 */       FileOutputStream fis = new FileOutputStream(appPath + File.separator + "files.inf");
/* 171 */       ObjectOutputStream oos = new ObjectOutputStream(fis);
/* 172 */       oos.writeUTF(appInf.getVersDate());
/* 173 */       oos.writeObject(filesInf);
/* 174 */       oos.close();
/* 175 */       fis.close();
/* 176 */     } catch (Exception e) {
/* 177 */       this.log.error("Exception when save files information to: " + appPath + "files.inf, " + e, e);
/* 178 */       return false;
/*     */     } 
/* 180 */     return true;
/*     */   }
/*     */   
/*     */   public void makeAvailableSpace(Set files, String currSetPath) throws Exception {
/* 184 */     long total = 0L;
/* 185 */     long occupied = 0L;
/* 186 */     long need = 0L;
/* 187 */     this.currSetPath = currSetPath;
/*     */     try {
/* 189 */       total = (1048576 * Integer.parseInt((String)SDCurrentContext.getInstance().getSettings().get("CacheSize")));
/* 190 */       occupied = getOccupiedSpace();
/* 191 */       Iterator<FileProxy> itFile = files.iterator();
/* 192 */       while (itFile.hasNext()) {
/* 193 */         need += ((FileProxy)itFile.next()).getSize().longValue();
/*     */       }
/* 195 */       if (total - occupied - need <= 0L) {
/* 196 */         deleteApps(total - occupied - need);
/*     */       }
/* 198 */     } catch (Exception e) {
/* 199 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private long getOccupiedSpace() throws Exception {
/* 204 */     long occupied = 0L;
/* 205 */     Device[] devices = SDCurrentContext.getInstance().getDevices();
/* 206 */     for (int z = 0; z < devices.length; z++) {
/* 207 */       Device device = devices[z];
/* 208 */       String devPath = buildDevPath(device.getDescription());
/* 209 */       TreeMap<Object, Object> appDate2Path = new TreeMap<Object, Object>((Comparator<?>)new StringComparator());
/* 210 */       Hashtable<Object, Object> appPath2Size = new Hashtable<Object, Object>();
/*     */       try {
/* 212 */         File devDir = new File(devPath);
/* 213 */         if (devDir.exists()) {
/* 214 */           File[] appNameDirs = devDir.listFiles();
/* 215 */           for (int i = 0; i < appNameDirs.length; i++) {
/* 216 */             if (appNameDirs[i].isDirectory()) {
/* 217 */               File[] appVersDirs = appNameDirs[i].listFiles();
/* 218 */               for (int j = 0; j < appVersDirs.length; j++) {
/* 219 */                 if (appVersDirs[j].isDirectory()) {
/* 220 */                   File[] appLanDirs = appVersDirs[j].listFiles();
/* 221 */                   for (int m = 0; m < appLanDirs.length; m++) {
/* 222 */                     if (appLanDirs[m].isDirectory()) {
/*     */                       try {
/* 224 */                         occupied += getAppOccupiedSpace(appLanDirs[m].getPath(), appDate2Path, appPath2Size);
/* 225 */                       } catch (Exception e) {}
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 235 */       } catch (Exception e) {
/* 236 */         throw e;
/*     */       } 
/* 238 */       this.appsDate2Path.put(device, appDate2Path);
/* 239 */       this.appsPath2Size.put(device, appPath2Size);
/*     */     } 
/* 241 */     return occupied;
/*     */   }
/*     */   
/*     */   private long getAppOccupiedSpace(String appPath, TreeMap<String, List<String>> appDate2Path, Hashtable<String, Long> appPath2Size) throws Exception {
/* 245 */     long occupied = 0L;
/*     */     try {
/* 247 */       File appSettings = new File(appPath + File.separator + "files.inf");
/* 248 */       if (!appSettings.exists())
/* 249 */         throw new Exception("missing file: " + appSettings.getPath()); 
/* 250 */       FileInputStream fis = new FileInputStream(appSettings);
/* 251 */       ObjectInputStream ois = new ObjectInputStream(fis);
/* 252 */       String data = ois.readUTF();
/* 253 */       if (appDate2Path.containsKey(data)) {
/* 254 */         ((List<String>)appDate2Path.get(data)).add(appSettings.getPath());
/*     */       } else {
/* 256 */         List<String> lstPaths = new ArrayList();
/* 257 */         lstPaths.add(appSettings.getPath());
/* 258 */         appDate2Path.put(data, lstPaths);
/*     */       } 
/* 260 */       ArrayList<SDFileInfo> fInfos = (ArrayList)ois.readObject();
/* 261 */       fis.close();
/* 262 */       ois.close();
/* 263 */       occupied += appSettings.length();
/* 264 */       for (int j = 0; j < fInfos.size(); j++) {
/*     */         try {
/* 266 */           occupied += ((SDFileInfo)fInfos.get(j)).getZipSize();
/* 267 */         } catch (Exception e) {}
/*     */       } 
/*     */ 
/*     */       
/* 271 */       appPath2Size.put(appSettings.getPath(), Long.valueOf(occupied));
/* 272 */     } catch (Exception e) {
/* 273 */       throw e;
/*     */     } 
/* 275 */     return occupied;
/*     */   }
/*     */   
/*     */   public boolean clearCache() {
/* 279 */     this.log.debug("Try to clear cache");
/* 280 */     boolean retVal = true;
/* 281 */     Device[] devices = SDCurrentContext.getInstance().getDevices();
/* 282 */     for (int i = 0; i < devices.length; i++) {
/* 283 */       String path = buildDevPath(devices[i].getDescription());
/* 284 */       File dir = new File(path);
/* 285 */       if (dir.exists() == true && 
/* 286 */         !deleteTree(dir)) {
/* 287 */         retVal = false;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return retVal;
/*     */   }
/*     */   
/*     */   private boolean deleteTree(File f) {
/* 295 */     boolean retVal = false;
/*     */     try {
/* 297 */       File[] files = f.listFiles();
/* 298 */       for (int i = 0; i < files.length; i++) {
/* 299 */         if (files[i].isDirectory() == true) {
/* 300 */           if (retVal = !deleteTree(files[i])) {
/* 301 */             this.log.error("Function deleteTree failed. Path " + files[i].getAbsolutePath());
/*     */           }
/*     */         }
/* 304 */         else if (!(retVal = files[i].delete())) {
/* 305 */           this.log.error("Could not delete file: " + files[i].getAbsolutePath());
/*     */         } else {
/* 307 */           this.log.debug("Delete file: " + files[i].getAbsolutePath());
/*     */         } 
/*     */       } 
/*     */       
/* 311 */       if (!(retVal = f.delete())) {
/* 312 */         this.log.error("Could not delete directory: " + f.getAbsolutePath());
/*     */       } else {
/* 314 */         this.log.debug("Delete directory: " + f.getAbsolutePath());
/*     */       } 
/* 316 */     } catch (Exception e) {
/* 317 */       this.log.error("Function deleteDirectory failed" + e);
/* 318 */       retVal = false;
/*     */     } 
/* 320 */     return retVal;
/*     */   }
/*     */   
/*     */   private void deleteApps(long sp) throws Exception {
/*     */     try {
/* 325 */       long space = sp;
/* 326 */       space = deleteApps(SDCurrentContext.getInstance().getSelectedTool(), space);
/* 327 */       if (space <= 0L) {
/* 328 */         Device[] devices = SDCurrentContext.getInstance().getDevices();
/* 329 */         for (int i = 0; i < devices.length && space <= 0L; i++) {
/* 330 */           Device device = devices[i];
/* 331 */           if (!SDCurrentContext.getInstance().getSelectedTool().equals(device))
/*     */           {
/* 333 */             space = deleteApps(device, space); } 
/*     */         } 
/*     */       } 
/* 336 */     } catch (Exception e) {
/* 337 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private long deleteApps(Device dev, long sp) throws Exception {
/* 342 */     long space = sp;
/* 343 */     TreeMap appDate2Path = (TreeMap)this.appsDate2Path.get(dev);
/* 344 */     Hashtable appPath2Size = (Hashtable)this.appsPath2Size.get(dev);
/* 345 */     Set dates = appDate2Path.keySet();
/* 346 */     Iterator<String> it = dates.iterator();
/* 347 */     while (it.hasNext() && space <= 0L) {
/* 348 */       String date = it.next();
/* 349 */       List<String> lstPaths = (List)appDate2Path.get(date);
/* 350 */       for (int i = 0; i < lstPaths.size() && space <= 0L; i++) {
/* 351 */         String path = lstPaths.get(i);
/* 352 */         long occupied = ((Long)appPath2Size.get(path)).longValue();
/*     */         
/* 354 */         try { if (this.currSetPath == null || this.currSetPath.compareTo(path) != 0)
/*     */           
/*     */           { 
/* 357 */             deleteApp(path);
/*     */ 
/*     */ 
/*     */             
/* 361 */             space += occupied; }  } catch (Exception e) { throw e; }
/*     */       
/*     */       } 
/* 364 */     }  return space;
/*     */   }
/*     */   
/*     */   public boolean deleteApp(String pathSetFile) throws Exception {
/* 368 */     boolean retVal = false;
/*     */     try {
/* 370 */       String path = null;
/* 371 */       int indx = pathSetFile.lastIndexOf(File.separator + "files.inf");
/* 372 */       if (indx > -1) {
/* 373 */         path = pathSetFile.substring(0, indx);
/*     */       }
/* 375 */       if (path == null) {
/* 376 */         this.log.error("Wrong path: " + pathSetFile);
/* 377 */         throw new Exception("Wrong path: " + pathSetFile);
/*     */       } 
/* 379 */       File app = new File(path);
/*     */       
/* 381 */       if (!(retVal = deleteChilds(app))) {
/* 382 */         this.log.error("Function deleteChilds failed");
/* 383 */         return retVal;
/*     */       } 
/* 385 */       if (!(retVal = app.delete())) {
/* 386 */         this.log.error("Can't delete " + app.getAbsolutePath());
/* 387 */         return retVal;
/*     */       } 
/* 389 */       if (!(retVal = deleteAppPath(app))) {
/* 390 */         this.log.error("Function deleteAppPath failed");
/* 391 */         return retVal;
/*     */       } 
/* 393 */     } catch (Exception e) {
/* 394 */       this.log.error("Function deleteApp failed", e);
/* 395 */       throw e;
/*     */     } 
/* 397 */     return retVal;
/*     */   }
/*     */   
/*     */   private boolean deleteChilds(File dir) throws Exception {
/* 401 */     boolean retVal = false;
/*     */     try {
/* 403 */       File[] childs = dir.listFiles();
/* 404 */       for (int i = 0; i < childs.length; i++) {
/* 405 */         if (childs[i].isDirectory()) {
/* 406 */           if (!(retVal = deleteChilds(childs[i]))) {
/* 407 */             this.log.error("Function deleteChilds failed");
/*     */             break;
/*     */           } 
/*     */         } else {
/* 411 */           if (!(retVal = childs[i].delete())) {
/* 412 */             this.log.error("Can't delete " + childs[i].getAbsolutePath());
/*     */             break;
/*     */           } 
/* 415 */           this.log.debug("Delete file " + childs[i].getAbsolutePath());
/*     */         }
/*     */       
/*     */       } 
/* 419 */     } catch (Exception e) {
/* 420 */       this.log.error("Function deleteChilds failed", e);
/* 421 */       throw e;
/*     */     } 
/* 423 */     return retVal;
/*     */   }
/*     */   
/*     */   private boolean deleteAppPath(File app) throws Exception {
/* 427 */     boolean retVal = false;
/*     */     try {
/* 429 */       File appVers = app.getParentFile();
/* 430 */       if (!(retVal = appVers.delete())) {
/* 431 */         this.log.error("Can't delete " + appVers.getAbsolutePath());
/* 432 */         return retVal;
/*     */       } 
/*     */       
/* 435 */       File appName = appVers.getParentFile();
/* 436 */       if (!(retVal = appName.delete())) {
/* 437 */         this.log.error("Can't delete " + appName.getAbsolutePath());
/* 438 */         return retVal;
/*     */       } 
/*     */       
/* 441 */       File appDev = appName.getParentFile();
/* 442 */       if (!(retVal = appDev.delete())) {
/* 443 */         this.log.error("Can't delete " + appDev.getAbsolutePath());
/* 444 */         return retVal;
/*     */       } 
/* 446 */     } catch (Exception e) {
/* 447 */       this.log.error("Function deleteAppPath failed", e);
/* 448 */       throw e;
/*     */     } 
/* 450 */     return retVal;
/*     */   }
/*     */   
/*     */   public File[] getDTCFiles() {
/* 454 */     String dtcPath = buildDTCPath();
/*     */     try {
/* 456 */       File dtcDir = new File(dtcPath);
/* 457 */       if (!dtcDir.exists()) {
/* 458 */         dtcDir.mkdirs();
/*     */       }
/* 460 */       return dtcDir.listFiles((FilenameFilter)new FilenameFilterImpl("dtc*.dat"));
/* 461 */     } catch (Exception e) {
/* 462 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public TroubleCode getDTCFile(File file) throws Exception {
/* 467 */     TroubleCode dtcData = null;
/*     */     try {
/* 469 */       FileInputStream fis = new FileInputStream(file);
/* 470 */       ObjectInputStream ois = new ObjectInputStream(fis);
/* 471 */       dtcData = (TroubleCode)ois.readObject();
/* 472 */       ois.close();
/* 473 */       fis.close();
/* 474 */     } catch (Exception e) {
/* 475 */       throw e;
/*     */     } 
/* 477 */     return dtcData;
/*     */   }
/*     */   
/*     */   public void saveDTCDataInCache(TroubleCode dtcData) throws Exception {
/* 481 */     String dtcPath = buildDTCPath();
/*     */     
/*     */     try {
/* 484 */       String dtcIndx = "1";
/* 485 */       File[] dtcFiles = getDTCFiles();
/* 486 */       if (dtcFiles != null) {
/* 487 */         dtcIndx = Integer.toString(dtcFiles.length + 1);
/*     */       }
/* 489 */       FileOutputStream fos = new FileOutputStream(dtcPath + File.separator + "dtc" + dtcIndx + ".dat");
/* 490 */       ObjectOutputStream oos = new ObjectOutputStream(fos);
/* 491 */       oos.writeObject(dtcData);
/* 492 */       oos.close();
/* 493 */       fos.close();
/* 494 */     } catch (Exception e) {
/* 495 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\SWCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */