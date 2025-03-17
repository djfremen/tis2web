package com.eoos.persistence.util;

import java.io.Serializable;

public interface Serializer extends Serializable {
  byte[] serialize(Object paramObject);
  
  Object deserialize(byte[] paramArrayOfbyte);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistenc\\util\Serializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */