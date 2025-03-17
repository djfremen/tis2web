/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.CustomException;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ExceptionImpl
/*    */   implements CustomException
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(ExceptionImpl.class);
/*    */   
/* 15 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 17 */           return new ExceptionImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private ExceptionImpl(String key) {
/* 24 */     this.key = key;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 28 */     return this.key;
/*    */   }
/*    */   
/*    */   public static synchronized CustomException getInstance(String key) {
/* 32 */     return (CustomException)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 36 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return super.toString() + "[key=" + this.key + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/*    */     try {
/* 46 */       return LabelResourceProvider.getInstance().getLabelResource().getMessage(locale, this.key);
/* 47 */     } catch (Exception e) {
/* 48 */       log.warn("unable to retrieve denotation - exception:" + e + " returning message key");
/* 49 */       return this.key;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ExceptionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */