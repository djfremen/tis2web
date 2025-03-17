package com.eoos.gm.tis2web.sps.client.ui.page;

import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;

public interface UIPage {
  AssignmentRequest getRequest();
  
  UIPage getPredecessor();
  
  void activate(Object paramObject);
  
  UIPage undo();
  
  boolean handle(AssignmentRequest paramAssignmentRequest, AttributeValueMapExt paramAttributeValueMapExt);
  
  boolean handle(SelectionResult paramSelectionResult, ValueRetrieval paramValueRetrieval);
  
  void setPredecessor(UIPage paramUIPage);
  
  void dump(int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\UIPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */