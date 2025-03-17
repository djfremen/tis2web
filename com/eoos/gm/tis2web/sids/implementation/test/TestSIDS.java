/*     */ package com.eoos.gm.tis2web.sids.implementation.test;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.sids.implementation.BaseVehicle;
/*     */ import com.eoos.gm.tis2web.sids.implementation.CacheSIDS;
/*     */ import com.eoos.gm.tis2web.sids.implementation.DisplayableServiceIDItem;
/*     */ import com.eoos.gm.tis2web.sids.implementation.SIDS;
/*     */ import com.eoos.gm.tis2web.sids.implementation.ServiceIDImpl;
/*     */ import com.eoos.gm.tis2web.sids.implementation.VinServiceIDs;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.sps.common.export.DataStorage;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.BasicConfigurator;
/*     */ 
/*     */ 
/*     */ public class TestSIDS
/*     */ {
/*     */   public static final String SESSION = "SIDS";
/*     */   public static final String TESTFILE = "testvins.txt";
/*  48 */   public static ServiceID SID_USER = (ServiceID)ServiceIDImpl.getInstance("SID_UserSelection");
/*  49 */   public static ServiceID SID_NONE = (ServiceID)ServiceIDImpl.getInstance("SID_None");
/*  50 */   public static ServiceID SID_ERROR = (ServiceID)ServiceIDImpl.getInstance("SID_Error");
/*     */   
/*  52 */   protected Map check = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute() throws Exception {
/*  58 */     List<String> vins = generateVINs();
/*  59 */     Locale locale = Locale.US;
/*  60 */     for (int i = 0; i < vins.size(); i++) {
/*  61 */       String vin = vins.get(i);
/*  62 */       DataStorage dataStorage = new DataStorage();
/*  63 */       dataStorage.set(CommonAttribute.SESSION_ID, (Value)new ValueAdapter("SIDS"));
/*  64 */       ServiceID serviceID = determineServiceID(locale, vin, (AttributeValueMap)dataStorage);
/*  65 */       if (serviceID != SID_USER) {
/*     */ 
/*     */         
/*  68 */         System.out.println(vin + " -> " + serviceID.toString().toUpperCase(Locale.ENGLISH));
/*  69 */         ServiceID verifiedServiceID = (ServiceID)this.check.get(vin);
/*  70 */         if (verifiedServiceID != null)
/*  71 */           if (verifiedServiceID == serviceID) {
/*  72 */             System.out.println("verified (DB)");
/*     */           } else {
/*  74 */             System.err.println("VERIFICATION FAILED !!!");
/*     */           }  
/*     */       } 
/*     */     } 
/*  78 */     System.out.println("done");
/*     */   }
/*     */   
/*     */   protected ServiceID determineServiceID(Locale locale, String vin, AttributeValueMap avMap) {
/*     */     try {
/*  83 */       ServiceID serviceID = null;
/*     */       try {
/*  85 */         serviceID = SIDS.getServiceID(locale, vin, avMap);
/*  86 */       } catch (NoServiceIDException n) {}
/*     */       
/*  88 */       ServiceIDImpl serviceIDImpl = ServiceIDImpl.getInstance(VinServiceIDs.getInstance(vin, locale).getServiceID(avMap));
/*  89 */       if (serviceID != serviceIDImpl) {
/*  90 */         System.err.println("VERIFICATION FAILED (" + serviceID + " vs " + serviceIDImpl + ")!!!");
/*  91 */         return SID_ERROR;
/*     */       } 
/*  93 */       System.out.println("verified (SIDS)");
/*  94 */       return serviceID;
/*     */     }
/*  96 */     catch (RequestException e) {
/*  97 */       SelectionRequest request = (SelectionRequest)e.getRequest();
/*  98 */       List<Value> options = request.getOptions();
/*  99 */       for (int j = 0; j < options.size(); j++) {
/* 100 */         Value option = options.get(j);
/* 101 */         AttributeValueMap avCopy = copy(avMap);
/* 102 */         avCopy.set(request.getAttribute(), option);
/* 103 */         ServiceID serviceID = determineServiceID(locale, vin, avCopy);
/* 104 */         System.out.println(vin + "(" + list(locale, avCopy) + ") -> " + serviceID.toString().toUpperCase(Locale.ENGLISH));
/*     */       } 
/* 106 */       return SID_USER;
/* 107 */     } catch (Exception x) {
/* 108 */       System.err.println("VIN: " + vin);
/* 109 */       System.err.println(x);
/*     */       
/* 111 */       return SID_ERROR;
/*     */     } 
/*     */   }
/*     */   protected String list(Locale locale, AttributeValueMap avMap) {
/* 115 */     StringBuffer buffer = new StringBuffer();
/* 116 */     Iterator<Attribute> it = avMap.getAttributes().iterator();
/* 117 */     while (it.hasNext()) {
/* 118 */       Attribute attribute = it.next();
/* 119 */       if (attribute instanceof com.eoos.gm.tis2web.sids.service.cai.DisplayableServiceIDAttr) {
/* 120 */         if (buffer.length() > 0) {
/* 121 */           buffer.append('|');
/*     */         }
/* 123 */         DisplayableServiceIDItem value = (DisplayableServiceIDItem)avMap.getValue(attribute);
/* 124 */         buffer.append(value.getDenotation(locale));
/*     */       } 
/*     */     } 
/* 127 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   protected AttributeValueMap copy(AttributeValueMap avMap) {
/* 131 */     DataStorage dataStorage = new DataStorage();
/* 132 */     Iterator<Attribute> it = avMap.getAttributes().iterator();
/* 133 */     while (it.hasNext()) {
/* 134 */       Attribute attribute = it.next();
/* 135 */       dataStorage.set(attribute, avMap.getValue(attribute));
/*     */     } 
/* 137 */     return (AttributeValueMap)dataStorage;
/*     */   }
/*     */   
/*     */   protected List generateVINs() {
/* 141 */     List<String> vins = new ArrayList();
/* 142 */     List<BaseVehicle> vehicles = CacheSIDS.getInstance().getBaseVehicles();
/* 143 */     for (int i = 0; i < vehicles.size(); i++) {
/* 144 */       BaseVehicle vehicle = vehicles.get(i);
/* 145 */       String vin = generateVIN(vehicle);
/* 146 */       if (!vins.contains(vin)) {
/* 147 */         vins.add(vin);
/*     */       }
/* 149 */       if (vehicle.getQualifier() == null) {
/* 150 */         ServiceID serviceID = (ServiceID)this.check.get(vin);
/* 151 */         if (serviceID != null) {
/* 152 */           if (serviceID != vehicle.getServiceID()) {
/* 153 */             this.check.put(vin, null);
/*     */           }
/*     */         } else {
/* 156 */           this.check.put(vin, vehicle.getServiceID());
/*     */         } 
/*     */       } 
/*     */     } 
/* 160 */     return vins;
/*     */   }
/*     */   
/*     */   protected String generateVIN(BaseVehicle vehicle) {
/* 164 */     char[] vin = new char[17];
/* 165 */     Arrays.fill(vin, '~');
/* 166 */     copy(vin, 0, vehicle.getWmi());
/* 167 */     copy(vin, 3, vehicle.getVds());
/* 168 */     copy(vin, 9, vehicle.getMy());
/* 169 */     copy(vin, 10, vehicle.getVis());
/* 170 */     return new String(vin);
/*     */   }
/*     */   
/*     */   protected void copy(char[] vin, int start, String value) {
/* 174 */     if (value == null) {
/*     */       return;
/*     */     }
/* 177 */     for (int i = 0; i < value.length(); ) {
/* 178 */       char c = value.charAt(i);
/* 179 */       if (c != '*' && c != '#') {
/* 180 */         vin[start + i] = c;
/*     */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(File testfile) throws IOException {
/* 188 */     BufferedReader in = null;
/* 189 */     String testcase = null;
/*     */     try {
/* 191 */       in = new BufferedReader(new FileReader(testfile));
/* 192 */       while ((testcase = in.readLine()) != null) {
/* 193 */         execute(testcase);
/*     */       }
/* 195 */     } catch (Exception e) {
/* 196 */       System.err.println(e);
/*     */     } finally {
/* 198 */       if (in != null) {
/* 199 */         in.close();
/*     */       }
/*     */     } 
/* 202 */     System.out.println("done");
/*     */   }
/*     */   
/*     */   protected void execute(InputStream testfile) throws IOException {
/* 206 */     BufferedReader in = null;
/* 207 */     String testcase = null;
/*     */     try {
/* 209 */       in = new BufferedReader(new InputStreamReader(testfile));
/* 210 */       while ((testcase = in.readLine()) != null) {
/* 211 */         execute(testcase);
/*     */       }
/* 213 */     } catch (Exception e) {
/* 214 */       System.err.println(e);
/*     */     } finally {
/* 216 */       if (in != null) {
/* 217 */         in.close();
/*     */       }
/*     */     } 
/* 220 */     System.out.println("done");
/*     */   }
/*     */   
/*     */   protected void execute(String testcase) throws Exception { ServiceIDImpl serviceIDImpl;
/* 224 */     String vin = null;
/* 225 */     ServiceID verifiedServiceID = null;
/* 226 */     Map<Object, Object> selections = new HashMap<Object, Object>();
/* 227 */     StringTokenizer tokenizer = new StringTokenizer(testcase, " (),");
/* 228 */     while (tokenizer.hasMoreTokens()) {
/* 229 */       String token = tokenizer.nextToken();
/* 230 */       if (verifiedServiceID == null) {
/* 231 */         serviceIDImpl = ServiceIDImpl.getInstance(token); continue;
/* 232 */       }  if (vin == null) {
/* 233 */         vin = token; continue;
/*     */       } 
/* 235 */       int delimiter = token.indexOf('=');
/* 236 */       if (delimiter >= 0) {
/* 237 */         selections.put(token.substring(delimiter + 1), token.substring(0, delimiter)); continue;
/*     */       } 
/* 239 */       System.err.println("invalid testcase: " + testcase);
/*     */     } 
/*     */ 
/*     */     
/* 243 */     DataStorage dataStorage = new DataStorage();
/* 244 */     dataStorage.set(CommonAttribute.SESSION_ID, (Value)new ValueAdapter("SIDS"));
/* 245 */     ServiceID serviceID = null;
/*     */     while (true) {
/*     */       try {
/* 248 */         serviceID = SIDS.getServiceID(Locale.US, vin, (AttributeValueMap)dataStorage);
/* 249 */       } catch (RequestException r) {
/* 250 */         SelectionRequest request = (SelectionRequest)r.getRequest();
/* 251 */         List<Value> options = request.getOptions();
/* 252 */         for (int j = 0; j < options.size(); j++) {
/* 253 */           Value option = options.get(j);
/* 254 */           DisplayableServiceIDItem value = (DisplayableServiceIDItem)option;
/* 255 */           String label = value.getDenotation(Locale.ENGLISH);
/* 256 */           if (selections.get(label) != null) {
/* 257 */             dataStorage.set(request.getAttribute(), option);
/*     */           }
/*     */         } 
/* 260 */       } catch (NoServiceIDException n) {
/* 261 */         serviceID = SID_NONE;
/* 262 */       } catch (Exception e) {
/* 263 */         serviceID = SID_ERROR;
/*     */       } 
/* 265 */       if (serviceID != null) {
/* 266 */         if (serviceID == serviceIDImpl) {
/* 267 */           System.out.println("verified testcase: " + testcase);
/*     */         } else {
/* 269 */           System.err.println("VERIFICATION FAILED [testcase: " + testcase + "]!!!");
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     }  } public static void main(String[] args) throws Exception {
/* 274 */     initTIS2WEB();
/* 275 */     TestSIDS test = new TestSIDS();
/*     */     
/* 277 */     if (test.getClass().getResource("testvins.txt") != null) {
/* 278 */       test.execute(test.getClass().getResourceAsStream("testvins.txt"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void initTIS2WEB() {
/* 283 */     BasicConfigurator.configure();
/* 284 */     String cfg = System.getProperty("user.dir") + "\\build\\env\\" + System.getProperty("user.name") + "\\overwrite\\";
/* 285 */     FrameServiceProvider.create((ResourceLoading)new DirectoryResourceLoading(new File(cfg)));
/* 286 */     FrameService fservice = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 287 */     ClientContext cc = ClientContextProvider.getInstance().getContext("SIDS", true);
/* 288 */     cc.registerDispatchable((Dispatchable)ResourceController.getInstance(cc));
/* 289 */     fservice.setLocale(Locale.US, "SIDS");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\test\TestSIDS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */