package com.eoos.gm.tis2web.ctoc.service;

import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;

public interface LTCTOCService extends CTOCService, CfgProvider, VINResolver {
  boolean supports(IConfiguration paramIConfiguration);
  
  public static interface Retrieval {
    LTCTOCService getLTCTOCService();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\LTCTOCService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */