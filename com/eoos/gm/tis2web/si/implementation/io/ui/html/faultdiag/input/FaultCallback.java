/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import java.text.Collator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ public class FaultCallback
/*     */   extends TocParser
/*     */   implements DataRetrievalAbstraction.DataCallback, FilterCallback
/*     */ {
/*  36 */   protected Map faultToDoc = new TreeMap<Object, Object>();
/*     */   
/*     */   protected List keySet;
/*     */   
/*     */   protected ClientContext context;
/*     */   protected SITOCElement root;
/*     */   protected Collator col;
/*  43 */   protected static Logger log = Logger.getLogger(FaultCallback.class);
/*     */ 
/*     */   
/*     */   public FaultCallback(SITOCElement root, SITOCProperty first, ClientContext context) {
/*  47 */     this.context = context;
/*  48 */     this.col = Collator.getInstance(context.getLocale());
/*     */     
/*  50 */     this.root = root;
/*  51 */     loadSubjects(root.getProperty(first));
/*  52 */     parse(root, first);
/*  53 */     this.keySet = new ArrayList(this.faultToDoc.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadSubjects(Object ctoc) {}
/*     */ 
/*     */   
/*     */   public FaultCallback(SITOCElement root, ClientContext context) {
/*  62 */     this.context = context;
/*  63 */     this.col = Collator.getInstance(context.getLocale());
/*  64 */     parseChildren(root);
/*  65 */     this.keySet = new ArrayList(this.faultToDoc.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getData() {
/*  76 */     return this.keySet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FaultCallback(SITOCElement root) {
/*  84 */     parseChildren(root);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addElement(SITOCElement x) {
/*  95 */     String dtc = (String)x.getProperty((SITOCProperty)SIOProperty.WIS_DTC);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (dtc != null) {
/* 101 */       StringTokenizer st = new StringTokenizer(dtc, ",");
/* 102 */       while (st.hasMoreTokens()) {
/* 103 */         String next = st.nextToken();
/*     */         
/* 105 */         next = next.trim();
/* 106 */         next.charAt(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 112 */         Set<FaultDiagElement> docs = (Set)this.faultToDoc.get(next);
/* 113 */         if (docs == null) {
/* 114 */           docs = new TreeSet();
/*     */         }
/* 116 */         docs.add(createElement(x, this.context));
/* 117 */         this.faultToDoc.put(next, docs);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FaultDiagElement createElement(SITOCElement x, ClientContext context) {
/* 128 */     return new SimpleFaultDiagElementImpl(x, context, this.root, this.col);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getData(Object filter) {
/* 138 */     Set<?> ret = (Set)this.faultToDoc.get(filter);
/* 139 */     if (ret == null) {
/* 140 */       ret = new HashSet();
/*     */     }
/* 142 */     return new ArrayList(ret);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getData(Object filter, Comparator<?> comp) {
/* 147 */     List<?> ret = (List)this.faultToDoc.get(filter);
/* 148 */     if (ret == null) {
/* 149 */       ret = new LinkedList();
/*     */     }
/* 151 */     Collections.sort(ret, comp);
/* 152 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\FaultCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */