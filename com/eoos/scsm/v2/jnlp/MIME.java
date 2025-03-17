/*    */ package com.eoos.scsm.v2.jnlp;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public final class MIME
/*    */ {
/*  9 */   public static final MIME JPG = new MIME("image/jpg");
/*    */   
/* 11 */   public static final MIME GIF = new MIME("image/gif");
/*    */   
/* 13 */   public static final MIME JAR = new MIME("application/x-java-archiv");
/*    */   
/* 15 */   public static final MIME JARDIFF = new MIME("application/x-java-archive-diff");
/*    */   
/* 17 */   public static final MIME JNLP = new MIME("application/x-java-jnlp-file");
/*    */   
/* 19 */   public static final MIME ERROR = new MIME("application/x-java-jnlp-error");
/*    */   
/*    */   private String mime;
/*    */   
/*    */   private MIME(String mime) {
/* 24 */     this.mime = mime.toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 28 */     return this.mime;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 32 */     if (this == obj)
/* 33 */       return true; 
/* 34 */     if (obj instanceof MIME) {
/* 35 */       return this.mime.equals(((MIME)obj).mime);
/*    */     }
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 42 */     int ret = MIME.class.hashCode();
/* 43 */     ret = HashCalc.addHashCode(ret, this.mime);
/* 44 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\jnlp\MIME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */