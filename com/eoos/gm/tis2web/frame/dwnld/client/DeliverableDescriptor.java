/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDeliverableDescriptor;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeliverableDescriptor
/*    */   implements IDeliverableDescriptor, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private DownloadUnit downloadUnit;
/*    */   private byte[] data;
/*    */   
/*    */   public DeliverableDescriptor(DownloadUnit downloadUnit, byte[] data) {
/* 19 */     this.downloadUnit = downloadUnit;
/* 20 */     this.data = data;
/*    */   }
/*    */   
/*    */   public byte[] getDescriptor() {
/* 24 */     return this.data;
/*    */   }
/*    */   
/*    */   public DownloadUnit getDownloadUnit() {
/* 28 */     return this.downloadUnit;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 32 */     if (this == obj)
/* 33 */       return true; 
/* 34 */     if (obj instanceof DeliverableDescriptor) {
/* 35 */       DeliverableDescriptor dd = (DeliverableDescriptor)obj;
/* 36 */       return this.downloadUnit.equals(dd.downloadUnit);
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     int ret = DeliverableDescriptor.class.hashCode();
/* 44 */     ret = HashCalc.addHashCode(ret, this.downloadUnit);
/* 45 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 49 */     return "DeliverableDescriptor[" + String.valueOf(this.downloadUnit.getDescripition(Locale.ENGLISH)) + "," + String.valueOf(this.downloadUnit.getVersionNumber()) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\DeliverableDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */