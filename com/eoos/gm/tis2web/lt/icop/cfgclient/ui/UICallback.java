/*    */ package com.eoos.gm.tis2web.lt.icop.cfgclient.ui;
/*    */ public interface UICallback { String getLabel(String paramString);
/*    */   String toString(Object paramObject);
/*    */   List getOptions(ID paramID);
/*    */   Object getCurrentValue(ID paramID);
/*    */   Locale getLocale();
/*    */   void onCancel();
/*    */   void onOK(Selection paramSelection);
/*    */   
/*    */   public static interface Selection { Object getSelection(UICallback.ID param1ID); }
/*    */   
/* 12 */   public enum ID { SERVER, MAKE, COUNTRY, LANGUAGE; }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclien\\ui\UICallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */