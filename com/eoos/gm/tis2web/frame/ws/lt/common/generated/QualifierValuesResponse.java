/*     */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"languages", "guiLanguages", "countries"})
/*     */ @XmlRootElement(name = "qualifierValuesResponse")
/*     */ public class QualifierValuesResponse
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected StringValueList languages;
/*     */   @XmlElement(required = true)
/*     */   protected StringValueList guiLanguages;
/*     */   @XmlElement(required = true)
/*     */   protected StringValueList countries;
/*     */   
/*     */   public StringValueList getLanguages() {
/*  57 */     return this.languages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLanguages(StringValueList value) {
/*  69 */     this.languages = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringValueList getGuiLanguages() {
/*  81 */     return this.guiLanguages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGuiLanguages(StringValueList value) {
/*  93 */     this.guiLanguages = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringValueList getCountries() {
/* 105 */     return this.countries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountries(StringValueList value) {
/* 117 */     this.countries = value;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\QualifierValuesResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */