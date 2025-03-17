/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public final class MailAttachment implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public String name;
/*    */   public String mime;
/*    */   public byte[] data;
/*    */   
/*    */   public MailAttachment(String name, String mime, byte[] data) {
/* 12 */     this.name = name;
/* 13 */     this.mime = mime;
/* 14 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\MailAttachment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */