/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.Reader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT1Reader
/*    */   extends BufferedReader
/*    */ {
/*    */   public static final String END_BLOCK = "Rem END";
/*    */   
/*    */   public VIT1Reader(Reader r) {
/* 22 */     super(r);
/*    */   }
/*    */ 
/*    */   
/*    */   public List readVIT1(ITestDriverSettings settings) throws Exception {
/* 27 */     List<String> lines = new ArrayList();
/* 28 */     boolean findNextBlock = false;
/* 29 */     int index = 0;
/*    */     
/* 31 */     settings.setIsLastVIT1(true); try {
/*    */       String line;
/* 33 */       while ((line = readLine()) != null) {
/* 34 */         index++;
/* 35 */         if (!findNextBlock) {
/* 36 */           if (line.indexOf("\"read\"") != -1) {
/* 37 */             lines.clear();
/* 38 */             findNextBlock = true;
/* 39 */             settings.setStatusLineNo(index); continue;
/*    */           } 
/* 41 */           if (line.indexOf("Status") != -1) {
/* 42 */             settings.setStatusLineNo(index);
/*    */           }
/* 44 */           lines.add(line);
/* 45 */           if (line.indexOf("Rem END") != -1) {
/* 46 */             settings.setIsLastVIT1(IsLastVIT1Block());
/*    */             break;
/*    */           } 
/*    */           continue;
/*    */         } 
/* 51 */         if (line.indexOf("Rem END") != -1) {
/* 52 */           findNextBlock = false;
/* 53 */           settings.setStatusLineNo(index + 1);
/*    */         }
/*    */       
/*    */       } 
/* 57 */     } catch (Exception e) {
/* 58 */       throw e;
/*    */     } 
/*    */     
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean IsLastVIT1Block() throws Exception {
/* 66 */     boolean skip = false;
/*    */     try {
/*    */       String line;
/* 69 */       while ((line = readLine()) != null) {
/* 70 */         if (line.indexOf("Status") != -1) {
/* 71 */           if (line.indexOf("\"read\"") != -1) {
/* 72 */             skip = true;
/*    */           } else {
/* 74 */             skip = false;
/*    */           } 
/*    */         }
/* 77 */         if (line.indexOf("Rem END") != -1) {
/* 78 */           if (!skip) {
/* 79 */             return false;
/*    */           }
/* 81 */           skip = false;
/*    */         } 
/*    */       } 
/* 84 */     } catch (Exception e) {
/* 85 */       throw e;
/*    */     } 
/*    */     
/* 88 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\VIT1Reader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */