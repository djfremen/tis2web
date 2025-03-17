/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.ConstraintFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.ConstraintType;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.FileReferenceType;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.InformationObjectType;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.LabelType;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.NodeType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCLabel;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.FileReference;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.CachingStrategy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.ICRList;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class XMLCTOCStore
/*     */   extends CTOCStore
/*     */ {
/*  60 */   protected static Logger log = Logger.getLogger(XMLCTOCStore.class);
/*     */ 
/*     */   
/*     */   protected static final String CHECKING_PROCEDURE = "SIT-10";
/*     */ 
/*     */   
/*     */   protected static final String WIRING_DIAGRAM = "SIT-15";
/*     */   
/*     */   public static final String DEFAULT_LOCALE = "en_GB";
/*     */   
/*     */   protected String application;
/*     */   
/*  72 */   protected Map keys = new HashMap<Object, Object>();
/*     */   
/*  74 */   protected Map labels = new HashMap<Object, Object>();
/*     */   
/*  76 */   protected Map sits = new HashMap<Object, Object>();
/*     */   
/*     */   protected List locales;
/*     */   protected boolean isOracleDB;
/*     */   public static final String SELECT_CTOC = "SELECT FILE_BLOB FROM FILES WHERE LOCALE = 'ctoc'";
/*     */   
/*     */   public HashMap getCTOCs() {
/*  83 */     return this.ctocs;
/*     */   }
/*     */   
/*     */   public XMLCTOCStore(IDatabaseLink dblink, Connection db, CTOCDomain domain, CachingStrategy strategy, Map factories, Collection sits, ILVCAdapter.Retrieval lvcRetrieval) throws Exception {
/*  87 */     super(factories, lvcRetrieval);
/*  88 */     this.isOracleDB = (dblink.getDBMS() == 1);
/*  89 */     String key = CTOCCache.makeKey(domain.ord());
/*  90 */     CTOCRootElement root = loadCTOC(domain, db, sits);
/*  91 */     CTOCCache ctoc = new CTOCCache(this, root);
/*  92 */     this.ctocs.put(key, ctoc);
/*  93 */     root.setCache(ctoc);
/*     */   }
/*     */   
/*     */   private VCR getNULLVCR() {
/*  97 */     return VCR.NULL;
/*     */   }
/*     */   
/*     */   CTOCLabel makeLabel() {
/* 101 */     Integer key = Integer.valueOf(this.labels.size());
/* 102 */     CTOCLabel label = new CTOCLabel(key);
/* 103 */     this.labels.put(key, label);
/* 104 */     return label;
/*     */   }
/*     */   
/*     */   CTOCLabel getLabel(Integer labelID) {
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized String getLabel(LocaleInfo locale, Integer labelID) {
/* 112 */     CTOCLabel label = (CTOCLabel)this.labels.get(labelID);
/* 113 */     if (label == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     String display = label.get(locale);
/* 117 */     if (display == null) {
/* 118 */       LGSIT lgsit = LGSIT.DEFAULT;
/* 119 */       List<Integer> flc = locale.getLocaleFLC(lgsit);
/* 120 */       if (flc == null) {
/* 121 */         return null;
/*     */       }
/* 123 */       for (int i = 0; i < flc.size(); i++) {
/* 124 */         LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(flc.get(i));
/* 125 */         display = label.get(li);
/* 126 */         if (label != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 131 */     return display;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void loadChildren(CTOCElement element) {}
/*     */ 
/*     */   
/*     */   synchronized void loadContent(CTOCElement element) {}
/*     */ 
/*     */   
/*     */   synchronized void loadProperties(CTOCElement element) {}
/*     */   
/*     */   public synchronized CTOCNode loadNode(int ctocID) {
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   protected Integer getKey(String id) {
/* 148 */     Integer key = (Integer)this.keys.get(id);
/* 149 */     if (key == null) {
/* 150 */       key = Integer.valueOf(this.keys.size());
/* 151 */       this.keys.put(id, key);
/*     */     } 
/* 153 */     return key;
/*     */   }
/*     */   
/*     */   protected void prepareSITs(Collection sits) {
/* 157 */     if (!Util.isNullOrEmpty(sits)) {
/* 158 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale("en_GB");
/* 159 */       for (Iterator<CTOCNode> iter = sits.iterator(); iter.hasNext(); ) {
/* 160 */         CTOCNode sit = iter.next();
/* 161 */         this.sits.put(sit.getLabel(locale).toUpperCase(Locale.ENGLISH), sit);
/*     */       } 
/*     */     } 
/* 164 */     CTOCType ctocType = null;
/* 165 */     if (this.application.equalsIgnoreCase(CTOCType.HELP.toString())) {
/* 166 */       ctocType = CTOCType.HELP;
/* 167 */       this.sits.put(CTOCType.VERSION.toString().toUpperCase(Locale.ENGLISH), new IONode(CTOCType.VERSION));
/* 168 */     } else if (this.application.equalsIgnoreCase(CTOCType.NEWS.toString())) {
/* 169 */       ctocType = CTOCType.NEWS;
/*     */     } 
/* 171 */     this.sits.put(this.application, new IONode(ctocType));
/*     */   }
/*     */   
/*     */   protected CTOCNode lookupSIT(String sit) {
/* 175 */     if (sit == null) {
/* 176 */       return null;
/*     */     }
/* 178 */     return (CTOCNode)this.sits.get(sit.toUpperCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   protected String lookupConstraintSIT(String sit) {
/* 182 */     CTOCNode node = lookupSIT(sit);
/* 183 */     if (node == null) {
/* 184 */       return sit;
/*     */     }
/* 186 */     String id = (String)node.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 187 */     if (id != null) {
/* 188 */       if (id.equals("SIT-10"))
/* 189 */         return "CPR"; 
/* 190 */       if (id.equals("SIT-15")) {
/* 191 */         return "WD";
/*     */       }
/* 193 */       return "SCDS";
/*     */     } 
/*     */     
/* 196 */     return sit;
/*     */   }
/*     */ 
/*     */   
/*     */   protected CTOCRootElement loadCTOC(CTOCDomain domain, CTOC ctoc, Collection sits) throws Exception {
/*     */     try {
/* 202 */       this.application = ctoc.getApplication().toUpperCase(Locale.ENGLISH);
/* 203 */       if (!domain.toString().toUpperCase(Locale.ENGLISH).startsWith(this.application)) {
/* 204 */         throw new IllegalArgumentException("xml-ctoc domain mismatch: '" + domain + "' vs '" + ctoc.getApplication() + "'.");
/*     */       }
/* 206 */       prepareSITs(sits);
/* 207 */       NodeType node = ctoc.getNode();
/* 208 */       int tocID = getKey(node.getNodeID()).intValue();
/* 209 */       handleLabel((List)null, node.getLabel());
/*     */ 
/*     */ 
/*     */       
/* 213 */       int ctype = CTOCType.CTOC.ord();
/* 214 */       CTOCRootElement root = new CTOCRootElement(tocID, ctype, true, false, null, null);
/* 215 */       root.add(CTOCProperty.ID, node.getNodeID());
/* 216 */       root.add(CTOCProperty.VERSION, node.getVersion());
/* 217 */       List<NodeType> nodes = node.getNode();
/* 218 */       for (int i = 0; i < nodes.size(); i++) {
/* 219 */         handleNode((CTOCNode)root, i, nodes.get(i), getNULLVCR());
/*     */       }
/*     */       
/* 222 */       int order = nodes.size();
/* 223 */       List<InformationObjectType> ios = node.getInformationObject();
/* 224 */       for (int j = 0; j < ios.size(); j++) {
/* 225 */         handleIO((CTOCNode)root, order++, ios.get(j), (VCR)null);
/*     */       }
/*     */       
/* 228 */       return root;
/* 229 */     } catch (Exception e) {
/*     */       
/* 231 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected CTOCRootElement loadCTOC(CTOCDomain domain, File sourceDirectory, CTOCNode sits) throws Exception {
/*     */     try {
/* 238 */       File ctocDefinition = findCTOC(domain, sourceDirectory);
/* 239 */       JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.ctoc.implementation.common.db.xml");
/* 240 */       Unmarshaller um = jc.createUnmarshaller();
/* 241 */       um.setValidating(false);
/* 242 */       CTOC ctoc = (CTOC)um.unmarshal(new URL("FILE:" + ctocDefinition.getAbsolutePath()));
/* 243 */       return loadCTOC(domain, ctoc, sits.getChildren());
/* 244 */     } catch (Exception e) {
/*     */       
/* 246 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CTOCRootElement loadCTOC(CTOCDomain domain, Connection db, Collection sits) throws Exception {
/* 254 */     Statement st = null;
/* 255 */     ResultSet rs = null;
/*     */     try {
/* 257 */       JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.ctoc.implementation.common.db.xml");
/* 258 */       Unmarshaller um = jc.createUnmarshaller();
/* 259 */       um.setValidating(false);
/* 260 */       st = db.createStatement();
/* 261 */       rs = st.executeQuery("SELECT FILE_BLOB FROM FILES WHERE LOCALE = 'ctoc'");
/* 262 */       if (rs.next()) {
/* 263 */         byte[] bytes = null;
/* 264 */         if (this.isOracleDB) {
/* 265 */           Blob b = rs.getBlob("FILE_BLOB");
/* 266 */           bytes = b.getBytes(1L, (int)b.length());
/*     */         } else {
/* 268 */           bytes = (byte[])rs.getObject("FILE_BLOB");
/*     */         } 
/* 270 */         ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
/* 271 */         CTOC ctoc = (CTOC)um.unmarshal(new StreamSource(bais));
/* 272 */         return loadCTOC(domain, ctoc, sits);
/*     */       } 
/* 274 */       throw new IllegalArgumentException("failed to load CTOC");
/*     */     }
/* 276 */     catch (Exception e) {
/*     */       
/* 278 */       throw e;
/*     */     } finally {
/*     */       try {
/* 281 */         if (rs != null) {
/* 282 */           rs.close();
/*     */         }
/* 284 */         if (st != null) {
/* 285 */           st.close();
/*     */         }
/* 287 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected CTOCLabel handleLabel(List<LocaleInfo> locales, List<LabelType> list) throws Exception {
/* 293 */     CTOCLabel label = makeLabel();
/* 294 */     for (int i = 0; i < list.size(); i++) {
/* 295 */       LabelType data = list.get(i);
/* 296 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(data.getLocale());
/* 297 */       if (locale == null) {
/* 298 */         log.error("unknown locale '" + data.getLocale() + "' encountered.");
/*     */       } else {
/* 300 */         label.add(locale, data.getLabel());
/* 301 */         if (locales != null && !locales.contains(locale)) {
/* 302 */           locales.add(locale);
/*     */         }
/*     */       } 
/*     */     } 
/* 306 */     return label;
/*     */   }
/*     */   
/*     */   protected FileReference handleFileReference(List<FileReferenceType> list) throws Exception {
/* 310 */     if (list != null && list.size() > 0) {
/* 311 */       FileReference references = new FileReference();
/* 312 */       for (int i = 0; i < list.size(); i++) {
/* 313 */         FileReferenceType reference = list.get(i);
/* 314 */         LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(reference.getLocale());
/* 315 */         references.add(locale, reference.getFile());
/*     */       } 
/* 317 */       return references;
/*     */     } 
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCR handleConstraint(List<ConstraintType> constraints, VCR vcr) {
/* 327 */     if (constraints != null && constraints.size() > 0) {
/* 328 */       VCR result = this.lvcRetrieval.getLVCAdapter().makeVCR();
/* 329 */       for (int i = 0; i < constraints.size(); i++) {
/* 330 */         ConstraintType constraint = constraints.get(i);
/* 331 */         String sit = lookupConstraintSIT(constraint.getServiceInformationType());
/* 332 */         VCRExpression restriction = ConstraintFactory.makeConstraintExpressionVCR(constraint.getApplication(), sit, constraint.getManufacturer(), constraint.getUserGroup(), constraint.getCountry(), this.lvcRetrieval);
/* 333 */         result.add(restriction);
/*     */       } 
/* 335 */       return (vcr == null) ? result : foldVCR(result, vcr);
/*     */     } 
/* 337 */     return vcr;
/*     */   }
/*     */   
/*     */   protected VCR updateConstraint(List locales, VCR vcr) {
/* 341 */     if (locales == null || locales.size() == 0 || (vcr != null && vcr.equals(VCR.NULL))) {
/* 342 */       return getNULLVCR();
/*     */     }
/* 344 */     return ConstraintFactory.extendConstraintVCR(vcr, 9, locales, this.lvcRetrieval);
/*     */   }
/*     */   
/*     */   protected IONode handleNode(CTOCNode parent, int order, NodeType node, VCR vcr) throws Exception {
/* 348 */     int tocID = getKey(node.getNodeID()).intValue();
/* 349 */     CTOCLabel label = handleLabel((List)null, node.getLabel());
/* 350 */     int ctype = CTOCType.STRUCTURE.ord();
/* 351 */     boolean hasChildren = (node.getNode() != null);
/* 352 */     boolean hasContent = (node.getInformationObject() != null);
/* 353 */     VCR constraint = handleConstraint(node.getConstraint(), vcr);
/*     */     
/* 355 */     IONode element = new IONode(tocID, label, order, ctype, hasChildren, hasContent, constraint, this.lvcRetrieval);
/* 356 */     element.add(CTOCProperty.ID, node.getNodeID());
/* 357 */     element.add(CTOCProperty.VERSION, node.getVersion());
/* 358 */     order = 0;
/* 359 */     boolean valid = false;
/* 360 */     vcr = (hasChildren || hasContent) ? getNULLVCR() : constraint;
/* 361 */     if (hasChildren) {
/* 362 */       List<NodeType> nodes = node.getNode();
/* 363 */       for (int i = 0; i < nodes.size(); i++) {
/* 364 */         IONode child = handleNode(element, order++, nodes.get(i), constraint);
/* 365 */         if (child != null) {
/* 366 */           vcr = extendVCR(vcr, child.getVCR());
/* 367 */           valid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 371 */     if (hasContent) {
/* 372 */       List<InformationObjectType> ios = node.getInformationObject();
/* 373 */       for (int i = 0; i < ios.size(); i++) {
/* 374 */         SITOCElement child = handleIO(element, order++, ios.get(i), constraint);
/* 375 */         vcr = extendVCR(vcr, child.getVCR());
/* 376 */         valid = true;
/*     */       } 
/*     */     } 
/* 379 */     if (valid) {
/* 380 */       element.modifyVCR(vcr);
/* 381 */       if (parent instanceof IONode) {
/* 382 */         ((IONode)parent).add((SITOCElement)element);
/*     */       } else {
/* 384 */         ((CTOCElement)parent).add((SITOCElement)element);
/*     */       } 
/* 386 */       log.debug(node.getNodeID() + " (" + element.getID() + "): " + element.getVCR());
/*     */     } else {
/*     */       
/* 389 */       element = null;
/*     */     } 
/* 391 */     return element;
/*     */   }
/*     */   
/*     */   protected SITOCElement handleIO(CTOCNode parent, int order, InformationObjectType io, VCR vcr) throws Exception {
/* 395 */     int contentID = getKey(io.getInformationObjectID()).intValue();
/* 396 */     CTOCNode sit = lookupSIT(io.getServiceInformationType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     if (sit == null) {
/* 403 */       throw new Exception("service information type unknown: " + io.getServiceInformationType());
/*     */     }
/* 405 */     CTOCFactory factory = (CTOCFactory)this.factories.get(sit.getType());
/* 406 */     if (factory == null) {
/* 407 */       log.error("no factory available (content-id='" + io.getInformationObjectID() + "','" + sit.getType() + "').");
/* 408 */       throw new Exception("no ctoc content factory available: " + sit.getType());
/*     */     } 
/* 410 */     VCR constraint = handleConstraint(io.getConstraint(), vcr);
/* 411 */     SITOCElement child = factory.make((CTOCType)sit.getType(), contentID, order, -1L, constraint);
/* 412 */     if (parent instanceof IONode) {
/* 413 */       ((IONode)parent).add(child);
/* 414 */     } else if (parent instanceof CTOCRootElement) {
/* 415 */       ((CTOCRootElement)parent).add(child);
/*     */     } else {
/* 417 */       throw new IllegalArgumentException();
/*     */     } 
/* 419 */     if (child instanceof IOElement) {
/* 420 */       IOElement e = (IOElement)child;
/* 421 */       e.add(SIOProperty.IO, io.getInformationObjectID());
/* 422 */       e.add(SIOProperty.VERSION, io.getVersion());
/* 423 */       if (io.getPage() != null) {
/* 424 */         e.add(SIOProperty.Page, io.getPage());
/*     */       }
/* 426 */       List locales = new LinkedList();
/*     */       
/* 428 */       CTOCLabel label = handleLabel(locales, io.getLabel());
/* 429 */       e.setLabel(label);
/* 430 */       e.add(SIOProperty.File, handleFileReference(io.getFileReference()));
/*     */ 
/*     */ 
/*     */       
/* 434 */       e.setVCR(updateConstraint(locales, e.getVCR()));
/* 435 */       log.debug(io.getInformationObjectID() + " (" + child.getID() + "): " + e.getVCR());
/*     */     } 
/* 437 */     return child;
/*     */   }
/*     */   
/*     */   protected File findCTOC(CTOCDomain domain, File source) {
/* 441 */     if (source.isFile()) {
/* 442 */       return source;
/*     */     }
/* 444 */     File[] list = source.listFiles();
/* 445 */     File candidate = null;
/* 446 */     for (int i = 0; i < list.length; i++) {
/* 447 */       if (list[i].isFile()) {
/* 448 */         if (list[i].getName().toUpperCase(Locale.ENGLISH).equalsIgnoreCase(domain.toString().toUpperCase(Locale.ENGLISH) + ".XML"))
/* 449 */           return list[i]; 
/* 450 */         if (list[i].getName().toUpperCase(Locale.ENGLISH).startsWith(domain.toString().toUpperCase(Locale.ENGLISH))) {
/* 451 */           candidate = list[i];
/* 452 */         } else if (list.length == 1 && list[i].getName().toUpperCase(Locale.ENGLISH).endsWith(".XML")) {
/* 453 */           if (candidate == null) {
/* 454 */             candidate = list[i];
/*     */           }
/* 456 */         } else if (list.length > 1 && list[i].getName().toUpperCase(Locale.ENGLISH).endsWith(".XML") && 
/* 457 */           candidate == null) {
/* 458 */           candidate = list[i];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 463 */     if (candidate != null || source.getName().toUpperCase(Locale.ENGLISH).endsWith("CTOC")) {
/* 464 */       return candidate;
/*     */     }
/* 466 */     return findCTOC(domain, new File(source.getAbsolutePath() + File.separator + "ctoc"));
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
/*     */   protected boolean identicalVCR(VCR vcr, VCRExpression expression) {
/* 481 */     ICRList<VCRExpression> iCRList = vcr.getExpressions();
/* 482 */     for (int i = 0; i < iCRList.size(); i++) {
/* 483 */       VCRExpression e = iCRList.get(i);
/* 484 */       if (e.identical(expression)) {
/* 485 */         return true;
/*     */       }
/*     */     } 
/* 488 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCR extendVCR(VCR vcr, VCR constraint) {
/* 496 */     if (vcr == getNULLVCR())
/* 497 */       return constraint; 
/* 498 */     if (constraint == getNULLVCR()) {
/* 499 */       return vcr;
/*     */     }
/* 501 */     VCR union = LVCAdapterProvider.getGlobalAdapter().createConstraintVCR();
/* 502 */     ICRList<VCRExpression> iCRList = vcr.getExpressions(); int i;
/* 503 */     for (i = 0; i < iCRList.size(); i++) {
/* 504 */       VCRExpression expression = iCRList.get(i);
/* 505 */       union.add(expression);
/*     */     } 
/* 507 */     iCRList = constraint.getExpressions();
/* 508 */     for (i = 0; i < iCRList.size(); i++) {
/* 509 */       VCRExpression expression = iCRList.get(i);
/* 510 */       if (!identicalVCR(union, expression)) {
/* 511 */         union.add(expression);
/*     */       }
/*     */     } 
/* 514 */     return union;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCR mergeVCR(VCR vcr, VCR constraint) {
/* 522 */     if (vcr == getNULLVCR())
/* 523 */       return constraint; 
/* 524 */     if (constraint == getNULLVCR()) {
/* 525 */       return vcr;
/*     */     }
/* 527 */     List<VCRTerm> terms = constraint.getTerms();
/* 528 */     for (int i = 0; i < terms.size(); i++) {
/* 529 */       VCRTerm term = terms.get(i);
/* 530 */       vcr = vcr.fold(term);
/*     */     } 
/* 532 */     return vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public VCR foldVCR(VCR constraint, VCR vcr) {
/* 537 */     if (vcr == getNULLVCR()) {
/* 538 */       return constraint;
/*     */     }
/* 540 */     return constraint.merge(vcr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean matchVCR(VCRExpression expressionIO, List<VCRTerm> termsVCR, boolean filter) {
/* 546 */     List<VCRTerm> termsIO = expressionIO.getTerms();
/* 547 */     for (int i = 0; i < termsIO.size(); i++) {
/*     */ 
/*     */       
/* 550 */       VCRTerm termIO = termsIO.get(i);
/* 551 */       boolean satisfied = false;
/* 552 */       for (int j = 0; j < termsVCR.size(); j++) {
/* 553 */         VCRTerm termVCR = termsVCR.get(j);
/* 554 */         if (termVCR.intersect(termIO) != null) {
/* 555 */           satisfied = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 559 */       if (!satisfied) {
/* 560 */         return false;
/*     */       }
/*     */     } 
/* 563 */     return filterVCR(expressionIO, termsVCR, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean filterVCR(VCRExpression expressionIO, List<VCRTerm> termsVCR, boolean filter) {
/* 569 */     if (!filter) {
/* 570 */       return true;
/*     */     }
/* 572 */     for (int k = 0; k < termsVCR.size(); k++) {
/*     */ 
/*     */       
/* 575 */       VCRTerm termVCR = termsVCR.get(k);
/* 576 */       if (termVCR.getDomainID() == 2) {
/* 577 */         return (expressionIO.getTerm(2) != null);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 583 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean matchVCR(VCR searchVCR, VCR ioVCR, boolean filter) {
/* 589 */     if (searchVCR == null || ioVCR == null || searchVCR == getNULLVCR() || ioVCR == getNULLVCR()) {
/* 590 */       return true;
/*     */     }
/* 592 */     List termsVCR = searchVCR.getTerms();
/* 593 */     ICRList<VCRExpression> iCRList = ioVCR.getExpressions();
/* 594 */     for (int i = 0; i < iCRList.size(); i++) {
/* 595 */       VCRExpression expressionIO = iCRList.get(i);
/* 596 */       if (matchVCR(expressionIO, termsVCR, filter)) {
/* 597 */         return true;
/*     */       }
/*     */     } 
/* 600 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCRExpression expressionIO, List<VCRTerm> termsVCR) {
/* 606 */     List<VCRTerm> termsIO = expressionIO.getTerms();
/* 607 */     for (int i = 0; i < termsVCR.size(); i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 613 */       VCRTerm termVCR = termsVCR.get(i);
/* 614 */       boolean satisfied = true;
/* 615 */       for (int j = 0; j < termsIO.size(); j++) {
/* 616 */         VCRTerm termIO = termsIO.get(j);
/* 617 */         if (termVCR.getDomainID() == termIO.getDomainID() && 
/* 618 */           termVCR.intersect(termIO) == null) {
/* 619 */           satisfied = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 624 */       if (!satisfied) {
/* 625 */         return true;
/*     */       }
/*     */     } 
/* 628 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCR searchVCR, VCR ioVCR) {
/* 633 */     if (searchVCR == null || ioVCR == null || searchVCR == getNULLVCR() || ioVCR == getNULLVCR()) {
/* 634 */       return false;
/*     */     }
/* 636 */     List termsVCR = searchVCR.getTerms();
/* 637 */     ICRList<VCRExpression> iCRList = ioVCR.getExpressions();
/* 638 */     for (int i = 0; i < iCRList.size(); i++) {
/* 639 */       VCRExpression expressionIO = iCRList.get(i);
/* 640 */       if (!contradictsVCR(expressionIO, termsVCR)) {
/* 641 */         return false;
/*     */       }
/*     */     } 
/* 644 */     return true;
/*     */   }
/*     */   
/*     */   protected void searchByProperty(CTOCSurrogate result, CTOCNode node, SIOProperty property, String value, VCR vcr) {
/* 648 */     List children = node.getChildren();
/* 649 */     for (int i = 0; i < children.size(); i++) {
/* 650 */       Object child = children.get(i);
/* 651 */       if (child instanceof IIOElement) {
/* 652 */         IOElement e = (IOElement)child;
/* 653 */         Object p = e.getProperty((SITOCProperty)property);
/* 654 */         if (((value == null && p == null) || (p != null && p.toString().equalsIgnoreCase(value))) && 
/* 655 */           matchVCR(vcr, e.getVCR(), true)) {
/* 656 */           result.add((SITOCElement)e);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 661 */       else if (!contradictsVCR(vcr, ((CTOCNode)child).getVCR())) {
/* 662 */         searchByProperty(result, (CTOCNode)child, property, value, vcr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/* 671 */     if (node == null || property == null) {
/* 672 */       return null;
/*     */     }
/* 674 */     CTOCSurrogateImpl result = new CTOCSurrogateImpl(null, this.lvcRetrieval, null);
/* 675 */     SIOProperty p = SIOProperty.get(property.ord());
/* 676 */     searchByProperty(result, node, p, value, vcr);
/* 677 */     return (result.getChildren() != null) ? (CTOCNode)result : null;
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
/*     */   
/*     */   public static void mainHELP(String[] args) throws Exception {}
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
/*     */   public static void checkVersionObject(CTOCNode node) {
/* 712 */     List children = node.getChildren();
/* 713 */     for (int i = 0; i < children.size(); i++) {
/* 714 */       Object child = children.get(i);
/* 715 */       if (child instanceof IIOElement) {
/* 716 */         IIOElement e = (IIOElement)child;
/* 717 */         if (e.getType().equals(SIOType.VERSION)) {
/* 718 */           System.out.println("found version object reference.");
/*     */         }
/*     */       } else {
/* 721 */         checkVersionObject((CTOCNode)child);
/*     */       } 
/*     */     } 
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
/*     */   public static void main(String[] args) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean check4Update(SITOCElement element, String version) {
/* 745 */     String v = null;
/* 746 */     if (element instanceof CTOCNode) {
/* 747 */       v = (String)element.getProperty((SITOCProperty)CTOCProperty.VERSION);
/*     */     } else {
/* 749 */       v = (String)element.getProperty((SITOCProperty)SIOProperty.VERSION);
/*     */     } 
/* 751 */     if (v != null && version.compareTo(v) < 0) {
/* 752 */       return true;
/*     */     }
/* 754 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\XMLCTOCStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */