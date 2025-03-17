/*     */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestGroupBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DefaultPage
/*     */   implements UIPage {
/*  17 */   protected static final Logger log = Logger.getLogger(UIAgent.class);
/*     */   
/*  19 */   protected static final RequestGroupBuilderImpl rgBuilder = new RequestGroupBuilderImpl();
/*     */   
/*     */   protected UIAgent agent;
/*     */   
/*     */   protected SPSFrame gui;
/*     */   
/*     */   protected AttributeValueMapExt data;
/*     */   
/*     */   protected AssignmentRequest request;
/*     */   
/*     */   protected UIPage predecessor;
/*     */   
/*     */   protected Object savepoint;
/*     */   
/*     */   public AssignmentRequest getRequest() {
/*  34 */     return this.request;
/*     */   }
/*     */   
/*     */   public UIPage getPredecessor() {
/*  38 */     return this.predecessor;
/*     */   }
/*     */   
/*     */   public DefaultPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/*  42 */     this.agent = agent;
/*  43 */     this.gui = gui;
/*  44 */     this.data = data;
/*  45 */     this.request = request;
/*  46 */     setDefaultRequestGroup();
/*  47 */     log.debug("create page: " + getRequestAttributeDisplay());
/*     */   }
/*     */   
/*     */   public void setPredecessor(UIPage page) {
/*  51 */     this.predecessor = page;
/*     */   }
/*     */   
/*     */   public void activate(Object savepoint) {
/*  55 */     log.debug("execute page: " + getRequestAttributeDisplay());
/*  56 */     this.savepoint = savepoint;
/*  57 */     this.gui.handleRequest(this.request);
/*     */   }
/*     */   
/*     */   public UIPage undo() {
/*  61 */     this.data.restoreSavePoint(this.savepoint);
/*  62 */     return this.predecessor;
/*     */   }
/*     */   
/*     */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/*  70 */     log.debug("handle selection: " + getSelectionAttributeDisplay(result));
/*  71 */     Attribute selection = result.getAttribute();
/*  72 */     if (data != null) {
/*  73 */       updateValue(data, selection);
/*     */     }
/*  75 */     this.gui.setNextButtonState(true);
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   protected void setDefaultRequestGroup() {
/*  80 */     if (this.request != null) {
/*  81 */       RequestGroup group = rgBuilder.makeRequestGroup();
/*  82 */       this.request.setRequestGroup(group);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void transferRequestGroup(AssignmentRequest request) {
/*  87 */     request.setRequestGroup(this.request.getRequestGroup());
/*     */   }
/*     */   
/*     */   protected void updateValue(ValueRetrieval update, Attribute attribute) {
/*  91 */     updateValue(attribute, update.getValue(attribute));
/*     */   }
/*     */   
/*     */   protected void updateValue(Attribute attribute, Value value) {
/*  95 */     Value current = this.data.getValue(attribute);
/*  96 */     if (current != null) {
/*  97 */       if (!current.equals(value)) {
/*  98 */         this.data.set(attribute, value);
/*     */       }
/* 100 */     } else if (value != null) {
/* 101 */       this.data.set(attribute, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getSelectionAttributeDisplay(SelectionResult result) {
/* 106 */     return (result == null) ? null : getAttributeDisplay(result.getAttribute());
/*     */   }
/*     */   
/*     */   protected String getRequestAttributeDisplay() {
/* 110 */     return (this.request == null) ? "none" : getAttributeDisplay(this.request.getAttribute());
/*     */   }
/*     */   
/*     */   protected String getAttributeDisplay(Attribute attribute) {
/* 114 */     String display = attribute.toString();
/* 115 */     if (display.indexOf("[key=") >= 0) {
/* 116 */       display = display.substring(display.indexOf("[key="));
/*     */     }
/* 118 */     return display;
/*     */   }
/*     */   
/*     */   public void dump(int level) {
/*     */     try {
/* 123 */       String attribute = this.request.getAttribute().toString();
/* 124 */       if (attribute.indexOf("[key=") >= 0) {
/* 125 */         attribute = attribute.substring(attribute.indexOf("[key="));
/*     */       }
/* 127 */       String group = this.request.getRequestGroup().toString();
/* 128 */       if (group.indexOf("[key=") >= 0) {
/* 129 */         group = group.substring(group.indexOf("[key="));
/*     */       }
/* 131 */       log.debug(level + "(" + this.savepoint + "): " + attribute + "/" + group);
/* 132 */       if (this.predecessor != null) {
/* 133 */         this.predecessor.dump(++level);
/*     */       }
/* 135 */     } catch (Exception x) {}
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\DefaultPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */