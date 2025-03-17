/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SIT;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.sitfilter.SITSelectBox;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SITSelectBox
/*     */   extends SITSelectBox
/*     */ {
/*     */   private TocTree tree;
/*     */   
/*     */   private class ObserverCallback
/*     */     implements SIT.Observer
/*     */   {
/*     */     private ObserverCallback() {}
/*     */     
/*     */     public void onChange(CTOCNode selectedSIT) {
/*  30 */       SITSelectBox.this.setValue(selectedSIT);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SITSelectBox(ClientContext context) {
/*  38 */     super(context);
/*  39 */     this.tree = TocTree.getInstance(context);
/*  40 */     SIT.getInstance(context).getObserverFacade().addObserver(new ObserverCallback());
/*  41 */     setValue(SIT.getInstance(context).getSelectedSIT());
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/*  45 */     return "selectednode";
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  49 */     return "_top";
/*     */   }
/*     */   
/*     */   private void updateTreeFilter() {
/*  53 */     List sits = null;
/*  54 */     if (getValue() == null) {
/*  55 */       sits = new ArrayList(getOptions());
/*  56 */       sits.remove((Object)null);
/*     */     } else {
/*  58 */       sits = Arrays.asList(new Object[] { getValue() });
/*     */     } 
/*  60 */     this.tree.setSITFilter(SIT.convertToStringList(sits));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean autoSubmitOnChange() {
/*  65 */     return true;
/*     */   }
/*     */   
/*     */   protected Object onChange(Map submitParams) {
/*  69 */     updateTreeFilter();
/*  70 */     SIT.getInstance(this.context).setSelectedSIT((CTOCNode)getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     return MainPage.getInstance(this.context);
/*     */   }
/*     */   
/*     */   protected String getDisplayValue(Object option) {
/*  81 */     if (option == null) {
/*  82 */       return this.context.getLabel("no.selection");
/*     */     }
/*  84 */     return ((SITOCElement)option).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */   }
/*     */   
/*     */   protected String getSubmitValue(Object option) {
/*  88 */     if (option == null) {
/*  89 */       return "null";
/*     */     }
/*  91 */     return String.valueOf(getOptions().indexOf(option));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getOption(String submitValue) {
/*  96 */     if (submitValue.equals("null")) {
/*  97 */       return null;
/*     */     }
/*     */     try {
/* 100 */       int index = Integer.parseInt(submitValue);
/* 101 */       return getOptions().get(index);
/* 102 */     } catch (Exception e) {
/* 103 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 109 */     return super.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   protected void onVehicleChange(boolean salesmakeChange) {
/* 113 */     super.onVehicleChange(salesmakeChange);
/* 114 */     updateTreeFilter();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\SITSelectBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */