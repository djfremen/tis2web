package com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter;

import java.util.List;

public interface Rule {
  Solver canApply(List paramList, Interpreter paramInterpreter);
  
  int Law();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\interpreter\Rule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */