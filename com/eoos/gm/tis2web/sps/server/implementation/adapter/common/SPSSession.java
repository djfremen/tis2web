/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSSession
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(SPSSession.class); protected long tag; protected long signature; protected SPSModel model; protected SPSLanguage language; protected SPSVehicle vehicle; protected SPSControllerList controllers; protected SPSControllerReference reference; protected SPSController controller;
/*     */   protected SPSProgrammingType method;
/*     */   protected SPSProgrammingData data;
/*     */   protected Value mode;
/*     */   protected boolean clearDTCs;
/*     */   protected int vehicleLink;
/*     */   protected String sessionID;
/*     */   
/*     */   private static final class MyLogoutListener implements ClientContextBase.LogoutListener { private final SPSSession session;
/*     */     
/*     */     public MyLogoutListener(SPSSession session, ClientContext context, Adapter adapter) {
/*  32 */       this.session = session;
/*  33 */       this.context = context;
/*  34 */       this.adapter = adapter;
/*     */     }
/*     */     private ClientContext context; private Adapter adapter;
/*     */     public void onLogout() {
/*  38 */       synchronized (this.session) {
/*  39 */         if (!this.session.isFinished()) {
/*  40 */           SPSEvents.logUnfinishedSession(this.adapter, this.session.sessionID, this.session);
/*  41 */           this.session.markFinished();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientContext getContext() {
/*  48 */       return this.context;
/*     */     } }
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
/*     */   protected boolean requireControllerVIT1 = true;
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
/*     */   protected boolean finished = false;
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
/*     */   private MyLogoutListener logoutListener;
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
/*     */   public long getTag() {
/*  95 */     return this.tag;
/*     */   }
/*     */   
/*     */   public long getSignature() {
/*  99 */     return this.signature;
/*     */   }
/*     */   
/*     */   protected SPSModel getModel() {
/* 103 */     return this.model;
/*     */   }
/*     */   
/*     */   public SPSLanguage getLanguage() {
/* 107 */     return this.language;
/*     */   }
/*     */   
/*     */   public SPSVehicle getVehicle() {
/* 111 */     return this.vehicle;
/*     */   }
/*     */   
/*     */   public Value getMode() {
/* 115 */     return this.mode;
/*     */   }
/*     */   
/*     */   public SPSControllerReference getControllerReference() {
/* 119 */     return this.reference;
/*     */   }
/*     */   
/*     */   public SPSController getController() {
/* 123 */     return this.controller;
/*     */   }
/*     */   
/*     */   public SPSProgrammingType getProgrammingType() {
/* 127 */     return this.method;
/*     */   }
/*     */   
/*     */   public void setClearDTCFlag(boolean clearDTCs) {
/* 131 */     this.clearDTCs = clearDTCs;
/*     */   }
/*     */   
/*     */   public boolean getClearDTCFlag() {
/* 135 */     return this.clearDTCs;
/*     */   }
/*     */   
/*     */   public void setVehicleLink(int vehicleLink) {
/* 139 */     this.vehicleLink = vehicleLink;
/*     */   }
/*     */   
/*     */   public int getVehicleLink() {
/* 143 */     return this.vehicleLink;
/*     */   }
/*     */   
/*     */   public boolean requiresControllerVIT1() {
/* 147 */     return this.requireControllerVIT1;
/*     */   }
/*     */   
/*     */   public void recordRequestControllerVIT1() {
/* 151 */     this.requireControllerVIT1 = false;
/*     */   }
/*     */   
/*     */   public void update(SPSSchemaAdapter adapter) throws Exception {
/* 155 */     if (this.data != null) {
/* 156 */       this.controller.update(adapter);
/* 157 */     } else if (this.controller == null) {
/*     */       
/* 159 */       if (this.reference != null) {
/*     */         
/* 161 */         this.controller = this.reference.update(this.vehicle.getOptions(), adapter);
/* 162 */         this.data = null;
/* 163 */       } else if (this.controllers != null) {
/* 164 */         this.controllers.update(this.vehicle.getOptions(), adapter);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public SPSControllerList getControllers() {
/* 169 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public SPSControllerList getControllers(List devices, Value mode, String toolType) throws Exception {
/* 173 */     if (this.controllers == null) {
/*     */ 
/*     */       
/* 176 */       this.controllers = this.model.getControllers(this, devices, mode, toolType);
/* 177 */       this.mode = mode;
/*     */     } 
/* 179 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public boolean setController(AttributeValueMap data, SPSControllerReference reference, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/*     */     try {
/* 184 */       this.reference = reference;
/* 185 */       this.method = method;
/* 186 */       SPSController result = reference.qualify(data, method, adapter);
/*     */       
/* 188 */       if (result != null) {
/* 189 */         this.controller = result;
/* 190 */         this.data = null;
/*     */         
/* 192 */         return true;
/*     */       } 
/* 194 */       if (this.vehicle.getOptions() != null && reference.getOptions() != null) {
/* 195 */         result = reference.update(this.vehicle.getOptions(), adapter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 202 */         if (result != null) {
/* 203 */           this.controller = result;
/* 204 */           return true;
/*     */         } 
/*     */       } 
/* 207 */       return false;
/*     */     }
/* 209 */     catch (Exception e) {
/* 210 */       if (e instanceof com.eoos.gm.tis2web.sps.service.cai.RequestException)
/* 211 */         throw e; 
/* 212 */       if (e instanceof SPSException) {
/* 213 */         if (((SPSException)e).getMessage().equals(CommonException.UnknownSoftware.getID()))
/* 214 */           throw e; 
/* 215 */         if (((SPSException)e).getMessage().equals(CommonException.UnknownVIN.getID()))
/* 216 */           throw e; 
/* 217 */         if (((SPSException)e).getMessage().equals(CommonException.UnknownHardware.getID()))
/* 218 */           throw e; 
/* 219 */         if (((SPSException)e).getMessage().equals(CommonException.InvalidHardware.getID())) {
/* 220 */           throw e;
/*     */         }
/*     */       } 
/* 223 */       throw new SPSException(CommonException.UnsupportedController);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/* 228 */     if (this.data == null && this.controller != null) {
/* 229 */       this.data = this.controller.getProgrammingData(adapter);
/*     */     }
/* 231 */     return this.data;
/*     */   }
/*     */   
/*     */   void reset(Attribute attribute, SPSSchemaAdapter adapter) throws Exception {
/* 235 */     if (attribute == null) {
/* 236 */       this.controllers = null;
/* 237 */       this.reference = null;
/* 238 */       this.controller = null;
/* 239 */       this.method = null;
/* 240 */       this.data = null;
/* 241 */       this.requireControllerVIT1 = true;
/* 242 */     } else if (attribute.equals(CommonAttribute.CONTROLLER)) {
/* 243 */       if (this.reference != null) {
/* 244 */         this.reference.reset();
/* 245 */         this.controllers.update(this.vehicle.getOptions(), adapter);
/*     */       } 
/* 247 */       if (this.controller != null) {
/* 248 */         this.controller.setHardware(null);
/*     */       }
/* 250 */       this.reference = null;
/* 251 */       this.controller = null;
/* 252 */       this.method = null;
/* 253 */       this.data = null;
/* 254 */       this.vehicle.setVIT1(null);
/* 255 */       this.requireControllerVIT1 = true;
/* 256 */     } else if (attribute.equals(CommonAttribute.PROGRAMMING_DATA_SELECTION)) {
/* 257 */       if (this.data != null) {
/* 258 */         if (this.vehicle.getVIT1() == null || this.vehicle.getVIT1().getHWNumber() == null) {
/* 259 */           this.controller.setHardware(null);
/* 260 */         } else if (this.controller.getHardware() != null) {
/* 261 */           SPSPart hardware = this.controller.getHardware().get(0);
/* 262 */           if (!hardware.getPartNumber().equals(this.vehicle.getVIT1().getHWNumber())) {
/* 263 */             this.controller.setHardware(null);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 268 */         this.data = null;
/*     */       } 
/* 270 */     } else if (attribute.equals(CommonAttribute.HARDWARE)) {
/* 271 */       this.controller.setHardware(null);
/* 272 */       this.data = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fixDataStorage(AttributeValueMap data) {
/* 277 */     SPSControllerReference reference = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/* 278 */     if (reference != null) {
/* 279 */       boolean npe = false;
/* 280 */       for (int i = 0; i < this.controllers.size(); i++) {
/* 281 */         SPSControllerReference target = (SPSControllerReference)this.controllers.get(i);
/* 282 */         if (target == null) {
/* 283 */           if (!npe) {
/*     */             
/*     */             try {
/*     */               
/* 287 */               log.error("no target controller (session='" + this.sessionID + ",status=" + this.finished + ",vin=" + this.vehicle.getVIN() + ")");
/* 288 */             } catch (Exception x) {
/* 289 */               log.error("no target controller (session='" + this.sessionID + ",status=" + this.finished + ")");
/*     */             }
/*     */           
/*     */           }
/* 293 */         } else if (target.equals(reference) && target != reference) {
/*     */ 
/*     */           
/* 296 */           data.exchange(CommonAttribute.CONTROLLER, (Value)target);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/* 303 */     return this.sessionID;
/*     */   }
/*     */   
/* 306 */   public SPSSession(long signature, long tag, SPSModel model, SPSLanguage language, SPSVehicle vehicle, String sessionID) { this.logoutListener = null; this.signature = signature; this.tag = tag; this.model = model;
/*     */     this.language = language;
/*     */     this.vehicle = vehicle;
/* 309 */     this.sessionID = sessionID; } public synchronized void registerLogoutListener(ClientContext context, Adapter adapter) { if (this.logoutListener == null) {
/* 310 */       this.logoutListener = new MyLogoutListener(this, context, adapter);
/* 311 */       context.addLogoutListener(this.logoutListener);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void markFinished() {
/* 317 */     this.finished = true;
/* 318 */     if (this.logoutListener != null) {
/* 319 */       final MyLogoutListener ll = this.logoutListener;
/* 320 */       Util.executeAsynchronous(new Runnable()
/*     */           {
/*     */             public void run() {
/* 323 */               ll.getContext().removeLogoutListener(ll);
/*     */             }
/*     */           });
/* 326 */       this.logoutListener = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized boolean isFinished() {
/* 331 */     return this.finished;
/*     */   }
/*     */   
/*     */   public void done() {
/* 335 */     this.controllers = null;
/* 336 */     this.reference = null;
/* 337 */     this.controller = null;
/* 338 */     this.method = null;
/* 339 */     this.data = null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSSession.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */