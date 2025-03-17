package com.eoos.gm.tis2web.vcr.service.cai;

import java.util.List;

public interface VCRExpression {
  List getTerms();
  
  void add(VCRAttribute paramVCRAttribute);
  
  void add(VCRTerm paramVCRTerm);
  
  VCRTerm getTerm(int paramInt);
  
  String toString();
  
  boolean identical(VCRExpression paramVCRExpression);
  
  VCRExpression copy();
  
  boolean gt(VCRExpression paramVCRExpression);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\cai\VCRExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */