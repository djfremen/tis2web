/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.html.element.input.HtmlInputElement;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HtmlElementContainerBase
/*     */   implements HtmlElementContainer
/*     */ {
/*  21 */   protected List elements = new LinkedList();
/*     */   
/*  23 */   private Boolean disabled = null;
/*     */   
/*  25 */   private HtmlElementContainer parent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElement(HtmlElement element) {
/*  32 */     this.elements.add(element);
/*  33 */     element.setContainer(this);
/*     */   }
/*     */   
/*     */   public void addAllElements(List elements) {
/*  37 */     if (elements != null) {
/*  38 */       Iterator<HtmlElement> iter = elements.iterator();
/*  39 */       while (iter.hasNext()) {
/*  40 */         addElement(iter.next());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean check(boolean hints) {
/*  46 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  50 */     if (!isDisabled()) {
/*  51 */       Iterator iter = this.elements.iterator();
/*  52 */       while (iter.hasNext()) {
/*  53 */         Object element = iter.next();
/*  54 */         if (element instanceof HtmlInputElement) {
/*  55 */           HtmlInputElement inputElement = (HtmlInputElement)element;
/*  56 */           if (inputElement.clicked()) {
/*  57 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   public HtmlElementContainer getContainer() {
/*  70 */     return this.parent;
/*     */   }
/*     */   
/*     */   public HtmlElementContainer getTopLevelContainer() {
/*  74 */     HtmlElementContainer container = this;
/*  75 */     while (container != null && container.getContainer() != null) {
/*  76 */       container = container.getContainer();
/*     */     }
/*  78 */     return container;
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  86 */     Iterator iter = this.elements.iterator();
/*  87 */     while (iter.hasNext()) {
/*  88 */       Object element = iter.next();
/*  89 */       if (element instanceof HtmlInputElement) {
/*  90 */         HtmlInputElement inputElement = (HtmlInputElement)element;
/*  91 */         if (inputElement.clicked()) {
/*  92 */           return inputElement.onClick(submitParams);
/*     */         }
/*     */       } 
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public void removeElement(HtmlElement element) {
/* 100 */     if (element != null && this.elements.remove(element)) {
/* 101 */       element.setContainer(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeElements(Collection elements) {
/* 106 */     if (elements != null) {
/* 107 */       for (Iterator<HtmlElement> iter = elements.iterator(); iter.hasNext();) {
/* 108 */         removeElement(iter.next());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeAllElements() {
/* 114 */     Iterator<HtmlElement> iter = this.elements.iterator();
/* 115 */     while (iter.hasNext()) {
/* 116 */       ((HtmlElement)iter.next()).setContainer(null);
/*     */     }
/* 118 */     this.elements.clear();
/*     */   }
/*     */   
/*     */   public void setContainer(HtmlElementContainer container) {
/* 122 */     this.parent = container;
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/* 126 */     if (!isDisabled()) {
/* 127 */       Iterator iter = this.elements.iterator();
/* 128 */       while (iter.hasNext()) {
/* 129 */         Object element = iter.next();
/* 130 */         if (element instanceof HtmlInputElement) {
/* 131 */           ((HtmlInputElement)element).setValue(submitParams);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDisabled(Boolean disabled) {
/* 138 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDisabled() {
/* 143 */     if (this.disabled != null) {
/* 144 */       return this.disabled.booleanValue();
/*     */     }
/* 146 */     return (this.parent != null) ? this.parent.isDisabled() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 152 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 156 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getElements() {
/* 160 */     return this.elements;
/*     */   }
/*     */   
/*     */   private boolean containesBookmark(HtmlElement element, String bookmark) {
/* 164 */     if (bookmark.equals(element.getBookmark())) {
/* 165 */       return true;
/*     */     }
/* 167 */     if (element instanceof HtmlElementContainer) {
/*     */       try {
/* 169 */         Iterator<HtmlElement> iter = ((HtmlElementContainer)element).getElements().iterator();
/* 170 */         while (iter.hasNext()) {
/* 171 */           boolean result = containesBookmark(iter.next(), bookmark);
/* 172 */           if (result) {
/* 173 */             return true;
/*     */           }
/*     */         } 
/* 176 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */     
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean containesBookmark(String bookmark) {
/* 184 */     return containesBookmark((HtmlElement)this, bookmark);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElementContainerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */