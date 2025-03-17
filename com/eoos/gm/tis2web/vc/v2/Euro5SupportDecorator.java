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
/*    */ public class Euro5SupportDecorator
/*    */ {
/*    */   public static Collection<String> getMakes() {
/* 15 */     final Collection<Make> makes = Euro5Support.getMakes();
/*    */     
/* 17 */     return new AbstractCollection<String>()
/*    */       {
/*    */         public Iterator<String> iterator()
/*    */         {
/* 21 */           return Util.createTransformingIterator(makes.iterator(), new Transforming()
/*    */               {
/*    */                 public Object transform(Object object) {
/* 24 */                   return VehicleConfigurationUtil.toString((Make)object);
/*    */                 }
/*    */               });
/*    */         }
/*    */ 
/*    */         
/*    */         public int size() {
/* 31 */           return makes.size();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\Euro5SupportDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */