package com.eoos.gm.tis2web.lt.v2;

import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
import com.eoos.gm.tis2web.fts.service.FTSService;
import com.eoos.gm.tis2web.lt.service.cai.LT;
import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;

public interface LTDataAdapter extends CfgProviderRetrieval, VINResolverRetrieval, LT.Retrieval, LTCTOCService.Retrieval, ILVCAdapter.Retrieval, FTSService.Retrieval {
  boolean supports(IConfiguration paramIConfiguration);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTDataAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */