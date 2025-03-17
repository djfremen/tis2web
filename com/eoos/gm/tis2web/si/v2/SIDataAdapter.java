package com.eoos.gm.tis2web.si.v2;

import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
import com.eoos.gm.tis2web.fts.service.FTSService;
import com.eoos.gm.tis2web.si.service.cai.SI;
import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;

public interface SIDataAdapter extends CfgProviderRetrieval, VINResolverRetrieval, SI.Retrieval, SICTOCService.Retrieval, ILVCAdapter.Retrieval, FTSService.Retrieval {
  boolean supports(IConfiguration paramIConfiguration);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SIDataAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */