/*    */ package com.eoos.gm.tis2web.sps.server.implementation.brand;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Brand;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class BrandProvider
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(BrandProvider.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/* 23 */   private final Object SYNC = new Object();
/*    */   
/* 25 */   private Set brands = null;
/*    */   
/*    */   private BrandProvider(ClientContext context) {
/* 28 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static BrandProvider getInstance(ClientContext context) {
/* 32 */     synchronized (context.getLockObject()) {
/* 33 */       BrandProvider instance = (BrandProvider)context.getObject(BrandProvider.class);
/* 34 */       if (instance == null) {
/* 35 */         instance = new BrandProvider(context);
/* 36 */         context.storeObject(BrandProvider.class, instance);
/*    */       } 
/* 38 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set getBrands() {
/* 43 */     synchronized (this.SYNC) {
/* 44 */       if (this.brands == null) {
/*    */         
/* 46 */         Set<Brand> globalBrands = new HashSet();
/* 47 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.sps.brand.");
/* 48 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 49 */           String brand = subConfigurationWrapper.getProperty(iter.next());
/* 50 */           globalBrands.add(Brand.getInstance(brand));
/*    */         } 
/*    */         
/* 53 */         if (ApplicationContext.getInstance().developMode()) {
/* 54 */           log.warn("!!! DEVELOP MODE - allowing access to all brands");
/* 55 */           this.brands = globalBrands;
/*    */         } else {
/* 57 */           Set<Brand> aclBrands = new HashSet();
/* 58 */           ACLService service = ACLServiceProvider.getInstance().getService();
/* 59 */           Set brandIdentifiers = service.getAuthorizedResources("Brand", this.context.getSharedContext().getUsrGroup2ManufMap(), this.context.getSharedContext().getCountry());
/* 60 */           for (Iterator<String> iterator = brandIdentifiers.iterator(); iterator.hasNext(); ) {
/* 61 */             String brandID = iterator.next();
/* 62 */             aclBrands.add(Brand.getInstance(brandID));
/*    */           } 
/*    */           
/* 65 */           this.brands = (Set)CollectionUtil.intersectAndReturn(globalBrands, aclBrands);
/*    */         } 
/*    */       } 
/* 68 */       return this.brands;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\brand\BrandProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */