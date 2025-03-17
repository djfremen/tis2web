/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class SPSTool
/*    */ {
/* 18 */   public final RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*    */ 
/*    */   
/*    */   public SPSTool(SPSClientController controller) {}
/*    */   
/*    */   public Object getVIN(AttributeValueMap data) throws Exception {
/* 24 */     Value make = data.getValue(CommonAttribute.SALESMAKE);
/* 25 */     if (make == null) {
/* 26 */       List salesmakes = getSalesMakeList();
/* 27 */       throw new RequestException(makeSelectionRequest(CommonAttribute.SALESMAKE, salesmakes));
/*    */     } 
/* 29 */     Value my = data.getValue(CommonAttribute.MODELYEAR);
/* 30 */     if (my == null) {
/* 31 */       List years = getModelYearList(data);
/* 32 */       throw new RequestException(makeSelectionRequest(CommonAttribute.MODELYEAR, years));
/*    */     } 
/* 34 */     Value vtype = data.getValue(CommonAttribute.MODEL);
/* 35 */     if (vtype == null) {
/* 36 */       List models = getModelList(data);
/* 37 */       throw new RequestException(makeSelectionRequest(CommonAttribute.MODEL, models));
/*    */     } 
/* 39 */     Value line = data.getValue(CommonAttribute.CARLINE);
/* 40 */     if (line == null) {
/* 41 */       List carlines = getCarlineList(data);
/* 42 */       throw new RequestException(makeSelectionRequest(CommonAttribute.CARLINE, carlines));
/*    */     } 
/* 44 */     return new ValueAdapter("GM81Z107x10123456");
/*    */   }
/*    */   
/*    */   protected SelectionRequest makeSelectionRequest(Attribute attribute, List selections) {
/* 48 */     return this.builder.makeSelectionRequest(attribute, selections, null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List getSalesMakeList() {
/* 53 */     List<ValueAdapter> list = new ArrayList();
/* 54 */     list.add(new ValueAdapter("Opel"));
/* 55 */     list.add(new ValueAdapter("Chevrolet"));
/* 56 */     return list;
/*    */   }
/*    */   
/*    */   protected List getModelYearList(AttributeValueMap data) {
/* 60 */     data.getValue(CommonAttribute.SALESMAKE);
/*    */     
/* 62 */     List<ValueAdapter> list = new ArrayList();
/* 63 */     list.add(new ValueAdapter("2004"));
/* 64 */     list.add(new ValueAdapter("2005"));
/* 65 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List getModelList(AttributeValueMap data) {
/* 70 */     List<ValueAdapter> list = new ArrayList();
/* 71 */     list.add(new ValueAdapter("LD TRK,MPV,Incomplete"));
/* 72 */     list.add(new ValueAdapter("Medium Duty Truck"));
/* 73 */     list.add(new ValueAdapter("Passenger Car"));
/* 74 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List getCarlineList(AttributeValueMap data) {
/* 79 */     List<ValueAdapter> list = new ArrayList();
/* 80 */     list.add(new ValueAdapter("Cayon"));
/* 81 */     list.add(new ValueAdapter("Envoy"));
/* 82 */     list.add(new ValueAdapter("Envoy XL"));
/* 83 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\SPSTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */