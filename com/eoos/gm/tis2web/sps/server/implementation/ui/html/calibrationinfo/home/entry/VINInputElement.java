/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.InvalidVINException;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VINInputElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(VINInputElement.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private TextInputElement inputVIN;
/*    */   
/*    */   private ClickButtonElement buttonApply;
/*    */   
/* 29 */   private InvalidVINException vinException = null;
/*    */   
/*    */   public VINInputElement(final ClientContext context, final EntryPanel entryPanel) {
/* 32 */     this.context = context;
/* 33 */     this.inputVIN = new TextInputElement(context.createID()) {
/*    */         public void setValue(Map map) {
/* 35 */           super.setValue(map);
/* 36 */           if (this.value != null) {
/* 37 */             this.value = ((String)this.value).toUpperCase(Locale.ENGLISH);
/*    */           }
/*    */         }
/*    */         
/*    */         public void setValue(Object value) {
/* 42 */           if (value != null) {
/* 43 */             super.setValue(((String)value).toUpperCase(Locale.ENGLISH));
/*    */           }
/*    */         }
/*    */       };
/* 47 */     addElement((HtmlElement)this.inputVIN);
/*    */     
/* 49 */     this.buttonApply = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 51 */           return context.getLabel("sps.get.calibrations");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 55 */           Object retValue = null;
/* 56 */           VINInputElement.this.vinException = null;
/* 57 */           String vin = (String)VINInputElement.this.inputVIN.getValue();
/*    */           try {
/* 59 */             if (!VehicleConfigurationUtil.checkVIN(vin, 1)) {
/* 60 */               throw new InvalidVINException();
/*    */             }
/* 62 */             retValue = entryPanel.onApplyVIN(vin);
/* 63 */           } catch (InvalidVINException e) {
/* 64 */             VINInputElement.this.vinException = e;
/* 65 */           } catch (SPSException e) {
/* 66 */             VINInputElement.this.vinException = new InvalidVINException(vin);
/* 67 */           } catch (Exception e) {
/* 68 */             VINInputElement.this.vinException = new InvalidVINException(vin);
/* 69 */             VINInputElement.log.error("unable to process vin - exception:" + e, e);
/*    */           } 
/* 71 */           return retValue;
/*    */         }
/*    */       };
/* 74 */     addElement((HtmlElement)this.buttonApply);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 79 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{LABEL}:</td><td>{INPUT}</td><td>{BUTTON}</td></tr>{STATUS}</table>");
/* 80 */     StringUtilities.replace(tmp, "{LABEL}", this.context.getLabel("vin"));
/* 81 */     StringUtilities.replace(tmp, "{INPUT}", this.inputVIN.getHtmlCode(params));
/* 82 */     StringUtilities.replace(tmp, "{BUTTON}", this.buttonApply.getHtmlCode(params));
/* 83 */     if (this.vinException == null) {
/* 84 */       StringUtilities.replace(tmp, "{STATUS}", "");
/*    */     } else {
/* 86 */       StringUtilities.replace(tmp, "{STATUS}", "<tr><td colspan=\"3\" style=\"color:red\">" + this.context.getMessage("invalid.vin") + "</td></tr>");
/*    */     } 
/* 88 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\entry\VINInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */