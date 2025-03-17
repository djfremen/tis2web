/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.CountrySelectionElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.UIData;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.UIDataProvider;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.ContactInputElement;
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
/*  30 */   private static final Logger log = Logger.getLogger(DealershipInfoInputElement.class);
/*     */   private static final String TEMPLATE;
/*     */   
/*     */   static {
/*     */     try {
/*  35 */       TEMPLATE = ApplicationContext.getInstance().loadFile(DealershipInfoInputElement.class, "dealershipinfopanel.html", null).toString();
/*  36 */     } catch (Exception e) {
/*  37 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  38 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*  42 */   private static final Object NONE = new Object();
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
/*  70 */   private List inputContacts = new LinkedList();
/*     */   
/*  72 */   private Map removeButtons = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   public DealershipInfoInputElement(final ClientContext context) {
/*  75 */     this.context = context;
/*     */     
/*  77 */     this.inputDealershipID = new TextInputElement(context.createID(), 10, 10);
/*  78 */     addElement((HtmlElement)this.inputDealershipID);
/*     */     
/*  80 */     this.inputDealership = new TextInputElement(context.createID());
/*  81 */     addElement((HtmlElement)this.inputDealership);
/*     */     
/*  83 */     this.inputStreet = new TextInputElement(context.createID());
/*  84 */     addElement((HtmlElement)this.inputStreet);
/*     */     
/*  86 */     this.inputZIP = new TextInputElement(context.createID(), 10, 10);
/*  87 */     addElement((HtmlElement)this.inputZIP);
/*     */     
/*  89 */     this.inputCity = new TextInputElement(context.createID());
/*  90 */     addElement((HtmlElement)this.inputCity);
/*     */     
/*  92 */     this.inputState = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  95 */             List<Object> ret = new LinkedList(UIData.getStateListUS());
/*  96 */             ret.add(0, DealershipInfoInputElement.NONE);
/*  97 */             return ret;
/*     */           }
/*     */         }1)
/*     */       {
/*     */         protected String getDisplayValue(Object option)
/*     */         {
/* 103 */           if (option == DealershipInfoInputElement.NONE) {
/* 104 */             return context.getLabel("other");
/*     */           }
/* 106 */           return super.getDisplayValue(option);
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(Object value) {
/* 111 */           if (value == null) {
/* 112 */             value = DealershipInfoInputElement.NONE;
/*     */           }
/* 114 */           super.setValue(value);
/*     */         }
/*     */         
/*     */         public Object getValue() {
/* 118 */           Object ret = super.getValue();
/* 119 */           if (ret == DealershipInfoInputElement.NONE) {
/* 120 */             ret = null;
/*     */           }
/* 122 */           return ret;
/*     */         }
/*     */       };
/*     */     
/* 126 */     addElement((HtmlElement)this.inputState);
/*     */     
/* 128 */     final List countries = UIDataProvider.getInstance().getCountries(context.getLocale());
/* 129 */     this.inputCountry = (SelectBoxSelectionElement)new CountrySelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/* 132 */             return countries;
/*     */           }
/*     */         },  1, null, context.getLocale());
/*     */     
/* 136 */     addElement((HtmlElement)this.inputCountry);
/* 137 */     this.inputCountry.setValue(countries.get(0));
/*     */     
/* 139 */     final List languages = UIDataProvider.getInstance().getLanguages(context.getLocale());
/* 140 */     this.inputLanguage = (SelectBoxSelectionElement)new LocaleSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/* 143 */             return languages;
/*     */           }
/*     */         },  1, null, context.getLocale());
/*     */     
/* 147 */     addElement((HtmlElement)this.inputLanguage);
/* 148 */     this.inputLanguage.setValue(languages.get(0));
/*     */     
/* 150 */     this.inputPhone = new TextInputElement(context.createID());
/* 151 */     addElement((HtmlElement)this.inputPhone);
/*     */     
/* 153 */     this.inputFax = new TextInputElement(context.createID());
/* 154 */     addElement((HtmlElement)this.inputFax);
/*     */     
/* 156 */     this.inputEmail = new TextInputElement(context.createID());
/* 157 */     addElement((HtmlElement)this.inputEmail);
/*     */     
/* 159 */     this.buttonAddContact = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 162 */           return context.getLabel("add");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 166 */           DealershipInfoInputElement.this.addNewContactInput();
/* 167 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 171 */     addElement((HtmlElement)this.buttonAddContact);
/*     */     
/* 173 */     addNewContactInput();
/*     */   }
/*     */ 
/*     */   
/*     */   private ContactInputElement addNewContactInput() {
/* 178 */     ContactInputElement cie = new ContactInputElement(this.context, (Locale)this.inputLanguage.getValue());
/* 179 */     addElement((HtmlElement)cie);
/* 180 */     this.inputContacts.add(cie);
/* 181 */     return cie;
/*     */   }
/*     */   
/*     */   private void removeAllContactInputs() {
/* 185 */     for (Iterator<ContactInputElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 186 */       ContactInputElement cie = iter.next();
/* 187 */       removeElement((HtmlElement)cie);
/* 188 */       iter.remove();
/* 189 */       ClickButtonElement removeButton = getRemoveButton((HtmlElement)cie);
/* 190 */       if (removeButton != null) {
/* 191 */         removeElement((HtmlElement)removeButton);
/* 192 */         this.removeButtons.remove(cie);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ClickButtonElement getRemoveButton(final HtmlElement contactElement) {
/* 198 */     ClickButtonElement ret = (ClickButtonElement)this.removeButtons.get(contactElement);
/* 199 */     if (ret == null) {
/* 200 */       ret = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 203 */             return DealershipInfoInputElement.this.context.getLabel("remove");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 207 */             DealershipInfoInputElement.this.removeElement(contactElement);
/* 208 */             DealershipInfoInputElement.this.inputContacts.remove(contactElement);
/* 209 */             DealershipInfoInputElement.this.removeElement((HtmlElement)this);
/* 210 */             DealershipInfoInputElement.this.removeButtons.remove(contactElement);
/* 211 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 215 */       addElement((HtmlElement)ret);
/* 216 */       this.removeButtons.put(contactElement, ret);
/*     */     } 
/* 218 */     return ret;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 222 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/*     */     
/* 224 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIP_ID}", this.context.getLabel("dealership.id"));
/* 225 */     StringUtilities.replace(ret, "{HINT_DEALERSHIP_ID}", this.context.getMessage("hint.dealership.id"));
/* 226 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIP_ID}", this.inputDealershipID.getHtmlCode(params));
/*     */     
/* 228 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIP}", this.context.getLabel("dealership"));
/* 229 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIP}", this.inputDealership.getHtmlCode(params));
/*     */     
/* 231 */     StringUtilities.replace(ret, "{LABEL_STREET}", this.context.getLabel("street"));
/* 232 */     StringUtilities.replace(ret, "{INPUT_STREET}", this.inputStreet.getHtmlCode(params));
/*     */     
/* 234 */     StringUtilities.replace(ret, "{LABEL_ZIP}", this.context.getLabel("zip"));
/* 235 */     StringUtilities.replace(ret, "{LABEL_CITY}", this.context.getLabel("city"));
/* 236 */     StringUtilities.replace(ret, "{INPUT_ZIP}", this.inputZIP.getHtmlCode(params));
/* 237 */     StringUtilities.replace(ret, "{INPUT_CITY}", this.inputCity.getHtmlCode(params));
/*     */     
/* 239 */     StringUtilities.replace(ret, "{LABEL_STATE}", this.context.getLabel("state"));
/* 240 */     StringUtilities.replace(ret, "{INPUT_STATE}", this.inputState.getHtmlCode(params));
/*     */     
/* 242 */     StringUtilities.replace(ret, "{LABEL_COUNTRY}", this.context.getLabel("country"));
/* 243 */     StringUtilities.replace(ret, "{INPUT_COUNTRY}", this.inputCountry.getHtmlCode(params));
/*     */     
/* 245 */     StringUtilities.replace(ret, "{LABEL_LANGUAGE}", this.context.getLabel("language"));
/* 246 */     StringUtilities.replace(ret, "{INPUT_LANGUAGE}", this.inputLanguage.getHtmlCode(params));
/*     */     
/* 248 */     StringUtilities.replace(ret, "{LABEL_PHONE}", this.context.getLabel("phone"));
/* 249 */     StringUtilities.replace(ret, "{INPUT_PHONE}", this.inputPhone.getHtmlCode(params));
/*     */     
/* 251 */     StringUtilities.replace(ret, "{LABEL_FAX}", this.context.getLabel("fax"));
/* 252 */     StringUtilities.replace(ret, "{INPUT_FAX}", this.inputFax.getHtmlCode(params));
/*     */     
/* 254 */     StringUtilities.replace(ret, "{LABEL_EMAIL}", this.context.getLabel("email"));
/* 255 */     StringUtilities.replace(ret, "{INPUT_EMAIL}", this.inputEmail.getHtmlCode(params));
/*     */     
/* 257 */     StringUtilities.replace(ret, "{LABEL_LANGUAGE}", this.context.getLabel("language"));
/* 258 */     StringUtilities.replace(ret, "{INPUT_LANGUAGE}", this.inputLanguage.getHtmlCode(params));
/*     */     
/* 260 */     StringUtilities.replace(ret, "{LABEL_CONTACTS}", this.context.getLabel("contacts"));
/*     */     
/* 262 */     for (Iterator<HtmlElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 263 */       HtmlElement element = iter.next();
/* 264 */       ClickButtonElement removeButton = getRemoveButton(element);
/* 265 */       StringUtilities.replace(ret, "{CONTACT_ROWS}", "<tr><td><table><tr><td>" + element.getHtmlCode(params) + "</td><td>" + removeButton.getHtmlCode(params) + "</td></tr></table></td></tr>\n{CONTACT_ROWS}");
/*     */     } 
/* 267 */     StringUtilities.replace(ret, "{CONTACT_ROWS}", "");
/* 268 */     StringUtilities.replace(ret, "{BUTTON_ADDCONTACT}", this.buttonAddContact.getHtmlCode(params));
/* 269 */     StringUtilities.replace(ret, "{HINT_BUTTON_ADD}", this.context.getMessage("registration.hint.button.add.contact"));
/*     */     
/* 271 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public String getCity() {
/* 275 */     return (String)this.inputCity.getValue();
/*     */   }
/*     */   
/*     */   public List getContacts() {
/* 279 */     List<DealershipInfo.Contact> retValue = new LinkedList();
/* 280 */     for (Iterator<ContactInputElement> iter = this.inputContacts.iterator(); iter.hasNext(); ) {
/* 281 */       ContactInputElement cie = iter.next();
/* 282 */       retValue.add((DealershipInfo.Contact)cie.getValue());
/*     */     } 
/* 284 */     return retValue;
/*     */   }
/*     */   
/*     */   public Locale getCountry() {
/* 288 */     return (Locale)this.inputCountry.getValue();
/*     */   }
/*     */   
/*     */   public String getDealership() {
/* 292 */     return (String)this.inputDealership.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDealershipID() {
/* 297 */     return (String)this.inputDealershipID.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEmail() {
/* 302 */     return (String)this.inputEmail.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFax() {
/* 307 */     return (String)this.inputFax.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale getLanguage() {
/* 312 */     return (Locale)this.inputLanguage.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhone() {
/* 317 */     return (String)this.inputPhone.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStreet() {
/* 322 */     return (String)this.inputStreet.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getZIP() {
/* 327 */     return (String)this.inputZIP.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 332 */     return this;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 336 */     DealershipInfo di = (DealershipInfo)value;
/* 337 */     if (di != null) {
/* 338 */       this.inputDealershipID.setValue(di.getDealershipID());
/* 339 */       this.inputDealership.setValue(di.getDealership());
/* 340 */       this.inputStreet.setValue(di.getStreet());
/* 341 */       this.inputZIP.setValue(di.getZIP());
/* 342 */       this.inputCity.setValue(di.getCity());
/* 343 */       this.inputState.setValue(di.getState());
/* 344 */       this.inputCountry.setValue(di.getCountry());
/* 345 */       this.inputLanguage.setValue(di.getLanguage());
/* 346 */       this.inputPhone.setValue(di.getPhone());
/* 347 */       this.inputFax.setValue(di.getFax());
/* 348 */       this.inputEmail.setValue(di.getEmail());
/*     */       
/* 350 */       removeAllContactInputs();
/* 351 */       List<DealershipInfo.Contact> contacts = di.getContacts();
/* 352 */       if (contacts != null && contacts.size() > 0) {
/* 353 */         for (int i = 0; i < contacts.size(); i++) {
/* 354 */           DealershipInfo.Contact contact = contacts.get(i);
/* 355 */           ContactInputElement cie = addNewContactInput();
/* 356 */           cie.setValue(contact);
/*     */         } 
/*     */       } else {
/* 359 */         addNewContactInput();
/*     */       } 
/*     */     } else {
/* 362 */       removeAllContactInputs();
/* 363 */       addNewContactInput();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getState() {
/* 368 */     return (String)this.inputState.getValue();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\DealershipInfoInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */