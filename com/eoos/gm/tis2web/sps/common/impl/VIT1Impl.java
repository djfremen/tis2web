/*     */ package com.eoos.gm.tis2web.sps.common.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class VIT1Impl
/*     */   extends VITImpl
/*     */   implements VIT1, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   private List cmbs = new ArrayList();
/*  17 */   private VIT freeOptions = new VITImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VIT getFreeOptions() {
/*  24 */     return this.freeOptions;
/*     */   }
/*     */   
/*     */   public VIT getControlModuleBlock(Integer ecuadr) {
/*  28 */     for (int i = 0; i < this.cmbs.size(); i++) {
/*  29 */       int currEcuadr = 0;
/*     */       try {
/*  31 */         currEcuadr = Integer.parseInt(((VIT)this.cmbs.get(i)).getAttrValue("ecu_adr"), 16);
/*  32 */       } catch (Exception e) {}
/*     */ 
/*     */       
/*  35 */       if (currEcuadr == ecuadr.intValue()) {
/*  36 */         VIT cmb = this.cmbs.get(i);
/*  37 */         return cmb;
/*     */       } 
/*     */     } 
/*     */     
/*  41 */     return null;
/*     */   }
/*     */   
/*     */   public VIT getHeader() {
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public List collectAllAttributes() {
/*  49 */     List lstAttrs = new ArrayList();
/*     */     
/*  51 */     lstAttrs.addAll(this.vitAttrs);
/*  52 */     for (int i = 0; i < this.cmbs.size(); i++) {
/*  53 */       lstAttrs.addAll(((VITImpl)this.cmbs.get(i)).getAttributes());
/*     */     }
/*  55 */     lstAttrs.addAll(this.freeOptions.getAttributes());
/*     */     
/*  57 */     return lstAttrs;
/*     */   }
/*     */   
/*     */   public List getControlModuleBlocks() {
/*  61 */     return this.cmbs;
/*     */   }
/*     */   
/*     */   public void addControlModuleBlock(VIT cmb) {
/*  65 */     this.cmbs.add(cmb);
/*     */   }
/*     */   
/*     */   public void removeControlModuleBlock(Integer ecuadr) {
/*  69 */     Iterator<VIT> it = this.cmbs.iterator();
/*  70 */     while (it.hasNext()) {
/*  71 */       VIT cmb = it.next();
/*     */       try {
/*  73 */         int ecu = Integer.parseInt(cmb.getAttrValue("ecu_adr"), 16);
/*  74 */         if (ecu == ecuadr.intValue()) {
/*  75 */           it.remove();
/*  76 */           Pair numcms = getAttribute("numcms");
/*  77 */           if (numcms != null) {
/*  78 */             PairImpl pairImpl = new PairImpl("numcms", (new Integer(this.cmbs.size())).toString());
/*  79 */             setAttribute((Pair)pairImpl);
/*     */           } 
/*     */           break;
/*     */         } 
/*  83 */       } catch (Exception e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List collectAttributes(Integer ecuadr) {
/*  90 */     List lstAttrs = new ArrayList();
/*  91 */     VIT cmb = null;
/*     */     
/*  93 */     lstAttrs.addAll(this.vitAttrs);
/*  94 */     for (int i = 0; i < this.cmbs.size(); i++) {
/*  95 */       int currEcuadr = 0;
/*     */       try {
/*  97 */         currEcuadr = Integer.parseInt(((VIT)this.cmbs.get(i)).getAttrValue("ecu_adr"), 16);
/*  98 */       } catch (Exception e) {}
/*     */ 
/*     */       
/* 101 */       if (currEcuadr == ecuadr.intValue()) {
/* 102 */         cmb = this.cmbs.get(i);
/*     */         break;
/*     */       } 
/*     */     } 
/* 106 */     if (cmb != null) {
/* 107 */       lstAttrs.addAll(cmb.getAttributes());
/*     */     }
/* 109 */     lstAttrs.addAll(this.freeOptions.getAttributes());
/*     */     
/* 111 */     return lstAttrs;
/*     */   }
/*     */   
/*     */   public Object getVIN() {
/* 115 */     String resVIN = null;
/*     */     
/* 117 */     for (int i = 0; i < this.cmbs.size(); i++) {
/* 118 */       String vin = ((VIT)this.cmbs.get(i)).getAttrValue("vin");
/* 119 */       if (vin == null || vin.length() != 0)
/*     */       {
/*     */         
/* 122 */         if (resVIN == null) {
/* 123 */           resVIN = vin;
/*     */         
/*     */         }
/* 126 */         else if (vin != null && vin.length() > 0 && 
/* 127 */           !resVIN.equals(vin)) {
/* 128 */           resVIN = null;
/*     */           break;
/*     */         } 
/*     */       }
/*     */     } 
/* 133 */     return resVIN;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VIT1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */