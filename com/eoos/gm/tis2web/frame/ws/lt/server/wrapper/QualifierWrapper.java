/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server.wrapper;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WindowsLanguageReverseMap;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidQualifier;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.Qualifier;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierValuesResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.StringValueList;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.InvalidQualifierException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.util.LtwsUtils;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QualifierWrapper
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(QualifierWrapper.class);
/*     */   
/*     */   private Qualifier qualifier;
/*     */   
/*     */   public QualifierWrapper(Qualifier q) {
/*  27 */     this.qualifier = q;
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  31 */     return WindowsLanguageReverseMap.getInstance().getLocale(this.qualifier.getLanguage());
/*     */   }
/*     */   
/*     */   public Locale getGuiLocale() {
/*  35 */     return WindowsLanguageReverseMap.getInstance().getLocale(this.qualifier.getGuiLanguage());
/*     */   }
/*     */   
/*     */   public String getT2wCountry() {
/*  39 */     String result = WindowsLanguageReverseMap.getInstance().getT2wCountry(this.qualifier.getCountry());
/*  40 */     return result;
/*     */   }
/*     */   
/*     */   public QualifierValuesResponse getQualifierValues() {
/*  44 */     QualifierValuesResponse qValues = new QualifierValuesResponse();
/*  45 */     qValues.setLanguages(new StringValueList());
/*  46 */     qValues.setGuiLanguages(new StringValueList());
/*  47 */     qValues.setCountries(new StringValueList());
/*  48 */     Iterator<String> it = QualifierData.getInstance().getLtLanguages().iterator();
/*  49 */     while (it.hasNext()) {
/*  50 */       qValues.getLanguages().getLe().add(it.next());
/*     */     }
/*  52 */     it = QualifierData.getInstance().getLtGuiLanguages().iterator();
/*  53 */     while (it.hasNext()) {
/*  54 */       qValues.getGuiLanguages().getLe().add(it.next());
/*     */     }
/*  56 */     it = QualifierData.getInstance().getLtCountries().iterator();
/*  57 */     while (it.hasNext()) {
/*  58 */       qValues.getCountries().getLe().add(it.next());
/*     */     }
/*  60 */     return qValues;
/*     */   }
/*     */   
/*     */   public void validateAll() throws QualifierFault {
/*  64 */     InvalidQualifierException ex = new InvalidQualifierException();
/*     */     try {
/*  66 */       validateLanguage();
/*  67 */     } catch (InvalidQualifierException invEx) {
/*  68 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/*  69 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/*     */     try {
/*  72 */       validateCountry();
/*  73 */     } catch (InvalidQualifierException invEx) {
/*  74 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/*  75 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/*     */     try {
/*  78 */       validateGuiLanguage();
/*  79 */     } catch (InvalidQualifierException invEx) {
/*  80 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/*  81 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/*  83 */     if (ex.getInvalidCode() != 0) {
/*  84 */       log.debug("Invalid qualifier: " + ex.toString());
/*  85 */       InvalidQualifier invQualifier = new InvalidQualifier();
/*  86 */       int errorCode = ex.getInvalidCode();
/*  87 */       invQualifier.setInvalidAttrCode(errorCode);
/*  88 */       LtwsUtils.setInvalidQualifierDetails(ex, invQualifier);
/*  89 */       QualifierFault qFault = new QualifierFault("Invalid qualifier", invQualifier);
/*  90 */       throw qFault;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void validateLangAndCountry() throws QualifierFault {
/*  95 */     InvalidQualifierException ex = new InvalidQualifierException();
/*     */     try {
/*  97 */       validateLanguage();
/*  98 */     } catch (InvalidQualifierException invEx) {
/*  99 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/* 100 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/*     */     try {
/* 103 */       validateCountry();
/* 104 */     } catch (InvalidQualifierException invEx) {
/* 105 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/* 106 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/* 108 */     if (ex.getInvalidCode() != 0) {
/* 109 */       log.debug("Invalid qualifier: " + ex.toString());
/* 110 */       InvalidQualifier invQualifier = new InvalidQualifier();
/* 111 */       LtwsUtils.setInvalidQualifierDetails(ex, invQualifier);
/* 112 */       QualifierFault qFault = new QualifierFault("Invalid qualifier", invQualifier);
/* 113 */       throw qFault;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void validateGuiLangAndCountry() throws QualifierFault {
/* 118 */     InvalidQualifierException ex = new InvalidQualifierException();
/*     */     try {
/* 120 */       validateGuiLanguage();
/* 121 */     } catch (InvalidQualifierException invEx) {
/* 122 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/* 123 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/*     */     try {
/* 126 */       validateCountry();
/* 127 */     } catch (InvalidQualifierException invEx) {
/* 128 */       ex.addToInvalidList(invEx.getInvalidAttributes());
/* 129 */       ex.addInvalidCode(invEx.getInvalidCode());
/*     */     } 
/* 131 */     if (ex.getInvalidCode() != 0) {
/* 132 */       log.debug("Invalid qualifier: " + ex.toString());
/* 133 */       InvalidQualifier invQualifier = new InvalidQualifier();
/* 134 */       LtwsUtils.setInvalidQualifierDetails(ex, invQualifier);
/* 135 */       QualifierFault qFault = new QualifierFault("Invalid qualifier", invQualifier);
/* 136 */       throw qFault;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateLanguage() throws InvalidQualifierException {
/* 141 */     InvalidQualifierException invEx = new InvalidQualifierException();
/* 142 */     Set<String> strSet = QualifierData.getInstance().getLtLanguages();
/* 143 */     if (this.qualifier.getLanguage() == null || !strSet.contains(this.qualifier.getLanguage())) {
/* 144 */       invEx.addToInvalidList((Pair)new PairImpl("language", this.qualifier.getLanguage()));
/* 145 */       invEx.addInvalidCode(1);
/* 146 */       throw invEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateCountry() throws InvalidQualifierException {
/* 151 */     InvalidQualifierException invEx = new InvalidQualifierException();
/* 152 */     Set<String> strSet = QualifierData.getInstance().getLtCountries();
/* 153 */     if (this.qualifier.getCountry() == null || !strSet.contains(this.qualifier.getCountry())) {
/* 154 */       invEx.addToInvalidList((Pair)new PairImpl("country", this.qualifier.getCountry()));
/* 155 */       invEx.addInvalidCode(4);
/* 156 */       throw invEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateGuiLanguage() throws InvalidQualifierException {
/* 161 */     InvalidQualifierException invEx = new InvalidQualifierException();
/* 162 */     Set<String> strSet = QualifierData.getInstance().getLtGuiLanguages();
/* 163 */     if (this.qualifier.getGuiLanguage() == null || !strSet.contains(this.qualifier.getGuiLanguage())) {
/* 164 */       invEx.addToInvalidList((Pair)new PairImpl("guiLanguage", this.qualifier.getGuiLanguage()));
/* 165 */       invEx.addInvalidCode(2);
/* 166 */       throw invEx;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\wrapper\QualifierWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */