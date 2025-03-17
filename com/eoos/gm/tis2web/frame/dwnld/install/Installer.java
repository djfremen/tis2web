/*     */ package com.eoos.gm.tis2web.frame.dwnld.install;
/*     */ 
/*     */ import com.eoos.datatype.IVersionNumber;
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceUtil;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Group;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.install.ui.SelectionDialog;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.install.ui.SummaryPanel;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.filter.InversionFacade;
/*     */ import com.eoos.scsm.v2.filter.LinkedFilter;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Installer
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(Installer.class);
/*     */   
/*  48 */   private File homeDir = null;
/*     */   
/*     */   private IDownloadService downloadService;
/*     */   
/*  52 */   public static final String RESULT_SUCCESS = new String("0");
/*     */   
/*  54 */   public static final String RESULT_FAILURE = new String("-1");
/*     */   
/*  56 */   public static final String RESULT_UNKNOWN = new String("999");
/*     */   private Callback callback;
/*     */   
/*     */   private static final class DownloadUnitWrapper
/*     */     implements IDownloadUnit {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final Collection classifiers;
/*     */     private final IDownloadUnit du;
/*     */     
/*     */     private DownloadUnitWrapper(Collection classifiers, IDownloadUnit du) {
/*  66 */       this.classifiers = classifiers;
/*  67 */       this.du = du;
/*     */     }
/*     */     
/*     */     public IVersionNumber getVersionNumber() {
/*  71 */       return this.du.getVersionNumber();
/*     */     }
/*     */     
/*     */     public long getTotalBytes() {
/*  75 */       return this.du.getTotalBytes();
/*     */     }
/*     */     
/*     */     public long getReleaseDate() {
/*  79 */       return this.du.getReleaseDate();
/*     */     }
/*     */     
/*     */     public IInstalledVersionLookup getInstalledVersionLookup() {
/*  83 */       return this.du.getInstalledVersionLookup();
/*     */     }
/*     */     
/*     */     public long getIdentifier() {
/*  87 */       return this.du.getIdentifier();
/*     */     }
/*     */     
/*     */     public Collection getFiles() {
/*  91 */       return this.du.getFiles();
/*     */     }
/*     */     
/*     */     public String getDescripition(Locale locale) {
/*  95 */       return this.du.getDescripition(locale);
/*     */     }
/*     */     
/*     */     public Collection getClassfiers() {
/*  99 */       return this.classifiers;
/*     */     }
/*     */     
/*     */     public IDownloadUnit getWrappedUnit() {
/* 103 */       return this.du;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/* 107 */       if (this == obj)
/* 108 */         return true; 
/* 109 */       if (obj instanceof DownloadUnitWrapper) {
/* 110 */         return this.du.equals(((DownloadUnitWrapper)obj).getWrappedUnit());
/*     */       }
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 117 */       return this.du.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 121 */       return "Wrapper for " + this.du.toString();
/*     */     }
/*     */     
/*     */     public Collection getDownloadServers() {
/* 125 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Callback
/*     */     extends IInstalledVersionLookup.IRegistryLookup
/*     */   {
/* 136 */     public static final Mode UPDATE_VERSIONS_ONLY = new Mode(); public static final class Mode {
/*     */       private Mode() {} }
/* 138 */     public static final Mode ALL_VERSIONS = new Mode();
/*     */     
/*     */     String getSessionID();
/*     */     
/*     */     JComponent getParentComponent();
/*     */     
/*     */     Locale getLocale();
/*     */     
/*     */     Mode getMode();
/*     */     
/*     */     String getLabel(String param1String); }
/*     */   
/*     */   public static final class Mode {
/*     */     private Mode() {}
/*     */   }
/*     */   
/*     */   public Installer(Callback callback, File dir) {
/* 155 */     this.callback = callback;
/* 156 */     this.homeDir = dir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processInstall(Collection softwareClassifiers) {
/* 161 */     log.debug("executing driver installation (check) ...");
/* 162 */     this.downloadService = DownloadServiceFactory.createInstance((SoftwareKey)new SessionKey(this.callback.getSessionID()), null, this.homeDir, null);
/*     */     
/* 164 */     Collection units = this.downloadService.getDownloadUnits(softwareClassifiers);
/* 165 */     if (!Util.isNullOrEmpty(units)) {
/* 166 */       log.debug("...retrieved " + units.size() + " units: " + units);
/* 167 */       Collection<IDownloadUnit> units2 = new LinkedList();
/*     */       
/* 169 */       for (Iterator<IDownloadUnit> iter = units.iterator(); iter.hasNext();) {
/* 170 */         units2.add(removeApplicationClassifier(iter.next()));
/*     */       }
/*     */       
/* 173 */       log.debug("...grouping units");
/* 174 */       DownloadServiceUtil.GroupingResult result = DownloadServiceUtil.groupUnits(units2);
/* 175 */       log.debug("...found " + result.getGroupCount() + " groups");
/* 176 */       final Map<Object, Object> groupsToInstallableVersionList = new HashMap<Object, Object>();
/* 177 */       final Map<Object, Object> groupToInstalledVersion = new HashMap<Object, Object>();
/* 178 */       for (int i = 0; i < result.getGroupCount(); i++) {
/* 179 */         LinkedFilter linkedFilter; Group group = result.getGroup(i);
/* 180 */         log.debug("...determining available/installable versions for group: " + String.valueOf(group) + "...");
/* 181 */         List<InversionFacade> filters = new LinkedList();
/*     */         
/* 183 */         final IVersionNumber installedVersion = group.getInstalledVersion(this.callback);
/* 184 */         if (installedVersion != null) {
/* 185 */           groupToInstalledVersion.put(group, installedVersion);
/* 186 */           filters.add(new InversionFacade(new Filter()
/*     */                 {
/*     */                   public boolean include(Object obj) {
/* 189 */                     return installedVersion.equals(obj);
/*     */                   }
/*     */                 }));
/*     */         } 
/* 193 */         log.debug("......determined installed version: " + String.valueOf(installedVersion));
/*     */         
/* 195 */         if (this.callback.getMode() == Callback.UPDATE_VERSIONS_ONLY) {
/* 196 */           filters.add(new Group.UpdateVersionFilter(installedVersion));
/*     */         }
/*     */         
/* 199 */         Filter filter = null;
/* 200 */         if (!Util.isNullOrEmpty(filters)) {
/* 201 */           linkedFilter = new LinkedFilter(filters, LinkedFilter.AND);
/*     */         }
/* 203 */         List<?> ret = new LinkedList(group.getVersions((Filter)linkedFilter));
/* 204 */         log.debug("...available/installable versions are: " + String.valueOf(ret));
/* 205 */         Collections.sort(ret, Util.reverseComparator(Util.NATURAL_COMPARATOR));
/*     */         
/* 207 */         if (ret.size() > 0) {
/* 208 */           groupsToInstallableVersionList.put(group, ret);
/*     */         }
/*     */       } 
/*     */       
/* 212 */       if (groupsToInstallableVersionList.size() > 0) {
/* 213 */         log.debug("...showing selection dialog");
/* 214 */         final List<?> groups = new LinkedList(groupsToInstallableVersionList.keySet());
/* 215 */         Collections.sort(groups, (Comparator<?>)new Group.DescriptionComparator(this.callback.getLocale()));
/*     */         
/* 217 */         SelectionDialog.Callback callback = new SelectionDialog.Callback()
/*     */           {
/*     */             public SummaryPanel.Callback onInstall(SelectionDialog.Callback.SelectionCallback callback, UIUtil.ProgressObserver observer) throws Exception {
/* 220 */               return Installer.this.onInstall(callback, observer);
/*     */             }
/*     */             
/*     */             public String getLabel(String key) {
/* 224 */               return Installer.this.callback.getLabel(key);
/*     */             }
/*     */             
/*     */             public String getErrorMessage(Exception e) {
/* 228 */               return Installer.this.callback.getLabel("error.occurred") + ":" + e.getMessage();
/*     */             }
/*     */             
/*     */             public List getAvailableVersions(Group group) {
/* 232 */               return (List)groupsToInstallableVersionList.get(group);
/*     */             }
/*     */             
/*     */             public String getDescription(Group group) {
/* 236 */               return group.getDescription(Installer.this.callback.getLocale());
/*     */             }
/*     */             
/*     */             public List getGroups() {
/* 240 */               return groups;
/*     */             }
/*     */             
/*     */             public IVersionNumber getInstalledVersion(Group group) {
/* 244 */               return (IVersionNumber)groupToInstalledVersion.get(group);
/*     */             }
/*     */           };
/*     */         
/* 248 */         SelectionDialog.execute(this.callback.getParentComponent(), callback);
/*     */       } else {
/*     */         
/* 251 */         log.debug("...no software updates found");
/*     */       } 
/*     */     } else {
/* 254 */       log.debug("...no software versions found");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IDownloadUnit removeApplicationClassifier(IDownloadUnit du) {
/* 259 */     Collection classifiers = new HashSet(du.getClassfiers());
/* 260 */     classifiers.removeAll(DownloadServiceUtil.APPLICATION_CLASSIFIERS);
/*     */     
/* 262 */     return new DownloadUnitWrapper(classifiers, du);
/*     */   }
/*     */   
/*     */   private String install(LinkedHashSet<IDownloadUnit> identifier, UIUtil.ProgressObserver observer) throws Exception {
/* 266 */     String result = RESULT_FAILURE;
/*     */     
/* 268 */     IDownloadUnit currentUnit = identifier.iterator().next();
/*     */     
/* 270 */     observer.setProgress(this.callback.getLabel("retrieving.data"));
/*     */ 
/*     */     
/* 273 */     File destDir = Util.createTmpDir("wrk_" + currentUnit.getIdentifier());
/*     */     try {
/* 275 */       log.debug("...starting data download");
/* 276 */       IDownloadPackage ipackage = this.downloadService.createPackage(Collections.singleton(currentUnit), destDir);
/*     */       
/*     */       try {
/* 279 */         final Object[] signal = { null };
/* 280 */         synchronized (signal) {
/* 281 */           this.downloadService.startOrResumeDwnld(ipackage, new IDownloadService.DownloadObserver()
/*     */               {
/*     */                 public void onFinished(IDownloadPackage pkg, IDownloadUnit unit, IDownloadFile file) {}
/*     */ 
/*     */                 
/*     */                 public void onFinished(IDownloadPackage pkg, IDownloadUnit unit) {}
/*     */ 
/*     */                 
/*     */                 public void onFinished(IDownloadPackage pkg) {
/* 290 */                   synchronized (signal) {
/* 291 */                     signal.notify();
/*     */                   } 
/*     */                 }
/*     */                 
/*     */                 public void onError(IDownloadPackage pkg, Exception e) {
/* 296 */                   synchronized (signal) {
/* 297 */                     signal[0] = e;
/* 298 */                     signal.notify();
/*     */                   } 
/*     */                 }
/*     */               });
/* 302 */           signal.wait();
/*     */         } 
/* 304 */         log.debug("...download finished");
/* 305 */         if (signal[0] != null) {
/* 306 */           log.debug("...rethrowing signaled exception: " + (Exception)signal[0], (Exception)signal[0]);
/* 307 */           throw (Exception)signal[0];
/*     */         } 
/*     */         
/* 310 */         Util.checkInterruption();
/*     */         
/* 312 */         observer.setProgress(this.callback.getLabel("executing.installer"));
/*     */         
/* 314 */         log.debug("...executing installer");
/*     */ 
/*     */         
/* 317 */         DownloadServiceUtil.ExecutableAccess ea = DownloadServiceUtil.getExecutable(currentUnit);
/* 318 */         if (ea == null) {
/* 319 */           throw new IllegalStateException("no starter information found");
/*     */         }
/* 321 */         String cmdLine = DownloadServiceUtil.getCmdLine(ea, destDir);
/*     */ 
/*     */         
/* 324 */         log.debug("...executing cmd: " + cmdLine + " in working directory: " + String.valueOf(destDir));
/* 325 */         Process process = Runtime.getRuntime().exec(cmdLine, (String[])null, destDir);
/* 326 */         process.getOutputStream().close();
/* 327 */         Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getInputStream()), System.out));
/* 328 */         Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getErrorStream()), System.err));
/*     */         
/* 330 */         log.debug("...waiting for termination");
/*     */         
/* 332 */         int status = process.waitFor();
/* 333 */         log.debug("...installer terminated, evaluating return code " + status);
/* 334 */         if (DownloadServiceUtil.isFailureCode(status, ea.getAsExecutable())) {
/* 335 */           result = RESULT_FAILURE;
/* 336 */           log.debug("...return code: installation failed");
/* 337 */         } else if (DownloadServiceUtil.isSuccessCode(status, ea.getAsExecutable())) {
/* 338 */           result = RESULT_SUCCESS;
/* 339 */           log.debug("...return code: installation succeeded");
/*     */         } else {
/* 341 */           result = RESULT_UNKNOWN;
/* 342 */           log.debug("...unknown return code");
/*     */         }
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 348 */         log.debug("dropping dwnld package");
/* 349 */         this.downloadService.dropPackage(ipackage, true);
/*     */       } 
/*     */     } finally {
/* 352 */       log.debug("deleting tmp dir ");
/* 353 */       if (!Util.deleteDir(destDir)) {
/* 354 */         log.warn("could not delete tmp dir " + destDir);
/*     */       }
/*     */     } 
/*     */     
/* 358 */     return result;
/*     */   }
/*     */   
/*     */   private SummaryPanel.Callback onInstall(SelectionDialog.Callback.SelectionCallback callback, UIUtil.ProgressObserver observer) throws Exception {
/* 362 */     log.debug("...executing installation...");
/* 363 */     List<?> groups = new LinkedList(callback.getSelectedGroups());
/* 364 */     Collections.sort(groups, (Comparator<?>)new Group.DescriptionComparator(this.callback.getLocale()));
/*     */     
/* 366 */     final Map<Object, Object> resultMap = new HashMap<Object, Object>();
/*     */     
/* 368 */     for (Iterator<?> iter = groups.iterator(); iter.hasNext(); ) {
/* 369 */       Group group = (Group)iter.next();
/* 370 */       log.debug("...current group: " + group);
/*     */       
/* 372 */       IDownloadUnit unit = ((DownloadUnitWrapper)group.getDownloadUnit(callback.getSelectedVersion(group))).getWrappedUnit();
/* 373 */       log.debug("...selected unit: " + unit);
/*     */       
/* 375 */       IVersionNumber version = unit.getVersionNumber();
/* 376 */       LinkedHashSet id = createIdentifier(unit, version);
/*     */       
/*     */       try {
/* 379 */         resultMap.put(id, install(id, observer));
/* 380 */         log.debug("...successfully executed installation");
/* 381 */       } catch (InterruptedException e) {
/* 382 */         throw e;
/* 383 */       } catch (Exception e) {
/* 384 */         log.error("unable to execute installer for :" + unit + ", " + version + ", skipping - exception:" + e, e);
/* 385 */         resultMap.put(id, RESULT_FAILURE);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 390 */     log.debug("...creating summary panel callback");
/* 391 */     SummaryPanel.Callback summaryCallback = new SummaryPanel.Callback() {
/*     */         public IVersionNumber getSelectedVersion(IDownloadUnit unit) {
/* 393 */           for (Iterator<Map.Entry> iter = resultMap.entrySet().iterator(); iter.hasNext(); ) {
/* 394 */             Map.Entry next = iter.next();
/* 395 */             LinkedHashSet id = (LinkedHashSet)next.getKey();
/* 396 */             Iterator<IDownloadUnit> idIter = id.iterator();
/* 397 */             IDownloadUnit _unit = idIter.next();
/* 398 */             if (unit.getVersionNumber().equals(_unit.getVersionNumber())) {
/* 399 */               return (IVersionNumber)idIter.next();
/*     */             }
/*     */           } 
/* 402 */           return null;
/*     */         }
/*     */         
/*     */         public String getDescriptionOfSelectedVersion(IDownloadUnit unit) {
/* 406 */           for (Iterator<Map.Entry> iter = resultMap.entrySet().iterator(); iter.hasNext(); ) {
/* 407 */             Map.Entry next = iter.next();
/* 408 */             LinkedHashSet id = (LinkedHashSet)next.getKey();
/* 409 */             Iterator<IDownloadUnit> idIter = id.iterator();
/* 410 */             IDownloadUnit _unit = idIter.next();
/* 411 */             if (unit.getVersionNumber().equals(_unit.getVersionNumber())) {
/* 412 */               return _unit.getDescripition(getLocale());
/*     */             }
/*     */           } 
/* 415 */           return null;
/*     */         }
/*     */         
/*     */         public String getLabel(String key) {
/* 419 */           return Installer.this.callback.getLabel(key);
/*     */         }
/*     */         
/*     */         public Set getInstalledSoftware() {
/* 423 */           Set<IDownloadUnit> set = new HashSet();
/* 424 */           for (Iterator<Map.Entry> iter = resultMap.entrySet().iterator(); iter.hasNext(); ) {
/* 425 */             Map.Entry next = iter.next();
/* 426 */             LinkedHashSet<IDownloadUnit> id = (LinkedHashSet)next.getKey();
/*     */             
/* 428 */             IDownloadUnit unit = id.iterator().next();
/* 429 */             Collection cClsCurUnit = unit.getClassfiers();
/* 430 */             boolean addThisUnit = true;
/* 431 */             for (Iterator<IDownloadUnit> it = set.iterator(); it.hasNext();) {
/* 432 */               if (cClsCurUnit.equals(((IDownloadUnit)it.next()).getClassfiers())) {
/* 433 */                 addThisUnit = false;
/*     */               }
/*     */             } 
/* 436 */             if (addThisUnit)
/*     */             {
/*     */               
/* 439 */               set.add(unit);
/*     */             }
/*     */           } 
/* 442 */           return set;
/*     */         }
/*     */         
/*     */         public String getInstallationResult(IDownloadUnit unit, IVersionNumber version) {
/* 446 */           String result = (String)resultMap.get(Installer.this.createIdentifier(unit, version));
/* 447 */           return (result != null) ? result : "999";
/*     */         }
/*     */         
/*     */         public Locale getLocale() {
/* 451 */           return Installer.this.callback.getLocale();
/*     */         }
/*     */       };
/*     */     
/* 455 */     return summaryCallback;
/*     */   }
/*     */   
/*     */   private LinkedHashSet createIdentifier(IDownloadUnit unit, IVersionNumber version) {
/* 459 */     return CollectionUtil.toSet(new Object[] { unit, version });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\install\Installer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */