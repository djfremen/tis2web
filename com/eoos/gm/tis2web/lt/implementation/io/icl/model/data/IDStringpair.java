/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDStringpair
/*     */   extends Pair
/*     */ {
/*     */   public IDStringpair() {}
/*     */   
/*     */   public IDStringpair(Integer o, String o2) {
/*  74 */     super(Long.valueOf(o.intValue()), o2);
/*     */   }
/*     */   
/*     */   public IDStringpair(int o, String o2) {
/*  78 */     super(Long.valueOf(o), o2);
/*     */   }
/*     */   
/*     */   public IDStringpair(Long o, String o2) {
/*  82 */     super(o, o2);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDStringpair(long ov, String o2) {
/*  87 */     setFirst(Long.valueOf(ov));
/*  88 */     this.second = o2;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  92 */     return (String)getSecond();
/*     */   }
/*     */   
/*     */   public void setValue(String v) {
/*  96 */     this.second = v;
/*     */   }
/*     */   
/*     */   public Integer getID() {
/* 100 */     return (Integer)getFirst();
/*     */   }
/*     */   
/*     */   public void setID(Integer ID) {
/* 104 */     this.first = ID;
/*     */   }
/*     */   
/*     */   public void setID(long ID) {
/* 108 */     this.first = Long.valueOf(ID);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\data\IDStringpair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */