/*    */ package com.eoos.gm.tis2web.vc.v2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.Transforming;
/*    */ import java.util.AbstractCollection;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MultipleResolutionException
/*    */   extends Exception
/*    */ {
/* 17 */   private final Collection<String> makes = new AbstractCollection<String>()
/*    */     {
/*    */       public int size()
/*    */       {
/* 21 */         return makes.size();
/*    */       }
/*    */ 
/*    */       
/*    */       public Iterator<String> iterator() {
/* 26 */         return Util.createTransformingIterator(makes.iterator(), new Transforming()
/*    */             {
/*    */               public Object transform(Object object) {
/* 29 */                 return VehicleConfigurationUtil.toString((Make)object);
/*    */               }
/*    */             });
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public Collection<String> getMakes() {
/* 37 */     return this.makes;
/*    */   }
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MultipleResolutionException(final Collection<Make> makes) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\MultipleResolutionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */