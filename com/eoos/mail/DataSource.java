package com.eoos.mail;

import java.io.IOException;
import java.io.InputStream;

public interface DataSource {
  String getContentType();
  
  InputStream getInputStream() throws IOException;
  
  String getName();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\DataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */