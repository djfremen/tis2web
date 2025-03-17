/*     */ package com.eoos.gm.tis2web.sps.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataDownloadRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*     */ import com.eoos.gm.tis2web.sps.common.SequenceSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ToolSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.TypeSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.VINConfirmationRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.VINReadRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HardwareKeyRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.LoadArchiveRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingByFileRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingDataDownloadContinuationRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VIT1Request;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.LoadArchiveRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ProgrammingByFileRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ReprogramFunctionSequenceRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ReprogramRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ReprogramSequenceRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.VIT1RequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HardwareKeyRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HardwareNumberInputRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HtmlDisplayRequestCodeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.InputRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.InstructionDisplayRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.ReprogramDisplayRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionDefaultRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.List;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class RequestBuilderImpl
/*     */   implements RequestBuilder {
/*     */   private static class InputRequestDVRImpl
/*     */     extends InputRequestImpl
/*     */     implements DefaultValueRetrieval {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private Value defaultValue;
/*     */     
/*     */     public InputRequestDVRImpl(RequestGroup requestGroup, Attribute attribute, Value defaultValue) {
/*  64 */       super(requestGroup, attribute);
/*  65 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/*     */     public Value getDefaultValue() {
/*  69 */       return this.defaultValue;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class VINConfirmationRequestDVRImpl
/*     */     extends VINConfirmationRequestImpl
/*     */     implements DefaultValueRetrieval {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private Value defaultValue;
/*     */     
/*     */     public VINConfirmationRequestDVRImpl(RequestGroup requestGroup, Attribute attribute, Value defaultValue) {
/*  80 */       super(requestGroup, attribute);
/*  81 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/*     */     public Value getDefaultValue() {
/*  85 */       return this.defaultValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectionRequest makeSelectionRequest(Attribute attr, List valueList, Value defaultSelection, RequestGroup requestGroup) {
/*  95 */     return (SelectionRequest)new SelectionDefaultRequestImpl(requestGroup, attr, valueList, defaultSelection);
/*     */   }
/*     */   
/*     */   public SelectionRequest makeSelectionRequest(Attribute attr, List valueList, RequestGroup requestGroup) {
/*  99 */     return (SelectionRequest)new SelectionRequestImpl(requestGroup, attr, valueList);
/*     */   }
/*     */   
/*     */   public InputRequest makeInputRequest(Attribute attr, DisplayableValue defaultValue, RequestGroup groupID) {
/* 103 */     if (defaultValue == null) {
/* 104 */       return (InputRequest)new InputRequestImpl(groupID, attr);
/*     */     }
/* 106 */     return (InputRequest)new InputRequestDVRImpl(groupID, attr, (Value)defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputRequest makeControllerSecurityCodeRequest(Attribute attr, DisplayableValue defaultValue, RequestGroup groupID, String controllerVIN) {
/* 111 */     if (defaultValue == null) {
/* 112 */       return (InputRequest)new ControllerSecurityCodeRequestImpl(groupID, attr, controllerVIN);
/*     */     }
/* 114 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DisplayRequest makeDisplayRequest(Attribute attr, RequestGroup groupID, String content) {
/* 119 */     return (DisplayRequest)new HtmlDisplayRequestCodeImpl(groupID, attr, CommonValue.OK, content);
/*     */   }
/*     */   
/*     */   public InstructionDisplayRequest makeInstructionDisplayRequest(Attribute attr, String content, List htmls) {
/* 123 */     return (InstructionDisplayRequest)new InstructionDisplayRequestImpl(null, attr, CommonValue.OK, content, htmls);
/*     */   }
/*     */   
/*     */   public VIT1Request makeVIT1Request(List rdata) {
/* 127 */     return (VIT1Request)new VIT1RequestImpl(rdata);
/*     */   }
/*     */   
/*     */   public VINReadRequest makeVINRequest() {
/* 131 */     return new VINReadRequestImpl((RequestGroup)null, (Attribute)null, (Value)null);
/*     */   }
/*     */   
/*     */   public VINConfirmationRequest makeVINRequest(Attribute attr, Value defaultValue, RequestGroup groupID) {
/* 135 */     if (defaultValue == null) {
/* 136 */       return new VINConfirmationRequestImpl(groupID, attr);
/*     */     }
/* 138 */     return new VINConfirmationRequestDVRImpl(groupID, attr, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolSelectionRequest makeToolSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 144 */     return new ToolSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public ProcessSelectionRequest makeProcessSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 148 */     return new ProcessSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public ControllerSelectionRequest makeControllerSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 152 */     return new ControllerSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public TypeSelectionRequest makeTypeSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 156 */     return new TypeSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public VINDisplayRequest makeVINDisplayRequest(Attribute attr, Value defaultValue) {
/* 160 */     return new VINDisplayRequestImpl((RequestGroup)null, attr, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public HardwareSelectionRequest makeHardwareSelectionRequest(Attribute attribute, String hardwareDescription, List hardwareList) {
/* 165 */     return new HardwareSelectionRequestImpl((RequestGroup)null, attribute, hardwareList, hardwareDescription);
/*     */   }
/*     */ 
/*     */   
/*     */   public HardwareSelectionRequest makeHardwareSelectionRequest(Attribute attribute, String hardwareDescription, List hardwareList, String controller) {
/* 170 */     return new HardwareSelectionRequestImpl((RequestGroup)null, attribute, hardwareList, hardwareDescription, controller);
/*     */   }
/*     */ 
/*     */   
/*     */   public HardwareSelectionRequest makeHardwareSelectionRequest(Attribute attribute, String hardwareDescription, List hardwareList, String controller, String label) {
/* 175 */     return new HardwareSelectionRequestImpl((RequestGroup)null, attribute, hardwareList, hardwareDescription, controller, label);
/*     */   }
/*     */ 
/*     */   
/*     */   public ProgrammingDataSelectionRequest makeProgrammingDataSelectionRequest(Attribute attribute, List moduleList) {
/* 180 */     return new ProgrammingDataSelectionRequestImpl((RequestGroup)null, attribute, moduleList);
/*     */   }
/*     */   
/*     */   public ProgrammingDataSelectionRequest makeProgrammingDataSelectionRequest(Attribute attribute, ProgrammingSequence sequence) {
/* 184 */     return new ProgrammingDataSelectionRequestImpl((RequestGroup)null, attribute, sequence);
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequest makeDisplaySummaryRequest(Attribute attribute, ProgrammingSequence sequence) {
/* 188 */     return new DisplaySummaryRequestImpl((RequestGroup)null, attribute, CommonValue.OK, sequence);
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequest makeDisplaySummaryRequest(Attribute attribute) {
/* 192 */     return new DisplaySummaryRequestImpl((RequestGroup)null, attribute, CommonValue.OK, (TableModel)null, (TableModel)null);
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequest makeDisplaySummaryRequest(Attribute attribute, Summary summary) {
/* 196 */     return new DisplaySummaryRequestImpl((RequestGroup)null, attribute, CommonValue.OK, summary);
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequest makeDisplaySummaryRequest(Attribute attribute, List sequence) {
/* 200 */     return new DisplaySummaryRequestImpl((RequestGroup)null, attribute, CommonValue.OK, sequence);
/*     */   }
/*     */   
/*     */   public ProgrammingDataDownloadRequest makeProgrammingDataDownloadRequest(Attribute attribute, List blobs) {
/* 204 */     return new ProgrammingDataDownloadRequestImpl(blobs);
/*     */   }
/*     */   
/*     */   public ProgrammingDataDownloadContinuationRequest makeProgrammingDataDownloadContinuationRequest() {
/* 208 */     return new ProgrammingDataDownloadContinuationRequestImpl();
/*     */   }
/*     */   
/*     */   public ReprogramDisplayRequest makeReprogramDisplayRequest() {
/* 212 */     return (ReprogramDisplayRequest)new ReprogramDisplayRequestImpl();
/*     */   }
/*     */   
/*     */   public ReprogramRequest makeReprogramRequest(Attribute attribute, ProgrammingData pdata) {
/* 216 */     ReprogramRequestImpl request = new ReprogramRequestImpl(pdata);
/* 217 */     return (ReprogramRequest)request;
/*     */   }
/*     */   
/*     */   public ReprogramRequest makeReprogramRequest(Attribute attribute, List controllers, List plist, List type4, List instructions, List onFailure) {
/* 221 */     ReprogramSequenceRequestImpl request = new ReprogramSequenceRequestImpl(controllers, plist, type4, instructions, onFailure);
/* 222 */     return (ReprogramRequest)request;
/*     */   }
/*     */   
/*     */   public ProgrammingByFileRequest makeProgrammingByFileRequest() {
/* 226 */     return (ProgrammingByFileRequest)new ProgrammingByFileRequestImpl();
/*     */   }
/*     */   
/*     */   public LoadArchiveRequest makeLoadArchiveRequest() {
/* 230 */     return (LoadArchiveRequest)new LoadArchiveRequestImpl();
/*     */   }
/*     */   
/*     */   public HardwareKeyRequest makeHardwareKeyRequest() {
/* 234 */     return (HardwareKeyRequest)new HardwareKeyRequestImpl((RequestGroup)null, CommonAttribute.HARDWARE_KEY);
/*     */   }
/*     */   
/*     */   public HardwareNumberInputRequest createHWNumberInputRequest() {
/* 238 */     return new HardwareNumberInputRequest(null, CommonAttribute.HARDWARE_NUMBER);
/*     */   }
/*     */   
/*     */   public SequenceSelectionRequest makeSequenceSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 242 */     return new SequenceSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public FunctionSelectionRequest makeFunctionSelectionRequest(Attribute attr, List valueList, RequestGroup groupID) {
/* 246 */     return new FunctionSelectionRequestImpl(groupID, attr, valueList);
/*     */   }
/*     */   
/*     */   public ReprogramRequest makeReprogramRequest(Attribute attribute, List functions, List plist, List preInstructions, List postInstructions) {
/* 250 */     return (ReprogramRequest)new ReprogramFunctionSequenceRequestImpl(functions, plist, preInstructions, postInstructions);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\RequestBuilderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */