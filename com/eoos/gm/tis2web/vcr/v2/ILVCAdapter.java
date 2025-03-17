/*    */ package com.eoos.gm.tis2web.vcr.v2;
/*    */ public interface ILVCAdapter extends CfgProvider, VINResolver { public static final String SALESMAKE = "Make"; public static final String MODEL = "Model"; public static final String MODELYEAR = "ModelYear"; public static final String ENGINE = "Engine"; public static final String TRANSMISSION = "Transmission"; Retrieval createRetrievalImpl(); List getSalesmakeDomain(); List getModelDomain(); List getEngineDomain(); List getTransmissionDomain(); VC getVC(); Object getVehicleOptionsDialog(String paramString, List paramList, ReturnHandler paramReturnHandler); VCRAttribute makeAttribute(VCValue paramVCValue); VCRAttribute makeAttribute(int paramInt1, int paramInt2); VCRTerm makeTerm(); VCRTerm makeTerm(VCValue paramVCValue); VCRTerm makeTerm(VCRAttribute paramVCRAttribute); VCRExpression makeExpression();
/*    */   VCR makeVCR();
/*    */   VCR makeVCR(VCRExpression paramVCRExpression);
/*    */   VCR makeVCR(VCRTerm paramVCRTerm);
/*    */   VCR makeVCR(VCRAttribute paramVCRAttribute);
/*    */   VCR makeVCR(VCValue paramVCValue);
/*    */   VCR makeVCR(int paramInt);
/*    */   VCR makeVCR(String paramString);
/*    */   VCR makeVCR(VCConfiguration paramVCConfiguration);
/*    */   VCR makeVCR(VCConfiguration paramVCConfiguration, VCValue paramVCValue1, VCValue paramVCValue2);
/*    */   Map<Integer, VCR> getVCRs(Collection paramCollection);
/*    */   boolean isNullVCR(VCR paramVCR);
/*    */   IVehicleOptionExpression createVehicleOptionExpression();
/*    */   VCR createConstraintVCR();
/*    */   List checkOptionRestriction(List paramList, VCR paramVCR1, VCR paramVCR2, VCR paramVCR3);
/*    */   VCR createVCR(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Locale paramLocale);
/*    */   VCR toVCR(IConfiguration paramIConfiguration);
/*    */   VCR toVCR(List paramList);
/*    */   Collection toConfiguration(VCR paramVCR);
/*    */   CfgDataProvider asCfgDataProvider();
/*    */   Collection getConfigurations_Legacy();
/*    */   public static interface ReturnHandler { Object onOK();
/*    */     Object onCancel(); }
/*    */   public static interface Retrieval { ILVCAdapter getLVCAdapter();
/*    */     public static final class RI implements Retrieval { public RI(ILVCAdapter adapter) {
/* 27 */         this.adapter = adapter;
/*    */       }
/*    */       private ILVCAdapter adapter;
/*    */       public ILVCAdapter getLVCAdapter() {
/* 31 */         return this.adapter;
/*    */       } }
/*    */      }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\v2\ILVCAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */