/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.util.HashCalc;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(Util.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Collection asPairCollection(AttributeValueMap avMap) {
/* 26 */     Collection attributes = avMap.getAttributes();
/* 27 */     Collection<PairImpl> retValue = new HashSet(attributes.size());
/* 28 */     for (Iterator<Attribute> iter = attributes.iterator(); iter.hasNext(); ) {
/* 29 */       Attribute attribute = iter.next();
/* 30 */       Value value = avMap.getValue(attribute);
/* 31 */       retValue.add(new PairImpl(attribute, value));
/*    */     } 
/* 33 */     return retValue;
/*    */   }
/*    */   
/*    */   public static int hashCode(AttributeValueMap avMap) {
/* 37 */     int retValue = AttributeValueMap.class.hashCode();
/* 38 */     retValue = HashCalc.addHashCode(retValue, asPairCollection(avMap));
/* 39 */     return retValue;
/*    */   }
/*    */   
/*    */   public static boolean equals(AttributeValueMap avMap, AttributeValueMap avMap2) {
/* 43 */     boolean retValue = false;
/*    */     try {
/* 45 */       if (avMap == avMap2) {
/* 46 */         retValue = true;
/*    */       } else {
/* 48 */         retValue = asPairCollection(avMap).equals(asPairCollection(avMap2));
/*    */       } 
/* 50 */     } catch (Exception e) {
/* 51 */       log.warn("unable to compare, returning false - exception: " + e, e);
/*    */     } 
/* 53 */     return retValue;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 57 */     System.out.println(equals(null, null));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\avmap\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */