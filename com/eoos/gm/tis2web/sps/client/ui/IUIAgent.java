/*     */ package com.eoos.gm.tis2web.sps.client.ui;public interface IUIAgent extends UIRequestHandler { void triggerNextRequest(); void blockGUI(); void unblockGUI(); void lockOn(); void lockOff(); void triggerRequest(); void execute(SelectionResult paramSelectionResult, ValueRetrieval paramValueRetrieval); void setVIT1(VIT1 paramVIT1, Integer paramInteger); void displayFinishButtons(); void changeStateButtons(); void setReprogrammingController(String paramString); void setReprogrammingController(String paramString1, String paramString2); void indicateReprogrammingFailed(); void undo(); void reset(); void restart(); String getFileName(String paramString1, String paramString2, List paramList, String paramString3); void requestBulletinDisplay(String paramString); String requestInstructionHTML(String paramString) throws Exception; byte[] requestInstructionImage(String paramString) throws Exception; boolean displayQuestionDialog(String paramString); void displayMessageDialog(String paramString); void removeMessageDialog(); void displayHTMLMessage(String paramString, List paramList); boolean displayQuestionHTMLMessage(String paramString, List paramList); void displayInformationText(String paramString); void updateInformationText(String paramString); void destroyInformationText(); void setStatusBar(String paramString);
/*     */   void showTestSummary(DefaultTableModel paramDefaultTableModel);
/*     */   void displayReprogramStatusPS(List paramList);
/*     */   void handleException(Exception paramException);
/*     */   void handleException(String paramString1, String paramString2);
/*     */   void handleException(String paramString);
/*     */   void displayMessage(String paramString);
/*     */   void showMessage(String paramString1, String paramString2, String paramString3);
/*     */   void displayHTML(String paramString);
/*     */   boolean isNAO();
/*     */   boolean isGlobalAdapter();
/*     */   boolean supportSPSFunctions();
/*     */   boolean isFinalInstructionDisplayed();
/*     */   void proceed();
/*     */   boolean isProceedPossible();
/*     */   public static class ImplAdapter implements IUIAgent { public boolean isNAO() {
/*  17 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isGlobalAdapter() {
/*  21 */       return false;
/*     */     }
/*     */     
/*     */     public boolean supportSPSFunctions() {
/*  25 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void displayHTML(String html) {}
/*     */ 
/*     */     
/*     */     public void showMessage(String severity, String title, String msg) {}
/*     */ 
/*     */     
/*     */     public void displayMessage(String msg) {}
/*     */ 
/*     */     
/*     */     public void handleException(String exceptionID) {}
/*     */ 
/*     */     
/*     */     public void handleException(String exceptionID, String info) {}
/*     */ 
/*     */     
/*     */     public void handleException(Exception exception) {}
/*     */ 
/*     */     
/*     */     public void displayReprogramStatusPS(List state) {}
/*     */ 
/*     */     
/*     */     public void showTestSummary(DefaultTableModel summary) {}
/*     */ 
/*     */     
/*     */     public void setStatusBar(String info) {}
/*     */ 
/*     */     
/*     */     public void destroyInformationText() {}
/*     */ 
/*     */     
/*     */     public void updateInformationText(String message) {}
/*     */ 
/*     */     
/*     */     public void displayInformationText(String message) {}
/*     */     
/*     */     public boolean displayQuestionHTMLMessage(String message, List traceInfo) {
/*  65 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void displayHTMLMessage(String message, List traceInfo) {}
/*     */ 
/*     */     
/*     */     public void removeMessageDialog() {}
/*     */ 
/*     */     
/*     */     public void displayMessageDialog(String message) {}
/*     */     
/*     */     public boolean displayQuestionDialog(String message) {
/*  78 */       return false;
/*     */     }
/*     */     
/*     */     public byte[] requestInstructionImage(String imageID) throws Exception {
/*  82 */       return null;
/*     */     }
/*     */     
/*     */     public String requestInstructionHTML(String instructionID) throws Exception {
/*  86 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void requestBulletinDisplay(String bulletin) {}
/*     */     
/*     */     public String getFileName(String title, String filter, List fileTypes, String initialDir) {
/*  93 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void restart() {}
/*     */ 
/*     */     
/*     */     public void reset() {}
/*     */ 
/*     */     
/*     */     public void undo() {}
/*     */ 
/*     */     
/*     */     public void indicateReprogrammingFailed() {}
/*     */ 
/*     */     
/*     */     public void setReprogrammingController(String label) {}
/*     */ 
/*     */     
/*     */     public void setReprogrammingController(String function, String position) {}
/*     */ 
/*     */     
/*     */     public void changeStateButtons() {}
/*     */ 
/*     */     
/*     */     public void displayFinishButtons() {}
/*     */ 
/*     */     
/*     */     public void setVIT1(VIT1 vit1, Integer device) {}
/*     */ 
/*     */     
/*     */     public void execute(SelectionResult result, ValueRetrieval data) {}
/*     */ 
/*     */     
/*     */     public void execute(SelectionResult result) {}
/*     */ 
/*     */     
/*     */     public void triggerRequest() {}
/*     */ 
/*     */     
/*     */     public void lockOff() {}
/*     */ 
/*     */     
/*     */     public void lockOn() {}
/*     */ 
/*     */     
/*     */     public void unblockGUI() {}
/*     */ 
/*     */     
/*     */     public void blockGUI() {}
/*     */ 
/*     */     
/*     */     public void triggerNextRequest() {}
/*     */ 
/*     */     
/*     */     public void execute(AssignmentRequest request, AttributeValueMapExt data) {}
/*     */ 
/*     */     
/*     */     public void notify(Attribute attribute) {}
/*     */     
/*     */     public boolean isFinalInstructionDisplayed() {
/* 154 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isProceedPossible() {
/* 158 */       return false;
/*     */     }
/*     */     
/*     */     public void proceed() {} }
/*     */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\IUIAgent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */