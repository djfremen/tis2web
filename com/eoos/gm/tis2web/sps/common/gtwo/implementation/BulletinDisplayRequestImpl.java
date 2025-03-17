/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.BulletinDisplayRequest;
/*    */ 
/*    */ public class BulletinDisplayRequestImpl implements BulletinDisplayRequest {
/*    */   protected String bulletin;
/*    */   
/*    */   public BulletinDisplayRequestImpl(String bulletin) {
/*  9 */     this.bulletin = bulletin;
/*    */   }
/*    */   
/*    */   public String getBulletin() {
/* 13 */     return this.bulletin;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\BulletinDisplayRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */