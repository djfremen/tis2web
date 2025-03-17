/*    */ package com.eoos.gm.tis2web.sas.client.req;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolSelectionRequestImpl
/*    */   implements ToolSelectionRequest
/*    */ {
/*    */   private List tools;
/*    */   
/*    */   public ToolSelectionRequestImpl(List tools) {
/* 13 */     this.tools = tools;
/*    */   }
/*    */   
/*    */   public List getTools() {
/* 17 */     return this.tools;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\req\ToolSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */