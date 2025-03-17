/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DisplayableAttributeImpl
/*    */   implements DisplayableAttribute, Serializable
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(DisplayableAttributeImpl.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 21 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 23 */           return new DisplayableAttributeImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String labelKey;
/*    */   
/*    */   private DisplayableAttributeImpl(String labelKey) {
/* 30 */     this.labelKey = labelKey;
/*    */   }
/*    */   
/*    */   public static synchronized DisplayableAttribute getInstance(String labelKey) {
/* 34 */     return (DisplayableAttribute)multitonSupport.getInstance(labelKey);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 38 */     return getInstance(this.labelKey);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return getDenotation(Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/*    */     try {
/* 48 */       String retValue = LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, this.labelKey);
/* 49 */       return (retValue == null) ? this.labelKey : retValue;
/* 50 */     } catch (Exception e) {
/* 51 */       log.warn("unable to retrieve denotation - exception:" + e + " returning label key");
/* 52 */       return this.labelKey;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\DisplayableAttributeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */