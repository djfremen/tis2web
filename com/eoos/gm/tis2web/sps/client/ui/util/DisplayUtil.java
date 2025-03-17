/*    */ package com.eoos.gm.tis2web.sps.client.ui.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayUtil
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(DisplayUtil.class);
/*    */   
/*    */   public static Collection createDisplayAdapter(Collection colect) {
/* 19 */     Collection<DisplayAdapter> result = null;
/*    */     try {
/* 21 */       Class<?> className = colect.getClass();
/*    */       
/*    */       try {
/* 24 */         result = (Collection)className.newInstance();
/* 25 */       } catch (Exception e) {
/* 26 */         result = new LinkedList();
/*    */       } 
/* 28 */       Iterator iterator = colect.iterator();
/* 29 */       while (iterator.hasNext()) {
/* 30 */         DisplayAdapter display = new DisplayAdapter(iterator.next());
/* 31 */         result.add(display);
/*    */       } 
/* 33 */     } catch (Exception e) {
/* 34 */       log.error("Exception in createDisplayAdapter() method: " + e, e);
/*    */     } 
/* 36 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\u\\util\DisplayUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */