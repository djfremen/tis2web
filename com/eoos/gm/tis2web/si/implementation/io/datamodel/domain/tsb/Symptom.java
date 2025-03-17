/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Symptom
/*    */ {
/* 31 */   protected static List domain = null;
/*    */   
/* 33 */   protected static Map nodeToInstance = new HashMap<Object, Object>();
/*    */   
/*    */   protected CTOCNode node;
/*    */   
/*    */   protected Symptom(CTOCNode node) {
/* 38 */     this.node = node;
/*    */   }
/*    */   
/*    */   public static synchronized Symptom getInstance(CTOCNode node) {
/* 42 */     Symptom instance = (Symptom)nodeToInstance.get(node);
/* 43 */     if (instance == null) {
/* 44 */       instance = new Symptom(node);
/* 45 */       nodeToInstance.put(node, instance);
/*    */     } 
/* 47 */     return instance;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 51 */     return this.node.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     if (this == obj)
/* 57 */       return true; 
/* 58 */     if (obj instanceof Symptom) {
/* 59 */       Symptom other = (Symptom)obj;
/* 60 */       boolean ret = Util.equals(this.node, other.node);
/* 61 */       return ret;
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIdentifier(LocaleInfo li) {
/* 68 */     return this.node.getLabel(li);
/*    */   }
/*    */   
/*    */   public String getIdentifier(Locale locale) {
/* 72 */     return getIdentifier(LocaleInfoProvider.getInstance().getLocale(locale));
/*    */   }
/*    */   
/*    */   public CTOCNode getNode() {
/* 76 */     return this.node;
/*    */   }
/*    */   
/*    */   public static synchronized List getDomain(ClientContext context) {
/* 80 */     if (domain == null) {
/* 81 */       List<Symptom> _domain = new LinkedList();
/* 82 */       SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/*    */       
/* 84 */       CTOCNode node = siCTOCService.getCTOC().getCTOC(CTOCDomain.COMPLAINT);
/* 85 */       if (node != null && node.getChildren() != null) {
/* 86 */         Iterator<CTOCNode> iter = node.getChildren().iterator();
/* 87 */         while (iter.hasNext()) {
/* 88 */           _domain.add(getInstance(iter.next()));
/*    */         }
/*    */       } 
/* 91 */       domain = Collections.unmodifiableList(_domain);
/*    */     } 
/* 93 */     return domain;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\Symptom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */