/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures;
/*     */ 
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI2;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ContentTreeNavigationSPIImpl
/*     */   implements TreeNavigationSPI2
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(ContentTreeNavigationSPIImpl.class);
/*     */   
/*     */   private Node virtualroot;
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   protected LocaleInfo li;
/*     */   
/*     */   protected String country;
/*     */ 
/*     */   
/*     */   public ContentTreeNavigationSPIImpl(ClientContext context) {
/*  34 */     this.context = context;
/*  35 */     SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/*     */     
/*  37 */     this.virtualroot = new Node((SITOCElement)siCTOCService.getCTOC().getCTOC(CTOCDomain.SPECIAL_BROCHURE));
/*  38 */     this.li = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*  39 */     this.country = SharedContextProxy.getInstance(context).getCountry();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getPath(Object _node) {
/*  44 */     List<Node> retValue = new LinkedList();
/*     */     try {
/*  46 */       Node node = (Node)_node;
/*  47 */       while (node.parent != null) {
/*  48 */         node = node.parent;
/*  49 */         retValue.add(node);
/*     */       } 
/*  51 */       Collections.reverse(retValue);
/*     */     }
/*  53 */     catch (Exception e) {
/*  54 */       log.error("error while determine path -error:" + e, e);
/*     */     } 
/*  56 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getChildren(Object _node) {
/*  60 */     List<Node> retValue = new LinkedList();
/*  61 */     Node node = (Node)_node;
/*     */     
/*  63 */     if (node.content == null) {
/*  64 */       return retValue;
/*     */     }
/*     */     
/*  67 */     if (node != null && node.content != null && !node.content.isSIO()) {
/*     */       
/*     */       try {
/*  70 */         List children = null;
/*  71 */         if (SpecialBrochuresContext.isInspectionNode(node)) {
/*  72 */           if (SpecialBrochuresContext.getInstance(this.context).inspectionMandatorySet()) {
/*  73 */             VCR vcr = SpecialBrochuresContext.getInstance(this.context).getVCR();
/*  74 */             children = node.content.getChildren(null, this.li, this.country, vcr);
/*     */           } 
/*     */         } else {
/*     */           
/*  78 */           children = node.content.getChildren();
/*     */         } 
/*     */         
/*  81 */         if (children != null) {
/*  82 */           Iterator<SITOCElement> iter = children.iterator();
/*  83 */           while (iter.hasNext()) {
/*  84 */             SITOCElement entry = iter.next();
/*  85 */             if (entry.getLabel(this.li) == null) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  93 */             Node child = new Node(entry);
/*  94 */             child.parent = node;
/*  95 */             retValue.add(child);
/*     */           } 
/*     */         } 
/*  98 */       } catch (Exception e) {
/*  99 */         log.error("unable to retrieve childs - error:" + e, e);
/*     */       } 
/*     */     }
/* 102 */     return retValue;
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 106 */     return ((Node)node).parent;
/*     */   }
/*     */   
/*     */   public Object getSuperroot() {
/* 110 */     return this.virtualroot;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\specialbrochures\ContentTreeNavigationSPIImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */