/*    */ package com.eoos.gm.tis2web.dtc.cas;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.cas.api.Identifier;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ public class IdentifierContainer
/*    */   implements Identifier
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Collection identifiers;
/*    */   
/*    */   public IdentifierContainer(Collection identifiers) {
/* 14 */     this.identifiers = identifiers;
/*    */   }
/*    */   
/*    */   public Collection getIdentifiers() {
/* 18 */     return this.identifiers;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\IdentifierContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */