/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicResourceController
/*    */   implements Dispatchable
/*    */ {
/* 19 */   private String identifier = null;
/*    */   
/* 21 */   private ClientContext context = null;
/*    */   
/* 23 */   private Map store = new HashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   private DynamicResourceController(ClientContext context) {
/* 27 */     this.context = context;
/* 28 */     this.identifier = context.createID();
/* 29 */     context.registerDispatchable(this);
/*    */   }
/*    */   
/*    */   public static DynamicResourceController getInstance(ClientContext context) {
/* 33 */     synchronized (context.getLockObject()) {
/* 34 */       DynamicResourceController instance = (DynamicResourceController)context.getObject(DynamicResourceController.class);
/* 35 */       if (instance == null) {
/* 36 */         instance = new DynamicResourceController(context);
/* 37 */         context.storeObject(DynamicResourceController.class, instance);
/*    */       } 
/* 39 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 44 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getURL(byte[] data, String type, String identifier) {
/* 48 */     this.store.put(identifier, new PairImpl(type, data));
/* 49 */     return this.context.constructDispatchURL(this, "get") + "&id=" + identifier;
/*    */   }
/*    */   
/*    */   public ResultObject get(Map params) {
/* 53 */     String identifier = (String)params.get("id");
/* 54 */     Pair pair = (Pair)this.store.get(identifier);
/* 55 */     return new ResultObject(10, true, pair);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\DynamicResourceController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */