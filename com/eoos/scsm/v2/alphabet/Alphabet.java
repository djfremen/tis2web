package com.eoos.scsm.v2.alphabet;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface Alphabet {
  Comparator getComparator();
  
  Set getElements();
  
  List getElementList();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\alphabet\Alphabet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */