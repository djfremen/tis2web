/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SITTextSearch;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.sitfilter.SITSelectBox;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class SITSelectBox
/*    */   extends SITSelectBox
/*    */ {
/*    */   private class ObserverCallback
/*    */     implements SITTextSearch.Observer
/*    */   {
/*    */     private ObserverCallback() {}
/*    */     
/*    */     public void onChange(CTOCNode selectedSIT) {
/* 24 */       SITSelectBox.this.setValue(selectedSIT);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SITSelectBox(ClientContext context) {
/* 31 */     super(context);
/* 32 */     SITTextSearch.getInstance(context).getObserverFacade().addObserver(new ObserverCallback());
/* 33 */     setValue(SITTextSearch.getInstance(context).getSelectedSIT());
/*    */   }
/*    */   
/*    */   protected String getTargetBookmark() {
/* 37 */     return "selectednode";
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 41 */     return "_top";
/*    */   }
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 49 */     SITTextSearch.getInstance(this.context).setSelectedSIT((CTOCNode)getValue());
/* 50 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 54 */     if (option == null) {
/* 55 */       return this.context.getLabel("no.selection");
/*    */     }
/* 57 */     return ((SITOCElement)option).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*    */   }
/*    */   
/*    */   protected String getSubmitValue(Object option) {
/* 61 */     if (option == null) {
/* 62 */       return "null";
/*    */     }
/* 64 */     return String.valueOf(getOptions().indexOf(option));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getOption(String submitValue) {
/* 69 */     if (submitValue.equals("null")) {
/* 70 */       return null;
/*    */     }
/*    */     try {
/* 73 */       int index = Integer.parseInt(submitValue);
/* 74 */       return getOptions().get(index);
/* 75 */     } catch (Exception e) {
/* 76 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 82 */     return super.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   protected void onVehicleChange(boolean salesmakeChange) {
/* 86 */     super.onVehicleChange(salesmakeChange);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\SITSelectBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */