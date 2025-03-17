/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScoutChainImpl
/*    */   implements IScout
/*    */ {
/* 22 */   private List scouts = new ArrayList();
/*    */   
/* 24 */   private static final Logger log = Logger.getLogger(ScoutChainImpl.class);
/*    */   
/*    */   public ScoutChainImpl() {
/* 27 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.login.scout.class.");
/* 28 */     SortedSet keys = new TreeSet(subConfigurationWrapper.getKeys());
/* 29 */     for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/* 30 */       String className = subConfigurationWrapper.getProperty(iter.next());
/* 31 */       this.scouts.add(ScoutFactory.getInstance().getScout(className));
/*    */     } 
/* 33 */     if (this.scouts.size() == 0) {
/* 34 */       log.warn("NO LOGIN SCOUT CONFIGURED !!!!");
/*    */     } else {
/*    */       
/* 37 */       log.debug("scouts: " + getScoutId());
/*    */     } 
/*    */   }
/*    */   
/*    */   public LoginInfo getLoginInfo(Map params) {
/* 42 */     String user = (String)params.get("user");
/* 43 */     LoginInfo result = null;
/* 44 */     Iterator<BaseScout> it = this.scouts.iterator();
/* 45 */     BaseScout scout = null;
/* 46 */     while (it.hasNext() && (result == null || !result.isAuthorized())) {
/*    */       try {
/* 48 */         scout = it.next();
/* 49 */         if (scout.isPortalMapped()) {
/* 50 */           log.debug("Scout is mapped to portal - skipping scout in chain: " + scout.getScoutId());
/*    */           continue;
/*    */         } 
/* 53 */         log.debug("scout verifying login request: " + scout.getScoutId());
/* 54 */         result = scout.getLoginInfo(params);
/* 55 */         if (result == null) {
/* 56 */           log.info(scout.getScoutId() + ": " + user + " ignored."); continue;
/* 57 */         }  if (result.isAuthorized()) {
/* 58 */           log.info(scout.getScoutId() + ": " + user + " authorized."); continue;
/*    */         } 
/* 60 */         log.info(scout.getScoutId() + ": " + user + " not authorized.");
/*    */       }
/* 62 */       catch (RuntimeException e) {
/* 63 */         if (this.scouts.size() == 1 && (e instanceof ScoutException || e instanceof InteractionException)) {
/* 64 */           throw e;
/*    */         }
/* 66 */         log.warn("unable to retrieve login info from " + String.valueOf(scout) + " - exception: " + e);
/*    */       }
/* 68 */       catch (Exception e) {
/* 69 */         log.warn("unable to retrieve login info from " + String.valueOf(scout) + " - exception: " + e);
/*    */       } 
/*    */     } 
/* 72 */     return result;
/*    */   }
/*    */   
/*    */   public String getScoutClassName() {
/* 76 */     StringBuffer strbuf = new StringBuffer();
/* 77 */     Iterator it = this.scouts.iterator();
/* 78 */     while (it.hasNext()) {
/* 79 */       strbuf.append((new StringBuilder()).append(it.next()).append(" ").toString());
/*    */     }
/* 81 */     return strbuf.toString();
/*    */   }
/*    */   
/*    */   public String getScoutId() {
/* 85 */     StringBuffer strbuf = new StringBuffer();
/* 86 */     Iterator<IScout> it = this.scouts.iterator();
/* 87 */     while (it.hasNext()) {
/* 88 */       strbuf.append(((IScout)it.next()).getScoutId() + " ");
/*    */     }
/* 90 */     return strbuf.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPortalMapped() {}
/*    */   
/*    */   public boolean isPortalMapped() {
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\ScoutChainImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */