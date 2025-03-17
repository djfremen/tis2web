/*    */ package com.eoos.gm.tis2web.swdl.common.domain.application;
/*    */ 
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class File
/*    */   extends FileProxy
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   private Integer pageNo = null;
/*    */   
/* 21 */   private Integer blockNo = null;
/*    */   
/* 23 */   private String memoryAddr = null;
/*    */   
/* 25 */   private Integer blockCount = null;
/*    */   
/* 27 */   private byte[] content = null;
/*    */ 
/*    */   
/*    */   public File(Integer pageNo, Integer blockNo, String memoryAddr, Integer blockCount, byte[] content, FileProxy file) {
/* 31 */     super((String)file.getIdentifier(), file.getName(), file.getRevision(), file.getSize(), file.getStatus(), file.getType(), file.getFingerprint(), file.getVersion());
/* 32 */     this.pageNo = pageNo;
/* 33 */     this.blockNo = blockNo;
/* 34 */     this.memoryAddr = memoryAddr;
/* 35 */     this.blockCount = blockCount;
/* 36 */     this.content = content;
/*    */   }
/*    */   
/*    */   public Integer getPageNo() {
/* 40 */     return this.pageNo;
/*    */   }
/*    */   
/*    */   public Integer getBlockNo() {
/* 44 */     return this.blockNo;
/*    */   }
/*    */   
/*    */   public String getMemoryAddr() {
/* 48 */     return this.memoryAddr;
/*    */   }
/*    */   
/*    */   public Integer getBlockCount() {
/* 52 */     return this.blockCount;
/*    */   }
/*    */   
/*    */   public byte[] getContent() {
/* 56 */     return this.content;
/*    */   }
/*    */   
/*    */   public void setContent(byte[] content) {
/* 60 */     this.content = content;
/*    */   }
/*    */   
/*    */   public boolean checkFingerprint() {
/*    */     try {
/* 65 */       String md5Content = StringUtilities.bytesToHex(MessageDigest.getInstance("MD5").digest(this.content));
/* 66 */       return md5Content.equals(getFingerprint());
/* 67 */     } catch (Exception e) {
/* 68 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\application\File.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */