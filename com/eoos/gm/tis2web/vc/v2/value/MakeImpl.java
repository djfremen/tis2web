/*    */ package com.eoos.gm.tis2web.vc.v2.value;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*    */ import com.eoos.scsm.v2.multiton.v4.StaticMultitonSupport;
/*    */ 
/*    */ public class MakeImpl
/*    */   implements Make {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 11 */   private static final IMultitonSupport multitonSupport = (IMultitonSupport)new StaticMultitonSupport(new IMultitonSupport.CreationCallback()
/*    */       {
/*    */         public Object createInstance(Object identifier) {
/* 14 */           return new MakeImpl((String)identifier);
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
/*    */   private MakeImpl(String id) {
/* 45 */     this.hash = -1; this.id = id;
/*    */   } public static MakeImpl getInstance(String make) { if (make != null) {
/*    */       make = make.trim(); make = make.replaceAll("\\s+", " "); MakeImpl ret = (MakeImpl)multitonSupport.getInstance(VehicleConfigurationUtil.normalize(make), true); NormalizationRegistry.getInstance().add(ret, make); return ret;
/* 48 */     }  return null; } public int hashCode() { if (this.hash == -1) {
/* 49 */       this.hash = VehicleConfigurationUtil.hashCode(this);
/*    */     }
/* 51 */     return this.hash; }
/*    */   public Object getIdentifier() { return this.id; } public String toString() {
/*    */     return this.id;
/*    */   } public boolean equals(Object obj) {
/* 55 */     if (this == obj)
/* 56 */       return true; 
/* 57 */     if (obj instanceof Make) {
/* 58 */       return VehicleConfigurationUtil.equals(this, (Make)obj);
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\value\MakeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */