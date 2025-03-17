/*     */ package com.eoos.gm.tis2web.frame.ws.e5.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.Document;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.Family;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.FamilyList;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.FatalError;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.FatalFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.GetDocument;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.GetFamilyDetails;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.GetLocales;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.GetMakes;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.InvParam;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.InvParamFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.LocaleList;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.MakeOpts;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.MissingDoc;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.MissingDocFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.MissingMakeFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.ResolveVin;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.Rpo;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.RpoList;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.SecurityFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.Str100List;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.VehDesc;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.common.gen.VehDetails;
/*     */ import com.eoos.gm.tis2web.rpo.RPOServiceImpl;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPO;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.CPRDocumentNotSupportedException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.SearchNotResultException;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.Euro5Support;
/*     */ import com.eoos.gm.tis2web.vc.v2.Euro5SupportDecorator;
/*     */ import com.eoos.gm.tis2web.vc.v2.MultipleResolutionException;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Resource;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @HandlerChain(file = "chain.xml")
/*     */ @WebService(serviceName = "E5Service", portName = "E5ServicePort", endpointInterface = "com.eoos.gm.tis2web.frame.ws.e5.common.gen.E5ServicePort", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5", wsdlLocation = "WEB-INF/wsdl/E5Service/euro5.wsdl")
/*     */ public class E5Service
/*     */ {
/*  72 */   private static final Logger log = Logger.getLogger(E5Service.class);
/*  73 */   private static final Logger e5performancelog = Logger.getLogger("e5performance");
/*     */   
/*     */   @Resource
/*     */   WebServiceContext wsContext;
/*     */ 
/*     */   
/*     */   public LocaleList getLocales(GetLocales param) throws SecurityFault, FatalFault {
/*  80 */     long ts = (new Date()).getTime();
/*  81 */     boolean success = false;
/*  82 */     String usr = "UNKNOWN";
/*     */     
/*     */     try {
/*  85 */       LocaleList result = new LocaleList();
/*  86 */       result.getLocale().addAll(LocaleInfoProvider.getInstance().getLocalesAsStrings());
/*  87 */       success = true;
/*  88 */       return result;
/*  89 */     } catch (Exception e) {
/*  90 */       log.error("Unable to retrieve locales[" + usr + "] - exception: ", e);
/*  91 */       throw getFatalFault();
/*     */     } finally {
/*  93 */       long pt = (new Date()).getTime() - ts;
/*  94 */       e5performancelog.debug(usr + ": getLocales()" + (success ? "" : " failed") + ": " + pt + " ms");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Str100List getMakes(GetMakes param) throws SecurityFault, FatalFault {
/* 100 */     long ts = (new Date()).getTime();
/* 101 */     boolean success = false;
/*     */     try {
/* 103 */       Str100List ret = new Str100List();
/* 104 */       List<Comparable> list = ret.getMake();
/* 105 */       list.addAll(Euro5SupportDecorator.getMakes());
/* 106 */       Collections.sort(list);
/* 107 */       success = true;
/* 108 */       return ret;
/* 109 */     } catch (Exception e) {
/* 110 */       log.error("unable to retrieve makes - exception: ", e);
/* 111 */       throw getFatalFault();
/*     */     } finally {
/* 113 */       long pt = (new Date()).getTime() - ts;
/* 114 */       e5performancelog.debug("getMakes()" + (success ? "" : " failed") + ": " + pt + " ms");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VehDetails resolveVin(ResolveVin param) throws SecurityFault, MissingMakeFault, InvParamFault, FatalFault {
/* 120 */     long ts = (new Date()).getTime();
/* 121 */     boolean success = false;
/*     */     try {
/* 123 */       Make make = null;
/*     */       
/* 125 */       if (!Util.isNullOrEmpty(param.getMake())) {
/* 126 */         make = VehicleConfigurationUtil.toMake(param.getMake());
/*     */       }
/* 128 */       Euro5Support.ResolutionResult result = Euro5Support.resolveVIN(param.getVin(), make);
/*     */       
/* 130 */       final Locale locale = Util.parseLocale(param.getLocale());
/* 131 */       VehDetails ret = new VehDetails();
/* 132 */       ret.setModelDesignator(result.getModelDesignator());
/* 133 */       ret.setVehicleNumber(result.getVehicleNumber());
/* 134 */       RpoList rpoList = new RpoList();
/* 135 */       ret.setRpoList(rpoList);
/*     */       
/* 137 */       final Collection<RPO> org = result.getRPOs();
/* 138 */       if (!Util.isNullOrEmpty(org)) {
/* 139 */         Collection<Rpo> rpos = new AbstractCollection<Rpo>()
/*     */           {
/*     */             public int size()
/*     */             {
/* 143 */               return org.size();
/*     */             }
/*     */ 
/*     */             
/*     */             public Iterator<Rpo> iterator() {
/* 148 */               return Util.createTransformingIterator(org.iterator(), new Transforming()
/*     */                   {
/*     */                     public Object transform(Object object) {
/* 151 */                       RPO rpo = (RPO)object;
/* 152 */                       if (rpo != null) {
/* 153 */                         Rpo ret = new Rpo();
/* 154 */                         ret.setCode(rpo.getCode());
/* 155 */                         ret.setDesc(rpo.getDescription(locale));
/* 156 */                         if (rpo.getFamily() != null) {
/* 157 */                           ret.setFamId(rpo.getFamily().getID());
/*     */                         }
/* 159 */                         ret.setLocale(locale.toString());
/* 160 */                         return ret;
/*     */                       } 
/* 162 */                       return null;
/*     */                     }
/*     */                   });
/*     */             }
/*     */           };
/*     */         
/* 168 */         rpoList.getRpo().addAll(rpos);
/*     */       } 
/*     */       
/* 171 */       VehDesc vdesc = new VehDesc();
/* 172 */       vdesc.setMake(VehicleConfigurationUtil.toString(result.getMake()));
/* 173 */       vdesc.setModel(VehicleConfigurationUtil.toString(result.getModel()));
/* 174 */       vdesc.setYear(VehicleConfigurationUtil.toString(result.getModelyear()));
/* 175 */       vdesc.setEngine(VehicleConfigurationUtil.toString(result.getEngine()));
/* 176 */       vdesc.setTransmission(VehicleConfigurationUtil.toString(result.getTransmission()));
/* 177 */       vdesc.setVin(result.getVIN().toString());
/* 178 */       ret.setVehDesc(vdesc);
/* 179 */       success = true;
/* 180 */       return ret;
/*     */     }
/* 182 */     catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException e) {
/* 183 */       throw new InvParamFault("invalid VIN", null);
/* 184 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.UnsupportedVINException e) {
/* 185 */       throw new InvParamFault("unsupported VIN", null);
/* 186 */     } catch (MultipleResolutionException e) {
/* 187 */       List<Comparable> availableMakes = new LinkedList(e.getMakes());
/* 188 */       Collections.sort(availableMakes);
/*     */       
/* 190 */       Str100List list = new Str100List();
/* 191 */       list.getMake().addAll(availableMakes);
/* 192 */       MakeOpts makeOpts = new MakeOpts();
/* 193 */       makeOpts.setMakes(list);
/*     */       
/* 195 */       throw new MissingMakeFault("missing make", makeOpts);
/* 196 */     } catch (Exception e) {
/* 197 */       log.error("unable to resolve VIN - exception: ", e);
/* 198 */       throw getFatalFault();
/*     */     } finally {
/* 200 */       long pt = (new Date()).getTime() - ts;
/* 201 */       e5performancelog.debug("resolveVin()" + (success ? "" : " failed") + ": " + pt + " ms");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSupportedLocale(Locale locale) {
/* 207 */     Collection<Locale> supportedLocales = Util.transformCollection(LocaleInfoProvider.getInstance().getLocales(), new Transforming<LocaleInfo, Locale>()
/*     */         {
/*     */           public Locale transform(LocaleInfo object) {
/* 210 */             return (object != null) ? Util.parseLocale(object.getLocale()) : null;
/*     */           }
/*     */         });
/* 213 */     return supportedLocales.contains(locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public FamilyList getFamilyDetails(final GetFamilyDetails param) throws SecurityFault, InvParamFault, FatalFault {
/* 218 */     long ts = (new Date()).getTime();
/* 219 */     boolean success = false;
/*     */     try {
/* 221 */       final Locale locale = Util.parseLocale(param.getLocale());
/* 222 */       if (isSupportedLocale(locale)) {
/* 223 */         if (Util.isNullOrEmpty(param.getFamilyId()) || (param.getFamilyId().size() == 1 && Util.isNullOrEmpty(param.getFamilyId().get(0)))) {
/* 224 */           InvParam invParam = new InvParam();
/* 225 */           invParam.setPName("familyId");
/* 226 */           throw new InvParamFault("missing identifier", invParam);
/*     */         } 
/* 228 */         log.debug("resolving families: " + param.getFamilyId());
/*     */         
/* 230 */         FamilyList ret = new FamilyList();
/* 231 */         ret.setLocale(locale.toString());
/* 232 */         List families = ret.getFamily();
/* 233 */         final Map<String, RPOFamily> resolved = RPOServiceImpl.getInstance().resolveFamilies(param.getFamilyId());
/*     */         
/* 235 */         families.addAll(new AbstractList<Family>()
/*     */             {
/*     */               public Family get(int index)
/*     */               {
/* 239 */                 RPOFamily rpoFamily = (RPOFamily)resolved.get(param.getFamilyId().get(index));
/* 240 */                 if (rpoFamily != null) {
/* 241 */                   Family ret = new Family();
/* 242 */                   ret.setDesc(rpoFamily.getDescription(locale));
/* 243 */                   ret.setLocale(locale.toString());
/* 244 */                   ret.setFamId(rpoFamily.getID());
/* 245 */                   return ret;
/*     */                 } 
/* 247 */                 return null;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public int size() {
/* 253 */                 return resolved.size();
/*     */               }
/*     */             });
/*     */         
/* 257 */         success = true;
/* 258 */         return ret;
/*     */       } 
/* 260 */       throw createInvalidLocaleFault(locale);
/*     */     
/*     */     }
/* 263 */     catch (NullPointerException e) {
/* 264 */       log.error("unable to retrieve RPO family details - exception: ", e);
/* 265 */       throw getFatalFault();
/*     */     } finally {
/* 267 */       long pt = (new Date()).getTime() - ts;
/* 268 */       e5performancelog.debug("getFamilyDetails()" + (success ? "" : " failed") + ": " + pt + " ms");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private InvParamFault createInvalidLocaleFault(Locale locale) {
/* 274 */     InvParam param = new InvParam();
/* 275 */     param.setPName("locale");
/* 276 */     param.setPValue(locale.toString());
/* 277 */     InvParamFault fault = new InvParamFault("unsupported locale", param);
/* 278 */     return fault;
/*     */   }
/*     */   
/*     */   public Document getDocument(GetDocument param) throws SecurityFault, InvParamFault, MissingDocFault, FatalFault {
/* 282 */     long ts = (new Date()).getTime();
/* 283 */     boolean success = false;
/* 284 */     String documentID = null;
/*     */     try {
/* 286 */       Locale locale = Util.parseLocale(param.getLocale());
/* 287 */       if (isSupportedLocale(locale)) {
/* 288 */         documentID = param.getDocId();
/* 289 */         SI.MHTML ret = SIDataAdapterFacade.getInstance().getSI().getDocument(documentID, LocaleInfoProvider.getInstance().getLocale(locale));
/* 290 */         Document doc = new Document();
/* 291 */         doc.setMtype(ret.getMIMEType());
/*     */         
/* 293 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */         try {
/* 295 */           ret.writeContent(baos);
/*     */         } finally {
/* 297 */           StreamUtil.close(baos, log);
/*     */         } 
/* 299 */         doc.setDoc(baos.toByteArray());
/* 300 */         success = true;
/* 301 */         return doc;
/*     */       } 
/* 303 */       throw createInvalidLocaleFault(locale);
/*     */     }
/* 305 */     catch (InvParamFault e) {
/* 306 */       throw e;
/* 307 */     } catch (DocumentNotFoundException ex) {
/* 308 */       MissingDoc missingDoc = new MissingDoc();
/* 309 */       missingDoc.setDocId(documentID);
/* 310 */       throw new MissingDocFault("missing document", missingDoc);
/*     */     }
/* 312 */     catch (DocumentContainerConstructionException ex) {
/* 313 */       InvParam invParam = new InvParam();
/* 314 */       invParam.setPName(documentID);
/* 315 */       throw new InvParamFault(ex.getMessage(), invParam);
/*     */     }
/* 317 */     catch (SearchNotResultException ex) {
/* 318 */       InvParam invParam = new InvParam();
/* 319 */       invParam.setPName(documentID);
/* 320 */       throw new InvParamFault("not match found", invParam);
/*     */     }
/* 322 */     catch (CPRDocumentNotSupportedException ex) {
/* 323 */       InvParam invParam = new InvParam();
/* 324 */       invParam.setPName(documentID);
/* 325 */       throw new InvParamFault("not supported cpr document", invParam);
/*     */     }
/* 327 */     catch (Exception e) {
/* 328 */       throw getFatalFault();
/*     */     } finally {
/* 330 */       long pt = (new Date()).getTime() - ts;
/* 331 */       e5performancelog.debug("getDocument()" + (success ? "" : " failed") + ": " + pt + " ms");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private FatalFault getFatalFault() {
/* 337 */     FatalError fError = new FatalError();
/* 338 */     fError.setDetails("Server error or not implemented yet.");
/* 339 */     return new FatalFault("Unspecified Fault", fError);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\E5Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */