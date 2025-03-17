/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap;
/*    */ 
/*    */ import com.eoos.datatype.SimpleMultiton;
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeValueMapDeltaCommulatingWrapper
/*    */   implements AttributeValueMap, Serializable
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(AttributeValueMapDeltaCommulatingWrapper.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private AttributeValueMap backend;
/*    */   
/* 28 */   private static final Object REMOVE = SimpleMultiton.getInstance("remove");
/*    */   
/* 30 */   private List deltas = new LinkedList();
/*    */   
/*    */   public AttributeValueMapDeltaCommulatingWrapper(AttributeValueMap backend) {
/* 33 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   public void set(Attribute attribute, Value value) {
/* 37 */     this.deltas.add(new PairImpl(attribute, value));
/*    */   }
/*    */   
/*    */   public void exchange(Attribute attribute, Value value) {
/* 41 */     this.backend.set(attribute, value);
/*    */   }
/*    */   
/*    */   public void remove(Attribute attribute) {
/* 45 */     this.deltas.add(new PairImpl(attribute, REMOVE));
/*    */   }
/*    */   
/*    */   public void synchronize(AttributeValueMap original) {
/* 49 */     log.debug("synchronizing " + String.valueOf(original));
/* 50 */     for (Iterator<Pair> iter = this.deltas.iterator(); iter.hasNext(); ) {
/* 51 */       Pair pair = iter.next();
/* 52 */       Attribute attribute = (Attribute)pair.getFirst();
/* 53 */       if (pair.getSecond() == REMOVE) {
/* 54 */         log.debug("removing attribute " + String.valueOf(attribute));
/* 55 */         original.remove(attribute); continue;
/*    */       } 
/* 57 */       Value value = (Value)pair.getSecond();
/* 58 */       log.debug("setting value for attribute " + String.valueOf(attribute) + " to " + String.valueOf(value));
/* 59 */       original.set(attribute, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getAttributes() {
/* 66 */     List<Object> list = new LinkedList(this.backend.getAttributes());
/* 67 */     for (Iterator<Pair> iter = this.deltas.iterator(); iter.hasNext(); ) {
/* 68 */       Pair pair = iter.next();
/* 69 */       if (pair.getSecond() == REMOVE) {
/* 70 */         while (list.remove(pair.getFirst()));
/*    */         continue;
/*    */       } 
/* 73 */       if (!list.contains(pair.getFirst())) {
/* 74 */         list.add(pair.getFirst());
/*    */       }
/*    */     } 
/*    */     
/* 78 */     return list;
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 82 */     Value retValue = this.backend.getValue(attribute);
/* 83 */     for (Iterator<Pair> iter = this.deltas.iterator(); iter.hasNext(); ) {
/* 84 */       Pair pair = iter.next();
/* 85 */       if (pair.getFirst().equals(attribute)) {
/* 86 */         if (pair.getSecond() == REMOVE) {
/* 87 */           retValue = null; continue;
/*    */         } 
/* 89 */         retValue = (Value)pair.getSecond();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 94 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\avmap\AttributeValueMapDeltaCommulatingWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */