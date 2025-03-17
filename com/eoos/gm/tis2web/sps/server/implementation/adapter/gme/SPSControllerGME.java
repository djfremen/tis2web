/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*      */ 
/*      */ import com.eoos.datatype.gtwo.Pair;
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*      */ import com.eoos.jdbc.JDBCUtil;
/*      */ import com.eoos.util.StringUtilities;
/*      */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSControllerGME
/*      */   implements SPSController {
/*      */   private static final long serialVersionUID = 1L;
/*   37 */   private static Logger log = Logger.getLogger(SPSModel.class); protected transient SPSSession session; protected transient List hardware; protected transient List predecessors; protected transient SPSSoftware calibration; protected transient SPSSoftware software; protected transient List history; protected transient List vcategories; protected transient Archive archive; protected String description; protected Integer id; protected Integer ecu; protected Integer system; protected Integer vehicle; protected List preProgrammingInstructions; protected List postProgrammingInstructions;
/*      */   protected boolean asbuilt;
/*      */   protected int vinid;
/*      */   protected int vci;
/*      */   protected Object data;
/*      */   protected transient SPSSchemaAdapterGME adapter;
/*      */   
/*      */   public static class Descriptions { private Map descriptions;
/*      */     private Map functions;
/*      */     
/*      */     private Descriptions(SPSSchemaAdapterGME adapter) {
/*   48 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*   49 */       this.descriptions = new HashMap<Object, Object>();
/*   50 */       Connection conn = null;
/*   51 */       DBMS.PreparedStatement stmt = null;
/*   52 */       ResultSet rs = null;
/*      */       try {
/*   54 */         conn = dblink.requestConnection();
/*   55 */         String sql = DBMS.getSQL(dblink, "SELECT ControllerID, LanguageID, Description, ShortDescr FROM SPS_Controller");
/*   56 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*   57 */         rs = stmt.executeQuery();
/*   58 */         while (rs.next()) {
/*   59 */           Integer id = Integer.valueOf(rs.getInt(1));
/*   60 */           String lg = rs.getString(2).trim();
/*   61 */           SPSDescription description = (SPSDescription)this.descriptions.get(id);
/*   62 */           if (description == null) {
/*   63 */             description = new SPSDescription();
/*   64 */             this.descriptions.put(id, description);
/*      */           } 
/*   66 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*   67 */           String label = DBMS.getString(dblink, language, rs, 4) + '\t' + DBMS.getString(dblink, language, rs, 3);
/*   68 */           description.add(language, label);
/*      */         } 
/*   70 */         SPSControllerGME.log.info("loaded gme controller descriptions (" + this.descriptions.size() + " controller-ids).");
/*   71 */       } catch (RuntimeException e) {
/*   72 */         throw e;
/*   73 */       } catch (Exception e) {
/*   74 */         throw new RuntimeException(e);
/*      */       } finally {
/*      */         try {
/*   77 */           if (rs != null) {
/*   78 */             rs.close();
/*      */           }
/*   80 */           if (stmt != null) {
/*   81 */             stmt.close();
/*      */           }
/*   83 */           if (conn != null && 
/*   84 */             !adapter.supportsSPSFunctions()) {
/*   85 */             dblink.releaseConnection(conn);
/*      */           }
/*      */         }
/*   88 */         catch (Exception x) {
/*   89 */           SPSControllerGME.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*   92 */       if (!adapter.supportsSPSFunctions()) {
/*      */         return;
/*      */       }
/*   95 */       this.functions = new HashMap<Object, Object>();
/*   96 */       this.controllers = new HashMap<Object, Object>();
/*      */       try {
/*   98 */         String sql = DBMS.getSQL(dblink, "SELECT ControllerID, FunctionID, LanguageID, Description, ShortDescr FROM SPS_Function");
/*   99 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  100 */         rs = stmt.executeQuery();
/*  101 */         while (rs.next()) {
/*  102 */           Integer cid = new Integer(rs.getInt(1));
/*  103 */           Integer fid = new Integer(rs.getInt(2));
/*  104 */           this.controllers.put(fid, cid);
/*  105 */           String lg = rs.getString(3).trim();
/*  106 */           SPSFunctions flist = (SPSFunctions)this.functions.get(cid);
/*  107 */           if (flist == null) {
/*  108 */             flist = new SPSFunctions(cid);
/*  109 */             this.functions.put(cid, flist);
/*      */           } 
/*  111 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*  112 */           String label = DBMS.getString(dblink, language, rs, 5) + '\t' + DBMS.getString(dblink, language, rs, 4);
/*  113 */           flist.add(fid, language, label);
/*      */         } 
/*  115 */         SPSControllerGME.log.info("loaded gme controller functions.");
/*  116 */       } catch (RuntimeException r11704) {
/*      */ 
/*      */       
/*  119 */       } catch (Exception e11704) {
/*      */ 
/*      */       
/*      */       } finally {
/*      */         try {
/*  124 */           if (rs != null) {
/*  125 */             rs.close();
/*      */           }
/*  127 */           if (stmt != null) {
/*  128 */             stmt.close();
/*      */           }
/*  130 */         } catch (Exception x) {
/*  131 */           SPSControllerGME.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*  134 */       this.displays = new HashMap<Object, Object>();
/*      */       try {
/*  136 */         String sql = DBMS.getSQL(dblink, "SELECT SequenceID, LanguageID, Description, DisplayOrder FROM SPS_SeqDisplay");
/*  137 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  138 */         rs = stmt.executeQuery();
/*  139 */         while (rs.next()) {
/*  140 */           Integer sid = new Integer(rs.getInt(1));
/*  141 */           String lg = rs.getString(2).trim();
/*  142 */           SPSLanguage language = SPSLanguage.getLanguage(lg, adapter);
/*  143 */           String label = DBMS.getString(dblink, language, rs, 3);
/*  144 */           Integer order = new Integer(rs.getInt(4));
/*  145 */           SPSSequenceDisplay display = (SPSSequenceDisplay)this.displays.get(sid);
/*  146 */           if (display == null) {
/*  147 */             display = new SPSSequenceDisplay(sid);
/*  148 */             this.displays.put(sid, display);
/*      */           } 
/*  150 */           display.add(language, label, order);
/*      */         } 
/*  152 */         SPSControllerGME.log.info("loaded gme controller sequence displays.");
/*  153 */       } catch (RuntimeException r11704) {
/*      */ 
/*      */       
/*  156 */       } catch (Exception e11704) {
/*      */ 
/*      */       
/*      */       } finally {
/*      */         try {
/*  161 */           if (rs != null) {
/*  162 */             rs.close();
/*      */           }
/*  164 */           if (stmt != null) {
/*  165 */             stmt.close();
/*      */           }
/*  167 */         } catch (Exception x) {
/*  168 */           SPSControllerGME.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*  171 */       this.sequences = new HashMap<Object, Object>();
/*      */       try {
/*  173 */         String sql = DBMS.getSQL(dblink, "SELECT SequenceID, FunctionID, ReqInfoID, FunctionOrder, OnSameSW FROM SPS_Sequence ORDER BY 1,2,4");
/*  174 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  175 */         rs = stmt.executeQuery();
/*  176 */         while (rs.next()) {
/*  177 */           Integer sid = new Integer(rs.getInt(1));
/*  178 */           Integer fid = new Integer(rs.getInt(2));
/*  179 */           Integer rid = new Integer(rs.getInt(3));
/*  180 */           Integer order = new Integer(rs.getInt(4));
/*  181 */           String onSameSW = rs.getString(5).trim();
/*  182 */           Integer cid = null;
/*  183 */           Iterator<SPSFunctions> it = this.functions.values().iterator();
/*  184 */           while (cid == null && it.hasNext()) {
/*  185 */             SPSFunctions flist = it.next();
/*  186 */             for (int i = 0; i < flist.size(); i++) {
/*  187 */               SPSFunction sPSFunction = (SPSFunction)flist.get(i);
/*  188 */               if (sPSFunction.getFunctionID().equals(fid)) {
/*  189 */                 cid = flist.getControllerID();
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  194 */           if (cid == null) {
/*  195 */             throw new IllegalStateException("sps-sequence: function-id '" + fid + "' undefined.");
/*      */           }
/*  197 */           SPSSequence sequence = (SPSSequence)this.sequences.get(sid);
/*  198 */           if (sequence == null) {
/*  199 */             sequence = new SPSSequence(sid);
/*  200 */             sequence.setDisplay((SPSSequenceDisplay)this.displays.get(sid));
/*  201 */             this.sequences.put(sid, sequence);
/*      */           } 
/*  203 */           SPSSequenceFunction function = new SPSSequenceFunction(cid, fid, rid, order, onSameSW);
/*  204 */           sequence.add(function);
/*      */         } 
/*  206 */         SPSControllerGME.log.info("loaded " + this.sequences.size() + " gme controller sequences.");
/*  207 */       } catch (RuntimeException r11704) {
/*      */       
/*  209 */       } catch (Exception e11704) {
/*      */       
/*      */       } finally {
/*      */         try {
/*  213 */           if (rs != null) {
/*  214 */             rs.close();
/*      */           }
/*  216 */           if (stmt != null) {
/*  217 */             stmt.close();
/*      */           }
/*  219 */           if (conn != null) {
/*  220 */             dblink.releaseConnection(conn);
/*      */           }
/*  222 */         } catch (Exception x) {
/*  223 */           SPSControllerGME.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*  226 */       this.displays = null;
/*      */     }
/*      */     private Map sequences; private Map displays; private Map controllers;
/*      */     public static Descriptions getInstance(SPSSchemaAdapterGME adapter) {
/*  230 */       synchronized (adapter.getSyncObject()) {
/*  231 */         Descriptions instance = (Descriptions)adapter.getObject(Descriptions.class);
/*  232 */         if (instance == null) {
/*  233 */           instance = new Descriptions(adapter);
/*  234 */           adapter.storeObject(Descriptions.class, instance);
/*      */         } 
/*  236 */         return instance;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Map getMap() {
/*  241 */       return this.descriptions;
/*      */     }
/*      */     
/*      */     public Map getFunctions() {
/*  245 */       return this.functions;
/*      */     }
/*      */     
/*      */     public Map getSequences() {
/*  249 */       return this.sequences;
/*      */     }
/*      */     
/*      */     public Integer getControllerID(Integer functionID) {
/*  253 */       return (Integer)this.controllers.get(functionID);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SPSControllerGME(SPSSession session, Integer vehicle, Integer id, Integer system, Integer ecu, SPSSchemaAdapterGME adapter) {
/*  296 */     this.session = session;
/*  297 */     this.adapter = adapter;
/*  298 */     this.vehicle = vehicle;
/*  299 */     this.id = id;
/*  300 */     this.system = system;
/*  301 */     this.ecu = ecu;
/*  302 */     if (id != null) {
/*  303 */       this.description = getDescription((SPSLanguage)session.getLanguage(), id, adapter);
/*  304 */       if (this.description == null) {
/*  305 */         log.error("failed to look-up controller description (controller=" + id + ",locale=" + session.getLanguage().getLocale() + ")");
/*  306 */         if (Descriptions.getInstance(adapter).getMap() == null) {
/*  307 */           log.info("no controller descriptions available.");
/*      */         } else {
/*  309 */           log.info("controller descriptions available: " + Descriptions.getInstance(adapter).getMap().size());
/*      */         } 
/*  311 */         this.description = "controller label missing (controller-ID=" + id + ")";
/*      */       } 
/*      */     } else {
/*  314 */       this.description = "temporary controller instance";
/*      */     } 
/*      */   }
/*      */   
/*      */   public void flagAsBuiltController() {
/*  319 */     this.asbuilt = true;
/*      */   }
/*      */   
/*      */   public boolean hasAsBuiltSupport(int device) {
/*  323 */     return (this.asbuilt && device == this.system.intValue());
/*      */   }
/*      */   
/*      */   public boolean isAsBuiltController() {
/*  327 */     return this.asbuilt;
/*      */   }
/*      */   
/*      */   public boolean isAsBuiltController(int ecu) {
/*  331 */     return (this.asbuilt && ecu == this.ecu.intValue());
/*      */   }
/*      */   
/*      */   public void setVINID(int vinid) {
/*  335 */     this.vinid = vinid;
/*      */   }
/*      */   
/*      */   public void setVCI(int vci) {
/*  339 */     this.vci = vci;
/*      */   }
/*      */   
/*      */   public int getVCI() {
/*  343 */     return this.vci;
/*      */   }
/*      */   
/*      */   public boolean checkAsBuiltController(int ecu) {
/*  347 */     return checkAsBuiltController(new HashSet(), ecu);
/*      */   }
/*      */   
/*      */   private boolean checkAsBuiltController(Set<SPSControllerGME> controllers, int ecu) {
/*  351 */     if (isAsBuiltController(ecu)) {
/*  352 */       return true;
/*      */     }
/*  354 */     controllers.add(this);
/*  355 */     if (this.predecessors != null) {
/*  356 */       for (int i = 0; i < this.predecessors.size(); i++) {
/*  357 */         SPSControllerGME controller = this.predecessors.get(i);
/*  358 */         if (!controllers.contains(controller) && controller.checkAsBuiltController(controllers, ecu)) {
/*  359 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  363 */     return false;
/*      */   }
/*      */   
/*      */   public int getVINID() {
/*  367 */     if (this.vinid > 0) {
/*  368 */       return this.vinid;
/*      */     }
/*  370 */     if (this.predecessors != null) {
/*  371 */       for (int i = 1; i < this.predecessors.size(); i++) {
/*  372 */         SPSControllerGME controller = this.predecessors.get(i);
/*  373 */         if (controller.getVINID() > 0) {
/*  374 */           return controller.getVINID();
/*      */         }
/*      */       } 
/*      */     }
/*  378 */     return -1;
/*      */   }
/*      */   
/*      */   public void setHardware(SPSPart part) {
/*  382 */     if (part == null) {
/*  383 */       this.hardware = null;
/*      */     } else {
/*  385 */       SPSHardware hw = (this.hardware != null) ? this.hardware.get(0) : null;
/*  386 */       this.hardware = new ArrayList();
/*  387 */       this.software = null;
/*  388 */       this.history = null;
/*  389 */       this.hardware.add(part);
/*  390 */       if (hw != null && !hw.equals(part)) {
/*      */         try {
/*  392 */           checkHardware();
/*  393 */         } catch (Exception x) {}
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List getHardware() {
/*  400 */     return this.hardware;
/*      */   }
/*      */   
/*      */   public void setControllerData(Object data) {
/*  404 */     this.data = data;
/*      */   }
/*      */   
/*      */   public int getID() {
/*  408 */     return this.ecu.intValue();
/*      */   }
/*      */   
/*      */   public Integer getControllerID() {
/*  412 */     return this.id;
/*      */   }
/*      */   
/*      */   public Integer getVehicleID() {
/*  416 */     return this.vehicle;
/*      */   }
/*      */   
/*      */   public Integer getSystemTypeID() {
/*  420 */     return this.system;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  424 */     return this.description;
/*      */   }
/*      */   
/*      */   public String getLabel() {
/*  428 */     return getLabel(this.id, this.adapter);
/*      */   }
/*      */   
/*      */   public int getDeviceID() {
/*  432 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public int getRequestMethodID() {
/*  436 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public List getProgrammingTypes() {
/*  440 */     return SPSProgrammingType.getProgrammingTypes(this.session, this.adapter);
/*      */   }
/*      */   
/*      */   public List getPreProgrammingInstructions() {
/*  444 */     return this.preProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public List getPostProgrammingInstructions() {
/*  448 */     return this.postProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public List getPostSelectionOptions() throws Exception {
/*  452 */     return filterOptions();
/*      */   }
/*      */   
/*      */   List getOptionCategories() throws Exception {
/*  456 */     return this.vcategories;
/*      */   }
/*      */   
/*      */   public List getPreSelectionOptions() throws Exception {
/*  460 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public void setArchive(Archive archive) {
/*  464 */     if (this.archive != null && this.archive.getChangeReason().equals(archive.getChangeReason())) {
/*      */       return;
/*      */     }
/*  467 */     this.archive = archive;
/*      */   }
/*      */   
/*      */   public Archive getArchive() {
/*  471 */     return this.archive;
/*      */   }
/*      */   
/*      */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/*  475 */     return determineProgrammingData();
/*      */   }
/*      */   
/*      */   public void update(SPSSchemaAdapter adapter) throws Exception {
/*  479 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public Object getControllerData() {
/*  483 */     return this.data;
/*      */   }
/*      */   
/*      */   List getHistory() throws Exception {
/*  487 */     buildHistory();
/*  488 */     return this.history;
/*      */   }
/*      */   
/*      */   void setCurrentSoftware(SPSSoftware current) {
/*  492 */     this.calibration = current;
/*      */   }
/*      */   
/*      */   SPSSoftware getCurrentSoftware() {
/*  496 */     return this.calibration;
/*      */   }
/*      */   
/*      */   SPSSoftware getSoftware() throws Exception {
/*  500 */     if (this.software == null) {
/*  501 */       this.software = SPSSoftware.load((SPSLanguage)this.session.getLanguage(), (VIT1Data)null, this.ecu.intValue(), this.adapter);
/*      */     }
/*  503 */     return this.software;
/*      */   }
/*      */   
/*      */   protected List queryPreProgrammingInstruction() throws Exception {
/*  507 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*      */     
/*  509 */     List<Integer> instructions = new ArrayList();
/*  510 */     Connection conn = null;
/*  511 */     DBMS.PreparedStatement stmt = null;
/*  512 */     ResultSet rs = null;
/*      */     try {
/*  514 */       conn = dblink.requestConnection();
/*  515 */       String sql = DBMS.getSQL(dblink, "SELECT FFID, Doc_Ref FROM SPS_PreProgramming WHERE ECUID = ?");
/*      */       try {
/*  517 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  518 */       } catch (Exception s) {
/*  519 */         sql = strip12449(sql, "Doc_Ref");
/*  520 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       } 
/*  522 */       stmt.setInt(1, this.ecu.intValue());
/*      */       try {
/*  524 */         rs = stmt.executeQuery();
/*  525 */       } catch (Exception s) {
/*  526 */         stmt.close();
/*  527 */         sql = strip12449(sql, "Doc_Ref");
/*  528 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  529 */         stmt.setInt(1, this.ecu.intValue());
/*  530 */         rs = stmt.executeQuery();
/*      */       } 
/*  532 */       while (rs.next()) {
/*  533 */         if (sql.toLowerCase().indexOf("doc_ref") < 0) {
/*  534 */           instructions.add(Integer.valueOf(rs.getInt(1))); continue;
/*      */         } 
/*  536 */         int docRef = rs.getInt(2);
/*  537 */         if (!rs.wasNull()) {
/*  538 */           String href = handleDocumentReference(dblink, null, docRef);
/*  539 */           instructions.add(new SPSDocumentService.SPSDocumentReference(rs.getInt(1), href)); continue;
/*      */         } 
/*  541 */         instructions.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/*      */     
/*      */     }
/*  545 */     catch (Exception e) {
/*  546 */       throw e;
/*      */     } finally {
/*      */       try {
/*  549 */         if (rs != null) {
/*  550 */           rs.close();
/*      */         }
/*  552 */         if (stmt != null) {
/*  553 */           stmt.close();
/*      */         }
/*  555 */         if (conn != null) {
/*  556 */           dblink.releaseConnection(conn);
/*      */         }
/*  558 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  561 */     return (instructions.size() > 0) ? instructions : null;
/*      */   }
/*      */   
/*      */   protected List queryPostProgrammingInstruction() throws Exception {
/*  565 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*      */     
/*  567 */     List<Integer> instructions = new ArrayList();
/*  568 */     Connection conn = null;
/*  569 */     DBMS.PreparedStatement stmt = null;
/*  570 */     ResultSet rs = null;
/*      */     try {
/*  572 */       conn = dblink.requestConnection();
/*  573 */       String sql = DBMS.getSQL(dblink, "SELECT FFID, Doc_Ref FROM SPS_PostProgramming WHERE ECUID = ?");
/*      */       try {
/*  575 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  576 */       } catch (Exception s) {
/*  577 */         sql = strip12449(sql, "Doc_Ref");
/*  578 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       } 
/*  580 */       stmt.setInt(1, this.ecu.intValue());
/*      */       try {
/*  582 */         rs = stmt.executeQuery();
/*  583 */       } catch (Exception s) {
/*  584 */         stmt.close();
/*  585 */         sql = strip12449(sql, "Doc_Ref");
/*  586 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  587 */         stmt.setInt(1, this.ecu.intValue());
/*  588 */         rs = stmt.executeQuery();
/*      */       } 
/*  590 */       while (rs.next()) {
/*  591 */         if (sql.toLowerCase().indexOf("doc_ref") < 0) {
/*  592 */           instructions.add(Integer.valueOf(rs.getInt(1))); continue;
/*      */         } 
/*  594 */         int docRef = rs.getInt(2);
/*  595 */         if (!rs.wasNull()) {
/*  596 */           String href = handleDocumentReference(dblink, null, docRef);
/*  597 */           instructions.add(new SPSDocumentService.SPSDocumentReference(rs.getInt(1), href)); continue;
/*      */         } 
/*  599 */         instructions.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/*      */     
/*      */     }
/*  603 */     catch (Exception e) {
/*  604 */       throw e;
/*      */     } finally {
/*      */       try {
/*  607 */         if (rs != null) {
/*  608 */           rs.close();
/*      */         }
/*  610 */         if (stmt != null) {
/*  611 */           stmt.close();
/*      */         }
/*  613 */         if (conn != null) {
/*  614 */           dblink.releaseConnection(conn);
/*      */         }
/*  616 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  619 */     return (instructions.size() > 0) ? instructions : null;
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData handleArchiveProgrammingData(SPSProgrammingData data) throws Exception {
/*  623 */     data.setArchive((SPSLanguage)this.session.getLanguage(), this.archive);
/*  624 */     if (data.getVMECUHN() == null) {
/*  625 */       this.software = SPSSoftware.load((SPSLanguage)this.session.getLanguage(), (VIT1Data)null, this.ecu.intValue(), this.adapter);
/*  626 */       setVIT2Data(data);
/*      */     } 
/*  628 */     this.software = new SPSSoftware(this.archive.getComment());
/*  629 */     return data;
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData determineProgrammingData() throws Exception {
/*  633 */     SPSProgrammingData data = new SPSProgrammingData();
/*  634 */     data.setDeviceID(this.system);
/*  635 */     data.setVIN(this.session.getVehicle().getVIN().toString());
/*  636 */     if (this.session.getVehicle().getVIT1() != null) {
/*  637 */       data.setSSECUSVN(this.session.getVehicle().getVIT1().getSWVersion());
/*      */     }
/*  639 */     if (this.archive != null) {
/*  640 */       if (this.session.getVehicle().getVIT1() != null) {
/*  641 */         data.setVMECUHN(this.session.getVehicle().getVIT1().getOPNumber());
/*      */       }
/*  643 */       return handleArchiveProgrammingData(data);
/*      */     } 
/*  645 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*  646 */     Connection conn = dblink.requestConnection();
/*      */     
/*      */     try {
/*  649 */       String sql = DBMS.getSQL(dblink, "SELECT a.IdentValue, b.VIT1Pos FROM SPS_ECUSoftware a,SPS_IdentDescription b WHERE a.ECUID = ?  AND b.IdentType = a.IdentType   AND UPPER(b.VIT2Ident) = UPPER('END_MODEL')   AND UPPER(b.VITType) = UPPER('A7')");
/*  650 */       DBMS.PreparedStatement stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       try {
/*  652 */         stmt.setInt(1, this.ecu.intValue());
/*  653 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  655 */           while (rs.next()) {
/*  656 */             String ident = rs.getString(1);
/*  657 */             if (ident != null) {
/*  658 */               ident = ident.trim();
/*      */             }
/*  660 */             rs.getInt(2);
/*      */             
/*  662 */             data.setEndModelIdent(ident);
/*  663 */             data.setSSECUSVN(ident);
/*      */           } 
/*      */         } finally {
/*  666 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*  669 */         stmt.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  674 */       sql = DBMS.getSQL(dblink, "SELECT m.ModuleID, m.ModuleOrder, m.ModuleName, m.ModuleType FROM SPS_Modules m WHERE m.ECUID = ? ORDER BY m.ModuleOrder");
/*  675 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       try {
/*  677 */         stmt.setInt(1, this.ecu.intValue());
/*  678 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/*  680 */           while (rs.next()) {
/*      */             
/*  682 */             int id = rs.getInt(1);
/*  683 */             int order = rs.getInt(2);
/*  684 */             String name = rs.getString(3);
/*  685 */             int type = rs.getInt(4);
/*  686 */             if (name != null) {
/*  687 */               name = name.trim();
/*      */             }
/*  689 */             data.add(id, order, name, type);
/*      */           } 
/*      */         } finally {
/*  692 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/*  695 */         stmt.close();
/*      */       } 
/*      */ 
/*      */       
/*  699 */       if (data.getModules() != null) {
/*  700 */         List<SPSModule> modules = data.getModules();
/*  701 */         String str = DBMS.getSQL(dblink, "SELECT m.ModuleID, d.Description FROM SPS_Modules m, SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = m.ModuleID  AND m.ECUID = ?  AND NOT m.ModuleType = 0  AND d.LanguageID = ?");
/*  702 */         DBMS.PreparedStatement preparedStatement = DBMS.prepareSQLStatement(conn, str);
/*      */         try {
/*  704 */           preparedStatement.setInt(1, this.ecu.intValue());
/*  705 */           SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/*  706 */           preparedStatement.setString(2, DBMS.getLanguageCode(dblink, language));
/*  707 */           ResultSet rs = preparedStatement.executeQuery();
/*      */           try {
/*  709 */             while (rs.next()) {
/*  710 */               String id = Integer.toString(rs.getInt(1));
/*  711 */               String description = DBMS.getString(dblink, language, rs, 2);
/*  712 */               description = removeTrailingLineFeed(description);
/*  713 */               for (int i = 0; i < modules.size(); i++) {
/*  714 */                 SPSModule module = modules.get(i);
/*  715 */                 if (module.getID().equals(id)) {
/*  716 */                   module.setDescription(description);
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } finally {
/*  722 */             JDBCUtil.close(rs, log);
/*      */           } 
/*      */         } finally {
/*  725 */           preparedStatement.close();
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  729 */       dblink.releaseConnection(conn);
/*      */     } 
/*  731 */     this.preProgrammingInstructions = queryPreProgrammingInstruction();
/*  732 */     this.postProgrammingInstructions = queryPostProgrammingInstruction();
/*  733 */     if (data.getEndModelIdent() == null) {
/*  734 */       setVIT2Data(data);
/*      */     }
/*  736 */     return data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String removeTrailingLineFeed(String fieldfix) {
/*  877 */     if (fieldfix != null && fieldfix.endsWith("\n")) {
/*  878 */       fieldfix = fieldfix.substring(0, fieldfix.length() - 1);
/*      */     }
/*  880 */     return fieldfix;
/*      */   }
/*      */   
/*      */   protected void setVIT2Data(SPSProgrammingData data) throws Exception {
/*  884 */     if (this.software == null) {
/*  885 */       getSoftware();
/*      */     }
/*  887 */     if (this.software != null) {
/*  888 */       if (this.software.getSSECUSVN() != null) {
/*  889 */         data.setSSECUSVN(this.software.getSSECUSVN());
/*      */       }
/*  891 */       if (this.software.getVMECUHN() != null) {
/*  892 */         data.setVMECUHN(this.software.getVMECUHN());
/*      */       } else {
/*  894 */         List<Pair> attributes = this.software.getAttributes();
/*  895 */         if (attributes.size() > 0) {
/*  896 */           Pair pair = attributes.get(0);
/*  897 */           data.setVMECUHN((String)pair.getSecond());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected List filterOptions() throws Exception {
/*  904 */     if (this.vcategories == null) {
/*  905 */       return null;
/*      */     }
/*  907 */     boolean isCALID = ((SPSSession)this.session).isCALID();
/*  908 */     List<SPSOptionCategory> result = new ArrayList();
/*  909 */     for (int j = 0; j < this.vcategories.size(); j++) {
/*  910 */       SPSVehicleCategory vcategory = this.vcategories.get(j);
/*      */ 
/*      */       
/*  913 */       if (!vcategory.isEvaluated() && vcategory.isOptionCategory()) {
/*  914 */         vcategory.getCategory().setOrder(vcategory.getOrder());
/*  915 */         result.add(vcategory.getCategory());
/*  916 */       } else if (isCALID && vcategory.getGroup() != null && 
/*  917 */         vcategory.getCategory().isHWOCategory() && !vcategory.isEvaluated()) {
/*  918 */         vcategory.getCategory().setOrder(vcategory.getOrder());
/*  919 */         result.add(vcategory.getCategory());
/*      */       } 
/*      */     } 
/*      */     
/*  923 */     return (result.size() == 0) ? null : result;
/*      */   }
/*      */   
/*      */   boolean qualify(int order) throws Exception {
/*  927 */     if (this.vcategories == null) {
/*  928 */       return true;
/*      */     }
/*  930 */     boolean accept = SPSVehicleCategory.qualify(this.session, this, this.vcategories, order);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  938 */     return accept;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void register(SPSVehicleCategory vcategory) {
/*  944 */     if (vcategory.isOptionCategory()) {
/*  945 */       SPSOptionCategory category = vcategory.getCategory();
/*  946 */       if (!category.isValid() && !category.isHWOCategory()) {
/*      */         return;
/*      */       }
/*      */     } 
/*  950 */     if (this.vcategories == null) {
/*  951 */       this.vcategories = new ArrayList();
/*      */     }
/*  953 */     this.vcategories.add(vcategory);
/*      */   }
/*      */   
/*      */   public boolean checkHardware() throws Exception {
/*  957 */     VIT1Data vit1 = this.session.getVehicle().getVIT1();
/*  958 */     if (vit1 != null && vit1.getID() != null) {
/*  959 */       if (this.predecessors == null) {
/*  960 */         this.predecessors = new ArrayList();
/*      */       }
/*  962 */       if (!isPredecessor(this, this.predecessors)) {
/*  963 */         this.predecessors.add(this);
/*      */       }
/*  965 */       this.predecessors = SPSModel.getInstance(this.adapter).checkHardware((SPSVehicle)this.session.getVehicle(), this.predecessors, vit1);
/*  966 */       if (this.predecessors == null || this.predecessors.size() == 0) {
/*  967 */         return false;
/*      */       }
/*  969 */       SPSControllerGME actual = this.predecessors.get(0);
/*  970 */       for (int i = 1; i < this.predecessors.size(); i++) {
/*  971 */         SPSControllerGME controller = this.predecessors.get(i);
/*  972 */         actual = SPSECU.Provider.getInstance(this.adapter).compareReleaseDate(actual, controller);
/*      */       } 
/*  974 */       this.ecu = Integer.valueOf(actual.getID());
/*  975 */       this.hardware = actual.hardware;
/*  976 */       log.debug("selected ecu: " + this);
/*      */     } else {
/*      */       
/*  979 */       if (getHardware() == null) {
/*  980 */         if (this.predecessors == null) {
/*  981 */           this.predecessors = new ArrayList();
/*      */         }
/*  983 */         this.predecessors.add(this);
/*  984 */         sortByReleaseDate(false);
/*  985 */         List hardware = SPSModel.getInstance(this.adapter).getHardware((SPSVehicle)this.session.getVehicle(), this.predecessors);
/*  986 */         if (hardware != null && 
/*  987 */           isHardwareSelectionRequired(this.predecessors, hardware)) {
/*  988 */           requestHardwareSelection(hardware);
/*      */         }
/*      */       }
/*  991 */       else if (!sortByReleaseDate(true)) {
/*      */         
/*  993 */         return false;
/*      */       } 
/*  995 */       log.debug("selected ecu: " + this);
/*      */     } 
/*  997 */     return true;
/*      */   }
/*      */   
/*      */   protected boolean isPredecessor(SPSControllerGME controller, List<SPSControllerGME> predecessors) {
/* 1001 */     for (int i = 0; i < predecessors.size(); i++) {
/* 1002 */       if (controller == (SPSControllerGME)predecessors.get(i)) {
/* 1003 */         return true;
/*      */       }
/*      */     } 
/* 1006 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean sortByReleaseDate(boolean checkHardware) {
/* 1010 */     SPSControllerGME actual = checkHardware ? null : this;
/* 1011 */     for (int i = 0; i < this.predecessors.size(); i++) {
/* 1012 */       SPSControllerGME controller = this.predecessors.get(i);
/* 1013 */       if (!checkHardware || matchHardware(controller)) {
/* 1014 */         actual = SPSECU.Provider.getInstance(this.adapter).compareReleaseDate(actual, controller);
/*      */       }
/*      */     } 
/* 1017 */     if (actual != null && actual != this) {
/* 1018 */       Integer controller = this.ecu;
/* 1019 */       this.ecu = actual.ecu;
/* 1020 */       actual.ecu = controller;
/*      */     } 
/* 1022 */     return (actual != null);
/*      */   }
/*      */   
/*      */   protected boolean matchHardware(SPSControllerGME controller) {
/* 1026 */     return (this.hardware == null) ? true : ((SPSHardware)this.hardware.get(0)).match(controller.ecu);
/*      */   }
/*      */   
/*      */   protected boolean isHardwareSelectionRequired(List<SPSControllerGME> predecessors, List<SPSHardware> hardware) {
/* 1030 */     boolean match = true;
/* 1031 */     for (int i = 0; i < hardware.size(); i++) {
/* 1032 */       SPSHardware hw = hardware.get(i);
/* 1033 */       if (!hw.match(this.ecu)) {
/* 1034 */         match = false;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1038 */     if (match) {
/* 1039 */       return false;
/*      */     }
/* 1041 */     SPSHardware candidate = null; int j;
/* 1042 */     for (j = 0; j < hardware.size(); j++) {
/* 1043 */       SPSHardware hw = hardware.get(j);
/* 1044 */       if (candidate == null) {
/* 1045 */         candidate = hw;
/* 1046 */       } else if (!candidate.getPartNumber().equals(hw.getPartNumber())) {
/* 1047 */         return true;
/*      */       } 
/*      */     } 
/* 1050 */     if (candidate != null) {
/* 1051 */       if (!candidate.match(this.ecu)) {
/* 1052 */         return true;
/*      */       }
/* 1054 */       for (j = 0; j < predecessors.size(); j++) {
/* 1055 */         SPSControllerGME controller = predecessors.get(j);
/* 1056 */         if (!matchHardware(controller)) {
/* 1057 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1061 */       setHardware(candidate);
/*      */     } 
/* 1063 */     return false;
/*      */   }
/*      */   
/*      */   protected void requestHardwareSelection(List<Comparable> hardware) throws Exception {
/* 1067 */     Collections.sort(hardware);
/* 1068 */     throw new RequestException(SPSSchemaAdapterGME.builder.makeSelectionRequest(CommonAttribute.HARDWARE, hardware, null));
/*      */   }
/*      */   
/*      */   void register(SPSControllerGME predecessor) {
/* 1072 */     if (this.predecessors == null) {
/* 1073 */       this.predecessors = new ArrayList();
/*      */     }
/* 1075 */     if (predecessor.predecessors != null) {
/* 1076 */       this.predecessors.addAll(predecessor.predecessors);
/*      */     }
/* 1078 */     this.predecessors.add(predecessor);
/*      */   }
/*      */   
/*      */   protected boolean isEmptyIdentValue(Pair ident) {
/* 1082 */     return (((String)ident.getSecond()).indexOf("#NULL#") >= 0);
/*      */   }
/*      */   
/*      */   private static void close(DBMS.PreparedStatement stmt) {
/* 1086 */     if (stmt != null) {
/*      */       try {
/* 1088 */         stmt.close();
/* 1089 */       } catch (Exception e) {
/* 1090 */         log.warn("unable to close stmt, ignoring exception: " + e, e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void buildHistory() throws Exception {
/* 1096 */     if (this.history != null || this.predecessors == null) {
/* 1097 */       checkFieldFixDescription();
/*      */       return;
/*      */     } 
/* 1100 */     if (!SPSModel.getInstance(this.adapter).checkSettingExists(((SPSVehicle)this.session.getVehicle()).getSalesMakeID(), "SWHistory", "enabled")) {
/* 1101 */       checkFieldFixDescription();
/*      */       return;
/*      */     } 
/* 1104 */     this.history = new ArrayList();
/* 1105 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/* 1106 */     Connection conn = dblink.requestConnection();
/*      */     try {
/* 1108 */       String sql = DBMS.getSQL(dblink, "SELECT id.Display, es.IdentValue, ed.ReleaseDate, es.ECUID, id.IdentType FROM SPS_ECUSoftware es, SPS_ECUDescription ed, SPS_IdentDisplay id WHERE es.ECUID = ed.ECUID  AND es.IdentType = id.IdentType  AND id.LanguageID = ?  AND es.ECUID IN (#list#) ORDER BY ed.ReleaseDate DESC, id.IdentType", this.predecessors.size());
/* 1109 */       DBMS.PreparedStatement stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       try {
/* 1111 */         SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/* 1112 */         stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 1113 */         for (int i = 1; i <= this.predecessors.size(); i++) {
/* 1114 */           stmt.setInt(i + 1, ((SPSControllerGME)this.predecessors.get(i - 1)).getID());
/*      */         }
/* 1116 */         ResultSet rs = stmt.executeQuery();
/*      */         try {
/* 1118 */           SPSSoftware current = null;
/* 1119 */           while (rs.next()) {
/* 1120 */             String release = rs.getString(3);
/* 1121 */             if (release != null) {
/* 1122 */               release = release.trim();
/*      */             }
/* 1124 */             int ecu = rs.getInt(4);
/* 1125 */             String display = DBMS.getString(dblink, language, rs, 1);
/* 1126 */             String value = rs.getString(2);
/* 1127 */             if (value != null) {
/* 1128 */               value = value.trim();
/*      */             }
/* 1130 */             PairImpl pairImpl = new PairImpl(display, value);
/* 1131 */             if (!isEmptyIdentValue((Pair)pairImpl)) {
/* 1132 */               if (current == null || current.getID() != ecu) {
/* 1133 */                 current = new SPSSoftware(release, ecu);
/* 1134 */                 this.history.add(current);
/*      */               } 
/* 1136 */               current.setAttribute((Pair)pairImpl);
/*      */             } 
/*      */           } 
/*      */         } finally {
/* 1140 */           JDBCUtil.close(rs, log);
/*      */         } 
/*      */       } finally {
/* 1143 */         close(stmt);
/*      */       } 
/*      */       
/* 1146 */       sql = DBMS.getSQL(dblink, "SELECT ffi.ECUID, ffd.Description, ffi.Doc_Ref FROM SPS_FieldFixInformation ffi, SPS_FieldFixDescription ffd WHERE ffi.FFID = ffd.FFID  AND ffd.LanguageID = ?  AND ffi.ECUID IN (#list#) ORDER BY ffi.ECUID DESC", this.predecessors.size());
/*      */       try {
/* 1148 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1149 */       } catch (Exception s) {
/* 1150 */         sql = strip12449(sql, "ffi.Doc_Ref");
/* 1151 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       } 
/*      */       try {
/* 1154 */         SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/* 1155 */         stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 1156 */         for (int i = 1; i <= this.predecessors.size(); i++) {
/* 1157 */           stmt.setInt(i + 1, ((SPSControllerGME)this.predecessors.get(i - 1)).getID());
/*      */         }
/* 1159 */         ResultSet rs = null;
/*      */         try {
/* 1161 */           rs = stmt.executeQuery();
/* 1162 */         } catch (Exception s) {
/* 1163 */           stmt.close();
/* 1164 */           sql = strip12449(sql, "ffi.Doc_Ref");
/* 1165 */           stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1166 */           stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 1167 */           for (int j = 1; j <= this.predecessors.size(); j++) {
/* 1168 */             stmt.setInt(j + 1, ((SPSControllerGME)this.predecessors.get(j - 1)).getID());
/*      */           }
/* 1170 */           rs = stmt.executeQuery();
/*      */         } 
/*      */         
/* 1173 */         while (rs.next()) {
/* 1174 */           int ecu = rs.getInt(1);
/* 1175 */           String description = DBMS.getString(dblink, language, rs, 2);
/*      */           
/* 1177 */           description = removeTrailingLineFeed(description);
/*      */           
/* 1179 */           for (int j = 0; j < this.history.size(); j++) {
/* 1180 */             SPSSoftware current = this.history.get(j);
/* 1181 */             if (current.getID() == ecu) {
/* 1182 */               current.setDescription(description);
/* 1183 */               if (this.software != null && current.getID() == this.software.getID()) {
/* 1184 */                 this.software.setDescription(description);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1190 */         JDBCUtil.close(rs);
/*      */       } finally {
/*      */         
/* 1193 */         close(stmt);
/*      */       } 
/*      */     } finally {
/* 1196 */       dblink.releaseConnection(conn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static String strip12449(String sql, String attribute) {
/* 1202 */     attribute = ", " + attribute;
/* 1203 */     int idx = sql.toUpperCase().indexOf(attribute.toUpperCase());
/* 1204 */     if (idx >= 0) {
/* 1205 */       return sql.substring(0, idx) + sql.substring(idx + attribute.length());
/*      */     }
/* 1207 */     return sql;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkFieldFixDescription() throws Exception {
/* 1212 */     if (this.archive != null || this.history != null || this.software == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1216 */     IDatabaseLink dblink = this.adapter.getDatabaseLink();
/* 1217 */     Connection conn = null;
/* 1218 */     DBMS.PreparedStatement stmt = null;
/* 1219 */     ResultSet rs = null;
/*      */     try {
/* 1221 */       conn = dblink.requestConnection();
/* 1222 */       String sql = DBMS.getSQL(dblink, "SELECT ffi.ECUID, ffd.Description, ffi.Doc_Ref FROM SPS_FieldFixInformation ffi, SPS_FieldFixDescription ffd WHERE ffi.FFID = ffd.FFID  AND ffd.LanguageID = ?  AND ffi.ECUID IN (#list#) ORDER BY ffi.ECUID DESC", 1);
/*      */       try {
/* 1224 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1225 */       } catch (Exception s) {
/* 1226 */         sql = strip12449(sql, "ffi.Doc_Ref");
/* 1227 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*      */       } 
/* 1229 */       SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/* 1230 */       stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 1231 */       stmt.setInt(2, getID());
/*      */       try {
/* 1233 */         rs = stmt.executeQuery();
/* 1234 */       } catch (Exception s) {
/* 1235 */         stmt.close();
/* 1236 */         sql = strip12449(sql, "ffi.Doc_Ref");
/* 1237 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1238 */         stmt.setString(1, DBMS.getLanguageCode(dblink, language));
/* 1239 */         stmt.setInt(2, getID());
/* 1240 */         rs = stmt.executeQuery();
/*      */       } 
/* 1242 */       if (rs.next()) {
/* 1243 */         String description = DBMS.getString(dblink, language, rs, 2);
/* 1244 */         if (sql.toLowerCase().indexOf("doc_ref") >= 0) {
/* 1245 */           int docRef = rs.getInt(3);
/* 1246 */           if (!rs.wasNull()) {
/* 1247 */             description = handleDocumentReference(dblink, description, docRef);
/*      */           }
/*      */         } 
/* 1250 */         this.software.setDescription(description);
/*      */       } 
/*      */     } finally {
/*      */       try {
/* 1254 */         if (rs != null) {
/* 1255 */           rs.close();
/*      */         }
/* 1257 */         if (stmt != null) {
/* 1258 */           stmt.close();
/*      */         }
/* 1260 */         if (conn != null) {
/* 1261 */           dblink.releaseConnection(conn);
/*      */         }
/* 1263 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String handleDocumentReference(IDatabaseLink dblink, String description, int docRef) throws Exception {
/* 1269 */     Connection conn = null;
/* 1270 */     DBMS.PreparedStatement stmt = null;
/* 1271 */     ResultSet rs = null;
/*      */     try {
/* 1273 */       conn = dblink.requestConnection();
/* 1274 */       String sql = DBMS.getSQL(dblink, "SELECT Doc_URI, LinkDescr_ID FROM SPS_DocRef WHERE Doc_Ref = ?");
/* 1275 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1276 */       stmt.setInt(1, docRef);
/* 1277 */       rs = stmt.executeQuery();
/* 1278 */       if (rs.next()) {
/* 1279 */         SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/* 1280 */         String uri = DBMS.getString(dblink, language, rs, 1);
/* 1281 */         String label = null;
/* 1282 */         int link = rs.getInt(2);
/* 1283 */         if (!rs.wasNull()) {
/* 1284 */           label = resolveHREFLabel(dblink, link);
/*      */         }
/* 1286 */         ClientContext context = ClientContextProvider.getInstance().getContext(this.session.getSessionID());
/* 1287 */         if (label == null) {
/* 1288 */           label = context.getApplicationContext().getMessage(language.getJavaLocale(), "sps.docref.default");
/*      */         }
/* 1290 */         uri = SPSDocumentService.getURL(context, uri);
/* 1291 */         description = (description == null) ? label : (description + "\n\n" + label);
/* 1292 */         return injectHREF(docRef, uri, description);
/*      */       } 
/* 1294 */     } catch (Exception e) {
/* 1295 */       log.error("failed to load document reference " + docRef, e);
/*      */     } finally {
/*      */       try {
/* 1298 */         if (rs != null) {
/* 1299 */           rs.close();
/*      */         }
/* 1301 */         if (stmt != null) {
/* 1302 */           stmt.close();
/*      */         }
/* 1304 */         if (conn != null) {
/* 1305 */           dblink.releaseConnection(conn);
/*      */         }
/* 1307 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1310 */     return description;
/*      */   }
/*      */   
/*      */   private String resolveHREFLabel(IDatabaseLink dblink, int ffid) {
/* 1314 */     Connection conn = null;
/* 1315 */     DBMS.PreparedStatement stmt = null;
/* 1316 */     ResultSet rs = null;
/*      */     try {
/* 1318 */       conn = dblink.requestConnection();
/* 1319 */       String sql = DBMS.getSQL(dblink, "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?");
/* 1320 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1321 */       stmt.setInt(1, ffid);
/* 1322 */       SPSLanguage language = (SPSLanguage)this.session.getLanguage();
/* 1323 */       stmt.setString(2, DBMS.getLanguageCode(dblink, language));
/* 1324 */       rs = stmt.executeQuery();
/* 1325 */       if (rs.next()) {
/* 1326 */         return DBMS.getString(dblink, language, rs, 1);
/*      */       }
/* 1328 */     } catch (Exception e) {
/* 1329 */       log.warn("failed to load href reference " + ffid, e);
/*      */     } finally {
/*      */       try {
/* 1332 */         if (rs != null) {
/* 1333 */           rs.close();
/*      */         }
/* 1335 */         if (stmt != null) {
/* 1336 */           stmt.close();
/*      */         }
/* 1338 */         if (conn != null) {
/* 1339 */           dblink.releaseConnection(conn);
/*      */         }
/* 1341 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1344 */     return "";
/*      */   }
/*      */   
/*      */   private String injectHREF(int docRef, String uri, String text) {
/*      */     try {
/* 1349 */       if (text.indexOf("<a>") < 0) {
/* 1350 */         log.error("no document reference anchor found (docRef=" + docRef + ")");
/*      */       } else {
/* 1352 */         return StringUtilities.replace(text, "<a>", "<a href=\"" + uri + "\">");
/*      */       } 
/* 1354 */     } catch (Exception e) {
/* 1355 */       log.error("failed to construct document reference " + docRef, e);
/*      */     } 
/* 1357 */     return text;
/*      */   }
/*      */   
/*      */   SPSSession getSession() {
/* 1361 */     return this.session;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1365 */     return this.ecu.intValue();
/*      */   }
/*      */   
/*      */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*      */   public boolean equals(Object object) {
/* 1370 */     if (object == null)
/* 1371 */       return false; 
/* 1372 */     if (object instanceof SPSController && ((SPSController)object).getID() == this.ecu.intValue())
/* 1373 */       return true; 
/* 1374 */     if (object instanceof SPSControllerReference) {
/* 1375 */       return ((SPSControllerReference)object).accept(this);
/*      */     }
/* 1377 */     return false;
/*      */   }
/*      */   
/*      */   public boolean match(SPSControllerGME controller) {
/* 1381 */     return (this.vehicle.equals(controller.vehicle) && this.id.equals(controller.id) && this.system.equals(controller.system) && this.ecu.equals(controller.ecu));
/*      */   }
/*      */   
/*      */   protected String assembleHardwareList() {
/* 1385 */     if (this.hardware == null) {
/* 1386 */       return "<none>";
/*      */     }
/* 1388 */     StringBuffer result = new StringBuffer();
/* 1389 */     for (int i = 0; i < this.hardware.size(); i++) {
/* 1390 */       if (i > 0) {
/* 1391 */         result.append("||");
/*      */       }
/* 1393 */       result.append(this.hardware.get(i));
/*      */     } 
/* 1395 */     return result.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1400 */     return "vid = " + this.vehicle + ", ecu = " + this.ecu + ", id = " + this.id + " (" + getDescription() + "), hw=" + assembleHardwareList();
/*      */   }
/*      */   
/*      */   protected static SPSSequence getSequence(Integer sid, SPSSchemaAdapterGME adapter) {
/* 1404 */     Map sequences = Descriptions.getInstance(adapter).getSequences();
/* 1405 */     return (SPSSequence)sequences.get(sid);
/*      */   }
/*      */   
/*      */   protected static SPSSequenceDisplay getSequenceDescription(Integer sid, SPSSchemaAdapterGME adapter) {
/* 1409 */     Map sequences = Descriptions.getInstance(adapter).getSequences();
/* 1410 */     SPSSequence sequence = (SPSSequence)sequences.get(sid);
/* 1411 */     return sequence.getDisplay();
/*      */   }
/*      */   
/*      */   protected static String getFunctionDescription(SPSLanguage language, Integer cid, Integer fid, SPSSchemaAdapterGME adapter) {
/* 1415 */     Map functions = Descriptions.getInstance(adapter).getFunctions();
/* 1416 */     SPSFunctions flist = (SPSFunctions)functions.get(cid);
/* 1417 */     return flist.getDescription(language, fid);
/*      */   }
/*      */   
/*      */   protected void fixFunctionDescription() {
/*      */     try {
/* 1422 */       int idx = this.description.lastIndexOf('-');
/* 1423 */       if (idx >= 0) {
/* 1424 */         this.description = this.description.substring(idx + 1).trim();
/*      */       }
/* 1426 */     } catch (Exception x) {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected static String getDescription(SPSLanguage language, Integer id, SPSSchemaAdapterGME adapter) {
/* 1431 */     Map descriptions = Descriptions.getInstance(adapter).getMap();
/* 1432 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(id);
/* 1433 */     return (description != null) ? description.get(language) : null;
/*      */   }
/*      */   
/*      */   protected static String getLabel(Integer id, SPSSchemaAdapterGME adapter) {
/* 1437 */     Map descriptions = Descriptions.getInstance(adapter).getMap();
/* 1438 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(id);
/* 1439 */     return (description != null) ? description.getDefaultLabel() : null;
/*      */   }
/*      */   
/*      */   protected String getDescription(SPSLanguage language) {
/* 1443 */     Map descriptions = Descriptions.getInstance(this.adapter).getMap();
/* 1444 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(this.id);
/* 1445 */     return (description != null) ? description.get(language) : null;
/*      */   }
/*      */   
/*      */   static void init(SPSSchemaAdapterGME adapter) throws Exception {
/* 1449 */     Descriptions.getInstance(adapter);
/* 1450 */     SPSECU.init(adapter);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerGME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */