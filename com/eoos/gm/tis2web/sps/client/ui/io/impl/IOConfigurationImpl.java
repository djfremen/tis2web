/*     */ package com.eoos.gm.tis2web.sps.client.ui.io.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.io.IOConfiguration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOConfigurationImpl
/*     */   implements IOConfiguration
/*     */ {
/*     */   public ArrayList changeProperty(String key, String value) {
/*  41 */     ArrayList<String> allLine = new ArrayList();
/*     */     try {
/*  43 */       boolean foundKey = false;
/*  44 */       String path = System.getProperties().get("user.home") + File.separator + "sps" + File.separator + "configuration.properties";
/*  45 */       File file = new File(path);
/*  46 */       RandomAccessFile raf = new RandomAccessFile(file, "r");
/*  47 */       String line = raf.readLine();
/*     */       
/*  49 */       while (line != null) {
/*     */         
/*  51 */         if (line.indexOf(key) != -1 && !Util.isNullOrEmpty(value)) {
/*  52 */           line = key + "=" + value;
/*  53 */           foundKey = true;
/*     */         } 
/*  55 */         allLine.add(line);
/*  56 */         line = raf.readLine();
/*     */       } 
/*     */       
/*  59 */       if (!foundKey && !Util.isNullOrEmpty(value)) {
/*  60 */         line = key + "=" + value;
/*  61 */         allLine.add(line);
/*     */       } 
/*     */       
/*  64 */       raf.close();
/*  65 */     } catch (Exception e) {
/*  66 */       System.out.println("Exception in changeProperty() methode:" + e.getMessage());
/*     */     } 
/*  68 */     return allLine;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String key, String value) {
/*  73 */     Properties prop = new Properties();
/*  74 */     prop = load();
/*  75 */     prop.setProperty(key, value);
/*     */     
/*  77 */     String path = System.getProperties().get("user.home") + File.separator + "sps" + File.separator + "configuration.properties";
/*  78 */     FileOutputStream outputStream = null;
/*     */     try {
/*  80 */       outputStream = new FileOutputStream(path);
/*  81 */     } catch (FileNotFoundException e) {
/*  82 */       e.printStackTrace();
/*     */     } 
/*     */     try {
/*  85 */       prop.store(outputStream, "header_value_in_propertyfile");
/*  86 */     } catch (IOException e1) {
/*  87 */       e1.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(ArrayList data) {
/*     */     try {
/*  97 */       String path = System.getProperties().get("user.home") + File.separator + "sps" + File.separator + "configuration.properties";
/*  98 */       FileOutputStream outputStream = new FileOutputStream(path);
/*  99 */       PrintWriter pw = new PrintWriter(outputStream, true);
/*     */       
/* 101 */       for (int i = 0; i <= data.size() - 1; i++) {
/* 102 */         pw.println(data.get(i));
/*     */       }
/*     */       
/* 105 */       pw.close();
/*     */     }
/* 107 */     catch (IOException e) {
/* 108 */       System.out.println("Exception e" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties load() {
/* 117 */     Properties properties = new Properties();
/*     */     try {
/* 119 */       File file = new File(System.getProperties().get("user.home") + File.separator + "sps" + File.separator + "configuration.properties");
/* 120 */       if (file.exists()) {
/* 121 */         FileInputStream inputStream = new FileInputStream(file);
/* 122 */         if (inputStream != null) {
/* 123 */           properties.load(inputStream);
/* 124 */           inputStream.close();
/*     */         } 
/*     */       } else {
/*     */         try {
/* 128 */           File dir = new File(System.getProperties().get("user.home") + File.separator + "sps");
/* 129 */           if (!dir.exists()) {
/* 130 */             dir.mkdir();
/*     */           }
/* 132 */           file.createNewFile();
/* 133 */         } catch (Exception x) {}
/*     */       }
/*     */     
/* 136 */     } catch (Exception e) {
/* 137 */       System.out.println("Exception  in load methode: " + e.getMessage());
/*     */     } 
/*     */     
/* 140 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList insertsNewLine(int index, String newValue) {
/* 149 */     ArrayList<String> allLine = new ArrayList();
/* 150 */     if (index == 0) {
/*     */       
/* 152 */       allLine.add("#VIN Identification");
/* 153 */       index++;
/* 154 */       allLine.add("VIN.ITEM" + index + "=" + newValue);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 159 */         String path = System.getProperties().get("user.home") + File.separator + "sps" + File.separator + "configuration.properties";
/* 160 */         File file = new File(path);
/* 161 */         RandomAccessFile raf = new RandomAccessFile(file, "r");
/* 162 */         String line = raf.readLine();
/*     */         
/* 164 */         while (line != null) {
/* 165 */           allLine.add(line);
/*     */           
/* 167 */           if (line.indexOf("VIN.ITEM" + index) != -1) {
/* 168 */             index++;
/* 169 */             allLine.add("VIN.ITEM" + index + "=" + newValue);
/*     */           } 
/*     */           
/* 172 */           line = raf.readLine();
/*     */         } 
/*     */         
/* 175 */         raf.close();
/* 176 */       } catch (Exception e) {
/* 177 */         System.out.println("Exception in insertsNewLine methode:" + e.getMessage());
/*     */       } 
/* 179 */     }  return allLine;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\io\impl\IOConfigurationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */