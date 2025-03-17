/*    */ package com.eoos.gm.tis2web.sps.client.test.testdriver;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.TestDriverImpl;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*    */ import com.eoos.gm.tis2web.sps.common.impl.SPSProgrammingDataImpl;
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
/*    */ 
/*    */ 
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args) {
/* 34 */     ClientSettings settings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/* 35 */     settings.setProperty("testdriver.option.readflag", "true");
/* 36 */     settings.saveConfiguration();
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
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     TestDriverImpl testDriverImpl = new TestDriverImpl();
/* 62 */     VIT1 vit1 = null;
/*    */     while (true) {
/*    */       try {
/* 65 */         vit1 = (VIT1)testDriverImpl.getECUData(null, new Object(), null);
/* 66 */       } catch (Exception e) {
/* 67 */         if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoMoreFilesException) {
/*    */           break;
/*    */         }
/* 70 */         if (e instanceof com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.NoValidVIT1Exception) {
/*    */           continue;
/*    */         }
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 78 */       if (vit1 != null)
/* 79 */         for (int i = 0; i < vit1.getControlModuleBlocks().size(); i++) {
/* 80 */           SPSProgrammingDataImpl sPSProgrammingDataImpl = new SPSProgrammingDataImpl(((VIT)vit1.getControlModuleBlocks().get(i)).getAttrValue("ecu_adr"), null, null, null);
/*    */           try {
/* 82 */             ((Boolean)testDriverImpl.reprogram(null, sPSProgrammingDataImpl, null, null, null)).booleanValue();
/* 83 */           } catch (Exception e) {}
/*    */         }  
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void testCheckFlexibleHWIdentifierForECUID(VIT1 vit1) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\testdriver\Test.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */