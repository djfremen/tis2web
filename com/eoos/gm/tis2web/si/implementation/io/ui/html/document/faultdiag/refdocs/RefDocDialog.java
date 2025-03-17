/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input.RefDocElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.HtmlLayout;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class RefDocDialog
/*     */   extends RefDocDialogBase
/*     */ {
/*     */   protected ClientContext context;
/*     */   
/*     */   public static class RefDocDR
/*     */     implements DataRetrievalAbstraction.DataCallback
/*     */   {
/*     */     private ClientContext context;
/*     */     
/*     */     public RefDocDR(ClientContext context) {
/*  41 */       this.context = context;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List getData() {
/*  50 */       List<RefDocDialog.RefDocElementImpl> ret = new LinkedList();
/*  51 */       for (int i = 0; i < 20; i++) {
/*  52 */         ret.add(new RefDocDialog.RefDocElementImpl(Integer.toString(i), this.context));
/*     */       }
/*     */       
/*  55 */       return ret;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RefDocElementImpl
/*     */     implements RefDocElement
/*     */   {
/*     */     private String pre;
/*     */     
/*     */     private ClientContext context;
/*     */ 
/*     */     
/*     */     public RefDocElementImpl(String pre, ClientContext context) {
/*  70 */       this.pre = pre;
/*  71 */       this.context = context;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SIO getSIO() {
/*     */       SIOCPR sIOCPR;
/*  81 */       SIO ret = null;
/*  82 */       DocumentPage dp = DocumentPage.getInstance(this.context);
/*  83 */       if (dp != null) {
/*  84 */         SIO sio = dp.getSIO();
/*  85 */         if (sio != null && sio instanceof SIOCPR) {
/*  86 */           sIOCPR = (SIOCPR)sio;
/*     */         }
/*     */       } 
/*  89 */       return (SIO)sIOCPR;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getVehicleSystem() {
/*  98 */       return this.pre + "VehicleSystem";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getInformationType() {
/* 107 */       return this.pre + "nformationType";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDocument() {
/* 116 */       return this.pre + "Document";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   private HtmlElementStack stack = new HtmlElementStack();
/*     */ 
/*     */ 
/*     */   
/* 127 */   protected String title = "";
/*     */   
/* 129 */   private DialogLayout layout = new DialogLayout() {
/*     */       public String getHorizontalAlignment() {
/* 131 */         return "center";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getWidth() {
/* 136 */         return "100%";
/*     */       }
/*     */     };
/*     */   
/*     */   private ClickButtonElement buttonClose;
/*     */   
/*     */   public RefDocDialog(ClientContext context) {
/* 143 */     super(context);
/* 144 */     this.context = context;
/* 145 */     addElement((HtmlElement)this.stack);
/* 146 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 148 */           return RefDocDialog.this.context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 153 */             HtmlElement elem = RefDocDialog.this.stack.peek();
/* 154 */             boolean back = false;
/* 155 */             if (elem instanceof RefDocContainer) {
/* 156 */               RefDocIFrame frame = ((RefDocContainer)elem).getRefDocFrame();
/* 157 */               DocumentPage dp = frame.getDocumentPage();
/* 158 */               back = dp.onBack();
/*     */             } 
/* 160 */             if (!back) {
/* 161 */               RefDocDialog.this.unregister(elem);
/* 162 */               RefDocDialog.this.stack.pop();
/*     */             } 
/* 164 */             RefDocDialog.this.title = "";
/* 165 */           } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */           
/* 167 */           RefDocDialog dlg = RefDocDialog.this;
/* 168 */           MainPage mp = MainPage.getInstance(RefDocDialog.this.context);
/* 169 */           return (RefDocDialog.this.stack.size() > 0) ? dlg : mp;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isDispose() {
/* 174 */           return (RefDocDialog.this.stack.size() <= 1);
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/* 178 */           return (RefDocDialog.this.stack.size() > 1) ? null : "_self";
/*     */         }
/*     */       };
/* 181 */     addElement((HtmlElement)this.buttonClose);
/*     */   }
/*     */   
/*     */   public synchronized void setElement(HtmlElement element) {
/* 185 */     this.stack.push(element);
/*     */   }
/*     */   
/*     */   public synchronized void setElements(DataRetrievalAbstraction.DataCallback docs) {
/* 189 */     this.stack.clear();
/* 190 */     RefDocList rdl = new RefDocList(this.context, docs, this, this.stack);
/* 191 */     List<RefDocElement> data = docs.getData();
/* 192 */     if (data.size() == 1) {
/* 193 */       rdl.setDocument(data.get(0));
/*     */     } else {
/* 195 */       this.stack.push((HtmlElement)rdl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getContent(Map params) {
/* 204 */     StringBuffer code = new StringBuffer("<table width=\"{WIDTH}\"><tr><td >{DOCUMENT}</td></tr><tr><td align = \"center\">{BUTTON}</td></tr></table>");
/* 205 */     StringUtilities.replace(code, "{WIDTH}", this.layout.getWidth());
/*     */     
/* 207 */     StringUtilities.replace(code, "{BUTTON}", this.buttonClose.getHtmlCode(params));
/* 208 */     StringUtilities.replace(code, "{DOCUMENT}", this.stack.getHtmlCode(params));
/* 209 */     return code.toString();
/*     */   }
/*     */   
/*     */   public String getURL(Map params) {
/* 213 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 214 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 218 */     return new ResultObject(0, getHtmlCode(params));
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 222 */     return this.title;
/*     */   }
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 226 */     return isDocument() ? "res/si/styles/wd-style.css" : super.getStyleSheetURL();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isDocument() {
/* 231 */     boolean isDocument = false;
/*     */     try {
/* 233 */       HtmlElement elem = this.stack.peek();
/* 234 */       if (checkInstance(elem)) {
/* 235 */         isDocument = true;
/*     */       }
/* 237 */     } catch (com.eoos.html.element.HtmlElementStack.EmptyStackException e) {}
/*     */     
/* 239 */     return isDocument;
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/* 243 */     return (HtmlLayout)this.layout;
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/* 247 */     this.title = title;
/*     */   }
/*     */   
/*     */   protected void unregister(HtmlElement elem) {
/* 251 */     if (elem instanceof RefDocContainer) {
/* 252 */       ((RefDocContainer)elem).unregister();
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean checkInstance(HtmlElement elem) {
/* 257 */     return elem instanceof RefDocContainer;
/*     */   }
/*     */   
/*     */   public static RefDocDialog getInstance(ClientContext context, DataRetrievalAbstraction.DataCallback docs) {
/* 261 */     synchronized (context.getLockObject()) {
/* 262 */       RefDocDialog instance = (RefDocDialog)context.getObject(RefDocDialog.class);
/* 263 */       if (instance == null) {
/* 264 */         instance = new RefDocDialog(context);
/* 265 */         context.storeObject(RefDocDialog.class, instance);
/*     */       } 
/* 267 */       instance.setElements(docs);
/* 268 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static RefDocDialog getInstance(ClientContext context) {
/* 273 */     synchronized (context.getLockObject()) {
/* 274 */       RefDocDialog instance = (RefDocDialog)context.getObject(RefDocDialog.class);
/* 275 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getTableStyle() {
/* 280 */     return isDocument() ? super.getTableStyle() : "";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\RefDocDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */