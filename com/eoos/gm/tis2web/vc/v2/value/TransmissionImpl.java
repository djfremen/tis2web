/*    */ package com.eoos.gm.tis2web.vc.v2.value;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*    */ import com.eoos.scsm.v2.multiton.v4.StaticMultitonSupport;
/*    */ 
/*    */ public class TransmissionImpl implements Transmission {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 10 */   private static final IMultitonSupport multitonSupport = (IMultitonSupport)new StaticMultitonSupport(new IMultitonSupport.CreationCallback()
/*    */       {
/*    */         public Object createInstance(Object identifier) {
/* 13 */           return new TransmissionImpl((String)identifier);
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int hash;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private TransmissionImpl(String id) {
/* 44 */     this.hash = -1; this.id = id;
/*    */   } public static TransmissionImpl getInstance(String transmission) { if (transmission != null) {
/*    */       transmission = transmission.trim(); transmission = transmission.replaceAll("\\s+", " "); TransmissionImpl ret = (TransmissionImpl)multitonSupport.getInstance(VehicleConfigurationUtil.normalize(transmission), true); NormalizationRegistry.getInstance().add(ret, transmission); return ret;
/* 47 */     }  return null; } public int hashCode() { if (this.hash == -1) {
/* 48 */       this.hash = VehicleConfigurationUtil.hashCode(this);
/*    */     }
/* 50 */     return this.hash; }
/*    */   public Object getIdentifier() { return this.id; } public String toString() {
/*    */     return this.id;
/*    */   } public boolean equals(Object obj) {
/* 54 */     if (this == obj)
/* 55 */       return true; 
/* 56 */     if (obj instanceof Transmission) {
/* 57 */       return VehicleConfigurationUtil.equals(this, (Transmission)obj);
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\value\TransmissionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */