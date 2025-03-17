/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SBT
/*     */ {
/*     */   public static final String NTF_TOC = "ntftoc=true";
/*     */   public static final int NTF_ID = 0;
/*     */   public static final int IB_ID = 1000000;
/*     */   private CTOCElementImpl ntf;
/*     */   private CTOCElementImpl ib;
/*  33 */   static int nodes = 0;
/*     */   
/*  35 */   static int labels = 0;
/*     */   
/*     */   public void buildRootSBT(CTOC ctoc, SBTCache cache, ILVCAdapter adapter) {
/*  38 */     CTOCNode ntfRoot = ctoc.getCTOC(CTOCDomain.NTF);
/*  39 */     if (ntfRoot == null) {
/*     */       try {
/*  41 */         ctoc.getCTOCStore().loadCTOC4NTF();
/*  42 */         ntfRoot = ctoc.getCTOC(CTOCDomain.NTF);
/*  43 */       } catch (Exception ex) {}
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (ntfRoot != null)
/*     */     {
/*  49 */       createNTF(ctoc, cache, (CTOCRootElement)cache.getRoot(), adapter);
/*     */     }
/*  51 */     createInspection(ctoc, cache, (CTOCRootElement)cache.getRoot(), adapter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void fixInspectionTree(CTOCElement ib) {
/*  57 */     Iterator<CTOCElement> it = ib.getChildren().iterator();
/*  58 */     while (it.hasNext()) {
/*  59 */       CTOCElement sitq = it.next();
/*  60 */       if (sitq.getChildren() == null) {
/*  61 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private CTOCElement createInspection(CTOC ctoc, SBTCache cache, CTOCRootElement sbt, ILVCAdapter adapter) {
/*  67 */     if (this.ib == null) {
/*  68 */       Integer labelIDInspection = Integer.valueOf(1000000);
/*  69 */       Iterator<LocaleInfo> it = LocaleInfoProvider.getInstance().getLocales().iterator();
/*  70 */       while (it.hasNext()) {
/*  71 */         LocaleInfo locale = it.next();
/*  72 */         String translation = null;
/*     */         try {
/*  74 */           translation = ApplicationContext.getInstance().getLabel(new Locale(locale.getLanguage(), locale.getCountry()), "sbt.inspection.brochures");
/*  75 */         } catch (Exception dgs) {}
/*     */         
/*  77 */         if (translation == null) {
/*  78 */           translation = "Inspection Brochures";
/*     */         }
/*  80 */         cache.registerLabel(labelIDInspection, locale, translation);
/*     */       } 
/*     */       
/*  83 */       labels++;
/*  84 */       this.ib = new CTOCElementImpl(1000000, labelIDInspection, 1, CTOCType.STRUCTURE.ord(), true, false, null, adapter);
/*  85 */       this.ib.add(CTOCProperty.SIT, "SIT-13");
/*  86 */       sbt.add((SITOCElement)this.ib);
/*  87 */       nodes++;
/*  88 */       buildInspectionTree(ctoc, cache, (CTOCElement)this.ib, adapter);
/*     */     } 
/*  90 */     return (CTOCElement)this.ib;
/*     */   }
/*     */   
/*     */   private void buildInspectionTree(CTOC ctoc, SBTCache cache, CTOCElement ib, ILVCAdapter adapter) {
/*  94 */     CTOCNode sits = ctoc.getCTOC(CTOCDomain.SIT);
/*  95 */     List<CTOCNode> children = sits.getChildren();
/*  96 */     for (int i = 0; i < children.size(); i++) {
/*  97 */       CTOCNode sit = children.get(i);
/*  98 */       String id = (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/*  99 */       if ("SIT-13".equals(id)) {
/* 100 */         List<CTOCNode> sitqs = sit.getChildren();
/* 101 */         for (int j = 0; j < sitqs.size(); j++) {
/* 102 */           buildInspectionTree(cache, ib, sitqs.get(j), adapter);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildInspectionTree(SBTCache cache, CTOCElement ib, CTOCNode it, ILVCAdapter adapter) {
/* 109 */     CTOCElementImpl node = new CTOCElementImpl(1000000 + it.getID().intValue(), it.getID(), it.getOrder(), CTOCType.STRUCTURE.ord(), false, true, null, adapter);
/* 110 */     node.add(CTOCProperty.SITQ, it);
/* 111 */     ib.add((SITOCElement)node);
/*     */   }
/*     */   
/*     */   private void createNTF(CTOC ctoc, SBTCache cache, CTOCRootElement sbt, ILVCAdapter adapter) {
/* 115 */     CTOCElement rootNTF = findNTF(cache, sbt, adapter);
/* 116 */     CTOCNode ntfs = ctoc.getCTOC(CTOCDomain.NTF);
/* 117 */     List children = null;
/* 118 */     if (ntfs != null) {
/*     */       
/* 120 */       children = ctoc.getCTOCStore().getChildren(ntfs.getID());
/* 121 */       for (Iterator<SITOCElement> it = children.iterator(); it.hasNext();) {
/* 122 */         rootNTF.add(it.next());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CTOCElement findNTF(SBTCache cache, CTOCRootElement sbt, ILVCAdapter adapter) {
/* 129 */     if (this.ntf == null) {
/* 130 */       Integer labelIDNTF = Integer.valueOf(0);
/* 131 */       Iterator<LocaleInfo> it = LocaleInfoProvider.getInstance().getLocales().iterator();
/* 132 */       while (it.hasNext()) {
/* 133 */         LocaleInfo locale = it.next();
/* 134 */         String translation = null;
/*     */         try {
/* 136 */           translation = ApplicationContext.getInstance().getLabel(new Locale(locale.getLanguage(), locale.getCountry()), "sbt.new.technical.features");
/* 137 */         } catch (Exception dgs) {}
/*     */         
/* 139 */         if (translation == null) {
/* 140 */           translation = "New Technical Features";
/*     */         }
/* 142 */         cache.registerLabel(labelIDNTF, locale, translation);
/*     */       } 
/* 144 */       labels++;
/* 145 */       this.ntf = new CTOCElementImpl(0, labelIDNTF, 0, CTOCType.STRUCTURE.ord(), true, false, null, adapter);
/* 146 */       sbt.add((SITOCElement)this.ntf);
/* 147 */       nodes++;
/*     */     } 
/* 149 */     return (CTOCElement)this.ntf;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\SBT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */