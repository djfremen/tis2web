/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.CheckBoxElement;
/*    */ 
/*    */ public class UseLTHoursTrigger
/*    */   extends CheckBoxElement {
/*    */   public UseLTHoursTrigger(ClientContext context) {
/*  9 */     super(context.createID());
/* 10 */     this.value = context.getSharedContext().useLTHours();
/*    */     
/* 12 */     if (this.value == null)
/* 13 */       this.value = Boolean.FALSE; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\UseLTHoursTrigger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */