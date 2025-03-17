/*    */ package com.eoos.gm.tis2web.swdl.common.domain.dtc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.Identifiable;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TroubleCode
/*    */   implements Identifiable, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private byte[] content = null;
/*    */   
/* 19 */   private String identifier = null;
/*    */   
/* 21 */   private String bacCode = null;
/*    */   
/* 23 */   private String countryCode = null;
/*    */   
/*    */   private Long date;
/*    */ 
/*    */   
/*    */   public TroubleCode(String identifier, String bacCode, String countryCode, Long date, byte[] content) {
/* 29 */     this.identifier = identifier;
/* 30 */     this.bacCode = bacCode;
/* 31 */     this.content = content;
/* 32 */     this.date = date;
/* 33 */     this.countryCode = countryCode;
/*    */   }
/*    */   
/*    */   public byte[] getContent() {
/* 37 */     return this.content;
/*    */   }
/*    */   
/*    */   public void setContent(byte[] content) {
/* 41 */     this.content = content;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 45 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getBACCode() {
/* 49 */     return this.bacCode;
/*    */   }
/*    */   
/*    */   public Long getDate() {
/* 53 */     return this.date;
/*    */   }
/*    */   
/*    */   public String getCountryCode() {
/* 57 */     return this.countryCode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\dtc\TroubleCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */