/*    */ package com.eoos.gm.tis2web.frame.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ResourceService;
/*    */ import com.eoos.resource.loading.ResourceLoading;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceServiceImpl
/*    */   implements ResourceService
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(ResourceServiceImpl.class);
/*    */   
/* 17 */   private ResourceLoading resourceLoading = null;
/*    */   
/*    */   public ResourceServiceImpl(ResourceLoading resourceLoading) {
/* 20 */     this.resourceLoading = resourceLoading;
/* 21 */     log.debug("resource service created, passed resource loading interface is: " + String.valueOf(resourceLoading));
/*    */   }
/*    */   
/*    */   public boolean existsResource(String name) {
/* 25 */     return this.resourceLoading.existsResource(name);
/*    */   }
/*    */   
/*    */   public InputStream getResource(String name) throws IOException {
/* 29 */     return this.resourceLoading.getResource(name);
/*    */   }
/*    */   
/*    */   public Collection searchResources(String searchPattern) {
/* 33 */     return this.resourceLoading.searchResources(searchPattern);
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\ResourceServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */