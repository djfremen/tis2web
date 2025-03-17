/*    */ package com.eoos.gm.tis2web.dtc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCStorageTask
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   SoftwareKey swk;
/*    */   Lease lease;
/* 25 */   Set dtcs = new HashSet();
/*    */   
/*    */   public DTCStorageTask(SoftwareKey swk, Lease lease, DTC dtc) {
/* 28 */     this(swk, lease, Collections.singleton(dtc));
/*    */   }
/*    */ 
/*    */   
/*    */   public DTCStorageTask(SoftwareKey swk, Lease lease, Collection dtcs) {
/* 33 */     this.swk = swk;
/* 34 */     this.lease = lease;
/* 35 */     this.dtcs.addAll(dtcs);
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 39 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public static Boolean resolveResult(Object result) throws Exception {
/* 43 */     if (result instanceof Exception) {
/* 44 */       throw (Exception)result;
/*    */     }
/* 46 */     return (Boolean)result;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 51 */     return new DTCStorageTask_ServerPart(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\cai\DTCStorageTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */