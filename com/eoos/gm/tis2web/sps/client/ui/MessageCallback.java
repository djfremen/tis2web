package com.eoos.gm.tis2web.sps.client.ui;

import java.util.List;

public interface MessageCallback {
  void displayMessageDialog(String paramString);
  
  boolean displayQuestionDialog(String paramString);
  
  void displayMessageDialogMsg(String paramString);
  
  boolean displayQuestionDialogMsg(String paramString);
  
  void displayInformationText(String paramString);
  
  void updateInformationText(String paramString);
  
  void destroyInformationText();
  
  void displayHTMLMessage(String paramString, List paramList);
  
  boolean displayQuestionHTMLMessage(String paramString, List paramList);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\MessageCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */