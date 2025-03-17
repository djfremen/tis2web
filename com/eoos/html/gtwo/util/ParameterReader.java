/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.tokenizer.StringTokenizer;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParameterReader
/*    */ {
/*    */   protected String defaultEncoding;
/*    */   
/*    */   public ParameterReader(String defaultEncoding) {
/* 26 */     this.defaultEncoding = defaultEncoding;
/*    */   }
/*    */   
/*    */   public Map getParameterMap(HttpSession session, HttpServletRequest request) {
/*    */     try {
/* 31 */       Map<Object, Object> retValue = new HashMap<Object, Object>();
/*    */ 
/*    */       
/* 34 */       if (request.getCharacterEncoding() == null) {
/* 35 */         request.setCharacterEncoding(this.defaultEncoding);
/*    */       }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 42 */       Enumeration<String> enumeration = request.getHeaderNames();
/* 43 */       while (enumeration.hasMoreElements()) {
/* 44 */         String strKey = enumeration.nextElement();
/* 45 */         retValue.put(strKey, request.getHeader(strKey));
/*    */       } 
/*    */ 
/*    */       
/* 49 */       enumeration = session.getAttributeNames();
/* 50 */       while (enumeration.hasMoreElements()) {
/* 51 */         String strKey = enumeration.nextElement();
/* 52 */         retValue.put(strKey, session.getAttribute(strKey));
/*    */       } 
/*    */ 
/*    */       
/* 56 */       enumeration = request.getAttributeNames();
/* 57 */       while (enumeration.hasMoreElements()) {
/* 58 */         String strKey = enumeration.nextElement();
/* 59 */         retValue.put(strKey, request.getAttribute(strKey));
/*    */       } 
/*    */ 
/*    */       
/* 63 */       enumeration = request.getParameterNames();
/* 64 */       while (enumeration.hasMoreElements()) {
/* 65 */         String strKey = enumeration.nextElement();
/* 66 */         String[] values = request.getParameterValues(strKey);
/* 67 */         if (values != null && values.length > 1) {
/* 68 */           retValue.put(strKey, request.getParameterValues(strKey)); continue;
/*    */         } 
/* 70 */         retValue.put(strKey, request.getParameter(strKey));
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 75 */       retValue.putAll(extractPath(request.getRequestURL().toString()));
/*    */       
/* 77 */       return retValue;
/* 78 */     } catch (Exception e) {
/* 79 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Map extractPath(String path) {
/* 86 */     Map<Object, Object> retValue = new HashMap<Object, Object>();
/* 87 */     StringTokenizer tokenizer = new StringTokenizer(path, "/");
/* 88 */     for (Iterator<String> iter = tokenizer.iterator(); iter.hasNext(); ) {
/* 89 */       String token = iter.next();
/*    */ 
/*    */       
/* 92 */       int index = token.indexOf("=");
/* 93 */       if (index != -1) {
/* 94 */         String key = token.substring(0, index);
/* 95 */         String value = token.substring(index + 1);
/* 96 */         retValue.put(key, value);
/*    */       } 
/*    */     } 
/* 99 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\ParameterReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */