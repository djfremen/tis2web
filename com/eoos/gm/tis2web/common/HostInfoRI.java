/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ 
/*    */ public class HostInfoRI
/*    */   implements HostInfo
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final String host;
/*    */   private final int port;
/*    */   
/*    */   public HostInfoRI(String host, int port) {
/* 13 */     this.host = host;
/* 14 */     this.port = port;
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 18 */     return this.port;
/*    */   }
/*    */   
/*    */   public String getHostname() {
/* 22 */     return this.host;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 26 */     return this.host + ":" + this.port;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 30 */     int ret = HostInfoRI.class.hashCode();
/* 31 */     ret = HashCalc.addHashCode(ret, this.host);
/* 32 */     ret = HashCalc.addHashCode(ret, this.port);
/* 33 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 37 */     if (this == obj)
/* 38 */       return true; 
/* 39 */     if (obj instanceof HostInfoRI) {
/* 40 */       HostInfoRI hostInfo = (HostInfoRI)obj;
/* 41 */       boolean ret = this.host.equalsIgnoreCase(hostInfo.getHostname());
/* 42 */       ret = (ret && this.port == hostInfo.getPort());
/* 43 */       return ret;
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\HostInfoRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */