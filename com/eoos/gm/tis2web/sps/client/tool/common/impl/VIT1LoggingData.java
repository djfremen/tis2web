/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.SimpleVIT;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT1LoggingData
/*    */ {
/* 55 */   private LinkedHashMap dataMap = new LinkedHashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public void addSimpleVIT(String ecuAddr, SimpleVIT simpleVIT) {
/* 59 */     if (ecuAddr != null) {
/* 60 */       if (!this.dataMap.containsKey(ecuAddr) || this.dataMap.get(ecuAddr) == null) {
/* 61 */         List vit1List = new LinkedList();
/* 62 */         this.dataMap.put(ecuAddr, vit1List);
/*    */       } 
/* 64 */       ((List<SimpleVIT>)this.dataMap.get(ecuAddr)).add(simpleVIT);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Map getVIT1History() {
/* 69 */     Map<Object, Object> result = new LinkedHashMap<Object, Object>();
/* 70 */     Iterator<String> it = this.dataMap.keySet().iterator();
/* 71 */     while (it.hasNext()) {
/* 72 */       String ecuAddr = it.next();
/* 73 */       List<Pair[]> pairs = new LinkedList();
/* 74 */       Iterator<SimpleVIT> it2 = ((List)this.dataMap.get(ecuAddr)).iterator();
/* 75 */       while (it2.hasNext()) {
/* 76 */         Pair[] simpleVITData = ((SimpleVIT)it2.next()).getClonedData();
/* 77 */         pairs.add(simpleVITData);
/*    */       } 
/* 79 */       result.put(ecuAddr, pairs);
/*    */     } 
/* 81 */     return result;
/*    */   }
/*    */   
/*    */   public void resetVIT1History() {
/* 85 */     this.dataMap = new LinkedHashMap<Object, Object>();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\VIT1LoggingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */