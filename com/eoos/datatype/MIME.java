/*    */ package com.eoos.datatype;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MIME
/*    */ {
/*    */   public static final String IMAGE_GIF = "image/gif";
/*    */   public static final String IMAGE_JPG = "image/jpg";
/*    */   public static final String IMAGE_TIF = "image/tif";
/*    */   public static final String IMAGE_PNG = "image/png";
/*    */   public static final String IMAGE_BMP = "image/bmp";
/*    */   public static final String UNKNOWN = "application/";
/*    */   public static final String TEXT_HTML = "text/html";
/*    */   public static final String TEXT_CSS = "text/css";
/*    */   public static final String TEXT_PLAIN = "text/plain";
/*    */   public static final String DOCUMENT_PDF = "application/pdf";
/*    */   public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
/*    */   
/*    */   public static String getMIME(String _filename) {
/* 28 */     String filename = _filename.toLowerCase(Locale.ENGLISH);
/* 29 */     if (filename.indexOf(".jpg") != -1)
/* 30 */       return "image/jpg"; 
/* 31 */     if (filename.indexOf(".gif") != -1)
/* 32 */       return "image/gif"; 
/* 33 */     if (filename.indexOf(".tif") != -1)
/* 34 */       return "image/tif"; 
/* 35 */     if (filename.indexOf(".png") != -1)
/* 36 */       return "image/png"; 
/* 37 */     if (filename.indexOf(".htm") != -1)
/* 38 */       return "text/html"; 
/* 39 */     if (filename.indexOf(".css") != -1)
/* 40 */       return "text/css"; 
/* 41 */     if (filename.indexOf(".bmp") != -1)
/* 42 */       return "image/bmp"; 
/* 43 */     if (filename.indexOf(".pdf") != -1)
/* 44 */       return "application/pdf"; 
/* 45 */     if (filename.indexOf(".txt") != -1) {
/* 46 */       return "text/plain";
/*    */     }
/* 48 */     return "application/";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\MIME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */