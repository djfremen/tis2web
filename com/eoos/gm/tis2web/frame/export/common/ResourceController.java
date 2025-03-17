/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceController
/*    */   implements Dispatchable
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(ResourceController.class);
/*    */   
/* 21 */   private String identifier = null;
/*    */   
/* 23 */   private ClientContext context = null;
/*    */ 
/*    */   
/*    */   private ResourceController(ClientContext context) {
/* 27 */     this.context = context;
/* 28 */     this.identifier = context.createID();
/* 29 */     context.registerDispatchable(this);
/*    */   }
/*    */   
/*    */   public static ResourceController getInstance(ClientContext context) {
/* 33 */     synchronized (context.getLockObject()) {
/* 34 */       ResourceController instance = (ResourceController)context.getObject(ResourceController.class);
/* 35 */       if (instance == null) {
/* 36 */         instance = new ResourceController(context);
/* 37 */         context.storeObject(ResourceController.class, instance);
/*    */       } 
/* 39 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 44 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getURL(String name, String type) {
/* 48 */     return this.context.constructDispatchURL(this, "get") + "&name=" + name + "&type=" + type;
/*    */   }
/*    */   
/*    */   public ResultObject get(Map params) {
/* 52 */     String name = (String)params.get("name");
/* 53 */     String type = (String)params.get("type");
/* 54 */     byte[] result = ApplicationContext.getInstance().loadResource(name);
/* 55 */     if (result == null) {
/* 56 */       log.warn("unable to load resource (name:" + name + ", type:" + type);
/*    */     }
/* 58 */     return new ResultObject(10, true, new PairImpl(type, result));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ResourceController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */