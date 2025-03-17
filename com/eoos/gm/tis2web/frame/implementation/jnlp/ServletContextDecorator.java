/*     */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.Servlet;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ 
/*     */ public class ServletContextDecorator
/*     */   implements ServletContext
/*     */ {
/*     */   protected ServletContext context;
/*     */   
/*     */   public ServletContextDecorator(ServletContext context) {
/*  20 */     this.context = context;
/*     */   }
/*     */   
/*     */   public ServletContext getContext(String uripath) {
/*  24 */     return this.context.getContext(uripath);
/*     */   }
/*     */   
/*     */   public int getMajorVersion() {
/*  28 */     return this.context.getMajorVersion();
/*     */   }
/*     */   
/*     */   public int getMinorVersion() {
/*  32 */     return this.context.getMinorVersion();
/*     */   }
/*     */   
/*     */   public String getMimeType(String file) {
/*  36 */     return this.context.getMimeType(file);
/*     */   }
/*     */   
/*     */   public Set getResourcePaths(String path) {
/*  40 */     return this.context.getResourcePaths(path);
/*     */   }
/*     */   
/*     */   public URL getResource(String path) throws MalformedURLException {
/*  44 */     if (path.startsWith("db")) {
/*  45 */       if (path.endsWith(".pack.gz")) {
/*  46 */         return DatabaseResourceURL.makeDatabaseResourceURL(path, true);
/*     */       }
/*  48 */       return DatabaseResourceURL.makeDatabaseResourceURL(path, false);
/*     */     } 
/*  50 */     return this.context.getResource(path);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String path) {
/*  55 */     return this.context.getResourceAsStream(path);
/*     */   }
/*     */   
/*     */   public RequestDispatcher getRequestDispatcher(String path) {
/*  59 */     return this.context.getRequestDispatcher(path);
/*     */   }
/*     */   
/*     */   public RequestDispatcher getNamedDispatcher(String name) {
/*  63 */     return this.context.getNamedDispatcher(name);
/*     */   }
/*     */   
/*     */   public Servlet getServlet(String name) throws ServletException {
/*  67 */     return null;
/*     */   }
/*     */   
/*     */   public Enumeration getServlets() {
/*  71 */     Vector v = new Vector();
/*  72 */     return v.elements();
/*     */   }
/*     */   
/*     */   public Enumeration getServletNames() {
/*  76 */     Vector v = new Vector();
/*  77 */     return v.elements();
/*     */   }
/*     */   
/*     */   public void log(String msg) {
/*  81 */     this.context.log(msg);
/*     */   }
/*     */   
/*     */   public void log(Exception exception, String msg) {
/*  85 */     this.context.log(msg, exception);
/*     */   }
/*     */   
/*     */   public void log(String message, Throwable throwable) {
/*  89 */     this.context.log(message, throwable);
/*     */   }
/*     */   
/*     */   public String getRealPath(String path) {
/*  93 */     if (path.startsWith("db")) {
/*  94 */       return null;
/*     */     }
/*  96 */     return this.context.getRealPath(path);
/*     */   }
/*     */   
/*     */   public String getServerInfo() {
/* 100 */     return this.context.getServerInfo();
/*     */   }
/*     */   
/*     */   public String getInitParameter(String name) {
/* 104 */     return this.context.getInitParameter(name);
/*     */   }
/*     */   
/*     */   public Enumeration getInitParameterNames() {
/* 108 */     return this.context.getInitParameterNames();
/*     */   }
/*     */   
/*     */   public Object getAttribute(String name) {
/* 112 */     return this.context.getAttribute(name);
/*     */   }
/*     */   
/*     */   public Enumeration getAttributeNames() {
/* 116 */     return this.context.getAttributeNames();
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, Object object) {
/* 120 */     this.context.setAttribute(name, object);
/*     */   }
/*     */   
/*     */   public void removeAttribute(String name) {
/* 124 */     this.context.removeAttribute(name);
/*     */   }
/*     */   
/*     */   public String getServletContextName() {
/* 128 */     return this.context.getServletContextName();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\ServletContextDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */