package com.eoos.collection;

import java.util.Map;

public interface BidirectionalMap extends Map {
  Object getKey(Object paramObject);
  
  BidirectionalMap inverse();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\collection\BidirectionalMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */