/*    */ package com.eoos.mail;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataSource;
/*    */ 
/*    */ public class DataSourceAdapter
/*    */   implements DataSource
/*    */ {
/*    */   private DataSource delegate;
/*    */   
/*    */   public DataSourceAdapter(DataSource delegate) {
/* 14 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public String getContentType() {
/* 18 */     return this.delegate.getContentType();
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 22 */     return this.delegate.getInputStream();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 26 */     return this.delegate.getName();
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream() throws IOException {
/* 30 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\DataSourceAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */