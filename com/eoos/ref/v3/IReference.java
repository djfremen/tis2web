package com.eoos.ref.v3;

public interface IReference<T> {
  T get();
  
  void clear();
  
  public static interface Snoopable<T> {
    T snoop();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\IReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */