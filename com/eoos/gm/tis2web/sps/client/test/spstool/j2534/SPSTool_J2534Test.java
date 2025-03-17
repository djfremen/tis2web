/*    */ package com.eoos.gm.tis2web.sps.client.test.spstool.j2534;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.SPSTool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSTool_J2534Test
/*    */ {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 20 */       ISPSTool spsTool = SPSTool.getInstance("SPSVCS");
/*    */ 
/*    */ 
/*    */       
/* 24 */       String commIFace = spsTool.initialize("%02.02%J2534,CarDAQ");
/*    */       
/* 26 */       System.out.println("Communication interface: " + commIFace);
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
/* 39 */       Pair[] properties = new Pair[5];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 46 */       properties[0] = (Pair)new PairImpl("REPROG", Integer.valueOf(4));
/* 47 */       properties[1] = (Pair)new PairImpl("MethodGroup", Integer.valueOf(1));
/* 48 */       properties[2] = (Pair)new PairImpl("EventType", Integer.valueOf(3));
/* 49 */       properties[3] = (Pair)new PairImpl("SalesOrg,GME", Integer.valueOf(3));
/* 50 */       properties[4] = (Pair)new PairImpl("Error_Not", Integer.valueOf(3));
/*    */       
/* 52 */       spsTool.setToolProperties(properties);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 61 */       Pair[] ecuData = spsTool.getECUData();
/*    */       
/* 63 */       if (ecuData != null) {
/* 64 */         System.out.println("**************************************************************");
/* 65 */         for (int index = 0; index < ecuData.length; index++) {
/* 66 */           System.out.println(ecuData[index].getFirst().toString() + "=" + ecuData[index].getSecond().toString());
/*    */         }
/* 68 */         System.out.println("**************************************************************");
/*    */       } else {
/* 70 */         System.out.println("Invalid ECU Data: array is NULL");
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 82 */     catch (Exception e) {
/* 83 */       System.out.println("Exception: " + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\spstool\j2534\SPSTool_J2534Test.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */