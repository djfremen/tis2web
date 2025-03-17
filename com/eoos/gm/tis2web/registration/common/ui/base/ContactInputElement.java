/*    */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.registration.common.ui.UIDataProvider;
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContactInputElement
/*    */   extends HtmlElementContainerBase
/*    */   implements DealershipInfo.Contact
/*    */ {
/*    */   private static final String TEMPLATE = "<table class=\"contact_input\"><tr><th>{LABEL}:</th><td><table><tr><td>{INPUT_CONTACTNAME}</td><td>{INPUT_CONTACTLANG}</td></tr></table></td></tr></table>";
/*    */   private ClientContext context;
/*    */   private TextInputElement inputName;
/*    */   private SelectBoxSelectionElement inputLanguage;
/*    */   
/*    */   public ContactInputElement(final ClientContext context, Locale defaultLanguage) {
/* 29 */     this.context = context;
/* 30 */     this.inputName = new TextInputElement(context.createID(), 20, -1) {
/*    */         protected String getStyleClass() {
/* 32 */           return "cnameinput";
/*    */         }
/*    */       };
/* 35 */     addElement((HtmlElement)this.inputName);
/*    */     
/* 37 */     final List languages = UIDataProvider.getInstance().getLanguages(context.getLocale());
/* 38 */     this.inputLanguage = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*    */         {
/*    */           public List getData() {
/* 41 */             return languages;
/*    */           }
/*    */         },  1)
/*    */       {
/*    */         protected String getDisplayValue(Object option)
/*    */         {
/* 47 */           return ((Locale)option).getDisplayName(context.getLocale());
/*    */         }
/*    */       };
/*    */     
/* 51 */     addElement((HtmlElement)this.inputLanguage);
/* 52 */     if (defaultLanguage != null) {
/* 53 */       this.inputLanguage.setValue(defaultLanguage);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 58 */     StringBuffer ret = new StringBuffer("<table class=\"contact_input\"><tr><th>{LABEL}:</th><td><table><tr><td>{INPUT_CONTACTNAME}</td><td>{INPUT_CONTACTLANG}</td></tr></table></td></tr></table>");
/* 59 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("name") + " / " + this.context.getLabel("language"));
/* 60 */     StringUtilities.replace(ret, "{INPUT_CONTACTNAME}", this.inputName.getHtmlCode(params));
/* 61 */     StringUtilities.replace(ret, "{INPUT_CONTACTLANG}", this.inputLanguage.getHtmlCode(params));
/* 62 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public Locale getLanguage() {
/* 66 */     return (Locale)this.inputLanguage.getValue();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 70 */     return (String)this.inputName.getValue();
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 74 */     return new DealershipInfo.Contact()
/*    */       {
/*    */         public String getName() {
/* 77 */           return ContactInputElement.this.getName();
/*    */         }
/*    */         
/*    */         public Locale getLanguage() {
/* 81 */           return ContactInputElement.this.getLanguage();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 88 */     DealershipInfo.Contact contact = (DealershipInfo.Contact)value;
/* 89 */     if (contact != null) {
/* 90 */       this.inputName.setValue(contact.getName());
/* 91 */       this.inputLanguage.setValue(contact.getLanguage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\ContactInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */