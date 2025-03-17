/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class SPSPartFile
/*    */   implements PartFile
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 15 */   protected static final int[] weight = new int[] { 8, 5, 6, 10, 9, 7, 4, 11, 2, 3 };
/*    */   
/* 17 */   protected List parts = new ArrayList();
/* 18 */   protected List modules = new ArrayList();
/*    */   
/*    */   public List getParts() {
/* 21 */     return this.parts;
/*    */   }
/*    */   
/*    */   public List getModules() {
/* 25 */     return this.modules;
/*    */   }
/*    */   
/*    */   public SPSPartFile(String filename) throws Exception {
/*    */     try {
/* 30 */       File file = new File(filename);
/* 31 */       StructureInputStream sis = new StructureInputStream(new FileInputStream(file));
/* 32 */       int sNumParts = sis.readShortLE(); int i;
/* 33 */       for (i = 0; i < 4; i++) {
/* 34 */         sis.readString(77);
/*    */       }
/* 36 */       for (i = 0; i < sNumParts; i++) {
/* 37 */         int nPartNum = sis.readIntLE();
/* 38 */         short sModID = sis.readShortLE();
/* 39 */         sis.readString(40);
/* 40 */         int nChkValue = sis.readIntLE();
/* 41 */         if (nChkValue != CalculateCheckValue(nPartNum, sModID)) {
/* 42 */           throw new IOException();
/*    */         }
/* 44 */         this.parts.add(Integer.toString(nPartNum));
/* 45 */         this.modules.add(Integer.toString(sModID));
/*    */       } 
/* 47 */     } catch (Exception e) {
/* 48 */       throw e;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   int CalculateCheckValue(int nPartNum, short sModID) {
/* 55 */     int nReturnValue = 0; int sCount;
/* 56 */     for (sCount = 0; sModID > 0 && sCount < 2; sCount++) {
/* 57 */       int remainder = sModID % 10;
/* 58 */       sModID = (short)(sModID / 10);
/* 59 */       nReturnValue += weight[sCount] * remainder;
/*    */     } 
/* 61 */     for (sCount = 0; nPartNum > 0 && sCount < 8; sCount++) {
/* 62 */       int remainder = nPartNum % 10;
/* 63 */       nPartNum /= 10;
/* 64 */       nReturnValue += weight[sCount + 2] * remainder;
/*    */     } 
/* 66 */     return nReturnValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\SPSPartFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */