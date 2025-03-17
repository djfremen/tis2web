/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.startup;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.AcceptorChain;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirCleaner
/*     */   implements Task, Configurable, Task.LocalExecution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   private static final Logger log = Logger.getLogger(DirCleaner.class);
/*     */   
/*     */   private File directory;
/*     */   
/*     */   private Acceptor fileInclusionAcceptor;
/*     */   
/*     */   private Acceptor fileExclusionAcceptor;
/*     */   
/*  36 */   private long maxAge = -1L;
/*     */   
/*     */   private boolean recursive = true;
/*     */   
/*     */   private Runnable runnable;
/*     */   
/*  42 */   private long period = -1L;
/*     */   
/*     */   public DirCleaner(Configuration configuration) throws Exception {
/*  45 */     log.debug("initializing");
/*  46 */     this.directory = new File(configuration.getProperty("dir"));
/*  47 */     if (!this.directory.exists() || !this.directory.isDirectory()) {
/*  48 */       throw new IllegalArgumentException(String.valueOf(this.directory));
/*     */     }
/*  50 */     log.debug("...directory: " + String.valueOf(this.directory));
/*     */     try {
/*  52 */       this.recursive = (new TypeDecorator(configuration)).getBoolean("recursive").booleanValue();
/*  53 */     } catch (Exception e) {
/*  54 */       log.warn("...missing or wrong configuration parameter 'recursive', using default");
/*     */     } 
/*  56 */     log.debug("...recurive: " + this.recursive);
/*     */     
/*     */     try {
/*  59 */       this.maxAge = (new TypeDecorator(configuration)).getNumber("max.age").longValue() * 24L * 60L * 60L * 1000L;
/*  60 */     } catch (Exception e) {
/*  61 */       log.warn("...missing or wrong configuration parameter 'max.age', using default");
/*     */     } 
/*  63 */     log.debug("...max.age: " + this.maxAge);
/*     */     
/*  65 */     List<StringAcceptor> inclusionAcceptors = new LinkedList();
/*  66 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "include.pattern.");
/*  67 */     for (Iterator<String> iter = subConfigurationWrapper1.getKeys().iterator(); iter.hasNext(); ) {
/*  68 */       String includePattern = subConfigurationWrapper1.getProperty(iter.next());
/*  69 */       includePattern = includePattern.replace('/', File.separatorChar);
/*  70 */       includePattern = includePattern.replace('\\', File.separatorChar);
/*  71 */       log.debug("... include pattern:" + includePattern);
/*  72 */       inclusionAcceptors.add(StringAcceptor.create(includePattern, true));
/*     */     } 
/*  74 */     this.fileInclusionAcceptor = (Acceptor)new AcceptorChain(inclusionAcceptors, AcceptorChain.TYPE_OR);
/*     */     
/*  76 */     List<StringAcceptor> exclusionAcceptors = new LinkedList();
/*  77 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper(configuration, "exclude.pattern.");
/*  78 */     for (Iterator<String> iterator1 = subConfigurationWrapper2.getKeys().iterator(); iterator1.hasNext(); ) {
/*  79 */       String excludePattern = subConfigurationWrapper2.getProperty(iterator1.next());
/*  80 */       excludePattern = excludePattern.replace('/', File.separatorChar);
/*  81 */       excludePattern = excludePattern.replace('\\', File.separatorChar);
/*  82 */       log.debug("... exclude pattern:" + excludePattern);
/*  83 */       exclusionAcceptors.add(StringAcceptor.create(excludePattern, true));
/*     */     } 
/*  85 */     this.fileExclusionAcceptor = (Acceptor)new AcceptorChain(exclusionAcceptors, AcceptorChain.TYPE_OR);
/*     */     
/*  87 */     this.runnable = new Runnable()
/*     */       {
/*     */         private void clean(File dir) {
/*  90 */           File[] files = dir.listFiles(new FileFilter()
/*     */               {
/*     */                 public boolean accept(File file) {
/*  93 */                   if (file.isDirectory()) {
/*  94 */                     return true;
/*     */                   }
/*  96 */                   boolean ret = DirCleaner.this.fileInclusionAcceptor.accept(file.getAbsolutePath());
/*  97 */                   ret = (ret && !DirCleaner.this.fileExclusionAcceptor.accept(file.getAbsolutePath()));
/*  98 */                   if (DirCleaner.this.maxAge != -1L) {
/*  99 */                     long actualAge = System.currentTimeMillis() - file.lastModified();
/* 100 */                     ret = (ret && actualAge > DirCleaner.this.maxAge);
/*     */                   } 
/* 102 */                   if (!ret) {
/* 103 */                     DirCleaner.log.debug("...excluding file: " + String.valueOf(file));
/*     */                   }
/* 105 */                   return ret;
/*     */                 }
/*     */               });
/*     */           
/* 109 */           if (files != null) {
/* 110 */             for (int i = 0; i < files.length; i++) {
/* 111 */               if (files[i].isFile()) {
/* 112 */                 DirCleaner.log.debug("...deleting file: " + String.valueOf(files[i]));
/* 113 */                 if (!files[i].delete()) {
/* 114 */                   DirCleaner.log.warn("...unable to delete file");
/*     */                 } else {
/* 116 */                   DirCleaner.log.debug("......successful");
/*     */                 } 
/*     */               } else {
/* 119 */                 clean(files[i]);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 123 */             DirCleaner.log.debug("no files found !");
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void run() {
/*     */           try {
/* 130 */             DirCleaner.log.info("cleaning directory " + String.valueOf(DirCleaner.this.directory));
/* 131 */             clean(DirCleaner.this.directory);
/* 132 */           } catch (Exception e) {
/* 133 */             DirCleaner.log.error("...unable to clean directory - exception: " + e, e);
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 141 */       String tmp = configuration.getProperty("periodic.exec");
/* 142 */       this.period = Util.parseMillis(tmp);
/* 143 */     } catch (Exception e) {
/* 144 */       log.warn("...missing or wrong configuration parameter 'periodic.exec', assuming one time execution");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object execute() {
/* 151 */     if (this.period != -1L) {
/* 152 */       log.debug("scheduling for periodic execution - period is: " + this.period + " ms");
/*     */       
/* 154 */       Util.getTimer().scheduleAtFixedRate(Util.createTimerTask(this.runnable), 0L, this.period);
/*     */     } else {
/* 156 */       log.debug("executing");
/* 157 */       this.runnable.run();
/*     */     } 
/* 159 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\startup\DirCleaner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */