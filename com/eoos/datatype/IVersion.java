package com.eoos.datatype;

public interface IVersion extends Comparable {
  boolean isHigherThan(String paramString) throws StructureException;
  
  boolean isEqualTo(String paramString);
  
  public static class StructureException extends Exception {
    private static final long serialVersionUID = 1L;
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\IVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */