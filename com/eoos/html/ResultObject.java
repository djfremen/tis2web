/*    */ package com.eoos.html;
/*    */ 
/*    */ import com.eoos.datatype.ResultObject;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class ResultObject
/*    */   extends ResultObject {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int TEXT_HTML = 0;
/*    */   public static final int IMAGE_GIF = 1;
/*    */   public static final int IMAGE_JPG = 2;
/*    */   public static final int IMAGE_PNG = 3;
/*    */   public static final int TEXT_XML = 4;
/*    */   public static final int UNKNOWN = 5;
/*    */   public static final int TEXT_CSS = 6;
/*    */   public static final int IMAGE_TIFF = 7;
/*    */   public static final int FORWARD = 8;
/*    */   public static final int NO_CONTENT = 9;
/*    */   public static final int MIME_INCLUDED = 10;
/*    */   public static final int BYTE_HTML = 11;
/*    */   public static final int REDIRECT = 12;
/*    */   public static final int FILE = 13;
/*    */   public static final int TEXT_MHTML = 14;
/*    */   protected boolean cachable = false;
/*    */   protected boolean compressable = true;
/* 27 */   private int statusCode = 0;
/*    */   
/*    */   public static class FileProperties {
/*    */     public String filename;
/*    */     public String mime;
/*    */     public byte[] data;
/*    */     public boolean inline = false;
/*    */   }
/*    */   
/*    */   public ResultObject(int code, int statusCode, Object object) {
/* 37 */     super(code, object);
/* 38 */     this.statusCode = statusCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultObject(int code, Object object) {
/* 43 */     super(code, object);
/*    */   }
/*    */   
/*    */   public ResultObject(int code, boolean cachable, Object object) {
/* 47 */     super(code, object);
/* 48 */     this.cachable = cachable;
/*    */   }
/*    */   
/*    */   public ResultObject(int code, boolean cachable, boolean compressable, Object object) {
/* 52 */     super(code, object);
/* 53 */     this.cachable = cachable;
/* 54 */     this.compressable = compressable;
/*    */   }
/*    */   
/*    */   public boolean isCachable() {
/* 58 */     return this.cachable;
/*    */   }
/*    */   
/*    */   public boolean isCompressable() {
/* 62 */     return this.compressable;
/*    */   }
/*    */   
/*    */   public boolean hasStatusCode() {
/* 66 */     return (this.statusCode != 0);
/*    */   }
/*    */   
/*    */   public int getStatusCode() {
/* 70 */     return this.statusCode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\ResultObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */