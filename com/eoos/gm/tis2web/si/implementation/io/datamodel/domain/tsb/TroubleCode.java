/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TroubleCode
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(TroubleCode.class);
/* 23 */   protected static List domain = null;
/*    */   
/* 25 */   protected static Map identifierToInstance = new HashMap<Object, Object>();
/*    */   
/*    */   protected String identifier;
/*    */   
/*    */   protected TroubleCode(String identifier) {
/* 30 */     this.identifier = identifier;
/*    */   }
/*    */   
/*    */   public static synchronized TroubleCode getInstance(String identifier) {
/* 34 */     TroubleCode instance = (TroubleCode)identifierToInstance.get(identifier);
/* 35 */     if (instance == null) {
/* 36 */       instance = new TroubleCode(identifier);
/* 37 */       identifierToInstance.put(identifier, instance);
/*    */     } 
/*    */     
/* 40 */     return instance;
/*    */   }
/*    */   
/*    */   public static synchronized TroubleCode getInstance(CTOCNode node) {
/* 44 */     String identifier = (String)node.getProperty((SITOCProperty)SIOProperty.DTC);
/* 45 */     return getInstance(identifier);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 49 */     return this.identifier.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (this == obj)
/* 55 */       return true; 
/* 56 */     if (obj instanceof TroubleCode) {
/* 57 */       TroubleCode other = (TroubleCode)obj;
/* 58 */       boolean ret = Util.equals(this.identifier, other.identifier);
/* 59 */       return ret;
/*    */     } 
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIdentifier() {
/* 66 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public static synchronized List getDomain(ClientContext context) {
/* 74 */     if (domain == null) {
/* 75 */       List<TroubleCode> _domain = new LinkedList();
/*    */       try {
/* 77 */         SIDataAdapterFacade.getInstance(context).getSICTOCService();
/* 78 */         Iterator<String> iter = SIDataAdapterFacade.getInstance(context).getSI().provideDTCs().iterator();
/* 79 */         while (iter.hasNext()) {
/* 80 */           _domain.add(getInstance(iter.next()));
/*    */         
/*    */         }
/*    */       }
/* 84 */       catch (NullPointerException e) {
/* 85 */         log.warn("unable to retrieve domain TroubleCode, ignoring - exception: " + e, e);
/*    */       } 
/*    */       
/* 88 */       domain = Collections.unmodifiableList(_domain);
/*    */     } 
/* 90 */     return domain;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TroubleCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */