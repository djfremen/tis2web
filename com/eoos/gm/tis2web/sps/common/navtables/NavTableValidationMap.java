/*    */ package com.eoos.gm.tis2web.sps.common.navtables;
/*    */ 
/*    */ import com.eoos.util.v2.Base64DecodingStream;
/*    */ import com.eoos.util.v2.Base64EncodingStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.StringReader;
/*    */ import java.io.StringWriter;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class NavTableValidationMap extends AbstractMap {
/* 21 */   private static final Logger log = Logger.getLogger(NavTableValidationMap.class);
/*    */   
/*    */   private Map backend;
/*    */   
/*    */   public NavTableValidationMap() {
/* 26 */     this.backend = Collections.synchronizedMap(new HashMap<Object, Object>());
/*    */   }
/*    */ 
/*    */   
/*    */   private NavTableValidationMap(Map backend) {
/* 31 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   public Object put(Object key, Object value) {
/* 35 */     return this.backend.put(key, value);
/*    */   }
/*    */   
/*    */   public Set entrySet() {
/* 39 */     return this.backend.entrySet();
/*    */   }
/*    */   
/*    */   public static NavTableValidationMap createInstance(String externalForm) {
/* 43 */     log.info("creating instance from external form");
/* 44 */     if (externalForm == null || externalForm.trim().length() == 0) {
/* 45 */       log.debug("external form is emptry, creating emptry instance");
/* 46 */       return new NavTableValidationMap();
/*    */     } 
/*    */     try {
/* 49 */       StringReader sr = new StringReader(externalForm);
/* 50 */       Base64DecodingStream decS = new Base64DecodingStream(sr);
/* 51 */       GZIPInputStream gzis = new GZIPInputStream((InputStream)decS);
/* 52 */       ObjectInputStream ois = new ObjectInputStream(gzis);
/* 53 */       Map map = (Map)ois.readObject();
/* 54 */       ois.close();
/* 55 */       return new NavTableValidationMap(map);
/* 56 */     } catch (Exception e) {
/* 57 */       log.error("unable to create instance - exception:" + e + ", rethrowing as runtime exception");
/* 58 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toExternalFrom() {
/* 64 */     log.info("externalizing");
/*    */     try {
/* 66 */       StringWriter sw = new StringWriter();
/* 67 */       Base64EncodingStream decS = new Base64EncodingStream(sw);
/* 68 */       GZIPOutputStream gzos = new GZIPOutputStream((OutputStream)decS);
/* 69 */       ObjectOutputStream oos = new ObjectOutputStream(gzos);
/* 70 */       oos.writeObject(this.backend);
/* 71 */       oos.close();
/* 72 */       return sw.toString();
/*    */     }
/* 74 */     catch (Exception e) {
/* 75 */       log.error("unable to externalize exception:" + e + " , rethrowing as runtime exception");
/* 76 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\navtables\NavTableValidationMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */