/*     */ package com.eoos.gm.tis2web.sps.client.settings;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class WarrantyClaimCodeStore
/*     */ {
/*  18 */   private static final Logger log = Logger.getLogger(WarrantyClaimCodeStore.class);
/*     */   
/*     */   public static final String STORE = "wcc.log";
/*     */   
/*     */   public static final int VIN = 0;
/*     */   public static final int WARRANTY_CLAIM_CODE = 1;
/*     */   public static final int TIMESTAMP = 2;
/*  25 */   private static Integer size = null;
/*     */   
/*     */   public static final boolean isWarrantyCodeListSupported() {
/*  28 */     return (getWarrantyCodeListSize() != null && getWarrantyCodeListSize().intValue() > 0);
/*     */   }
/*     */   
/*     */   public static final Integer getWarrantyCodeListSize() {
/*  32 */     if (size == null) {
/*  33 */       if (ClientAppContextProvider.getClientAppContext().getWarrantyCodeList() != null) {
/*  34 */         size = ClientAppContextProvider.getClientAppContext().getWarrantyCodeList();
/*  35 */       } else if (ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("warranty.code.list") != null) {
/*  36 */         size = Integer.valueOf(ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("warranty.code.list"));
/*     */       } 
/*     */     }
/*  39 */     return size;
/*     */   }
/*     */   
/*     */   public static List getWarrantyCodeList() {
/*  43 */     if (!isWarrantyCodeListSupported()) {
/*  44 */       return null;
/*     */     }
/*  46 */     String directory = ClientAppContextProvider.getClientAppContext().getHomeDir();
/*  47 */     File store = new File(directory, "wcc.log");
/*  48 */     if (store.exists()) {
/*  49 */       List records = loadWarrantyClaimCode(store);
/*  50 */       if (records != null) {
/*  51 */         return records;
/*     */       }
/*     */     } 
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public static void recordWarrantyClaimCode(String vin, String wcc) {
/*  58 */     if (!isWarrantyCodeListSupported()) {
/*     */       return;
/*     */     }
/*  61 */     List<String[]> records = null;
/*  62 */     String directory = ClientAppContextProvider.getClientAppContext().getHomeDir();
/*  63 */     File store = new File(directory, "wcc.log");
/*  64 */     if (store.exists()) {
/*  65 */       records = loadWarrantyClaimCode(store);
/*     */     }
/*     */     try {
/*  68 */       BufferedWriter out = new BufferedWriter(new FileWriter(store));
/*  69 */       out.write(vin + '\t' + wcc + '\t' + System.currentTimeMillis());
/*  70 */       out.newLine();
/*  71 */       if (records != null) {
/*  72 */         if (records.size() == getWarrantyCodeListSize().intValue()) {
/*  73 */           records.remove(records.size() - 1);
/*     */         }
/*  75 */         for (int i = 0; i < records.size(); i++) {
/*  76 */           String[] data = records.get(i);
/*  77 */           out.write(data[0] + '\t' + data[1] + '\t' + data[2]);
/*  78 */           out.newLine();
/*     */         } 
/*     */       } 
/*  81 */       out.close();
/*  82 */     } catch (IOException e) {
/*  83 */       log.error("failed to write warranty claim code store", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List loadWarrantyClaimCode(File store) {
/*     */     try {
/*  89 */       List<String[]> wcc = new ArrayList();
/*  90 */       BufferedReader in = new BufferedReader(new FileReader(store));
/*     */       String record;
/*  92 */       while ((record = in.readLine()) != null) {
/*  93 */         if (record.trim().length() == 0) {
/*     */           continue;
/*     */         }
/*  96 */         StringTokenizer tokenizer = new StringTokenizer(record.trim());
/*  97 */         if (tokenizer.hasMoreTokens()) {
/*  98 */           String[] data = new String[3];
/*  99 */           data[0] = tokenizer.nextToken();
/* 100 */           data[1] = tokenizer.nextToken();
/* 101 */           data[2] = tokenizer.nextToken();
/* 102 */           wcc.add(data);
/*     */         } 
/*     */       } 
/* 105 */       in.close();
/* 106 */       return (wcc.size() > 0) ? wcc : null;
/* 107 */     } catch (IOException e) {
/* 108 */       log.error(e);
/*     */       
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\WarrantyClaimCodeStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */