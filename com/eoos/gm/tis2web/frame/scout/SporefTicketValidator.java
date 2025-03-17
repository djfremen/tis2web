/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eds.spo.util.GenerateTicket;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SporefTicketValidator
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(SporefTicketValidator.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Map params) {
/* 20 */     boolean result = false;
/* 21 */     String appParm = (String)params.get("appParm");
/* 22 */     String user = (String)params.get("user");
/* 23 */     String group = (String)params.get("group");
/* 24 */     String origin = (String)params.get("origin");
/* 25 */     String ticket = (String)params.get("ticket");
/* 26 */     String secret = ApplicationContext.getInstance().getProperty("frame.scout.sporefticket.secret");
/* 27 */     long roundby = -1L;
/*    */     try {
/* 29 */       roundby = Long.parseLong(ApplicationContext.getInstance().getProperty("frame.scout.sporefticket.roundby"));
/* 30 */     } catch (Exception e) {
/* 31 */       log.warn("unable to parse cfg value, using default - exception: " + e, e);
/*    */     } 
/*    */ 
/*    */     
/* 35 */     GenerateTicket gt = new GenerateTicket();
/* 36 */     gt.setApplication(appParm);
/* 37 */     gt.setGroup(null);
/* 38 */     gt.setOrigin(origin);
/* 39 */     gt.setRoundBy(roundby);
/* 40 */     gt.setSecret(secret);
/* 41 */     gt.setUser(user);
/* 42 */     result = gt.isValidTicket(ticket);
/*    */     
/* 44 */     if (log.isDebugEnabled()) {
/* 45 */       log.debug("Ticket_validation(appParm=" + appParm + ", user=" + user + ", group=" + group + ", origin=" + origin + ", ticket=" + ticket + ", secret=" + secret + ", roundby=" + roundby + "): " + result);
/*    */     }
/* 47 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\SporefTicketValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */