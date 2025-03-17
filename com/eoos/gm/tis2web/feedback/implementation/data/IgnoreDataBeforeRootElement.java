/*    */ package com.eoos.gm.tis2web.feedback.implementation.data;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IgnoreDataBeforeRootElement
/*    */ {
/*    */   protected static boolean isTagOpen(byte b) {
/* 15 */     switch (b) {
/*    */       case 60:
/* 17 */         return true;
/*    */     } 
/* 19 */     return false;
/*    */   }
/*    */   
/*    */   protected static boolean isIgnorableByte(byte b) {
/* 23 */     switch (b) {
/*    */       case 10:
/* 25 */         return true;
/*    */       
/*    */       case 13:
/* 28 */         return true;
/*    */       
/*    */       case 9:
/* 31 */         return true;
/*    */       
/*    */       case 32:
/* 34 */         return true;
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   protected static boolean compareBytesStartFeedbackRootElement(byte[] template, int pos, byte[] startFeedbackRootElement) {
/* 40 */     if (startFeedbackRootElement.length > template.length - pos)
/* 41 */       return false; 
/* 42 */     for (int i = 0; i < startFeedbackRootElement.length; i++) {
/* 43 */       if (startFeedbackRootElement[i] != template[i + pos])
/* 44 */         return false; 
/*    */     } 
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   protected static byte[] makeByteArray(ByteArrayInputStream template) throws IOException {
/* 50 */     byte[] bytes = new byte[template.available()];
/* 51 */     if (template.read(bytes) != bytes.length) {
/* 52 */       log.error("failed to transfer template byte array.");
/* 53 */       throw new RuntimeException();
/*    */     } 
/* 55 */     return bytes;
/*    */   }
/*    */ 
/*    */   
/*    */   protected static ByteArrayInputStream makeByteArrayInputStream(byte[] template, int offset) {
/* 60 */     return new ByteArrayInputStream(template, offset, template.length + offset);
/*    */   }
/*    */   
/*    */   public static ByteArrayInputStream compareByteArray(ByteArrayInputStream template) throws IOException {
/* 64 */     byte[] rootElement = "Feedback ".getBytes("utf-8");
/* 65 */     byte[] bytes = makeByteArray(template);
/* 66 */     int offset = findRootElementStartPos(bytes, rootElement);
/* 67 */     if (offset != -1) {
/* 68 */       return makeByteArrayInputStream(bytes, offset);
/*    */     }
/* 70 */     return makeByteArrayInputStream(bytes, 0);
/*    */   }
/*    */   
/*    */   protected static int findRootElementStartPos(byte[] template, byte[] rootElement) throws UnsupportedEncodingException {
/* 74 */     int ret = -1;
/* 75 */     int end = template.length;
/* 76 */     for (int idx = 0; idx < end; idx++) {
/* 77 */       if (isTagOpen(template[idx])) {
/*    */         int emptyIdx;
/* 79 */         for (emptyIdx = idx + 1; isIgnorableByte(template[emptyIdx]); emptyIdx++);
/*    */         
/* 81 */         if (compareBytesStartFeedbackRootElement(template, emptyIdx, rootElement))
/* 82 */           return idx; 
/*    */       } 
/*    */     } 
/* 85 */     return ret;
/*    */   }
/*    */   
/*    */   static Class _mthclass$(String x0) {
/*    */     try {
/* 90 */       return Class.forName(x0);
/* 91 */     } catch (ClassNotFoundException x1) {
/* 92 */       throw new NoClassDefFoundError(x1.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 99 */   private static final Logger log = Logger.getLogger(IgnoreDataBeforeRootElement.class);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementation\data\IgnoreDataBeforeRootElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */