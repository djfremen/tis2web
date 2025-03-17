/*    */ package com.eoos.gm.tis2web.frame.msg.admin.ui.el;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocaleSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   private ClientContext context;
/*    */   private Callback callback;
/*    */   
/*    */   public LocaleSelectionElement(ClientContext context, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size, Callback callback) {
/* 31 */     super(context.createID(), singleSelectionMode, optionsCallback, size);
/* 32 */     this.context = context;
/* 33 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public static LocaleSelectionElement create(ClientContext context, IMessage msg, Callback callback) {
/* 37 */     return create(context, msg, callback, null);
/*    */   }
/*    */   
/*    */   public static LocaleSelectionElement create(ClientContext context, IMessage msg, Callback callback, Set locales) {
/* 41 */     final List<Locale> options = new LinkedList();
/* 42 */     if (locales != null) {
/* 43 */       options.addAll(locales);
/*    */     } else {
/* 45 */       Locale[] alocales = Locale.getAvailableLocales();
/* 46 */       for (int i = 0; i < alocales.length; i++) {
/* 47 */         if (alocales[i].getCountry().length() == 0) {
/* 48 */           options.add(alocales[i]);
/*    */         }
/*    */       } 
/*    */     } 
/* 52 */     Collections.sort(options, Util.getLocaleComparator(context.getLocale()));
/*    */     
/* 54 */     LocaleSelectionElement ret = new LocaleSelectionElement(context, true, new DataRetrievalAbstraction.DataCallback()
/*    */         {
/*    */           public List getData() {
/* 57 */             return options;
/*    */           }
/*    */         },  1, callback);
/*    */ 
/*    */     
/* 62 */     ret.setValue(Util.toLanguage(context.getLocale()));
/* 63 */     return ret;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 67 */     return ((Locale)option).getDisplayName(this.context.getLocale());
/*    */   } public static interface Callback {
/*    */     boolean autoSubmit(); Object onChange(); boolean mark(Locale param1Locale); }
/*    */   protected boolean autoSubmitOnChange() {
/* 71 */     return this.callback.autoSubmit();
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 75 */     return this.callback.onChange();
/*    */   }
/*    */   
/*    */   protected Map getAdditionalAttributes(Object option) {
/* 79 */     if (this.callback.mark((Locale)option)) {
/* 80 */       Map<Object, Object> ret = new HashMap<Object, Object>();
/* 81 */       ret.put("style", "color:red");
/* 82 */       return ret;
/*    */     } 
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\el\LocaleSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */