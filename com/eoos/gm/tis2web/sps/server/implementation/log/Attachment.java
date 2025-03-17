/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ import com.eoos.util.HashCalc;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public final class Attachment {
/*    */   private Key key;
/*    */   private Object object;
/*    */   
/*    */   public static class Key {
/*    */     private Key(String id) {
/* 12 */       this.id = id.toLowerCase(Locale.ENGLISH).trim();
/*    */     }
/*    */     private String id;
/*    */     public static Key forString(String qualifier) {
/* 16 */       return new Key(qualifier);
/*    */     }
/*    */     
/*    */     public boolean equals(Object obj) {
/* 20 */       if (this == obj)
/* 21 */         return true; 
/* 22 */       if (obj instanceof Key) {
/* 23 */         return ((Key)obj).id.equals(this.id);
/*    */       }
/* 25 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 30 */       int ret = Key.class.hashCode();
/* 31 */       ret = HashCalc.addHashCode(ret, this.id);
/* 32 */       return ret;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 36 */       return this.id;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Attachment(Key key, Object object) {
/* 44 */     this.key = key;
/* 45 */     this.object = object;
/*    */   }
/*    */   
/*    */   public Key getKey() {
/* 49 */     return this.key;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 53 */     return this.object;
/*    */   }
/*    */   
/* 56 */   public static final Key KEY_VIT1 = new Key("vit1");
/*    */   
/* 58 */   public static final Key KEY_VIT1_RNR = new Key("vit1.rnr");
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\Attachment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */