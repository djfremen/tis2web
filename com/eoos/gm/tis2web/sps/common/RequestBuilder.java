package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.HardwareKeyRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.LoadArchiveRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingByFileRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ProgrammingDataDownloadContinuationRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VIT1Request;
import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.HardwareNumberInputRequest;
import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.util.List;

public interface RequestBuilder {
  SelectionRequest makeSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  SelectionRequest makeSelectionRequest(Attribute paramAttribute, List paramList, Value paramValue, RequestGroup paramRequestGroup);
  
  InputRequest makeInputRequest(Attribute paramAttribute, DisplayableValue paramDisplayableValue, RequestGroup paramRequestGroup);
  
  InputRequest makeControllerSecurityCodeRequest(Attribute paramAttribute, DisplayableValue paramDisplayableValue, RequestGroup paramRequestGroup, String paramString);
  
  DisplayRequest makeDisplayRequest(Attribute paramAttribute, RequestGroup paramRequestGroup, String paramString);
  
  InstructionDisplayRequest makeInstructionDisplayRequest(Attribute paramAttribute, String paramString, List paramList);
  
  VIT1Request makeVIT1Request(List paramList);
  
  VINReadRequest makeVINRequest();
  
  VINConfirmationRequest makeVINRequest(Attribute paramAttribute, Value paramValue, RequestGroup paramRequestGroup);
  
  ToolSelectionRequest makeToolSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  ProcessSelectionRequest makeProcessSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  ControllerSelectionRequest makeControllerSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  TypeSelectionRequest makeTypeSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  SequenceSelectionRequest makeSequenceSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  FunctionSelectionRequest makeFunctionSelectionRequest(Attribute paramAttribute, List paramList, RequestGroup paramRequestGroup);
  
  VINDisplayRequest makeVINDisplayRequest(Attribute paramAttribute, Value paramValue);
  
  HardwareSelectionRequest makeHardwareSelectionRequest(Attribute paramAttribute, String paramString, List paramList);
  
  HardwareSelectionRequest makeHardwareSelectionRequest(Attribute paramAttribute, String paramString1, List paramList, String paramString2);
  
  HardwareSelectionRequest makeHardwareSelectionRequest(Attribute paramAttribute, String paramString1, List paramList, String paramString2, String paramString3);
  
  ProgrammingDataSelectionRequest makeProgrammingDataSelectionRequest(Attribute paramAttribute, List paramList);
  
  ProgrammingDataSelectionRequest makeProgrammingDataSelectionRequest(Attribute paramAttribute, ProgrammingSequence paramProgrammingSequence);
  
  DisplaySummaryRequest makeDisplaySummaryRequest(Attribute paramAttribute);
  
  DisplaySummaryRequest makeDisplaySummaryRequest(Attribute paramAttribute, ProgrammingSequence paramProgrammingSequence);
  
  DisplaySummaryRequest makeDisplaySummaryRequest(Attribute paramAttribute, Summary paramSummary);
  
  DisplaySummaryRequest makeDisplaySummaryRequest(Attribute paramAttribute, List paramList);
  
  ProgrammingDataDownloadRequest makeProgrammingDataDownloadRequest(Attribute paramAttribute, List paramList);
  
  ProgrammingDataDownloadContinuationRequest makeProgrammingDataDownloadContinuationRequest();
  
  ReprogramDisplayRequest makeReprogramDisplayRequest();
  
  ReprogramRequest makeReprogramRequest(Attribute paramAttribute, ProgrammingData paramProgrammingData);
  
  ReprogramRequest makeReprogramRequest(Attribute paramAttribute, List paramList1, List paramList2, List paramList3, List paramList4, List paramList5);
  
  ReprogramRequest makeReprogramRequest(Attribute paramAttribute, List paramList1, List paramList2, List paramList3, List paramList4);
  
  ProgrammingByFileRequest makeProgrammingByFileRequest();
  
  LoadArchiveRequest makeLoadArchiveRequest();
  
  HardwareKeyRequest makeHardwareKeyRequest();
  
  HardwareNumberInputRequest createHWNumberInputRequest();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\RequestBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */