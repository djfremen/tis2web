/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*    */ import com.eoos.gm.tis2web.sps.common.impl.VITImpl;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT2Impl
/*    */   extends VITImpl
/*    */   implements VIT2, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Object[] getVIT2Array() {
/* 16 */     return this.vitAttrs.toArray();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\VIT2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */