/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.StreamingTask;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.server.DwnldServiceServer;
/*    */ import com.eoos.scsm.v2.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetDataTask
/*    */   implements StreamingTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private SoftwareKey swk;
/*    */   private Lease lease;
/*    */   private DownloadFile file;
/* 25 */   private transient OutputStream os = null;
/*    */   
/*    */   public GetDataTask(SoftwareKey swk, Lease lease, DownloadFile file, OutputStream os) {
/* 28 */     this.swk = swk;
/* 29 */     this.lease = lease;
/* 30 */     this.file = file;
/* 31 */     this.os = os;
/*    */   }
/*    */   
/*    */   public void execute(OutputStream os) throws Exception {
/* 35 */     GZIPOutputStream gzos = null;
/*    */     try {
/* 37 */       if (!this.file.gzipCompressed()) {
/* 38 */         gzos = new GZIPOutputStream(os, 50000);
/* 39 */         os = gzos;
/*    */       } 
/* 41 */       DwnldServiceServer.getInstance(this.swk, this.lease).getData(this.file, os);
/*    */     } finally {
/* 43 */       if (gzos != null) {
/* 44 */         gzos.finish();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handleResult(InputStream is) throws IOException {
/* 50 */     if (this.os != null) {
/* 51 */       StreamUtil.transfer(new GZIPInputStream(is, 50000), this.os);
/*    */     } else {
/* 53 */       throw new IllegalStateException("output stream is null");
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 58 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\GetDataTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */