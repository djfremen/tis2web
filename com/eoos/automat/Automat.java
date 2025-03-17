package com.eoos.automat;

import java.util.Set;

public interface Automat {
  void process(Object paramObject);
  
  Set getCurrentStates();
  
  void reset();
  
  State getStartState();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\Automat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */