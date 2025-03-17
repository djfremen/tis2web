/*    */ package com.eoos.resource.loading;
/*    */ 
/*    */ import com.eoos.automat.StringAcceptor;
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectoryResourceLoading
/*    */   extends ResourceLoadingImplAdapter
/*    */ {
/*    */   private File base;
/* 26 */   private final Object SYNC_RESOURCES = new Object();
/*    */   
/* 28 */   private Collection resources = null;
/*    */   
/*    */   public DirectoryResourceLoading(File base) {
/* 31 */     this.base = base;
/*    */   }
/*    */   
/*    */   public InputStream getResource(Object identifier) throws IOException {
/* 35 */     File file = new File(this.base, (String)identifier);
/* 36 */     return new BufferedInputStream(new FileInputStream(file));
/*    */   }
/*    */   
/*    */   private Collection contentOf(File file) {
/* 40 */     Collection<File> retValue = null;
/* 41 */     if (!file.isDirectory()) {
/* 42 */       retValue = Collections.EMPTY_SET;
/*    */     } else {
/* 44 */       retValue = new HashSet();
/* 45 */       File[] files = file.listFiles();
/* 46 */       for (int i = 0; i < files.length; i++) {
/* 47 */         File child = files[i];
/* 48 */         retValue.add(child);
/* 49 */         retValue.addAll(contentOf(child));
/*    */       } 
/*    */     } 
/* 52 */     return retValue;
/*    */   }
/*    */   
/*    */   public Collection getResources() {
/* 56 */     synchronized (this.SYNC_RESOURCES) {
/* 57 */       if (this.resources == null) {
/* 58 */         this.resources = new HashSet();
/* 59 */         String prefix = this.base.getPath();
/* 60 */         Collection files = contentOf(this.base);
/* 61 */         for (Iterator<File> iter = files.iterator(); iter.hasNext(); ) {
/* 62 */           File file = iter.next();
/* 63 */           String path = file.getPath();
/* 64 */           int index = path.indexOf(prefix);
/* 65 */           if (index != -1) {
/* 66 */             path = path.substring(index + prefix.length());
/*    */           }
/* 68 */           path = StringUtilities.replace(path, File.separator, "/");
/* 69 */           this.resources.add(path);
/*    */         } 
/*    */       } 
/* 72 */       return this.resources;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection searchResources(Object searchPattern) {
/* 78 */     final StringAcceptor acceptor = StringAcceptor.create((String)searchPattern, true);
/* 79 */     Filter filter = new Filter() {
/*    */         public boolean include(Object object) {
/* 81 */           boolean retValue = false;
/*    */           try {
/* 83 */             retValue = acceptor.accept(object);
/* 84 */           } catch (Exception e) {}
/*    */           
/* 86 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 90 */     return CollectionUtil.filterAndReturn(new LinkedHashSet(getResources()), filter);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 95 */     DirectoryResourceLoading drl = new DirectoryResourceLoading(new File("d:/tmp"));
/* 96 */     System.out.println(drl.searchResources("*com/*MailService*"));
/* 97 */     String tmp = new String((byte[])drl.loadResource("/META-INF/MANIFEST.MF"));
/* 98 */     System.out.println(tmp);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\DirectoryResourceLoading.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */