/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class WIS
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(WIS.class);
/*     */   
/*     */   public static final String SAAB_MAKE;
/*     */   
/*     */   public static final String SAAB_GROUP;
/*     */   
/*     */   public static final String[] SAAB_DATA;
/*     */   
/*     */   static {
/*  32 */     String _tmp = ApplicationContext.getInstance().getProperty("frame.wis.saab-make");
/*  33 */     if (_tmp == null) {
/*  34 */       _tmp = "SAAB";
/*     */     }
/*  36 */     SAAB_MAKE = _tmp;
/*     */     
/*  38 */     _tmp = ApplicationContext.getInstance().getProperty("frame.wis.saab-group");
/*  39 */     if (_tmp == null) {
/*  40 */       _tmp = "wis";
/*     */     }
/*  42 */     SAAB_GROUP = _tmp;
/*     */     
/*  44 */     _tmp = ApplicationContext.getInstance().getProperty("frame.si.saab-data");
/*  45 */     if (_tmp == null) {
/*  46 */       _tmp = "SAAB; CADILLAC (GME)";
/*     */     }
/*  48 */     SAAB_DATA = _tmp.split(";");
/*  49 */     for (int i = 0; i < SAAB_DATA.length; i++) {
/*  50 */       SAAB_DATA[i] = StringUtilities.removeWhitespaces(SAAB_DATA[i].toLowerCase(Locale.ENGLISH));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean hasSaabData(ClientContext context) {
/*     */     try {
/*  56 */       String make = VCFacade.getInstance(context).getCurrentSalesmake();
/*  57 */       if (make != null) {
/*  58 */         make = StringUtilities.removeWhitespaces(make.toLowerCase(Locale.ENGLISH));
/*  59 */         for (int i = 0; i < SAAB_DATA.length; i++) {
/*  60 */           if (SAAB_DATA[i].equals(make)) {
/*  61 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*  66 */     } catch (Exception x) {}
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSaabLoginGroup(LoginInfo loginInfo) {
/*     */     try {
/*  75 */       Set<E> groups = loginInfo.getGroup2ManufMap().keySet();
/*  76 */       if (groups == null || groups.size() != 1) {
/*  77 */         return false;
/*     */       }
/*  79 */       return groups.iterator().next().equals(SAAB_GROUP);
/*     */     }
/*  81 */     catch (Exception e) {
/*  82 */       log.warn("unable to determine if login is saab login group, returning false - exception:" + e);
/*  83 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void replaceUsrGroup2ManufMap(LoginInfo loginInfo) {
/*  91 */     Set<String> set = new HashSet();
/*  92 */     set.add(SAAB_MAKE);
/*     */     
/*  94 */     Map<Object, Object> usrGroup2Manuf = new HashMap<Object, Object>();
/*  95 */     usrGroup2Manuf.put(SAAB_GROUP, set);
/*  96 */     loginInfo.setGroup2ManufMap(usrGroup2Manuf);
/*     */   }
/*     */   
/*     */   public static boolean isSaabUserGroup(ClientContext context) {
/*     */     try {
/* 101 */       SharedContext sc = context.getSharedContext();
/* 102 */       Set<K> groups = (sc.getUsrGroup2ManufMap() != null) ? sc.getUsrGroup2ManufMap().keySet() : null;
/* 103 */       if (groups == null || groups.size() != 1) {
/* 104 */         return false;
/*     */       }
/* 106 */       return groups.iterator().next().equals(SAAB_GROUP);
/*     */     }
/* 108 */     catch (Exception e) {
/* 109 */       log.error("unable to retrieve group-manufacturer map from ACL module - error:" + e, e);
/* 110 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSaabResourceAuthorized(ClientContext context) {
/* 115 */     Set makes = null;
/*     */ 
/*     */     
/*     */     try {
/* 119 */       SharedContext sc = context.getSharedContext();
/* 120 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 121 */       makes = aclMI.getAuthorizedResources("Salesmake", sc.getUsrGroup2ManufMap(), sc.getCountry());
/* 122 */       return makes.contains(SAAB_MAKE);
/* 123 */     } catch (Exception e) {
/* 124 */       log.error("unable to retrieve sales-make list from ACL module - error:" + e, e);
/* 125 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSalesmakeSaab(ClientContext context) {
/* 130 */     return VCFacade.getInstance(context).isCurrentSalesmake(SAAB_MAKE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection checkSalesmakes(ClientContext context, Collection makes) {
/* 141 */     if (isSaabUserGroup(context)) {
/*     */       
/* 143 */       List<Object> result = new ArrayList();
/* 144 */       for (Iterator iter = makes.iterator(); iter.hasNext(); ) {
/* 145 */         Object make = iter.next();
/* 146 */         if (make.toString().equals(SAAB_MAKE)) {
/* 147 */           result.add(make);
/*     */         }
/*     */       } 
/* 150 */       return result;
/* 151 */     }  if (!isSaabResourceAuthorized(context)) {
/* 152 */       List<Object> result = new ArrayList();
/* 153 */       for (Iterator iter = makes.iterator(); iter.hasNext(); ) {
/* 154 */         Object make = iter.next();
/* 155 */         if (!make.toString().equals(SAAB_MAKE)) {
/* 156 */           result.add(make);
/*     */         }
/*     */       } 
/* 159 */       return result;
/*     */     } 
/* 161 */     return makes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void filterSalesmakes(ClientContext context, List makes) {
/* 170 */     if (isSaabUserGroup(context)) {
/* 171 */       for (Iterator<String> iter = makes.iterator(); iter.hasNext(); ) {
/* 172 */         String make = iter.next();
/* 173 */         if (!make.equals(SAAB_MAKE)) {
/* 174 */           iter.remove();
/*     */         }
/*     */       } 
/* 177 */     } else if (!isSaabResourceAuthorized(context) && makes.contains(SAAB_MAKE)) {
/*     */       
/* 179 */       for (Iterator<String> iter = makes.iterator(); iter.hasNext(); ) {
/* 180 */         String make = iter.next();
/* 181 */         if (make.equals(SAAB_MAKE))
/* 182 */           iter.remove(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\WIS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */