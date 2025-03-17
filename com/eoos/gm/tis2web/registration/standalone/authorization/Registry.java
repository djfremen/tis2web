/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFlag;
/*    */ import com.eoos.gm.tis2web.util.FileStreamFactory;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ 
/*    */ public class Registry
/*    */ {
/*    */   public static RegistryEntry queryDatabase(String database, String hardwareID, String subscriberID) {
/*    */     try {
/* 17 */       InputStream is = FileStreamFactory.getInstance().getInputStream(new File(database));
/* 18 */       InputStreamReader ir = new InputStreamReader(is, Charset.forName("utf-8"));
/* 19 */       BufferedReader br = new BufferedReader(ir);
/*    */       
/*    */       String line;
/*    */       
/* 23 */       while ((line = br.readLine()) != null) {
/* 24 */         StringTokenizer st = new StringTokenizer(line.trim(), ",");
/* 25 */         while (st.hasMoreTokens()) {
/* 26 */           String sid = st.nextToken();
/* 27 */           String hid = st.nextToken();
/* 28 */           int rf = Integer.parseInt(st.nextToken());
/* 29 */           String ta = st.nextToken();
/* 30 */           if (hardwareID.equals(hid) || subscriberID.equals(sid)) {
/* 31 */             return new RegistryEntry(sid, RegistrationFlag.get(rf), AuthorizationImpl.lookup(ta));
/*    */           }
/*    */         } 
/*    */       } 
/* 35 */       br.close();
/* 36 */     } catch (Exception e) {}
/*    */     
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\Registry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */