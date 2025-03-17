/*    */ package com.eoos.gm.tis2web.sas.client.req;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.ExecutionAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IncompleteResponseConfirmationRequest
/*    */   extends ConfirmationRequestImpl
/*    */ {
/*    */   public void confirm(ExecutionAdapter adapter) {
/* 15 */     adapter.setValue(ExecutionAdapter.Key.INCOMPLETE_SSAR_WARNING, Boolean.TRUE);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\req\IncompleteResponseConfirmationRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */