/*     */ package com.eoos.gm.tis2web.vc.v2;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.log.AppenderProxy;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Euro5Support {
/*     */   public static interface ResolutionResult extends RPOContainer {
/*     */     VIN getVIN();
/*     */     
/*     */     Make getMake();
/*     */     
/*     */     Model getModel();
/*     */     
/*     */     Modelyear getModelyear();
/*     */     
/*     */     Engine getEngine();
/*     */     
/*     */     Transmission getTransmission();
/*     */   }
/*     */   
/*     */   private static final class MakeCollectionAdapter extends AbstractCollection<Make> {
/*     */     MakeCollectionAdapter(Set<IConfiguration> cfgs) {
/*  38 */       this.cfgs = cfgs;
/*     */     }
/*     */     private final Set<IConfiguration> cfgs;
/*     */     
/*     */     public int size() {
/*  43 */       return this.cfgs.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Make> iterator() {
/*  48 */       return Util.createTransformingIterator(this.cfgs.iterator(), new Transforming()
/*     */           {
/*     */             public Object transform(Object object) {
/*  51 */               return VehicleConfigurationUtil.getMake((IConfiguration)object);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*  57 */   private static final Logger log = Logger.getLogger(Euro5Support.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection<Make> getMakes() {
/*  64 */     Collection values = GlobalVCDataProvider.getInstance().getValues(VehicleConfigurationUtil.KEY_MAKE, VehicleConfigurationUtil.cfgManagement.getEmptyConfiguration());
/*  65 */     return VehicleConfigurationUtil.resolveValues(values);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResolutionResult resolveVIN(String vin, Make make) throws VIN.InvalidVINException, VIN.UnsupportedVINException, MultipleResolutionException {
/*     */     RPOContainer rPOContainer1;
/*  84 */     if (!VehicleConfigurationUtil.checkVIN(vin, 1)) {
/*  85 */       throw new VIN.InvalidVINException(vin);
/*     */     }
/*  87 */     final VINImpl _vin = new VINImpl(vin);
/*  88 */     IConfiguration _resolved = null;
/*  89 */     Set<IConfiguration> cfgs = GlobalVINResolver.getInstance().resolveVIN((VIN)vINImpl);
/*     */     
/*  91 */     if (Util.isNullOrEmpty(cfgs))
/*  92 */       throw new VIN.UnsupportedVINException(vin); 
/*  93 */     if (cfgs.size() > 1) {
/*  94 */       if (make != null) {
/*  95 */         for (Iterator<IConfiguration> iter = cfgs.iterator(); iter.hasNext() && _resolved == null; ) {
/*  96 */           IConfiguration cfg = iter.next();
/*  97 */           if (make.equals(VehicleConfigurationUtil.getMake(cfg))) {
/*  98 */             _resolved = cfg;
/*     */           }
/*     */         } 
/*     */       }
/* 102 */       if (_resolved == null) {
/* 103 */         Collection<Make> makes = new MakeCollectionAdapter(cfgs);
/* 104 */         throw new MultipleResolutionException(makes);
/*     */       } 
/*     */     } else {
/* 107 */       _resolved = (IConfiguration)CollectionUtil.getFirst(cfgs);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 112 */       rPOContainer1 = RPOServiceImpl.getInstance().getRPOs(vin);
/* 113 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.UnsupportedVINException e) {
/* 114 */       rPOContainer1 = new RPOContainer()
/*     */         {
/*     */           public String getVehicleNumber() {
/* 117 */             return null;
/*     */           }
/*     */           
/*     */           public Collection getRPOs() {
/* 121 */             return Collections.EMPTY_SET;
/*     */           }
/*     */           
/*     */           public String getModelDesignator() {
/* 125 */             return null;
/*     */           }
/*     */         };
/*     */     } 
/* 129 */     final RPOContainer delegateRPO = rPOContainer1;
/* 130 */     final IConfiguration resolved = _resolved;
/* 131 */     return new ResolutionResult()
/*     */       {
/*     */         public String getVehicleNumber() {
/* 134 */           return delegateRPO.getVehicleNumber();
/*     */         }
/*     */         
/*     */         public Collection getRPOs() {
/* 138 */           return delegateRPO.getRPOs();
/*     */         }
/*     */         
/*     */         public String getModelDesignator() {
/* 142 */           return delegateRPO.getModelDesignator();
/*     */         }
/*     */         
/*     */         public VIN getVIN() {
/* 146 */           return _vin;
/*     */         }
/*     */         
/*     */         public Transmission getTransmission() {
/* 150 */           return VehicleConfigurationUtil.getTransmission(resolved);
/*     */         }
/*     */         
/*     */         public Modelyear getModelyear() {
/* 154 */           return VehicleConfigurationUtil.getModelyear(resolved);
/*     */         }
/*     */         
/*     */         public Model getModel() {
/* 158 */           return VehicleConfigurationUtil.getModel(resolved);
/*     */         }
/*     */         
/*     */         public Make getMake() {
/* 162 */           return VehicleConfigurationUtil.getMake(resolved);
/*     */         }
/*     */         
/*     */         public Engine getEngine() {
/* 166 */           return VehicleConfigurationUtil.getEngine(resolved);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws VIN.InvalidVINException, VIN.UnsupportedVINException {
/*     */     ResolutionResult result;
/* 175 */     AppenderProxy appenderProxy = new AppenderProxy("Console", -1L);
/* 176 */     Logger.getRootLogger().addAppender((Appender)appenderProxy);
/*     */     
/* 178 */     ApplicationContext.localStartup(new File("."), "smaier", 8080);
/*     */     
/* 180 */     log.debug(Euro5SupportDecorator.getMakes());
/*     */     
/*     */     try {
/* 183 */       result = resolveVIN("W0LGT57M391000284", VehicleConfigurationUtil.toMake("Opel"));
/* 184 */     } catch (MultipleResolutionException e) {
/* 185 */       log.debug("MULTIPLE RESOLUTION POSSIBLITIES -> MAKES: " + e.getMakes());
/*     */       return;
/*     */     } 
/* 188 */     log.debug(String.valueOf(result.getMake()));
/* 189 */     log.debug(String.valueOf(result.getModel()));
/* 190 */     log.debug(String.valueOf(result.getModelyear()));
/* 191 */     log.debug(String.valueOf(result.getEngine()));
/* 192 */     log.debug(String.valueOf(result.getTransmission()));
/* 193 */     log.debug(result.getModelDesignator());
/* 194 */     log.debug(result.getVehicleNumber());
/* 195 */     log.debug(result.getRPOs());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\Euro5Support.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */