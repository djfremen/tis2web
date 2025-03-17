/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TransferDataPanel;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HtmlDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ 
/*    */ public class DisplayPage
/*    */   extends DefaultPage {
/* 16 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/*    */   public DisplayPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 19 */     super(agent, gui, data, request);
/*    */   }
/*    */   
/*    */   public void activate(Object savepoint) {
/* 23 */     boolean handleIntermediateProgrammingInstruction = false;
/* 24 */     if (this.request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 25 */       handleIntermediateProgrammingInstruction = true;
/* 26 */       String title = resourceProvider.getLabel(null, "pre-programming.instructions");
/* 27 */       title = title + " (" + TransferDataPanel.getReprogrammingController() + ")";
/* 28 */       this.savepoint = savepoint;
/* 29 */       String message = '\n' + ((HtmlDisplayRequest)this.request).getHtmlCode() + '\n' + '\n';
/* 30 */       this.gui.displayMessage("info", title, message);
/* 31 */     } else if (this.request.getAttribute().equals(CommonAttribute.INTERMEDIATE_POST_PROGRAMMING_INSTRUCTIONS)) {
/* 32 */       handleIntermediateProgrammingInstruction = true;
/* 33 */       String title = resourceProvider.getLabel(null, "post-programming.instructions");
/* 34 */       title = title + " (" + TransferDataPanel.getReprogrammingController() + ")";
/* 35 */       this.savepoint = savepoint;
/* 36 */       String message = '\n' + ((HtmlDisplayRequest)this.request).getHtmlCode() + '\n' + '\n';
/* 37 */       this.gui.displayMessage("info", title, message);
/* 38 */     } else if (this.request.getAttribute().equals(CommonAttribute.INTERMEDIATE_PROGRAMMING_INSTRUCTIONS)) {
/* 39 */       handleIntermediateProgrammingInstruction = true;
/* 40 */       String urlString = ((HtmlDisplayRequest)this.request).getHtmlCode();
/* 41 */       String title = null;
/* 42 */       String message = null;
/* 43 */       if (urlString.indexOf("intermediate-pre-prog-instructions") >= 0) {
/* 44 */         title = resourceProvider.getLabel(null, "programming-sequence.pre-instructions");
/* 45 */         message = urlString.substring("intermediate-pre-prog-instructions".length() + 1);
/* 46 */       } else if (urlString.indexOf("intermediate-post-prog-instructions") >= 0) {
/* 47 */         title = resourceProvider.getLabel(null, "programming-sequence.post-instructions");
/* 48 */         message = urlString.substring("intermediate-post-prog-instructions".length() + 1);
/*    */       } 
/* 50 */       log.debug("execute page: " + getRequestAttributeDisplay());
/* 51 */       this.savepoint = savepoint;
/* 52 */       message = '\n' + message + '\n' + '\n';
/* 53 */       this.gui.displayMessage("info", title, message);
/*    */     } else {
/* 55 */       super.activate(savepoint);
/*    */     } 
/* 57 */     this.data.set(this.request.getAttribute(), CommonValue.OK);
/* 58 */     if (this.request.getAttribute().equals(CommonAttribute.REPLACE_INSTRUCTIONS)) {
/* 59 */       if (this.data.getValue(CommonAttribute.CONTROLLER) == null) {
/* 60 */         this.agent.triggerRequest();
/*    */       
/*    */       }
/*    */     }
/* 64 */     else if (this.request.getAttribute().equals(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS)) {
/* 65 */       if (this.data.getValue(CommonAttribute.SAME_CALIBRATIONS) == null) {
/* 66 */         this.agent.triggerRequest();
/*    */       }
/* 68 */     } else if (this.request.getAttribute().equals(CommonAttribute.FINAL_INSTRUCTIONS)) {
/* 69 */       this.agent.triggerRequest();
/*    */     } 
/* 71 */     if (!this.request.getAttribute().equals(CommonAttribute.TYPE4_DATA)) {
/* 72 */       if (!handleIntermediateProgrammingInstruction) {
/* 73 */         this.gui.setNextButtonState(true);
/*    */       }
/*    */     } else {
/* 76 */       this.agent.notify(CommonAttribute.REPROGRAM);
/* 77 */       this.agent.triggerRequest();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\DisplayPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */