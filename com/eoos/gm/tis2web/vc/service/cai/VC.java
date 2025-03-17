package com.eoos.gm.tis2web.vc.service.cai;

import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.Collection;
import java.util.List;

public interface VC {
  public static final String LOCALE = "Locale";
  
  public static final String MAKE = "Make";
  
  public static final String MODEL = "Model";
  
  public static final String MODELYEAR = "ModelYear";
  
  public static final String VEHICLE = "Vehicle";
  
  public static final String ENGINE = "Engine";
  
  public static final String TRANSMISSION = "Transmission";
  
  public static final String VIN_WMI = "VIN WMI";
  
  public static final String VIN_MODELYEAR = "VIN ModelYear";
  
  public static final String SCDS_MAKE = "SCDS Make";
  
  public static final String SCDS_MODELCODE = "SCDS Model Code";
  
  public static final String SCDS_TRANSMISSION = "SCDS Transmission";
  
  public static final String SCDS_VEHICLE = "SCDS Vehicle";
  
  public static final String SCDS_VCC = "SCDS Vehicle Configuration";
  
  public static final String DDB_ELECTRONIC_SYSTEM = "DDB Electronic System";
  
  public static final String DDB_ELECTRONIC_SYSTEM_GROUP = "DDB Electronic System Group";
  
  public static final String DDB_SPECIAL_CONSTRAINT = "DDB Special Constraint";
  
  public static final String DDB_SPECIAL_CONSTRAINT_GROUP = "DDB Special Constraint Group";
  
  public static final String DRIVE = "ESI Drive";
  
  public static final String SIT = "SIT";
  
  Collection getSalesMakes();
  
  VCDomain getDomain(int paramInt);
  
  VCDomain getDomain(Integer paramInteger);
  
  VCDomain getDomain(String paramString);
  
  String getLabel(VCValue paramVCValue);
  
  String getLabel(Integer paramInteger, VCValue paramVCValue);
  
  VCValue getValue(Integer paramInteger, VCDomain paramVCDomain, String paramString);
  
  VCConfiguration getConfiguration(VCR paramVCR);
  
  Collection getConfigurations();
  
  VCConfiguration getConfiguration(Integer paramInteger);
  
  VCConfiguration getConfiguration(VCMake paramVCMake, VCModel paramVCModel, VCModelYear paramVCModelYear);
  
  String getDisplay(VCConfiguration paramVCConfiguration);
  
  String getDisplayModel(VCR paramVCR);
  
  Collection getConfigurations(VCR paramVCR);
  
  Collection getConfigurations(Collection paramCollection, VCR paramVCR);
  
  Collection getSalesMakes(Collection paramCollection);
  
  Collection getModels(Collection paramCollection);
  
  Collection getModelYears(Collection paramCollection);
  
  Collection getEngines(VCR paramVCR, Collection paramCollection);
  
  Collection getTransmissions(VCR paramVCR, Collection paramCollection);
  
  VINDecoder getVINDecoder();
  
  List getVehicleOptions(Integer paramInteger);
  
  List getAttributes(String paramString, VCR paramVCR);
  
  DBVersionInformation getVersionInfo();
  
  VCValue map(VCValue paramVCValue, String paramString);
  
  List listAttributes(VCDomain paramVCDomain, VCR paramVCR);
  
  VCValue getLabelValue(Integer paramInteger, VCRDomain paramVCRDomain, String paramString);
  
  Collection toConfiguration(VCR paramVCR);
  
  VCR toVCR(IConfiguration paramIConfiguration);
  
  VCR toVCR(List paramList);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VC.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */