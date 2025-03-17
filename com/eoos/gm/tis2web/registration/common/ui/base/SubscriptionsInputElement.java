/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SubscriptionProvider;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
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
/*     */ public class SubscriptionsInputElement extends HtmlElementContainerBase {
/*  28 */   private static final Logger log = Logger.getLogger(SubscriptionsInputElement.class);
/*     */   private static final String TEMPLATE;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       TEMPLATE = ApplicationContext.getInstance().loadFile(SubscriptionsInputElement.class, "subscriptionpanel.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to init - rethrowing as runtime exception:" + e, e);
/*  36 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private SelectBoxSelectionElement subscriptionSelection;
/*     */   
/*     */   private ClickButtonElement buttonAdd;
/*  46 */   private Map removeButtons = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  48 */   private Set selectedSubscriptions = Collections.synchronizedSet(new LinkedHashSet());
/*     */   
/*  50 */   private Set unremovableSubscriptions = Collections.synchronizedSet(new HashSet());
/*     */   
/*     */   public SubscriptionsInputElement(final ClientContext context) {
/*  53 */     this.context = context;
/*     */     
/*  55 */     DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  58 */           return SubscriptionsInputElement.this.getSubscriptionDomain();
/*     */         }
/*     */       };
/*     */     
/*  62 */     this.subscriptionSelection = new SelectBoxSelectionElement(context.createID(), true, dataCallback, 1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  65 */           return ((Subscription)option).getDenotation(context.getLocale());
/*     */         }
/*     */       };
/*     */     
/*  69 */     addElement((HtmlElement)this.subscriptionSelection);
/*     */     try {
/*  71 */       this.subscriptionSelection.setValue(dataCallback.getData().get(0));
/*  72 */     } catch (Exception e) {
/*  73 */       log.warn("unable to set initial value for subscription selection, ignoring - exception:" + e, e);
/*     */     } 
/*     */     
/*  76 */     this.buttonAdd = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  79 */           return context.getLabel("add");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  84 */             Subscription subs = (Subscription)SubscriptionsInputElement.this.subscriptionSelection.getValue();
/*  85 */             if (subs != null) {
/*  86 */               SubscriptionsInputElement.this.selectedSubscriptions.add(subs);
/*     */             }
/*     */           }
/*  89 */           catch (Exception e) {
/*  90 */             SubscriptionsInputElement.log.error("unable to add subscription - exception:" + e, e);
/*     */           } 
/*  92 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  96 */     addElement((HtmlElement)this.buttonAdd);
/*     */   }
/*     */   
/*     */   private ClickButtonElement getRemoveButton(final Subscription subscription) {
/* 100 */     ClickButtonElement buttonRemove = (ClickButtonElement)this.removeButtons.get(subscription);
/* 101 */     if (buttonRemove == null && !this.unremovableSubscriptions.contains(subscription)) {
/* 102 */       buttonRemove = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 105 */             return SubscriptionsInputElement.this.context.getLabel("remove");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 110 */               SubscriptionsInputElement.this.selectedSubscriptions.remove(subscription);
/* 111 */             } catch (Exception e) {
/* 112 */               SubscriptionsInputElement.log.error("unable to remove subscription: " + String.valueOf(subscription) + " - exception:" + e, e);
/*     */             } 
/* 114 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 118 */       addElement((HtmlElement)buttonRemove);
/* 119 */       this.removeButtons.put(subscription, buttonRemove);
/*     */     } 
/* 121 */     return buttonRemove;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 125 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 126 */     StringUtilities.replace(ret, "{LABEL}", this.context.getLabel("subscriptions"));
/* 127 */     StringUtilities.replace(ret, "{SELECT_SUBS}", this.subscriptionSelection.getHtmlCode(params));
/* 128 */     StringUtilities.replace(ret, "{BUTTON_ADD}", this.buttonAdd.getHtmlCode(params));
/*     */     
/* 130 */     for (Iterator<Subscription> iter = this.selectedSubscriptions.iterator(); iter.hasNext(); ) {
/* 131 */       StringBuffer row = new StringBuffer("<tr><td>{SUBSCRIPTION}</td><td>{BUTTON}</td></tr>");
/* 132 */       Subscription subs = iter.next();
/* 133 */       StringUtilities.replace(row, "{SUBSCRIPTION}", subs.getDenotation(this.context.getLocale()));
/* 134 */       ClickButtonElement buttonRemove = getRemoveButton(subs);
/* 135 */       if (buttonRemove != null) {
/* 136 */         StringUtilities.replace(row, "{BUTTON}", buttonRemove.getHtmlCode(params));
/*     */       } else {
/* 138 */         StringUtilities.replace(row, "{BUTTON}", "");
/*     */       } 
/* 140 */       StringUtilities.replace(ret, "{ROWS}", row + "{ROWS}");
/*     */     } 
/* 142 */     StringUtilities.replace(ret, "{ROWS}", "");
/* 143 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/* 147 */     return new LinkedList(this.selectedSubscriptions);
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 151 */     this.selectedSubscriptions.clear();
/* 152 */     if (value != null) {
/* 153 */       if (value instanceof Subscription) {
/* 154 */         this.selectedSubscriptions.add(value);
/* 155 */       } else if (value instanceof Collection) {
/* 156 */         AssertUtil.ensure(value, (Condition)new AssertUtil.ElementOfType(Subscription.class));
/* 157 */         this.selectedSubscriptions.addAll((Collection)value);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addValue(Object value) {
/* 164 */     if (value == null) {
/* 165 */       this.selectedSubscriptions.clear();
/*     */     }
/* 167 */     else if (value instanceof Subscription) {
/* 168 */       this.selectedSubscriptions.add(value);
/* 169 */     } else if (value instanceof Collection) {
/*     */       
/* 171 */       this.selectedSubscriptions.addAll((Collection)value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnremovableSubscription(Subscription subscription) {
/* 177 */     this.unremovableSubscriptions.add(subscription);
/*     */   }
/*     */   
/*     */   protected List getSubscriptionDomain() {
/* 181 */     return SubscriptionProvider.getInstance().getSubscriptions(this.context);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\SubscriptionsInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */