package com.eoos.gm.tis2web.frame.dwnld.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IServer {
  Collection getDownloadUnits(Collection paramCollection);
  
  Collection getRelatedUnits(Collection paramCollection);
  
  void getData(DownloadFile paramDownloadFile, OutputStream paramOutputStream) throws IOException;
  
  CookieWrapper getAkamaiCookie();
  
  Collection getAllDownloadURIs();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\IServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */