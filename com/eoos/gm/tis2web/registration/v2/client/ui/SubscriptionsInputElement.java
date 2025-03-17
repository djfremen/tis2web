/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.AssertUtil;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubscriptionsInputElement
/*     */   extends ContextualElementContainerBase
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(SubscriptionsInputElement.class); private static final String TEMPLATE; private Callback callback; private SelectBoxSelectionElement subscriptionSelection;
/*     */   private ClickButtonElement buttonAdd;
/*     */   
/*     */   static {
/*     */     try {
/*  38 */       TEMPLATE = ApplicationContext.getInstance().loadFile(SubscriptionsInputElement.class, "subscriptionpanel.html", null).toString();
/*  39 */     } catch (Exception e) {
/*  40 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  41 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private Map removeButtons = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  53 */   private Set additionalSubscriptions = Collections.synchronizedSet(new LinkedHashSet());
/*     */   
/*     */   private boolean singleSelect = false;
/*     */   
/*     */   public SubscriptionsInputElement(final ClientContext context, final boolean singleSelect, final Callback callback) {
/*  58 */     super(context);
/*  59 */     this.callback = callback;
/*  60 */     this.singleSelect = singleSelect;
/*     */     
/*  62 */     DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  65 */           return callback.getSubscriptionDomain();
/*     */         }
/*     */       };
/*     */     
/*  69 */     this.subscriptionSelection = new SelectBoxSelectionElement(context.createID(), true, dataCallback, 1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  72 */           return ((Subscription)option).getDenotation(context.getLocale());
/*     */         }
/*     */       };
/*     */     
/*  76 */     addElement((HtmlElement)this.subscriptionSelection);
/*     */     try {
/*  78 */       this.subscriptionSelection.setValue(dataCallback.getData().get(0));
/*  79 */     } catch (Exception e) {
/*  80 */       log.warn("unable to set initial value for subscription selection, ignoring - exception:" + e, e);
/*     */     } 
/*     */     
/*  83 */     this.buttonAdd = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  86 */           return singleSelect ? context.getLabel("set") : context.getLabel("add");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  91 */             Subscription subs = (Subscription)SubscriptionsInputElement.this.subscriptionSelection.getValue();
/*  92 */             if (subs != null) {
/*  93 */               if (singleSelect) {
/*  94 */                 SubscriptionsInputElement.this.additionalSubscriptions = new HashSet();
/*     */               }
/*  96 */               SubscriptionsInputElement.this.additionalSubscriptions.add(subs);
/*     */             } 
/*  98 */             return null;
/*     */           }
/* 100 */           catch (Exception e) {
/* 101 */             SubscriptionsInputElement.log.error("unable to add subscription - exception:" + e, e);
/* 102 */             return SubscriptionsInputElement.this.getErrorPopup(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 107 */     addElement((HtmlElement)this.buttonAdd);
/*     */   }
/*     */   
/*     */   private Collection getUnremoveableSubscriptions() {
/* 111 */     Collection ret = this.callback.getExistingSubscriptions();
/* 112 */     if (ret == null) {
/* 113 */       ret = Collections.EMPTY_SET;
/*     */     }
/* 115 */     return ret;
/*     */   } public static interface Callback {
/*     */     Collection getExistingSubscriptions(); List getSubscriptionDomain(); }
/*     */   private Collection getSelectedSubscriptions() {
/* 119 */     Collection ret = new HashSet();
/* 120 */     Collection existing = this.callback.getExistingSubscriptions();
/* 121 */     if (existing != null) {
/* 122 */       ret.addAll(existing);
/*     */     }
/* 124 */     ret.addAll(this.additionalSubscriptions);
/* 125 */     return ret;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getRemoveButton(final Subscription subscription) {
/* 129 */     ClickButtonElement buttonRemove = (ClickButtonElement)this.removeButtons.get(subscription);
/* 130 */     if (buttonRemove == null && !getUnremoveableSubscriptions().contains(subscription)) {
/* 131 */       buttonRemove = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 134 */             return SubscriptionsInputElement.this.context.getLabel("remove");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 139 */               SubscriptionsInputElement.this.additionalSubscriptions.remove(subscription);
/* 140 */               return null;
/* 141 */             } catch (Exception e) {
/* 142 */               SubscriptionsInputElement.log.error("unable to remove subscription: " + String.valueOf(subscription) + " - exception:" + e, e);
/* 143 */               return SubscriptionsInputElement.this.getErrorPopup(e);
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 148 */       addElement((HtmlElement)buttonRemove);
/* 149 */       this.removeButtons.put(subscription, buttonRemove);
/*     */     } 
/* 151 */     return buttonRemove;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 155 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 156 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("subscriptions"));
/* 157 */     StringUtilities.replace(ret, "{SELECT_SUBS}", this.subscriptionSelection.getHtmlCode(params));
/* 158 */     StringUtilities.replace(ret, "{BUTTON_ADD}", this.buttonAdd.getHtmlCode(params));
/*     */     
/* 160 */     for (Iterator<Subscription> iter = getSelectedSubscriptions().iterator(); iter.hasNext(); ) {
/* 161 */       StringBuffer row = new StringBuffer("<tr><td>{SUBSCRIPTION}</td><td>{BUTTON}</td></tr>");
/* 162 */       Subscription subs = iter.next();
/* 163 */       StringUtilities.replace(row, "{SUBSCRIPTION}", subs.getDenotation(this.context.getLocale()));
/* 164 */       ClickButtonElement buttonRemove = getRemoveButton(subs);
/* 165 */       if (buttonRemove != null && !this.singleSelect) {
/* 166 */         StringUtilities.replace(row, "{BUTTON}", buttonRemove.getHtmlCode(params));
/*     */       } else {
/* 168 */         StringUtilities.replace(row, "{BUTTON}", "");
/*     */       } 
/* 170 */       StringUtilities.replace(ret, "{ROWS}", row + "{ROWS}");
/*     */     } 
/* 172 */     StringUtilities.replace(ret, "{ROWS}", "");
/* 173 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/* 177 */     return new LinkedList(getSelectedSubscriptions());
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 181 */     this.additionalSubscriptions.clear();
/* 182 */     if (value != null)
/* 183 */       if (value instanceof Subscription) {
/* 184 */         this.additionalSubscriptions.add(value);
/* 185 */       } else if (value instanceof Collection) {
/* 186 */         AssertUtil.ensure(value, (Condition)new AssertUtil.ElementOfType(Subscription.class));
/* 187 */         this.additionalSubscriptions.addAll((Collection)value);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\SubscriptionsInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */