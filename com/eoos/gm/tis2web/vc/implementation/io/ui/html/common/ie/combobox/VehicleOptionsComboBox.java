/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.combobox;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.vc.implementation.io.datamodel.VehicleOptionsData;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import java.util.List;
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
/*    */ public class VehicleOptionsComboBox
/*    */   extends SelectBoxSelectionElement
/*    */ {
/* 20 */   private ClientContext context = null;
/*    */   
/* 22 */   private VehicleOptionsData vod = null;
/*    */ 
/*    */   
/*    */   public VehicleOptionsComboBox(ClientContext context, String key) {
/* 26 */     super(key, true, null, 1);
/* 27 */     this.context = context;
/* 28 */     this.vod = VehicleOptionsData.getInstance(this.context);
/*    */   }
/*    */   
/*    */   public void setOptions(List options) {
/* 32 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 36 */     return true;
/*    */   }
/*    */   
/*    */   public String getParameterName() {
/* 40 */     return this.parameterName;
/*    */   }
/*    */   
/*    */   protected List getOptions() {
/* 44 */     return this.vod.getOptionValues(getParameterName());
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 52 */     String retValue = null;
/* 53 */     String valueName = (String)option;
/* 54 */     if (valueName.equalsIgnoreCase("$INSTALLED$")) {
/* 55 */       retValue = this.context.getLabel("vo.installed");
/* 56 */     } else if (valueName.equalsIgnoreCase("$NOT_INSTALLED$")) {
/* 57 */       retValue = this.context.getLabel("vo.not.installed");
/* 58 */     } else if (valueName.equalsIgnoreCase("$UNKNOWN$")) {
/* 59 */       retValue = this.context.getLabel("vo.unknown");
/*    */     } else {
/* 61 */       retValue = super.getDisplayValue(option);
/*    */     } 
/* 63 */     return this.vod.truncateValueName(retValue);
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 67 */     return this.vod.getSelectedValue(getParameterName());
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 71 */     String fullValueName = this.vod.truncatedToFullName((String)value);
/* 72 */     this.vod.setSelectedValue(getParameterName(), fullValueName);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\combobox\VehicleOptionsComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */