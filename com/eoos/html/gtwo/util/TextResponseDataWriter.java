/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.SectionIndex;
/*    */ import com.eoos.html.gtwo.ResponseData;
/*    */ import com.eoos.html.gtwo.ResponseWriter;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.regexp.RE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextResponseDataWriter
/*    */   implements ResponseWriter
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(TextResponseDataWriter.class);
/*    */   
/*    */   protected String encoding;
/*    */   
/*    */   protected BinaryResponseDataWriter binaryWriter;
/*    */   protected static final RE reCharset;
/*    */   
/*    */   static {
/*    */     try {
/* 33 */       reCharset = new RE("[Cc][Hh][Aa][Rr][Ss][Ee][Tt]=[^\\s]*");
/* 34 */     } catch (Exception e) {
/* 35 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TextResponseDataWriter(BinaryResponseDataWriter binaryWriter) {
/* 44 */     this.binaryWriter = (binaryWriter != null) ? binaryWriter : new BinaryResponseDataWriter();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(HttpServletRequest request, HttpServletResponse response, Object data) {
/*    */     try {
/*    */       byte[] _data;
/* 53 */       ResponseData responseData = (ResponseData)data;
/* 54 */       ResponseData.MetaData metaData = (ResponseData.MetaData)responseData.getMetaData();
/*    */ 
/*    */       
/* 57 */       String charset = "us-ascii";
/*    */       
/* 59 */       String mime = metaData.getMIME();
/* 60 */       SectionIndex index = StringUtilities.getSectionIndex(mime, reCharset, 0);
/* 61 */       if (index != null) {
/* 62 */         String charsetDeclaration = StringUtilities.getSectionContent(mime, index);
/* 63 */         charset = charsetDeclaration.substring(charsetDeclaration.lastIndexOf('=') + 1);
/*    */       } 
/*    */       
/* 66 */       String textData = (String)responseData.getData();
/* 67 */       if (textData == null) {
/* 68 */         response.setStatus(204);
/*    */         
/*    */         return;
/*    */       } 
/*    */       
/*    */       try {
/* 74 */         _data = textData.getBytes(charset);
/* 75 */       } catch (UnsupportedEncodingException e) {
/* 76 */         log.error("unable to convert to binary with charset:" + charset);
/* 77 */         throw e;
/*    */       } 
/*    */       
/* 80 */       ResponseData binaryData = new ResponseDataImpl(metaData.getCacheType(), mime, _data);
/* 81 */       this.binaryWriter.write(request, response, binaryData);
/*    */     }
/* 83 */     catch (Exception e) {
/* 84 */       log.error("unable to write to response object - exception:" + e, e);
/* 85 */       log.error("throwing exception wrapper");
/* 86 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\TextResponseDataWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */