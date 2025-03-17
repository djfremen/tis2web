/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Brand
/*    */   implements Serializable, Denotation
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 17 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 19 */           return new Brand((String)identifier);
/*    */         }
/*    */       });
/*    */   
/* 23 */   public static final Brand GME = getInstance("gme");
/* 24 */   public static final Brand NAO = getInstance("nao");
/* 25 */   public static final Brand SATURN = getInstance("sat");
/* 26 */   public static final Brand Global = getInstance("global");
/*    */   
/*    */   private final String identifier;
/*    */   
/*    */   private Brand(String identifier) {
/* 31 */     this.identifier = identifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Brand getInstance(String identifier) {
/* 36 */     identifier = identifier.substring(0, Math.min(3, identifier.length()));
/* 37 */     return (Brand)multitonSupport.getInstance(identifier.toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 41 */     return getInstance(this.identifier);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return "Brand@" + String.valueOf(this.identifier);
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 49 */     String retValue = LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, "sps.brand." + this.identifier);
/* 50 */     return (retValue != null) ? retValue : String.valueOf(this.identifier);
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 54 */     return this.identifier;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\Brand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */