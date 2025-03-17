package com.eoos.gm.tis2web.techlineprint.client.app;

import com.eoos.datatype.gtwo.Pair;

public interface TechlinePrintBridge {
  boolean setProperties(String paramString, Pair[] paramArrayOfPair);
  
  boolean setLanguage(String paramString1, String paramString2);
  
  boolean startUI(String paramString, String[] paramArrayOfString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\app\TechlinePrintBridge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */