/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ 
/*    */ 
/*    */ public abstract class RemoteTool
/*    */   implements Tool
/*    */ {
/*    */   protected String id;
/*    */   protected String type;
/*    */   
/*    */   public String getId() {
/* 14 */     return this.id;
/*    */   }
/*    */   
/*    */   public ValueAdapter getType() {
/* 18 */     return new ValueAdapter(this.type);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\RemoteTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */