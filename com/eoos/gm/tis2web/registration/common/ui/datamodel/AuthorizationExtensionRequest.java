package com.eoos.gm.tis2web.registration.common.ui.datamodel;

import java.util.Set;

public interface AuthorizationExtensionRequest extends AuthorizationRequest {
  AuthorizationRequest getPrimaryRequest();
  
  Set getAdditionalSubscriptions();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\AuthorizationExtensionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */