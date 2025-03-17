package com.eoos.gm.tis2web.frame.dwnld.client;

import com.eoos.gm.tis2web.common.MissingAuthenticationException;
import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
import java.io.IOException;
import java.io.OutputStream;

public interface FileDataProvider {
  void getData(DownloadFile paramDownloadFile, OutputStream paramOutputStream) throws IOException, MissingAuthenticationException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\FileDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */