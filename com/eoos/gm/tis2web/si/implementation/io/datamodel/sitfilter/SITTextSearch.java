/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.observable.Notification;
/*    */ import com.eoos.observable.ObservableSupport;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SITTextSearch
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(SITTextSearch.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   private ObservableSupport observableSupport = new ObservableSupport();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private CTOCNode selectedSIT;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SITTextSearch getInstance(ClientContext context) {
/* 34 */     synchronized (context.getLockObject()) {
/* 35 */       SITTextSearch instance = (SITTextSearch)context.getObject(SITTextSearch.class);
/* 36 */       if (instance == null) {
/* 37 */         instance = new SITTextSearch(context);
/* 38 */         context.storeObject(SITTextSearch.class, instance);
/*    */       } 
/* 40 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setSelectedSIT(CTOCNode selectedSIT) {
/* 45 */     if ((this.selectedSIT == null) ? (selectedSIT != null) : !this.selectedSIT.equals(selectedSIT)) {
/* 46 */       this.selectedSIT = selectedSIT;
/* 47 */       this.observableSupport.notifyObservers(new Notification() {
/*    */             public void notify(Object observer) {
/*    */               try {
/* 50 */                 ((SITTextSearch.Observer)observer).onChange(SITTextSearch.this.selectedSIT);
/* 51 */               } catch (Exception e) {}
/*    */             }
/*    */           });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public CTOCNode getSelectedSIT() {
/* 59 */     return this.selectedSIT;
/*    */   } public static interface Observer {
/*    */     void onChange(CTOCNode param1CTOCNode); }
/*    */   public ObservableSupport getObserverFacade() {
/* 63 */     return this.observableSupport;
/*    */   }
/*    */   
/*    */   public static List convertToStringList(List ctocSITs) {
/* 67 */     List<Object> retValue = new LinkedList();
/* 68 */     if (ctocSITs != null) {
/* 69 */       Iterator<CTOCNode> iter = ctocSITs.iterator();
/* 70 */       while (iter.hasNext()) {
/* 71 */         CTOCNode tmp = iter.next();
/* 72 */         retValue.add(tmp.getProperty((SITOCProperty)CTOCProperty.SIT));
/*    */       } 
/*    */     } 
/* 75 */     return retValue;
/*    */   }
/*    */   
/*    */   public static String convertToString(CTOCNode sit) {
/* 79 */     return (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/*    */   }
/*    */   
/*    */   public SITTextSearch(ClientContext context) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\sitfilter\SITTextSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */