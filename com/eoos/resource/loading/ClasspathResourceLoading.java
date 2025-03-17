/*    */ package com.eoos.resource.loading;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class ClasspathResourceLoading
/*    */   extends ResourceLoadingImplAdapter
/*    */ {
/*    */   private Class clazz;
/*    */   
/*    */   public ClasspathResourceLoading(Object principal) {
/* 14 */     this.clazz = principal.getClass();
/*    */   }
/*    */   
/*    */   public ClasspathResourceLoading(Class clazz) {
/* 18 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public InputStream getResource(Object identifier) throws IOException {
/* 22 */     InputStream ret = null;
/* 23 */     ClassLoader cl = this.clazz.getClassLoader();
/* 24 */     String pckg = this.clazz.getName().replace('.', '/');
/* 25 */     while ((ret = cl.getResourceAsStream(pckg + "/" + String.valueOf(identifier))) == null && pckg.lastIndexOf("/") != -1) {
/* 26 */       pckg = pckg.substring(0, pckg.lastIndexOf("/"));
/*    */     }
/* 28 */     return ret;
/*    */   }
/*    */   
/*    */   public Collection searchResources(Object searchPattern) {
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 36 */     ClasspathResourceLoading crl = new ClasspathResourceLoading(ClasspathResourceLoading.class);
/* 37 */     System.out.println(new String(StreamUtil.readFully(crl.getResource("text.txt"))));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\ClasspathResourceLoading.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */