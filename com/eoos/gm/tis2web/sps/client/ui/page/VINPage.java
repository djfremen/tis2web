/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VINPage
/*    */   extends InputPage
/*    */ {
/* 19 */   private List attributes = new ArrayList();
/*    */   
/*    */   public VINPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 22 */     super(agent, gui, data, request);
/*    */   }
/*    */   
/*    */   public boolean handleInput() {
/* 26 */     log.debug("vin-validation requested: " + this.gui.getValue(CommonAttribute.VIN));
/* 27 */     updateValue((ValueRetrieval)this.gui, CommonAttribute.VIN);
/* 28 */     Iterator it = this.attributes.iterator();
/* 29 */     while (it.hasNext()) {
/* 30 */       Object attribute = it.next();
/* 31 */       Value value = this.gui.getValue((Attribute)attribute);
/* 32 */       if (value != null) {
/* 33 */         updateValue((ValueRetrieval)this.gui, (Attribute)attribute);
/*    */       }
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/* 40 */     if (request instanceof SelectionRequest) {
/*    */       
/* 42 */       Object attribute = ((SelectionRequest)request).getAttribute();
/* 43 */       this.attributes.add(attribute);
/* 44 */       if (attribute instanceof com.eoos.gm.tis2web.sids.service.cai.DisplayableServiceIDAttr || attribute.equals(CommonAttribute.SALESMAKE) || attribute.equals(CommonAttribute.MODELYEAR) || attribute.equals(CommonAttribute.ENGINE) || attribute.equals(CommonAttribute.TRANSMISSION) || attribute.equals(CommonAttribute.MODEL)) {
/* 45 */         transferRequestGroup(request);
/* 46 */         this.gui.handleRequest(request);
/* 47 */         this.gui.setNextButtonState(true);
/* 48 */         return true;
/*    */       } 
/* 50 */       return false;
/*    */     } 
/*    */     
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\VINPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */