/*     */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.GroupRI;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassifierRI;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DownloadServiceUtil
/*     */ {
/*  23 */   public static final Classifier GDS = (Classifier)new ClassifierRI("GDS");
/*     */   
/*  25 */   public static final Classifier SPS = (Classifier)new ClassifierRI("SPS");
/*     */   
/*  27 */   public static final List APPLICATION_CLASSIFIERS = Arrays.asList(new Classifier[] { GDS, SPS });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IDownloadUnit getNewestVersion(Collection downloadUnits) {
/*  34 */     IDownloadUnit ret = null;
/*  35 */     for (Iterator<IDownloadUnit> iter = downloadUnits.iterator(); iter.hasNext(); ) {
/*  36 */       IDownloadUnit current = iter.next();
/*  37 */       if (ret == null || Util.isHigher((Comparable)current.getVersionNumber(), (Comparable)ret.getVersionNumber())) {
/*  38 */         ret = current;
/*     */       }
/*     */     } 
/*  41 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GroupingResult groupUnits(Collection downloadUnits) {
/*  52 */     Map<Object, Object> map = new HashMap<Object, Object>();
/*  53 */     for (Iterator<IDownloadUnit> iter = downloadUnits.iterator(); iter.hasNext(); ) {
/*  54 */       IDownloadUnit unit = iter.next();
/*  55 */       Collection classifiers = unit.getClassfiers();
/*  56 */       Collection<IDownloadUnit> group = (Collection)map.get(classifiers);
/*  57 */       if (group == null) {
/*  58 */         group = new LinkedHashSet();
/*  59 */         map.put(classifiers, group);
/*     */       } 
/*  61 */       group.add(unit);
/*     */     } 
/*     */     
/*  64 */     final List tmp = new ArrayList(map.values());
/*     */     
/*  66 */     return new GroupingResult()
/*     */       {
/*     */         public int getGroupCount() {
/*  69 */           return tmp.size();
/*     */         }
/*     */         
/*     */         public Group getGroup(int i) {
/*  73 */           return (Group)GroupRI.createGroup(tmp.get(i));
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static String getDenotation(Collection downloadUnits, Locale locale) {
/*  79 */     return getNewestVersion(downloadUnits).getDescripition(locale);
/*     */   }
/*     */   
/*     */   public static interface ExecutableAccess {
/*     */     IDownloadFile getAsFile();
/*     */     
/*     */     IDownloadFile.IExecutable getAsExecutable();
/*     */   }
/*     */   
/*     */   public static ExecutableAccess getExecutable(IDownloadUnit unit) {
/*  89 */     ExecutableAccess ret = null;
/*  90 */     for (Iterator<IDownloadFile> iter = unit.getFiles().iterator(); iter.hasNext() && ret == null; ) {
/*  91 */       final IDownloadFile file = iter.next();
/*  92 */       if (file instanceof IDownloadFile.IExecutable) {
/*  93 */         ret = new ExecutableAccess()
/*     */           {
/*     */             public IDownloadFile getAsFile() {
/*  96 */               return file;
/*     */             }
/*     */             
/*     */             public IDownloadFile.IExecutable getAsExecutable() {
/* 100 */               return (IDownloadFile.IExecutable)file;
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/* 105 */     return ret;
/*     */   } public static interface GroupingResult {
/*     */     int getGroupCount(); Group getGroup(int param1Int); }
/*     */   public static String getCmdLine(ExecutableAccess file, File destDir) {
/* 109 */     StringBuffer ret = new StringBuffer();
/*     */     
/* 111 */     File targetFile = new File(destDir, file.getAsExecutable().getExecutablePath());
/* 112 */     String cmdLineParams = file.getAsExecutable().getCmdLineParams();
/* 113 */     if (Util.isNullOrEmpty(cmdLineParams)) {
/* 114 */       cmdLineParams = "";
/*     */     }
/* 116 */     if ("exe".equalsIgnoreCase(file.getAsExecutable().getType())) {
/* 117 */       ret.append("cmd.exe /C " + targetFile.getAbsolutePath()).append(" ").append(cmdLineParams);
/* 118 */     } else if ("java".equalsIgnoreCase(file.getAsExecutable().getType())) {
/* 119 */       ret.append("java " + cmdLineParams + " -jar " + targetFile.toString());
/* 120 */     } else if ("msi".equalsIgnoreCase(file.getAsExecutable().getType())) {
/* 121 */       ret.append("msiexec ").append(targetFile.getAbsolutePath()).append(" ").append(cmdLineParams);
/*     */     } 
/* 123 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public static boolean isFailureCode(int exitStatus, IDownloadFile.IExecutable executable) {
/* 127 */     String failureCodes = executable.getFailureCodes();
/* 128 */     if (!Util.isNullOrEmpty(failureCodes)) {
/* 129 */       return Util.parseList(failureCodes).contains(String.valueOf(exitStatus));
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSuccessCode(int exitStatus, IDownloadFile.IExecutable executable) {
/* 136 */     String codes = executable.getSuccessCodes();
/* 137 */     if (!Util.isNullOrEmpty(codes)) {
/* 138 */       return Util.parseList(codes).contains(String.valueOf(exitStatus));
/*     */     }
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Classifier createClassifier(String classifier) {
/* 145 */     return (Classifier)new ClassifierRI(classifier);
/*     */   }
/*     */   
/*     */   public static DwnldFilter createClassficationFilter(String classification) {
/* 149 */     return (DwnldFilter)ClassificationFilter.create(classification);
/*     */   }
/*     */   
/*     */   public static List toGDSFilter(DwnldFilter filter) {
/* 153 */     List<ClassificationFilter> ret = new LinkedList();
/* 154 */     ret.add(ClassificationFilter.GDS);
/* 155 */     ret.add(filter);
/* 156 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\DownloadServiceUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */