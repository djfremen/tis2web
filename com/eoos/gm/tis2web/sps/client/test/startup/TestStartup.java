/*     */ package com.eoos.gm.tis2web.sps.client.test.startup;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.system.classloader.ClientClassLoader;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestStartup
/*     */ {
/*     */   public static void main(String[] args) {
/*  21 */     System.setProperty("com.eoos.gm.tis2web.sps.client", "true");
/*  22 */     System.setProperty("language.id", "de_DE");
/*  23 */     System.setProperty("brands", "GME, NAO");
/*  24 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*  25 */     ClientSettings clientSettings = appContext.getClientSettings();
/*  26 */     printConfiguration(clientSettings, "initial configuration");
/*     */ 
/*     */     
/*     */     while (true) {
/*  30 */       CmdDialog cmdDialog = new CmdDialog();
/*     */       try {
/*  32 */         String input = cmdDialog.perform("Please enter <PROPERTY>=<NEW_VALUE> or 'end' to end program : ", "end");
/*  33 */         int i = input.indexOf('=');
/*  34 */         if (i > 0) {
/*  35 */           String prop = input.substring(0, i);
/*  36 */           String value = input.substring(i + 1, input.length());
/*  37 */           if (prop != null && clientSettings.getKeys().contains(prop)) {
/*  38 */             clientSettings.setProperty(prop, value);
/*     */           }
/*     */         } 
/*  41 */       } catch (Exception e) {
/*  42 */         if (e instanceof EndException) {
/*     */           break;
/*     */         }
/*  45 */         System.out.println(e);
/*  46 */         System.exit(-1);
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     printConfiguration(clientSettings, "modified configuration");
/*     */ 
/*     */ 
/*     */     
/*  54 */     clientSettings.saveConfiguration();
/*     */     
/*  56 */     System.out.println("\n*** Properties group: ***");
/*  57 */     Properties props = clientSettings.getProperties("vin.");
/*  58 */     Iterator<String> it = props.keySet().iterator();
/*  59 */     while (it.hasNext()) {
/*  60 */       String key = it.next();
/*  61 */       System.out.println(key + ": " + props.getProperty(key));
/*     */     } 
/*  63 */     System.out.println("*** End of properties group ***\n");
/*     */     
/*  65 */     System.out.println("*** Sorted VIN's: ***");
/*  66 */     List vins = clientSettings.getVINs();
/*  67 */     System.out.println(vins);
/*  68 */     System.out.println("*** End of sorted VIN's: ***\n");
/*     */ 
/*     */     
/*     */     while (true) {
/*  72 */       CmdDialog cmdDialog = new CmdDialog();
/*     */       try {
/*  74 */         String input = cmdDialog.perform("Please enter new VIN or 'end' to end program : ", "end");
/*  75 */         clientSettings.setVIN(input);
/*  76 */       } catch (Exception e) {
/*  77 */         if (e instanceof EndException) {
/*     */           break;
/*     */         }
/*  80 */         System.out.println(e);
/*  81 */         System.exit(-1);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     clientSettings.saveConfiguration();
/*  86 */     System.out.println("*** Sorted VIN's: ***");
/*  87 */     vins = clientSettings.getVINs();
/*  88 */     System.out.println(vins);
/*  89 */     System.out.println("*** End of sorted VIN's: ***\n");
/*     */     
/*  91 */     ClientClassLoader clientClassLoader = new ClientClassLoader(TestStartup.class.getClassLoader());
/*     */     try {
/*  93 */       TestClientInit tester = (TestClientInit)clientClassLoader.loadClass("com.eoos.gm.tis2web.sps.client.test.startup.TestClientInit").newInstance();
/*  94 */       tester.test();
/*  95 */     } catch (Exception e) {
/*  96 */       System.out.println("TestClientInit execution error: " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printConfiguration(ClientSettings settings, String header) {
/* 102 */     System.out.println("*** " + header + " ***");
/* 103 */     Iterator<String> keyIterator = settings.getKeys().iterator();
/* 104 */     while (keyIterator.hasNext()) {
/* 105 */       String key = keyIterator.next();
/* 106 */       System.out.println(key + "=" + settings.getProperty(key));
/*     */     } 
/* 108 */     System.out.println("*** End of " + header + " ***\n");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\startup\TestStartup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */