/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.ButtonContainer;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.nao.DetailedSummaryPopup;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.swing.table.TableModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SummaryTableNAO
/*    */   extends HtmlElementContainerBase
/*    */   implements ButtonContainer
/*    */ {
/*    */   private ClientContext context;
/*    */   private TableModel model;
/*    */   private ClickButtonElement detailedSummaryButton;
/* 26 */   private List buttonElements = new LinkedList();
/*    */ 
/*    */   
/*    */   public SummaryTableNAO(final ClientContext context, TableModel model, final CustomAVMap avMap) {
/* 30 */     this.context = context;
/* 31 */     this.model = model;
/*    */     
/* 33 */     this.detailedSummaryButton = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 35 */           return context.getLabel("sps.detailed.summary");
/*    */         }
/*    */ 
/*    */         
/*    */         public Object onClick(Map submitParams) {
/*    */           try {
/* 41 */             return new DetailedSummaryPopup(context, avMap);
/* 42 */           } catch (Exception e) {
/* 43 */             return "<html><body onLoad=\"javascript:window.close()\"></body></html>";
/*    */           } 
/*    */         }
/*    */         
/*    */         protected String getTargetFrame() {
/* 48 */           return "detailedsummary";
/*    */         }
/*    */       };
/* 51 */     addElement((HtmlElement)this.detailedSummaryButton);
/*    */     
/* 53 */     this.buttonElements.add(this.detailedSummaryButton);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 57 */     StringBuffer tmp = new StringBuffer("<span class=\"nao\" ><table width=\"100%\">{ROWS}</table></span>");
/*    */     
/* 59 */     StringBuffer header = new StringBuffer();
/* 60 */     header.append("<tr><th>");
/* 61 */     header.append(this.context.getLabel("id"));
/* 62 */     header.append("</th><th>");
/* 63 */     header.append(this.context.getLabel("selected") + "#");
/* 64 */     header.append("</th><th>");
/* 65 */     header.append(this.context.getLabel("description"));
/* 66 */     header.append("</th></tr>");
/* 67 */     StringUtilities.replace(tmp, "{ROWS}", header.toString() + "{ROWS}");
/*    */     
/* 69 */     for (int i = 0; i < this.model.getRowCount(); i++) {
/* 70 */       StringBuffer row = new StringBuffer("<tr>{COLS}</tr>");
/* 71 */       for (int j = 0; j < this.model.getColumnCount(); j++) {
/* 72 */         String col = "<td>" + String.valueOf(this.model.getValueAt(i, j)) + "</td>";
/* 73 */         StringUtilities.replace(row, "{COLS}", col + "{COLS}");
/*    */       } 
/* 75 */       StringUtilities.replace(row, "{COLS}", "");
/* 76 */       StringUtilities.replace(tmp, "{ROWS}", row.toString() + "{ROWS}");
/*    */     } 
/* 78 */     StringUtilities.replace(tmp, "{ROWS}", "");
/* 79 */     return tmp.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public List getButtonElements() {
/* 84 */     return this.buttonElements;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\SummaryTableNAO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */