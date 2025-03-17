/*    */ package com.eoos.gm.tis2web.common.filedwnld;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.DownloadManager;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceFactory;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.FileNameFilter;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.WellKnownFilter;
/*    */ import com.eoos.scsm.v2.swing.UIUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileDwnld
/*    */ {
/*    */   public static void execute(File homeDir, final LabelResource labelResource, Locale locale) throws Exception {
/* 26 */     DownloadManager dwnldService = (DownloadManager)DownloadServiceFactory.createInstance((SoftwareKey)new SessionKey(getSessionID()), null, new File(homeDir, "wrk"), null);
/* 27 */     Collection<FileNameFilter> filters = new HashSet();
/* 28 */     filters.add(new FileNameFilter(getFilename()));
/* 29 */     filters.add(WellKnownFilter.NEWEST_VERSION);
/* 30 */     Collection<IDownloadUnit> units = dwnldService.getDownloadUnits(filters);
/* 31 */     if (!Util.isNullOrEmpty(units)) {
/* 32 */       final File[] targetFile = { null };
/* 33 */       Util.executeOnAWTThread(new Runnable()
/*    */           {
/*    */             public void run() {
/* 36 */               JFileChooser fileChooser = new JFileChooser();
/* 37 */               fileChooser.setSelectedFile(new File(FileDwnld.getFilename()));
/* 38 */               fileChooser.setDialogTitle(labelResource.getLabel("select.dest.dir"));
/* 39 */               if (0 == fileChooser.showSaveDialog(null)) {
/* 40 */                 targetFile[0] = fileChooser.getSelectedFile();
/*    */               }
/*    */             }
/*    */           },  true);
/* 44 */       if (targetFile[0] != null) {
/* 45 */         File tmp = Util.createTmpDir("t2w.filedwnld");
/* 46 */         IDownloadPackage pkg = dwnldService.createPackage(units, tmp);
/* 47 */         UIUtil.ProgressObserver po = UIUtil.showProgressObserverV2(null, labelResource);
/*    */         try {
/* 49 */           po.setProgress(labelResource.getMessage("downloading"));
/* 50 */           dwnldService.downloadPackage(pkg);
/* 51 */           File origin = new File(tmp, getFilename());
/* 52 */           if (targetFile[0].exists() && !targetFile[0].delete()) {
/* 53 */             throw new IOException("unable to delete existing target file");
/*    */           }
/* 55 */           if (!origin.renameTo(targetFile[0])) {
/* 56 */             throw new IOException("unable to rename file");
/*    */           }
/*    */         } finally {
/*    */           
/* 60 */           po.close();
/*    */         } 
/* 62 */         Util.executeOnAWTThread(new Runnable()
/*    */             {
/*    */               public void run() {
/* 65 */                 JOptionPane.showMessageDialog(null, labelResource.getMessage("download.completed"));
/*    */               }
/*    */             }true);
/*    */       } else {
/* 69 */         throw new InterruptedException();
/*    */       } 
/*    */     } else {
/* 72 */       throw new FeedbackException(labelResource.getMessage("error.no.such.file"), null);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String getSessionID() {
/* 77 */     return System.getProperty("session.id");
/*    */   }
/*    */   
/*    */   private static String getFilename() {
/* 81 */     return System.getProperty("filename");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\filedwnld\FileDwnld.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */