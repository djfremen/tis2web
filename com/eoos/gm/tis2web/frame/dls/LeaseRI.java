/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeaseRI
/*    */   implements Lease, ILeaseInternal, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private long creationDate;
/*    */   private long expirationDate;
/*    */   private String securityToken;
/*    */   
/*    */   public LeaseRI(long creationDate, long expirationDate, String securityToken) {
/* 20 */     this.creationDate = creationDate;
/* 21 */     this.expirationDate = expirationDate;
/* 22 */     this.securityToken = securityToken;
/*    */   }
/*    */   
/*    */   public long getCreationDate() {
/* 26 */     return this.creationDate;
/*    */   }
/*    */   
/*    */   public long getExpirationDate() {
/* 30 */     return this.expirationDate;
/*    */   }
/*    */   
/*    */   public String getSecurityToken() {
/* 34 */     return this.securityToken;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     return Util.getClassName(this) + "[until " + Util.formatDate(this.expirationDate) + "]";
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 42 */     if (this == obj)
/* 43 */       return true; 
/* 44 */     if (obj instanceof LeaseRI) {
/* 45 */       LeaseRI lease = (LeaseRI)obj;
/*    */       
/* 47 */       boolean ret = (this.expirationDate == lease.expirationDate);
/* 48 */       ret = (ret && this.creationDate == lease.creationDate);
/* 49 */       ret = (ret && this.securityToken.equals(lease.securityToken));
/* 50 */       return ret;
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     int ret = LeaseRI.class.hashCode();
/* 59 */     ret = HashCalc.addHashCode(ret, this.expirationDate);
/* 60 */     ret = HashCalc.addHashCode(ret, this.creationDate);
/* 61 */     ret = HashCalc.addHashCode(ret, this.securityToken);
/* 62 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\LeaseRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */