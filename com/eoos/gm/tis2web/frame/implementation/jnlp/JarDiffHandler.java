/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import javax.servlet.ServletContext;
/*    */ import jnlp.sample.servlet.DownloadRequest;
/*    */ import jnlp.sample.servlet.DownloadResponse;
/*    */ import jnlp.sample.servlet.JarDiffHandler;
/*    */ import jnlp.sample.servlet.JnlpResource;
/*    */ import jnlp.sample.servlet.Logger;
/*    */ import jnlp.sample.servlet.ResourceCatalog;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class JarDiffHandler extends JarDiffHandler {
/*    */   public JarDiffHandler(ServletContext servletContext, Logger logger) throws Exception {
/* 15 */     super(servletContext, logger);
/*    */   }
/*    */   
/*    */   public synchronized DownloadResponse getJarDiffEntry(ResourceCatalog catalog, DownloadRequest dreq, JnlpResource res) {
/* 19 */     return super.getJarDiffEntry(catalog, dreq, res);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\JarDiffHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */