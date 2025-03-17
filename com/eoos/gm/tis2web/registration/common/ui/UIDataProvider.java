/*    */ package com.eoos.gm.tis2web.registration.common.ui;
/*    */ 
/*    */ import com.eoos.scsm.v2.collection.util.ArrayIterator;
/*    */ import com.eoos.scsm.v2.util.LocaleComparator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class UIDataProvider
/*    */ {
/* 17 */   private static UIDataProvider instance = null;
/*    */   
/* 19 */   private final Object SYNC_LANG = new Object();
/*    */   
/* 21 */   private Collection languages = null;
/*    */   
/* 23 */   private final Object SYNC_COUNTRIES = new Object();
/*    */   
/* 25 */   private Collection countries = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized UIDataProvider getInstance() {
/* 32 */     if (instance == null) {
/* 33 */       instance = new UIDataProvider();
/*    */     }
/* 35 */     return instance;
/*    */   }
/*    */   
/*    */   public Collection getCountries() {
/* 39 */     synchronized (this.SYNC_COUNTRIES) {
/* 40 */       if (this.countries == null) {
/*    */         try {
/* 42 */           Collection<Locale> tmp = new HashSet();
/* 43 */           for (ArrayIterator<String> arrayIterator = new ArrayIterator((Object[])Locale.getISOCountries()); arrayIterator.hasNext();) {
/* 44 */             tmp.add(new Locale("", arrayIterator.next()));
/*    */           }
/* 46 */           this.countries = Collections.unmodifiableCollection(tmp);
/* 47 */         } catch (Exception e) {
/* 48 */           throw new RuntimeException("unable to init country locales", e);
/*    */         } 
/*    */       }
/* 51 */       return this.countries;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection getLanguages() {
/* 56 */     synchronized (this.SYNC_LANG) {
/* 57 */       if (this.languages == null) {
/*    */         
/*    */         try {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 66 */           this.languages = Arrays.asList(Locale.getAvailableLocales());
/* 67 */         } catch (Exception e) {
/* 68 */           throw new RuntimeException("unable to init language locales", e);
/*    */         } 
/*    */       }
/* 71 */       return this.languages;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List getLanguages(Locale userLocale) {
/* 76 */     List<?> ret = new ArrayList(getLanguages());
/* 77 */     Collections.sort(ret, (Comparator<?>)new LocaleComparator(userLocale));
/* 78 */     return ret;
/*    */   }
/*    */   
/*    */   public List getCountries(Locale userLocale) {
/* 82 */     List<?> ret = new ArrayList(getCountries());
/* 83 */     Collections.sort(ret, (Comparator<?>)new LocaleComparator(userLocale));
/* 84 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\UIDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */