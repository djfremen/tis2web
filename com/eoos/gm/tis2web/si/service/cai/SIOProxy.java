/*    */ package com.eoos.gm.tis2web.si.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SIOProxy
/*    */   implements SIOElement
/*    */ {
/*    */   protected SIOElement sio;
/*    */   protected int order;
/*    */   
/*    */   public SIOProxy(SIOElement sio, int order) {
/* 16 */     this.sio = sio;
/* 17 */     this.order = order;
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 21 */     return this.sio.getID();
/*    */   }
/*    */   
/*    */   public SITOCType getType() {
/* 25 */     return this.sio.getType();
/*    */   }
/*    */   
/*    */   public SIO getSIO() {
/* 29 */     return this.sio;
/*    */   }
/*    */   
/*    */   public boolean isSIO() {
/* 33 */     return this.sio.isSIO();
/*    */   }
/*    */   
/*    */   public String getLabel(LocaleInfo locale) {
/* 37 */     return this.sio.getLabel(locale);
/*    */   }
/*    */   
/*    */   public int getOrder() {
/* 41 */     return this.order;
/*    */   }
/*    */   
/*    */   public List getChildren() {
/* 45 */     return this.sio.getChildren();
/*    */   }
/*    */   
/*    */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 49 */     return this.sio.getChildren();
/*    */   }
/*    */   
/*    */   public VCR getVCR() {
/* 53 */     return this.sio.getVCR();
/*    */   }
/*    */   
/*    */   public boolean hasProperty(SITOCProperty property) {
/* 57 */     return this.sio.hasProperty(property);
/*    */   }
/*    */   
/*    */   public Object getProperty(SITOCProperty property) {
/* 61 */     return this.sio.getProperty(property);
/*    */   }
/*    */   
/*    */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 65 */     return this.sio.isQualified(locale, country, vcr);
/*    */   }
/*    */   
/*    */   public String getLiteratureNumber() {
/* 69 */     return this.sio.getLiteratureNumber();
/*    */   }
/*    */   
/*    */   public String getNonMarketsConstraints() {
/* 73 */     return this.sio.getNonMarketsConstraints();
/*    */   }
/*    */   
/*    */   public List getSITIDs() {
/* 77 */     return this.sio.getSITIDs();
/*    */   }
/*    */   
/*    */   public List<String> getSits() {
/* 81 */     return this.sio.getSits();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */