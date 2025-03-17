/*    */ package com.eoos.gm.tis2web.si.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class AbstractSIO
/*    */   implements SIO
/*    */ {
/*    */   private Integer sioID;
/*    */   private int order;
/*    */   private VCR vcr;
/*    */   
/*    */   protected AbstractSIO(Integer sioID, int order, VCR vcr) {
/* 19 */     this.sioID = sioID;
/* 20 */     this.order = order;
/* 21 */     this.vcr = vcr;
/*    */   }
/*    */   
/*    */   public abstract boolean isQualified(LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
/*    */   
/*    */   public List getChildren() {
/* 27 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 35 */     return this.sioID;
/*    */   }
/*    */   
/*    */   public abstract String getLabel(LocaleInfo paramLocaleInfo);
/*    */   
/*    */   public int getOrder() {
/* 41 */     return this.order;
/*    */   }
/*    */   
/*    */   public abstract Object getProperty(SITOCProperty paramSITOCProperty);
/*    */   
/*    */   public abstract SITOCType getType();
/*    */   
/*    */   public VCR getVCR() {
/* 49 */     return this.vcr;
/*    */   }
/*    */   
/*    */   public boolean hasProperty(SITOCProperty property) {
/* 53 */     return (getProperty(property) != null);
/*    */   }
/*    */   
/*    */   public boolean isSIO() {
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public void setVCR(VCR vcr) {
/* 61 */     this.vcr = vcr;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append(Util.getClassName(getClass())).append("[id:").append(getID()).append("]");
/* 67 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 71 */     int ret = SIO.class.hashCode();
/* 72 */     ret = HashCalc.addHashCode(ret, getID());
/* 73 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 77 */     if (this == obj)
/* 78 */       return true; 
/* 79 */     if (obj instanceof SIO) {
/* 80 */       return getID().equals(((SIO)obj).getID());
/*    */     }
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\AbstractSIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */