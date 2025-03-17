/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.html.gtwo.ResponseData;
/*    */ import com.eoos.html.gtwo.ResponseWriter;
/*    */ import com.eoos.util.ZipUtil;
/*    */ import javax.servlet.ServletOutputStream;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BinaryResponseDataWriter
/*    */   implements ResponseWriter
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(BinaryResponseDataWriter.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setCacheHeaders(HttpServletResponse response, int cacheType) {
/* 31 */     switch (cacheType) {
/*    */       case 0:
/* 33 */         response.setHeader("Pragma", "no-cache");
/* 34 */         response.setHeader("Cache-Control", "no-cache");
/* 35 */         response.setDateHeader("Expires", 0L);
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/* 40 */         response.setHeader("Cache-Control", "private");
/*    */         break;
/*    */       
/*    */       case 1:
/* 44 */         response.setHeader("Cache-Control", "public");
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(HttpServletRequest request, HttpServletResponse response, Object data) {
/*    */     try {
/* 53 */       ResponseData responseData = (ResponseData)data;
/* 54 */       ResponseData.MetaData metaData = (ResponseData.MetaData)responseData.getMetaData();
/*    */ 
/*    */       
/* 57 */       byte[] _data = (byte[])responseData.getData();
/* 58 */       if (_data == null) {
/* 59 */         response.setStatus(204);
/*    */         
/*    */         return;
/*    */       } 
/* 63 */       response.setContentType(metaData.getMIME());
/*    */ 
/*    */       
/* 66 */       setCacheHeaders(response, metaData.getCacheType());
/*    */ 
/*    */       
/* 69 */       String encoding = request.getHeader("Accept-Encoding");
/* 70 */       if (encoding != null && encoding.indexOf("gzip") != -1) {
/* 71 */         _data = ZipUtil.gzip(_data);
/* 72 */         response.setHeader("Content-Encoding", "gzip");
/*    */       } 
/* 74 */       response.setContentLength(_data.length);
/* 75 */       ServletOutputStream out = response.getOutputStream();
/* 76 */       out.write(_data);
/*    */     }
/* 78 */     catch (Exception e) {
/* 79 */       log.error("unable to write to reponse object - exception:" + e, e);
/* 80 */       log.error("throwing exception wrapper");
/* 81 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\BinaryResponseDataWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */