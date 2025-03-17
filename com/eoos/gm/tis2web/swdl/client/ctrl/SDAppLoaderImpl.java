/*     */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*     */ import com.eoos.gm.tis2web.swdl.client.ui.ctrl.SDCurrentContext;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.DomainUtil;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.SDFileInfoComparator;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.File;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.FileProxy;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SDAppLoaderImpl
/*     */   implements SDAppLoader
/*     */ {
/*  39 */   private Logger log = Logger.getLogger(SDAppLoaderImpl.class);
/*  40 */   private DeviceInfo appInfo = null;
/*  41 */   private Hashtable id2file = null;
/*  42 */   private ArrayList filesInfo = null;
/*  43 */   private SWCacheManager cacheMan = new SWCacheManager();
/*     */   private boolean isAborted = false;
/*  45 */   private String appPath = null;
/*  46 */   private int error = 0;
/*     */ 
/*     */   
/*     */   public SDAppLoaderImpl(DeviceInfo appInfo) throws Exception {
/*  50 */     this.appInfo = appInfo;
/*  51 */     this.appPath = this.cacheMan.buildAppPath(appInfo);
/*  52 */     this.filesInfo = this.cacheMan.loadFilesInfo(appInfo);
/*     */     try {
/*  54 */       getAppFromServer();
/*  55 */     } catch (Exception e) {
/*  56 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getFilesFromServer() {
/*  61 */     String currSetPath = null;
/*  62 */     Version vers = (Version)this.appInfo.getVersionObject();
/*  63 */     Set files = new HashSet(vers.getLanNeutralFiles());
/*  64 */     files.addAll(vers.getFiles(new Language(this.appInfo.getLanguage())));
/*  65 */     int countFiles = files.size();
/*  66 */     if (this.filesInfo != null) {
/*  67 */       currSetPath = this.appPath + File.separator + "files.inf";
/*  68 */       files = this.cacheMan.checkFilesIntegrity(files, this.filesInfo, this.appPath);
/*     */     } 
/*     */     try {
/*  71 */       this.cacheMan.makeAvailableSpace(files, currSetPath);
/*  72 */     } catch (Exception e) {
/*  73 */       this.log.error("Exception when try to free space drom cache: " + e, e);
/*  74 */       this.error = 5;
/*     */       return;
/*     */     } 
/*  77 */     if (this.filesInfo == null) {
/*  78 */       if (!this.cacheMan.createTreeDirs(this.appInfo, this.appPath)) {
/*  79 */         this.log.error("Error when create the tree directories " + this.appPath + ".");
/*  80 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(29L));
/*  81 */         this.error = 1;
/*     */         return;
/*     */       } 
/*  84 */       this.filesInfo = new ArrayList();
/*     */     } 
/*     */     
/*  87 */     SDEvent ev = new SDEvent(25L, Integer.valueOf(countFiles));
/*  88 */     SDNotificationServer.getInstance().sendNotification((Notification)ev);
/*  89 */     this.isAborted = !((Boolean)ev.getParam(1)).booleanValue();
/*     */     
/*  91 */     Iterator<FileProxy> itFile = files.iterator();
/*  92 */     int iFiles = this.filesInfo.size();
/*  93 */     while (itFile.hasNext() && !this.isAborted) {
/*  94 */       FileProxy fileProxy = itFile.next();
/*  95 */       ev = new SDEvent(26L, fileProxy.getSize(), fileProxy.getName());
/*  96 */       SDNotificationServer.getInstance().sendNotification((Notification)ev);
/*  97 */       this.isAborted = !((Boolean)ev.getParam(2)).booleanValue();
/*     */       
/*  99 */       if (this.isAborted) {
/*     */         break;
/*     */       }
/* 102 */       Command command = new Command(4);
/* 103 */       command.addParameter("device", SDCurrentContext.getInstance().getSelectedTool());
/* 104 */       command.addParameter("applicationid", vers.getApplication().getIdentifier());
/* 105 */       command.addParameter("fileid", fileProxy.getIdentifier());
/*     */       
/*     */       try {
/* 108 */         Object obj = ServerRequestor.getInstance().sendRequestFile(command);
/* 109 */         if (obj != null && obj instanceof ServerError) {
/* 110 */           (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 111 */           this.log.error("Server error when get file: " + fileProxy.getName() + ", errNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 112 */           this.error = -4;
/*     */           break;
/*     */         } 
/* 115 */         if (obj != null && obj instanceof File) {
/* 116 */           if (!((File)obj).checkFingerprint()) {
/* 117 */             this.log.error("Incorect file (fingerprint): " + fileProxy.getName());
/* 118 */             this.error = 2;
/*     */             break;
/*     */           } 
/* 121 */           saveFile((File)obj);
/*     */         } else {
/* 123 */           this.log.error("Incorect object: when get file " + fileProxy.getName() + " from server.");
/* 124 */           this.error = 3;
/*     */           break;
/*     */         } 
/* 127 */       } catch (Exception e) {
/* 128 */         this.log.error("Exception when get the file: " + fileProxy.getName() + " from Server, " + e, e);
/* 129 */         this.error = 3;
/*     */         
/*     */         break;
/*     */       } 
/* 133 */       this.log.debug("File " + fileProxy.getName() + ", download successful.");
/* 134 */       iFiles++;
/* 135 */       ev = new SDEvent(28L, Integer.valueOf(iFiles));
/* 136 */       SDNotificationServer.getInstance().sendNotification((Notification)ev);
/* 137 */       this.isAborted = !((Boolean)ev.getParam(1)).booleanValue();
/*     */     } 
/* 139 */     if (!this.cacheMan.saveFilesInfo(this.appInfo, this.filesInfo, this.appPath)) {
/* 140 */       this.error = 4;
/*     */     }
/* 142 */     SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(29L));
/*     */   }
/*     */   
/*     */   private void saveFile(File file) throws Exception {
/*     */     try {
/* 147 */       this.filesInfo.add(DomainUtil.File2SDFileInfo(file));
/* 148 */       this.cacheMan.saveFileInCache(this.appPath, file);
/* 149 */     } catch (Exception e) {
/* 150 */       this.log.error(("unable to save file:" + file != null) ? file.getName() : "null");
/* 151 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getAppFromServer() throws Exception {
/*     */     try {
/* 157 */       (new Thread() {
/*     */           public void run() {
/* 159 */             SDAppLoaderImpl.this.getFilesFromServer();
/*     */           }
/*     */         }).start();
/*     */       
/* 163 */       SDEvent ev = new SDEvent(24L);
/* 164 */       SDNotificationServer.getInstance().sendNotification((Notification)ev);
/* 165 */       if (this.isAborted) {
/* 166 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(15L, "SelectDevice", new Boolean(true)));
/* 167 */         throw new Exception("Download from server aborted from user.");
/*     */       } 
/* 169 */       Exception exp = null;
/* 170 */       String ids = "";
/* 171 */       switch (this.error) {
/*     */         case -4:
/* 173 */           ids = DomainUtil.SrvErr2IDS((SDCurrentContext.getInstance()).srvError);
/* 174 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, ids, "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 175 */           exp = new Exception("Download failed. Corrupted file when downloaded from server.");
/*     */           break;
/*     */         case 2:
/* 178 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERROR_BAD_FILE", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 179 */           exp = new Exception("Download failed. Corrupted file when downloaded from server.");
/*     */           break;
/*     */         case 1:
/* 182 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERROR_CREATE_DIR", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 183 */           exp = new Exception("Cannot create directories structure for cache.");
/*     */           break;
/*     */         case 5:
/* 186 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERROR_MAKE_SPACE", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 187 */           exp = new Exception("Error occured when try to erase applications from cache.");
/*     */           break;
/*     */         case 4:
/* 190 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_ERROR_SAVE_INF", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 191 */           exp = new Exception("Error occured when try to save application info.");
/*     */           break;
/*     */         case 3:
/* 194 */           SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(2L, "IDS_COMM_SRV_FAILED", "IDS_TITLE_SWDL", Integer.valueOf(0)));
/* 195 */           exp = new Exception("Error occured when download the files from server.");
/*     */           break;
/*     */       } 
/* 198 */       if (this.error > 0 || this.error == -4) {
/* 199 */         SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(15L, "SelectDevice", new Boolean(true)));
/* 200 */         throw exp;
/*     */       } 
/* 202 */     } catch (Exception e) {
/* 203 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getFileData(Integer fileID) {
/* 208 */     byte[] buff = null;
/*     */     try {
/* 210 */       buff = this.cacheMan.getFileFromCache((String)this.id2file.get(fileID));
/*     */       try {
/* 212 */         byte[] buf = ZipUtil.gunzip(buff);
/* 213 */         buff = buf;
/* 214 */       } catch (Exception e) {
/* 215 */         this.log.error("Exception when unzip the file content. FileID = " + fileID, e);
/*     */       } 
/* 217 */     } catch (Exception e) {
/* 218 */       this.log.error("Exception when get the file with FileID = " + fileID, e);
/*     */     } 
/* 220 */     return buff;
/*     */   }
/*     */   
/*     */   public Hashtable getFileID2Name() {
/* 224 */     TreeMap<Object, Object> sortIDs = new TreeMap<Object, Object>();
/* 225 */     if (this.id2file == null)
/*     */     {
/* 227 */       for (int i = 0; i < this.filesInfo.size(); i++) {
/* 228 */         SDFileInfo fileInfo = this.filesInfo.get(i);
/* 229 */         String path = this.appPath + File.separator + fileInfo.getFileID() + "_" + fileInfo.getFileName();
/* 230 */         sortIDs.put(Integer.valueOf(fileInfo.getFileID()), path);
/*     */       } 
/*     */     }
/* 233 */     this.id2file = new Hashtable<Object, Object>(sortIDs);
/* 234 */     return this.id2file;
/*     */   }
/*     */   
/*     */   public SDFileInfo[] getFilesInfo() {
/* 238 */     SDFileInfo[] filesInf = new SDFileInfo[0];
/* 239 */     TreeSet sortIDs = new TreeSet((Comparator<?>)new SDFileInfoComparator());
/* 240 */     sortIDs.addAll(this.filesInfo);
/* 241 */     filesInf = (SDFileInfo[])sortIDs.toArray((Object[])filesInf);
/* 242 */     return filesInf;
/*     */   }
/*     */   
/*     */   public boolean isDownAborted() {
/* 246 */     return this.isAborted;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\SDAppLoaderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */