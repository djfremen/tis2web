/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.datatype.IVersionNumber;
/*    */ import com.eoos.datatype.VersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RegistryVersionLookup
/*    */   implements IInstalledVersionLookup, Serializable
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(RegistryVersionLookup.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private String key;
/*    */   
/*    */   private String name;
/*    */   
/*    */   private String conversion;
/*    */   
/*    */   public RegistryVersionLookup(String key, String name, String conversion) {
/* 24 */     this.key = key;
/* 25 */     this.name = name;
/* 26 */     this.conversion = conversion;
/*    */   }
/*    */   
/*    */   private String convert(IInstalledVersionLookup.IRegistryLookup.RegistryValue value) {
/* 30 */     String ret = null;
/* 31 */     if (value != null) {
/* 32 */       if (Util.isNullOrEmpty(this.conversion)) {
/* 33 */         ret = String.valueOf(value.getData());
/* 34 */       } else if (this.conversion.equalsIgnoreCase("HR1")) {
/* 35 */         Number data = (Number)value.getData();
/* 36 */         if (data != null) {
/* 37 */           String hexString = Long.toHexString(data.longValue());
/* 38 */           if (hexString.length() % 2 != 0) {
/* 39 */             hexString = "0" + hexString;
/*    */           }
/* 41 */           StringBuffer buffer = new StringBuffer();
/*    */           
/* 43 */           for (int i = 0; i < Math.min(hexString.length() / 2, 3); i++) {
/* 44 */             int number = Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
/* 45 */             buffer.append(number).append(".");
/*    */           } 
/* 47 */           ret = buffer.substring(0, buffer.length() - 1);
/*    */         } 
/*    */       } else {
/* 50 */         ret = String.valueOf(value.getData());
/* 51 */         int index = this.conversion.indexOf("=");
/* 52 */         if (index != -1) {
/* 53 */           ret = String.valueOf(value.getData());
/* 54 */           if (this.conversion.substring(0, index).equals(ret)) {
/* 55 */             ret = this.conversion.substring(index + 1);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/* 60 */     return ret;
/*    */   }
/*    */   public IVersionNumber getInstalledVersion(IInstalledVersionLookup.IRegistryLookup registryLookup) {
/*    */     VersionNumber versionNumber;
/* 64 */     IVersionNumber ret = null;
/*    */     try {
/* 66 */       String _version = convert(registryLookup.getRegistryValue(this.key, this.name));
/* 67 */       if (!Util.isNullOrEmpty(_version)) {
/* 68 */         versionNumber = VersionNumber.parse(_version);
/*    */       }
/* 70 */     } catch (Exception e) {
/* 71 */       log.error("unable to determine installed version, returning null - exception:" + e, e);
/*    */     } 
/* 73 */     return (IVersionNumber)versionNumber;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\RegistryVersionLookup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */