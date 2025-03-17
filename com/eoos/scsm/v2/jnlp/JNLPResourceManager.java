package com.eoos.scsm.v2.jnlp;

import com.eoos.scsm.v2.util.VersionNumber;
import java.io.IOException;
import java.io.InputStream;

public interface JNLPResourceManager {
  Object getResource(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws ResourceNotFoundException, VersionNotFoundException;
  
  InputStream getInputStream(Object paramObject) throws IOException;
  
  MIME getMIME(Object paramObject);
  
  VersionNumber getVersionNumber(Object paramObject);
  
  String getName(Object paramObject);
  
  MIME getEncoding(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\jnlp\JNLPResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */