package com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
import com.eoos.gm.tis2web.sps.service.cai.RequestException;
import java.util.Collection;

public interface SPSClientFacade {
  Boolean execute(AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
  
  byte[] getSPSBlob(ProgrammingDataUnit paramProgrammingDataUnit, AttributeValueMap paramAttributeValueMap) throws Exception;
  
  String getBulletin(String paramString1, String paramString2) throws Exception;
  
  String getHTML(String paramString1, String paramString2) throws Exception;
  
  byte[] getImage(String paramString) throws Exception;
  
  String getHWKReplacement() throws UnprivilegedUserException;
  
  void sendMail(String paramString1, Collection paramCollection1, String paramString2, String paramString3, Collection paramCollection2) throws Exception;
  
  String getHardwareID() throws Exception;
  
  String getMessage(String paramString, AttributeValueMap paramAttributeValueMap);
  
  void sendErrorNotificationEmail(String paramString, AttributeValueMap paramAttributeValueMap);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\serveraccess\SPSClientFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */