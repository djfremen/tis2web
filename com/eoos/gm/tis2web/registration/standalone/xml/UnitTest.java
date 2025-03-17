/*    */ package com.eoos.gm.tis2web.registration.standalone.xml;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.AuthorizationImpl;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class UnitTest
/*    */ {
/*    */   private static void testLoadingAuthorizationDefinition() {
/* 10 */     System.out.println("Start Unit Test");
/* 11 */     String file = "C:\\Projects\\gm\\webTIS\\phase 4+5\\work\\registration\\xml\\authorization.xml";
/* 12 */     AuthorizationImpl.load(new File(file));
/* 13 */     assertion((AuthorizationImpl.lookup("GME-002") != null));
/* 14 */     System.out.println("Unit Test Completed");
/*    */   }
/*    */   
/*    */   public static void assertion(boolean result) {
/* 18 */     if (!result) {
/* 19 */       throw new RuntimeException();
/*    */     }
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 24 */     testLoadingAuthorizationDefinition();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\UnitTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */