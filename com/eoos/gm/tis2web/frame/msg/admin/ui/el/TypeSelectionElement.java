/*    */ package com.eoos.gm.tis2web.frame.msg.admin.ui.el;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import com.eoos.gm.tis2web.frame.msg.util.MessageUtil;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class TypeSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   private ClientContext context;
/* 17 */   private static final List OPTIONS = Arrays.asList(new IMessage.Type[] { IMessage.Type.INFO, IMessage.Type.WARNING });
/*    */   
/*    */   public TypeSelectionElement(ClientContext context, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size) {
/* 20 */     super(context.createID(), singleSelectionMode, optionsCallback, size);
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static TypeSelectionElement create(ClientContext context, IMessage msg) {
/* 25 */     TypeSelectionElement ret = new TypeSelectionElement(context, true, new DataRetrievalAbstraction.DataCallback()
/*    */         {
/*    */           public List getData() {
/* 28 */             return TypeSelectionElement.OPTIONS;
/*    */           }
/*    */         },  1);
/*    */     
/* 32 */     if (msg != null) {
/* 33 */       ret.setValue(msg.getType());
/*    */     }
/* 35 */     return ret;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 39 */     return MessageUtil.getTypeLabel((IMessage.Type)option, this.context.getLocale()).toUpperCase(Locale.ENGLISH);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\el\TypeSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */