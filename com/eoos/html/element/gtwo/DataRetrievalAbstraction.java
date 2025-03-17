package com.eoos.html.element.gtwo;

import java.util.List;

public interface DataRetrievalAbstraction {
  void setDataCallback(DataCallback paramDataCallback);
  
  DataCallback getDataCallback();
  
  public static interface DataCallback {
    List getData();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\DataRetrievalAbstraction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */