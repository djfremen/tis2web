package com.eoos.gm.tis2web.sps.server.implementation.calibinfo;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
import java.util.List;
import java.util.Set;

public interface Navigation {
  Set getParts(Module paramModule);
  
  Part getRootPart(Module paramModule);
  
  List getChildren(Part paramPart);
  
  Part getParent(Part paramPart);
  
  boolean isSelectable(Part paramPart);
  
  Part getSelectedPart(Module paramModule);
  
  void setSelectedPart(Module paramModule, Part paramPart);
  
  List getLeafParts(Module paramModule);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\Navigation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */