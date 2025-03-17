/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
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
/*    */ public class ParameterMapImpl
/*    */   implements ParameterMap
/*    */ {
/*    */   private static final String KEY_SESSION = "http.session";
/*    */   private static final String KEY_REQUEST = "http.request";
/*    */   private static final String KEY_RESPONSE = "http.response";
/* 25 */   protected Map map = null;
/*    */   
/*    */   public ParameterMapImpl(HttpSession session, HttpServletRequest request, HttpServletResponse response, Map<String, HttpSession> parameterMap) {
/* 28 */     parameterMap.put("http.session", session);
/* 29 */     parameterMap.put("http.request", request);
/* 30 */     parameterMap.put("http.response", response);
/* 31 */     this.map = parameterMap;
/*    */   }
/*    */   
/*    */   public HttpSession getSession() {
/* 35 */     return (HttpSession)this.map.get("http.session");
/*    */   }
/*    */   
/*    */   public HttpServletRequest getRequest() {
/* 39 */     return (HttpServletRequest)this.map.get("http.request");
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpServletResponse getResponse() {
/* 44 */     return (HttpServletResponse)this.map.get("http.response");
/*    */   }
/*    */   
/*    */   public int size() {
/* 48 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 52 */     return this.map.isEmpty();
/*    */   }
/*    */   
/*    */   public boolean containsKey(Object key) {
/* 56 */     return this.map.containsKey(key);
/*    */   }
/*    */   
/*    */   public boolean containsValue(Object value) {
/* 60 */     return this.map.containsValue(value);
/*    */   }
/*    */   
/*    */   public Object get(Object key) {
/* 64 */     return this.map.get(key);
/*    */   }
/*    */   
/*    */   public Object put(Object key, Object value) {
/* 68 */     return this.map.put(key, value);
/*    */   }
/*    */   
/*    */   public Object remove(Object key) {
/* 72 */     return this.map.remove(key);
/*    */   }
/*    */   
/*    */   public void putAll(Map t) {
/* 76 */     this.map.putAll(t);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 80 */     this.map.clear();
/*    */   }
/*    */   
/*    */   public Set keySet() {
/* 84 */     return this.map.keySet();
/*    */   }
/*    */   
/*    */   public Collection values() {
/* 88 */     return this.map.values();
/*    */   }
/*    */   
/*    */   public Set entrySet() {
/* 92 */     return this.map.entrySet();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\ParameterMapImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */