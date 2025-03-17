/*    */ package com.eoos.gm.tis2web.sas.server.implementation.serverside;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.jdbc.JDBCUtil;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HardwareKeyValidation
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger(HardwareKeyValidation.class);
/*    */   
/*    */   public static final int REGISTERED = 1;
/*    */   
/* 27 */   private static Reference refInstance = null;
/*    */   
/* 29 */   private IDatabaseLink dbLink = null;
/*    */   
/*    */   private HardwareKeyValidation() {
/* 32 */     ApplicationContext applicationContext = ApplicationContext.getInstance();
/*    */     
/*    */     try {
/* 35 */       this.dbLink = (IDatabaseLink)DatabaseLink.openDatabase((Configuration)applicationContext, "frame.hardware-key-registry.table.db");
/* 36 */     } catch (Exception e) {
/* 37 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized HardwareKeyValidation getInstance() {
/* 43 */     HardwareKeyValidation retValue = null;
/* 44 */     if (refInstance == null || (retValue = refInstance.get()) == null) {
/* 45 */       retValue = new HardwareKeyValidation();
/* 46 */       refInstance = new SoftReference<HardwareKeyValidation>(retValue);
/*    */     } 
/* 48 */     return retValue;
/*    */   }
/*    */   
/*    */   public boolean isValid(HardwareKey hwk) throws Exception {
/* 52 */     log.debug("validation hardware key: " + String.valueOf(hwk));
/* 53 */     boolean retValue = false;
/* 54 */     Connection connection = null;
/*    */     try {
/* 56 */       connection = this.dbLink.requestConnection();
/* 57 */       PreparedStatement stmt = connection.prepareStatement("SELECT STATUS FROM HARDWARE_KEY_REGISTRY WHERE HARDWARE_KEY = ?");
/*    */       try {
/* 59 */         stmt.setString(1, hwk.getEncoded());
/* 60 */         ResultSet rs = stmt.executeQuery();
/*    */         try {
/* 62 */           if (rs.next()) {
/* 63 */             if (rs.getInt("STATUS") == 1) {
/* 64 */               log.debug("...validation succeeded for hardware key: " + String.valueOf(hwk));
/* 65 */               retValue = true;
/*    */             } else {
/* 67 */               log.debug("...invalid hardware key:" + String.valueOf(hwk) + " in registry, but status != REGISTERED");
/*    */             } 
/*    */           } else {
/* 70 */             log.debug("...invalid hardware key: " + String.valueOf(hwk) + " not in registry");
/*    */           } 
/* 72 */           return retValue;
/*    */         } finally {
/* 74 */           JDBCUtil.close(rs);
/*    */         } 
/*    */       } finally {
/* 77 */         JDBCUtil.close(stmt);
/*    */       } 
/*    */     } finally {
/* 80 */       if (connection != null)
/* 81 */         this.dbLink.releaseConnection(connection); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\HardwareKeyValidation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */