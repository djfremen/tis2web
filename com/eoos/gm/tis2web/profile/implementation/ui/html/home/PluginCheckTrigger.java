/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.CheckBoxElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginCheckTrigger
/*    */   extends CheckBoxElement
/*    */ {
/*    */   public PluginCheckTrigger(ClientContext context) {
/* 12 */     super(context.createID());
/* 13 */     this.value = context.getSharedContext().checkPlugins();
/*    */     
/* 15 */     if (this.value == null)
/* 16 */       this.value = Boolean.TRUE; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\PluginCheckTrigger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */