/*     */ package com.eoos.gm.tis2web.registration.server.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.server.RegistryImpl;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DealershipEntity
/*     */   implements Dealership
/*     */ {
/*     */   private Registry registry;
/*     */   private int status;
/*  15 */   private int dealershippk = -1;
/*     */   private String dealershipid;
/*     */   private String dealershipName;
/*     */   private String dealershipStreet;
/*     */   private String dealershipZIP;
/*     */   private String dealershipCity;
/*     */   private String dealershipState;
/*     */   private String dealershipCountry;
/*     */   private String dealershipLanguage;
/*     */   private String dealershipPhone;
/*     */   private String dealershipFax;
/*     */   private String dealershipEmail;
/*     */   private List dealershipContacts;
/*     */   
/*     */   public DealershipEntity() {
/*  30 */     this.status = 0;
/*     */   }
/*     */   
/*     */   public DealershipEntity(Dealership dealership) {
/*  34 */     this.status = 0;
/*  35 */     this.dealershipid = dealership.getDealershipID();
/*  36 */     this.dealershipName = dealership.getDealershipName();
/*  37 */     this.dealershipStreet = dealership.getDealershipStreet();
/*  38 */     this.dealershipZIP = dealership.getDealershipZIP();
/*  39 */     this.dealershipCity = dealership.getDealershipCity();
/*  40 */     this.dealershipState = dealership.getDealershipState();
/*  41 */     this.dealershipCountry = dealership.getDealershipCountry();
/*  42 */     this.dealershipLanguage = dealership.getDealershipLanguage();
/*  43 */     this.dealershipPhone = dealership.getDealershipPhone();
/*  44 */     this.dealershipFax = dealership.getDealershipFax();
/*  45 */     this.dealershipEmail = dealership.getDealershipEmail();
/*  46 */     List<DealershipContact> contacts = dealership.getDealershipContacts();
/*  47 */     if (contacts != null) {
/*  48 */       this.dealershipContacts = new ArrayList();
/*  49 */       for (int i = 0; i < contacts.size(); i++) {
/*  50 */         DealershipContact contact = contacts.get(i);
/*  51 */         this.dealershipContacts.add(new DealershipContactEntity(contact.getContactName(), contact.getContactLanguage()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public DealershipEntity(Registry registry) {
/*  57 */     this.registry = registry;
/*  58 */     this.status = 1;
/*     */   }
/*     */   
/*     */   public DealershipEntity(Registry registry, int status) {
/*  62 */     this.registry = registry;
/*  63 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/*  67 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setDealershipPK(int dealershippk) {
/*  71 */     this.dealershippk = dealershippk;
/*     */   }
/*     */   
/*     */   public int getDealershipPK() {
/*  75 */     return this.dealershippk;
/*     */   }
/*     */   
/*     */   public void setDealershipID(String dealershipID) {
/*  79 */     this.dealershipid = dealershipID;
/*     */   }
/*     */   
/*     */   public String getDealershipID() {
/*  83 */     return this.dealershipid;
/*     */   }
/*     */   
/*     */   public void setDealershipName(String dealershipName) {
/*  87 */     this.dealershipName = dealershipName;
/*     */   }
/*     */   
/*     */   public String getDealershipName() {
/*  91 */     return this.dealershipName;
/*     */   }
/*     */   
/*     */   public void setDealershipStreet(String dealershipStreet) {
/*  95 */     this.dealershipStreet = dealershipStreet;
/*     */   }
/*     */   
/*     */   public String getDealershipStreet() {
/*  99 */     checkStatus();
/* 100 */     return this.dealershipStreet;
/*     */   }
/*     */   
/*     */   public void setDealershipZIP(String dealershipZIP) {
/* 104 */     this.dealershipZIP = dealershipZIP;
/*     */   }
/*     */   
/*     */   public String getDealershipZIP() {
/* 108 */     return this.dealershipZIP;
/*     */   }
/*     */   
/*     */   public void setDealershipCity(String dealershipCity) {
/* 112 */     this.dealershipCity = dealershipCity;
/*     */   }
/*     */   
/*     */   public String getDealershipCity() {
/* 116 */     return this.dealershipCity;
/*     */   }
/*     */   
/*     */   public void setDealershipState(String dealershipState) {
/* 120 */     this.dealershipState = dealershipState;
/*     */   }
/*     */   
/*     */   public String getDealershipState() {
/* 124 */     return this.dealershipState;
/*     */   }
/*     */   
/*     */   public void setDealershipCountry(String dealershipCountry) {
/* 128 */     this.dealershipCountry = dealershipCountry;
/*     */   }
/*     */   
/*     */   public String getDealershipCountry() {
/* 132 */     return this.dealershipCountry;
/*     */   }
/*     */   
/*     */   public void setDealershipLanguage(String dealershipLanguage) {
/* 136 */     this.dealershipLanguage = dealershipLanguage;
/*     */   }
/*     */   
/*     */   public String getDealershipLanguage() {
/* 140 */     checkStatus();
/* 141 */     return this.dealershipLanguage;
/*     */   }
/*     */   
/*     */   public void setDealershipPhone(String dealershipPhone) {
/* 145 */     this.dealershipPhone = dealershipPhone;
/*     */   }
/*     */   
/*     */   public String getDealershipPhone() {
/* 149 */     checkStatus();
/* 150 */     return this.dealershipPhone;
/*     */   }
/*     */   
/*     */   public void setDealershipFax(String dealershipFax) {
/* 154 */     this.dealershipFax = dealershipFax;
/*     */   }
/*     */   
/*     */   public String getDealershipFax() {
/* 158 */     checkStatus();
/* 159 */     return this.dealershipFax;
/*     */   }
/*     */   
/*     */   public void setDealershipEmail(String dealershipEmail) {
/* 163 */     this.dealershipEmail = dealershipEmail;
/*     */   }
/*     */   
/*     */   public String getDealershipEmail() {
/* 167 */     checkStatus();
/* 168 */     return this.dealershipEmail;
/*     */   }
/*     */   
/*     */   public void setDealershipContacts(List dealershipContacts) {
/* 172 */     this.dealershipContacts = dealershipContacts;
/*     */   }
/*     */   
/*     */   public List getDealershipContacts() {
/* 176 */     checkStatus();
/* 177 */     return this.dealershipContacts;
/*     */   }
/*     */   
/*     */   private void checkStatus() {
/* 181 */     if (this.status == 1) {
/* 182 */       ((RegistryImpl)this.registry).load(this);
/* 183 */       this.status = 2;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\DealershipEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */