package com.eoos.gm.tis2web.sps.client.ui;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;

public interface UIRequestHandler {
  void execute(AssignmentRequest paramAssignmentRequest, AttributeValueMapExt paramAttributeValueMapExt);
  
  void notify(Attribute paramAttribute);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\UIRequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */