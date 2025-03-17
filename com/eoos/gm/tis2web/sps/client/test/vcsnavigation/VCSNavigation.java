/*    */ package com.eoos.gm.tis2web.sps.client.test.vcsnavigation;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueImpl;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap.AttributeValueMapImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VCSNavigation {
/*    */   public void test() {
/* 19 */     System.setProperty("com.eoos.gm.tis2web.sps.client", "true");
/* 20 */     System.setProperty("language.id", "de_DE");
/* 21 */     System.setProperty("brands", "GME,NAO,SAT");
/*    */     
/* 23 */     System.setProperty("navtable.filter", "H4sIAAAAAAAAAFvzloG1uIhBPCuxLFGvtCQzR88nMy87NcUjsTgjOLXkRs71qKl3teSYGBgqihgEEaqg8rtcWqdO27HdhBkoX1DOw8DAoGDvwAACvCUM3JnFVcpaEAjk5SXmI/GScoqReOm5ici8kjwkXmYxMi8jPwWJV5xYgsRLQeGl56aiqES2ITMPxb7cJBivAgD/FvbGEQEAAAEA");
/*    */     
/* 25 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*    */     try {
/* 27 */       appContext.init();
/* 28 */     } catch (Exception e) {
/* 29 */       System.out.println(e);
/*    */       return;
/*    */     } 
/* 32 */     Tool vcsTool = appContext.getTool("J2534 CarDAQ PLUS");
/*    */     try {
/* 34 */       vcsTool.init();
/* 35 */     } catch (Exception e) {
/* 36 */       System.out.println(e);
/*    */     } 
/* 38 */     Object vin = null;
/* 39 */     AttributeValueMapImpl requestData = new AttributeValueMapImpl();
/* 40 */     requestData.set(CommonAttribute.MODE, ValueImpl.getInstance("REPROG"));
/* 41 */     while (vin == null) {
/*    */       try {
/* 43 */         vin = vcsTool.getVIN(null, requestData);
/* 44 */       } catch (Exception e) {
/* 45 */         if (e instanceof RequestException) {
/* 46 */           SelectionRequest request = (SelectionRequest)((RequestException)e).getRequest();
/* 47 */           SelectionDialog dialog = new SelectionDialog();
/* 48 */           DisplayableAttribute attribute = (DisplayableAttribute)request.getAttribute();
/* 49 */           List values = request.getOptions();
/* 50 */           String input = dialog.perform(attribute, values, appContext.getLocale());
/* 51 */           Value value = findValue(values, input);
/* 52 */           requestData.set((Attribute)attribute, value); continue;
/*    */         } 
/* 54 */         System.out.println("Poor code - who wrote it?");
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 59 */     vcsTool.reset();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Value findValue(List values, String label) {
/*    */     DisplayableValue displayableValue;
/* 90 */     Value result = null;
/* 91 */     Iterator<DisplayableValue> it = values.iterator();
/* 92 */     while (it.hasNext()) {
/* 93 */       DisplayableValue value = it.next();
/* 94 */       if (label.compareTo(value.getDenotation(ClientAppContextProvider.getClientAppContext().getLocale())) == 0) {
/* 95 */         displayableValue = value;
/*    */         break;
/*    */       } 
/*    */     } 
/* 99 */     return (Value)displayableValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\vcsnavigation\VCSNavigation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */