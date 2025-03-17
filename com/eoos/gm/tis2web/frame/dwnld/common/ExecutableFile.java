/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecutableFile
/*    */   extends DownloadFile
/*    */   implements IDownloadFile.IExecutable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ExecutionInfo executionInfo;
/*    */   
/*    */   public static class ExecutionInfo
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     public ExecutionMode execMode;
/*    */     public String starterType;
/*    */     public String cmdLineParams;
/*    */     public String successCodes;
/*    */     public String failureCodes;
/* 30 */     public String path = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ExecutableFile(long identifier, String name, long size, byte[] checksum, String path, String compression, long compressedSize, ExecutionInfo execInfo) {
/* 36 */     super(identifier, name, size, checksum, path, compression, compressedSize);
/* 37 */     this.executionInfo = execInfo;
/*    */   }
/*    */   
/*    */   public String getCmdLineParams() {
/* 41 */     return this.executionInfo.cmdLineParams;
/*    */   }
/*    */   
/*    */   public ExecutionMode getExecutionMode() {
/* 45 */     return this.executionInfo.execMode;
/*    */   }
/*    */   
/*    */   public String getFailureCodes() {
/* 49 */     return this.executionInfo.failureCodes;
/*    */   }
/*    */   
/*    */   public String getSuccessCodes() {
/* 53 */     return this.executionInfo.successCodes;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 57 */     return this.executionInfo.starterType;
/*    */   }
/*    */   
/*    */   private String getFullName() {
/* 61 */     StringBuffer ret = new StringBuffer();
/* 62 */     if (!Util.isNullOrEmpty(getPath())) {
/* 63 */       ret.append(getPath().replace('\\', '/')).append("/");
/*    */     }
/* 65 */     ret.append(getName());
/* 66 */     StringUtilities.replace(ret, "//", "/");
/*    */     
/* 68 */     return ret.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getExecutablePath() {
/* 73 */     return Util.isNullOrEmpty(this.executionInfo.path) ? getFullName() : this.executionInfo.path;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\ExecutableFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */