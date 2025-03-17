/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.db.DBMS;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.db.ICLCache;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.MajorOperation;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CE2value;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CLElement;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.CheckList;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Footnote;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.Pair;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.data.PositionBuildResult;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.BulletType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.CplusType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.EngineType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Footer;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.FooterType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Header;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.HeaderType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ModelType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Parameter;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Position;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.PositionsType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ReleaseType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Serviceplan;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.TransmissionType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.VINType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.BulletImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.CplusImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.EngineImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.FooterImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.FootnoteImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.HeaderImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ModelImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ParameterImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PositionImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ReleaseImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ServiceplanImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.StdFootnoteImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.TransmissionImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.VINImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ICL {
/*  48 */   protected ICLCache cache = null;
/*     */   
/*     */   public ICL(IDatabaseLink icldb) throws Exception {
/*  51 */     DBMS dbms = new DBMS(icldb);
/*  52 */     this.cache = new ICLCache(dbms);
/*     */   }
/*     */   
/*     */   public Integer getCheckListID(String country, String model, String modelYear, String engine, String majorOperation) {
/*  56 */     return this.cache.getCheckListID(country, model, modelYear, engine, majorOperation);
/*     */   }
/*     */   
/*     */   public List getCheckListAttributes(String country, String model, String modelYear, String engine, String majorOperation) {
/*  60 */     return this.cache.getCheckListAttributes(country, model, modelYear, engine, majorOperation);
/*     */   }
/*     */   
/*     */   public List getCheckListModelYears(String country, String model, String engine, String majorOperation) {
/*  64 */     return this.cache.getCheckListModelYears(country, model, engine, majorOperation);
/*     */   }
/*     */   
/*     */   public List getCheckListEngines(String country, String model, String modelYear, String majorOperation) {
/*  68 */     return this.cache.getCheckListEngines(country, model, modelYear, majorOperation);
/*     */   }
/*     */   
/*     */   public Serviceplan getServiceplan(Locale locale, Integer clid, String majorOperation, String make, String model, String modelYear, String engine, String transmission, String vin) throws Exception {
/*  72 */     Integer lgID = this.cache.getLanguageID(locale);
/*  73 */     MajorOperation mo = this.cache.getMajorOperation(majorOperation);
/*  74 */     ServiceplanImpl serviceplanImpl = new ServiceplanImpl();
/*  75 */     serviceplanImpl.setLanguage(locale.toString());
/*  76 */     serviceplanImpl.setHeader((HeaderType)buildHeader(lgID.intValue(), mo, make, model, modelYear, engine, transmission, vin));
/*  77 */     CheckList oCheck = this.cache.getCheckList(clid.intValue(), lgID.intValue());
/*  78 */     if (oCheck != null) {
/*  79 */       PositionBuildResult oRes = buildPositions(lgID.intValue(), oCheck);
/*  80 */       serviceplanImpl.setPositions((PositionsType)oRes.getPositions());
/*  81 */       serviceplanImpl.setFooter((FooterType)buildFooter(lgID.intValue(), oCheck, oRes.isBullet(), oRes.isClpus(), oRes.getNumbersAndFootnotes()));
/*  82 */       return (Serviceplan)serviceplanImpl;
/*     */     } 
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   public String getServiceTypeXSL(Locale locale, String majorOperation) throws Exception {
/*  88 */     Integer lgID = this.cache.getLanguageID(locale);
/*  89 */     MajorOperation mo = this.cache.getMajorOperation(majorOperation);
/*  90 */     if (mo.getServiceType() != null) {
/*  91 */       return this.cache.getServiceTypeXSL(mo.getServiceType(), lgID.intValue());
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Header buildHeader(int lgID, MajorOperation mo, String make, String model, String modelYear, String engine, String transmission, String vin) {
/*  98 */     HeaderImpl headerImpl = new HeaderImpl();
/*  99 */     headerImpl.setMake(make);
/* 100 */     headerImpl.setTitle(this.cache.getConstant("TITLE", lgID));
/* 101 */     headerImpl.setCustomer(this.cache.getConstant("CUSTOMER", lgID));
/* 102 */     headerImpl.setOrderno(this.cache.getConstant("ORDERNO", lgID));
/* 103 */     headerImpl.setInspType(mo.getDescription(lgID));
/* 104 */     if (mo.getServiceType() != null) {
/* 105 */       headerImpl.setServType(this.cache.getServiceType(mo.getServiceType(), lgID));
/*     */     }
/*     */ 
/*     */     
/* 109 */     if (mo.getDriverType() != null) {
/* 110 */       headerImpl.setDrvType(this.cache.getDriverType(mo.getDriverType(), lgID));
/*     */     }
/* 112 */     ReleaseImpl releaseImpl = new ReleaseImpl();
/* 113 */     releaseImpl.setLabel(this.cache.getConstant("RELEASE", lgID));
/* 114 */     releaseImpl.setValue(modelYear);
/* 115 */     headerImpl.setRelease((ReleaseType)releaseImpl);
/* 116 */     headerImpl.setDate(this.cache.getConstant("DATE", lgID));
/* 117 */     headerImpl.setPhone(this.cache.getConstant("PHONE", lgID));
/* 118 */     headerImpl.setKm(this.cache.getConstant("KM", lgID));
/* 119 */     headerImpl.setNumberplate(this.cache.getConstant("NUMBERPLATE", lgID));
/*     */     
/* 121 */     ModelImpl modelImpl = new ModelImpl();
/* 122 */     modelImpl.setLabel(this.cache.getConstant("MODEL", lgID));
/* 123 */     modelImpl.setValue(model);
/* 124 */     headerImpl.setModel((ModelType)modelImpl);
/*     */     
/* 126 */     EngineImpl engineImpl = new EngineImpl();
/* 127 */     engineImpl.setLabel(this.cache.getConstant("ENGINE", lgID));
/* 128 */     engineImpl.setValue(engine);
/* 129 */     headerImpl.setEngine((EngineType)engineImpl);
/*     */     
/* 131 */     TransmissionImpl transmissionImpl = new TransmissionImpl();
/* 132 */     transmissionImpl.setLabel(this.cache.getConstant("TRANSMISSION", lgID));
/* 133 */     transmissionImpl.setValue(transmission);
/* 134 */     headerImpl.setTransmission((TransmissionType)transmissionImpl);
/*     */     
/* 136 */     VINImpl vINImpl = new VINImpl();
/* 137 */     vINImpl.setLabel(this.cache.getConstant("VIN", lgID));
/* 138 */     vINImpl.setValue(vin);
/* 139 */     headerImpl.setVIN((VINType)vINImpl);
/*     */     
/* 141 */     return (Header)headerImpl;
/*     */   }
/*     */   
/*     */   protected PositionBuildResult buildPositions(int lgID, CheckList oCheck) {
/* 145 */     PositionBuildResult oRes = new PositionBuildResult();
/*     */     
/* 147 */     oRes.getPositions().setTitle(this.cache.getConstant("POSITIONHEADER", lgID));
/* 148 */     oRes.getPositions().setState(this.cache.getConstant("STATE", lgID));
/*     */     
/* 150 */     int iCurrentFootNodeNr = 1;
/* 151 */     HashMap<Object, Object> oFIDs = new HashMap<Object, Object>();
/* 152 */     for (Iterator<CLElement> it = oCheck.getElements().iterator(); it.hasNext(); ) {
/* 153 */       CLElement oEl = it.next();
/* 154 */       PositionImpl positionImpl = new PositionImpl();
/* 155 */       oRes.getPositions().getPosition().add(positionImpl);
/* 156 */       positionImpl.setText(oEl.getDescription().getDescription());
/* 157 */       if (oEl.getComment() != null) {
/* 158 */         positionImpl.setComment(oEl.getComment().getDescription());
/*     */       }
/* 160 */       positionImpl.setNr((oEl.getSpLink() != null) ? oEl.getSpLink() : "");
/* 161 */       if (oEl.isAi_flag() || oEl.isEc_flag()) {
/* 162 */         StdFootnoteImpl stdFootnoteImpl = new StdFootnoteImpl();
/* 163 */         positionImpl.setStdFootnote((StdFootnoteType)stdFootnoteImpl);
/* 164 */         if (oEl.isAi_flag()) {
/* 165 */           oRes.setClpus(true);
/* 166 */           stdFootnoteImpl.setCplus((CplusType)new CplusImpl());
/*     */         } 
/* 168 */         if (oEl.isEc_flag()) {
/* 169 */           oRes.setBullet(true);
/* 170 */           stdFootnoteImpl.setBullet((BulletType)new BulletImpl());
/*     */         } 
/*     */       } 
/* 173 */       if (oEl.getAttributes() != null) {
/* 174 */         addAttributes((Position)positionImpl, oEl);
/*     */       }
/* 176 */       if (oEl.getFootnode() != null) {
/* 177 */         iCurrentFootNodeNr = addFootnotes(oEl, (Position)positionImpl, iCurrentFootNodeNr, oFIDs, oRes.getNumbersAndFootnotes());
/*     */       }
/*     */     } 
/*     */     
/* 181 */     return oRes;
/*     */   }
/*     */   
/*     */   protected void addAttributes(Position p, CLElement oEl) {
/* 185 */     HashMap<Object, Object> oAttr2Parameter = new HashMap<Object, Object>();
/* 186 */     for (Iterator<CE2value> it = oEl.getAttributes().iterator(); it.hasNext(); ) {
/* 187 */       ParameterImpl parameterImpl; CE2value oV = it.next();
/* 188 */       Long lID = Long.valueOf(oV.getAttribute().getID());
/* 189 */       Parameter oP = null;
/* 190 */       boolean bNew = true;
/* 191 */       if (oAttr2Parameter.containsKey(lID)) {
/* 192 */         oP = (Parameter)oAttr2Parameter.get(lID);
/* 193 */         bNew = false;
/*     */       } else {
/* 195 */         parameterImpl = new ParameterImpl();
/* 196 */         parameterImpl.setName(oV.getAttribute().getDescription().getDescription());
/* 197 */         oAttr2Parameter.put(lID, parameterImpl);
/*     */       } 
/* 199 */       parameterImpl.getValue().add(oV.getValue().getDescription().getDescription());
/* 200 */       if (bNew) {
/* 201 */         p.getParameter().add(parameterImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int addFootnotes(CLElement oEl, Position p, int iCurrentFootNodeNr, Map<Long, String> oFIDs, List<Pair> lNumberAndFootnode) {
/* 209 */     for (Iterator<Footnote> it = oEl.getFootnode().iterator(); it.hasNext(); ) {
/* 210 */       String oSign; Footnote oFE = it.next();
/* 211 */       FootnoteImpl footnoteImpl = new FootnoteImpl();
/*     */       
/* 213 */       Long lID = Long.valueOf(oFE.getID());
/*     */ 
/*     */       
/* 216 */       if (!oFIDs.containsKey(lID)) {
/* 217 */         oSign = String.valueOf(iCurrentFootNodeNr++) + ")";
/* 218 */         oFIDs.put(lID, oSign);
/* 219 */         lNumberAndFootnode.add(new Pair(oSign, oFE.getDescription().getDescription()));
/*     */       } else {
/* 221 */         oSign = oFIDs.get(lID);
/*     */       } 
/* 223 */       footnoteImpl.setSign(oSign);
/* 224 */       p.getFootnote().add(footnoteImpl);
/*     */     } 
/*     */     
/* 227 */     return iCurrentFootNodeNr;
/*     */   }
/*     */   
/*     */   protected Footer buildFooter(int lgID, CheckList oCheck, boolean bHasbullet, boolean bHascplus, List lFootnotesFromElem) {
/* 231 */     FooterImpl footerImpl = new FooterImpl();
/* 232 */     footerImpl.setConfirmation(this.cache.getConstant("CONFIRMATION", lgID));
/* 233 */     footerImpl.setDefects(this.cache.getConstant("DEFECTS", lgID));
/* 234 */     footerImpl.setMechanic(this.cache.getConstant("MECHANIC", lgID));
/* 235 */     footerImpl.setCheck(this.cache.getConstant("CHECK", lgID));
/* 236 */     footerImpl.setWorkshop(this.cache.getConstant("WORKSHOP", lgID));
/*     */     
/* 238 */     if (bHasbullet) {
/* 239 */       StdFootnoteImpl stdFootnoteImpl = new StdFootnoteImpl();
/* 240 */       stdFootnoteImpl.setBullet((BulletType)new BulletImpl());
/* 241 */       stdFootnoteImpl.setDescription(this.cache.getConstant("HEAVYUSAGE", lgID));
/* 242 */       footerImpl.getStdFootnote().add(stdFootnoteImpl);
/*     */     } 
/*     */     
/* 245 */     if (bHascplus) {
/* 246 */       StdFootnoteImpl stdFootnoteImpl = new StdFootnoteImpl();
/* 247 */       stdFootnoteImpl.setCplus((CplusType)new CplusImpl());
/* 248 */       stdFootnoteImpl.setDescription(this.cache.getConstant("ADDITEM", lgID));
/* 249 */       footerImpl.getStdFootnote().add(stdFootnoteImpl);
/*     */     } 
/*     */     
/* 252 */     if (lFootnotesFromElem != null) {
/* 253 */       for (Iterator<Pair> it = lFootnotesFromElem.iterator(); it.hasNext(); ) {
/* 254 */         Pair oP = it.next();
/* 255 */         FootnoteImpl footnoteImpl = new FootnoteImpl();
/* 256 */         footnoteImpl.setSign((String)oP.getFirst());
/* 257 */         footnoteImpl.setDescription((String)oP.getSecond());
/* 258 */         footerImpl.getFootnote().add(footnoteImpl);
/*     */       } 
/*     */     }
/* 261 */     if (oCheck.getFootnotes() != null) {
/* 262 */       for (Iterator<Footnote> it = oCheck.getFootnotes().iterator(); it.hasNext(); ) {
/* 263 */         Footnote oFE = it.next();
/* 264 */         footerImpl.getPosFootnote().add(oFE.getDescription().getDescription());
/*     */       } 
/*     */     }
/* 267 */     return (Footer)footerImpl;
/*     */   }
/*     */   
/*     */   protected void test() throws Exception {
/* 271 */     String make = "OPEL";
/* 272 */     Locale locale = Locale.GERMAN;
/* 273 */     Integer lgID = this.cache.getLanguageID(locale);
/* 274 */     Integer clid = this.cache.getCheckListID("DE", "AGILA", "2000", "Z 10 XE", "S000102");
/* 275 */     MajorOperation mo = this.cache.getMajorOperation("S000102");
/* 276 */     ServiceplanImpl serviceplanImpl = new ServiceplanImpl();
/* 277 */     serviceplanImpl.setLanguage(locale.toString());
/* 278 */     serviceplanImpl.setHeader((HeaderType)buildHeader(lgID.intValue(), mo, make, "AGILA", "2000", "Z 10 XE", null, ""));
/* 279 */     CheckList oCheck = this.cache.getCheckList(clid.intValue(), lgID.intValue());
/* 280 */     if (oCheck != null) {
/* 281 */       PositionBuildResult oRes = buildPositions(lgID.intValue(), oCheck);
/* 282 */       serviceplanImpl.setPositions((PositionsType)oRes.getPositions());
/* 283 */       serviceplanImpl.setFooter((FooterType)buildFooter(lgID.intValue(), oCheck, oRes.isBullet(), oRes.isClpus(), oRes.getNumbersAndFootnotes()));
/* 284 */       TestGeneratedClasses.marshal((Serviceplan)serviceplanImpl, "c:\\transfer\\checklist.xml");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\ICL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */