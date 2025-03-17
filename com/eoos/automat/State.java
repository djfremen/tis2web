package com.eoos.automat;

public interface State {
  boolean superstateOf(State paramState);
  
  boolean substateOf(State paramState);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\State.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */