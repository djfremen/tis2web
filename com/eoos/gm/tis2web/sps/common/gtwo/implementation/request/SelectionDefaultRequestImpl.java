/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SelectionDefaultRequestImpl
/*    */   extends SelectionRequestImpl implements DefaultValueRetrieval {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected Value defaultSelection;
/*    */   
/*    */   public SelectionDefaultRequestImpl(RequestGroup requestGroup, Attribute attribute, List options, Value defaultSelection) {
/* 15 */     super(requestGroup, attribute, options);
/* 16 */     this.defaultSelection = defaultSelection;
/*    */   }
/*    */   
/*    */   public Value getDefaultValue() {
/* 20 */     return this.defaultSelection;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\SelectionDefaultRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */