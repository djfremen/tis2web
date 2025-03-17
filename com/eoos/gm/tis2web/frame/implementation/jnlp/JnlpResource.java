/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.net.URL;
/*    */ import javax.servlet.ServletContext;
/*    */ import jnlp.sample.servlet.JnlpResource;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class JnlpResource extends JnlpResource {
/*    */   protected URL url;
/*    */   
/*    */   public JnlpResource(ServletContext servletContext, String name, String versionId, String[] osList, String[] archList, String[] localeList, String path, String returnVersionId, long timestamp, String encoding, boolean pack200) {
/* 13 */     super(servletContext, name, versionId, osList, archList, localeList, path, returnVersionId, encoding);
/* 14 */     this.timestamp = timestamp;
/* 15 */     this.url = DatabaseResourceURL.makeDatabaseResourceURL(this, pack200);
/*    */   }
/*    */   protected long timestamp;
/*    */   public URL getResource() {
/* 19 */     return this.url;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 23 */     return this.url.toString();
/*    */   }
/*    */   
/*    */   public long getLastModified() {
/* 27 */     return this.timestamp;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\JnlpResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */