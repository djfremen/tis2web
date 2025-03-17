/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.ModuleFilter;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ModuleSelectionElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private ClientContext context;
/*    */   private SelectBoxSelectionElement selectionElement;
/*    */   
/*    */   public ModuleSelectionElement(final ClientContext context, List<?> modules) {
/* 28 */     this.context = context;
/*    */     
/* 30 */     final List filteredModules = (List)CollectionUtil.filterAndReturn((modules != null) ? new ArrayList(modules) : null, ModuleFilter.FILTER_MODULE_0);
/* 31 */     DataRetrievalAbstraction.DataCallback dc = new DataRetrievalAbstraction.DataCallback() {
/*    */         public List getData() {
/* 33 */           return filteredModules;
/*    */         }
/*    */       };
/* 36 */     this.selectionElement = new SelectBoxSelectionElement(context.createID(), true, dc, dc.getData().size(), "_top") {
/*    */         protected String getDisplayValue(Object option) {
/* 38 */           return ((Module)option).getDenotation(context.getLocale());
/*    */         }
/*    */         
/*    */         protected boolean autoSubmitOnChange() {
/* 42 */           return true;
/*    */         }
/*    */         
/*    */         protected Object onChange(Map submitParams) {
/* 46 */           return ModuleSelectionElement.this.onModuleSelected((Module)getValue());
/*    */         }
/*    */         
/*    */         protected Map getAdditionalAttributes(Object option) {
/* 50 */           Map<Object, Object> retValue = null;
/* 51 */           if (!ModuleSelectionElement.this.hasSelectedPart((Module)option)) {
/* 52 */             retValue = new HashMap<Object, Object>();
/* 53 */             retValue.put("style", "color:red");
/*    */           } 
/* 55 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 59 */     addElement((HtmlElement)this.selectionElement);
/*    */     
/* 61 */     this.selectionElement.setValue(dc.getData().get(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 66 */     return this.selectionElement.getValue();
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 70 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{LABEL}:</td><td>{SELECTION}</td></tr></table>");
/* 71 */     StringUtilities.replace(tmp, "{LABEL}", this.context.getLabel("module"));
/* 72 */     StringUtilities.replace(tmp, "{SELECTION}", this.selectionElement.getHtmlCode(params));
/* 73 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public abstract Object onModuleSelected(Module paramModule);
/*    */   
/*    */   public abstract boolean hasSelectedPart(Module paramModule);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\ModuleSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */