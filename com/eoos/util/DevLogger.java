/*    */ package com.eoos.util;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DevLogger
/*    */ {
/*    */   public static Logger getLogger(Class clazz) {
/* 11 */     return Logger.getLogger("develop." + clazz.getName());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\DevLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */