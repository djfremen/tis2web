package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import java.io.Serializable;
import java.util.List;

public interface History extends Serializable {
  String getReleaseDate();
  
  List getAttributes();
  
  String getDescription();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\History.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */