/*     */ package com.eoos.resource.loading;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServletResourceLoading
/*     */   extends ResourceLoadingImplAdapter
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(ServletResourceLoading.class);
/*     */   
/*     */   private HttpServlet servlet;
/*     */   
/*  29 */   private final Object SYNC_RESOURCES = new Object();
/*     */   
/*  31 */   private Collection resources = null;
/*     */   
/*     */   private Acceptor exclusionAcceptor;
/*     */   
/*     */   public ServletResourceLoading(HttpServlet servlet) {
/*  36 */     this.servlet = servlet;
/*  37 */     this.exclusionAcceptor = createExclusionAcceptor();
/*     */   }
/*     */   
/*     */   protected Acceptor createExclusionAcceptor() {
/*  41 */     final StringAcceptor a1 = StringAcceptor.create("*WEB-INF*", true);
/*  42 */     final StringAcceptor a2 = StringAcceptor.create("*META-INF*", true);
/*  43 */     return new Acceptor()
/*     */       {
/*     */         public boolean accept(Object object) {
/*  46 */           return (a1.accept(object) || a2.accept(object));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResource(Object identifier) throws IOException {
/*  54 */     return this.servlet.getServletContext().getResourceAsStream((String)identifier);
/*     */   }
/*     */   
/*     */   private boolean isDirectory(String path) {
/*  58 */     return (path.lastIndexOf("/") == path.length() - 1);
/*     */   }
/*     */   
/*     */   private boolean exclude(String path) {
/*  62 */     boolean retValue = false;
/*  63 */     if (this.exclusionAcceptor != null) {
/*  64 */       retValue = this.exclusionAcceptor.accept(path);
/*     */     }
/*  66 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection contentOf(String path) {
/*  71 */     log.debug("retrieving contentOf :" + String.valueOf(path));
/*  72 */     Collection retValue = null;
/*  73 */     if (!isDirectory(path) || exclude(path)) {
/*  74 */       retValue = Collections.EMPTY_SET;
/*     */     } else {
/*  76 */       retValue = new HashSet();
/*  77 */       Set paths = this.servlet.getServletContext().getResourcePaths(path);
/*  78 */       if (paths.contains(path)) {
/*  79 */         paths.remove(path);
/*     */       }
/*  81 */       if (paths != null) {
/*  82 */         retValue.addAll(paths);
/*  83 */         for (Iterator<String> iter = paths.iterator(); iter.hasNext();) {
/*  84 */           retValue.addAll(contentOf(iter.next()));
/*     */         }
/*     */       } 
/*     */     } 
/*  88 */     return retValue;
/*     */   }
/*     */   
/*     */   public Collection searchResources(Object searchPattern) {
/*  92 */     log.debug("searching resources for pattern: " + String.valueOf(searchPattern));
/*  93 */     synchronized (this.SYNC_RESOURCES) {
/*  94 */       if (this.resources == null) {
/*  95 */         this.resources = contentOf("/");
/*     */       }
/*  97 */       final StringAcceptor acceptor = StringAcceptor.create((String)searchPattern, true);
/*  98 */       Filter filter = new Filter() {
/*     */           public boolean include(Object object) {
/* 100 */             boolean retValue = false;
/*     */             try {
/* 102 */               retValue = acceptor.accept(object);
/* 103 */             } catch (Exception e) {}
/*     */             
/* 105 */             return retValue;
/*     */           }
/*     */         };
/*     */       
/* 109 */       Collection retValue = CollectionUtil.filterAndReturn(new LinkedHashSet(this.resources), filter);
/* 110 */       log.debug("found :" + String.valueOf(retValue));
/* 111 */       return retValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\ServletResourceLoading.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */