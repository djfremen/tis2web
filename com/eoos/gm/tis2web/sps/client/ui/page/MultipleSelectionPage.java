/*     */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResultImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ 
/*     */ public class MultipleSelectionPage extends DefaultPage {
/*     */   protected boolean fixedAttributesOnly = true;
/*     */   
/*     */   public boolean hasFixedAttributesOnly() {
/*  25 */     return this.fixedAttributesOnly;
/*     */   }
/*     */   
/*     */   public MultipleSelectionPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/*  29 */     super(agent, gui, data, request);
/*     */   }
/*     */   
/*     */   public void activate(Object savepoint) {
/*  33 */     log.debug("execute page: " + getRequestAttributeDisplay());
/*  34 */     this.savepoint = savepoint;
/*  35 */     if (!isFixedAttribute(this.request, this.data)) {
/*  36 */       this.fixedAttributesOnly = false;
/*  37 */       this.gui.handleRequest(this.request);
/*     */     } 
/*  39 */     handleDefaultValueRetrieval(this.request);
/*     */   }
/*     */   
/*     */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/*  43 */     if (request.getClass().equals(this.request.getClass()) || request.getAttribute().getClass().equals(this.request.getAttribute().getClass())) {
/*  44 */       if (!isFixedAttribute(request, data)) {
/*  45 */         this.fixedAttributesOnly = false;
/*  46 */         transferRequestGroup(request);
/*  47 */         this.gui.handleRequest(request);
/*     */       } 
/*  49 */       handleDefaultValueRetrieval(request);
/*  50 */       return true;
/*     */     } 
/*  52 */     this.gui.setNextButtonState(true);
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFixedAttribute(AssignmentRequest request, AttributeValueMapExt data) {
/*  58 */     return (isFixedAttribute(request, data, CommonAttribute.ENGINE) || isFixedAttribute(request, data, CommonAttribute.TRANSMISSION));
/*     */   }
/*     */   
/*     */   protected boolean isFixedAttribute(AssignmentRequest request, AttributeValueMapExt data, Attribute attribute) {
/*  62 */     if (!(request instanceof DefaultValueRetrieval) || data.getValue(attribute) == null) {
/*  63 */       return false;
/*     */     }
/*  65 */     String target = normalize((String)AVUtil.accessValue((AttributeValueMap)data, attribute));
/*  66 */     Value value = ((DefaultValueRetrieval)request).getDefaultValue();
/*  67 */     String match = (value instanceof DisplayableValue) ? ((DisplayableValue)value).getDenotation(null) : null;
/*  68 */     if (request instanceof SelectionRequest && ((SelectionRequest)request).getOptions() != null) {
/*  69 */       return (target != null && target.equals(normalize(match)) && ((SelectionRequest)request).getOptions().size() == 1);
/*     */     }
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   protected String normalize(String label) {
/*  75 */     if (label == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     StringBuffer buffer = new StringBuffer();
/*  79 */     for (int i = 0; i < label.length(); i++) {
/*  80 */       char c = label.charAt(i);
/*  81 */       if (!Character.isWhitespace(c)) {
/*  82 */         buffer.append(c);
/*     */       }
/*     */     } 
/*  85 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/*  89 */     log.debug("handle selection: " + getSelectionAttributeDisplay(result));
/*  90 */     Attribute selection = result.getAttribute();
/*  91 */     updateValue(data, selection);
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   protected void setDefaultRequestGroup() {
/*  96 */     if (this.request != null && this.request.getRequestGroup() == null) {
/*  97 */       RequestGroup group = rgBuilder.makeRequestGroup();
/*  98 */       this.request.setRequestGroup(group);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleDefaultValueRetrieval(AssignmentRequest request) {
/* 103 */     if (request instanceof DefaultValueRetrieval && (
/* 104 */       (DefaultValueRetrieval)request).getDefaultValue() != null) {
/* 105 */       Object confirmation = this.data.getValue(CommonAttribute.CONFIRMED_OPTIONS);
/* 106 */       if (confirmation == null || CommonValue.OK.equals(confirmation)) {
/* 107 */         this.data.set(request.getAttribute(), ((DefaultValueRetrieval)request).getDefaultValue());
/*     */       }
/* 109 */       if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VehicleAttributeDefaultSelectionRequestImpl) {
/* 110 */         this.agent.execute((SelectionResult)new SelectionResultImpl(request.getAttribute()));
/*     */       } else {
/* 112 */         this.agent.blockGUI();
/* 113 */         this.agent.triggerRequest();
/* 114 */         this.agent.unblockGUI();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\MultipleSelectionPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */