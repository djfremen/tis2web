/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*    */ import java.io.Serializable;
/*    */ import java.text.Collator;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayableServiceIDItem
/*    */   implements DisplayableAttribute, DisplayableValue, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String label;
/*    */   private int id;
/*    */   
/*    */   public static class Compare
/*    */     implements Comparator
/*    */   {
/*    */     private Collator collator;
/*    */     
/*    */     public Compare(Collator collator) {
/* 30 */       this.collator = collator;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public int compare(Object o1, Object o2) {
/* 36 */       return this.collator.compare(((DisplayableServiceIDItem)o1).getDenotation(null), ((DisplayableServiceIDItem)o2).getDenotation(null));
/*    */     }
/*    */   }
/*    */   
/*    */   public DisplayableServiceIDItem(int id, String label) {
/* 41 */     this.id = id;
/* 42 */     this.label = label;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 57 */     return (obj != null && obj instanceof DisplayableServiceIDItem) ? ((((DisplayableServiceIDItem)obj).id == this.id)) : false;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 61 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setId(int id) {
/* 69 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale arg0) {
/* 73 */     return this.label;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\DisplayableServiceIDItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */