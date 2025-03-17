/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgrammingDataSelectionRequestImpl
/*    */   extends SelectionRequestImpl
/*    */   implements ProgrammingDataSelectionRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private List modules;
/*    */   private ProgrammingSequence sequence;
/*    */   
/*    */   public ProgrammingDataSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List modules) {
/* 23 */     super(requestGroup, attribute, modules);
/* 24 */     this.modules = modules;
/*    */   }
/*    */   
/*    */   public List getModules() {
/* 28 */     return this.modules;
/*    */   }
/*    */   
/*    */   public ProgrammingDataSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, ProgrammingSequence sequence) {
/* 32 */     super(requestGroup, attribute, null);
/* 33 */     this.sequence = sequence;
/*    */   }
/*    */   
/*    */   public ProgrammingSequence getProgrammingSequence() {
/* 37 */     return this.sequence;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\ProgrammingDataSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */