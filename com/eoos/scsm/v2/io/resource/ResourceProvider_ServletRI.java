/*     */ package com.eoos.scsm.v2.io.resource;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.ServletContext;
/*     */ 
/*     */ public class ResourceProvider_ServletRI
/*     */   implements ResourceProvider, ResourceProvider.Search
/*     */ {
/*     */   private ServletContext servletContext;
/*     */   private String root;
/*     */   
/*     */   private static class OwnSearchFilter
/*     */     implements ResourceProvider.Search.Filter {
/*     */     public Pattern namePattern;
/*     */     
/*     */     private OwnSearchFilter() {}
/*     */   }
/*     */   
/*     */   public ResourceProvider_ServletRI(ServletContext servletContext, String root) {
/*  25 */     this.servletContext = servletContext;
/*  26 */     this.root = root;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream(Object resource) throws IOException {
/*  31 */     return this.servletContext.getResourceAsStream(String.valueOf(resource));
/*     */   }
/*     */   
/*     */   public static interface ResourceVisitor
/*     */   {
/*     */     void onVisit(Object param1Object) throws StopVisitException;
/*     */     
/*     */     public static class StopVisitException
/*     */       extends Exception {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public StopVisitException() {}
/*     */       
/*  44 */       public StopVisitException(Throwable cause) { super(cause); } } } public static class StopVisitException extends Exception { public StopVisitException(Throwable cause) { super(cause); }
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public StopVisitException() {} }
/*     */   
/*     */   private boolean isDir(String resource) {
/*  52 */     return resource.endsWith("/");
/*     */   }
/*     */   
/*     */   private void visitResources(String currentPath, ResourceVisitor visitor) throws ResourceVisitor.StopVisitException {
/*  56 */     for (Iterator<String> iter = this.servletContext.getResourcePaths(currentPath).iterator(); iter.hasNext(); ) {
/*  57 */       String resource = iter.next();
/*  58 */       if (isDir(resource)) {
/*  59 */         visitResources(resource, visitor); continue;
/*     */       } 
/*  61 */       visitor.onVisit(resource);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getResources() {
/*  67 */     final Collection ret = new HashSet();
/*     */     try {
/*  69 */       visitResources(this.root, new ResourceVisitor()
/*     */           {
/*     */             public void onVisit(Object resource) throws ResourceProvider_ServletRI.ResourceVisitor.StopVisitException {
/*  72 */               ret.add(resource);
/*     */             }
/*     */           });
/*  75 */     } catch (StopVisitException e) {}
/*     */ 
/*     */     
/*  78 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection searchResources(ResourceProvider.Search.Filter filter) {
/*  82 */     if (filter == null) {
/*  83 */       return getResources();
/*     */     }
/*  85 */     final Pattern namePattern = ((OwnSearchFilter)filter).namePattern;
/*  86 */     final Collection ret = new HashSet();
/*     */     try {
/*  88 */       visitResources(this.root, new ResourceVisitor()
/*     */           {
/*     */             public void onVisit(Object resource) throws ResourceProvider_ServletRI.ResourceVisitor.StopVisitException {
/*  91 */               String name = ResourceProvider_ServletRI.this.getName(resource);
/*  92 */               if (namePattern.matcher(name).matches()) {
/*  93 */                 ret.add(resource);
/*     */               }
/*     */             }
/*     */           });
/*  97 */     } catch (StopVisitException e) {}
/*     */     
/*  99 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(Object resource) {
/* 104 */     String ret = (String)resource;
/* 105 */     if (!ret.startsWith(this.root)) {
/* 106 */       throw new IllegalArgumentException("resource was not provided by this provider");
/*     */     }
/* 108 */     return ret.substring(this.root.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceProvider.Search.Filter createSearchFilter(Pattern namePattern) {
/* 113 */     OwnSearchFilter ret = new OwnSearchFilter();
/* 114 */     ret.namePattern = namePattern;
/* 115 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\resource\ResourceProvider_ServletRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */