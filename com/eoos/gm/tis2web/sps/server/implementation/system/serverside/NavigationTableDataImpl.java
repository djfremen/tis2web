/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableData;
/*    */ import com.eoos.util.HashCalc;
/*    */ import com.eoos.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavigationTableDataImpl
/*    */   implements NavigationTableData, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5589405432186489505L;
/*    */   private String identifier;
/*    */   private byte[] data;
/*    */   private byte[] checksum;
/*    */   
/*    */   public NavigationTableDataImpl(String identifier, byte[] data, byte[] checksum) {
/* 20 */     this.identifier = identifier;
/* 21 */     this.data = data;
/* 22 */     this.checksum = checksum;
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 26 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 30 */     return this.data;
/*    */   }
/*    */   
/*    */   public byte[] getChecksum() {
/* 34 */     return this.checksum;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 38 */     boolean retValue = false;
/* 39 */     if (this == obj) {
/* 40 */       retValue = true;
/* 41 */     } else if (obj instanceof NavigationTableData) {
/* 42 */       retValue = Util.equals(this.identifier, ((NavigationTableData)obj).getIdentifier());
/*    */     } 
/* 44 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 48 */     int retValue = NavigationTableData.class.hashCode();
/* 49 */     retValue = HashCalc.addHashCode(retValue, this.identifier);
/* 50 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return "NavigationTableDataImpl@" + String.valueOf(this.identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\NavigationTableDataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */