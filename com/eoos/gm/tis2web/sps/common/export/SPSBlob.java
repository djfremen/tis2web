package com.eoos.gm.tis2web.sps.common.export;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;

public interface SPSBlob extends ProgrammingDataUnit {
  byte[] getData();
  
  void setData(byte[] paramArrayOfbyte);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\export\SPSBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */