/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StyleSheetController
/*    */   implements Dispatchable
/*    */ {
/* 18 */   private String identifier = null;
/*    */   
/* 20 */   private ClientContextBase context = null;
/*    */   private static ResultObject RO_STYLESHEET;
/*    */   
/*    */   static {
/*    */     try {
/* 25 */       RO_STYLESHEET = new ResultObject(6, true, true, new String(ApplicationContext.getInstance().loadResource("common/style.css")));
/* 26 */     } catch (Exception e) {
/* 27 */       throw new RuntimeException("unable to load stylesheet", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private StyleSheetController(ClientContextBase context) {
/* 33 */     this.context = context;
/* 34 */     this.identifier = context.createID();
/* 35 */     context.registerDispatchable(this);
/*    */   }
/*    */   
/*    */   public static StyleSheetController getInstance(ClientContextBase context) {
/* 39 */     synchronized (context.getLockObject()) {
/* 40 */       StyleSheetController instance = (StyleSheetController)context.getObject(StyleSheetController.class);
/* 41 */       if (instance == null) {
/* 42 */         instance = new StyleSheetController(context);
/* 43 */         context.storeObject(StyleSheetController.class, instance);
/*    */       } 
/* 45 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 50 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getURL() {
/* 54 */     return this.context.constructDispatchURL(this, "get");
/*    */   }
/*    */   
/*    */   public ResultObject get(Map params) {
/* 58 */     return RO_STYLESHEET;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\StyleSheetController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */