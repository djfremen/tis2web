/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.DESCRIPTIONType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATIONType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.TIS2WEB;
/*     */ import com.eoos.gm.tis2web.util.FileStreamFactory;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthorizationImpl
/*     */   implements Subscription, Authorization
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(AuthorizationImpl.class);
/*     */   
/*     */   private static String FORM;
/*     */   private static String EMAIL;
/*  31 */   private static Map authorizations = new HashMap<Object, Object>();
/*     */   private String authorizationID;
/*     */   private String organization;
/*     */   private String url;
/*     */   private String form;
/*     */   private String email;
/*  37 */   private Map descriptions = new HashMap<Object, Object>();
/*     */   private String group;
/*     */   
/*     */   private AuthorizationImpl(String authorizationID) {
/*  41 */     this.authorizationID = authorizationID.trim();
/*     */   }
/*     */   
/*     */   public static String getEMAIL() {
/*  45 */     return EMAIL;
/*     */   }
/*     */   
/*     */   public static String getFORM() {
/*  49 */     return FORM;
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/*  53 */     return this.authorizationID;
/*     */   }
/*     */   
/*     */   public String getSubscriptionID() {
/*  57 */     return this.authorizationID;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  61 */     return this.email;
/*     */   }
/*     */   
/*     */   public String getForm() {
/*  65 */     return this.form;
/*     */   }
/*     */   
/*     */   public String getGroup() {
/*  69 */     return this.group;
/*     */   }
/*     */   
/*     */   public String getOrganization() {
/*  73 */     return this.organization;
/*     */   }
/*     */   
/*     */   public String getUrl() {
/*  77 */     return this.url;
/*     */   }
/*     */   
/*     */   public String getDescription(Locale locale) {
/*  81 */     String description = (String)this.descriptions.get(locale.toString());
/*  82 */     if (description == null) {
/*  83 */       return (String)this.descriptions.get(locale.getLanguage());
/*     */     }
/*  85 */     return description;
/*     */   }
/*     */   
/*     */   public void addDescription(String locale, String description) {
/*  89 */     this.descriptions.put(locale.trim(), description.trim());
/*  90 */     this.descriptions.put(locale.substring(0, 2), description.trim());
/*     */   }
/*     */   
/*     */   public void setEmail(String email) {
/*  94 */     this.email = email.trim();
/*     */   }
/*     */   
/*     */   public void setForm(String form) {
/*  98 */     this.form = form.trim();
/*     */   }
/*     */   
/*     */   public void setGroup(String group) {
/* 102 */     this.group = group.trim();
/*     */   }
/*     */   
/*     */   public void setOrganization(String organization) {
/* 106 */     this.organization = organization.trim().toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public void setUrl(String url) {
/* 110 */     this.url = url.trim();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 114 */     return this.authorizationID;
/*     */   }
/*     */   
/*     */   public static Authorization lookup(String authorizationID) {
/* 118 */     return (Authorization)authorizations.get(authorizationID);
/*     */   }
/*     */   
/*     */   public static Collection getAuthorizations() {
/* 122 */     return authorizations.values();
/*     */   }
/*     */   
/*     */   public static String getEmail(Collection authorizations) {
/* 126 */     Authorization target = checkSalesOrganization(authorizations);
/* 127 */     if (target != null) {
/* 128 */       return target.getEmail();
/*     */     }
/* 130 */     return getEMAIL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getForm(Collection authorizations) {
/* 135 */     Authorization target = checkSalesOrganization(authorizations);
/* 136 */     if (target != null) {
/* 137 */       return target.getForm();
/*     */     }
/* 139 */     return getFORM();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Authorization checkSalesOrganization(Collection authorizations) {
/* 144 */     if (authorizations.isEmpty()) {
/* 145 */       throw new IllegalArgumentException();
/*     */     }
/* 147 */     Iterator<Authorization> it = authorizations.iterator();
/* 148 */     Authorization target = null;
/* 149 */     while (it.hasNext()) {
/* 150 */       if (target == null) {
/* 151 */         target = it.next(); continue;
/* 152 */       }  if (!target.getOrganization().equals(((Authorization)it.next()).getOrganization())) {
/* 153 */         return null;
/*     */       }
/*     */     } 
/* 156 */     return target;
/*     */   }
/*     */   
/*     */   private static void register(Authorization authorization) {
/* 160 */     authorizations.put(authorization.getAuthorizationID(), authorization);
/*     */   }
/*     */   
/*     */   public static void load(File file) {
/* 164 */     BufferedInputStream in = null;
/*     */     try {
/* 166 */       JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.registration.standalone.xml");
/* 167 */       Unmarshaller um = jc.createUnmarshaller();
/*     */       
/* 169 */       in = new BufferedInputStream(FileStreamFactory.getInstance().getInputStream(file));
/* 170 */       TIS2WEB t2w = (TIS2WEB)um.unmarshal(in);
/* 171 */       FORM = t2w.getFORM();
/* 172 */       EMAIL = t2w.getEMAIL();
/* 173 */       List<AUTHORIZATIONType> authorizations = t2w.getAUTHORIZATION();
/* 174 */       for (int i = 0; i < authorizations.size(); i++) {
/* 175 */         AUTHORIZATIONType a = authorizations.get(i);
/* 176 */         Authorization authorization = new AuthorizationImpl(a.getAuthorizationID());
/* 177 */         register(authorization);
/* 178 */         ORGANIZATIONType o = a.getORGANIZATION();
/* 179 */         authorization.setOrganization(o.getOrganizationID());
/* 180 */         authorization.setForm(o.getFORM());
/* 181 */         authorization.setEmail(o.getEMAIL());
/* 182 */         load(authorization, a.getDESCRIPTION());
/* 183 */         authorization.setGroup(a.getGROUP().getGroupID());
/*     */       } 
/* 185 */     } catch (Exception x) {
/* 186 */       log.error(x);
/*     */     } finally {
/* 188 */       if (in != null) {
/*     */         try {
/* 190 */           in.close();
/* 191 */         } catch (Exception ix) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void load(Authorization authorization, List<DESCRIPTIONType> descriptions) {
/* 199 */     for (int i = 0; i < descriptions.size(); i++) {
/* 200 */       DESCRIPTIONType d = descriptions.get(i);
/* 201 */       authorization.addDescription(d.getLocale(), d.getDescription());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\AuthorizationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */