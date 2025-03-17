/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.EmailAddressValidator;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import java.beans.XMLDecoder;
/*     */ import java.beans.XMLEncoder;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DealershipInformation
/*     */   implements Dealership, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  25 */   private static final Logger log = Logger.getLogger(DealershipInformation.class);
/*     */   
/*     */   private String dealershipid;
/*     */   
/*     */   private String dealershipName;
/*     */   
/*     */   private String dealershipStreet;
/*     */   
/*     */   private String dealershipZIP;
/*     */   
/*     */   private String dealershipCity;
/*     */   
/*     */   private String dealershipState;
/*     */   
/*     */   private String dealershipCountry;
/*     */   
/*     */   private String dealershipLanguage;
/*     */   
/*     */   private String dealershipPhone;
/*     */   
/*     */   private String dealershipFax;
/*     */   
/*     */   private String dealershipEmail;
/*     */   
/*     */   private List dealershipContacts;
/*     */ 
/*     */   
/*     */   public DealershipInformation() {}
/*     */   
/*     */   public String check() {
/*  55 */     String error = hasMandatoryInformation();
/*  56 */     if (error != null) {
/*  57 */       return error;
/*     */     }
/*  59 */     if (!checkDealerCode()) {
/*  60 */       return "dealercode.invalid";
/*     */     }
/*  62 */     if (!checkContact()) {
/*  63 */       return "contact.missing";
/*     */     }
/*  65 */     if (!checkEmail()) {
/*  66 */       return "email.invalid";
/*     */     }
/*     */     try {
/*  69 */       error = checkData();
/*  70 */       if (error != null) {
/*  71 */         return error;
/*     */       }
/*  73 */     } catch (ContactNameMissingException e) {
/*  74 */       return "contact.name.missing";
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   private String hasMandatoryInformation() {
/*  80 */     if (this.dealershipid == null) {
/*  81 */       return "missing.id";
/*     */     }
/*  83 */     if (this.dealershipName == null) {
/*  84 */       return "missing.name";
/*     */     }
/*  86 */     if (this.dealershipStreet == null) {
/*  87 */       return "missing.street";
/*     */     }
/*  89 */     if (this.dealershipZIP == null) {
/*  90 */       return "missing.zip";
/*     */     }
/*  92 */     if (this.dealershipCity == null) {
/*  93 */       return "missing.city";
/*     */     }
/*  95 */     if (this.dealershipCountry == null) {
/*  96 */       return "missing.country";
/*     */     }
/*  98 */     if (this.dealershipLanguage == null) {
/*  99 */       return "missing.language";
/*     */     }
/* 101 */     if (this.dealershipContacts == null) {
/* 102 */       return "missing.contacs";
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   private boolean checkDealerCode() {
/* 108 */     return (this.dealershipid.length() > 0 && this.dealershipid.length() <= 10);
/*     */   }
/*     */   
/*     */   private boolean checkContact() {
/* 112 */     return (this.dealershipFax != null || this.dealershipEmail != null);
/*     */   }
/*     */   
/*     */   private boolean checkEmail() {
/* 116 */     if (this.dealershipEmail == null) {
/* 117 */       return true;
/*     */     }
/* 119 */     this.dealershipEmail = this.dealershipEmail.trim();
/* 120 */     return EmailAddressValidator.isValid(this.dealershipEmail);
/*     */   }
/*     */   
/*     */   private String checkData() throws ContactNameMissingException {
/* 124 */     if (!lencheck(255, this.dealershipName)) {
/* 125 */       return "invalid.name";
/*     */     }
/* 127 */     if (!lencheck(255, this.dealershipStreet)) {
/* 128 */       return "invalid.street";
/*     */     }
/* 130 */     if (!lencheck(10, this.dealershipZIP)) {
/* 131 */       return "invalid.zip";
/*     */     }
/* 133 */     if (!lencheck(255, this.dealershipCity)) {
/* 134 */       return "invalid.city";
/*     */     }
/* 136 */     if (!lencheck(255, this.dealershipPhone)) {
/* 137 */       return "invalid.phone";
/*     */     }
/* 139 */     if (!lencheck(255, this.dealershipFax)) {
/* 140 */       return "invalid.fax";
/*     */     }
/* 142 */     if (!lencheck(255, this.dealershipEmail)) {
/* 143 */       return "invalid.email";
/*     */     }
/* 145 */     for (int i = 0; i < this.dealershipContacts.size(); i++) {
/* 146 */       DealershipContactInfo contact = this.dealershipContacts.get(i);
/* 147 */       if (contact.getContactName() == null)
/* 148 */         throw new ContactNameMissingException(); 
/* 149 */       if (!lencheck(255, contact.getContactName())) {
/* 150 */         return "invalid.contact.name";
/*     */       }
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */   
/*     */   public DealershipInformation(ClientContext context, DealershipInfo dealership) {
/* 157 */     this.dealershipid = check(dealership.getDealershipID());
/* 158 */     this.dealershipName = check(dealership.getDealership());
/* 159 */     this.dealershipStreet = check(dealership.getStreet());
/* 160 */     this.dealershipZIP = check(dealership.getZIP());
/* 161 */     this.dealershipCity = check(dealership.getCity());
/* 162 */     this.dealershipState = dealership.getState();
/* 163 */     this.dealershipCountry = encodeCountry(dealership.getCountry());
/* 164 */     this.dealershipLanguage = encodeLanguage(dealership.getLanguage());
/* 165 */     this.dealershipPhone = check(dealership.getPhone());
/* 166 */     this.dealershipFax = check(dealership.getFax());
/* 167 */     this.dealershipEmail = check(dealership.getEmail());
/* 168 */     List<DealershipInfo.Contact> contacts = dealership.getContacts();
/* 169 */     if (contacts != null) {
/* 170 */       this.dealershipContacts = new ArrayList();
/* 171 */       for (int i = 0; i < contacts.size(); i++) {
/* 172 */         DealershipInfo.Contact contact = contacts.get(i);
/* 173 */         this.dealershipContacts.add(new DealershipContactInfo(check(contact.getName()), encodeLanguage(contact.getLanguage())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String check(String input) {
/* 179 */     input = input.trim();
/* 180 */     return (input == null || input.length() == 0) ? null : input;
/*     */   }
/*     */   
/*     */   private boolean lencheck(int maxlength, String input) {
/* 184 */     return (input == null || input.length() <= maxlength);
/*     */   }
/*     */   
/*     */   private String encodeLanguage(Locale locale) {
/* 188 */     if (locale == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     return locale.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String encodeCountry(Locale locale) {
/* 196 */     if (locale == null) {
/* 197 */       return null;
/*     */     }
/* 199 */     return locale.getCountry();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DealershipInformation load(File file) {
/* 204 */     XMLDecoder xml = null;
/* 205 */     FileInputStream fi = null;
/*     */     try {
/* 207 */       fi = new FileInputStream(file);
/* 208 */       xml = new XMLDecoder(fi);
/* 209 */       return (DealershipInformation)xml.readObject();
/* 210 */     } catch (Exception e) {
/* 211 */       log.info("failed to load dealership information");
/* 212 */       return null;
/*     */     } finally {
/* 214 */       if (xml != null) {
/*     */         try {
/* 216 */           xml.close();
/* 217 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 220 */       if (fi != null) {
/*     */         try {
/* 222 */           fi.close();
/* 223 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(File file) throws IOException {
/* 230 */     XMLEncoder xml = null;
/* 231 */     FileOutputStream fo = null;
/*     */     try {
/* 233 */       fo = new FileOutputStream(file);
/* 234 */       xml = new XMLEncoder(fo);
/* 235 */       xml.writeObject(this);
/* 236 */     } catch (Exception e) {
/* 237 */       log.error("failed to store dealership information", e);
/* 238 */       throw new IOException("failed to store dealership information");
/*     */     } finally {
/* 240 */       if (xml != null) {
/*     */         try {
/* 242 */           xml.close();
/* 243 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 246 */       if (fo != null) {
/*     */         try {
/* 248 */           fo.close();
/* 249 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDealershipID(String dealershipID) {
/* 256 */     this.dealershipid = dealershipID;
/*     */   }
/*     */   
/*     */   public String getDealershipID() {
/* 260 */     return this.dealershipid;
/*     */   }
/*     */   
/*     */   public void setDealershipName(String dealershipName) {
/* 264 */     this.dealershipName = dealershipName;
/*     */   }
/*     */   
/*     */   public String getDealershipName() {
/* 268 */     return this.dealershipName;
/*     */   }
/*     */   
/*     */   public void setDealershipStreet(String dealershipStreet) {
/* 272 */     this.dealershipStreet = dealershipStreet;
/*     */   }
/*     */   
/*     */   public String getDealershipStreet() {
/* 276 */     return this.dealershipStreet;
/*     */   }
/*     */   
/*     */   public void setDealershipZIP(String dealershipZIP) {
/* 280 */     this.dealershipZIP = dealershipZIP;
/*     */   }
/*     */   
/*     */   public String getDealershipZIP() {
/* 284 */     return this.dealershipZIP;
/*     */   }
/*     */   
/*     */   public void setDealershipCity(String dealershipCity) {
/* 288 */     this.dealershipCity = dealershipCity;
/*     */   }
/*     */   
/*     */   public String getDealershipCity() {
/* 292 */     return this.dealershipCity;
/*     */   }
/*     */   
/*     */   public void setDealershipState(String dealershipState) {
/* 296 */     this.dealershipState = dealershipState;
/*     */   }
/*     */   
/*     */   public String getDealershipState() {
/* 300 */     return this.dealershipState;
/*     */   }
/*     */   
/*     */   public void setDealershipCountry(String dealershipCountry) {
/* 304 */     this.dealershipCountry = dealershipCountry;
/*     */   }
/*     */   
/*     */   public String getDealershipCountry() {
/* 308 */     return this.dealershipCountry;
/*     */   }
/*     */   
/*     */   public void setDealershipLanguage(String dealershipLanguage) {
/* 312 */     this.dealershipLanguage = dealershipLanguage;
/*     */   }
/*     */   
/*     */   public String getDealershipLanguage() {
/* 316 */     return this.dealershipLanguage;
/*     */   }
/*     */   
/*     */   public void setDealershipPhone(String dealershipPhone) {
/* 320 */     this.dealershipPhone = dealershipPhone;
/*     */   }
/*     */   
/*     */   public String getDealershipPhone() {
/* 324 */     return this.dealershipPhone;
/*     */   }
/*     */   
/*     */   public void setDealershipFax(String dealershipFax) {
/* 328 */     this.dealershipFax = dealershipFax;
/*     */   }
/*     */   
/*     */   public String getDealershipFax() {
/* 332 */     return this.dealershipFax;
/*     */   }
/*     */   
/*     */   public void setDealershipEmail(String dealershipEmail) {
/* 336 */     this.dealershipEmail = dealershipEmail;
/*     */   }
/*     */   
/*     */   public String getDealershipEmail() {
/* 340 */     return this.dealershipEmail;
/*     */   }
/*     */   
/*     */   public void setDealershipContacts(List dealershipContacts) {
/* 344 */     this.dealershipContacts = dealershipContacts;
/*     */   }
/*     */   
/*     */   public List getDealershipContacts() {
/* 348 */     return this.dealershipContacts;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\DealershipInformation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */