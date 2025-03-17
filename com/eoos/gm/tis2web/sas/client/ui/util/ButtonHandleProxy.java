/*    */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ButtonHandleProxy
/*    */   implements ButtonHandle
/*    */ {
/*    */   private class Attributes {
/*    */     private Attributes() {}
/*    */     
/* 13 */     public Boolean enabled = null;
/*    */     
/* 15 */     public String label = null;
/*    */     
/* 17 */     public Boolean visible = null;
/*    */   }
/*    */   
/* 20 */   private Map typeToAttributes = new HashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Attributes getAttributes(ButtonHandle.Type type) {
/* 27 */     Attributes retValue = (Attributes)this.typeToAttributes.get(type);
/* 28 */     if (retValue == null) {
/* 29 */       retValue = new Attributes();
/* 30 */       this.typeToAttributes.put(type, retValue);
/*    */     } 
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public synchronized ButtonHandle flush(ButtonHandle handle) {
/* 36 */     for (Iterator<ButtonHandle.Type> iter = this.typeToAttributes.keySet().iterator(); iter.hasNext(); ) {
/* 37 */       ButtonHandle.Type type = iter.next();
/* 38 */       Attributes attributes = getAttributes(type);
/* 39 */       if (attributes != null) {
/* 40 */         if (attributes.enabled != null) {
/* 41 */           handle.setEnabled(type, attributes.enabled.booleanValue());
/*    */         }
/* 43 */         if (attributes.label != null) {
/* 44 */           handle.setLabel(type, attributes.label);
/*    */         }
/* 46 */         if (attributes.visible != null) {
/* 47 */           handle.setVisible(type, attributes.visible.booleanValue());
/*    */         }
/*    */       } 
/*    */     } 
/* 51 */     return handle;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void setEnabled(ButtonHandle.Type type, boolean enabled) {
/* 56 */     (getAttributes(type)).enabled = new Boolean(enabled);
/*    */   }
/*    */   
/*    */   public void setLabel(ButtonHandle.Type type, String label) {
/* 60 */     (getAttributes(type)).label = label;
/*    */   }
/*    */   
/*    */   public void setVisible(ButtonHandle.Type type, boolean visible) {
/* 64 */     (getAttributes(type)).visible = new Boolean(visible);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\ButtonHandleProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */