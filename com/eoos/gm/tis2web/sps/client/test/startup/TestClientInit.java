/*    */ package com.eoos.gm.tis2web.sps.client.test.startup;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.SupportedProtocols;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOptions;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class TestClientInit
/*    */ {
/*    */   public void test() {
/* 29 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*    */     try {
/* 31 */       appContext.init();
/* 32 */     } catch (Exception e) {
/* 33 */       System.out.println(e);
/* 34 */       System.exit(-1);
/*    */     } 
/* 36 */     ClientSettings clientSettings = appContext.getClientSettings();
/* 37 */     System.out.println("Locale: " + appContext.getLocale());
/* 38 */     List<String> allowedTools = new ArrayList();
/* 39 */     allowedTools.add("Tech2 Remote");
/* 40 */     allowedTools.add("Test Driver");
/*    */     
/* 42 */     System.out.println("Local Tool Names: " + appContext.getToolNames());
/* 43 */     LabelResource labels = LabelResourceProvider.getInstance().getLabelResource();
/* 44 */     System.out.println("Label for testing: " + labels.getLabel(appContext.getLocale(), "TEST_LABEL"));
/* 45 */     System.out.println("Message for testing: " + labels.getMessage(appContext.getLocale(), "TEST_MESSAGE"));
/* 46 */     System.out.println("sps cache: " + clientSettings.getSPSCache());
/* 47 */     if (clientSettings.setSPSCache("relative")) {
/* 48 */       clientSettings.saveConfiguration();
/*    */     }
/* 50 */     String tst = "C:\\tmp\\correct";
/* 51 */     if (clientSettings.setSPSCache(tst)) {
/* 52 */       clientSettings.saveConfiguration();
/*    */     }
/* 54 */     tst = "spsCache";
/* 55 */     if (clientSettings.setSPSCache(tst)) {
/* 56 */       clientSettings.saveConfiguration();
/*    */     }
/* 58 */     System.out.println("Final path: " + clientSettings.getSPSCache());
/*    */     
/* 60 */     clientSettings.setProperty("testdriver.option.writemodules", "false");
/* 61 */     clientSettings.setProperty("testdriver.option.vit1path", "C:\\tmp\\somethingelse");
/* 62 */     clientSettings.saveConfiguration();
/* 63 */     System.out.println("\n*** Tool options ***");
/* 64 */     Iterator<Tool> it = appContext.getTools().iterator();
/* 65 */     while (it.hasNext()) {
/* 66 */       Tool tool = it.next();
/* 67 */       if (tool instanceof ToolOptions) {
/* 68 */         List<ToolOption> options = ((ToolOptions)tool).getOptions();
/* 69 */         for (int i = 0; i < options.size(); i++) {
/* 70 */           StringBuffer allValues = new StringBuffer();
/* 71 */           ToolOption option = options.get(i);
/* 72 */           ToolOption toolOption1 = option;
/* 73 */           Iterator<OptionValue> it2 = option.getValues().iterator();
/* 74 */           while (it2.hasNext()) {
/* 75 */             OptionValue value = it2.next();
/* 76 */             allValues.append(value.getDenotation(null) + ' ');
/*    */           } 
/* 78 */           System.out.println(tool.getId() + " options: " + toolOption1.getDenotation(null) + "(" + allValues.toString() + ")");
/* 79 */           System.out.println("Tool property key: " + option.getPropertyKey());
/*    */         } 
/*    */       } 
/*    */     } 
/* 83 */     Iterator<Tool> toolIterator = appContext.getTools().iterator();
/* 84 */     while (toolIterator.hasNext()) {
/* 85 */       Tool tool = toolIterator.next();
/* 86 */       if (tool instanceof SupportedProtocols) {
/* 87 */         System.out.println("Tool " + tool.getId() + " supports:");
/* 88 */         List supportedProtocols = ((SupportedProtocols)tool).getSupportedProtocols();
/* 89 */         Iterator<String> protocolIterator = supportedProtocols.iterator();
/* 90 */         while (protocolIterator.hasNext())
/* 91 */           System.out.println("\t" + protocolIterator.next()); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\startup\TestClientInit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */