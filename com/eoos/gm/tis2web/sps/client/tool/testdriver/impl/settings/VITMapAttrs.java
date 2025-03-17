/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VITMapAttrs {
/*    */   public static final String VAL_UART = "UART";
/*    */   public static final String VAL_CLASS2 = "Class 2";
/*    */   public static final String VAL_KWP2000 = "KWP2000";
/*    */   public static final String VAL_GMLAN = "GMLAN";
/* 12 */   protected static Map mapAttrs = new HashMap<Object, Object>();
/*    */   static {
/* 14 */     VITLabelResource res = new VITLabelResource();
/* 15 */     mapAttrs.putAll(res.getVITAttrName2Desc());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getAttrName(String attrDesc) {
/* 22 */     Iterator<Map.Entry> it = mapAttrs.entrySet().iterator();
/* 23 */     while (it.hasNext()) {
/* 24 */       Map.Entry desc2name = it.next();
/* 25 */       if (((String)desc2name.getValue()).equals(attrDesc)) {
/* 26 */         return (String)desc2name.getKey();
/*    */       }
/*    */     } 
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public static String getAttrDesc(String attrName) {
/* 33 */     return (String)mapAttrs.get(attrName);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\settings\VITMapAttrs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */