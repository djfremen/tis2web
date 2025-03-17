/*     */ package com.eoos.gm.tis2web.lt.icop.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.scout.ICOPScout;
/*     */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTALExport;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.util.Task;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ICOPServerSupport
/*     */ {
/*  34 */   private static final Logger log = Logger.getLogger(ICOPServerSupport.class);
/*     */   
/*     */   public static interface ConfigurationWrapper
/*     */   {
/*     */     VIN getVIN();
/*     */     
/*     */     IConfiguration getConfiguration();
/*     */   }
/*     */   
/*     */   public static RequestParameters startGUI(String sessionID, VIN vin, IConfiguration cfg, String country, String locale) {
/*     */     try {
/*  45 */       final URL loginURL = new URL(ApplicationContext.getInstance().getServerURL());
/*     */       
/*  47 */       final Properties params = new Properties();
/*  48 */       params.put("user", sessionID);
/*  49 */       params.put("portal_ID", "ICOP");
/*  50 */       params.put("country", country);
/*  51 */       params.put("locale", locale);
/*     */       
/*  53 */       StringBuilder vehicle = new StringBuilder();
/*  54 */       if (vin != null) {
/*  55 */         vehicle.append(String.valueOf(vin));
/*     */       }
/*  57 */       vehicle.append("@");
/*  58 */       if (cfg != null) {
/*  59 */         vehicle.append(VehicleConfigurationUtil.toString(cfg, true));
/*     */       }
/*     */       
/*  62 */       params.put("vehicle", vehicle.toString());
/*  63 */       params.put("token", ICOPScout.createToken((byte)2).toString());
/*     */       
/*  65 */       return new RequestParameters()
/*     */         {
/*     */           public URL getURL() {
/*  68 */             return loginURL;
/*     */           }
/*     */           
/*     */           public Properties getPostParameters() {
/*  72 */             return params;
/*     */           }
/*     */         };
/*     */     }
/*  76 */     catch (MalformedURLException e) {
/*  77 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static RequestParameters setVehDesc(String sessionID, final VIN vin, IConfiguration _cfg) throws InvalidSessionException {
/*     */     try {
/*  84 */       final URL baseURL = new URL(ApplicationContext.getInstance().getServerURL());
/*  85 */       final String targetSessionID = "ICOP." + sessionID;
/*  86 */       final IConfiguration cfg = VehicleConfigurationUtil.ensureSerializable(_cfg);
/*  87 */       ClusterTask clusterTask = new ClusterTask()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object execute() {
/*  92 */             ClientContext context = ClientContextProvider.getInstance().getContext(targetSessionID, false);
/*  93 */             if (context != null) {
/*  94 */               VCFacade.getInstance(context).storeCfg(cfg, vin);
/*  95 */               return MainPage.getInstance(context).getURL();
/*     */             } 
/*  97 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 101 */       ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, null);
/* 102 */       ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 103 */       Exception e = null;
/* 104 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 105 */         URL url = iter.next();
/* 106 */         if (result.getResult(url) instanceof Exception) {
/* 107 */           e = (Exception)result.getResult(url);
/* 108 */           log.warn("failed to set vehicle description - for cluster server :" + url + " - exception: " + e); continue;
/* 109 */         }  if (result.getResult(url) != null) {
/* 110 */           final String dispatchURL = replaceHtmlEscape((String)result.getResult(url));
/* 111 */           return new RequestParameters()
/*     */             {
/*     */               public URL getURL() {
/*     */                 try {
/* 115 */                   return baseURL.toURI().resolve(dispatchURL).toURL();
/* 116 */                 } catch (URISyntaxException e) {
/* 117 */                   throw new RuntimeException(e);
/* 118 */                 } catch (MalformedURLException e) {
/* 119 */                   throw new RuntimeException(e);
/*     */                 } 
/*     */               }
/*     */               
/*     */               public Properties getPostParameters() {
/* 124 */                 return null;
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/* 129 */       if (e != null) {
/* 130 */         throw e;
/*     */       }
/* 132 */       throw new InvalidSessionException(targetSessionID);
/*     */     }
/* 134 */     catch (InvalidSessionException e) {
/* 135 */       throw e;
/* 136 */     } catch (Exception e) {
/* 137 */       throw new RuntimeException("unable to reset TAL", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface TAL
/*     */   {
/*     */     List<String[]> getPositions();
/*     */     
/*     */     String getSum();
/*     */     
/*     */     String getUnit();
/*     */     
/*     */     VIN getVIN();
/*     */     
/*     */     IConfiguration getVehicleConfiguration();
/*     */     
/*     */     String getHeader();
/*     */     
/*     */     String getTrailer();
/*     */   }
/*     */   
/*     */   public static TAL getTAL(String sessionID) throws InvalidSessionException {
/*     */     try {
/* 160 */       final String targetSessionID = "ICOP." + sessionID;
/* 161 */       ClusterTask clusterTask = new ClusterTask()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object execute() {
/* 166 */             List<String[]> ret = (List)new LinkedList<String>();
/*     */             
/* 168 */             ClientContext context = ClientContextProvider.getInstance().getContext(targetSessionID, false);
/* 169 */             if (context != null) {
/* 170 */               IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/* 171 */               VIN vin = VCFacade.getInstance(context).getVIN();
/* 172 */               ret.add(new String[] { VehicleConfigurationUtil.encode(vin, cfg) });
/* 173 */               LTClientContext lt = LTClientContext.getInstance(context);
/* 174 */               List lTAL = lt.getSelection().getTALList();
/* 175 */               for (Iterator<LTDataWork> itm = lTAL.iterator(); itm.hasNext(); ) {
/* 176 */                 LTDataWork work = itm.next();
/* 177 */                 String[] row = { "", "", "", "" };
/* 178 */                 row[0] = work.getNr();
/* 179 */                 row[1] = work.getDescription();
/* 180 */                 if (work.getSXAWList() != null && work.getSXAWList().size() > 0) {
/* 181 */                   LTSXAWData ltsxAwdata = work.getSXAWList().get(0);
/* 182 */                   if (ltsxAwdata.getSx() != null) {
/* 183 */                     row[2] = ltsxAwdata.getSx();
/*     */                   }
/* 185 */                   row[3] = ltsxAwdata.getAwFormatted();
/*     */                 } 
/* 187 */                 ret.add(row);
/*     */               } 
/*     */               
/* 190 */               String sum = lt.getAWSum(lTAL);
/* 191 */               String unit = lt.getAWUnit();
/* 192 */               LTTALExport talExport = new LTTALExport(context);
/* 193 */               String header = talExport.getHeader();
/* 194 */               String trailer = talExport.getTrailer();
/* 195 */               ret.add(new String[] { sum, unit, header, trailer });
/* 196 */               return ret;
/*     */             } 
/* 198 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 203 */       ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, null);
/* 204 */       ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 205 */       Exception e = null;
/* 206 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 207 */         URL url = iter.next();
/* 208 */         if (result.getResult(url) instanceof Exception) {
/* 209 */           e = (Exception)result.getResult(url);
/* 210 */           log.warn("failed to retrieve TAL - for cluster server :" + url + " - exception: " + e); continue;
/* 211 */         }  if (result.getResult(url) != null) {
/* 212 */           final List<String[]> tmp = (List<String[]>)result.getResult(url);
/* 213 */           final VehicleConfigurationUtil.DecodingResult dResult = VehicleConfigurationUtil.decode(((String[])tmp.get(0))[0]);
/* 214 */           return new TAL()
/*     */             {
/*     */               public String getSum() {
/* 217 */                 return ((String[])tmp.get(tmp.size() - 1))[0];
/*     */               }
/*     */               
/*     */               public String getUnit() {
/* 221 */                 return ((String[])tmp.get(tmp.size() - 1))[1];
/*     */               }
/*     */               
/*     */               public String getHeader() {
/* 225 */                 return ((String[])tmp.get(tmp.size() - 1))[2];
/*     */               }
/*     */               
/*     */               public String getTrailer() {
/* 229 */                 return ((String[])tmp.get(tmp.size() - 1))[3];
/*     */               }
/*     */               
/*     */               public List<String[]> getPositions() {
/* 233 */                 return (List)tmp.subList(1, tmp.size() - 1);
/*     */               }
/*     */               
/*     */               public IConfiguration getVehicleConfiguration() {
/* 237 */                 return dResult.getConfiguration();
/*     */               }
/*     */               
/*     */               public VIN getVIN() {
/* 241 */                 return dResult.getVIN();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/* 246 */       if (e != null) {
/* 247 */         throw e;
/*     */       }
/* 249 */       throw new InvalidSessionException(targetSessionID);
/*     */     }
/* 251 */     catch (InvalidSessionException e) {
/* 252 */       throw e;
/* 253 */     } catch (Exception e) {
/* 254 */       throw new RuntimeException("unable to retrieve TAL", e);
/*     */     } 
/*     */   } public static interface RequestParameters {
/*     */     URL getURL();
/*     */     Properties getPostParameters(); }
/*     */   public static boolean resetTal(String sessionID) throws InvalidSessionException {
/*     */     try {
/* 261 */       final String targetSessionID = "ICOP." + sessionID;
/* 262 */       ClusterTask clusterTask = new ClusterTask()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object execute() {
/* 267 */             ClientContext context = ClientContextProvider.getInstance().getContext(targetSessionID, false);
/* 268 */             if (context != null) {
/* 269 */               LTClientContext.getInstance(context).getSelection().clear();
/* 270 */               return Boolean.TRUE;
/*     */             } 
/* 272 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 276 */       ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, null);
/* 277 */       ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 278 */       Exception e = null;
/* 279 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 280 */         URL url = iter.next();
/* 281 */         if (result.getResult(url) instanceof Exception) {
/* 282 */           e = (Exception)result.getResult(url);
/* 283 */           log.warn("failed to reset tal - for cluster server :" + url + " - exception: " + e); continue;
/* 284 */         }  if (result.getResult(url) != null) {
/* 285 */           result.getResult(url);
/* 286 */           return Boolean.TRUE.booleanValue();
/*     */         } 
/*     */       } 
/* 289 */       if (e != null) {
/* 290 */         throw e;
/*     */       }
/* 292 */       throw new InvalidSessionException(targetSessionID);
/*     */     }
/* 294 */     catch (InvalidSessionException e) {
/* 295 */       throw e;
/* 296 */     } catch (Exception e) {
/* 297 */       throw new RuntimeException("unable to reset TAL", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurationWrapper getVehicleConfiguration(String sessionID) throws InvalidSessionException {
/*     */     try {
/* 309 */       final String targetSessionID = "ICOP." + sessionID;
/* 310 */       ClusterTask clusterTask = new ClusterTask()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object execute() {
/* 315 */             new LinkedList();
/*     */             
/* 317 */             ClientContext context = ClientContextProvider.getInstance().getContext(targetSessionID, false);
/* 318 */             if (context != null) {
/* 319 */               IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/* 320 */               VIN vin = VCFacade.getInstance(context).getVIN();
/* 321 */               return VehicleConfigurationUtil.encode(vin, cfg);
/*     */             } 
/* 323 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 328 */       ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, null);
/* 329 */       ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 330 */       Exception e = null;
/* 331 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 332 */         URL url = iter.next();
/* 333 */         if (result.getResult(url) instanceof Exception) {
/* 334 */           e = (Exception)result.getResult(url);
/* 335 */           log.warn("failed to get vehicle configuration - for cluster server :" + url + " - exception: " + e); continue;
/* 336 */         }  if (result.getResult(url) != null) {
/* 337 */           final VehicleConfigurationUtil.DecodingResult dResult = VehicleConfigurationUtil.decode((String)result.getResult(url));
/* 338 */           return new ConfigurationWrapper()
/*     */             {
/*     */               public VIN getVIN() {
/* 341 */                 return dResult.getVIN();
/*     */               }
/*     */               
/*     */               public IConfiguration getConfiguration() {
/* 345 */                 return dResult.getConfiguration();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/* 350 */       if (e != null) {
/* 351 */         throw e;
/*     */       }
/* 353 */       throw new InvalidSessionException(targetSessionID);
/*     */     }
/* 355 */     catch (InvalidSessionException e) {
/* 356 */       throw e;
/* 357 */     } catch (Exception e) {
/* 358 */       throw new RuntimeException("unable to retrieve vehicle description", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean logout(String sessionID) {
/*     */     try {
/* 365 */       final String targetSessionID = "ICOP." + sessionID;
/* 366 */       ClusterTask clusterTask = new ClusterTask()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object execute() {
/*     */             try {
/* 372 */               return Boolean.valueOf(ClientContextProvider.getInstance().invalidateSession(targetSessionID));
/* 373 */             } catch (Exception e) {
/* 374 */               ICOPServerSupport.log.warn("unable to execute invalidation of session: " + targetSessionID + ", ignoring - exception: ", e);
/* 375 */               return Boolean.valueOf(false);
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 381 */       ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, null);
/* 382 */       ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/*     */       
/* 384 */       boolean ret = false;
/* 385 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext() && !ret; ) {
/* 386 */         URL url = iter.next();
/* 387 */         if (result.getResult(url) instanceof Exception) {
/* 388 */           log.warn("failed to logout - for cluster server :" + url); continue;
/* 389 */         }  if (result.getResult(url) != null) {
/* 390 */           ret = ((Boolean)result.getResult(url)).booleanValue();
/*     */         }
/*     */       } 
/* 393 */       return ret;
/* 394 */     } catch (Exception e) {
/* 395 */       throw new RuntimeException("unable to execute logout", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String replaceHtmlEscape(String string) {
/* 401 */     return string.replace("&amp;", "&");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\server\ICOPServerSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */