/*    */ package com.eoos.gm.tis2web.frame.msg.admin.ui.el;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.acl.service.Group;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class GroupSelectionElement
/*    */   extends SelectBoxSelectionElement {
/*    */   public GroupSelectionElement(ClientContext context, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size) {
/* 19 */     super(context.createID(), singleSelectionMode, optionsCallback, size);
/*    */   }
/*    */   
/*    */   public static GroupSelectionElement create(ClientContext context, IMessage msg) {
/* 23 */     final List<?> options = new LinkedList(ACLServiceProvider.getInstance().getService().getUserGroups());
/* 24 */     Collections.sort(options, Group.COMPARATOR);
/*    */ 
/*    */     
/* 27 */     Collection selection = null;
/* 28 */     if (msg != null) {
/* 29 */       selection = new HashSet(msg.getUserGroups());
/*    */     }
/*    */     
/* 32 */     GroupSelectionElement ret = new GroupSelectionElement(context, false, new DataRetrievalAbstraction.DataCallback()
/*    */         {
/*    */           public List getData() {
/* 35 */             return options;
/*    */           }
/*    */         },  Math.min(5, options.size()));
/*    */     
/* 39 */     ret.setValue(selection);
/* 40 */     return ret;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 44 */     return ((Group)option).toExternal().toUpperCase(Locale.ENGLISH);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\el\GroupSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */