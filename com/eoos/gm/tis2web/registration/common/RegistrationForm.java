/*     */ package com.eoos.gm.tis2web.registration.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.REGISTRATION;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.CHUNKTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPCONTACTTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.LICENSEKEYTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.REGISTRATIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SOFTWAREKEYTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SUBSCRIPTIONTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegistrationForm
/*     */ {
/*     */   private REGISTRATION form;
/*     */   
/*     */   public RegistrationForm(String requestID, String requestDate, String subscriberID, Integer sessions) {
/*  39 */     this.form = (REGISTRATION)new REGISTRATIONImpl();
/*     */     
/*  41 */     this.form.setRequestID(requestID);
/*  42 */     this.form.setRequestDate(requestDate);
/*  43 */     if (subscriberID != null) {
/*  44 */       this.form.setSubscriberID(subscriberID);
/*     */     }
/*  46 */     if (sessions != null) {
/*  47 */       this.form.setLicensedSessions(sessions.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getXML() throws JAXBException, IOException {
/*  53 */     JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.registration.common.xml");
/*  54 */     Marshaller MARSHALLER = jc.createMarshaller();
/*  55 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  56 */     MARSHALLER.marshal(this.form, baos);
/*  57 */     baos.close();
/*  58 */     return baos.toByteArray();
/*     */   }
/*     */   
/*     */   public void setSubscription(Locale locale, Subscription subscription) {
/*  62 */     SUBSCRIPTIONTypeImpl sUBSCRIPTIONTypeImpl = new SUBSCRIPTIONTypeImpl();
/*  63 */     sUBSCRIPTIONTypeImpl.setSubscriptionID(subscription.getSubscriptionID());
/*  64 */     sUBSCRIPTIONTypeImpl.setDescription(subscription.getDescription(locale));
/*  65 */     this.form.getSUBSCRIPTION().add(sUBSCRIPTIONTypeImpl);
/*     */   }
/*     */   
/*     */   public void setSubscriptions(Locale locale, Collection subscriptions) {
/*  69 */     Iterator<Subscription> it = subscriptions.iterator();
/*  70 */     while (it.hasNext()) {
/*  71 */       setSubscription(locale, it.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSoftwareKey(String swk) {
/*  76 */     SOFTWAREKEYTypeImpl sOFTWAREKEYTypeImpl = new SOFTWAREKEYTypeImpl();
/*  77 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/*  78 */       CHUNKTypeImpl cHUNKTypeImpl = new CHUNKTypeImpl();
/*  79 */       cHUNKTypeImpl.setChunkNo(i);
/*  80 */       cHUNKTypeImpl.setData(swk.substring(4 * i, 4 * (i + 1)));
/*  81 */       sOFTWAREKEYTypeImpl.getCHUNK().add(cHUNKTypeImpl);
/*     */     } 
/*  83 */     this.form.setSOFTWAREKEY((SOFTWAREKEYType)sOFTWAREKEYTypeImpl);
/*     */   }
/*     */   
/*     */   public void setLicenseKey(String swk) {
/*  87 */     LICENSEKEYTypeImpl lICENSEKEYTypeImpl = new LICENSEKEYTypeImpl();
/*  88 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/*  89 */       CHUNKTypeImpl cHUNKTypeImpl = new CHUNKTypeImpl();
/*  90 */       cHUNKTypeImpl.setChunkNo(i);
/*  91 */       cHUNKTypeImpl.setData(swk.substring(4 * i, 4 * (i + 1)));
/*  92 */       lICENSEKEYTypeImpl.getCHUNK().add(cHUNKTypeImpl);
/*     */     } 
/*  94 */     this.form.setLICENSEKEY((LICENSEKEYType)lICENSEKEYTypeImpl);
/*     */   }
/*     */   
/*     */   private static Locale createLocale(String lang, String country) {
/*  98 */     Locale ret = Util.parseLocale(lang);
/*  99 */     if (Util.isNullOrEmpty(ret.getCountry())) {
/* 100 */       ret = new Locale(ret.getLanguage(), country);
/*     */     }
/* 102 */     return ret;
/*     */   }
/*     */   
/*     */   public void setDealershipInformation(Dealership dealership) {
/* 106 */     DEALERSHIPTypeImpl dEALERSHIPTypeImpl = new DEALERSHIPTypeImpl();
/* 107 */     dEALERSHIPTypeImpl.setDealershipCode(dealership.getDealershipID());
/* 108 */     dEALERSHIPTypeImpl.setDealershipName(dealership.getDealershipName());
/* 109 */     dEALERSHIPTypeImpl.setDealershipStreet(dealership.getDealershipStreet());
/* 110 */     dEALERSHIPTypeImpl.setDealershipZIP(dealership.getDealershipZIP());
/* 111 */     dEALERSHIPTypeImpl.setDealershipCity(dealership.getDealershipCity());
/* 112 */     if (dealership.getDealershipState() != null) {
/* 113 */       dEALERSHIPTypeImpl.setDealershipState(dealership.getDealershipState());
/*     */     }
/* 115 */     Locale locale = createLocale(dealership.getDealershipLanguage(), dealership.getDealershipCountry());
/* 116 */     dEALERSHIPTypeImpl.setDealershipCountry(locale.getDisplayCountry() + " (" + dealership.getDealershipCountry() + ")");
/* 117 */     dEALERSHIPTypeImpl.setDealershipLanguage(locale.getDisplayLanguage() + " (" + dealership.getDealershipLanguage() + ")");
/* 118 */     if (dealership.getDealershipPhone() != null) {
/* 119 */       dEALERSHIPTypeImpl.setDealershipPhone(dealership.getDealershipPhone());
/*     */     }
/* 121 */     if (dealership.getDealershipFax() != null) {
/* 122 */       dEALERSHIPTypeImpl.setDealershipFax(dealership.getDealershipFax());
/*     */     }
/* 124 */     if (dealership.getDealershipEmail() != null) {
/* 125 */       dEALERSHIPTypeImpl.setDealershipEmail(dealership.getDealershipEmail());
/*     */     }
/* 127 */     List<DealershipContact> contacts = dealership.getDealershipContacts();
/* 128 */     for (int i = 0; i < contacts.size(); i++) {
/* 129 */       DealershipContact contact = contacts.get(i);
/* 130 */       DEALERSHIPCONTACTTypeImpl dEALERSHIPCONTACTTypeImpl = new DEALERSHIPCONTACTTypeImpl();
/* 131 */       locale = createLocale(contact.getContactLanguage(), dealership.getDealershipCountry());
/* 132 */       dEALERSHIPCONTACTTypeImpl.setContactLanguage(locale.getDisplayLanguage() + " (" + contact.getContactLanguage() + ")");
/* 133 */       dEALERSHIPCONTACTTypeImpl.setContactName(contact.getContactName());
/* 134 */       dEALERSHIPTypeImpl.getDEALERSHIPCONTACT().add(dEALERSHIPCONTACTTypeImpl);
/*     */     } 
/* 136 */     this.form.setDEALERSHIP((DEALERSHIPType)dEALERSHIPTypeImpl);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\RegistrationForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */