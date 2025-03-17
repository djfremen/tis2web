/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.SortedSet;
/*    */ import java.util.StringTokenizer;
/*    */ import java.util.TreeSet;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PortalScoutMapper
/*    */   implements IScout
/*    */ {
/*    */   private IScout iscout;
/*    */   private Map portalScoutMap;
/* 23 */   private static final Logger log = Logger.getLogger(PortalScoutMapper.class);
/*    */   
/*    */   public PortalScoutMapper(IScout iscout) {
/* 26 */     this.iscout = iscout;
/* 27 */     this.portalScoutMap = new HashMap<Object, Object>();
/* 28 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.login.scout.portalmapping.");
/* 29 */     SortedSet keys = new TreeSet(subConfigurationWrapper.getKeys());
/* 30 */     for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/* 31 */       String key = iter.next();
/* 32 */       String portalId = subConfigurationWrapper.getProperty(key);
/*    */       try {
/* 34 */         String cName = key.substring(0, key.lastIndexOf('.'));
/* 35 */         StringTokenizer strTok = new StringTokenizer(iscout.getScoutClassName());
/* 36 */         while (strTok.hasMoreElements()) {
/* 37 */           String scoutClassName = strTok.nextToken();
/* 38 */           if (scoutClassName.lastIndexOf(cName) >= 0) {
/* 39 */             IScout scout = ScoutFactory.getInstance().getScout(scoutClassName);
/* 40 */             scout.setPortalMapped();
/* 41 */             this.portalScoutMap.put(portalId, scout);
/* 42 */             log.debug("portal-scout mapping: (" + portalId + ", " + scout.getClass().getName() + ")");
/*    */           }
/*    */         
/*    */         }
/*    */       
/* 47 */       } catch (Exception e) {
/* 48 */         log.error("Cannot assign portals to scout/portalId: " + key + "/" + portalId + ", " + e, e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public LoginInfo getLoginInfo(Map params) {
/* 54 */     LoginInfo retValue = null;
/* 55 */     String portalId = (String)params.get("portal_ID");
/* 56 */     if (!Util.isNullOrEmpty(portalId)) {
/*    */       try {
/* 58 */         log.debug("Trying portal-to-scout mapping for portal_Id: " + portalId + " ...");
/* 59 */         retValue = ((IScout)this.portalScoutMap.get(portalId)).getLoginInfo(params);
/* 60 */         log.debug("Portal-to-scout-mapping applied: (" + portalId + ", " + ((IScout)this.portalScoutMap.get(portalId)).getScoutId() + ")");
/* 61 */       } catch (ScoutException e) {
/* 62 */         throw e;
/* 63 */       } catch (Exception e) {
/* 64 */         log.warn("Cannot apply portal-to-scout-mapping for portal_ID: " + portalId + " - exception:", e);
/*    */       } 
/*    */     } else {
/* 67 */       log.debug("no portal id specified, skipping portal mapping");
/*    */     } 
/* 69 */     if (retValue == null) {
/* 70 */       retValue = this.iscout.getLoginInfo(params);
/*    */     }
/*    */     
/* 73 */     return retValue;
/*    */   }
/*    */   
/*    */   public String getScoutClassName() {
/* 77 */     return this.iscout.getScoutClassName();
/*    */   }
/*    */   
/*    */   public String getScoutId() {
/* 81 */     return this.iscout.getScoutId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPortalMapped() {}
/*    */   
/*    */   public boolean isPortalMapped() {
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\PortalScoutMapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */