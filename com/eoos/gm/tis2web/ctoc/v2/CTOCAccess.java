/*    */ package com.eoos.gm.tis2web.ctoc.v2;public interface CTOCAccess { Collection<Node> getTOCRoots(CTOCDomain paramCTOCDomain);
/*    */   Collection<Node> getChildren(Node paramNode);
/*    */   String getLabel(Node paramNode, Locale paramLocale);
/*    */   Node getParent(Node paramNode);
/*    */   Collection<Content> getContent(Node paramNode);
/*    */   VCR getVCR(Node paramNode);
/*    */   VCR getVCR(Content paramContent);
/*    */   String getProperty(Node paramNode, PropertyKey paramPropertyKey);
/*    */   public static interface Content {}
/*    */   public static interface Node extends Comparable {}
/* 11 */   public enum PropertyKey { SIT; }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\v2\CTOCAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */