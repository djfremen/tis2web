/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.AcceptorChain;
/*     */ import com.eoos.automat.AcceptorUtil;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.util.FileUtilities;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SearchTask
/*     */   implements Task, Task.ClusterExecution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   private static final Logger log = Logger.getLogger(SearchTask.class);
/*     */   
/*     */   private static final long LIMIT = 5242880L;
/*     */   
/*     */   private String[] filenamePatternsInclusion;
/*     */   
/*     */   private String[] filenamePatternsExclusion;
/*     */   
/*     */   private String pattern;
/*     */   
/*     */   public SearchTask(String[] filenamePatternsInclusion, String[] filenamePatternExclusion, String regexp) {
/*  39 */     this.filenamePatternsInclusion = filenamePatternsInclusion;
/*  40 */     this.filenamePatternsExclusion = filenamePatternExclusion;
/*  41 */     this.pattern = regexp;
/*     */   }
/*     */   public Object execute() {
/*     */     try {
/*     */       AcceptorChain acceptorChain1, acceptorChain2;
/*  46 */       Acceptor filenameInclusionAcceptor = Acceptor.ACCEPT_ALL;
/*  47 */       if (this.filenamePatternsInclusion != null && this.filenamePatternsInclusion.length > 0) {
/*  48 */         List<StringAcceptor> acceptors = new LinkedList();
/*  49 */         for (int i = 0; i < this.filenamePatternsInclusion.length; i++) {
/*  50 */           String str = this.filenamePatternsInclusion[i];
/*  51 */           if (str != null && str.trim().length() > 0) {
/*  52 */             acceptors.add(StringAcceptor.create(str.trim(), true));
/*     */           }
/*     */         } 
/*  55 */         acceptorChain1 = new AcceptorChain(acceptors, AcceptorChain.TYPE_OR);
/*     */       } 
/*     */       
/*  58 */       Acceptor filenameExclusionAcceptor = Acceptor.ACCEPT_NONE;
/*  59 */       if (this.filenamePatternsExclusion != null && this.filenamePatternsExclusion.length > 0) {
/*  60 */         List<StringAcceptor> acceptors = new LinkedList();
/*  61 */         for (int i = 0; i < this.filenamePatternsExclusion.length; i++) {
/*  62 */           String str = this.filenamePatternsExclusion[i];
/*  63 */           if (str != null && str.trim().length() > 0) {
/*  64 */             acceptors.add(StringAcceptor.create(str.trim(), true));
/*     */           }
/*     */         } 
/*  67 */         acceptorChain2 = new AcceptorChain(acceptors, AcceptorChain.TYPE_OR);
/*     */       } 
/*     */       
/*  70 */       AcceptorChain acceptorChain3 = new AcceptorChain(new Acceptor[] { (Acceptor)acceptorChain1, AcceptorUtil.invertAcceptor((Acceptor)acceptorChain2) }, AcceptorChain.TYPE_AND);
/*     */       
/*  72 */       Pattern pattern = Pattern.compile(this.pattern, 2);
/*     */       
/*  74 */       Collection<File> ret = new HashSet();
/*  75 */       File logDir = new File(ApplicationContext.getInstance().getProperty("frame.log.dir"));
/*  76 */       if (logDir.exists()) {
/*  77 */         List files = FileUtilities.getFiles(logDir, true);
/*  78 */         for (Iterator<File> iter = files.iterator(); iter.hasNext(); ) {
/*  79 */           File file = iter.next();
/*  80 */           if (file.canRead() && file.length() <= 5242880L) {
/*  81 */             if (acceptorChain3.accept(file.getAbsolutePath()) || acceptorChain3.accept(file.getName())) {
/*  82 */               String content = null;
/*  83 */               FileInputStream fis = new FileInputStream(file);
/*     */               try {
/*  85 */                 content = new String(StreamUtil.readFully(fis), "utf-8");
/*     */               } finally {
/*  87 */                 fis.close();
/*     */               } 
/*  89 */               Matcher matcher = pattern.matcher(content);
/*  90 */               if (matcher.find())
/*  91 */                 ret.add(file); 
/*     */             } 
/*     */             continue;
/*     */           } 
/*  95 */           log.warn("file: " + String.valueOf(file) + " is (currently) either not readable or it is to large, skipping");
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       return ret;
/* 100 */     } catch (Exception e) {
/* 101 */       log.error("unable to determine matching files, returning empty set - exception:" + e, e);
/* 102 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlog\SearchTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */