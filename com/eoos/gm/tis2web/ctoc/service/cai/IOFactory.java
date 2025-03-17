package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.si.service.cai.SIO;
import com.eoos.gm.tis2web.si.service.cai.SIOBlob;

public interface IOFactory {
  CTOCFactory getFactory();
  
  SIO getSIO(Integer paramInteger);
  
  SIOBlob getGraphic(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\IOFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */