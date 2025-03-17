/*    */ package com.eoos.gm.tis2web.vc.v2.vin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VINResolverProvider
/*    */   implements Service, VINResolverRetrieval
/*    */ {
/*    */   public Object getIdentifier() {
/* 16 */     return this;
/*    */   }
/*    */   
/*    */   public Set getVINResolvers(ClientContext context) {
/* 20 */     return Collections.singleton(LegacyVCDBVINResolver.getInstance());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\VINResolverProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */