/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AVUtil
/*    */ {
/* 21 */   private static Logger log = Logger.getLogger(AVUtil.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object accessValue(AttributeValueMap data, Attribute attribute) {
/* 28 */     Value value = data.getValue(attribute);
/* 29 */     if (value == null)
/* 30 */       return null; 
/* 31 */     if (value instanceof ValueAdapter) {
/* 32 */       return ((ValueAdapter)value).getAdaptee();
/*    */     }
/* 34 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getDisplayString(Object object, Locale locale) {
/* 39 */     String retValue = null;
/* 40 */     if (object instanceof Denotation) {
/* 41 */       retValue = ((Denotation)object).getDenotation(locale);
/*    */     } else {
/* 43 */       retValue = String.valueOf(object);
/*    */     } 
/* 45 */     return retValue;
/*    */   }
/*    */   
/*    */   public static void dump(AttributeValueMap data) {
/*    */     try {
/* 50 */       Collection attributes = data.getAttributes();
/* 51 */       Iterator<Attribute> it = attributes.iterator();
/* 52 */       while (it.hasNext()) {
/* 53 */         Attribute attribute = it.next();
/* 54 */         String attr = attribute.toString();
/* 55 */         if (attr.indexOf("[key=") >= 0) {
/* 56 */           attr = attr.substring(attr.indexOf("[key="));
/*    */         }
/* 58 */         String value = accessValue(data, attribute).toString();
/* 59 */         if (value.indexOf("[key=") >= 0) {
/* 60 */           value = value.substring(value.indexOf("[key="));
/*    */         }
/* 62 */         log.debug(attr + " := " + value);
/*    */       } 
/* 64 */     } catch (Exception x) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static Map toMap(AttributeValueMap data) {
/* 69 */     Map<Object, Object> ret = new LinkedHashMap<Object, Object>();
/* 70 */     for (Iterator<Attribute> iter = data.getAttributes().iterator(); iter.hasNext(); ) {
/* 71 */       Attribute attribute = iter.next();
/* 72 */       Value value = data.getValue(attribute);
/* 73 */       ret.put(attribute, value);
/*    */     } 
/* 75 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\AVUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */