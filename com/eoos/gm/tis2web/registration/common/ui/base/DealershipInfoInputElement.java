/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.CountrySelectionElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.UIData;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.UIDataProvider;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.LocaleSelectionElement;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DealershipInfoInputElement
/*     */   extends HtmlElementContainerBase
/*     */   implements DealershipInfo {
/*  29 */   private static final Logger log = Logger.getLogger(DealershipInfoInputElement.class);
/*     */   private static final String TEMPLATE;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       TEMPLATE = ApplicationContext.getInstance().loadFile(DealershipInfoInputElement.class, "dealershipinfopanel.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  37 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*  41 */   private static final Object NONE = new Object();
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement inputDealershipID;
/*     */   
/*     */   private TextInputElement inputDealership;
/*     */   
/*     */   private TextInputElement inputStreet;
/*     */   
/*     */   private TextInputElement inputZIP;
/*     */   
/*     */   private TextInputElement inputCity;
/*     */   
/*     */   private SelectBoxSelectionElement inputState;
/*     */   
/*     */   private SelectBoxSelectionElement inputCountry;
/*     */   
/*     */   private SelectBoxSelectionElement inputLanguage;
/*     */   
/*     */   private TextInputElement inputPhone;
/*     */   
/*     */   private TextInputElement inputFax;
/*     */   
/*     */   private TextInputElement inputEmail;
/*     */   
/*     */   private ClickButtonElement buttonAddContact;
/*     */   
/*  69 */   private List inputContacts = new LinkedList();
/*     */   
/*  71 */   private Map removeButtons = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   public DealershipInfoInputElement(final ClientContext context) {
/*  74 */     this.context = context;
/*     */     
/*  76 */     this.inputDealershipID = new TextInputElement(context.createID());
/*  77 */     addElement((HtmlElement)this.inputDealershipID);
/*     */     
/*  79 */     this.inputDealership = new TextInputElement(context.createID());
/*  80 */     addElement((HtmlElement)this.inputDealership);
/*     */     
/*  82 */     this.inputStreet = new TextInputElement(context.createID());
/*  83 */     addElement((HtmlElement)this.inputStreet);
/*     */     
/*  85 */     this.inputZIP = new TextInputElement(context.createID(), 10, 10);
/*  86 */     addElement((HtmlElement)this.inputZIP);
/*     */     
/*  88 */     this.inputCity = new TextInputElement(context.createID());
/*  89 */     addElement((HtmlElement)this.inputCity);
/*     */     
/*  91 */     this.inputState = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  94 */             List<Object> ret = new LinkedList(UIData.getStateListUS());
/*  95 */             ret.add(0, DealershipInfoInputElement.NONE);
/*  96 */             return ret;
/*     */           }
/*     */         }1)
/*     */       {
/*     */         protected String getDisplayValue(Object option)
/*     */         {
/* 102 */           if (option == DealershipInfoInputElement.NONE) {
/* 103 */             return context.getLabel("other");
/*     */           }
/* 105 */           return super.getDisplayValue(option);
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(Object value) {
/* 110 */           if (value == null) {
/* 111 */             value = DealershipInfoInputElement.NONE;
/*     */           }
/* 113 */           super.setValue(value);
/*     */         }
/*     */         
/*     */         public Object getValue() {
/* 117 */           Object ret = super.getValue();
/* 118 */           if (ret == DealershipInfoInputElement.NONE) {
/* 119 */             ret = null;
/*     */           }
/* 121 */           return ret;
/*     */         }
/*     */       };
/*     */     
/* 125 */     addElement((HtmlElement)this.inputState);
/*     */     
/* 127 */     final List countries = UIDataProvider.getInstance().getCountries(context.getLocale());
/* 128 */     this.inputCountry = (SelectBoxSelectionElement)new CountrySelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/* 131 */             return countries;
/*     */           }
/*     */         },  1, null, context.getLocale());
/*     */     
/* 135 */     addElement((HtmlElement)this.inputCountry);
/* 136 */     this.inputCountry.setValue(countries.get(0));
/*     */     
/* 138 */     final List languages = UIDataProvider.getInstance().getLanguages(context.getLocale());
/* 139 */     this.inputLanguage = (SelectBoxSelectionElement)new LocaleSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/* 142 */             List<Object> ret = languages;
/* 143 */             if (DealershipInfoInputElement.this.inputLanguage.getValue() != null && !ret.contains(DealershipInfoInputElement.this.inputLanguage.getValue())) {
/* 144 */               ret = new LinkedList(languages);
/* 145 */               ret.add(DealershipInfoInputElement.this.inputLanguage.getValue());
/*     */             } 
/* 147 */             return ret;
/*     */           }
/*     */         },  1, null, context.getLocale());
/*     */     
/* 151 */     addElement((HtmlElement)this.inputLanguage);
/* 152 */     this.inputLanguage.setValue(languages.get(0));
/*     */     
/* 154 */     this.inputPhone = new TextInputElement(context.createID());
/* 155 */     addElement((HtmlElement)this.inputPhone);
/*     */     
/* 157 */     this.inputFax = new TextInputElement(context.createID());
/* 158 */     addElement((HtmlElement)this.inputFax);
/*     */     
/* 160 */     this.inputEmail = new TextInputElement(context.createID());
/* 161 */     addElement((HtmlElement)this.inputEmail);
/*     */     
/* 163 */     this.buttonAddContact = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 166 */           return context.getLabel("add");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 170 */           DealershipInfoInputElement.this.addNewContactInput();
/* 171 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 175 */     addElement((HtmlElement)this.buttonAddContact);
/*     */     
/* 177 */     addNewContactInput();
/*     */   }
/*     */ 
/*     */   
/*     */   private ContactInputElement addNewContactInput() {
/* 182 */     ContactInputElement cie = new ContactInputElement(this.context, (Locale)this.inputLanguage.getValue());
/* 183 */     addElement((HtmlElement)cie);
/* 184 */     this.inputContacts.add(cie);
/* 185 */     return cie;
/*     */   }
/*     */   
/*     */   private void removeAllContactInputs() {
/* 189 */     for (Iterator<ContactInputElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 190 */       ContactInputElement cie = iter.next();
/* 191 */       removeElement((HtmlElement)cie);
/* 192 */       iter.remove();
/* 193 */       ClickButtonElement removeButton = getRemoveButton((HtmlElement)cie);
/* 194 */       if (removeButton != null) {
/* 195 */         removeElement((HtmlElement)removeButton);
/* 196 */         this.removeButtons.remove(cie);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ClickButtonElement getRemoveButton(final HtmlElement contactElement) {
/* 202 */     ClickButtonElement ret = (ClickButtonElement)this.removeButtons.get(contactElement);
/* 203 */     if (ret == null) {
/* 204 */       ret = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 207 */             return DealershipInfoInputElement.this.context.getLabel("remove");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 211 */             DealershipInfoInputElement.this.removeElement(contactElement);
/* 212 */             DealershipInfoInputElement.this.inputContacts.remove(contactElement);
/* 213 */             DealershipInfoInputElement.this.removeElement((HtmlElement)this);
/* 214 */             DealershipInfoInputElement.this.removeButtons.remove(contactElement);
/* 215 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 219 */       addElement((HtmlElement)ret);
/* 220 */       this.removeButtons.put(contactElement, ret);
/*     */     } 
/* 222 */     return ret;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 226 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/*     */     
/* 228 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIP_ID}", this.context.getLabel("dealership.id"));
/* 229 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIP_ID}", this.inputDealershipID.getHtmlCode(params));
/*     */     
/* 231 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIP}", this.context.getLabel("dealership"));
/* 232 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIP}", this.inputDealership.getHtmlCode(params));
/*     */     
/* 234 */     StringUtilities.replace(ret, "{LABEL_STREET}", this.context.getLabel("street"));
/* 235 */     StringUtilities.replace(ret, "{INPUT_STREET}", this.inputStreet.getHtmlCode(params));
/*     */     
/* 237 */     StringUtilities.replace(ret, "{LABEL_ZIP}", this.context.getLabel("zip"));
/* 238 */     StringUtilities.replace(ret, "{LABEL_CITY}", this.context.getLabel("city"));
/* 239 */     StringUtilities.replace(ret, "{INPUT_ZIP}", this.inputZIP.getHtmlCode(params));
/* 240 */     StringUtilities.replace(ret, "{INPUT_CITY}", this.inputCity.getHtmlCode(params));
/*     */     
/* 242 */     StringUtilities.replace(ret, "{LABEL_STATE}", this.context.getLabel("state"));
/* 243 */     StringUtilities.replace(ret, "{INPUT_STATE}", this.inputState.getHtmlCode(params));
/*     */     
/* 245 */     StringUtilities.replace(ret, "{LABEL_COUNTRY}", this.context.getLabel("country"));
/* 246 */     StringUtilities.replace(ret, "{INPUT_COUNTRY}", this.inputCountry.getHtmlCode(params));
/*     */     
/* 248 */     StringUtilities.replace(ret, "{LABEL_LANGUAGE}", this.context.getLabel("language"));
/* 249 */     StringUtilities.replace(ret, "{INPUT_LANGUAGE}", this.inputLanguage.getHtmlCode(params));
/*     */     
/* 251 */     StringUtilities.replace(ret, "{LABEL_PHONE_FAX_EMAIL}", this.context.getLabel("phone") + " / " + this.context.getLabel("fax") + " / " + this.context.getLabel("email"));
/* 252 */     StringUtilities.replace(ret, "{INPUT_PHONE}", this.inputPhone.getHtmlCode(params));
/* 253 */     StringUtilities.replace(ret, "{INPUT_FAX}", this.inputFax.getHtmlCode(params));
/* 254 */     StringUtilities.replace(ret, "{INPUT_EMAIL}", this.inputEmail.getHtmlCode(params));
/*     */     
/* 256 */     StringUtilities.replace(ret, "{LABEL_LANGUAGE}", this.context.getLabel("language"));
/* 257 */     StringUtilities.replace(ret, "{INPUT_LANGUAGE}", this.inputLanguage.getHtmlCode(params));
/*     */     
/* 259 */     StringUtilities.replace(ret, "{LABEL_CONTACTS}", this.context.getLabel("contacts"));
/*     */     
/* 261 */     for (Iterator<HtmlElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 262 */       HtmlElement element = iter.next();
/* 263 */       ClickButtonElement removeButton = getRemoveButton(element);
/* 264 */       StringUtilities.replace(ret, "{CONTACT_ROWS}", "<tr><td><table><tr><td>" + element.getHtmlCode(params) + "</td><td>" + removeButton.getHtmlCode(params) + "</td></tr></table></td></tr>\n{CONTACT_ROWS}");
/*     */     } 
/* 266 */     StringUtilities.replace(ret, "{CONTACT_ROWS}", "");
/* 267 */     StringUtilities.replace(ret, "{BUTTON_ADDCONTACT}", this.buttonAddContact.getHtmlCode(params));
/*     */     
/* 269 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public String getCity() {
/* 273 */     return (String)this.inputCity.getValue();
/*     */   }
/*     */   
/*     */   public List getContacts() {
/* 277 */     List<DealershipInfo.Contact> retValue = new LinkedList();
/* 278 */     for (Iterator<ContactInputElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 279 */       ContactInputElement cie = iter.next();
/* 280 */       retValue.add((DealershipInfo.Contact)cie.getValue());
/*     */     } 
/* 282 */     return retValue;
/*     */   }
/*     */   
/*     */   public Locale getCountry() {
/* 286 */     return (Locale)this.inputCountry.getValue();
/*     */   }
/*     */   
/*     */   public String getDealership() {
/* 290 */     return (String)this.inputDealership.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDealershipID() {
/* 295 */     return (String)this.inputDealershipID.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEmail() {
/* 300 */     return (String)this.inputEmail.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFax() {
/* 305 */     return (String)this.inputFax.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale getLanguage() {
/* 310 */     return (Locale)this.inputLanguage.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhone() {
/* 315 */     return (String)this.inputPhone.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStreet() {
/* 320 */     return (String)this.inputStreet.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getZIP() {
/* 325 */     return (String)this.inputZIP.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 330 */     return this;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 334 */     DealershipInfo di = (DealershipInfo)value;
/* 335 */     if (di != null) {
/* 336 */       this.inputDealershipID.setValue(di.getDealershipID());
/* 337 */       this.inputDealership.setValue(di.getDealership());
/* 338 */       this.inputStreet.setValue(di.getStreet());
/* 339 */       this.inputZIP.setValue(di.getZIP());
/* 340 */       this.inputCity.setValue(di.getCity());
/* 341 */       this.inputState.setValue(di.getState());
/* 342 */       this.inputCountry.setValue(di.getCountry());
/* 343 */       this.inputLanguage.setValue(di.getLanguage());
/* 344 */       this.inputPhone.setValue(di.getPhone());
/* 345 */       this.inputFax.setValue(di.getFax());
/* 346 */       this.inputEmail.setValue(di.getEmail());
/*     */       
/* 348 */       removeAllContactInputs();
/* 349 */       List<DealershipInfo.Contact> contacts = di.getContacts();
/* 350 */       if (contacts != null && contacts.size() > 0) {
/* 351 */         for (int i = 0; i < contacts.size(); i++) {
/* 352 */           DealershipInfo.Contact contact = contacts.get(i);
/* 353 */           ContactInputElement cie = addNewContactInput();
/* 354 */           cie.setValue(contact);
/*     */         } 
/*     */       } else {
/* 357 */         addNewContactInput();
/*     */       } 
/*     */     } else {
/* 360 */       removeAllContactInputs();
/* 361 */       addNewContactInput();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getState() {
/* 366 */     return (String)this.inputState.getValue();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\DealershipInfoInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */