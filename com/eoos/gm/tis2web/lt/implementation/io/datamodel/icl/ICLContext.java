/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.icl;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.IndeterminableVCRException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.db.ICLCache;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Serviceplan;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.fop.apps.Driver;
/*     */ import org.apache.fop.apps.FOPException;
/*     */ import org.apache.fop.apps.Options;
/*     */ import org.apache.fop.configuration.Configuration;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ICLContext
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(ICLContext.class);
/*     */   
/*  53 */   protected static Marshaller MARSHALLER = null;
/*     */   
/*  55 */   protected static Transformer TRANSFORMER = null;
/*     */   
/*     */   static {
/*     */     class MyLoader
/*     */       extends ClassLoader {
/*     */       public MyLoader(ClassLoader parent) {
/*  61 */         super(parent);
/*     */       }
/*     */       
/*     */       public URL getResource(String name) {
/*  65 */         URL retValue = null;
/*  66 */         retValue = super.getResource(name);
/*  67 */         if (retValue == null) {
/*  68 */           ICLContext.log.info("second try");
/*  69 */           retValue = super.getResource("/" + name);
/*     */         } 
/*  71 */         ICLContext.log.info("getResource(" + name + ") - retValue:" + String.valueOf(retValue));
/*  72 */         return retValue;
/*     */       }
/*     */     };
/*     */     
/*     */     try {
/*  77 */       MyLoader cl = new MyLoader(ICLContext.class.getClassLoader());
/*     */       
/*  79 */       JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml", cl);
/*  80 */       MARSHALLER = jc.createMarshaller();
/*  81 */       String pathXSL = "lt/icl/fop/sp2fo.xsl";
/*  82 */       TRANSFORMER = getTransformer(pathXSL);
/*  83 */       log.debug("TRANSFORMER = getTransformer(pathXSL) is of type: " + TRANSFORMER.getClass().getCanonicalName());
/*     */ 
/*     */       
/*     */       try {
/*  87 */         byte[] fopConfData = ApplicationContext.getInstance().loadResource("lt/icl/fop/userconfig.xml");
/*  88 */         new Options(new ByteArrayInputStream(fopConfData));
/*     */         
/*  90 */         String urlFOP = ApplicationContext.getInstance().getProperty("component.lt.icl.url.base.fop.resource");
/*  91 */         if (Util.contains(urlFOP, "#LOCAL_URL")) {
/*  92 */           urlFOP = urlFOP.replace("#LOCAL_URL", ApplicationContext.getInstance().getLocalURL().toString());
/*     */         }
/*     */         
/*  95 */         if (urlFOP.lastIndexOf("/") != urlFOP.length() - 1) {
/*  96 */           urlFOP = urlFOP + "/";
/*     */         }
/*  98 */         Configuration.put("baseDir", urlFOP);
/*  99 */       } catch (FOPException e) {
/* 100 */         log.error("unable to configure fop - error:" + e, (Throwable)e);
/*     */       }
/*     */     
/* 103 */     } catch (Exception e) {
/* 104 */       log.error("unable to init ICLContext - exception:" + e, e);
/* 105 */       throw new ExceptionWrapper(e);
/*     */     } 
/* 107 */     log.debug("Static block execution completed");
/*     */   } public static interface ICLContextCallback {
/*     */     SIOLT getCurrentElement(); } protected static Transformer getTransformer(String pathTOXLS) {
/*     */     Transformer TRANSFORMER;
/* 111 */     byte[] xslData = ApplicationContext.getInstance().loadResource(pathTOXLS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     TransformerFactoryImpl transformerFactoryImpl = new TransformerFactoryImpl();
/*     */ 
/*     */     
/* 121 */     InputStream is = new ByteArrayInputStream(xslData);
/*     */     
/*     */     try {
/* 124 */       TRANSFORMER = transformerFactoryImpl.newTransformer(new StreamSource(is));
/* 125 */       log.debug("TRANSFORMER = tFactory.newTransformer(new StreamSource(is)) is of type: " + TRANSFORMER.getClass().getCanonicalName());
/* 126 */     } catch (TransformerConfigurationException e) {
/* 127 */       e.printStackTrace();
/* 128 */       return null;
/*     */     } 
/* 130 */     return TRANSFORMER;
/*     */   }
/*     */   
/*     */   protected class VehicleData
/*     */   {
/* 135 */     public String make = null;
/*     */     
/* 137 */     public String model = null;
/*     */     
/* 139 */     public String modelyear = null;
/*     */     
/* 141 */     public String engine = null;
/*     */     
/* 143 */     public String majorOperationNumber = null;
/*     */     
/* 145 */     public String transmission = null;
/*     */     
/* 147 */     public String vin = null;
/*     */ 
/*     */     
/*     */     public VehicleData(IConfiguration cfg) throws IndeterminableVCRException {
/* 151 */       this.make = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getMake(cfg));
/* 152 */       this.model = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getModel(cfg));
/* 153 */       this.modelyear = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getModelyear(cfg));
/* 154 */       this.engine = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getEngine(cfg));
/* 155 */       this.transmission = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getTransmission(cfg));
/* 156 */       this.vin = VCFacade.getInstance(ICLContext.this.context).getCurrentVIN();
/*     */     }
/*     */   }
/*     */   
/* 160 */   protected Integer checklistID = null;
/*     */   
/*     */   protected boolean dirty = true;
/*     */   
/* 164 */   protected VehicleData vehicle = null;
/*     */   
/* 166 */   protected String majorOperationNumber = null;
/*     */   
/* 168 */   protected String majorOperationNumberWS = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String country;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */ 
/*     */   
/* 179 */   protected ICLContextCallback callback = null;
/*     */   
/*     */   public ICLContext(ClientContext context, ICLContextCallback callback) {
/* 182 */     this.context = context;
/* 183 */     this.callback = callback;
/* 184 */     this.country = SharedContext.getInstance(context).getCountry();
/* 185 */     VCServiceProvider.getInstance().getService(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 188 */             ICLContext.this.dirty = true;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ICLContext(ClientContext context, String opNumber) {
/* 196 */     this.context = context;
/* 197 */     this.majorOperationNumberWS = opNumber;
/* 198 */     this.country = SharedContext.getInstance(context).getCountry();
/*     */   }
/*     */   
/*     */   protected void update() throws IndeterminableVCRException {
/* 202 */     boolean updateChecklistID = false;
/* 203 */     if (this.dirty) {
/* 204 */       this.vehicle = new VehicleData(VCFacade.getInstance(this.context).getCfg());
/* 205 */       updateChecklistID = true;
/* 206 */       this.dirty = false;
/*     */     } 
/* 208 */     String currentOperationNumber = null;
/* 209 */     if (this.callback != null) {
/* 210 */       currentOperationNumber = this.callback.getCurrentElement().getMajorOperationNumber();
/*     */     } else {
/* 212 */       currentOperationNumber = "undefined";
/*     */     } 
/* 214 */     boolean changed = true;
/*     */     try {
/* 216 */       if (currentOperationNumber.equals(this.majorOperationNumber)) {
/* 217 */         changed = false;
/*     */       }
/* 219 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 222 */     updateChecklistID = (updateChecklistID || changed);
/*     */     
/* 224 */     if (updateChecklistID) {
/* 225 */       if (this.callback != null) {
/* 226 */         this.majorOperationNumber = currentOperationNumber;
/*     */       } else {
/* 228 */         this.majorOperationNumber = this.majorOperationNumberWS;
/*     */       } 
/*     */       
/* 231 */       this.checklistID = null;
/*     */       try {
/* 233 */         this.checklistID = LTDataAdapterFacade.getInstance(this.context).getLT().getCheckListID(this.country, this.vehicle.model, this.vehicle.modelyear, this.vehicle.engine, this.majorOperationNumber);
/* 234 */       } catch (IllegalArgumentException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean singleChecklistAvailable() {
/*     */     try {
/* 245 */       update();
/* 246 */       return (this.checklistID != null && !this.checklistID.equals(ICLCache.MULTIPLE_HITS));
/* 247 */     } catch (IndeterminableVCRException e) {
/* 248 */       log.warn("unable to determine correct result since VCR is not determinable, returning false");
/* 249 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean multipleChecklistAvailable() {
/*     */     try {
/* 255 */       update();
/* 256 */       return (this.checklistID != null && this.checklistID.equals(ICLCache.MULTIPLE_HITS));
/* 257 */     } catch (IndeterminableVCRException e) {
/* 258 */       log.warn("unable to determine correct result since VCR is not determinable, returning false");
/* 259 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean checklistAvailable() {
/* 264 */     return (singleChecklistAvailable() || multipleChecklistAvailable());
/*     */   }
/*     */   
/*     */   public byte[] getChecklist() {
/* 268 */     byte[] retValue = null;
/* 269 */     if (singleChecklistAvailable()) {
/*     */       
/*     */       try {
/*     */         try {
/* 273 */           log.info("Generate Inspection Check List: user= " + this.context.getSessionID() + " locale=" + this.context.getLocale() + " make=" + this.vehicle.make + " model=" + this.vehicle.model + " model-year=" + this.vehicle.modelyear + " engine=" + this.vehicle.engine);
/* 274 */         } catch (Exception e) {}
/*     */ 
/*     */         
/* 277 */         LT lt = LTDataAdapterFacade.getInstance(this.context).getLT();
/* 278 */         Serviceplan sp = lt.getServiceplan(this.context.getLocale(), this.checklistID, this.majorOperationNumber, this.vehicle.make, this.vehicle.model, this.vehicle.modelyear, this.vehicle.engine, this.vehicle.transmission, this.vehicle.vin);
/* 279 */         String nameXSL = lt.getServiceTypeXSL(this.context.getLocale(), this.majorOperationNumber);
/*     */         
/* 281 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */         
/* 283 */         MARSHALLER.marshal(sp, baos);
/* 284 */         baos.close();
/*     */ 
/*     */         
/* 287 */         Driver driver = new Driver();
/* 288 */         driver.setRenderer(1);
/*     */ 
/*     */         
/* 291 */         ByteArrayOutputStream result = new ByteArrayOutputStream();
/* 292 */         driver.setOutputStream(result);
/*     */ 
/*     */         
/* 295 */         Source src = new StreamSource(new ByteArrayInputStream(baos.toByteArray()));
/*     */ 
/*     */ 
/*     */         
/* 299 */         Result res = new SAXResult(driver.getContentHandler());
/*     */         
/* 301 */         if (nameXSL != null) {
/* 302 */           String pathToString = "lt/icl/fop/" + nameXSL.toLowerCase(Locale.ENGLISH) + ".xsl";
/* 303 */           if (ApplicationContext.getInstance().loadResource(pathToString) != null)
/*     */           {
/* 305 */             TRANSFORMER = getTransformer(pathToString);
/*     */           }
/*     */         } 
/*     */         
/* 309 */         TRANSFORMER.transform(src, res);
/*     */         
/* 311 */         result.close();
/* 312 */         retValue = result.toByteArray();
/* 313 */       } catch (Exception e) {
/* 314 */         log.error("unable to generate pdf for majorOperationNumber '" + this.majorOperationNumber + "' -error:" + e, e);
/* 315 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/* 318 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getVehicleRestriction() {
/* 322 */     return LTDataAdapterFacade.getInstance(this.context).getLT().getCheckListAttributes(this.country, this.vehicle.model, this.vehicle.modelyear, this.vehicle.engine, this.majorOperationNumber);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\icl\ICLContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */