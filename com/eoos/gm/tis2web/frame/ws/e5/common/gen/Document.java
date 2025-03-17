/*    */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
/*    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "", propOrder = {"doc"})
/*    */ @XmlRootElement(name = "Document")
/*    */ public class Document
/*    */ {
/*    */   @XmlElement(required = true, type = String.class)
/*    */   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
/*    */   @XmlSchemaType(name = "hexBinary")
/*    */   protected byte[] doc;
/*    */   @XmlAttribute
/*    */   protected String mtype;
/*    */   
/*    */   public byte[] getDoc() {
/* 58 */     return this.doc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDoc(byte[] value) {
/* 70 */     this.doc = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMtype() {
/* 82 */     if (this.mtype == null) {
/* 83 */       return "message/rfc822";
/*    */     }
/* 85 */     return this.mtype;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMtype(String value) {
/* 98 */     this.mtype = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\Document.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */