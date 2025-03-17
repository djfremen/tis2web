/*    */ package com.eoos.gm.tis2web.sps.client.tool.navigation.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.ToolValueImpl;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavigationValueImpl
/*    */   extends ToolValueImpl
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String label;
/*    */   
/*    */   public NavigationValueImpl(String _key, String _label) {
/* 20 */     super(_key);
/* 21 */     this.label = _label;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 25 */     return this.label;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\navigation\impl\NavigationValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */