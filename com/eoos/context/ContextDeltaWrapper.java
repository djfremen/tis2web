/*    */ package com.eoos.context;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContextDeltaWrapper
/*    */   implements IContext
/*    */ {
/*    */   private IContext wrappedContext;
/*    */   private Context delta;
/* 17 */   private Collection removed = new HashSet();
/*    */   
/*    */   public ContextDeltaWrapper(IContext context) {
/* 20 */     this.wrappedContext = context;
/* 21 */     this.delta = new Context();
/*    */   }
/*    */   
/*    */   public void storeObject(Object identifier, Object data) {
/* 25 */     this.delta.storeObject(identifier, data);
/* 26 */     this.removed.remove(identifier);
/*    */   }
/*    */   
/*    */   public Object getObject(Object identifier) {
/* 30 */     Object retValue = null;
/* 31 */     if (!this.removed.contains(identifier)) {
/* 32 */       retValue = this.delta.getObject(identifier);
/* 33 */       if (retValue == null) {
/* 34 */         retValue = this.wrappedContext.getObject(identifier);
/*    */       }
/*    */     } 
/* 37 */     return retValue;
/*    */   }
/*    */   
/*    */   public Collection getObjects(Filter filter) {
/* 41 */     Collection retValue = this.delta.getObjects(filter);
/* 42 */     retValue.addAll(this.wrappedContext.getObjects(filter));
/* 43 */     return retValue;
/*    */   }
/*    */   
/*    */   public void removeObject(Object identifier) {
/* 47 */     this.removed.add(identifier);
/* 48 */     this.delta.removeObject(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\context\ContextDeltaWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */