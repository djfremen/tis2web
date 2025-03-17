/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkDialog;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkListElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WiringDiagrammList
/*     */   extends LinkListElement
/*     */ {
/*     */   public static class WDComparator
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2) {
/*  38 */       SIO n1 = (SIO)o1;
/*  39 */       SIO n2 = (SIO)o2;
/*  40 */       return n1.getOrder() - n2.getOrder();
/*     */     }
/*     */   }
/*     */   
/*  44 */   static WDComparator wdComparator = new WDComparator();
/*     */   
/*     */   public static class SITQComparator implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/*  48 */       CTOCNode n1 = (CTOCNode)o1;
/*  49 */       CTOCNode n2 = (CTOCNode)o2;
/*  50 */       return n1.getOrder() - n2.getOrder();
/*     */     }
/*     */   }
/*     */   
/*  54 */   static SITQComparator sitqComparator = new SITQComparator();
/*     */   
/*     */   public static CTOCNode getSITQ(SIO sio) {
/*  57 */     if (sio.getProperty((SITOCProperty)SIOProperty.SIT) != null) {
/*  58 */       List<CTOCNode> sits = (List)sio.getProperty((SITOCProperty)SIOProperty.SIT);
/*  59 */       for (int i = 0; i < sits.size(); i++) {
/*  60 */         CTOCNode node = sits.get(i);
/*  61 */         if (node.getProperty((SITOCProperty)CTOCProperty.SITQ) != null) {
/*  62 */           return node;
/*     */         }
/*     */       } 
/*     */     } 
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private HtmlLabel header;
/*     */   
/*     */   private LocaleInfo locale;
/*     */ 
/*     */   
/*     */   public WiringDiagrammList(ClientContext context, SIOCPR sioCpr) {
/*  80 */     super(context, (sioCpr.getWiringDiagrams() != null) ? sioCpr.getWiringDiagrams().getChildren() : new LinkedList(), (SIO)sioCpr);
/*  81 */     setDataCallback((DataRetrievalAbstraction.DataCallback)this);
/*  82 */     this.context = context;
/*     */     
/*  84 */     this.header = new HtmlLabel(context.getLabel("cpr.CircuitDiagramm"));
/*  85 */     this.locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*     */     
/*  87 */     List<SIO> sios = getData();
/*  88 */     if (sios.size() > 1) {
/*  89 */       List<CTOCNode> sitqs = new LinkedList();
/*  90 */       Map<Object, Object> wds = new HashMap<Object, Object>();
/*  91 */       for (int i = 0; i < sios.size(); i++) {
/*     */         
/*  93 */         SIO sio = sios.get(i);
/*  94 */         CTOCNode sitq = getSITQ(sio);
/*  95 */         if (sitq != null) {
/*  96 */           if (!sitqs.contains(sitq)) {
/*  97 */             sitqs.add(sitq);
/*     */           }
/*  99 */           List<SIO> bucket = (List)wds.get(sitq);
/* 100 */           if (bucket == null) {
/* 101 */             bucket = new LinkedList();
/* 102 */             wds.put(sitq, bucket);
/*     */           } 
/* 104 */           bucket.add(sio);
/*     */         } 
/*     */       } 
/* 107 */       this.links = new LinkedList();
/* 108 */       Collections.sort(sitqs, sitqComparator);
/* 109 */       for (int n = 0; n < sitqs.size(); n++) {
/* 110 */         CTOCNode sitq = sitqs.get(n);
/* 111 */         List<?> bucket = (List)wds.get(sitq);
/* 112 */         if (bucket != null) {
/*     */ 
/*     */           
/* 115 */           this.links.add(sitq);
/* 116 */           Collections.sort(bucket, wdComparator);
/* 117 */           for (int m = 0; m < bucket.size(); m++) {
/* 118 */             SIO sio = (SIO)bucket.get(m);
/* 119 */             this.links.add(sio);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected int getColumnCount() {
/* 126 */     return 1;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 130 */     if (data instanceof CTOCNode) {
/* 131 */       final CTOCNode node = (CTOCNode)data;
/* 132 */       return (HtmlElement)new LinkElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 135 */             StringBuffer code = new StringBuffer();
/* 136 */             code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */                     public String getImageSource() {
/* 138 */                       String image = "common/sit-open.gif";
/* 139 */                       return "pic/" + image;
/*     */                     }
/*     */                     
/*     */                     public void getAdditionalAttributes(Map<String, String> map) {
/* 143 */                       map.put("border", "0");
/* 144 */                       map.put("HSPACE", "10");
/*     */                     }
/*     */                   }));
/*     */             
/* 148 */             return code.toString() + node.getLabel(WiringDiagrammList.this.locale);
/*     */           }
/*     */           
/*     */           public String getHtmlCode(Map params) {
/* 152 */             StringBuffer code = new StringBuffer();
/* 153 */             code.append(this.renderingCallback.getLabel());
/* 154 */             return code.toString();
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 158 */             return null;
/*     */           }
/*     */         };
/*     */     } 
/* 162 */     final SIO node = (SIO)data;
/* 163 */     return (HtmlElement)new LinkElement(this.context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 166 */           StringBuffer code = new StringBuffer();
/* 167 */           code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */                   public String getImageSource() {
/* 169 */                     String image = "common/leaf-icon.gif";
/* 170 */                     return "pic/" + image;
/*     */                   }
/*     */                   
/*     */                   public void getAdditionalAttributes(Map<String, String> map) {
/* 174 */                     map.put("border", "0");
/* 175 */                     map.put("HSPACE", "10");
/*     */                   }
/*     */                 }));
/*     */           
/* 179 */           return "<p style=\"text-indent:1em;margin-bottom:0pt;margin-top:0pt\">" + code.toString() + node.getLabel(WiringDiagrammList.this.locale) + "</p>";
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 183 */           WiringDiagrammList.this.setDocument(node);
/* 184 */           return WiringDiagrammList.this.page;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 191 */     return (HtmlElement)this.header;
/*     */   }
/*     */   
/*     */   protected boolean enableHeader() {
/* 195 */     return false;
/*     */   }
/*     */   
/*     */   public void setDocument(Object obj) {
/* 199 */     if (obj instanceof SIO) {
/* 200 */       setDocument((SIO)obj);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDocument(SIO node) {
/* 205 */     this.stack.push((HtmlElement)new WiringDiagrammContainer(this.context, node, this.stack, (WiringDiagrammPage)this.page));
/* 206 */     this.page.setTitle(node.getLabel(this.locale));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\WiringDiagrammList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */