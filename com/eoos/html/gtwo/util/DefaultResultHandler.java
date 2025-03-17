/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.html.gtwo.CodeRenderer;
/*    */ import com.eoos.html.gtwo.ResponseData;
/*    */ import java.util.Locale;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultResultHandler
/*    */ {
/* 19 */   protected static DefaultResultHandler instance = null;
/*    */   
/* 21 */   protected BinaryResponseDataWriter binaryWriter = null;
/* 22 */   protected TextResponseDataWriter textWriter = null;
/*    */ 
/*    */   
/*    */   protected DefaultResultHandler() {
/* 26 */     this.binaryWriter = new BinaryResponseDataWriter();
/* 27 */     this.textWriter = new TextResponseDataWriter(this.binaryWriter);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized DefaultResultHandler getInstance() {
/* 32 */     if (instance == null) {
/* 33 */       instance = new DefaultResultHandler();
/*    */     }
/* 35 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleResult(HttpServletRequest request, HttpServletResponse response, Object result) {
/* 41 */     if (result instanceof CodeRenderer) {
/* 42 */       String code = ((CodeRenderer)result).getHtmlCode(null).toString();
/* 43 */       result = new ResponseDataImpl(0, "text/html; charset=utf-8", code);
/* 44 */     } else if (result instanceof String) {
/* 45 */       result = new ResponseDataImpl(0, "text/plain; charset=utf-8", result);
/* 46 */     } else if (result instanceof byte[]) {
/* 47 */       result = new ResponseDataImpl(0, "application/octet-stream", result);
/*    */     } 
/*    */     
/* 50 */     if (result instanceof ResponseData) {
/* 51 */       String mime = ((ResponseData.MetaData)((ResponseData)result).getMetaData()).getMIME();
/* 52 */       mime = mime.toLowerCase(Locale.ENGLISH);
/* 53 */       if (mime.indexOf("text/") != -1) {
/* 54 */         this.textWriter.write(request, response, result);
/*    */       } else {
/* 56 */         this.binaryWriter.write(request, response, result);
/*    */       } 
/* 58 */     } else if (result instanceof ResponseHandler) {
/* 59 */       ((ResponseHandler)result).handleResult(request, response);
/*    */     } else {
/* 61 */       throw new RuntimeException("unknown result type");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\DefaultResultHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */