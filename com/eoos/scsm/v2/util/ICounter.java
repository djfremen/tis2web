package com.eoos.scsm.v2.util;

import java.math.BigInteger;

public interface ICounter extends Comparable {
  void inc();
  
  void inc(long paramLong);
  
  void dec();
  
  void dec(long paramLong);
  
  BigInteger getCount();
  
  void setCount(BigInteger paramBigInteger);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\ICounter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */