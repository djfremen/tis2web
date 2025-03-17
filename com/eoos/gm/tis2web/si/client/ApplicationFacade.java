/*     */ package com.eoos.gm.tis2web.si.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.si.client.model.Baudrate;
/*     */ import com.eoos.gm.tis2web.si.client.model.Device;
/*     */ import com.eoos.gm.tis2web.si.client.model.IApplication;
/*     */ import com.eoos.gm.tis2web.si.client.model.IServer;
/*     */ import com.eoos.gm.tis2web.si.client.model.Port;
/*     */ import com.eoos.gm.tis2web.si.client.ui.SelectionPanel;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.propcfg.util.WriteableConfigurationFacade;
/*     */ import com.eoos.scsm.v2.util.I18NSupportV2;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ApplicationFacade
/*     */   implements IApplication
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(ApplicationFacade.class);
/*     */   
/*     */   private WriteableConfigurationFacade cfg;
/*     */   
/*     */   private LabelResource labelResource;
/*     */   private IServer serverDelegate;
/*     */   private Device device;
/*     */   private List<Port> ports;
/*     */   
/*     */   public ApplicationFacade(File homeDir, Configuration cfg, LabelResource labelResource, Locale locale, List<Port> ports) {
/*  34 */     this.cfg = WriteableConfigurationFacade.create(cfg, new File(homeDir, "settings.properties"));
/*  35 */     this.labelResource = labelResource;
/*  36 */     this.device = Device.valueOf(cfg.getProperty("mode"));
/*  37 */     SessionKey sessionKey = new SessionKey(cfg.getProperty("session.id"));
/*  38 */     this.serverDelegate = new ServerFacade(sessionKey);
/*  39 */     this.ports = ports;
/*     */   }
/*     */   
/*     */   public String getLabel(String key) {
/*  43 */     return this.labelResource.getLabel(key);
/*     */   }
/*     */   
/*     */   public String getMessage(String key) {
/*  47 */     return this.labelResource.getMessage(key);
/*     */   }
/*     */   
/*     */   public IServer getServerInterface() {
/*  51 */     return this.serverDelegate;
/*     */   }
/*     */   
/*     */   private Port getSelectedPort() {
/*  55 */     return ClientUtil.toPort(this.cfg.getProperty("port"));
/*     */   }
/*     */   
/*     */   private void setSelectedPort(Port port) {
/*  59 */     this.cfg.setProperty("port", port.toString());
/*     */   }
/*     */   
/*     */   private int getSelectedScreen() {
/*  63 */     Number ret = ConfigurationUtil.getNumber("screen", (Configuration)this.cfg);
/*  64 */     return (ret != null) ? ret.intValue() : -1;
/*     */   }
/*     */   
/*     */   private void setSelectedScreen(int screen) {
/*  68 */     this.cfg.setProperty("screen", String.valueOf(screen));
/*     */   }
/*     */   
/*     */   public void executeTransfer() throws FeedbackException, InterruptedException {
/*  72 */     log.debug("executing transfer...");
/*  73 */     String identifier = this.cfg.getProperty("data.id");
/*  74 */     if (Util.isNullOrEmpty(identifier)) {
/*  75 */       throw new FeedbackException("error.no.identifier");
/*     */     }
/*  77 */     log.debug("...requesting data for id:" + identifier);
/*     */     
/*  79 */     byte[] data = null;
/*     */     try {
/*  81 */       data = getServerInterface().getScreenData(identifier);
/*  82 */     } catch (Exception e) {
/*  83 */       throw new FeedbackException("error.unable.to.download.data", e);
/*     */     } 
/*  85 */     if (Util.isNullOrEmpty(data)) {
/*  86 */       throw new FeedbackException("error.empty.data");
/*     */     }
/*  88 */     log.debug("...retrieved data (size:" + Util.formatBytesSI(data.length, "${bytes} B") + ")");
/*  89 */     log.debug("...requesting screen and communication port from user");
/*     */     
/*  91 */     SelectionPanel.Input input = SelectionPanel.getInput(this.device, getSelectedScreen(), getSelectedPort(), getSelectedBaudrate(), new SelectionPanel.Callback()
/*     */         {
/*     */           public String getText(String key, I18NSupportV2.Type type) {
/*  94 */             return ApplicationFacade.this.labelResource.getText(key, type);
/*     */           }
/*     */           
/*     */           public int getAvailableScreenCount() {
/*  98 */             return ApplicationFacade.this.device.getScreenCount();
/*     */           }
/*     */           
/*     */           public List<Port> getAvailablePorts() {
/* 102 */             return ApplicationFacade.this.ports;
/*     */           }
/*     */           
/*     */           public List<Baudrate> getAvailableRates() {
/* 106 */             return ApplicationFacade.this.device.getBaudrates();
/*     */           }
/*     */         });
/* 109 */     if (input != null) {
/* 110 */       setSelectedPort(input.getSelectedPort());
/* 111 */       setSelectedScreen(input.getSelectedScreen());
/* 112 */       setSelectedBaudrate(input.getSelectedBaudrate());
/*     */       
/* 114 */       log.debug("...selected screen : " + input.getSelectedScreen());
/* 115 */       log.debug("...selected port : " + input.getSelectedPort());
/* 116 */       log.debug("...selected baudrate : " + input.getSelectedBaudrate());
/* 117 */       log.debug("...writing data");
/*     */       try {
/* 119 */         this.device.storeDeviceScreen(input.getSelectedPort(), input.getSelectedBaudrate(), input.getSelectedScreen(), data);
/* 120 */       } catch (Exception e) {
/* 121 */         throw new FeedbackException("error.unable.to.write", e);
/*     */       } 
/*     */     } else {
/* 124 */       throw new InterruptedException();
/*     */     } 
/* 126 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   private Baudrate getSelectedBaudrate() {
/* 130 */     return ClientUtil.toBaudrate(this.cfg.getProperty("baudrate"));
/*     */   }
/*     */   
/*     */   private void setSelectedBaudrate(Baudrate baudrate) {
/* 134 */     this.cfg.setProperty("baudrate", baudrate.toString());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\ApplicationFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */