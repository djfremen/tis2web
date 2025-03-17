/*    */ package com.eoos.html.element.gtwo;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class LocaleSelectionElement
/*    */   extends SelectBoxSelectionElement {
/*  7 */   private Locale displayLocale = null;
/*    */   
/*    */   public LocaleSelectionElement(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size, String targetBookmark, Locale displayLocale) {
/* 10 */     super(parameterName, singleSelectionMode, optionsCallback, size, targetBookmark);
/* 11 */     this.displayLocale = displayLocale;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 15 */     if (option instanceof Locale) {
/* 16 */       if (this.displayLocale != null) {
/* 17 */         return ((Locale)option).getDisplayName(this.displayLocale);
/*    */       }
/* 19 */       return ((Locale)option).getDisplayName();
/*    */     } 
/*    */     
/* 22 */     return super.getDisplayValue(option);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\LocaleSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */