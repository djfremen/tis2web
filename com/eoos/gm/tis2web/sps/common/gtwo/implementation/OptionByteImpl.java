/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.OptionByte;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class OptionByteImpl
/*    */   implements OptionByte, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected int Device_ID;
/*    */   protected int Type;
/*    */   protected int Block_No;
/*    */   protected int Byte_Offset;
/*    */   protected int Data;
/*    */   
/*    */   public OptionByteImpl(int type, int device_ID, int block_No, int byte_Offset, byte data) {
/* 16 */     this.Type = type;
/* 17 */     this.Device_ID = device_ID;
/* 18 */     this.Block_No = block_No;
/* 19 */     this.Byte_Offset = byte_Offset;
/* 20 */     this.Data = data;
/*    */   }
/*    */   
/*    */   public OptionByteImpl(int type, int device_ID) {
/* 24 */     this.Type = type;
/* 25 */     this.Device_ID = device_ID;
/*    */   }
/*    */   
/*    */   public void setBlockNo(String block_No) {
/* 29 */     this.Block_No = Integer.parseInt(block_No, 16);
/*    */   }
/*    */   
/*    */   public void setByteOffset(String byte_Offset) {
/* 33 */     this.Byte_Offset = Integer.parseInt(byte_Offset, 16);
/*    */   }
/*    */   
/*    */   public void setData(String data) {
/* 37 */     this.Data = Integer.parseInt(data, 16);
/*    */   }
/*    */   
/*    */   public void setBlockNo(int block_No) {
/* 41 */     this.Block_No = block_No;
/*    */   }
/*    */   
/*    */   public void setByteOffset(int byte_Offset) {
/* 45 */     this.Byte_Offset = byte_Offset;
/*    */   }
/*    */   
/*    */   public void setData(int data) {
/* 49 */     this.Data = data;
/*    */   }
/*    */   
/*    */   public void setDeviceID(int device_ID) {
/* 53 */     this.Device_ID = device_ID;
/*    */   }
/*    */   
/*    */   public void setType(int type) {
/* 57 */     this.Type = type;
/*    */   }
/*    */   
/*    */   public int getDeviceID() {
/* 61 */     return this.Device_ID;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 65 */     return this.Type;
/*    */   }
/*    */   
/*    */   public int getBlockNum() {
/* 69 */     return this.Block_No;
/*    */   }
/*    */   
/*    */   public int getByteNum() {
/* 73 */     return this.Byte_Offset;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 77 */     return this.Data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\OptionByteImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */