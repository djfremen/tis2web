package com.eoos.gm.tis2web.frame.ws.e5.common.gen;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "E5ServicePort", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface E5ServicePort {
  @WebMethod
  @WebResult(name = "locales", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param")
  LocaleList getLocales(@WebParam(name = "getLocales", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param") GetLocales paramGetLocales) throws FatalFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "makes", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param")
  Str100List getMakes(@WebParam(name = "getMakes", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param") GetMakes paramGetMakes) throws FatalFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "vehDetails", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param")
  VehDetails resolveVin(@WebParam(name = "resolveVin", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param") ResolveVin paramResolveVin) throws FatalFault, InvParamFault, MissingMakeFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "familyDetails", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param")
  FamilyList getFamilyDetails(@WebParam(name = "getFamilyDetails", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param") GetFamilyDetails paramGetFamilyDetails) throws FatalFault, InvParamFault, SecurityFault;
  
  @WebMethod
  @WebResult(name = "Document", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param")
  Document getDocument(@WebParam(name = "getDocument", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types", partName = "param") GetDocument paramGetDocument) throws FatalFault, InvParamFault, MissingDocFault, SecurityFault;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\E5ServicePort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */