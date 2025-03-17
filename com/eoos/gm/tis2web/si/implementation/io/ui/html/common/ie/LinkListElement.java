/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LinkListElement
/*     */   extends ListElement
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(LinkListElement.class);
/*     */   
/*     */   public static class SIOComparator implements Comparator {
/*     */     protected LocaleInfo locale;
/*     */     
/*     */     public SIOComparator(LocaleInfo locale) {
/*  41 */       this.locale = locale;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/*  45 */       SIO n1 = (SIO)o1;
/*  46 */       SIO n2 = (SIO)o2;
/*  47 */       return n1.getLabel(this.locale).compareTo(n2.getLabel(this.locale));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  52 */   protected List links = new LinkedList();
/*     */   
/*     */   protected HtmlElementStack stack;
/*     */   
/*     */   protected LinkDialog page;
/*     */ 
/*     */   
/*     */   public LinkListElement(ClientContext context, List sios, SIO base) {
/*  60 */     ILVCAdapter lvc = SIDataAdapterFacade.getInstance(context).getLVCAdapter();
/*  61 */     VCR vcr = VCR.NULL;
/*     */     try {
/*  63 */       vcr = lvc.toVCR(VCFacade.getInstance(context).getCfg());
/*  64 */     } catch (Exception e) {
/*  65 */       log.warn("unable to determine vcr, using null vcr - exception: " + e, e);
/*     */     } 
/*  67 */     base.getVCR();
/*  68 */     Set items = new HashSet(sios.size());
/*  69 */     Iterator<SIO> it = sios.iterator();
/*  70 */     while (it.hasNext()) {
/*  71 */       SIO sio = it.next();
/*  72 */       VCR sioVCR = sio.getVCR();
/*  73 */       if (sioVCR == null || sioVCR == VCR.NULL) {
/*  74 */         addLink(items, sio); continue;
/*     */       } 
/*  76 */       if (vcr != null && vcr != VCR.NULL && 
/*  77 */         !sioVCR.match(vcr)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  83 */       addLink(items, sio);
/*     */     } 
/*     */     
/*  86 */     if (this.links.size() > 1) {
/*  87 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*  88 */       Collections.sort(this.links, new SIOComparator(locale));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addLink(Set<Integer> items, SIO sio) {
/*  93 */     if (!items.contains(sio.getID())) {
/*  94 */       this.links.add(sio);
/*  95 */       items.add(sio.getID());
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void setDocument(Object paramObject);
/*     */   
/*     */   public List getData() {
/* 102 */     return this.links;
/*     */   }
/*     */   
/*     */   public void setPage(LinkDialog page) {
/* 106 */     this.page = page;
/*     */   }
/*     */   
/*     */   public void setStack(HtmlElementStack stack) {
/* 110 */     this.stack = stack;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\LinkListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */