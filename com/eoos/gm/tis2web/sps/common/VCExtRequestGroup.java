package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.util.List;

public interface VCExtRequestGroup extends RequestGroup {
  String getVIN();
  
  List getAttributes();
  
  Value getValue(Attribute paramAttribute);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\VCExtRequestGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */