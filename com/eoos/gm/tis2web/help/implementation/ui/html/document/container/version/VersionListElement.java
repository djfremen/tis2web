/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container.version;
/*    */ 
/*    */ import com.eoos.datatype.VersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.ListElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VersionListElement
/*    */   extends ListElement
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected List data;
/*    */   
/*    */   public VersionListElement(ClientContext context, List moduleInformation) {
/* 23 */     setDataCallback(this);
/* 24 */     this.context = context;
/* 25 */     this.data = moduleInformation;
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 29 */     return 2;
/*    */   }
/*    */   
/*    */   protected HtmlElement getHeader(int columnIndex) {
/* 33 */     if (columnIndex == 0) {
/* 34 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("module.label"));
/*    */     }
/* 36 */     return (HtmlElement)new HtmlLabel(this.context.getLabel("module.database"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean enableHeader() {
/* 41 */     return true;
/*    */   }
/*    */   
/*    */   protected String getModuleLabel(String moduleType) {
/* 45 */     return this.context.getLabel("module.type." + moduleType);
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributesTable(Map<String, String> map) {
/* 49 */     map.put("width", "100%");
/* 50 */     map.put("border", "1");
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 54 */     ModuleInformation mi = (ModuleInformation)data;
/* 55 */     if (columnIndex == 0) {
/* 56 */       VersionNumber versionNumber = mi.getVersion();
/* 57 */       String description = mi.getDescription(this.context.getLocale());
/* 58 */       if (versionNumber != null) {
/* 59 */         return (HtmlElement)new HtmlLabel(String.valueOf(description) + "<br>(" + this.context.getLabel("module.version") + " = " + String.valueOf(mi.getVersion()) + ")");
/*    */       }
/* 61 */       return (HtmlElement)new HtmlLabel(String.valueOf(description));
/*    */     } 
/* 63 */     if (columnIndex == 1) {
/* 64 */       Object dbVersionInfo = mi.getDatabaseVersionInformation();
/* 65 */       if (dbVersionInfo != null) {
/* 66 */         List<Object> rows = new ArrayList();
/* 67 */         if (dbVersionInfo instanceof Collection) {
/* 68 */           rows.addAll((Collection)dbVersionInfo);
/*    */         } else {
/* 70 */           rows.add(dbVersionInfo);
/*    */         } 
/* 72 */         return (HtmlElement)new VersionElement(this.context, rows);
/*    */       } 
/* 74 */       return (HtmlElement)new HtmlLabel("&nbsp;");
/*    */     } 
/* 76 */     throw new IllegalArgumentException();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List getData() {
/* 82 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\version\VersionListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */