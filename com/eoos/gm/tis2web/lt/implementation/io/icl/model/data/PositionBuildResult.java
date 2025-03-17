/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.data;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Positions;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PositionsImpl;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class PositionBuildResult
/*     */ {
/*     */   private boolean bullet = false;
/*     */   private boolean clpus = false;
/*  76 */   private List numbersAndFootnotes = new LinkedList();
/*     */ 
/*     */   
/*  79 */   private Positions positions = (Positions)new PositionsImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBullet() {
/*  87 */     return this.bullet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBullet(boolean bullet) {
/*  97 */     this.bullet = bullet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClpus() {
/* 106 */     return this.clpus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClpus(boolean clpus) {
/* 116 */     this.clpus = clpus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getNumbersAndFootnotes() {
/* 125 */     return this.numbersAndFootnotes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumbersAndFootnotes(List numbersAndFootnotes) {
/* 135 */     this.numbersAndFootnotes = numbersAndFootnotes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Positions getPositions() {
/* 144 */     return this.positions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositions(Positions positions) {
/* 154 */     this.positions = positions;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\data\PositionBuildResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */