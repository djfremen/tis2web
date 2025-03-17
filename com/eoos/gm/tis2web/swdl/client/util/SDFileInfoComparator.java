/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SDFileInfoComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 22 */       SDFileInfo fileInfo1 = (SDFileInfo)o1;
/* 23 */       SDFileInfo fileInfo2 = (SDFileInfo)o2;
/* 24 */       if (fileInfo1.getFileID() > fileInfo2.getFileID()) {
/* 25 */         return 1;
/*    */       }
/* 27 */       if (fileInfo1.getFileID() < fileInfo2.getFileID()) {
/* 28 */         return -1;
/*    */       }
/* 30 */       return 0;
/*    */     
/*    */     }
/* 33 */     catch (Exception e) {
/* 34 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\SDFileInfoComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */