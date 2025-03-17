/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSPartFile
/*    */   implements PartFile
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   private static final Logger log = Logger.getLogger(SPSPartFile.class);
/*    */   
/* 21 */   protected static final int[] weight = new int[] { 8, 5, 6, 10, 9, 7, 4, 11, 2, 3 };
/*    */   
/* 23 */   protected List parts = new ArrayList();
/*    */   
/* 25 */   protected List modules = new ArrayList();
/*    */   
/*    */   public List getParts() {
/* 28 */     return this.parts;
/*    */   }
/*    */   
/*    */   public List getModules() {
/* 32 */     return this.modules;
/*    */   }
/*    */   
/*    */   public SPSPartFile(byte[] data) throws Exception {
/*    */     try {
/* 37 */       handleSIS(new StructureInputStream(data));
/* 38 */     } catch (Exception e) {
/* 39 */       throw e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public SPSPartFile(File file) throws Exception {
/* 44 */     FileInputStream fis = null;
/*    */     try {
/* 46 */       fis = new FileInputStream(file);
/* 47 */       handleSIS(new StructureInputStream(fis));
/* 48 */     } catch (Exception e) {
/* 49 */       throw e;
/*    */     } finally {
/* 51 */       if (fis != null) {
/*    */         try {
/* 53 */           fis.close();
/* 54 */         } catch (Exception e) {
/* 55 */           log.error("unable to close file input stream - exception:" + e, e);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void handleSIS(StructureInputStream sis) throws Exception {
/*    */     try {
/* 63 */       int sNumParts = sis.readShortLE(); int i;
/* 64 */       for (i = 0; i < 4; i++) {
/* 65 */         sis.readString(77);
/*    */       }
/* 67 */       for (i = 0; i < sNumParts; i++) {
/* 68 */         int nPartNum = sis.readIntLE();
/* 69 */         short sModID = sis.readShortLE();
/* 70 */         sis.readString(40);
/*    */         
/* 72 */         int nChkValue = sis.readIntLE();
/* 73 */         if (nChkValue != CalculateCheckValue(nPartNum, sModID)) {
/* 74 */           throw new IOException();
/*    */         }
/* 76 */         this.parts.add(Integer.toString(nPartNum));
/* 77 */         this.modules.add(Integer.toString(sModID));
/*    */       } 
/* 79 */     } catch (Exception e) {
/* 80 */       throw e;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   int CalculateCheckValue(int nPartNum, short sModID) {
/* 87 */     int nReturnValue = 0; int sCount;
/* 88 */     for (sCount = 0; sModID > 0 && sCount < 2; sCount++) {
/* 89 */       int remainder = sModID % 10;
/* 90 */       sModID = (short)(sModID / 10);
/* 91 */       nReturnValue += weight[sCount] * remainder;
/*    */     } 
/* 93 */     for (sCount = 0; nPartNum > 0 && sCount < 8; sCount++) {
/* 94 */       int remainder = nPartNum % 10;
/* 95 */       nPartNum /= 10;
/* 96 */       nReturnValue += weight[sCount + 2] * remainder;
/*    */     } 
/* 98 */     return nReturnValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSPartFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */