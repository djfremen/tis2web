/*    */ package com.eoos.gm.tis2web.vc.v2.impl.ui.html.dialog;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlTableRenderer;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class ConfigurationSelectionDialog
/*    */   extends DialogBase {
/* 19 */   private List linkElements = new LinkedList();
/*    */   private ClickButtonElement buttonCancel;
/*    */   
/*    */   public ConfigurationSelectionDialog(final ClientContext context, Collection configurations) {
/* 23 */     super(context);
/*    */     
/* 25 */     for (Iterator<IConfiguration> iter = configurations.iterator(); iter.hasNext(); ) {
/* 26 */       final IConfiguration vc = iter.next();
/* 27 */       LinkElement linkElement = new LinkElement(context.createID(), null)
/*    */         {
/*    */           protected String getLabel() {
/* 30 */             return VehicleConfigurationUtil.toString(vc, context, false);
/*    */           }
/*    */           
/*    */           public Object onClick(Map submitParams) {
/* 34 */             return ConfigurationSelectionDialog.this.onClose(vc);
/*    */           }
/*    */         };
/* 37 */       this.linkElements.add(linkElement);
/*    */     } 
/*    */     
/* 40 */     addAllElements(this.linkElements);
/*    */     
/* 42 */     this.buttonCancel = new ClickButtonElement(context.createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 45 */           return context.getLabel("cancel");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 49 */           return ConfigurationSelectionDialog.this.onClose((IConfiguration)null);
/*    */         }
/*    */       };
/*    */     
/* 53 */     addElement((HtmlElement)this.buttonCancel);
/*    */   }
/*    */   
/*    */   protected String getID() {
/* 57 */     return "vcselection";
/*    */   }
/*    */   
/*    */   protected String getContent(final Map params) {
/* 61 */     HtmlTableRenderer.CallbackAdapter callbackAdapter = new HtmlTableRenderer.CallbackAdapter()
/*    */       {
/*    */         public int getRowCount() {
/* 64 */           return ConfigurationSelectionDialog.this.linkElements.size();
/*    */         }
/*    */         
/*    */         public String getContent(int rowIndex, int columnIndex) {
/* 68 */           if (columnIndex == 1) {
/* 69 */             return ((LinkElement)ConfigurationSelectionDialog.this.linkElements.get(rowIndex)).getHtmlCode(params);
/*    */           }
/* 71 */           return "&bull;";
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public int getColumnCount() {
/* 77 */           return 2;
/*    */         }
/*    */       };
/*    */     
/* 81 */     StringBuffer ret = new StringBuffer();
/* 82 */     ret.append(HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)callbackAdapter));
/* 83 */     ret.append("<div class=\"buttoncontainer\">" + this.buttonCancel.getHtmlCode(params) + "</div>");
/* 84 */     return ret.toString();
/*    */   }
/*    */   
/*    */   protected String getTitle(Map params) {
/* 88 */     return this.context.getMessage("vc.please.choose.vehicle.configuration");
/*    */   }
/*    */   
/*    */   protected abstract Object onClose(IConfiguration paramIConfiguration);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\imp\\ui\html\dialog\ConfigurationSelectionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */