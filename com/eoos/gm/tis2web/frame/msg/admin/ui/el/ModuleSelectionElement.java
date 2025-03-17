/*    */ package com.eoos.gm.tis2web.frame.msg.admin.ui.el;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.Module;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ 
/*    */ public class ModuleSelectionElement
/*    */   extends SelectBoxSelectionElement {
/*    */   private ClientContext context;
/*    */   private static final List OPTIONS;
/*    */   
/*    */   static {
/* 24 */     List<?> services = new LinkedList(ConfiguredServiceProvider.getInstance().getServices(VisualModule.class));
/* 25 */     for (ListIterator<VisualModule> iter = services.listIterator(); iter.hasNext(); ) {
/* 26 */       Module module = Module.getInstance(((VisualModule)iter.next()).getType());
/* 27 */       iter.set(module);
/*    */     } 
/* 29 */     Collections.sort(services, Util.COMPARATOR_TOSTRING);
/* 30 */     OPTIONS = services;
/*    */   }
/*    */   
/*    */   private ModuleSelectionElement(ClientContext context, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size) {
/* 34 */     super(context.createID(), singleSelectionMode, optionsCallback, size);
/* 35 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static ModuleSelectionElement create(ClientContext context, IMessage msg) {
/* 39 */     Collection selection = (msg != null) ? msg.getTargetModules() : new HashSet();
/*    */     
/* 41 */     ModuleSelectionElement ret = new ModuleSelectionElement(context, false, new DataRetrievalAbstraction.DataCallback()
/*    */         {
/*    */           public List getData() {
/* 44 */             return ModuleSelectionElement.OPTIONS;
/*    */           }
/*    */         },  Math.min(5, OPTIONS.size()));
/*    */     
/* 48 */     ret.setValue(selection);
/* 49 */     return ret;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 53 */     return ((Module)option).getDenotation(this.context.getLocale());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\el\ModuleSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */