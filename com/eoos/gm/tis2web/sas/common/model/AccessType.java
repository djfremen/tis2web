/*    */ package com.eoos.gm.tis2web.sas.common.model;
/*    */ 
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class AccessType
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 13 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 15 */           return new AccessType((String)identifier);
/*    */         }
/*    */       });
/*    */   
/* 19 */   public static final AccessType SKA = getInstance("ska");
/*    */   
/* 21 */   public static final AccessType SCA = getInstance("sca");
/*    */   
/* 23 */   public static final AccessType SSA = getInstance("ssa");
/*    */   
/*    */   private String key;
/*    */   
/*    */   private AccessType(String key) {
/* 28 */     this.key = key;
/*    */   }
/*    */   
/*    */   private static synchronized AccessType getInstance(String key) {
/* 32 */     key = StringUtilities.removeWhitespaces(key);
/* 33 */     return (AccessType)multitonSupport.getInstance(key.toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 37 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return this.key;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\AccessType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */