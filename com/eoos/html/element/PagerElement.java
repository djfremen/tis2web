/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.Pager;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PagerElement extends HtmlElementContainerBase {
/*     */   private static final String template = "<table id=\"pagertable\" align=\"center\"><tr><td>{BEGIN}</td><td>{FB}</td><td>{PAGERLINKS}</td><td>{FF}</td><td>{END}</td></tr></table>";
/*     */   protected Pager pager;
/*     */   protected String parameterName;
/*     */   protected int pageLinkCount;
/*     */   protected HtmlElementContainerBase dynamicContainer;
/*     */   protected HtmlElement linkBegin;
/*     */   protected HtmlElement linkFastBack;
/*     */   protected HtmlElement linkEnd;
/*     */   protected HtmlElement linkFastForward;
/*     */   
/*     */   protected abstract class CustomLinkElement extends LinkElement {
/*     */     private String label;
/*     */     
/*     */     public CustomLinkElement(String parameterName, String label) {
/*  25 */       super(parameterName, null);
/*  26 */       this.label = label;
/*     */     }
/*     */     
/*     */     protected String getTargetFrame() {
/*  30 */       return PagerElement.this.getTargetFrame();
/*     */     }
/*     */     
/*     */     protected String getTargetBookmark() {
/*  34 */       return PagerElement.this.getTargetBookmark();
/*     */     }
/*     */     
/*     */     public abstract Object onClick(Map param1Map);
/*     */     
/*     */     protected String getLabel() {
/*  40 */       return this.label;
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PagerElement(String parameterName, Pager pager, int pageLinkCount) {
/*  60 */     this.parameterName = parameterName;
/*  61 */     this.pager = pager;
/*  62 */     this.pageLinkCount = pageLinkCount;
/*     */     
/*  64 */     this.dynamicContainer = new HtmlElementContainerBase() {
/*     */         public String getHtmlCode(Map params) {
/*  66 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*  69 */     addElement((HtmlElement)this.dynamicContainer);
/*     */     
/*  71 */     this.linkBegin = (HtmlElement)new CustomLinkElement(this.parameterName + "B", "<span style=\"white-space: nowrap;\">|&lt;</span>") {
/*     */         public Object onClick(Map params) {
/*  73 */           PagerElement.this.pager.gotoPage(0);
/*     */           
/*  75 */           HtmlElementContainer container = getContainer();
/*  76 */           while (container.getContainer() != null) {
/*  77 */             container = container.getContainer();
/*     */           }
/*  79 */           return container;
/*     */         }
/*     */       };
/*  82 */     addElement(this.linkBegin);
/*     */     
/*  84 */     this.linkFastBack = (HtmlElement)new CustomLinkElement(this.parameterName + "FB", "<span style=\"white-space: nowrap;\">&lt;&lt;</span>") {
/*     */         public Object onClick(Map params) {
/*  86 */           Pager pager = PagerElement.this.pager;
/*  87 */           pager.gotoPage(Math.max(pager.getCurrentPageIndex() - PagerElement.this.pageLinkCount, 0));
/*     */           
/*  89 */           HtmlElementContainer container = getContainer();
/*  90 */           while (container.getContainer() != null) {
/*  91 */             container = container.getContainer();
/*     */           }
/*  93 */           return container;
/*     */         }
/*     */       };
/*  96 */     addElement(this.linkFastBack);
/*     */     
/*  98 */     this.linkFastForward = (HtmlElement)new CustomLinkElement(this.parameterName + "FF", "<span style=\"white-space: nowrap;\">&gt;&gt;</span>") {
/*     */         public Object onClick(Map params) {
/* 100 */           Pager pager = PagerElement.this.pager;
/* 101 */           pager.gotoPage(Math.min(pager.getCurrentPageIndex() + PagerElement.this.pageLinkCount, pager.getPageCount() - 1));
/*     */           
/* 103 */           HtmlElementContainer container = getContainer();
/* 104 */           while (container.getContainer() != null) {
/* 105 */             container = container.getContainer();
/*     */           }
/* 107 */           return container;
/*     */         }
/*     */       };
/* 110 */     addElement(this.linkFastForward);
/*     */     
/* 112 */     this.linkEnd = (HtmlElement)new CustomLinkElement(this.parameterName + "E", "<span style=\"white-space: nowrap;\">&gt;|</span>") {
/*     */         public Object onClick(Map params) {
/* 114 */           Pager pager = PagerElement.this.pager;
/* 115 */           pager.gotoPage(pager.getPageCount() - 1);
/*     */           
/* 117 */           HtmlElementContainer container = getContainer();
/* 118 */           while (container.getContainer() != null) {
/* 119 */             container = container.getContainer();
/*     */           }
/* 121 */           return container;
/*     */         }
/*     */       };
/* 124 */     addElement(this.linkEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 129 */     String retValue = null;
/*     */     
/* 131 */     this.dynamicContainer.removeAllElements();
/*     */     
/* 133 */     int pageCount = this.pager.getPageCount();
/*     */     
/* 135 */     if (pageCount > 1) {
/* 136 */       StringBuffer code = StringBufferPool.getThreadInstance().get("<table id=\"pagertable\" align=\"center\"><tr><td>{BEGIN}</td><td>{FB}</td><td>{PAGERLINKS}</td><td>{FF}</td><td>{END}</td></tr></table>");
/*     */       
/*     */       try {
/* 139 */         StringUtilities.replace(code, "{BEGIN}", this.linkBegin.getHtmlCode(params));
/* 140 */         StringUtilities.replace(code, "{FB}", this.linkFastBack.getHtmlCode(params));
/* 141 */         StringUtilities.replace(code, "{FF}", this.linkFastForward.getHtmlCode(params));
/* 142 */         StringUtilities.replace(code, "{END}", this.linkEnd.getHtmlCode(params));
/*     */         
/* 144 */         int firstPageIndex = Math.max(0, this.pager.getCurrentPageIndex() - this.pageLinkCount / 2);
/* 145 */         int lastPageIndex = Math.min(this.pager.getPageCount() - 1, firstPageIndex + this.pageLinkCount);
/*     */         
/* 147 */         for (int i = firstPageIndex; i <= lastPageIndex; i++) {
/* 148 */           final int index = i;
/* 149 */           final CustomLinkElement link = new CustomLinkElement(this.parameterName + i, "[" + i + "]") {
/*     */               public Object onClick(Map params) {
/* 151 */                 PagerElement.this.pager.gotoPage(index);
/*     */                 
/* 153 */                 HtmlElementContainer container = getContainer();
/* 154 */                 while (container.getContainer() != null) {
/* 155 */                   container = container.getContainer();
/*     */                 }
/* 157 */                 return container;
/*     */               }
/*     */             };
/* 160 */           this.dynamicContainer.addElement((HtmlElement)link);
/*     */           
/* 162 */           String linkCode = null;
/*     */           
/* 164 */           if (i == this.pager.getCurrentPageIndex()) {
/* 165 */             linkCode = HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*     */                   public String getContent() {
/* 167 */                     return link.getHtmlCode(null);
/*     */                   }
/*     */                   
/*     */                   protected String getID() {
/* 171 */                     return "selected";
/*     */                   }
/*     */                 });
/*     */           } else {
/* 175 */             linkCode = link.getHtmlCode(null);
/*     */           } 
/* 177 */           StringUtilities.replace(code, "{PAGERLINKS}", linkCode + "</td><td>{PAGERLINKS}");
/*     */         } 
/* 179 */         StringUtilities.replace(code, "<td>{PAGERLINKS}</td>", "");
/*     */         
/* 181 */         retValue = code.toString();
/*     */       } finally {
/*     */         
/* 184 */         StringBufferPool.getThreadInstance().free(code);
/*     */       } 
/*     */     } else {
/* 187 */       retValue = "";
/*     */     } 
/* 189 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getTargetFrame() {
/* 194 */     return null;
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/* 198 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/* 202 */     return this.parameterName;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\PagerElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */