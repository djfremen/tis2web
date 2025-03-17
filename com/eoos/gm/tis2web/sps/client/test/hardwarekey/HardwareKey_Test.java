/*     */ package com.eoos.gm.tis2web.sps.client.test.hardwarekey;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*     */ import com.eoos.gm.tis2web.sps.client.hardwarekey.IHardwareKey;
/*     */ import com.eoos.gm.tis2web.sps.client.hardwarekey.impl.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*     */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*     */ 
/*     */ 
/*     */ public class HardwareKey_Test
/*     */ {
/*     */   public static void main(String[] args) {
/*  13 */     IStarter starter = Starter.getInstance();
/*     */     
/*  15 */     if (starter != null) {
/*     */       
/*  17 */       if (starter.isStartAllowed() == true) {
/*  18 */         IHardwareKey hwk = HardwareKey.getInstance();
/*     */         
/*  20 */         if (hwk != null) {
/*     */           try {
/*  22 */             String encodedHardwareKey_16 = hwk.getEncodedHWK_16();
/*  23 */             System.out.println("Encoded hardware key 16: " + encodedHardwareKey_16);
/*  24 */           } catch (SystemDriverNotInstalledException e) {
/*  25 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  29 */             String encodedHardwareKey_32 = hwk.getEncodedHWK_32();
/*  30 */             System.out.println("Encoded hardware key 32: " + encodedHardwareKey_32);
/*  31 */           } catch (SystemDriverNotInstalledException e) {
/*  32 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  36 */             String hardwareKey = hwk.getHWK();
/*  37 */             System.out.println("Hardware key: " + hardwareKey);
/*  38 */           } catch (SystemDriverNotInstalledException e) {
/*  39 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  43 */             String encodedHardwareKey_32 = hwk.getEncodedHWK_32();
/*  44 */             System.out.println("Encoded hardware key 32: " + encodedHardwareKey_32);
/*  45 */           } catch (SystemDriverNotInstalledException e) {
/*  46 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  50 */             String encodedHardwareKey_16 = hwk.getEncodedHWK_16();
/*  51 */             System.out.println("Encoded hardware key 16: " + encodedHardwareKey_16);
/*  52 */           } catch (SystemDriverNotInstalledException e) {
/*  53 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  57 */             String encodedHardwareKey_16 = hwk.getEncodedHWK_16();
/*  58 */             System.out.println("Encoded hardware key 16: " + encodedHardwareKey_16);
/*  59 */           } catch (SystemDriverNotInstalledException e) {
/*  60 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  64 */             String encodedHardwareKey_32 = hwk.getEncodedHWK_32();
/*  65 */             System.out.println("Encoded hardware key 32: " + encodedHardwareKey_32);
/*  66 */           } catch (SystemDriverNotInstalledException e) {
/*  67 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  71 */             String hardwareKey = hwk.getHWK();
/*  72 */             System.out.println("Hardware key: " + hardwareKey);
/*  73 */           } catch (SystemDriverNotInstalledException e) {
/*  74 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  78 */             String encodedHardwareKey_32 = hwk.getEncodedHWK_32();
/*  79 */             System.out.println("Encoded hardware key 32: " + encodedHardwareKey_32);
/*  80 */           } catch (SystemDriverNotInstalledException e) {
/*  81 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  85 */             String hardwareKey = hwk.getHWK();
/*  86 */             System.out.println("Hardware key: " + hardwareKey);
/*  87 */           } catch (SystemDriverNotInstalledException e) {
/*  88 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           try {
/*  92 */             String hardwareKey = hwk.getHWK();
/*  93 */             System.out.println("Hardware key: " + hardwareKey);
/*  94 */           } catch (SystemDriverNotInstalledException e) {
/*  95 */             e.printStackTrace();
/*     */           } 
/*     */           
/*  98 */           System.out.println("Release Libraries: " + hwk.releaseLibraries());
/*     */         } 
/*     */       } else {
/* 101 */         System.out.println("Start is not allowed");
/*     */       } 
/*     */     } else {
/* 104 */       System.out.println("Starter is null");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\hardwarekey\HardwareKey_Test.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */