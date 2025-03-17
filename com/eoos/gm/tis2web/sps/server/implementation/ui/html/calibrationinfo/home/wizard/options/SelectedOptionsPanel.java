/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectedOptionsPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(SelectedOptionsPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 23 */       template = ApplicationContext.getInstance().loadFile(SelectedOptionsPanel.class, "selectedoptionspanel.html", null).toString();
/* 24 */     } catch (Exception e) {
/* 25 */       log.error("unable to load template - error:" + e, e);
/* 26 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   private SelectedOptionsTable table;
/*    */   
/*    */   public SelectedOptionsPanel(ClientContext context, CustomAVMap avMap) {
/* 35 */     this.context = context;
/*    */     
/* 37 */     this.table = new SelectedOptionsTable(context, avMap);
/* 38 */     addElement((HtmlElement)this.table);
/*    */   }
/*    */ 
/*    */   
/*    */   public SelectedOptionsPanel(ClientContext context, SelectedOptionsTable.Callback callback) {
/* 43 */     this.context = context;
/*    */     
/* 45 */     this.table = new SelectedOptionsTable(context, callback);
/* 46 */     addElement((HtmlElement)this.table);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 51 */     StringBuffer tmp = new StringBuffer(template);
/* 52 */     StringUtilities.replace(tmp, "{LABEL_SELECTED_OPTIONS}", this.context.getLabel("sps.selected.options"));
/* 53 */     StringUtilities.replace(tmp, "{SELECTED_OPTIONS_TABLE}", this.table.getHtmlCode(params));
/* 54 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\options\SelectedOptionsPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */