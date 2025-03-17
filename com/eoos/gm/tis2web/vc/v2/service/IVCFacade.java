package com.eoos.gm.tis2web.vc.v2.service;

import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vc.v2.value.Make;
import com.eoos.gm.tis2web.vc.v2.vin.VIN;
import com.eoos.html.HtmlCodeRenderer;
import java.util.Collection;

public interface IVCFacade {
  void addObserver(VehicleCfgStorage.Observer paramObserver);
  
  VIN asVIN(String paramString) throws VIN.InvalidVINException;
  
  IConfiguration createConfiguration(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  IConfiguration getCfg();
  
  String getCurrentSalesmake();
  
  String getCurrentModel();
  
  String getCurrentModelyear();
  
  String getCurrentEngine();
  
  String getCurrentTransmission();
  
  String getCurrentVCDisplay(boolean paramBoolean);
  
  String getCurrentVIN();
  
  HtmlCodeRenderer getDialog(VCService.DialogCallback paramDialogCallback);
  
  String getDisplayValue(Object paramObject);
  
  Collection getSalesmakeDomain();
  
  boolean isCurrentSalesmake(String paramString);
  
  void removeObserver(VehicleCfgStorage.Observer paramObserver);
  
  void setSalesmake(String paramString);
  
  void setSalesmake(Make paramMake);
  
  void storeCfg(IConfiguration paramIConfiguration, VIN paramVIN);
  
  void setVIN(String paramString) throws VIN.InvalidVINException;
  
  Collection getValidModels(IConfiguration paramIConfiguration);
  
  Collection getValidModelyears(IConfiguration paramIConfiguration);
  
  Collection getValidTransmissions(IConfiguration paramIConfiguration);
  
  Collection getValidEngines(IConfiguration paramIConfiguration);
  
  VIN getVIN();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\IVCFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */