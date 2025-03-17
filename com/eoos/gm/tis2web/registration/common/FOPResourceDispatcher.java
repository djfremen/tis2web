/*    */ package com.eoos.gm.tis2web.registration.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ResourceDispatcher;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class FOPResourceDispatcher
/*    */   extends ResourceDispatcher
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected String translateToResourceName(HttpServletRequest request) {
/* 12 */     return "/registration/" + request.getPathInfo();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\FOPResourceDispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */