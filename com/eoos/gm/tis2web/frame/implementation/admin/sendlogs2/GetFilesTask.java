/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.sendlogs2;
/*    */ 
/*    */ import com.eoos.automat.Acceptor;
/*    */ import com.eoos.automat.AcceptorChain;
/*    */ import com.eoos.automat.AcceptorUtil;
/*    */ import com.eoos.automat.StringAcceptor;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.util.FileUtilities;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class GetFilesTask
/*    */   implements Task, Task.ClusterExecution
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   private static final Logger log = Logger.getLogger(GetFilesTask.class);
/*    */   
/*    */   private String[] filenamePatternsInclusion;
/*    */   
/*    */   private String[] filenamePatternsExclusion;
/*    */   
/*    */   public GetFilesTask(String[] filenamePatternsInclusion, String[] filenamePatternExclusion) {
/* 30 */     this.filenamePatternsInclusion = filenamePatternsInclusion;
/* 31 */     this.filenamePatternsExclusion = filenamePatternExclusion;
/*    */   }
/*    */   public Object execute() {
/*    */     try {
/*    */       AcceptorChain acceptorChain1, acceptorChain2;
/* 36 */       Acceptor filenameInclusionAcceptor = Acceptor.ACCEPT_ALL;
/* 37 */       if (this.filenamePatternsInclusion != null && this.filenamePatternsInclusion.length > 0) {
/* 38 */         List<StringAcceptor> acceptors = new LinkedList();
/* 39 */         for (int i = 0; i < this.filenamePatternsInclusion.length; i++) {
/* 40 */           String pattern = this.filenamePatternsInclusion[i];
/* 41 */           if (pattern != null && pattern.trim().length() > 0) {
/* 42 */             acceptors.add(StringAcceptor.create(pattern.trim(), true));
/*    */           }
/*    */         } 
/* 45 */         acceptorChain1 = new AcceptorChain(acceptors, AcceptorChain.TYPE_OR);
/*    */       } 
/*    */       
/* 48 */       Acceptor filenameExclusionAcceptor = Acceptor.ACCEPT_NONE;
/* 49 */       if (this.filenamePatternsExclusion != null && this.filenamePatternsExclusion.length > 0) {
/* 50 */         List<StringAcceptor> acceptors = new LinkedList();
/* 51 */         for (int i = 0; i < this.filenamePatternsExclusion.length; i++) {
/* 52 */           String pattern = this.filenamePatternsExclusion[i];
/* 53 */           if (pattern != null && pattern.trim().length() > 0) {
/* 54 */             acceptors.add(StringAcceptor.create(pattern.trim(), true));
/*    */           }
/*    */         } 
/* 57 */         acceptorChain2 = new AcceptorChain(acceptors, AcceptorChain.TYPE_OR);
/*    */       } 
/*    */       
/* 60 */       final AcceptorChain acceptor = new AcceptorChain(new Acceptor[] { (Acceptor)acceptorChain1, AcceptorUtil.invertAcceptor((Acceptor)acceptorChain2) }, AcceptorChain.TYPE_AND);
/*    */       
/* 62 */       Collection ret = Collections.EMPTY_SET;
/* 63 */       final File logDir = new File(ApplicationContext.getInstance().getProperty("frame.log.dir"));
/* 64 */       if (logDir.exists()) {
/* 65 */         ret = FileUtilities.getFiles(logDir, true, new FileFilter()
/*    */             {
/*    */               public boolean accept(File pathname) {
/* 68 */                 return acceptor.accept(FileUtilities.toRelativeFile(logDir, pathname).getPath());
/*    */               }
/*    */             });
/*    */       }
/*    */ 
/*    */       
/* 74 */       return ret;
/* 75 */     } catch (Exception e) {
/* 76 */       log.error("unable to determine matching files, returning empty set - exception:" + e, e);
/* 77 */       return Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\sendlogs2\GetFilesTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */