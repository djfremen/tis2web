/*    */ package com.eoos.gm.tis2web.sas.common.model;
/*    */ 
/*    */ import com.eoos.instantiation.Multiton;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SalesOrganisation
/*    */   implements Multiton, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static MultitonSupport multitonSupport;
/*    */   
/*    */   static {
/* 18 */     MultitonSupport.CreationCallback cc = new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 20 */           return new SalesOrganisation((String)identifier);
/*    */         }
/*    */       };
/*    */     
/* 24 */     multitonSupport = new MultitonSupport(cc);
/*    */   }
/*    */   
/* 27 */   public static final SalesOrganisation GME = getInstance("gme");
/*    */   
/* 29 */   public static final SalesOrganisation NAO = getInstance("nao");
/*    */   
/*    */   private String identifier;
/*    */   
/*    */   private SalesOrganisation(String identifier) {
/* 34 */     this.identifier = identifier;
/*    */   }
/*    */   
/*    */   public static synchronized SalesOrganisation getInstance(String identifier) {
/* 38 */     identifier = StringUtilities.trimIdentifier(identifier);
/* 39 */     return (SalesOrganisation)multitonSupport.getInstance(identifier);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 43 */     return getInstance(this.identifier);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return "SalesOrganization[" + String.valueOf(this.identifier) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\SalesOrganisation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */