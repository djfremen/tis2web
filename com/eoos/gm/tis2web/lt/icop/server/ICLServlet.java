/*     */ package com.eoos.gm.tis2web.lt.icop.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LaborOpFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDesc;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDescriptionFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.AWAdapter;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.InvalidPlatformException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.UnspecifiedException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.VehDescWrapper;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.icl.ICLContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ICLServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  37 */   private static final Logger log = Logger.getLogger(ICLServlet.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*     */     try {
/*  46 */       String inst = ApplicationContext.getInstance().getProperty("frame.installation.type");
/*  47 */       if (inst == null || (inst.compareToIgnoreCase("standalone") != 0 && inst.compareToIgnoreCase("server") != 0)) {
/*  48 */         throw new InvalidPlatformException(inst);
/*     */       }
/*     */       
/*  51 */       log.debug("received request: " + request.getQueryString());
/*  52 */       long ts = (new Date()).getTime();
/*  53 */       String reqId = "IclServlet_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/*  54 */       Locale locale = Locale.US;
/*     */       try {
/*  56 */         locale = new Locale(request.getParameter("LANG"));
/*  57 */       } catch (Exception e) {
/*  58 */         log.debug("Invalid or missing LANG parameter (using en_US instead): " + request.getParameter("LANG"));
/*     */       } 
/*     */       
/*  61 */       VehDescWrapper vehDescWrapper = getVehDescWrapper(request);
/*  62 */       vehDescWrapper.validateVehDesc(locale, true);
/*  63 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/*  64 */       ClientContext clientContext = ClientContextProvider.getInstance().getTmpContext(reqId, locale);
/*  65 */       LTClientContext ltClientContext = getContext(clientContext, request.getParameter("COUNTRY"), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), true);
/*  66 */       AWAdapter awAdapter = new AWAdapter();
/*  67 */       awAdapter.validateOpNumber(request.getParameter("MAJOPNR"), ltClientContext);
/*  68 */       getContext(clientContext, request.getParameter("COUNTRY"), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), false);
/*  69 */       ICLContext iclContext = new ICLContext(clientContext, request.getParameter("MAJOPNR"));
/*  70 */       byte[] blob = iclContext.getChecklist();
/*  71 */       if (blob == null || blob.length == 0) {
/*  72 */         log.debug("returning status :no content");
/*  73 */         response.setStatus(204);
/*     */       } else {
/*  75 */         ServletOutputStream servletOutputStream = null;
/*  76 */         response.setContentType("application/pdf");
/*     */         
/*  78 */         String encoding = request.getHeader("Accept-Encoding");
/*  79 */         if (encoding != null && encoding.indexOf("gzip") != -1) {
/*  80 */           log.debug("compressing response");
/*  81 */           blob = ZipUtil.gzip(blob);
/*  82 */           response.setHeader("Content-Encoding", "gzip");
/*     */         } 
/*  84 */         response.setContentLength(blob.length);
/*  85 */         servletOutputStream = response.getOutputStream();
/*  86 */         servletOutputStream.write(blob);
/*  87 */         servletOutputStream.close();
/*  88 */         long pt = (new Date()).getTime() - ts;
/*  89 */         log.debug("ICL request served  [" + reqId + "]: " + pt + " ms");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*  94 */     } catch (InvalidPlatformException invEx) {
/*  95 */       log.debug("ICL servlet request denied on platform: " + invEx.getInstType());
/*  96 */     } catch (VehDescriptionFault invVeh) {
/*  97 */       log.debug("Invalid vehicle description: " + invVeh.getFaultInfo().getInvalidAttrCode());
/*  98 */     } catch (LaborOpFault invOp) {
/*  99 */       log.debug("Invalid laborOp: " + invOp.getFaultInfo().getLaborOp());
/* 100 */     } catch (Exception e) {
/* 101 */       log.error("Exception in ICLServlet: " + e.toString());
/* 102 */       e.printStackTrace();
/*     */     } 
/* 104 */     String responseStr = "No valid Vehicle (Checklist) found for URL";
/* 105 */     response.setContentType("text/plain");
/* 106 */     response.setContentLength(responseStr.length());
/* 107 */     ServletOutputStream out = response.getOutputStream();
/* 108 */     out.print(responseStr);
/* 109 */     out.close();
/*     */   }
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 113 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 117 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   private LTClientContext getContext(ClientContext context, String country, IConfiguration vehConfig, String vin, boolean enforceLTHours) throws UnspecifiedException {
/* 121 */     LTClientContext result = null;
/* 122 */     context.getSharedContext().setCountry(country);
/* 123 */     context.getSharedContext().setUseLTHours(new Boolean(enforceLTHours));
/*     */     try {
/* 125 */       VCFacade.getInstance(context).storeCfg(vehConfig, (vin != null) ? (VIN)new VINImpl(vin) : null);
/* 126 */     } catch (Exception e) {
/* 127 */       log.debug("Unexpected exception (1): " + e, e);
/* 128 */       throw new UnspecifiedException();
/*     */     } 
/* 130 */     result = LTClientContext.getInstance(context);
/*     */     try {
/* 132 */       result.setVCR();
/* 133 */     } catch (Exception e) {
/* 134 */       log.debug("Unexpected exception (2): " + e, e);
/* 135 */       throw new UnspecifiedException();
/*     */     } 
/* 137 */     return result;
/*     */   }
/*     */   
/*     */   private VehDescWrapper getVehDescWrapper(HttpServletRequest request) throws UnsupportedEncodingException {
/* 141 */     VehDescWrapper result = null;
/* 142 */     VehDesc vehDesc = new VehDesc();
/* 143 */     if (request.getParameter("VIN") != null) {
/* 144 */       vehDesc.setVin(request.getParameter("VIN"));
/*     */     }
/* 146 */     if (request.getParameter("MAKE") != null) {
/* 147 */       vehDesc.setMake(request.getParameter("MAKE"));
/* 148 */       vehDesc.setDefaultmake(request.getParameter("MAKE"));
/*     */     } 
/* 150 */     if (request.getParameter("MODEL") != null) {
/* 151 */       vehDesc.setModel(request.getParameter("MODEL"));
/*     */     }
/* 153 */     if (request.getParameter("MODELYEAR") != null) {
/* 154 */       vehDesc.setYear(request.getParameter("MODELYEAR"));
/*     */     }
/* 156 */     if (request.getParameter("ENGINE") != null) {
/* 157 */       String engineStr = URLDecoder.decode(request.getParameter("ENGINE"), "utf-8");
/* 158 */       vehDesc.setEngine(engineStr);
/*     */     } 
/* 160 */     if (request.getParameter("TRANSMISSION") != null) {
/* 161 */       String transStr = URLDecoder.decode(request.getParameter("TRANSMISSION"), "utf-8");
/* 162 */       vehDesc.setTransmission(transStr);
/*     */     } 
/* 164 */     if (request.getParameter("DEFAULTMAKE") != null) {
/* 165 */       vehDesc.setDefaultmake(request.getParameter("DEFAULTMAKE"));
/*     */     }
/* 167 */     result = new VehDescWrapper(vehDesc);
/* 168 */     return result;
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 172 */     if (req.getProtocol().endsWith("1.1")) {
/* 173 */       resp.sendError(405, "");
/*     */     } else {
/* 175 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 180 */     if (req.getProtocol().endsWith("1.1")) {
/* 181 */       resp.sendError(405, "");
/*     */     } else {
/* 183 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 188 */     if (req.getProtocol().endsWith("1.1")) {
/* 189 */       resp.sendError(405, "");
/*     */     } else {
/* 191 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 196 */     if (req.getProtocol().endsWith("1.1")) {
/* 197 */       resp.sendError(405, "");
/*     */     } else {
/* 199 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 204 */     if (req.getProtocol().endsWith("1.1")) {
/* 205 */       resp.sendError(405, "");
/*     */     } else {
/* 207 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\server\ICLServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */