/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueRetrievalAdapter
/*    */   implements ValueRetrieval
/*    */ {
/*    */   private AttributeValueMap avMap;
/*    */   
/*    */   public ValueRetrievalAdapter(AttributeValueMap avMap) {
/* 15 */     this.avMap = avMap;
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 19 */     return this.avMap.getValue(attribute);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\ValueRetrievalAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */