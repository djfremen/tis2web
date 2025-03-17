/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DisplayableValueImpl
/*    */   implements DisplayableValue, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private static final Logger log = Logger.getLogger(DisplayableValueImpl.class);
/*    */   
/* 18 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 20 */           return new DisplayableValueImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String labelKey;
/*    */   
/*    */   private DisplayableValueImpl(String labelKey) {
/* 27 */     this.labelKey = labelKey;
/*    */   }
/*    */   
/*    */   public static synchronized DisplayableValue getInstance(String labelKey) {
/* 31 */     return (DisplayableValue)multitonSupport.getInstance(labelKey);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 35 */     return getInstance(this.labelKey);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     return getDenotation(Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/*    */     try {
/* 45 */       String retValue = LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, this.labelKey);
/* 46 */       return (retValue == null) ? this.labelKey : retValue;
/* 47 */     } catch (Exception e) {
/* 48 */       log.warn("unable to retrieve denotation - exception:" + e + " returning label key");
/* 49 */       return this.labelKey;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\DisplayableValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */