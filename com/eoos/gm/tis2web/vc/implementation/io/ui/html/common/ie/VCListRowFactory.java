/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.combobox.VehicleOptionsComboBox;
/*    */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.label.VcHtmlLabel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VCListRowFactory
/*    */ {
/* 17 */   private static final MandatoryCallback MC_NOT_MANDATORY = new MandatoryCallback()
/*    */     {
/*    */       public boolean isMandatory() {
/* 20 */         return false;
/*    */       }
/*    */     };
/*    */   
/* 24 */   protected ClientContext context = null;
/*    */   
/*    */   private VCListRowFactory(ClientContext context) {
/* 27 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static VCListRowFactory getInstance(ClientContext context) {
/* 31 */     synchronized (context.getLockObject()) {
/* 32 */       VCListRowFactory instance = (VCListRowFactory)context.getObject(VCListRowFactory.class);
/* 33 */       if (instance == null) {
/* 34 */         instance = new VCListRowFactory(context);
/* 35 */         context.storeObject(VCListRowFactory.class, instance);
/*    */       } 
/* 37 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public VCListRow createComboBoxRow(String key) {
/* 42 */     HtmlElement label = createLabel(key, MC_NOT_MANDATORY);
/* 43 */     HtmlElement element = createComboBoxElement(key, key, MC_NOT_MANDATORY);
/* 44 */     return new VCListRow(label, element, null);
/*    */   }
/*    */   
/*    */   private HtmlElement createLabel(String key, MandatoryCallback mandatoryCallback) {
/* 48 */     return (HtmlElement)new VcHtmlLabel(this.context.getLabel(key), mandatoryCallback);
/*    */   }
/*    */   
/*    */   private HtmlElement createComboBoxElement(String key, String domainID, MandatoryCallback mc) {
/* 52 */     HtmlElement comboBox = null;
/* 53 */     if (key.equals("vc.attributename.salesmake"))
/* 54 */       throw new IllegalArgumentException(); 
/* 55 */     if (key.equals("vc.attributename.model"))
/* 56 */       throw new IllegalArgumentException(); 
/* 57 */     if (key.equals("vc.attributename.modelyear"))
/* 58 */       throw new IllegalArgumentException(); 
/* 59 */     if (key.equals("vc.attributename.engine"))
/* 60 */       throw new IllegalArgumentException(); 
/* 61 */     if (key.equals("vc.attributename.transmission")) {
/* 62 */       throw new IllegalArgumentException();
/*    */     }
/* 64 */     return (HtmlElement)new VehicleOptionsComboBox(this.context, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\VCListRowFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */