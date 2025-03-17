package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.client.ui.MessageCallback;
import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
import java.util.Map;

public interface Tool {
  String getId();
  
  ValueAdapter getType();
  
  void init() throws Exception;
  
  void reset();
  
  Object getVIN(DeviceCommunicationCallback paramDeviceCommunicationCallback, Object paramObject) throws Exception;
  
  Object getECUData(DeviceCommunicationCallback paramDeviceCommunicationCallback, Object paramObject, AttributeValueMap paramAttributeValueMap) throws Exception;
  
  Object reprogram(MessageCallback paramMessageCallback, Object paramObject1, Object paramObject2, String paramString1, String paramString2) throws Exception;
  
  boolean testConnection(Object paramObject);
  
  Map getVIT1History();
  
  void resetVIT1History();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\Tool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */