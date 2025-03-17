/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.SchemaAdapterAPI;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CVNResolutionElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(CVNResolutionElement.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private TextInputElement inputPartNumber;
/*    */   
/*    */   private ClickButtonElement buttonApply;
/*    */   
/* 29 */   private Collection result = null;
/*    */   
/*    */   public CVNResolutionElement(final ClientContext context, EntryPanel entryPanel) {
/* 32 */     this.context = context;
/*    */     
/* 34 */     this.inputPartNumber = new TextInputElement(context.createID());
/* 35 */     addElement((HtmlElement)this.inputPartNumber);
/*    */     
/* 37 */     this.buttonApply = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 39 */           return context.getLabel("sps.get.cvn");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 43 */           String partNumber = (String)CVNResolutionElement.this.inputPartNumber.getValue();
/* 44 */           if (partNumber != null && partNumber.trim().length() > 0) {
/* 45 */             return CVNResolutionElement.this.onGetCVN(partNumber);
/*    */           }
/* 47 */           return null;
/*    */         }
/*    */       };
/*    */     
/* 51 */     addElement((HtmlElement)this.buttonApply);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onGetCVN(String partNumber) {
/* 56 */     this.result = null;
/*    */     try {
/* 58 */       this.result = SPSServer.getInstance(this.context.getSessionID()).getCalibrationVerificationNumber(partNumber);
/* 59 */     } catch (Exception e) {
/* 60 */       log.warn("unable to resolve cvn - exception :" + e);
/*    */     } 
/*    */     
/* 63 */     return null;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 67 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{LABEL}:</td><td>{INPUT}</td><td>{BUTTON}</td></tr></table>{RESULT}");
/* 68 */     StringUtilities.replace(tmp, "{LABEL}", this.context.getLabel("part.number"));
/* 69 */     StringUtilities.replace(tmp, "{INPUT}", this.inputPartNumber.getHtmlCode(params));
/* 70 */     StringUtilities.replace(tmp, "{BUTTON}", this.buttonApply.getHtmlCode(params));
/*    */ 
/*    */     
/* 73 */     if (this.result != null) {
/* 74 */       if (this.result.size() == 0) {
/* 75 */         StringUtilities.replace(tmp, "{RESULT}", this.context.getMessage("sps.calibration.info.cvn.unknown.partnumber"));
/*    */       } else {
/* 77 */         StringUtilities.replace(tmp, "{RESULT}", "<br>{ROWS}");
/* 78 */         for (Iterator<SchemaAdapterAPI.CVNResult> iter = this.result.iterator(); iter.hasNext(); ) {
/* 79 */           SchemaAdapterAPI.CVNResult result = iter.next();
/* 80 */           StringUtilities.replace(tmp, "{ROWS}", "CVN: " + result.getCalibrationVerificationNumber() + " (Adapter: " + result.getSchemaAdapterDisplayableID() + ")<br>{ROWS}");
/*    */         } 
/* 82 */         StringUtilities.replace(tmp, "{ROWS}", "");
/*    */       } 
/*    */     } else {
/*    */       
/* 86 */       StringUtilities.replace(tmp, "{RESULT}", "");
/*    */     } 
/*    */     
/* 89 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\entry\CVNResolutionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */