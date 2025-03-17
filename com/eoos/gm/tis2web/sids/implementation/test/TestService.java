/*     */ package com.eoos.gm.tis2web.sids.implementation.test;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.sids.implementation.DisplayableServiceIDAttrImpl;
/*     */ import com.eoos.gm.tis2web.sids.implementation.DisplayableServiceIDItem;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDService;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDServiceProvider;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestService
/*     */ {
/*     */   public static void main(String[] args) {
/*     */     boolean finished;
/*  29 */     DirectoryResourceLoading drl = new DirectoryResourceLoading(new File("C:/projects/GM/tis2web/delivery.refactored/build/env/geitel/dest/war"));
/*  30 */     FrameServiceProvider.create((ResourceLoading)drl);
/*     */ 
/*     */     
/*  33 */     ServiceIDServiceProvider sisProv = ServiceIDServiceProvider.getInstance();
/*  34 */     ServiceIDService sis = sisProv.getService();
/*     */     
/*  36 */     Locale loc = Locale.GERMANY;
/*     */ 
/*     */     
/*  39 */     SIAttrValMap map = new SIAttrValMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  73 */       finished = true;
/*     */       try {
/*  75 */         String vin = "1g4cw52k4y4125770";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  85 */         ServiceID sID = sis.getServiceID(loc, vin, map);
/*  86 */         System.out.println(sID);
/*  87 */         Collection attrs = map.getAttributes();
/*  88 */         Iterator<DisplayableServiceIDItem> it = attrs.iterator();
/*  89 */         while (it.hasNext()) {
/*  90 */           DisplayableServiceIDItem attr = it.next();
/*  91 */           DisplayableServiceIDItem val = (DisplayableServiceIDItem)map.getValue((Attribute)attr);
/*  92 */           System.out.println("attr/value=" + attr.getDenotation(null) + " : " + attr.getId() + " / " + val.getDenotation(null) + " : " + val.getId());
/*     */         }
/*     */       
/*     */       }
/*  96 */       catch (RequestException e) {
/*  97 */         finished = false;
/*  98 */         SelectionRequest sr = (SelectionRequest)e.getRequest();
/*  99 */         if (sr != null) {
/* 100 */           DisplayableServiceIDAttrImpl attr = (DisplayableServiceIDAttrImpl)sr.getAttribute();
/* 101 */           System.out.println("attr.getDenotation=" + attr.getDenotation(null));
/* 102 */           System.out.println("attr.getDescription=" + attr.getDescription());
/* 103 */           System.out.println("attr.getId=" + attr.getId());
/* 104 */           List opts = sr.getOptions();
/* 105 */           Iterator<DisplayableServiceIDItem> it = opts.iterator();
/* 106 */           int cur = 0;
/* 107 */           while (it.hasNext()) {
/* 108 */             DisplayableServiceIDItem val = it.next();
/*     */             
/* 110 */             System.out.println("val.getDenotation=" + val.getDenotation(null));
/* 111 */             System.out.println("val.getId=" + val.getId());
/* 112 */             cur++;
/* 113 */             if (val.getId() == 245)
/*     */             {
/* 115 */               map.set((Attribute)attr, (Value)val);
/*     */             }
/* 117 */             if (0 == map.map.keySet().size() && cur == opts.size()) {
/* 118 */               map.set((Attribute)attr, (Value)val);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 123 */       } catch (Exception e) {
/* 124 */         e.printStackTrace();
/*     */       } 
/* 126 */     } while (!finished);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\test\TestService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */