package com.eoos.gm.tis2web.si.client.model;

public interface IApplication {
  String getLabel(String paramString);
  
  String getMessage(String paramString);
  
  void executeTransfer() throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\model\IApplication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */