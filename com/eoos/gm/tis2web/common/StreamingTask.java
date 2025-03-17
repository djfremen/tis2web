package com.eoos.gm.tis2web.common;

import com.eoos.util.Task;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamingTask extends Task {
  void execute(OutputStream paramOutputStream) throws Exception;
  
  void handleResult(InputStream paramInputStream) throws IOException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\StreamingTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */