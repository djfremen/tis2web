/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.v2.Base64DecodingStream;
/*    */ import com.eoos.util.v2.Base64EncodingStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.StringReader;
/*    */ import java.io.StringWriter;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ 
/*    */ public class NavigationTableFilter {
/*    */   private Collection entries;
/*    */   
/*    */   public NavigationTableFilter(Collection<?> entries) {
/* 23 */     this.entries = new LinkedHashSet(entries);
/*    */   }
/*    */   
/*    */   public String toExternal() {
/* 27 */     if (Util.isNullOrEmpty(this.entries)) {
/* 28 */       return "";
/*    */     }
/*    */     try {
/* 31 */       StringWriter sw = new StringWriter();
/* 32 */       Base64EncodingStream eos = new Base64EncodingStream(sw);
/* 33 */       GZIPOutputStream gzos = new GZIPOutputStream((OutputStream)eos);
/* 34 */       ObjectOutputStream oos = new ObjectOutputStream(gzos);
/* 35 */       oos.writeObject(this.entries);
/* 36 */       oos.close();
/* 37 */       return sw.toString();
/* 38 */     } catch (IOException e) {
/* 39 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static NavigationTableFilter fromExternal(String externalForm) {
/*    */     try {
/* 46 */       Collection c = null;
/* 47 */       if (Util.isNullOrEmpty(externalForm)) {
/* 48 */         c = Collections.EMPTY_SET;
/*    */       } else {
/* 50 */         StringReader sr = new StringReader(externalForm);
/* 51 */         Base64DecodingStream dis = new Base64DecodingStream(sr);
/* 52 */         GZIPInputStream gzis = new GZIPInputStream((InputStream)dis);
/* 53 */         ObjectInputStream ois = new ObjectInputStream(gzis);
/*    */         try {
/* 55 */           c = (Collection)ois.readObject();
/* 56 */         } catch (ClassNotFoundException e) {
/* 57 */           throw new Error(e);
/*    */         } 
/* 59 */         ois.close();
/*    */       } 
/* 61 */       return new NavigationTableFilter(c);
/* 62 */     } catch (IOException e) {
/* 63 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection getEntries() {
/* 68 */     return this.entries;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\NavigationTableFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */