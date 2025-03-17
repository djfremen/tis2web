/*    */ package com.eoos.gm.tis2web.lt.implementation.io.domain;
/*    */ 
/*    */ import com.eoos.util.ZipUtil;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class AWBlob
/*    */   extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public Object getProperty(BLOBProperty property) {
/* 12 */     return get(property);
/*    */   }
/*    */   
/*    */   public void setProperty(BLOBProperty property, Object value) {
/* 16 */     put((K)property, (V)value);
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 20 */     return (Integer)getProperty(BLOBProperty.ID);
/*    */   }
/*    */   
/*    */   public String getMime() {
/* 24 */     return (String)getProperty(BLOBProperty.MIMETYPE);
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 28 */     byte[] data = (byte[])getProperty(BLOBProperty.BLOB);
/* 29 */     if (data != null) {
/*    */       try {
/* 31 */         data = ZipUtil.gunzip(data);
/* 32 */       } catch (Exception e) {}
/*    */     }
/*    */     
/* 35 */     return data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\domain\AWBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */