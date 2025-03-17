/*    */ package com.eoos.gm.tis2web.sps.server.implementation.calibinfo;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModuleFilter
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(ModuleFilter.class);
/*    */   
/* 14 */   public static final Filter FILTER_MODULE_0 = new Filter() {
/*    */       public boolean include(Object obj) {
/*    */         try {
/* 17 */           Module module = (Module)obj;
/* 18 */           if (Integer.parseInt(module.getID()) == 0) {
/* 19 */             return false;
/*    */           }
/* 21 */         } catch (Exception e) {
/* 22 */           ModuleFilter.log.warn("unable to determine inclusion state, returning true - exception: " + e, e);
/*    */         } 
/* 24 */         return true;
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\ModuleFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */