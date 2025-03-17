/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*    */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationEntity;
/*    */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SubscriptionProvider
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(SubscriptionProvider.class);
/*    */   
/* 20 */   private static SubscriptionProvider instance = new SubscriptionProvider();
/*    */   
/* 22 */   private List subscriptions = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void init(ClientContext context) {
/* 28 */     this.subscriptions = new LinkedList();
/*    */     try {
/* 30 */       if (ApplicationContext.getInstance().isStandalone()) {
/* 31 */         SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/* 32 */         for (Iterator<Subscription> iter = service.getAuthorizations().iterator(); iter.hasNext(); ) {
/* 33 */           Subscription ae = iter.next();
/* 34 */           this.subscriptions.add(new SubscriptionAdapter(ae));
/*    */         } 
/*    */       } else {
/* 37 */         List tmp = RegistrationProvider.getInstance().getService().getAuthorizations();
/* 38 */         for (Iterator<AuthorizationEntity> iter = tmp.iterator(); iter.hasNext(); ) {
/* 39 */           AuthorizationEntity ae = iter.next();
/* 40 */           this.subscriptions.add(new SubscriptionAdapter((Subscription)ae));
/*    */         } 
/*    */       } 
/* 43 */     } catch (Exception e) {
/* 44 */       log.error("unable to init subscriptions, continuing with empty subscription list - exception:" + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static SubscriptionProvider getInstance() {
/* 49 */     return instance;
/*    */   }
/*    */   
/*    */   public List getSubscriptions(ClientContext context) {
/* 53 */     if (this.subscriptions == null) {
/* 54 */       init(context);
/*    */     }
/* 56 */     return this.subscriptions;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\SubscriptionProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */