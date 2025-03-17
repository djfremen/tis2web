/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*    */ import com.eoos.html.element.input.CheckBoxElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DealerVCIDisplayTrigger
/*    */   extends CheckBoxElement
/*    */ {
/*    */   public DealerVCIDisplayTrigger(ClientContext context) {
/* 13 */     super(context.createID());
/* 14 */     this.value = UIContext.getInstance(context).displayDealerVCI() ? Boolean.TRUE : Boolean.FALSE;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\entry\DealerVCIDisplayTrigger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */