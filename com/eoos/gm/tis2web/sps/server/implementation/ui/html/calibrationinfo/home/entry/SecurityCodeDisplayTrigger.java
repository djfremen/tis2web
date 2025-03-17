/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*    */ import com.eoos.html.element.input.CheckBoxElement;
/*    */ 
/*    */ public class SecurityCodeDisplayTrigger
/*    */   extends CheckBoxElement {
/*    */   public SecurityCodeDisplayTrigger(ClientContext context) {
/* 10 */     super(context.createID());
/* 11 */     this.value = UIContext.getInstance(context).displayDealerVCI() ? Boolean.TRUE : Boolean.FALSE;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\entry\SecurityCodeDisplayTrigger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */