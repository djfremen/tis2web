/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.DealershipContact;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DealershipAdapter
/*     */   implements DealershipInfo
/*     */ {
/*     */   private ClientContext context;
/*     */   private Dealership dealership;
/*     */   private List contacts;
/*     */   
/*     */   public DealershipAdapter(ClientContext context, Dealership dealership) {
/*  23 */     this.context = context;
/*  24 */     this.dealership = dealership;
/*     */   }
/*     */   
/*     */   public String getCity() {
/*  28 */     return this.dealership.getDealershipCity();
/*     */   }
/*     */   
/*     */   public List getContacts() {
/*  32 */     if (this.contacts == null && this.dealership.getDealershipContacts() != null) {
/*  33 */       this.contacts = new ContactsAdapter(this.context, this.dealership.getDealershipContacts());
/*     */     }
/*  35 */     return this.contacts;
/*     */   }
/*     */   
/*     */   public Locale getCountry() {
/*  39 */     String _country = this.dealership.getDealershipCountry();
/*  40 */     if (_country == null) {
/*  41 */       _country = SharedContext.getInstance(this.context).getCountry();
/*     */     }
/*  43 */     return new Locale("", (_country != null) ? _country : "");
/*     */   }
/*     */   
/*     */   public String getDealership() {
/*  47 */     return this.dealership.getDealershipName();
/*     */   }
/*     */   
/*     */   public String getDealershipID() {
/*  51 */     return this.dealership.getDealershipID();
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  55 */     return this.dealership.getDealershipEmail();
/*     */   }
/*     */   
/*     */   public String getFax() {
/*  59 */     return this.dealership.getDealershipFax();
/*     */   }
/*     */   
/*     */   public Locale getLanguage() {
/*  63 */     Locale ret = Util.parseLocale(this.dealership.getDealershipLanguage());
/*  64 */     return (ret != null) ? ret : this.context.getLocale();
/*     */   }
/*     */   
/*     */   public String getPhone() {
/*  68 */     return this.dealership.getDealershipPhone();
/*     */   }
/*     */   
/*     */   public String getState() {
/*  72 */     return this.dealership.getDealershipState();
/*     */   }
/*     */   
/*     */   public String getStreet() {
/*  76 */     return this.dealership.getDealershipStreet();
/*     */   }
/*     */   
/*     */   public String getZIP() {
/*  80 */     return this.dealership.getDealershipZIP();
/*     */   }
/*     */   
/*     */   public static class ContactsAdapter
/*     */     extends ArrayList {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public ContactsAdapter(ClientContext context, List<DealershipContact> contacts) {
/*  88 */       for (int i = 0; i < contacts.size(); i++) {
/*  89 */         DealershipContact contact = contacts.get(i);
/*  90 */         add((E)new DealershipAdapter.ContactAdapter(context, contact));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ContactAdapter
/*     */     implements DealershipInfo.Contact
/*     */   {
/*     */     private ClientContext context;
/*     */     private DealershipContact contact;
/*     */     
/*     */     public ContactAdapter(ClientContext context, DealershipContact contact) {
/* 103 */       this.context = context;
/* 104 */       this.contact = contact;
/*     */     }
/*     */     
/*     */     public Locale getLanguage() {
/* 108 */       Locale ret = Util.parseLocale(this.contact.getContactLanguage());
/* 109 */       return (ret != null) ? ret : this.context.getLocale();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 114 */       return this.contact.getContactName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\DealershipAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */