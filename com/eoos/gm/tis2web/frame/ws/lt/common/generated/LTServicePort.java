package com.eoos.gm.tis2web.frame.ws.lt.common.generated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "LTServicePort", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface LTServicePort {
  @WebMethod
  @WebResult(name = "qualifierValuesResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  QualifierValuesResponse getQualifierValues(@WebParam(name = "qualifierValuesRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") QualifierValuesRequest paramQualifierValuesRequest) throws FatalFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "qualifierValidateResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  boolean validateQualifier(@WebParam(name = "qualifierValidateRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") QualifierValidateRequest paramQualifierValidateRequest) throws FatalFault, QualifierFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "opNumValResult", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  OpNumValResult validateOpNumber(@WebParam(name = "opNumValRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") OpNumValRequest paramOpNumValRequest) throws FatalFault, LaborOpFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "vehValResult", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  VehValResult validateVehDesc(@WebParam(name = "vehValRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") VehValRequest paramVehValRequest) throws FatalFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "setVehDescResult", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  SetVehDescResult setVehDesc(@WebParam(name = "setVehDescRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") SetVehDescRequest paramSetVehDescRequest) throws FatalFault, IdFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "getVehResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  GetVehResponse getVehDesc(@WebParam(name = "getVehRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") GetVehRequest paramGetVehRequest) throws FatalFault, IdFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "opList", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  OpList getLaborOperation(@WebParam(name = "opRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") OpRequest paramOpRequest) throws FatalFault, LaborOpFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "guiResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  GuiResponse startGui(@WebParam(name = "guiRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") GuiRequest paramGuiRequest) throws FatalFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "talResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  TalResponse getTal(@WebParam(name = "talRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") TalRequest paramTalRequest) throws FatalFault, IdFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "iclResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  IclResponse getIcl(@WebParam(name = "iclRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") IclRequest paramIclRequest) throws FatalFault, LaborOpFault, QualifierFault, SecurityFault, VehDescriptionFault;
  
  @WebMethod
  @WebResult(name = "resetTalResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  boolean resetTal(@WebParam(name = "resetTalRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") ResetTalRequest paramResetTalRequest) throws FatalFault, IdFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "logoutResponse", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params")
  boolean logout(@WebParam(name = "logoutRequest", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", partName = "params") LogoutRequest paramLogoutRequest) throws FatalFault, IdFault, SecurityFault;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\LTServicePort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */