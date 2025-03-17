/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.HtmlLayout;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ 
/*     */ public abstract class LinkDialog
/*     */   extends DialogBase
/*     */ {
/*     */   protected ClientContext context;
/*  31 */   private HtmlElementStack stack = new HtmlElementStack();
/*     */   
/*     */   protected SIO sio;
/*     */   
/*     */   protected String title;
/*     */   
/*     */   private LinkListElement wd;
/*     */ 
/*     */   
/*  40 */   private DialogLayout layout = new DialogLayout() {
/*     */       public String getHorizontalAlignment() {
/*  42 */         return "center";
/*     */       }
/*     */       
/*     */       public String getWidth() {
/*  46 */         boolean isDocument = false;
/*     */         try {
/*  48 */           HtmlElement elem = LinkDialog.this.stack.peek();
/*  49 */           if (LinkDialog.this.checkInstance(elem)) {
/*  50 */             isDocument = true;
/*     */           }
/*  52 */         } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */ 
/*     */         
/*  55 */         return isDocument ? "100%" : null;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonClose;
/*     */ 
/*     */   
/*     */   public LinkDialog(ClientContext context, SIO sio, LinkListElement wd) {
/*  64 */     super(context);
/*  65 */     this.context = context;
/*  66 */     setNode(sio, wd);
/*  67 */     addElement((HtmlElement)this.stack);
/*  68 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  70 */           return LinkDialog.this.context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  75 */             HtmlElement elem = LinkDialog.this.stack.peek();
/*  76 */             LinkDialog.this.unregister(elem);
/*  77 */             LinkDialog.this.stack.pop();
/*  78 */             LinkDialog.this.resetTitle();
/*  79 */           } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */           
/*  81 */           LinkDialog dlg = LinkDialog.this;
/*  82 */           MainPage mp = MainPage.getInstance(LinkDialog.this.context);
/*  83 */           return (LinkDialog.this.stack.size() > 0) ? dlg : mp;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isDispose() {
/*  88 */           return (LinkDialog.this.stack.size() <= 1);
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/*  92 */           return (LinkDialog.this.stack.size() > 1) ? null : "_self";
/*     */         }
/*     */       };
/*  95 */     addElement((HtmlElement)this.buttonClose);
/*     */   }
/*     */   
/*     */   public synchronized void setNode(SIO sio, LinkListElement wd) {
/*     */     try {
/* 100 */       HtmlElement elem = this.stack.peek();
/* 101 */       this.stack.pop();
/* 102 */       this.stack.pop();
/* 103 */       unregister(elem);
/* 104 */     } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */     
/* 106 */     this.wd = wd;
/* 107 */     this.sio = sio;
/* 108 */     wd.setPage(this);
/* 109 */     wd.setStack(this.stack);
/*     */     
/* 111 */     List data = wd.getData();
/* 112 */     if (data.size() == 1) {
/* 113 */       wd.setDocument(data.get(0));
/*     */     } else {
/* 115 */       this.stack.push((HtmlElement)wd);
/* 116 */       resetTitle();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean show() {
/* 121 */     return (this.wd.getData().size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getContent(Map params) {
/* 130 */     StringBuffer code = new StringBuffer("<table width=\"{WIDTH}\"><tr><td >{DOCUMENT}</td></tr><tr><td align = \"center\">{BUTTON}</td></tr></table>");
/* 131 */     StringUtilities.replace(code, "{WIDTH}", this.layout.getWidth());
/*     */     
/* 133 */     StringUtilities.replace(code, "{BUTTON}", this.buttonClose.getHtmlCode(params));
/* 134 */     StringUtilities.replace(code, "{DOCUMENT}", this.stack.getHtmlCode(params));
/* 135 */     return code.toString();
/*     */   }
/*     */   
/*     */   public String getURL(Map params) {
/* 139 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 140 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 144 */     return new ResultObject(0, getHtmlCode(params));
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 148 */     return this.title;
/*     */   }
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 152 */     return "res/si/styles/wd-style.css";
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/* 156 */     return (HtmlLayout)this.layout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/* 162 */     this.title = title;
/*     */   }
/*     */   
/*     */   protected abstract boolean checkInstance(HtmlElement paramHtmlElement);
/*     */   
/*     */   protected abstract void unregister(HtmlElement paramHtmlElement);
/*     */   
/*     */   public abstract void resetTitle();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\LinkDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */