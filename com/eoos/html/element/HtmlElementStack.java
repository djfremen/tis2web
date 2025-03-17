/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.html.element.input.HtmlInputElement;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HtmlElementStack
/*     */   implements HtmlInputElement, HtmlElementContainer
/*     */ {
/*     */   public static class EmptyStackException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*  24 */   private Stack stack = new Stack();
/*     */   
/*  26 */   private Boolean disabled = null;
/*     */   
/*  28 */   private HtmlElementContainer container = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean check(boolean hints) {
/*  35 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public synchronized boolean clicked() {
/*     */     try {
/*  40 */       HtmlElement element = peek();
/*  41 */       if (element instanceof HtmlInputElement) {
/*  42 */         return ((HtmlInputElement)element).clicked();
/*     */       }
/*  44 */       return false;
/*     */     }
/*  46 */     catch (EmptyStackException e) {
/*  47 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String getBookmark() {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public synchronized HtmlElementContainer getContainer() {
/*  57 */     return this.container;
/*     */   }
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/*     */     try {
/*  62 */       return peek().getHtmlCode(params);
/*  63 */     } catch (EmptyStackException e) {
/*  64 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized HtmlLayout getLayout() {
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized Object getValue() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public synchronized boolean isDisabled() {
/*  77 */     if (this.disabled != null) {
/*  78 */       return this.disabled.booleanValue();
/*     */     }
/*  80 */     return (this.container != null) ? this.container.isDisabled() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Object onClick(Map submitParams) {
/*     */     try {
/*  86 */       return ((HtmlInputElement)peek()).onClick(submitParams);
/*  87 */     } catch (EmptyStackException e) {
/*  88 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void setContainer(HtmlElementContainer container) {
/*  93 */     this.container = container;
/*     */   }
/*     */   
/*     */   public synchronized void setDisabled(Boolean disabled) {
/*  97 */     this.disabled = disabled;
/*     */   }
/*     */   
/*     */   public synchronized void setValue(Object value) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public synchronized void setValue(Map submitParams) {
/*     */     try {
/* 106 */       HtmlElement element = peek();
/* 107 */       if (element instanceof HtmlInputElement) {
/* 108 */         ((HtmlInputElement)element).setValue(submitParams);
/*     */       }
/* 110 */     } catch (EmptyStackException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void push(HtmlElement element) {
/* 116 */     this.stack.push(element);
/* 117 */     element.setContainer(this);
/*     */   }
/*     */   
/*     */   public synchronized void pop() {
/*     */     try {
/* 122 */       HtmlElement element = this.stack.pop();
/* 123 */       element.setContainer((HtmlElementContainer)null);
/* 124 */     } catch (java.util.EmptyStackException e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HtmlElement peek() throws EmptyStackException {
/*     */     try {
/* 130 */       return this.stack.peek();
/* 131 */     } catch (java.util.EmptyStackException e) {
/* 132 */       throw new EmptyStackException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addElement(HtmlElement element) {
/* 137 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeElement(HtmlElement element) {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 147 */     this.stack.clear();
/*     */   }
/*     */   
/*     */   public List getElements() {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int size() {
/* 155 */     return this.stack.size();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElementStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */