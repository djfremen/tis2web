/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.ListElement;
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
/*    */ public class VCListElement
/*    */   extends ListElement
/*    */ {
/* 20 */   protected ClientContext context = null;
/*    */   
/* 22 */   protected HtmlLabel labelAttribute = null;
/*    */   
/* 24 */   protected HtmlLabel labelValue = null;
/*    */ 
/*    */   
/*    */   public VCListElement(ClientContext context, List data) {
/* 28 */     super(data);
/* 29 */     this.context = context;
/* 30 */     createHeaderLabels();
/*    */   }
/*    */   
/*    */   protected void createHeaderLabels() {
/* 34 */     this.labelAttribute = new HtmlLabel(this.context.getLabel("vc.attribute"));
/* 35 */     this.labelValue = new HtmlLabel(this.context.getLabel("vc.value"));
/*    */   }
/*    */   
/*    */   protected int getColumnCount() {
/* 39 */     return 2;
/*    */   }
/*    */   
/*    */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 43 */     HtmlElement element = null;
/* 44 */     if (data instanceof VCListRow) {
/* 45 */       VCListRow row = (VCListRow)data;
/* 46 */       switch (columnIndex) {
/*    */         case 0:
/* 48 */           element = row.getAttributeLabel();
/*    */           break;
/*    */         case 1:
/* 51 */           element = row.getInputElement();
/*    */           break;
/*    */       } 
/*    */     } 
/* 55 */     return element;
/*    */   }
/*    */   protected HtmlElement getHeader(int columnIndex) {
/*    */     HtmlLabel htmlLabel;
/* 59 */     HtmlElement element = null;
/* 60 */     switch (columnIndex) {
/*    */       case 0:
/* 62 */         htmlLabel = this.labelAttribute;
/*    */         break;
/*    */       case 1:
/* 65 */         htmlLabel = this.labelValue;
/*    */         break;
/*    */     } 
/* 68 */     return (HtmlElement)htmlLabel;
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributesTable(Map<String, String> map) {
/* 72 */     map.put("id", "vctable");
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map<String, String> map) {
/* 76 */     switch (columnIndex) {
/*    */       case 0:
/* 78 */         map.put("id", "vcattribute");
/*    */         break;
/*    */       case 1:
/* 81 */         map.put("id", "vcvalueselection");
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\VCListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */