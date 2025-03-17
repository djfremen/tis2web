/*    */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SDFileSelImpl
/*    */   implements SDFileSel
/*    */ {
/* 18 */   private Logger log = Logger.getLogger(SDFileSelImpl.class);
/* 19 */   private Hashtable sdFiles = null;
/* 20 */   private int fileID = 0;
/* 21 */   private byte[] buffer = null;
/* 22 */   private SDAppLoader appLoader = null;
/* 23 */   private int bufferPos = 0;
/*    */   
/*    */   public SDFileSelImpl(SDAppLoader appL) {
/* 26 */     this.appLoader = appL;
/* 27 */     this.sdFiles = this.appLoader.getFileID2Name();
/* 28 */     if (this.sdFiles.keys().hasMoreElements()) {
/* 29 */       this.fileID = ((Integer)this.sdFiles.keys().nextElement()).intValue();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int getNextFileID(int fID) {
/* 38 */     Enumeration<Integer> enumFIDs = this.sdFiles.keys();
/* 39 */     while (enumFIDs.hasMoreElements()) {
/* 40 */       int fIDNext = ((Integer)enumFIDs.nextElement()).intValue();
/* 41 */       if (fIDNext == fID && 
/* 42 */         enumFIDs.hasMoreElements())
/* 43 */         return ((Integer)enumFIDs.nextElement()).intValue(); 
/*    */     } 
/* 45 */     return 0;
/*    */   }
/*    */   
/*    */   private byte[] getDataFromBuffer(int size) {
/* 49 */     byte[] bufData = null;
/* 50 */     if (size >= this.buffer.length - this.bufferPos) {
/* 51 */       bufData = new byte[this.buffer.length];
/* 52 */       System.arraycopy(this.buffer, this.bufferPos, bufData, 0, this.buffer.length - this.bufferPos);
/* 53 */       this.bufferPos = 0;
/* 54 */       this.buffer = null;
/*    */     } else {
/* 56 */       bufData = new byte[size];
/* 57 */       System.arraycopy(this.buffer, this.bufferPos, bufData, 0, size);
/* 58 */       this.bufferPos += size;
/*    */     } 
/* 60 */     return bufData;
/*    */   }
/*    */   
/*    */   public byte[] getNextFile(int size) {
/* 64 */     byte[] data = null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     if (this.buffer != null) {
/* 70 */       data = getDataFromBuffer(size);
/*    */     } else {
/* 72 */       this.buffer = this.appLoader.getFileData(Integer.valueOf(this.fileID));
/* 73 */       data = getDataFromBuffer(size);
/* 74 */       this.fileID = getNextFileID(this.fileID);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     this.log.debug("get next file buffer(" + size + ") from fileID:" + Integer.toString(this.fileID) + ",fileName:" + (String)this.sdFiles.get(Integer.valueOf(this.fileID)));
/* 81 */     return data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean Seek(int appPos, int fID) {
/* 89 */     this.log.debug("seek fileID:" + Integer.toString(fID));
/* 90 */     if (appPos != 0 && fID != 0 && this.sdFiles.containsKey(Integer.valueOf(fID))) {
/* 91 */       this.fileID = fID;
/* 92 */       this.bufferPos = 0;
/* 93 */       this.buffer = null;
/* 94 */       return true;
/*    */     } 
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\SDFileSelImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */