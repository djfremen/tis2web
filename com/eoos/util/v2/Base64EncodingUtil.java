/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Base64EncodingUtil
/*    */ {
/*    */   private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
/*    */   
/*    */   public static String encode(byte[] _data) {
/* 13 */     if (_data == null)
/* 14 */       return null; 
/* 15 */     if (_data.length == 0) {
/* 16 */       return "";
/*    */     }
/* 18 */     int fill = _data.length % 3;
/* 19 */     if (fill != 0) {
/* 20 */       fill = 3 - fill;
/*    */     }
/*    */     
/* 23 */     byte[] data = new byte[_data.length + fill];
/* 24 */     System.arraycopy(_data, 0, data, 0, _data.length);
/* 25 */     for (int i = 0; i < fill; i++) {
/* 26 */       data[_data.length + i] = 0;
/*    */     }
/*    */     
/* 29 */     byte[] index = new byte[data.length / 3 * 4];
/*    */     
/* 31 */     for (int j = 0; j < data.length / 3; j++) {
/* 32 */       index[j * 4] = (byte)(data[j * 3] >> 2 & 0x3F);
/* 33 */       index[j * 4 + 1] = (byte)(data[j * 3] << 4 & 0x3F | data[j * 3 + 1] >> 4 & 0xF);
/* 34 */       index[j * 4 + 2] = (byte)(data[j * 3 + 1] << 2 & 0x3F | data[j * 3 + 2] >> 6 & 0x3);
/* 35 */       index[j * 4 + 3] = (byte)(data[j * 3 + 2] & 0x3F);
/*    */     } 
/* 37 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get(); try {
/*    */       int k;
/* 39 */       for (k = 0; k < index.length; k++) {
/* 40 */         retValue.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(index[k]));
/*    */       }
/*    */       
/* 43 */       for (k = 0; k < fill; k++) {
/* 44 */         retValue.setCharAt(retValue.length() - 1 - k, '=');
/*    */       }
/* 46 */       return retValue.toString();
/*    */     } finally {
/*    */       
/* 49 */       StringBufferPool.getThreadInstance().free(retValue);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] decode(String _data) {
/* 55 */     if (_data == null)
/* 56 */       return null; 
/* 57 */     if (_data.length() == 0) {
/* 58 */       return new byte[0];
/*    */     }
/* 60 */     int[] data = new int[_data.length()];
/* 61 */     for (int i = 0; i < _data.length(); i++) {
/* 62 */       data[i] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(_data.charAt(i));
/*    */     }
/*    */     
/* 65 */     byte[] retValue = new byte[data.length / 4 * 3];
/*    */     
/* 67 */     for (int j = 0; j < data.length / 4; j++) {
/* 68 */       retValue[j * 3] = (byte)(data[j * 4] << 2 & 0xFC | data[j * 4 + 1] >> 4 & 0x3);
/* 69 */       retValue[j * 3 + 1] = (byte)(data[j * 4 + 1] << 4 & 0xF0 | data[j * 4 + 2] >> 2 & 0xF);
/* 70 */       retValue[j * 3 + 2] = (byte)(data[j * 4 + 2] << 6 & 0xC0 | data[j * 4 + 3] & 0x3F);
/*    */     } 
/* 72 */     int fill = _data.indexOf('=');
/* 73 */     if (fill == -1) {
/* 74 */       fill = 0;
/* 75 */     } else if (fill == _data.length() - 1) {
/* 76 */       fill = 1;
/*    */     } else {
/* 78 */       fill = 2;
/*    */     } 
/*    */     
/* 81 */     if (fill != 0) {
/* 82 */       byte[] tmp = new byte[retValue.length - fill];
/* 83 */       System.arraycopy(retValue, 0, tmp, 0, tmp.length);
/* 84 */       retValue = tmp;
/*    */     } 
/*    */     
/* 87 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\Base64EncodingUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */