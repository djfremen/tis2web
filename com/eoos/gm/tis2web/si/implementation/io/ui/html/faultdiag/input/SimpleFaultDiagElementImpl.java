/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import java.text.Collator;
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
/*     */ 
/*     */ public class SimpleFaultDiagElementImpl
/*     */   extends FaultDiagElementImpl
/*     */   implements SimpleFaultDiagElement
/*     */ {
/*     */   private String serviceCategorie;
/*     */   
/*     */   public String getServiceCategorie() {
/*  34 */     return this.serviceCategorie;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFaultDiagElementImpl(SITOCElement x, final ClientContext context, SITOCElement root, Collator col) {
/*  41 */     super(x, col, context);
/*     */     
/*  43 */     (new TocPropParser(x, (SITOCProperty)SIOProperty.WIS, (SITOCProperty)CTOCProperty.AssemblyGroup) {
/*     */         protected void addPropElement(SITOCElement y) {
/*  45 */           TocParser p = new TocParser() {
/*     */               public void addElement(SITOCElement z) {
/*  47 */                 Object prop = z.getProperty(SimpleFaultDiagElementImpl.null.this.second);
/*  48 */                 if (prop != null && SimpleFaultDiagElementImpl.null.this.propVal.indexOf("sc=" + prop.toString()) >= 0) {
/*  49 */                   SimpleFaultDiagElementImpl.this.serviceCategorie = z.getLabel(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  50 */                   this.go = false;
/*     */                 } 
/*     */               }
/*     */             };
/*  54 */           p.parseChildren(y);
/*  55 */           this.go = p.isGo();
/*     */         }
/*     */       }).parse(root, "sct=");
/*     */ 
/*     */ 
/*     */     
/*  61 */     if (this.serviceCategorie == null) {
/*  62 */       this.serviceCategorie = "";
/*     */     }
/*  64 */     this.symptom = x.getLabel(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  65 */     if (this.symptom == null) {
/*  66 */       this.symptom = "";
/*     */     }
/*     */     
/*  69 */     this.serviceCategorie = this.serviceCategorie.trim();
/*  70 */     this.symptom = this.symptom.trim();
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
/*     */ 
/*     */   
/*     */   public int compareTo(Object o) {
/*  91 */     int ret = 0;
/*  92 */     if (o instanceof SimpleFaultDiagElement) {
/*  93 */       SimpleFaultDiagElement bEl = (SimpleFaultDiagElement)o;
/*  94 */       ret = this.col.compare(this.serviceCategorie, bEl.getServiceCategorie());
/*  95 */       if (ret == 0)
/*     */       {
/*  97 */         ret = super.compareTo(o);
/*     */       }
/*     */     } else {
/*     */       
/* 101 */       ret = super.compareTo(o);
/*     */     } 
/* 103 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\SimpleFaultDiagElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */