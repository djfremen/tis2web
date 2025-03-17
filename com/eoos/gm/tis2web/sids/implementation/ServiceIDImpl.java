/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.ObjectStreamException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServiceIDImpl
/*    */   implements ServiceID
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 15 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 17 */           return new ServiceIDImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String identifier;
/*    */   
/*    */   private ServiceIDImpl(String identifier) {
/* 24 */     this.identifier = identifier;
/*    */   }
/*    */   
/*    */   public static synchronized ServiceIDImpl getInstance(String identifier) {
/* 28 */     return (ServiceIDImpl)multitonSupport.getInstance(StringUtilities.trimIdentifier(identifier));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return String.valueOf(this.identifier);
/*    */   }
/*    */   
/*    */   public Object readResolve() throws ObjectStreamException {
/* 36 */     return getInstance(this.identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ServiceIDImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */