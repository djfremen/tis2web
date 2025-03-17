/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sids.service.cai.DisplayableServiceIDAttr;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayableServiceIDAttrImpl
/*    */   extends DisplayableServiceIDItem
/*    */   implements DisplayableServiceIDAttr
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String description;
/*    */   
/*    */   public String getDescription() {
/* 18 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   DisplayableServiceIDAttrImpl(int id, String label, String description) {
/* 24 */     super(id, label);
/* 25 */     this.description = description;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\DisplayableServiceIDAttrImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */